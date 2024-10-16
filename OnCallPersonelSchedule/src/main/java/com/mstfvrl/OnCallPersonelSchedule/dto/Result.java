package com.mstfvrl.OnCallPersonelSchedule.dto;

import lombok.Getter;

public class Result implements IResult {
	
	@Getter
	private Boolean isSuccess;
	@Getter
	private String message;

	public Result(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Result(Boolean isSuccess, String message) {
		this(isSuccess);
		this.message = message;
	}
}