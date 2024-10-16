package com.mstfvrl.OnCallPersonelSchedule.service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
public class ProgramManager {

	private final Random random = new Random();
	@Getter
	private List<Personel> personelList;
	@Getter
	private SelectedMonth selectedMonth;

	public ProgramManager(YearMonth ym) {
		this.selectedMonth = new SelectedMonth(ym.getYear(), ym.getMonthValue());
		this.personelList = new ArrayList<>();
		StaticPrimitives.getSettings().getNames().forEach(name -> personelList.add(new Personel(name)));
	}

	public int getDayCountInSelectedMonth() {
		return selectedMonth.getDayCountInMonth();
	}

	public List<Integer> getSpecifiedDayPoints() {
		return selectedMonth.getSpecifiedDayPoints();
	}

	public void setSpecifiedDayPoints(List<Integer> specifiedDayPoints) {
		selectedMonth.setSpecifiedDayPoints(specifiedDayPoints);
	}

	public List<String> getPersonelsOffDays() {
		return personelList.stream().map(Personel::getOffDays).collect(Collectors.toList());
	}

	public void setPersonelsOffDays(List<String> offDays) {
		for (int i = 0; i < offDays.size(); i++) {
			personelList.get(i).addOffDays(Arrays.stream(offDays.get(i).split(",")).filter(day -> !day.trim().isEmpty())
					.map(String::trim).map(Integer::parseInt).toArray(Integer[]::new));
		}
	}

	public List<String> getPersonelVolunteerDays() {
		return personelList.stream().map(Personel::getVolunteerDays).collect(Collectors.toList());
	}

	public void setPersonelVolunteerDays(List<String> volunteerDays) {
		for (int i = 0; i < volunteerDays.size(); i++) {
			personelList.get(i).addVolunteerDays(
					Arrays.stream(volunteerDays.get(i).split(",")).filter(day -> !day.trim().isEmpty())
							.map(String::trim).map(Integer::parseInt).toArray(Integer[]::new));
		}
	}

	public DayInMonth getDayMonth(int dayIndex) {
		return selectedMonth.getDaysInMonth().get(dayIndex);
	}

	public Personel getOnCallStuff(int dayIndex, int stuffIndex) {
		return selectedMonth.getDaysInMonth().get(dayIndex).getOnCallStuffSquad().getPersonel(stuffIndex);
	}

	public void resetStuffProperties() {
		personelList.forEach(Personel::resetPersonelProperties);
	}

	public void makeOnCallStuffList() {
		try {
			var orderedByDayPointDayList = selectedMonth.getDaysInMonth().stream()
					.sorted(Comparator.comparingInt(DayInMonth::getSpecifiedDayPoint).reversed())
					.collect(Collectors.toList());

			for (DayInMonth day : orderedByDayPointDayList) {
				for (int k = 0; k < StaticPrimitives.getSettings().getOnCallPersonelCount(); k++) {
					Personel personel = pickAVolunteerPersonel(day.getDayIndex());
					if (personel == null) {
						personel = pickAOnCallStuff(day.getDayIndex());
					}

					personel.setScore(personel.getScore() + day.getSpecifiedDayPoint());
					personel.setOnCallCount(personel.getOnCallCount() + 1);
					personel.addNotAvailableDays(day.getDayIndex());

					selectedMonth.getDaysInMonth().get(day.getDayIndex() - 1).getOnCallStuffSquad().setPersonel(k,
							personel);
				}
			}
		} catch (Exception err) {
			err.printStackTrace(); // Or log the error using your logging framework
		}
	}

	public List<List<String>> toNamesDataTable() {

		var result = new ArrayList<List<String>>();
		personelList.forEach(p -> result.add(p.toRowArray()));
		return result;
	}

	public List<List<String>> toHalfScheduleDataTable() {

		var result = new ArrayList<List<String>>();
		selectedMonth.getDaysInMonth().forEach(d -> result.add(d.toHalfRowArray()));
		return new ArrayList<List<String>>();
	}

	public List<List<String>> toFullScheduleDataTable() {
		var result = new ArrayList<List<String>>();
		selectedMonth.getDaysInMonth().forEach(d -> result.add(d.toFullRowArray()));
		return new ArrayList<List<String>>();
	}

	private Personel pickAVolunteerPersonel(int day) {
		List<Personel> volunteers = personelList.stream().filter(p -> p.isVolunteerDay(day) && p.isAvailableDay(day))
				.collect(Collectors.toList());

		return !volunteers.isEmpty() ? volunteers.get(random.nextInt(volunteers.size())) : null;
	}

	private Personel pickAOnCallStuff(int day) {
		List<Personel> availables = personelList.stream()
				.filter(p -> p.isAvailableDay(day) && !p.isVolunteerDayApproaching(day)).collect(Collectors.toList());

		if (!availables.isEmpty()) {
			int minScore = availables.stream().mapToInt(Personel::getScore).min().orElseThrow();
			List<Personel> minScorePersonels = availables.stream().filter(p -> p.getScore() == minScore)
					.collect(Collectors.toList());
			return minScorePersonels.get(random.nextInt(minScorePersonels.size()));
		} else {
			throw new NoSuchElementException(StaticPrimitives.ErrorMessages.AVAILABLE_PERSONNEL_COUNT + ": "
					+ StaticPrimitives.ErrorMessages.AVAILABLE_PERSONNEL_NOT_FOUND);
		}
	}
}