package org.kuali.hr.time.flsa;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class FlsaWeek {
	private List<FlsaDay> flsaDays = new ArrayList<FlsaDay>();
	private int flsaDayConstant;
	private Time flsaTime;
	
	public FlsaWeek() {
		
	}
	
	public FlsaWeek(int dayConstant, Time time) {
		this.flsaDayConstant = dayConstant;
		this.flsaTime = time;
	}

	public List<FlsaDay> getFlsaDays() {
		return flsaDays;
	}

	public void setFlsaDays(List<FlsaDay> flsaDays) {
		this.flsaDays = flsaDays;
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

	public Time getFlsaTime() {
		return flsaTime;
	}

	public void setFlsaTime(Time flsaTime) {
		this.flsaTime = flsaTime;
	}
}
