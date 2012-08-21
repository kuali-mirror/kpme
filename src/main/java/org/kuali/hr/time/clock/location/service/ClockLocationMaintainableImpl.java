package org.kuali.hr.time.clock.location.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.cache.CacheUtils;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.clock.location.ClockLocationRuleIpAddress;
import org.kuali.hr.time.clock.location.validation.ClockLocationRuleRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

/**
 * 
 * Used to modify behavior of Clock Location Maintenance page
 * 
 */
public class ClockLocationMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * Used to swap out wildcard values for numeric types
	 */
	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if (fieldValues.containsKey("workArea")
				&& StringUtils.equals(fieldValues.get("workArea"), "%")) {
			fieldValues.put("workArea", "-1");
		}
		if (fieldValues.containsKey("jobNumber")
				&& StringUtils.equals(fieldValues.get("jobNumber"), "%")) {
			fieldValues.put("jobNumber", "-1");
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}

	@Override
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		ClockLocationRule clockLocationRule = (ClockLocationRule) this
				.getBusinessObject();
		List<ClockLocationRule> clockLocationRuleList = TkServiceLocator
				.getClockLocationRuleService()
				.getNewerVersionClockLocationRule(clockLocationRule.getDept(),
						clockLocationRule.getWorkArea(),
						clockLocationRule.getPrincipalId(),
						clockLocationRule.getJobNumber(),
						clockLocationRule.getEffectiveDate());
		if (clockLocationRuleList.size() > 0) {
			GlobalVariables.getMessageMap().putWarningForSectionId(
					"Clock Location Rule Maintenance",
					"clocklocationrule.newer.exists", null);
		}
		super.processAfterEdit(document, parameters);
	}

	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getClockLocationRuleService().getClockLocationRule(id);
	}
	
	@Override
    public void addNewLineToCollection( String collectionName ) {
		// need to validate each new ipAddress
        if (collectionName.equals("ipAddresses")) {
        	ClockLocationRuleIpAddress anIP = (ClockLocationRuleIpAddress)newCollectionLines.get(collectionName );
    		if(!ClockLocationRuleRule.validateIpAddress(anIP.getIpAddress())) {
    			GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE +"ipAddresses", 
        				"ipaddress.invalid.format",anIP.getIpAddress());
        		return;
    		}
        }
		super.addNewLineToCollection(collectionName);
    }
		
	@Override
	public void saveBusinessObject() {
		ClockLocationRule clr = (ClockLocationRule) this.getBusinessObject();
		List<ClockLocationRuleIpAddress> ips = clr.getIpAddresses();
		super.saveBusinessObject();
		if(!ips.isEmpty()) {
			for(ClockLocationRuleIpAddress ipAddress : ips) {
				ipAddress.setTkClockLocationRuleId(clr.getTkClockLocationRuleId());
				KRADServiceLocator.getBusinessObjectService().save(ipAddress);
			}
            CacheUtils.flushCache(ClockLocationRule.CACHE_NAME);
		}
		
	}
}
