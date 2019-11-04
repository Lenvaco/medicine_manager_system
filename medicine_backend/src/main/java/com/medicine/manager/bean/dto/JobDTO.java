package com.medicine.manager.bean.dto;

import com.medicine.manager.model.Job;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lenvaco
 * @date 2019/10/21 16:07
 */
@Data
public class JobDTO implements Serializable {
	private static final long serialVersionUID = -974264029787505244L;

	private Long id;
	private String name;
	private Long sort;
	private DeptDTO dept;
	private Date createTime;

	public JobDTO(Job job) {
		this.id = job.getJId();
		this.name = job.getName();
		this.sort = job.getSort();
		this.dept = new DeptDTO(job.getDept());
		this.createTime = job.getCreateTime();
	}
}
