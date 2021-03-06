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
package org.kuali.kpme.tklm.time.rules.overtime.daily.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;
import org.kuali.kpme.tklm.time.rules.overtime.daily.DailyOvertimeRule;
import org.kuali.kpme.tklm.time.rules.overtime.daily.dao.DailyOvertimeRuleDao;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetailBo;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class DailyOvertimeRuleServiceImpl implements DailyOvertimeRuleService {

    private static final Logger LOG = Logger.getLogger(DailyOvertimeRuleServiceImpl.class);

	private DailyOvertimeRuleDao dailyOvertimeRuleDao = null;
    private static final ModelObjectUtils.Transformer<TimeBlock, TimeBlock.Builder> toTimeBlockBuilder =
            new ModelObjectUtils.Transformer<TimeBlock, TimeBlock.Builder>() {
                public TimeBlock.Builder transform(TimeBlock input) {
                    return TimeBlock.Builder.create(input);
                };
            };
    private static final ModelObjectUtils.Transformer<TimeBlockBo, TimeBlock> toTimeBlock =
            new ModelObjectUtils.Transformer<TimeBlockBo, TimeBlock>() {
                public TimeBlock transform(TimeBlockBo input) {
                    return TimeBlockBo.to(input);
                };
            };
    private static final ModelObjectUtils.Transformer<TimeBlock, TimeBlockBo> toTimeBlockBo =
            new ModelObjectUtils.Transformer<TimeBlock, TimeBlockBo>() {
                public TimeBlockBo transform(TimeBlock input) {
                    return TimeBlockBo.from(input);
                };
            };

	public void saveOrUpdate(DailyOvertimeRule dailyOvertimeRule) {
		dailyOvertimeRuleDao.saveOrUpdate(dailyOvertimeRule);
	}

	public void saveOrUpdate(List<DailyOvertimeRule> dailyOvertimeRules) {
		dailyOvertimeRuleDao.saveOrUpdate(dailyOvertimeRules);
	}

	@Override
	/**
	 * Search for the valid Daily Overtime Rule, wild cards are allowed on
	 * groupKeyCode maybe wild card later 05/10/14
	 * paytype
	 * department
	 * workArea
	 *
	 * asOfDate is required.
	 */
	public DailyOvertimeRule getDailyOvertimeRule(String groupKeyCode, String paytype, String dept, Long workArea, LocalDate asOfDate) {
		DailyOvertimeRule dailyOvertimeRule = null;

		//		l, p, d, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(groupKeyCode, paytype, dept, workArea, asOfDate);

		//		l, p, d, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(groupKeyCode, paytype, dept, -1L, asOfDate);

		//		l, p, *, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(groupKeyCode, paytype, "%", workArea, asOfDate);

		//		l, p, *, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(groupKeyCode, paytype, "%", -1L, asOfDate);

		//		l, *, d, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(groupKeyCode, "%", dept, workArea, asOfDate);

		//		l, *, d, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(groupKeyCode, "%", dept, -1L, asOfDate);

		//		l, *, *, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(groupKeyCode, "%", "%", workArea, asOfDate);

		//		l, *, *, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule(groupKeyCode, "%", "%", -1L, asOfDate);

		//		*, p, d, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", paytype, dept, workArea, asOfDate);

		//		*, p, d, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", paytype, dept, -1L, asOfDate);

		//		*, p, *, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", paytype, "%", workArea, asOfDate);

		//		*, p, *, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", paytype, "%", -1L, asOfDate);

		//		*, *, d, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", "%", dept, workArea, asOfDate);

		//		*, *, d, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", "%", dept, -1L, asOfDate);

		//		*, *, *, w
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", "%", "%", workArea, asOfDate);

		//		*, *, *, -1
		if (dailyOvertimeRule == null)
			dailyOvertimeRule = dailyOvertimeRuleDao.findDailyOvertimeRule("%", "%", "%", -1L, asOfDate);

		return dailyOvertimeRule;
	}



	public void setDailyOvertimeRuleDao(DailyOvertimeRuleDao dailyOvertimeRuleDao) {
		this.dailyOvertimeRuleDao = dailyOvertimeRuleDao;
	}

	private Assignment getIdentifyingKey(TimeBlockContract block, LocalDate asOfDate, String principalId) {
		List<Assignment> lstAssign = HrServiceLocator.getAssignmentService().getAssignments(principalId, asOfDate);

		for(Assignment assign : lstAssign){
			if((assign.getJobNumber().compareTo(block.getJobNumber()) == 0) && (assign.getWorkArea().compareTo(block.getWorkArea()) == 0)){
				return assign;
			}
		}
		return null;
	}


	public void processDailyOvertimeRules(TimesheetDocument timesheetDocument, TkTimeBlockAggregate timeBlockAggregate){
		Map<DailyOvertimeRule, List<Assignment>> mapDailyOvtRulesToAssignment = new HashMap<DailyOvertimeRule, List<Assignment>>();

		for(Assignment assignment : timesheetDocument.getAllAssignments()) {
			JobContract job = assignment.getJob();
			DailyOvertimeRule dailyOvertimeRule = getDailyOvertimeRule(job.getGroupKey().getGroupKeyCode(), job.getHrPayType(), job.getDept(), assignment.getWorkArea(), timesheetDocument.getDocEndDate());

			if(dailyOvertimeRule !=null) {
				if(mapDailyOvtRulesToAssignment.containsKey(dailyOvertimeRule)){
					List<Assignment> lstAssign = mapDailyOvtRulesToAssignment.get(dailyOvertimeRule);
					lstAssign.add(assignment);
					mapDailyOvtRulesToAssignment.put(dailyOvertimeRule, lstAssign);
				}  else {
					List<Assignment> lstAssign = new ArrayList<Assignment>();
					lstAssign.add(assignment);
					mapDailyOvtRulesToAssignment.put(dailyOvertimeRule, lstAssign);
				}
			}
		}

		//Quick bail
		if(mapDailyOvtRulesToAssignment.isEmpty()){
			return;
		}

        List<List<TimeBlock>> newTimeBlocks = new ArrayList<List<TimeBlock>>();
        List<List<TimeBlockBo>> aggregateTimeBlockBos = new ArrayList<List<TimeBlockBo>>();
        for (List<TimeBlock> imBlocks : timeBlockAggregate.getDayTimeBlockList()) {
            aggregateTimeBlockBos.add(ModelObjectUtils.transform(imBlocks, toTimeBlockBo));
        }
		// TODO: We iterate Day by Day
		for(List<TimeBlockBo> dayTimeBlocks : aggregateTimeBlockBos){

			if (dayTimeBlocks.size() == 0) {
				continue;
            }

			// 1: ... bucketing by (DailyOvertimeRule -> List<TimeBlock>)
			Map<DailyOvertimeRule,List<TimeBlockBo>> dailyOvtRuleToDayTotals = new HashMap<DailyOvertimeRule,List<TimeBlockBo>>();
			for(TimeBlockBo timeBlock : dayTimeBlocks) {
                Assignment assign = this.getIdentifyingKey(timeBlock, timesheetDocument.getAsOfDate(), timesheetDocument.getPrincipalId());
				for(Map.Entry<DailyOvertimeRule, List<Assignment>> entry : mapDailyOvtRulesToAssignment.entrySet()){
					List<Assignment> lstAssign = entry.getValue();

                    // for this kind of operation to work, equals() and hashCode() need to
                    // be over ridden for the object of comparison.
					if(lstAssign.contains(assign)){
                        // comparison here will always work, because we're comparing
                        // against our existing instantiation of the object.
						if(dailyOvtRuleToDayTotals.get(entry.getKey()) != null){
							List<TimeBlockBo> lstTimeBlock = dailyOvtRuleToDayTotals.get(entry.getKey());
							lstTimeBlock.add(timeBlock);
							dailyOvtRuleToDayTotals.put(entry.getKey(), lstTimeBlock);
						} else {
							List<TimeBlockBo> lstTimeBlock = new ArrayList<TimeBlockBo>();
							lstTimeBlock.add(timeBlock);
							dailyOvtRuleToDayTotals.put(entry.getKey(), lstTimeBlock);
						}
					}
				}
			}

			for(DailyOvertimeRule dr : mapDailyOvtRulesToAssignment.keySet() ){
				Set<String> fromEarnGroup = HrServiceLocator.getEarnCodeGroupService().getEarnCodeListForEarnCodeGroup(dr.getFromEarnGroup(), timesheetDocument.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate());
				List<TimeBlockBo> blocksForRule = dailyOvtRuleToDayTotals.get(dr);
				if (blocksForRule == null || blocksForRule.size() == 0) {
					continue; // skip to next rule and check for valid blocks.
                }
				sortTimeBlocksNatural(blocksForRule);

				// 3: Iterate over the timeblocks, apply the rule when necessary.
				BigDecimal hours = BigDecimal.ZERO;
				List<TimeBlockBo> applicationList = new LinkedList<TimeBlockBo>();
				TimeBlockBo previous = null;
				for (TimeBlockBo block : blocksForRule) {
					if (exceedsMaxGap(previous, block, dr.getMaxGap())) {
						apply(hours, applicationList, dr, fromEarnGroup);
						applicationList.clear();
						hours = BigDecimal.ZERO;
						previous = null; // reset our chain
					} else {
						previous = block; // build up our chain
					}
                    applicationList.add(block);
					for (TimeHourDetailBo thd : block.getTimeHourDetails()) {
						if (fromEarnGroup.contains(thd.getEarnCode())) {
							hours = hours.add(thd.getHours(), HrConstants.MATH_CONTEXT);
                        }
                    }
				}
				// when we run out of blocks, we may have more to apply.
				apply(hours, applicationList, dr, fromEarnGroup);
			}
		}
        //convert back to TimeBlock
        for (List<TimeBlockBo> bos : aggregateTimeBlockBos) {
            newTimeBlocks.add(ModelObjectUtils.transform(bos, toTimeBlock));
        }
        timeBlockAggregate.setDayTimeBlockList(newTimeBlocks);
	}

	/**
	 * Reverse sorts blocks and applies hours to matching earn codes in the
	 * time hour detail entries.
	 *
	 * @param hours Total number of Daily Overtime Hours to apply.
	 * @param blocks Time blocks found to need rule application.
	 * @param rule The rule we are applying.
	 * @param earnGroup Earn group we've already loaded for this rule.
	 */
	private void apply(BigDecimal hours, List<TimeBlockBo> blocks, DailyOvertimeRule rule, Set<String> earnGroup) {
		sortTimeBlocksInverse(blocks);
		if (blocks != null && blocks.size() > 0)
			if (hours.compareTo(rule.getMinHours()) >= 0) {
                BigDecimal remaining = hours.subtract(rule.getMinHours(), HrConstants.MATH_CONTEXT);
				for (TimeBlockBo block : blocks) {
					remaining = applyOvertimeToTimeBlock(block, rule.getEarnCode(), earnGroup, remaining);
                }
                if (remaining.compareTo(BigDecimal.ZERO) > 0) {
                    LOG.warn("Hours remaining that were unapplied in DailyOvertimeRule.");
                }
            }
	}


	/**
	 * Method to apply (if applicable) overtime additions to the indiciated TimeBlock.  TimeBlock
	 * earn code is checked against the convertFromEarnCodes Set.
	 *
	 * @param block
	 * @param otEarnCode
	 * @param convertFromEarnCodes
	 * @param otHours
	 *
	 * @return The amount of overtime hours remaining to be applied.  (BigDecimal is immutable)
	 */
	private BigDecimal applyOvertimeToTimeBlock(TimeBlockBo block, String otEarnCode, Set<String> convertFromEarnCodes, BigDecimal otHours) {
		BigDecimal applied = BigDecimal.ZERO;

		if (otHours.compareTo(BigDecimal.ZERO) <= 0) {
			return BigDecimal.ZERO;
        }

		List<TimeHourDetailBo> details = block.getTimeHourDetails();
		List<TimeHourDetailBo> addDetails = new LinkedList<TimeHourDetailBo>();
		for (TimeHourDetailBo detail : details) {
			if (convertFromEarnCodes.contains(detail.getEarnCode())) {
				// n = detailHours - otHours
				BigDecimal n = detail.getHours().subtract(otHours, HrConstants.MATH_CONTEXT);
				// n >= 0 (meaning there are greater than or equal amount of Detail hours vs. OT hours, so apply all OT hours here)
				// n < = (meaning there were more OT hours than Detail hours, so apply only the # of hours in detail and update applied.
				if (n.compareTo(BigDecimal.ZERO) >= 0) {
					// if
					applied = otHours;
				} else {
					applied = detail.getHours();
				}

				// Make a new TimeHourDetail with the otEarnCode with "applied" hours
				TimeHourDetailBo timeHourDetail = new TimeHourDetailBo();
				timeHourDetail.setHours(applied);
				timeHourDetail.setEarnCode(otEarnCode);
				timeHourDetail.setTkTimeBlockId(block.getTkTimeBlockId());

				// Decrement existing matched FROM earn code.
				detail.setHours(detail.getHours().subtract(applied, HrConstants.MATH_CONTEXT));
				addDetails.add(timeHourDetail);
			}
		}

		if (addDetails.size() > 0) {
			details.addAll(addDetails);
			block.setTimeHourDetails(details);
		}

		return otHours.subtract(applied);
	}


	// TODO : Refactor this Copy-Pasta mess to util/comparator classes.

	private void sortTimeBlocksInverse(List<? extends TimeBlockContract> blocks) {
		Collections.sort(blocks, new Comparator<TimeBlockContract>() { // Sort the Time Blocks
			public int compare(TimeBlockContract tb1, TimeBlockContract tb2) {
				if (tb1 != null && tb2 != null)
					return -1 * tb1.getBeginDateTime().compareTo(tb2.getBeginDateTime());
				return 0;
			}
		});
	}


	private void sortTimeBlocksNatural(List<? extends TimeBlockContract> blocks) {
		Collections.sort(blocks, new Comparator<TimeBlockContract>() { // Sort the Time Blocks
			public int compare(TimeBlockContract tb1, TimeBlockContract tb2) {
				if (tb1 != null && tb2 != null)
					return tb1.getBeginDateTime().compareTo(tb2.getBeginDateTime());
				return 0;
			}
		});
	}

	/**
	 * Does the difference between the previous time blocks clock out time and the
	 * current time blocks clock in time exceed the max gap?
	 *
	 * @param previous If null, false is returned.
	 * @param current
	 * @param maxGap
	 * @return
	 */
	boolean exceedsMaxGap(TimeBlockContract previous, TimeBlockContract current, BigDecimal maxGap) {
		if (previous == null)
			return false;

		long difference = current.getBeginDateTime().getMillis() - previous.getEndDateTime().getMillis();
		BigDecimal gapHours = TKUtils.convertMillisToHours(difference);
		BigDecimal cmpGapHrs = TKUtils.convertMinutesToHours(maxGap);
		return (gapHours.compareTo(cmpGapHrs) > 0);
	}

	@Override
	public DailyOvertimeRule getDailyOvertimeRule(String tkDailyOvertimeRuleId) {
		return dailyOvertimeRuleDao.getDailyOvertimeRule(tkDailyOvertimeRuleId);
	}
	
	public List<DailyOvertimeRule> getDailyOvertimeRules(String userPrincipalId, List <DailyOvertimeRule> dailyOvertimeRuleObjs){
		List<DailyOvertimeRule> results = new ArrayList<DailyOvertimeRule>();
    	
    	if ( dailyOvertimeRuleObjs != null ){
    		for (DailyOvertimeRule dailyOvertimeRuleObj : dailyOvertimeRuleObjs) {
            	String department = dailyOvertimeRuleObj.getDept();
            	String groupKeyCd = dailyOvertimeRuleObj.getGroupKeyCode();
            	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, groupKeyCd, dailyOvertimeRuleObj.getEffectiveLocalDate());
            	String loc = departmentObj != null ? departmentObj.getGroupKey().getLocationId() : null;
            	
            	Map<String, String> roleQualification = new HashMap<String, String>();
            	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
            	roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
                roleQualification.put(KPMERoleMemberAttribute.GROUP_KEY_CODE.getRoleMemberAttributeName(), groupKeyCd);
            	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), loc);
            	
            	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
        				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
        		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
        				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
            		results.add(dailyOvertimeRuleObj);
            	}
        	}
    	}
    	
    	return results;
	}
}
