package com.mstfvrl.OnCallPersonelSchedule.dto;

public class SuccessResult extends Result {

	public SuccessResult(String message) {
		super(true, message);
	}

	public SuccessResult() {
		super(true);
	}
}