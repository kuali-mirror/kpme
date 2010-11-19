package org.kuali.hr.time.flsa;

import java.util.ArrayList;
import java.util.List;

public class FlsaWeek {
	private List<FlsaDay> flsaDays = new ArrayList<FlsaDay>();

	public List<FlsaDay> getFlsaDays() {
		return flsaDays;
	}

	public void setFlsaDays(List<FlsaDay> flsaDays) {
		this.flsaDays = flsaDays;
	}
	
	public boolean isFullWeek(){
		return flsaDays.size() == 7 ? true : false;
	}
}
