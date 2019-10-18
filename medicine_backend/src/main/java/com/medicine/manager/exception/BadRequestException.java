package com.medicine.manager.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author lenvaco
 * @date 2019/9/27 9:12
 */
@Getter
public class BadRequestException extends RuntimeException{

	private Integer status = BAD_REQUEST.value();

	public BadRequestException(String msg){
		super(msg);
	}

	public BadRequestException(HttpStatus status, String msg){
		super(msg);
		this.status = status.value();
	}
}