package org.kuali.hr.time.rule;

import java.util.List;

import org.kuali.hr.time.timeblock.TimeBlock;

public interface TkRuleControllerService {
	public List<TimeBlock> applyRules(String action, List<TimeBlock> timeBlocks);
}
