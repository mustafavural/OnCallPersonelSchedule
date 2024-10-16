package com.mstfvrl.OnCallPersonelSchedule.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.Setter;

public class Personel {

	@Getter
	private final String fullName;
	private final List<Integer> offDays = new ArrayList<>();
	private final List<Integer> volunteerDays = new ArrayList<>();
	private final List<Integer> notAvailableDays = new ArrayList<>();
	@Getter
	@Setter
	private int score = 0;
	@Getter
	@Setter
	private int onCallCount = 0;

	public Personel(String fullName) {
		this.fullName = fullName;
	}

	public String getOffDays() {
		return offDays.stream().map(String::valueOf).collect(Collectors.joining(","));
	}

	public String getVolunteerDays() {
		return volunteerDays.stream().map(String::valueOf).collect(Collectors.joining(","));
	}

	private static List<Integer> getDaysRange(int startDay) {
		return IntStream.range(startDay, startDay + StaticPrimitives.getSettings().getOffDayCount() + 1).boxed()
				.collect(Collectors.toList());
	}

	public boolean isAvailableDay(int day) {
		return getDaysRange(day).stream().noneMatch(notAvailableDays::contains);
	}

	public boolean isVolunteerDay(int day) {
		return volunteerDays.contains(day);
	}

	public boolean isVolunteerDayApproaching(int day) {
		return getDaysRange(day).stream().anyMatch(volunteerDays::contains);
	}

	public void addNotAvailableDays(int day) {
		notAvailableDays.addAll(getDaysRange(day));
	}

	public void removeNotAvailableDays(int day) {
		notAvailableDays.removeAll(getDaysRange(day));
	}

	public void addOffDays(Integer... days) {
		offDays.addAll(Arrays.asList(days));
		notAvailableDays.addAll(Arrays.asList(days));
	}

	public void removeOffDays(Integer... days) {
		offDays.removeAll(Arrays.asList(days));
		notAvailableDays.removeAll(Arrays.asList(days));
	}

	public void addVolunteerDays(Integer... days) {
		volunteerDays.addAll(Arrays.asList(days));
	}

	public void removeVolunteerDays(Integer... days) {
		volunteerDays.removeAll(Arrays.asList(days));
	}

	public void resetPersonelProperties() {
		score = 0;
		onCallCount = 0;
		offDays.clear();
		notAvailableDays.clear();
		volunteerDays.clear();
	}

	public List<String> toRowArray() {
		return Arrays.asList(fullName, getOffDays(), getVolunteerDays(), String.valueOf(score),
				String.valueOf(onCallCount));
	}
}