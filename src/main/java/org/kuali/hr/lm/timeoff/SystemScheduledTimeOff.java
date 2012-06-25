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
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class SystemScheduledTimeOff extends HrBusinessObject {

	private static final long serialVersionUID = 1L;
	private String lmSystemScheduledTimeOffId;
	private String leavePlan;
	private String accrualCategory;
	private String earnCode;
	private Date accruedDate;
	private Date scheduledTimeOffDate;
	private String location;
	private String descr;
	private Long amountofTime;
	private String unusedTime;
	private BigDecimal transferConversionFactor;
	private String transfertoEarnCode;
	private Date expirationDate;
	private String premiumHoliday;
	private Boolean history;

	private LeavePlan leavePlanObj;
	private AccrualCategory accrualCategoryObj;
	private EarnCode earnCodeObj;
	private Location locationObj;
	
	public String getLmSystemScheduledTimeOffId() {
		return lmSystemScheduledTimeOffId;
	}

	public void setLmSystemScheduledTimeOffId(String lmSystemScheduledTimeOffId) {
		this.lmSystemScheduledTimeOffId = lmSystemScheduledTimeOffId;
	}

	public String getLeavePlan() {
		if (this.earnCodeObj == null && 
				(!StringUtils.isEmpty(this.earnCode) && this.effectiveDate != null)) {		
			earnCodeObj =  TkServiceLocator.getEarnCodeService().getEarnCode(earnCode, this.effectiveDate);
		}
		leavePlan = (earnCodeObj != null) ? earnCodeObj.getLeavePlan() : "";
		return (earnCodeObj != null) ? earnCodeObj.getLeavePlan() : "";
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}

	public String getAccrualCategory() {
//		if (this.earnCodeObj == null && 
//				(!StringUtils.isEmpty(this.earnCode) && this.effectiveDate != null)) {		
//			earnCodeObj =  TkServiceLocator.getEarnCodeService().getEarnCode(earnCode, this.effectiveDate);
//		}
//		accrualCategory = (earnCodeObj != null) ? earnCodeObj.getAccrualCategory() : "";
//		return (earnCodeObj != null) ? earnCodeObj.getAccrualCategory() : "";
		return accrualCategory;
	}

	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
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

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public String getTransfertoEarnCode() {
		return transfertoEarnCode;
	}

	public void setTransfertoEarnCode(String transfertoEarnCode) {
		this.transfertoEarnCode = transfertoEarnCode;
	}

	public EarnCode getEarnCodeObj() {
		return earnCodeObj;
	}

	public void setEarnCodeObj(EarnCode earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}

	public Location getLocationObj() {
		return locationObj;
	}

	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}

	@Override
	protected String getUniqueKey() {
		String lmSystemScheduledTimeOffKey = getEarnCode() +"_"+ getLeavePlan() +"_"+ getAccrualCategory();
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
