package org.kuali.hr.lm.earncodesec.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.earncodesec.EarnCodeSecurity;
import org.kuali.hr.time.HrEffectiveDateActiveLookupableHelper;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

public class EarnCodeSecurityLookupableHelper extends HrEffectiveDateActiveLookupableHelper {

	private static final long serialVersionUID = 696878785547265569L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = new ArrayList<HtmlData>();
		
		List<HtmlData> defaultCustomActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		
		EarnCodeSecurity earnCodeSecurity = (EarnCodeSecurity) businessObject;
		String hrEarnCodeSecurityId = earnCodeSecurity.getHrEarnCodeSecurityId();
		String location = earnCodeSecurity.getLocation();
		String department = earnCodeSecurity.getDept();
		
		boolean systemAdmin = TKContext.getUser().isSystemAdmin();
		boolean locationAdmin = TKContext.getUser().getLocationAdminAreas().contains(location);
		boolean departmentAdmin = TKContext.getUser().getDepartmentAdminAreas().contains(department);
		
		for (HtmlData defaultCustomActionUrl : defaultCustomActionUrls){
			if (StringUtils.equals(defaultCustomActionUrl.getMethodToCall(), "edit")) {
				if (systemAdmin || locationAdmin || departmentAdmin) {
					customActionUrls.add(defaultCustomActionUrl);
				}
			} else {
				customActionUrls.add(defaultCustomActionUrl);
			}
		}
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("hrEarnCodeSecurityId", hrEarnCodeSecurityId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}

	@Override
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
        String salGroup = fieldValues.get("hrSalGroup");
        String dept = fieldValues.get("dept");
        String earnCode = fieldValues.get("earnCode");
        String location = fieldValues.get("location");
        String fromEffdt = fieldValues.get("rangeLowerBoundKeyPrefix_effectiveDate");
        String toEffdt = StringUtils.isNotBlank(fieldValues.get("effectiveDate")) ? fieldValues.get("effectiveDate").replace("<=", "") : "";
        String active = fieldValues.get("active");
        String showHist = fieldValues.get("history");
        System.out.println("field value map is >>> "+fieldValues);
        System.out.println("actual list is  "+TkServiceLocator.getEarnCodeSecurityService().searchEarnCodeSecurities(dept, salGroup, earnCode, location, 
				TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), active, showHist));
		return TkServiceLocator.getEarnCodeSecurityService().searchEarnCodeSecurities(dept, salGroup, earnCode, location, 
								TKUtils.formatDateString(fromEffdt), TKUtils.formatDateString(toEffdt), active, showHist);
	}

}
