package com.medicine.manager.web.handler;

import com.medicine.manager.bean.ApiError;
import com.medicine.manager.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author lenvaco
 * @date 2019/9/27 9:33
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 处理所有不可知的异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Throwable.class)
	public ResponseEntity handleException(Throwable e){
		e.printStackTrace();;
		// 打印堆栈信息
		log.error("发生了错误： {}", e.getMessage());
		ApiError apiError = new ApiError(BAD_REQUEST.value(), e.getMessage());
		return buildResponseEntity(apiError);
	}
	/**
	 * 处理自定义异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = BadRequestException.class)
	public ResponseEntity<ApiError> badRequestException(BadRequestException e) {
		// 打印堆栈信息
		log.error("发生了错误： {}", e.getMessage());
		ApiError apiError = new ApiError(e.getStatus(), e.getMessage());
		return buildResponseEntity(apiError);
	}

	/**
	 * 统一返回
	 * @param apiError
	 * @return
	 */
	private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity(apiError, HttpStatus.valueOf(apiError.getStatus()));
	}
}
