package com.mstfvrl.OnCallPersonelSchedule.service;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;


public class Settings {

	@Getter
	private boolean hasDefaultParameters;
	@Getter
	@Setter
	private int onCallPersonelCount;
	@Getter
	@Setter
	private int offDayCount;
	@Getter
	@Setter
	private List<Integer> dayPoints;
	@Getter
	@Setter
	private List<String> names;

	public Settings() {
		this.hasDefaultParameters = true;
		this.onCallPersonelCount = 5;
		this.offDayCount = 2;
		this.dayPoints = Arrays.asList(16, 8, 8, 8, 8, 16, 24);
		this.names = Arrays.asList("i", "s", "i", "m", "l", "e", "r");
	}

	public Settings(int onCallPersonelCount, int offDayCount, List<Integer> dayPoints, List<String> names) {
		this.hasDefaultParameters = false;
		this.onCallPersonelCount = onCallPersonelCount;
		this.offDayCount = offDayCount;
		this.dayPoints = dayPoints;
		this.names = names;
	}

	@Override
	public String toString() {
		return "Settings{" + "hasDefaultParameters=" + hasDefaultParameters + ", onCallPersonelCount="
				+ onCallPersonelCount + ", offDayCount=" + offDayCount + ", dayPoints="
				+ Arrays.toString(dayPoints.toArray()) + ", names=" + Arrays.toString(names.toArray()) + '}';
	}
}
