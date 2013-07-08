package org.kuali.kpme.pm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.kuali.kpme.core.web.KPMEAction;
import org.kuali.rice.core.api.config.property.ConfigContext;

public class ProcessMaintAction extends KPMEAction {
		
	public ActionForward processMaintDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ProcessMaintForm aDocForm = (ProcessMaintForm) form;
    	String category = aDocForm.getCategory();
    	String reason = aDocForm.getReason();
    	String positionId = aDocForm.getPositionId();
    	
    	String path = ConfigContext.getCurrentContextConfig().getProperty("application.url"); 
    	String string1 = path + "/portal.do?channelTitle=Position&channelUrl=" + path + "/kpme/positionMaintenance?";
    	String idString = "hrPositionId=" + positionId + "&category=" + category + "&reason=" + reason;
    	String string2 = "&viewTypeName=MAINTENANCE&returnLocation=" + path + "/portal.do&methodToCall=maintenanceEdit&dataObjectClassName=org.kuali.kpme.pm.position.Position";
    	
    	ActionRedirect redirect = new ActionRedirect();
        redirect.setPath(string1 + idString + string2);
		return redirect;
    }

	
}
