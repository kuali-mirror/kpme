package org.kuali.hr.time.earngroup.validation;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.codehaus.plexus.util.StringUtils;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.earngroup.EarnGroupDefinition;
import org.kuali.hr.time.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class EarnGroupValidation  extends MaintenanceDocumentRuleBase{

	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		EarnGroup earnGroup = (EarnGroup)this.getNewBo();
		Set<String> earnCodes = new HashSet<String>();
		int index = 0;
		for(EarnGroupDefinition earnGroupDef : earnGroup.getEarnGroups()){
			if(earnCodes.contains(earnGroupDef.getEarnCode())){
				this.putFieldError("earnGroups["+index+"].earnCode", "earngroup.duplicate.earncode",earnGroupDef.getEarnCode());

			}
			if(earnGroup.getShowSummary()) {
				validateEarnCode(earnGroupDef.getEarnCode().toUpperCase(), index, earnGroup);
			}
			if (!ValidationUtils.validateEarnCode(earnGroupDef.getEarnCode(), earnGroup.getEffectiveDate())) {
				this.putFieldError("earnGroups["+index+"].earnCode", "error.existence", "Earncode '" + earnGroupDef.getEarnCode()+ "'");
			}
			earnCodes.add(earnGroupDef.getEarnCode());
			index++;
		}
		if(ValidationUtils.newerVersionExists(EarnGroup.class, "earnGroup", earnGroup.getEarnGroup(), earnGroup.getEffectiveDate())) {
			this.putFieldError("effectiveDate", "earngroup.effectiveDate.newr.exists");
			return false;
		}
		return true;
	}

    protected void validateEarnCode(String earnCode, int index, EarnGroup editedEarnGroup) {
    	BusinessObjectService businessObjectService = KNSServiceLocator.getBusinessObjectService();
    	Map<String,Object> criteria = new HashMap<String,Object>();
		criteria.put("showSummary", "Y");
		criteria.put("active", "Y");
    	Collection aCol = businessObjectService.findMatching(EarnGroup.class, criteria);
		Iterator<EarnGroup> itr = aCol.iterator();
		while (itr.hasNext()) {
			EarnGroup earnGroup = itr.next();
			if(!earnGroup.getTkEarnGroupId().equals(editedEarnGroup.getTkEarnGroupId())) {
				criteria = new HashMap<String,Object>();
				criteria.put("tkEarnGroupId", earnGroup.getTkEarnGroupId());

				Collection earnGroupDefs = businessObjectService.findMatching(EarnGroupDefinition.class, criteria);
				Iterator<EarnGroupDefinition> iterator = earnGroupDefs.iterator();
				while (iterator.hasNext()) {
					EarnGroupDefinition def = iterator.next();
					if(StringUtils.equals(earnCode, def.getEarnCode().toUpperCase())) {
						String[] parameters = new String[2];
						parameters[0] = earnCode;
						parameters[1] = earnGroup.getDescr();
						this.putFieldError("earnGroups["+index+"].earnCode", "earngroup.earncode.already.used", parameters);
					}
				}
			}
		}
    }

 }
