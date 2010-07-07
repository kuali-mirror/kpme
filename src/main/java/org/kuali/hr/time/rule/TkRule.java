package org.kuali.hr.time.rule;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public abstract class TkRule extends PersistableBusinessObjectBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2167884665622901547L;

	public abstract boolean isValid(TkRuleContext tkRuleContext);
}
