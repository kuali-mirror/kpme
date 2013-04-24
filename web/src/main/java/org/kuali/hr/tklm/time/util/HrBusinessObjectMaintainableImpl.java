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
package org.kuali.hr.tklm.time.util;

import org.joda.time.LocalDate;
import org.kuali.hr.core.HrBusinessObject;
import org.kuali.hr.core.cache.CacheUtils;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.krad.service.KRADServiceLocator;

import java.sql.Timestamp;
import java.util.List;

public abstract class HrBusinessObjectMaintainableImpl extends KualiMaintainableImpl {
    protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(HrBusinessObjectMaintainableImpl.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void saveBusinessObject() {
		HrBusinessObject hrObj = (HrBusinessObject) this.getBusinessObject();
		if(hrObj.getId()!=null){
			HrBusinessObject oldHrObj = this.getObjectById(hrObj.getId());
			if(oldHrObj!= null){
				//if the effective dates are the same do not create a new row just inactivate the old one
				if(hrObj.getEffectiveDate().equals(oldHrObj.getEffectiveDate())){
					oldHrObj.setActive(false);
					oldHrObj.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(LocalDate.now().toDate().getTime()))); 
				} else{
					//if effective dates not the same add a new row that inactivates the old entry based on the new effective date
					oldHrObj.setTimestamp(TKUtils.subtractOneSecondFromTimestamp(new Timestamp(LocalDate.now().toDate().getTime())));
					oldHrObj.setEffectiveDate(hrObj.getEffectiveDate());
					oldHrObj.setActive(false);
					oldHrObj.setId(null);
				}
				KRADServiceLocator.getBusinessObjectService().save(oldHrObj);
			}
		}
		hrObj.setTimestamp(new Timestamp(System.currentTimeMillis()));
		hrObj.setId(null);
		
		customSaveLogic(hrObj);
		KRADServiceLocator.getBusinessObjectService().save(hrObj);

        //cache clearing?!?!
        try {
            List<String> cacheNames = (List<String>)hrObj.getClass().getDeclaredField("CACHE_FLUSH").get(hrObj);
            CacheUtils.flushCaches(cacheNames);
        } catch (NoSuchFieldException e) {
            try {
                String cacheName = (String)hrObj.getClass().getDeclaredField("CACHE_NAME").get(hrObj);
                CacheUtils.flushCache(cacheName);
            } catch (NoSuchFieldException ex) {
                // no cache name found
                LOG.warn("No cache name found for object: " + hrObj.getClass().getName());
            } catch (IllegalAccessException ex) {
                LOG.warn("No cache name found for object: " + hrObj.getClass().getName());
            }
            // no cache name found
            //LOG.warn("No cache name found for object: " + hrObj.getClass().getName());
        } catch (IllegalAccessException e) {
            LOG.warn("No caches found for object: " + hrObj.getClass().getName());
        }
    }
	
	public abstract HrBusinessObject getObjectById(String id);
	public void customSaveLogic(HrBusinessObject hrObj){};
}
