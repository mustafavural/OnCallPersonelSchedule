package com.mstfvrl.OnCallPersonelSchedule.service;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OnCallStuffSquad {

	@Getter
	private final List<Personel> personelList = new ArrayList<>(
			StaticPrimitives.getSettings().getOnCallPersonelCount());

	public Personel getPersonel(int index) {
		return personelList.get(index);
	}

	public void setPersonel(int index, Personel personel) {
		personelList.set(index, personel);
	}

	public List<String> toRowArray() {
		return personelList.stream().map(Personel::getFullName).toList();
	}
}