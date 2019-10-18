package com.medicine.manager.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lenvaco
 * @date 2019/9/22 23:21
 */
@Data
@Getter
@Setter
public class ResponseResult<T> {
    private Integer status;
    private String msg;
    private T data;

}
