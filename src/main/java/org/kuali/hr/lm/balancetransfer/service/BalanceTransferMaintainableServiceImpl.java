/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.lm.balancetransfer.service;

import java.util.Map;

import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

public class BalanceTransferMaintainableServiceImpl extends
		HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -789218061798169466L;

	@Override
	public void saveBusinessObject() {
		//CacheUtils.flushCache(BalanceTransfer.CACHE_NAME);
		super.saveBusinessObject();
/*		@SuppressWarnings("deprecation")
		PersistableBusinessObject pbo = this.getBusinessObject();
		if(pbo instanceof BalanceTransfer) {
			BalanceTransfer balanceTransfer = (BalanceTransfer) pbo;
			if(!isOldBusinessObjectInDocument()) {
				balanceTransfer = TkServiceLocator.getBalanceTransferService().transfer(balanceTransfer);
				setBusinessObject(balanceTransfer);
			}
		}
*/
	}
	
	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getBalanceTransferService().getBalanceTransferById(id);
	}

	/**
	 * Used to 
	 */
	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}

}
