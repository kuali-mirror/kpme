package org.kuali.hr.time.earngroup.validation;

import java.util.HashSet;
import java.util.Set;

import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.earngroup.EarnGroupDefinition;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

public class EarnGroupValidation  extends MaintenanceDocumentRuleBase{
	
	@Override
	protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {
		EarnGroup earnGroup = (EarnGroup)this.getNewBo();
		Set<String> earnCodes = new HashSet<String>();
		int index = 0;
		for(EarnGroupDefinition earnGroupDef : earnGroup.getEarnGroups()){
			if(earnCodes.contains(earnGroupDef.getEarnCode())){
				this.putFieldError("earnGroups["+index+"].earnCode", "earngroup.duplicate.earncode",earnGroupDef.getEarnCode());
						
			} 
			earnCodes.add(earnGroupDef.getEarnCode());
			index++;
		}
		return true;
	}
}
