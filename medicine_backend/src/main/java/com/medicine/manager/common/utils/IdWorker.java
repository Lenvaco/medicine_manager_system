package com.medicine.manager.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * <p>名称：IdWorker.java</p>
 * <p>描述：分布式自增长ID</p>
 * <pre>
 *     Twitter的 Snowflake　JAVA实现方案
 * </pre>
 * 核心代码为其IdWorker这个类实现，其原理结构如下，我分别用一个0表示一位，用—分割开部分的作用：
 * 1||0---0000000000 0000000000 0000000000 0000000000 0 --- 00000 ---00000 ---000000000000
 * 在上面的字符串中，第一位为未使用（实际上也可作为long的符号位），接下来的41位为毫秒级时间，
 * 然后5位datacenter标识位，5位机器ID（并不算标识符，实际是为线程标识），
 * 然后12位该毫秒内的当前毫秒内的计数，加起来刚好64位，为一个Long型。
 * 这样的好处是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞（由datacenter和机器ID作区分），
 * 并且效率较高，经测试，snowflake每秒能够产生26万ID左右，完全满足需要。
 * <p>
 * 64位ID (42(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))
 *
 * @author Polim
 */
@Slf4j
public class IdWorker {
	// 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
	private final static long START_STMP  = 1288834974657L;
	// 机器标识位数
	private final static long MACHINE_BIT = 5L;
	// 数据中心标识位数
	private final static long DATACENTER_BIT  = 5L;
	// 毫秒内自增位
	private final static long SEQUENCE_BIT = 12L;
	// 机器ID最大值
	private final static long MAX_MACHINE_NUM  = -1L ^ (-1L << MACHINE_BIT);
	// 数据中心ID最大值
	private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT );

	private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
	// 机器ID偏左移12位
	private final static long MACHINE_ID_SHIFT = SEQUENCE_BIT;
	// 数据中心ID左移17位
	private final static long DATA_CENTER_ID_SHIFT = SEQUENCE_BIT + MACHINE_BIT;
	// 时间毫秒左移22位
	private final static long TIME_STAMP_SHIFT = SEQUENCE_BIT + MACHINE_BIT + DATACENTER_BIT ;
	/* 上次生产id时间戳 */
	private static long lastTimestamp = -1L;
	// 序列号
	private long sequence = 0L;
	//工作机器id
	private final long machineId;
	// 数据标识id部分
	private final long datacenterId;

	public IdWorker(){
		this.datacenterId = getDatacenterId(MAX_DATACENTER_NUM);
		this.machineId = getMAX_MACHINE_NUM (datacenterId, MAX_MACHINE_NUM );
	}
	/**
	 * @param machineId
	 *            工作机器ID
	 * @param datacenterId
	 *            序列号
	 */
	public IdWorker(long machineId, long datacenterId) {
		if (machineId > MAX_MACHINE_NUM  || machineId < 0) {
			throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_MACHINE_NUM ));
		}
		if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
			throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATACENTER_NUM));
		}
		this.machineId = machineId;
		this.datacenterId = datacenterId;
	}
	/**
	 * 获取下一个ID
	 *
	 * @return
	 */
	public synchronized long nextId() {
		long timestamp = timeGen();
		if (timestamp < lastTimestamp) {
			throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		}

		if (lastTimestamp == timestamp) {
			// 当前毫秒内，则+1
			sequence = (sequence + 1) & MAX_SEQUENCE;
			if (sequence == 0) {
				// 当前毫秒内计数满了，则等待下一秒
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0L;
		}
		lastTimestamp = timestamp;
		// ID偏移组合生成最终的ID，并返回ID
		long nextId = ((timestamp - START_STMP ) << TIME_STAMP_SHIFT)
				| (datacenterId << DATA_CENTER_ID_SHIFT)
				| (machineId << MACHINE_ID_SHIFT) | sequence;

		return nextId;
	}

	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

	/**
	 * <p>
	 * 获取 MAX_MACHINE_NUM
	 * </p>
	 */
	protected static long getMAX_MACHINE_NUM (long datacenterId, long MAX_MACHINE_NUM ) {
		StringBuffer mpid = new StringBuffer();
		mpid.append(datacenterId);
		String name = ManagementFactory.getRuntimeMXBean().getName();
		if (!name.isEmpty()) {
			/*
			 * GET jvmPid
			 */
			mpid.append(name.split("@")[0]);
		}
		/*
		 * MAC + PID 的 hashcode 获取16个低位
		 */
		return (mpid.toString().hashCode() & 0xffff) % (MAX_MACHINE_NUM  + 1);
	}

	/**
	 * <p>
	 * 数据标识id部分
	 * </p>
	 */
	protected static long getDatacenterId(long MAX_DATACENTER_NUM) {
		long id = 0L;
		try {
			InetAddress ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			if (network == null) {
				id = 1L;
			} else {
				byte[] mac = network.getHardwareAddress();
				id = ((0x000000FF & (long) mac[mac.length - 1])
						| (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
				id = id % (MAX_DATACENTER_NUM + 1);
			}
		} catch (Exception e) {
			log.warn(" getDatacenterId: " + e.getMessage());
		}
		return id;
	}


}
