package com.example.demo.alarm.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlarmMapper {
	public List<Alarm> findAllAlarm(int memberId);
	public List<Deal> findAllDeal(int memberId);
	public List<String> findAllLikedMember(int productId);
	public void insertAlarm(Map<String, Object> params);
	public void deleteAlarm(int alarmId);
	public void updateReadstate(Alarm alarm);
	public void updateProductClose(Map<String, Object> params);
	public int countAlarmsByMemberId(int memberId);
	public Deal findDealById(int dealId);
	public void updateDealReadState(Deal deal);
}
