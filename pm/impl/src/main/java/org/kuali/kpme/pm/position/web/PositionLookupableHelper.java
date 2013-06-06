package org.kuali.kpme.pm.position.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;

public class PositionLookupableHelper extends LookupableImpl {
	private static final long serialVersionUID = 1L;

	@Override
    protected List<? extends BusinessObject> getSearchResults(LookupForm form, Map<String, String> fieldValues, boolean unbounded) {
				
    	String positionNum = fieldValues.get("positionNumber");
        String description = fieldValues.get("description");
        String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");

        if (StringUtils.equals(positionNum, "%")) {
            positionNum = "";
        }
        
        return PmServiceLocator.getPositionService().getPositions(positionNum, description, TKUtils.formatDateString(fromEffdt),
                TKUtils.formatDateString(toEffdt), active, showHist);	

    }    

}
