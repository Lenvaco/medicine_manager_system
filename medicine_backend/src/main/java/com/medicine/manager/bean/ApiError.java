package com.medicine.manager.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lenvaco
 * @date 2019/9/27 9:31
 */
@Data
public class ApiError {
	private Integer status;
	private String message;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timestamp;

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	public ApiError(Integer status,String message) {
		this();
		this.status = status;
		this.message = message;
	}
}
