package com.mstfvrl.OnCallPersonelSchedule.service;

import java.time.YearMonth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProgramConfiguraiton {

	@Bean
	ProgramManager getProgramManager(YearMonth ym) {
		return new ProgramManager(ym);
	}

	@Bean
	YearMonth getYearMonth() {
		return YearMonth.of(2024, 10);
	}
}
