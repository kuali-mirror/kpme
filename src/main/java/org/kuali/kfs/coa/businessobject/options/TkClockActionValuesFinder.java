package org.kuali.kfs.coa.businessobject.options;

import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

public class TkClockActionValuesFinder extends KeyValuesBase {

	@Override
	public List getKeyValues() {
		List<KeyLabelPair> keyLabels = new LinkedList<KeyLabelPair>();
		keyLabels.add(new KeyLabelPair(TkConstants.CLOCK_IN, "Clock In"));
		keyLabels.add(new KeyLabelPair(TkConstants.CLOCK_OUT, "Clock Out"));
		keyLabels.add(new KeyLabelPair(TkConstants.LUNCH_IN, "Take Lunch"));
		keyLabels.add(new KeyLabelPair(TkConstants.LUNCH_OUT, "Return From Lunch"));
		keyLabels.add(new KeyLabelPair(TkConstants.BREAK_IN, "Take Break"));
		keyLabels.add(new KeyLabelPair(TkConstants.BREAK_OUT, "Return From Break"));
		
  		return keyLabels;
	}

}
