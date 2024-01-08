package com.example.demo.alarm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.alarm.service.AlarmService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AlarmController {
	
	private final AlarmService service;
	
	@DeleteMapping("/alarms/{alarm_id}")
	public ResponseEntity deleteAlarm(@PathVariable("alarm_id") int alarmId) {
		service.deleteAlarm(alarmId);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(1);
	}

}
