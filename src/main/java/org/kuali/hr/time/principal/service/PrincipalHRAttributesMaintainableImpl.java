package org.kuali.hr.time.principal.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.cache.CacheUtils;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class PrincipalHRAttributesMaintainableImpl extends KualiMaintainableImpl {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if (fieldValues.containsKey("principalId")
				&& StringUtils.isNotEmpty(fieldValues.get("principalId"))) {
			Person p = KimApiServiceLocator.getPersonService().getPerson(
					fieldValues.get("principalId"));
			if (p != null) {
				fieldValues.put("name", p.getName());
			}else{
				fieldValues.put("name", "");
			}
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}

	@Override
	public void saveBusinessObject() {
		PrincipalHRAttributes principalHRAttr = (PrincipalHRAttributes) this.getBusinessObject();
		principalHRAttr.setTimestamp(null);
		KRADServiceLocator.getBusinessObjectService().save(principalHRAttr);
        CacheUtils.flushCache(PrincipalHRAttributes.CACHE_NAME);
	}
	
	
}
