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
