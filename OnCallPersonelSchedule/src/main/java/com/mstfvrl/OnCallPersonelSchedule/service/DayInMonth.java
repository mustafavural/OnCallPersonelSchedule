package com.mstfvrl.OnCallPersonelSchedule.service;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
public class DayInMonth {

	private int dayIndex;
	private String dayName;
	private int dayPoint;
	@Setter
	private int specifiedDayPoint;
	private final OnCallStuffSquad onCallStuffSquad = new OnCallStuffSquad();

	public DayInMonth(int dayIndex, int dayOfWeek, int dayPoint) {
		this.dayIndex = dayIndex;
		this.dayName = java.time.DayOfWeek.of(dayOfWeek + 1).name(); // `dayOfWeek` 0'dan başladığı için +1 ekleniyor
		this.dayPoint = dayPoint;
	}

	public List<String> toHalfRowArray() {
		return Arrays.asList(toString(), String.valueOf(specifiedDayPoint));
	}

	public List<String> toFullRowArray() {
		var FullRowArray = toHalfRowArray();
		FullRowArray.addAll(onCallStuffSquad.toRowArray());
		return FullRowArray;
	}

	@Override
	public String toString() {
		return String.format("%d %s %s", dayIndex, StaticPrimitives.getSelectedMonthName(), dayName);
	}
}