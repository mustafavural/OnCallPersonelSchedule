package com.mstfvrl.OnCallPersonelSchedule.dto;

import lombok.Getter;

public class DataResult<T> extends Result implements IDataResult<T> {

	@Getter
	private T data;

	public DataResult(T data, Boolean isSuccess, String message) {
		super(isSuccess, message);
		this.data = data;
	}

	public DataResult(T data, Boolean isSuccess) {
		super(isSuccess);
		this.data = data;
	}
}