package org.kuali.hr.time.workarea.web;

import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.authorization.BusinessObjectRestrictions;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.util.KNSConstants;
import org.springframework.web.util.UrlPathHelper;

public class WorkAreaLookupableHelper extends KualiLookupableHelperServiceImpl {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
//    private UrlPathHelper urlPathHelper = new UrlPathHelper();
//    
//    @SuppressWarnings("unchecked")
//    @Override
//    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
//	List<HtmlData> urls = new LinkedList<HtmlData>();
//
//	String url =  urlPathHelper.getOriginatingContextPath(TKContext.getHttpServletRequest());
//	
//	AnchorHtmlData anchorHtmlData = new AnchorHtmlData(url + "/WorkAreaMaintenance.do?methodToCall=docHandler&command=initiate&workAreaId=" + ((WorkArea)businessObject).getTkWorkAreaId(), KNSConstants.DOC_HANDLER_METHOD, KNSConstants.MAINTENANCE_EDIT_METHOD_TO_CALL);
//	urls.add(anchorHtmlData);
//
//	return urls;
//    }
//
//	@Override
//	public boolean allowsMaintenanceNewOrCopyAction() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	protected String getActionUrlHref(BusinessObject businessObject,
//			String methodToCall, List pkNames) {
//		// TODO Auto-generated method stub
//		return super.getActionUrlHref(businessObject, methodToCall, pkNames);
//	}
//
//	@Override
//	protected AnchorHtmlData getUrlData(BusinessObject businessObject,
//			String methodToCall, List pkNames) {
//		// TODO Auto-generated method stub
//		return super.getUrlData(businessObject, methodToCall, pkNames);
//	}
//
//	@Override
//	protected AnchorHtmlData getUrlData(BusinessObject businessObject,
//			String methodToCall, String displayText, List pkNames) {
//		// TODO Auto-generated method stub
//		return super.getUrlData(businessObject, methodToCall, displayText, pkNames);
//	}
//
//	@Override
//	protected String getActionUrlTitleText(BusinessObject businessObject,
//			String displayText, List pkNames,
//			BusinessObjectRestrictions businessObjectRestrictions) {
//		// TODO Auto-generated method stub
//		return super.getActionUrlTitleText(businessObject, displayText, pkNames,
//				businessObjectRestrictions);
//	}
//    
//    

}
