package org.kuali.hr.lm.timeoff;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.location.Location;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class SystemScheduledTimeOff extends HrBusinessObject {

	private static final long serialVersionUID = 1L;
	private String lmSystemScheduledTimeOffId;
	private String leavePlan;
	private String accrualCategory;
	private String leaveCode;
	private Date accruedDate;
	private Date scheduledTimeOffDate;
	private String location;
	private String descr;
	private Long amountofTime;
	private String unusedTime;
	private BigDecimal transferConversionFactor;
	private String transfertoLeaveCode;
	private Date expirationDate;
	private String premiumHoliday;
	private Boolean history;

	private LeavePlan leavePlanObj;
	private AccrualCategory accrualCategoryObj;
	private LeaveCode leaveCodeObj;
	private Location locationObj;
	
	public String getLmSystemScheduledTimeOffId() {
		return lmSystemScheduledTimeOffId;
	}

	public void setLmSystemScheduledTimeOffId(String lmSystemScheduledTimeOffId) {
		this.lmSystemScheduledTimeOffId = lmSystemScheduledTimeOffId;
	}

	public String getLeavePlan() {
		if (this.leaveCodeObj == null && 
				(!StringUtils.isEmpty(this.leaveCode) && this.effectiveDate != null)) {		
			leaveCodeObj =  TkServiceLocator.getLeaveCodeService().getLeaveCode(leaveCode, this.effectiveDate);
		}
		leavePlan = (leaveCodeObj != null) ? leaveCodeObj.getLeavePlan() : "";
		return (leaveCodeObj != null) ? leaveCodeObj.getLeavePlan() : "";
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}

	public String getAccrualCategory() {
		if (this.leaveCodeObj == null && 
				(!StringUtils.isEmpty(this.leaveCode) && this.effectiveDate != null)) {		
			leaveCodeObj =  TkServiceLocator.getLeaveCodeService().getLeaveCode(leaveCode, this.effectiveDate);
		}
		accrualCategory = (leaveCodeObj != null) ? leaveCodeObj.getAccrualCategory() : "";
		return (leaveCodeObj != null) ? leaveCodeObj.getAccrualCategory() : "";
	}

	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
	}

	public String getLeaveCode() {
		return leaveCode;
	}

	public void setLeaveCode(String leaveCode) {
		this.leaveCode = leaveCode;
	}

	public Date getAccruedDate() {
		return accruedDate;
	}

	public void setAccruedDate(Date accruedDate) {
		this.accruedDate = accruedDate;
	}

	public Date getScheduledTimeOffDate() {
		return scheduledTimeOffDate;
	}

	public void setScheduledTimeOffDate(Date scheduledTimeOffDate) {
		this.scheduledTimeOffDate = scheduledTimeOffDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Long getAmountofTime() {
		return amountofTime;
	}

	public void setAmountofTime(Long amountofTime) {
		this.amountofTime = amountofTime;
	}

	public String getUnusedTime() {
		return unusedTime;
	}

	public void setUnusedTime(String unusedTime) {
		this.unusedTime = unusedTime;
	}

	public BigDecimal getTransferConversionFactor() {
		return transferConversionFactor;
	}

	public void setTransferConversionFactor(BigDecimal transferConversionFactor) {
		this.transferConversionFactor = transferConversionFactor;
	}

	public String getTransfertoLeaveCode() {
		return transfertoLeaveCode;
	}

	public void setTransfertoLeaveCode(String transfertoLeaveCode) {
		this.transfertoLeaveCode = transfertoLeaveCode;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getPremiumHoliday() {
		return premiumHoliday;
	}

	public void setPremiumHoliday(String premiumHoliday) {
		this.premiumHoliday = premiumHoliday;
	}

	public LeavePlan getLeavePlanObj() {
		return leavePlanObj;
	}

	public void setLeavePlanObj(LeavePlan leavePlanObj) {
		this.leavePlanObj = leavePlanObj;
	}

	public AccrualCategory getAccrualCategoryObj() {
		return accrualCategoryObj;
	}

	public void setAccrualCategoryObj(AccrualCategory accrualCategoryObj) {
		this.accrualCategoryObj = accrualCategoryObj;
	}

	public LeaveCode getLeaveCodeObj() {
		return leaveCodeObj;
	}

	public void setLeaveCodeObj(LeaveCode leaveCodeObj) {
		this.leaveCodeObj = leaveCodeObj;
	}

	public Location getLocationObj() {
		return locationObj;
	}

	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}

	@Override
	protected String getUniqueKey() {
		String lmSystemScheduledTimeOffKey = getLeaveCode() +"_"+ getLeavePlan() +"_"+ getAccrualCategory();
		return lmSystemScheduledTimeOffKey;
	}

	@Override
	public String getId() {
		return getLmSystemScheduledTimeOffId();
	}

	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean getHistory() {
		return history;
	}

	public void setHistory(Boolean history) {
		this.history = history;
	}

	@Override
	public void setId(String id) {
		setLmSystemScheduledTimeOffId(id);
	}

}
