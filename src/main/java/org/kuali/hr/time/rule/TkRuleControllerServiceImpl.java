package org.kuali.hr.time.rule;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

public class TkRuleControllerServiceImpl implements TkRuleControllerService {
	
	public List<TimeBlock> applyRules(String action, List<TimeBlock> timeBlocks, PayCalendarDates payEntry){
		//foreach action run the rules that apply
		if(StringUtils.equals(action, TkConstants.ACTIONS.CLOCK_IN)){
			
		} else if(StringUtils.equals(action, TkConstants.ACTIONS.ADD_TIME_BLOCK)){
			TkTimeBlockAggregate timeBlockAggregate = new TkTimeBlockAggregate(timeBlocks, payEntry);
			
		}

		
		return timeBlocks;
	}


}
