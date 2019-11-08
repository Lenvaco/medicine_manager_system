package com.medicine.manager.bean.dto;

import com.medicine.manager.model.Job;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lenvaco
 * @date 2019/10/21 16:09
 */
@Data
public class JobSmallDTO implements Serializable {
	private static final long serialVersionUID = -4079022615455894020L;
	private Long id;
	private String name;
	public JobSmallDTO(Job job) {
		this.id = job.getId();
		this.name = job.getName();
	}
}
