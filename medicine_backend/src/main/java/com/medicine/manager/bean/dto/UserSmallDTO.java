package com.medicine.manager.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lenvaco
 * @date 2019/11/17 14:30
 */
@Data
public class UserSmallDTO implements Serializable {
	private static final long serialVersionUID = 1183499425141283399L;
	@NotNull
	private Long id;
	private String name;
}
