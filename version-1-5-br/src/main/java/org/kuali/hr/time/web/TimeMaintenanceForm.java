package org.kuali.hr.time.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.bo.impl.KimAttributes;
import org.kuali.rice.kim.util.KimConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.service.ModuleService;
import org.kuali.rice.kns.web.struts.form.KualiMaintenanceForm;

/**
 * This is set up in the kr/struts-config.xml, so that our maintenance docs
 * have some modified header information.
 */
public class TimeMaintenanceForm extends KualiMaintenanceForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * This was cut and pasted from the parent, with some modification.
	 */
	@Override
	protected String getPersonInquiryUrlLink(Person user, String linkBody) {
        StringBuffer urlBuffer = new StringBuffer();
                
        if(user != null && StringUtils.isNotEmpty(linkBody) ) {
        	ModuleService moduleService = KNSServiceLocator.getKualiModuleService().getResponsibleModuleService(Person.class);
        	Map<String, String[]> parameters = new HashMap<String, String[]>();
        	parameters.put(KimAttributes.PRINCIPAL_ID, new String[] { user.getPrincipalId() });
        	String inquiryUrl = moduleService.getExternalizableBusinessObjectInquiryUrl(Person.class, parameters);
            if(!StringUtils.equals(KimConstants.EntityTypes.SYSTEM, user.getEntityTypeCode())){
	            urlBuffer.append("<a href='");
	            urlBuffer.append(inquiryUrl);
	            urlBuffer.append("' ");
	            urlBuffer.append("target='_blank'");
	            urlBuffer.append("title='Person Inquiry'>");
	            urlBuffer.append(linkBody);
	            urlBuffer.append("</a>");
	            
	            // Added re: KPME-207 (https://jira.kuali.org/browse/KPME-207)
	            urlBuffer.append(" (");
	            urlBuffer.append(user.getName());
	            urlBuffer.append(")");
            } else{
            	urlBuffer.append(linkBody);
            }
        }
        
        return urlBuffer.toString();
	}

}
