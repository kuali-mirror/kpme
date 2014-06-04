package org.kuali.kpme.edo.reports.service;

import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;

import java.util.*;

public class EdoPromotionAndTenureReportLookupableHelper extends KualiLookupableHelperServiceImpl {

	private EdoPromotionAndTenureReportViewService promotionAndTenureReportService;
	
    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
    	return super.getSearchResults(fieldValues);
    	
    	//update the search to use the formatted reports
    	//return getPromotionAndTenureReportService().getFormattedPromotionAndTenureReports(fieldValues);
    }
    
    public void setPromotionAndTenureReportService(EdoPromotionAndTenureReportViewService promotionAndTenureReportService) {
    	this.promotionAndTenureReportService = promotionAndTenureReportService;
    }
    
    public EdoPromotionAndTenureReportViewService getPromotionAndTenureReportService() { 
    	return promotionAndTenureReportService;
    }    
}
