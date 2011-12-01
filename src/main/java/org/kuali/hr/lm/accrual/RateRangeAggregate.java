package org.kuali.hr.lm.accrual;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RateRangeAggregate {
	private List<RateRange> rateRanges = new ArrayList<RateRange>();
	private RateRange currentRate;
	private boolean rateRangeChanged;
	
	public RateRange getRate(Date date) {		
		rateRangeChanged = false;
		if (currentRate == null) {
			currentRate = rateRanges.get(0);
		}
		
		if (currentRate.getRange().contains(date.getTime())) {
			for (RateRange rateRange : rateRanges) {
				if (rateRange.getRange().contains(date.getTime())) {
					rateRangeChanged = rateRange.getAccrualRatePercentageModifier() != currentRate.getAccrualRatePercentageModifier();
					currentRate = rateRange;
					break;
				}
			}
		}
		return currentRate;
	}
	
	public List<RateRange> getRateRanges() {
		return rateRanges;
	}
	public void setRateRanges(List<RateRange> rateRanges) {
		this.rateRanges = rateRanges;
	}
	public RateRange getCurrentRate() {
		return currentRate;
	}
	public void setCurrentRate(RateRange currentRate) {
		this.currentRate = currentRate;
	}
	public boolean isRateRangeChanged() {
		return rateRangeChanged;
	}
	public void setRateRangeChanged(boolean rateRangeChanged) {
		this.rateRangeChanged = rateRangeChanged;
	}

}
