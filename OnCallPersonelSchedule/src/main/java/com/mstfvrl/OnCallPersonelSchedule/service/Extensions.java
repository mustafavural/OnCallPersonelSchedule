package com.mstfvrl.OnCallPersonelSchedule.service;

import java.util.List;
import java.util.function.Consumer;

public class Extensions {

	// Generic method to perform an action on each element of the list
	public static <T> void forEach(List<T> source, Consumer<T> action) {
		for (T item : source) {
			action.accept(item);
		}
	}

	// Method to convert a string to an int
	public static int toInt32(String value) {
		return Integer.parseInt(value);
	}
}