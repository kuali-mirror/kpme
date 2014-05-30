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
package org.kuali.kpme.core.bo.validation;

import java.util.ListIterator;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.HrKeyedSetBusinessObject;
import org.kuali.kpme.core.bo.derived.HrBusinessObjectKey;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public abstract class HrKeyedSetBusinessObjectValidation<O extends HrKeyedSetBusinessObject<O, K>, K extends HrBusinessObjectKey<O, K>> extends MaintenanceDocumentRuleBase {
	
	private static final String EFFECTIVE_KEY_LIST_PROPERTY_PATH = "dataObject.effectiveKeyList[%d].groupKeyCode";
	private static final String EMPTY_KEY_LIST_ERROR_KEY = "keyedSet.keyList.empty";
	private static final String DUPLICATE_GROUP_KEY_CODE_ERROR_KEY = "keyedSet.keyList.duplicates";
	private static final String ERROR_EXISTENCE_KEY = "error.existence";
	private static final String EFFECTIVE_KEY_LIST = "dataObject.effectiveKeyList";
	
	@SuppressWarnings("unchecked")
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean retVal = false;
		LOG.debug("entering common validation logic for abstract HrKeyedSetBusinessObject");
		if((this.getNewDataObject() != null) && (this.getNewDataObject() instanceof HrKeyedSetBusinessObject)) {
			retVal = true;
			HrKeyedSetBusinessObject<O,K> newKeyOwner = (HrKeyedSetBusinessObject<O,K>) this.getNewDataObject();
			retVal &= this.checkGroupKeyListNotEmpty(newKeyOwner);
			retVal &= this.checkThatAllGroupKeysExist(newKeyOwner);
			retVal &= this.checkNoDuplicateGroupKeyCodes(newKeyOwner);
		}		
		return retVal;
	}


	private boolean checkNoDuplicateGroupKeyCodes(HrKeyedSetBusinessObject<O, K> keyOwnerObj) {
		if(CollectionUtils.isNotEmpty(keyOwnerObj.getEffectiveKeyList()) && (keyOwnerObj.getEffectiveKeyList().size() != keyOwnerObj.getEffectiveKeySet().size())) {
			this.putFieldError(EFFECTIVE_KEY_LIST, DUPLICATE_GROUP_KEY_CODE_ERROR_KEY);
			return false;
		}
		else {
			return true;
		}
	}


	private boolean checkThatAllGroupKeysExist(HrKeyedSetBusinessObject<O, K> keyOwnerObj) {
		boolean retVal = true;
		LocalDate asOfDate = LocalDate.fromDateFields(keyOwnerObj.getRelativeEffectiveDate());		
		if(CollectionUtils.isNotEmpty(keyOwnerObj.getEffectiveKeyList())) {
	    	for (ListIterator<K> iterator = keyOwnerObj.getEffectiveKeyList().listIterator(); iterator.hasNext(); ) {
				int index = iterator.nextIndex();
				K key = iterator.next();
				if(!ValidationUtils.validateGroupKey(key.getGroupKeyCode(), asOfDate)) {
					// put error message
					this.putFieldError( String.format(EFFECTIVE_KEY_LIST_PROPERTY_PATH, index), ERROR_EXISTENCE_KEY, "Group Key '"+ key.getGroupKeyCode() + "'");
					retVal = false;
				}
			}
    	}		
		return retVal;
	}
	


	private boolean checkGroupKeyListNotEmpty(HrKeyedSetBusinessObject<?,?> keyOwnerObj) {
		if(CollectionUtils.isEmpty(keyOwnerObj.getEffectiveKeyList())) {
			this.putFieldError(EFFECTIVE_KEY_LIST, EMPTY_KEY_LIST_ERROR_KEY);
			return false;
		}
		else {
			return true;
		}
	}

}