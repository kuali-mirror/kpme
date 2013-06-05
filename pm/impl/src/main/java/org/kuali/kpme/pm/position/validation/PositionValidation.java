package org.kuali.kpme.pm.position.validation;

import java.math.BigDecimal;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.classification.Classification;
import org.kuali.kpme.pm.classification.duty.ClassificationDuty;
import org.kuali.kpme.pm.position.Position;
import org.kuali.kpme.pm.position.PositionDuty;
import org.kuali.kpme.pm.position.funding.PositionFunding;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;

public class PositionValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		boolean valid = false;
		LOG.debug("entering custom validation for Position");
		Position aPosition = (Position) this.getNewDataObject();
		
		if (aPosition != null) {
			valid = true;
			valid &= this.validateDutyListPercentage(aPosition);
			valid &= this.validateFundingList(aPosition);
		}
		return valid;
	}
	
	private boolean validateDutyListPercentage(Position aPosition) {
		if(CollectionUtils.isNotEmpty(aPosition.getDutyList())) {
			BigDecimal sum = BigDecimal.ZERO;
			for(PositionDuty aDuty : aPosition.getDutyList()) {
				if(aDuty != null && aDuty.getPercentage() != null) {
					sum = sum.add(aDuty.getPercentage());
				}
			}
			if(sum.compareTo(new BigDecimal(100)) > 0) {
				String[] parameters = new String[1];
				parameters[0] = sum.toString();
				this.putFieldError("dataObject.dutyList", "duty.percentage.exceedsMaximum", parameters);
				return false;
			}
		}		
		return true;
	}
	
	private boolean validateFundingList(Position aPosition) {
		if(CollectionUtils.isNotEmpty(aPosition.getFundingList())) {
			for(PositionFunding aPf : aPosition.getFundingList()) {
				boolean results = this.validateAddFundingLine(aPf, aPosition);
				if(!results)
					return false;
			}
			
		}
		return true;
	}
	
	protected boolean validateAddFundingLine(PositionFunding pf, Position aPosition) {
    	if(pf.getEffectiveDate() != null && aPosition.getEffectiveDate() != null) {
    		if(pf.getEffectiveDate().compareTo(aPosition.getEffectiveDate()) < 0) {
    			String[] parameters = new String[2];
    			parameters[0] = pf.getEffectiveDate().toString();
    			parameters[1] = aPosition.getEffectiveDate().toString();
    			this.putFieldError("dataObject.fundingList","error.funding.effdt.invalid", parameters);
   			 	return false;
    		}
    	}
    	if(StringUtils.isNotEmpty(pf.getAccount())) {
    		boolean results = ValidationUtils.validateAccount(pf.getAccount());
    		if(!results) {
    			this.putFieldError("dataObject.fundingList","error.funding.account.notExist", pf.getAccount());
    			return results;
    		}
    	}
    	if(StringUtils.isNotEmpty(pf.getSubAccount())) {
    		boolean results = ValidationUtils.validateSubAccount(pf.getSubAccount());
    		if(!results) {
    			this.putFieldError("dataObject.fundingList","error.funding.subAccount.notExist", pf.getSubAccount());
	   			 return results;
    		}
    	}
    	if(StringUtils.isNotEmpty(pf.getObjectCode())) {
    		boolean results = ValidationUtils.validateObjectCode(pf.getObjectCode());
    		if(!results) {
    			this.putFieldError("dataObject.fundingList","error.funding.objectCode.notExist", pf.getObjectCode());
      			 return results;
    		}
    	}
    	if(StringUtils.isNotEmpty(pf.getSubObjectCode())) {
    		boolean results = ValidationUtils.validateSubObjectCode(pf.getSubObjectCode());
    		if(!results) {
    			this.putFieldError("dataObject.fundingList","error.funding.subObjectCode.notExist", pf.getSubObjectCode());
      			 return results;
    		}
    	}
    	return true;
    
	}
	

//	@Override
//	public boolean processCustomAddCollectionLineBusinessRules(
//			MaintenanceDocument document, String collectionName,
//			PersistableBusinessObject line) {
//		boolean isValid = true;
//       
//        if (document.getNewMaintainableObject().getDataObject() instanceof Position) {
//        	Position aPosition = (Position) document.getNewMaintainableObject().getDataObject();
//        	// Funding line validation
//	        if (line instanceof PositionFunding) {
//	        	PositionFunding pf = (PositionFunding) line;
//	        	boolean results = this.validateAddFundingLine(pf, aPosition);
//	        	if(!results) {
//	        		GlobalVariables.getMessageMap().addToErrorPath("document");
//	        		return false;
//	        	}
//	        }
//        }
//
//        return isValid;
//	}
	
}
