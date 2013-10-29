package org.kuali.kpme.core.api.bo.service;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;

public interface HrBusinessObjectService {
	
	public boolean doesNewerVersionExist(HrBusinessObjectContract bo);
	
}
