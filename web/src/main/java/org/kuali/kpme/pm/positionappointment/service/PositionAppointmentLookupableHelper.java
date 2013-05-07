package org.kuali.kpme.pm.positionappointment.service;

import org.kuali.kpme.core.lookup.KPMELookupableHelper;
import org.kuali.kpme.pm.positionappointment.PositionAppointment;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

import java.util.List;
import java.util.Properties;


@SuppressWarnings("deprecation")
public class PositionAppointmentLookupableHelper extends KPMELookupableHelper {

	private static final long serialVersionUID = 4826886027602440306L;
	
	@Override
	@SuppressWarnings("rawtypes")
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);		
		
		PositionAppointment pa = (PositionAppointment) businessObject;
		String pmPositionAppointmentId = pa.getPmPositionAppointmentId();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("pmPositionAppointmentId", pmPositionAppointmentId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}
	/*
    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues){
        String pmPositionAppointmentId = fieldValues.get("pmPositionAppointmentId");
        String description = fieldValues.get("description");
        String fromEffdt = TKUtils.getFromDateString(fieldValues.get("effectiveDate"));
        String toEffdt = TKUtils.getToDateString(fieldValues.get("effectiveDate"));
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");
        String positionAppointment = fieldValues.get("positionAppointment");
        String institution = fieldValues.get("institution");
        String campus = fieldValues.get("campus");
        LocalDate asOfDate = (fieldValues.get("effectiveDate"));
        

        if (StringUtils.equals(pmPositionAppointmentId, "%")) {
        	pmPositionAppointmentId = "";
        }
        
        return PmServiceLocator.getPositionAppointmentService().getPositionAppointmentList(positionAppointment, institution, campus, asOfDate);
        			//TKUtils.formatDateString(toEffdt), active, showHist);
    }
    */
}
