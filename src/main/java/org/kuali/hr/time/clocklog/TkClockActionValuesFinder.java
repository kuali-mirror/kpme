package org.kuali.hr.time.clocklog;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.web.struts.form.KualiForm;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;

public class TkClockActionValuesFinder extends KeyValuesBase {

	@Override
	public List getKeyValues() {
		List<KeyLabelPair> keyLabels = new LinkedList<KeyLabelPair>();
        // get the missed punch doc Id if this is an existing document
        String mpDocId = (String)TKContext.getHttpServletRequest().getParameter(TkConstants.DOCUMENT_ID_REQUEST_NAME);
        if(StringUtils.isBlank(mpDocId)) {
        	mpDocId = (String)TKContext.getHttpServletRequest().getAttribute(TkConstants.DOCUMENT_ID_REQUEST_NAME);
        	if(StringUtils.isBlank(mpDocId)){
            	KualiForm kualiForm = (KualiForm)TKContext.getHttpServletRequest().getAttribute("KualiForm");
            	if(kualiForm instanceof KualiTransactionalDocumentFormBase){
            		mpDocId = ((KualiTransactionalDocumentFormBase)kualiForm).getDocId();
            	}
            }
        	if(StringUtils.isBlank(mpDocId)){
        		 mpDocId = (String)TKContext.getHttpServletRequest().getParameter("docId");
            }
        }
        // if the user is working on an existing missed punch doc, only return available actions based on the initial clock action
        if(!StringUtils.isEmpty(mpDocId)) {
        	MissedPunchDocument mp = TkServiceLocator.getMissedPunchService().getMissedPunchByRouteHeader(mpDocId);
        	if(mp != null) {
        		String clockAction = mp.getClockAction();
        		if(!StringUtils.isEmpty(clockAction)) {
        			Set<String> availableActions = TkConstants.CLOCK_AVAILABLE_ACTION_MAP.get(clockAction);
        			for (String entry : availableActions) {
        				keyLabels.add(new KeyLabelPair(entry, TkConstants.CLOCK_ACTION_STRINGS.get(entry)));
        	        }
        		}
        	}
        } else {
        	 TKUser user = TKContext.getUser();
             if (user != null ) {
                 ClockLog lastClock = TkServiceLocator.getClockLogService().getLastClockLog(user.getTargetPrincipalId());
                 Set<String> validEntries = lastClock != null ?
                         TkConstants.CLOCK_ACTION_TRANSITION_MAP.get(lastClock.getClockAction()) :
                         TkConstants.CLOCK_ACTION_TRANSITION_MAP.get(TkConstants.CLOCK_OUT); // Force CLOCK_IN as next valid action.
                 for (String entry : validEntries) {
                     keyLabels.add(new KeyLabelPair(entry, TkConstants.CLOCK_ACTION_STRINGS.get(entry)));
                 }
             }  
        }
        
        if(keyLabels.isEmpty()) {		// default is returning all options
        	for (Map.Entry entry : TkConstants.CLOCK_ACTION_STRINGS.entrySet()) {
              keyLabels.add(new KeyLabelPair(entry.getKey(), (String)entry.getValue()));
          }
        }
  		return keyLabels;
	}

}