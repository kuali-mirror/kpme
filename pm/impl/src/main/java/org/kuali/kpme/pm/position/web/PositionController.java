/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.pm.position.web;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/positionMaintenance")
public class PositionController extends MaintenanceDocumentController {
	
	    
    @Override
    @RequestMapping(params = "methodToCall=" + "setupMaintenance")
    protected void setupMaintenance(MaintenanceDocumentForm form, HttpServletRequest request, String maintenanceAction) {
    	super.setupMaintenance(form, request, maintenanceAction);
    	
    	if(KRADConstants.MAINTENANCE_EDIT_ACTION.equals(maintenanceAction)) {
    		 MaintenanceDocument document = form.getDocument();
			 PositionBo oldPosition = (PositionBo) document.getOldMaintainableObject().getDataObject();
			 PositionBo newPosition = (PositionBo) document.getNewMaintainableObject().getDataObject();
			 
    		 Map<String, String[]> aMap = request.getParameterMap();
    		 String[] aStringCol = aMap.get("category");
    		 if(aStringCol.length != 0) {
    			 String aString = aStringCol[0];
    			 if(StringUtils.isNotEmpty(aString)) {
	    			 oldPosition.setCategory(aString);
	    			 newPosition.setCategory(aString);
    			 }
    		 }
    		 
//    		 aStringCol = aMap.get("reason");
//    		 if(aStringCol.length != 0) {
//    			 String aString = aStringCol[0];
//    			 if(StringUtils.isNotEmpty(aString)) {
//	    			 oldPosition.setReason(aString);
//	    			 newPosition.setReason(aString);
//    			 }
//    		 }
    		 
    		 document.getOldMaintainableObject().setDataObject(oldPosition);
    		 document.getNewMaintainableObject().setDataObject(newPosition);
    	}
    }
  
}
