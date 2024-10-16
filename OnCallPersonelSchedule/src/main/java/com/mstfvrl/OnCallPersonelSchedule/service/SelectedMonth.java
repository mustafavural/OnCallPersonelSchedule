package com.mstfvrl.OnCallPersonelSchedule.service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

public class SelectedMonth {

	@Getter
	private int dayCountInMonth;
	@Getter
	private final List<DayInMonth> daysInMonth;

	public SelectedMonth(int year, int month) {
		this.dayCountInMonth = YearMonth.of(year, month).lengthOfMonth();
		StaticPrimitives.setSelectedMonthName(
				java.time.format.DateTimeFormatter.ofPattern("MMMM").format(java.time.LocalDate.of(year, month, 1)));
		this.daysInMonth = new ArrayList<>();

		for (int day = 1; day <= dayCountInMonth; day++) {
			java.time.LocalDate date = java.time.LocalDate.of(year, month, day);
			int dayOfWeek = date.getDayOfWeek().getValue() - 1; // 0'dan başlatmak için
			FileManager.createInstance();
			int dayPoint = StaticPrimitives.getSettings().getDayPoints().get(dayOfWeek);
			daysInMonth.add(new DayInMonth(day, dayOfWeek, dayPoint));
		}
	}

	public String getMonthName() {
		return StaticPrimitives.getSelectedMonthName();
	}

	public List<Integer> getSpecifiedDayPoints() {
		return daysInMonth.stream().map(DayInMonth::getSpecifiedDayPoint).collect(Collectors.toList());
	}

	public void setSpecifiedDayPoints(List<Integer> specifiedDayPoints) {
		for (int i = 0; i < specifiedDayPoints.size(); i++) {
			daysInMonth.get(i).setSpecifiedDayPoint(specifiedDayPoints.get(i));
		}
	}

	public List<List<String>> toHalfDataTable() {
		var result = new ArrayList<List<String>>();

		for (DayInMonth dayInMonth : daysInMonth) {
			result.add(dayInMonth.toHalfRowArray());
		}

		return result;
	}

	public List<List<String>> toFullDataTable() {
		var result = new ArrayList<List<String>>();

		for (DayInMonth dayInMonth : daysInMonth) {
			result.add(dayInMonth.toFullRowArray());
		}

		return result;
	}
}
