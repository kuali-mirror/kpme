package org.kuali.hr.time.flsa;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;

public class FlsaWeek {
	private List<FlsaDay> flsaDays = new ArrayList<FlsaDay>();
	private int flsaDayConstant;
	private LocalTime flsaTime;
	
	public FlsaWeek() {
		
	}
	
	public FlsaWeek(int dayConstant, LocalTime time) {
		this.flsaDayConstant = dayConstant;
		this.flsaTime = time;
	}

	public List<FlsaDay> getFlsaDays() {
		return flsaDays;
	}
	
	public void addFlsaDay(FlsaDay flsaDay) {
		flsaDays.add(flsaDay);
	}
	
	public boolean isFullWeek(){
		// TODO : This assumption could be wrong if the FLSA time is before
		// the virtual day start.
		return flsaDays.size() == 7 ? true : false;
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
