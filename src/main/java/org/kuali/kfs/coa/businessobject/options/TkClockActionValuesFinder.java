package org.kuali.kfs.coa.businessobject.options;

import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TkClockActionValuesFinder extends KeyValuesBase {

	@Override
	public List getKeyValues() {
		List<KeyLabelPair> keyLabels = new LinkedList<KeyLabelPair>();

        TKUser user = TKContext.getUser();

        if (user != null) {
            ClockLog lastClock = TkServiceLocator.getClockLogService().getLastClockLog(user.getTargetPrincipalId());

            Set<String> validEntries = lastClock != null ?
                    TkConstants.CLOCK_ACTION_TRANSITION_MAP.get(lastClock.getClockAction()) :
                    TkConstants.CLOCK_ACTION_TRANSITION_MAP.get(TkConstants.CLOCK_OUT); // Force CLOCK_IN as next valid action.

            for (String entry : validEntries) {
                keyLabels.add(new KeyLabelPair(entry, TkConstants.CLOCK_ACTION_STRINGS.get(entry)));
            }
        } else {
            // Default to adding all options.
            for (Map.Entry entry : TkConstants.CLOCK_ACTION_STRINGS.entrySet()) {
                keyLabels.add(new KeyLabelPair(entry.getKey(), (String)entry.getValue()));
            }
        }

  		return keyLabels;
	}

}