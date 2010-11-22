package org.kuali.hr.time.flsa;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;

public class FlsaWeek {
	private List<FlsaDay> flsaDays = new ArrayList<FlsaDay>();
	private int flsaDayConstant;
	private LocalTime flsaTime;
	private LocalTime payPeriodBeginTime;
	
	public FlsaWeek() {
		
	}
	
	public FlsaWeek(int dayConstant, LocalTime flsaTime, LocalTime payPeriodBeginTime) {
		this.flsaDayConstant = dayConstant;
		this.flsaTime = flsaTime;
		this.payPeriodBeginTime = payPeriodBeginTime;
	}

	public List<FlsaDay> getFlsaDays() {
		return flsaDays;
	}
	
	public void addFlsaDay(FlsaDay flsaDay) {
		flsaDays.add(flsaDay);
	}

	/**
	 * Check to see if the first week is Full or not.
	 * 
	 * If the first week of a pay period has an FLSA starting time that is before
	 * the "Virtual Day" pay period start time, part of the time required for this
	 * first day will be in the previous pay period even if we have 7 days.
	 * 
	 * @return
	 */
	public boolean isFirstWeekFull() {
		if (flsaDays.size() == 7) {
			return (flsaTime.isBefore(payPeriodBeginTime)) ? false : true;
		} else {
			return false;
		}
	}
	
	public int getFlsaDayConstant() {
		return flsaDayConstant;
	}

	public void setFlsaDayConstant(int flsaDayConstant) {
		this.flsaDayConstant = flsaDayConstant;
	}

	public LocalTime getFlsaTime() {
		return flsaTime;
	}

	public void setFlsaTime(LocalTime flsaTime) {
		this.flsaTime = flsaTime;
	}
}
