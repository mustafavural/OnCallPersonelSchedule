package com.mstfvrl.OnCallPersonelSchedule.service;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

public class StaticPrimitives {

	public static final String CONFIG_FILE = "config.xml";

	@Getter
	@Setter
	private static Settings settings;

	@Getter
	@Setter
	private static String selectedMonthName;

	@Getter
	public static final Map<String, String> monthNames = Map.ofEntries(Map.entry("1", "Ocak"), Map.entry("2", "Şubat"),
			Map.entry("3", "Mart"), Map.entry("4", "Nisan"), Map.entry("5", "Mayıs"), Map.entry("6", "Haziran"),
			Map.entry("7", "Temmuz"), Map.entry("8", "Ağustos"), Map.entry("9", "Eylül"), Map.entry("10", "Ekim"),
			Map.entry("11", "Kasım"), Map.entry("12", "Aralık"));

	static {
		// Initialization logic if needed
		// FileManager.createInstance();
	}

	public static class ErrorMessages {
		public static final String AVAILABLE_PERSONNEL_COUNT = "Musait Personel Sayısı";
		public static final String AVAILABLE_PERSONNEL_NOT_FOUND = "Mevcut havuzda uygun personel bulunamadı. Listeyi kontrol edin.";
	}
}
