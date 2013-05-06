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
package org.kuali.kpme.tklm.time.rules.lunch.sys.service;

import java.sql.Timestamp;

import org.kuali.kpme.core.cache.CacheUtils;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.rules.lunch.sys.SystemLunchRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class SystemLunchRuleMaintainableImpl extends KualiMaintainableImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void saveBusinessObject() {
		SystemLunchRule sysLunchRule = (SystemLunchRule)this.getBusinessObject();
		if(sysLunchRule.getTkSystemLunchRuleId()!=null && sysLunchRule.isActive()){
			SystemLunchRule oldSystemLunchRule = TkServiceLocator.getSystemLunchRuleService().getSystemLunchRule(sysLunchRule.getTkSystemLunchRuleId());
			if(sysLunchRule.getEffectiveDate().equals(oldSystemLunchRule.getEffectiveDate())){
				sysLunchRule.setTimestamp(null);
			} else{
				if(oldSystemLunchRule!=null){
					oldSystemLunchRule.setActive(false);
					//NOTE this is done to prevent the timestamp of the inactive one to be greater than the 
					oldSystemLunchRule.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(TKUtils.getCurrentTimestamp()));
					oldSystemLunchRule.setEffectiveDate(sysLunchRule.getEffectiveDate());
					KRADServiceLocator.getBusinessObjectService().save(oldSystemLunchRule);
				}
				sysLunchRule.setTimestamp(TKUtils.getCurrentTimestamp());
				sysLunchRule.setTkSystemLunchRuleId(null);
			}
		}
		
		KRADServiceLocator.getBusinessObjectService().save(sysLunchRule);
        CacheUtils.flushCache(SystemLunchRule.CACHE_NAME);
	}
}
