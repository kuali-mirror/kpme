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
package org.kuali.kpme.core.lookup;

import java.util.List;

import org.kuali.rice.krad.datadictionary.AttributeDefinition;

public class WildcardableAttributeDefinition extends AttributeDefinition {

	private static final long serialVersionUID = 284545134942635573L;
	
    private boolean containsWildcardData;
	private List<String> allowewdWildcardStrings;
	
	
	public boolean getContainsWildcardData() {
		return containsWildcardData;
	}
	public void setContainsWildcardData(boolean containsWildcardData) {
		this.containsWildcardData = containsWildcardData;
	}
	
	
	public List<String> getAllowedWildcardStrings() {
		return allowewdWildcardStrings;
	}
	public void setAllowedWildcardStrings(List<String> allowewdWildcardStrings) {
		this.allowewdWildcardStrings = allowewdWildcardStrings;
	}

}
