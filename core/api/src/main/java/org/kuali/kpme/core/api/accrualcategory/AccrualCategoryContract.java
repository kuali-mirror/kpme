package org.kuali.kpme.core.api.accrualcategory;

import java.math.BigDecimal;
import java.util.List;

import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRuleContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.leaveplan.LeavePlanContract;
import org.kuali.rice.core.api.mo.common.GloballyUnique;
import org.kuali.rice.core.api.mo.common.Versioned;
import org.kuali.rice.core.api.mo.common.active.Inactivatable;

/**
 * <p>AccrualCategoryContract interface.</p>
 *
 */
public interface AccrualCategoryContract extends Versioned, GloballyUnique, Inactivatable {
	
	/**
	 * Flag that shows if the AccrualCetegory has any AccrualCategoryRules
	 * 
	 * <p>
	 * hasRules flag of AccrualCategory
	 * </p>
	 * 
	 * @return Y if has rules, N if not
	 */
	public String getHasRules();
	
	/**
	 * The Minimum percentage of work that needs to be reached to earn accrual
	 * for this Accrual Category
	 * 
	 * <p>
	 * minimumPercentageWorked of an AccrualCategory
	 * <p>
	 * 
	 * @return minPercentWorked for AccrualCategory
	 */
	public BigDecimal getMinPercentWorked();

	/**
	 * The LeavePlan object associated with the AccrualCategory
	 * 
	 * <p>
	 * leavePlanObject of an AccrualCategory
	 * <p>
	 * 
	 * @return leavePlanObj for AccrualCategory
	 */
	public LeavePlanContract getLeavePlanObj();
	
	/**
	 * The list of AccrualCaegoryRules of the AccrualCategory
	 * 
	 * <p>
	 * accrualCategoryRules of an AccrualCategory
	 * <p>
	 * 
	 * @return accrualCategoryRules for AccrualCategory
	 */
	public List<? extends AccrualCategoryRuleContract> getAccrualCategoryRules();
	
	/**
	 * The Primary Key of an AccrualCategory entry saved in a database
	 * 
	 * <p>
	 * lmAccrualCategoryId of an AccrualCategory
	 * <p>
	 * 
	 * @return lmAccrualCategoryId for AccrualCategory
	 */
	public String getLmAccrualCategoryId();
	
	/**
	 * The LeavePlan name associated with the AccrualCategory
	 * 
	 * <p>
	 * leavePlan of an AccrualCategory
	 * <p>
	 * 
	 * @return leavePlan for AccrualCategory
	 */
	public String getLeavePlan();

	/**
	 * The name of an AccrualCategory
	 * 
	 * <p>
	 * accrualCategory of an AccrualCategory
	 * <p>
	 * 
	 * @return accrualCategory for AccrualCategory
	 */
	public String getAccrualCategory();
	
	/**
	 * The description of an AccrualCategory
	 * 
	 * <p>
	 * description of an AccrualCategory
	 * <p>
	 * 
	 * @return desc for AccrualCategory
	 */
	public String getDescr();
	
	/**
	 * The Accrual Earn Interval of an AccrualCategory
	 * 
	 * <p>
	 * accrualEarnInterval of an AccrualCategory
	 * <p>
	 * 
	 * @return accrualEarnInterval for AccrualCategory
	 */
	public String getAccrualEarnInterval();
	
	/**
	 * The Proration flag of an AccrualCategory
	 * 
	 * <p>
	 * proration flag of an AccrualCategory
	 * <p>
	 * 
	 * @return Y if on, N if not
	 */
	public String getProration();

	/**
	 * The Donation flag of an AccrualCategory
	 * 
	 * <p>
	 * donation flag of an AccrualCategory
	 * <p>
	 * 
	 * @return Y if on, N if not
	 */
	public String getDonation() ;
	
	/**
	 * The showOnGrid flag of an AccrualCategory. If on, the accrued leave balances of the 
	 * AccrualCategory will show on Summary grid of leave clendar
	 * 
	 * <p>
	 * showOnGrid flag of an AccrualCategory
	 * <p>
	 * 
	 * @return Y if on, N if not
	 */
	public String getShowOnGrid();

	/**
	 * Unit of time for accruing leave of an AccrualCategory
	 * 
	 * <p>
	 * unitOfTime of an AccrualCategory
	 * <p>
	 * 
	 * @return unitOfTime for AccrualCategory
	 */
	public String getUnitOfTime();

	/**
	 * The history flag of an AccrualCategory
	 * 
	 * <p>
	 * history flag of an AccrualCategory
	 * <p>
	 * 
	 * @return Y if on, N if not
	 */
	public Boolean getHistory();
	
	/**
	 * The EarnCode of the earnCodeObject associated with an AccrualCategory
	 * 
	 * <p>
	 * EarnCode of an AccrualCategory
	 * <p>
	 * 
	 * @return EarnCode for AccrualCategory
	 */
	public String getEarnCode();

	/**
	 * The earnCodeObject associated with an AccrualCategory
	 * 
	 * <p>
	 * earnCodeObject of an AccrualCategory
	 * <p>
	 * 
	 * @return earnCodeObject for AccrualCategory
	 */
	public EarnCodeContract getEarnCodeObj();

}
