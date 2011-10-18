package org.kuali.hr.lm.accrual;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.HrBusinessObject;

public class LeaveAccrualCategory extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long lmAccrualCategoryId;
	private String leavePlan;
	private String accrualCategory;
	private String descr;
	private String accrualEarnInterval;
	private String proration;
	private String donation;
	private String showOnGrid;
	private String unitOfTime;
	private Boolean history;

	private LeavePlan leavePlanObj;
	private List<LeaveAccrualCategoryRule> accrualCategoryRules = new LinkedList<LeaveAccrualCategoryRule>();

	public LeavePlan getLeavePlanObj() {
		return leavePlanObj;
	}

	public void setLeavePlanObj(LeavePlan leavePlanObj) {
		this.leavePlanObj = leavePlanObj;
	}

	public List<LeaveAccrualCategoryRule> getAccrualCategoryRules() {
		return accrualCategoryRules;
	}

	public void setAccrualCategoryRules(
			List<LeaveAccrualCategoryRule> accrualCategoryRules) {
		this.accrualCategoryRules = accrualCategoryRules;
	}

	public Long getLmAccrualCategoryId() {
		return lmAccrualCategoryId;
	}

	public void setLmAccrualCategoryId(Long lmAccrualCategoryId) {
		this.lmAccrualCategoryId = lmAccrualCategoryId;
	}

	public String getLeavePlan() {
		return leavePlan;
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}

	public String getAccrualCategory() {
		return accrualCategory;
	}

	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getAccrualEarnInterval() {
		return accrualEarnInterval;
	}

	public void setAccrualEarnInterval(String accrualEarnInterval) {
		this.accrualEarnInterval = accrualEarnInterval;
	}

	public String getProration() {
		return proration;
	}

	public void setProration(String proration) {
		this.proration = proration;
	}

	public String getDonation() {
		return donation;
	}

	public void setDonation(String donation) {
		this.donation = donation;
	}

	public String getShowOnGrid() {
		return showOnGrid;
	}

	public void setShowOnGrid(String showOnGrid) {
		this.showOnGrid = showOnGrid;
	}

	public String getUnitOfTime() {
		return unitOfTime;
	}

	public void setUnitOfTime(String unitOfTime) {
		this.unitOfTime = unitOfTime;
	}

	@Override
	protected String getUniqueKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
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

}
