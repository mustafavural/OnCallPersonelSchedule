package com.mstfvrl.OnCallPersonelSchedule.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mstfvrl.OnCallPersonelSchedule.dto.IDataResult;
import com.mstfvrl.OnCallPersonelSchedule.dto.SuccessDataResult;
import com.mstfvrl.OnCallPersonelSchedule.service.ProgramManager;
import com.mstfvrl.OnCallPersonelSchedule.service.StaticPrimitives;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@AllArgsConstructor
@Data
public class OnCallPSController {

	private ProgramManager programManager;

	@PostMapping(value = "/showRest", consumes = "application/json", produces = "application/json")
	public IDataResult<?> showRest(@RequestBody String Deger) {
		return new SuccessDataResult<String>("Girilen Değer : " + Deger);
	}

	@GetMapping("/getpersonelnames")
	public IDataResult<List<String>> getPersonelNames() {
		return new SuccessDataResult<List<String>>(StaticPrimitives.getSettings().getNames());
	}
}