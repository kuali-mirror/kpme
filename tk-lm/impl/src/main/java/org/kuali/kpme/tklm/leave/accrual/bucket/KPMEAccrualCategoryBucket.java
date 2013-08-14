/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.tklm.leave.accrual.bucket;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.leave.accrual.bucket.KPMEAccrualCategoryBucketContract;
import org.kuali.kpme.tklm.leave.accrual.bucket.exception.KPMEBalanceException;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;

public class KPMEAccrualCategoryBucket implements KPMEAccrualCategoryBucketContract {

	//Pre-loaded with classes defined in bean kpmeAccrualCategoryBucket if instantiated via context
	private ArrayList<Class<LeaveBalance>> baseBalanceList;
	
	//Leave Summary
	private LinkedHashMap<String, List<LeaveBalance>> leaveBalances;
	private CalendarEntry viewingCalendarEntry;
	private PrincipalHRAttributes principalCalendar;
	private boolean isInitialized;
	private LocalDate asOfDate;
	
	public void initialize(PrincipalHRAttributes currentPrincipalCalendar) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		leaveBalances = new LinkedHashMap<String, List<LeaveBalance>>();
		principalCalendar = currentPrincipalCalendar;
		asOfDate = DateTime.now().toLocalDate();
		List<AccrualCategory> accrualCategories = HrServiceLocator.getAccrualCategoryService().getActiveAccrualCategoriesForLeavePlan(currentPrincipalCalendar.getLeavePlan(), asOfDate);
		for(AccrualCategory accrualCategory : accrualCategories) {
			List<LeaveBalance> leaveBalances = new ArrayList<LeaveBalance>();
			for(Class<LeaveBalance> leaveBalanceClazz : baseBalanceList) {
				Constructor<?>[] constructors = leaveBalanceClazz.getConstructors();
				//does this array contain default constructors as well??
				Object [] args = new Object[2];
				args[0] = accrualCategory;
				args[1] = principalCalendar;
				Constructor<?> constructor = constructors[0];
				LeaveBalance myLeaveBalance = (LeaveBalance) constructor.newInstance(args);
				leaveBalances.add(myLeaveBalance);
			}
			this.leaveBalances.put(accrualCategory.getLmAccrualCategoryId(),leaveBalances);
		}
		//This list *could* contain leave blocks for accrual categories that have been deactivated, those from another leave plan, etc
		List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksSinceCarryOver(currentPrincipalCalendar.getPrincipalId(),
				LmServiceLocator.getLeaveBlockService().getLastCarryOverBlocks(currentPrincipalCalendar.getPrincipalId(), asOfDate),
				DateTime.now().toLocalDate().plusDays(365), true);
		
		//These maps could also contain such accrual categories within their keyset.
		Map<String, LeaveBlock> carryOverBlocks = LmServiceLocator.getLeaveBlockService().getLastCarryOverBlocks(currentPrincipalCalendar.getPrincipalId(), asOfDate);
		Map<String, List<LeaveBlock>> accrualCategoryMappedLeaveBlocks = mapLeaveBlocksByAccrualCategory(leaveBlocks);
		//merge carryOverBlock map with accrualCategoryMappedLeaveBlocks.
		for(Entry<String, LeaveBlock> entry : carryOverBlocks.entrySet()) {
			if(accrualCategoryMappedLeaveBlocks.containsKey(entry.getKey()))
				accrualCategoryMappedLeaveBlocks.get(entry.getKey()).add(entry.getValue());
			else {
				List<LeaveBlock> carryOverList = new ArrayList<LeaveBlock>();
				carryOverList.add(entry.getValue());
				accrualCategoryMappedLeaveBlocks.put(entry.getKey(), carryOverList);
			}
		}
		//add leave blocks, accrual category by accrual category, to bucket.
		for(Entry<String, List<LeaveBlock>> entry : accrualCategoryMappedLeaveBlocks.entrySet()) {
			for(LeaveBlock leaveBlock : entry.getValue()) {
				try {
					addLeaveBlock(leaveBlock);
				} catch (KPMEBalanceException e) {
					InstantiationException ie = new InstantiationException();
					ie.setStackTrace(e.getStackTrace());
					throw ie;
				}
			}
		}
	}
	
	private void initialize(AccrualCategory accrualCategory) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<LeaveBalance> leaveBalances = new ArrayList<LeaveBalance>();
		for(Class<LeaveBalance> leaveBalance : baseBalanceList) {
			Constructor<?>[] constructors = leaveBalance.getConstructors();
			Object [] args = new Object[2];
			args[0] = accrualCategory;
			args[2] = principalCalendar;
			Constructor<?> constructor = constructors[0];
			LeaveBalance myLeaveBalance = (LeaveBalance) constructor.newInstance(args);
			leaveBalances.add(myLeaveBalance);
		}
		this.leaveBalances.put(accrualCategory.getLmAccrualCategoryId(),leaveBalances);
	}
	
	public LeaveBalance getLeaveBalance(AccrualCategoryContract accrualCategory, String balanceType) {
		List<LeaveBalance> leaveBalances = this.leaveBalances.get(accrualCategory.getLmAccrualCategoryId());
		for(LeaveBalance leaveBalance : leaveBalances) {
			if(leaveBalance.getBalanceType().equals(balanceType))
				return leaveBalance;
		}
		return null;
	}

	public void addLeaveBlock(LeaveBlock leaveBlock) throws KPMEBalanceException {
		AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(leaveBlock.getAccrualCategory(), leaveBlock.getLeaveLocalDate());
		List<LeaveBalance> balancesForAccrualCategory = leaveBalances.get(accrualCategory.getLmAccrualCategoryId());
		if(balancesForAccrualCategory == null) {
			try {
				initialize(accrualCategory);
			} catch (InstantiationException e) {
				KPMEBalanceException be = new KPMEBalanceException();
				be.setStackTrace(e.getStackTrace());
				throw be;
			} catch (IllegalAccessException e) {
				KPMEBalanceException be = new KPMEBalanceException();
				be.setStackTrace(e.getStackTrace());
				throw be;
			} catch (IllegalArgumentException e) {
				KPMEBalanceException be = new KPMEBalanceException();
				be.setStackTrace(e.getStackTrace());
				throw be;
			} catch (InvocationTargetException e) {
				KPMEBalanceException be = new KPMEBalanceException();
				be.setStackTrace(e.getStackTrace());
				throw be;
			}
		}
		if(!(StringUtils.equals(leaveBlock.getRequestStatus(), HrConstants.REQUEST_STATUS.DEFERRED)
						|| StringUtils.equals(leaveBlock.getRequestStatus(), HrConstants.REQUEST_STATUS.DISAPPROVED))) {
			for(LeaveBalance leaveBalance : balancesForAccrualCategory) {
				leaveBalance.add(leaveBlock);
			}
		}
		
	}

	public void removeLeaveBlock(LeaveBlock leaveBlock) throws KPMEBalanceException {
		AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(leaveBlock.getAccrualCategory(), leaveBlock.getLeaveLocalDate());
		List<LeaveBalance> balancesForAccrualCategory = leaveBalances.get(accrualCategory.getLmAccrualCategoryId());
		if(balancesForAccrualCategory == null) {
			try {
				initialize(accrualCategory);
			} catch (InstantiationException e) {
				KPMEBalanceException be = new KPMEBalanceException();
				be.setStackTrace(e.getStackTrace());
				throw be;
			} catch (IllegalAccessException e) {
				KPMEBalanceException be = new KPMEBalanceException();
				be.setStackTrace(e.getStackTrace());
				throw be;
			} catch (IllegalArgumentException e) {
				KPMEBalanceException be = new KPMEBalanceException();
				be.setStackTrace(e.getStackTrace());
				throw be;
			} catch (InvocationTargetException e) {
				KPMEBalanceException be = new KPMEBalanceException();
				be.setStackTrace(e.getStackTrace());
				throw be;
			}
		}

		if(!(StringUtils.equals(leaveBlock.getRequestStatus(), HrConstants.REQUEST_STATUS.DEFERRED)
				|| StringUtils.equals(leaveBlock.getRequestStatus(), HrConstants.REQUEST_STATUS.DISAPPROVED))) {
			//leaveBalance.add is gaurded by the same conditional. These should not exist in any leave balance,
			//except perhaps a "DEFERRED" or "DISAPPROVED" leave balance.
			for(LeaveBalance leaveBalance : balancesForAccrualCategory) {
				leaveBalance.remove(leaveBlock);
			}
		}
	}
	
	public void editLeaveBlock(LeaveBlock leaveBlock) throws KPMEBalanceException {
		AccrualCategory accrualCategory = HrServiceLocator.getAccrualCategoryService().getAccrualCategory(leaveBlock.getAccrualCategory(), leaveBlock.getLeaveLocalDate());
		List<LeaveBalance> balancesForAccrualCategory = leaveBalances.get(accrualCategory.getLmAccrualCategoryId());
		if(balancesForAccrualCategory == null) {
			try {
				initialize(accrualCategory);
			} catch (InstantiationException e) {
				KPMEBalanceException be = new KPMEBalanceException();
				be.setStackTrace(e.getStackTrace());
				throw be;
			} catch (IllegalAccessException e) {
				KPMEBalanceException be = new KPMEBalanceException();
				be.setStackTrace(e.getStackTrace());
				throw be;
			} catch (IllegalArgumentException e) {
				KPMEBalanceException be = new KPMEBalanceException();
				be.setStackTrace(e.getStackTrace());
				throw be;
			} catch (InvocationTargetException e) {
				KPMEBalanceException be = new KPMEBalanceException();
				be.setStackTrace(e.getStackTrace());
				throw be;
			}
		}
		
		for(LeaveBalance leaveBalance : balancesForAccrualCategory) {
			leaveBalance.adjust(leaveBlock);
		}
	}

	public void calculateLeaveBalanceForCalendar(CalendarEntry calendarEntry) throws KPMEBalanceException {
		
		if(viewingCalendarEntry == null) {
			viewingCalendarEntry = calendarEntry;
		}
		
		if(!viewingCalendarEntry.getHrCalendarId().equals(calendarEntry.getHrCalendarId()))
			throw new IllegalArgumentException("LeaveCalendarDocument's Calendar Entry must be from within the same Calendar");

		//assignment keys are needed to filter out leave blocks created by time calendar service
		Set<String> assignmentKeys = new HashSet<String>();

		//build assignment key set, and determine if the calendar switch is into a different leave plan calendar year.
		DateTime rolloverDate = iterateOverCalendarEntries(assignmentKeys, calendarEntry);
		
		//removes leave blocks affected by the calendar change, adjusts the asOfDate for the bucket and its leave balances,
		//then re-adds the affected leave blocks
		adjustBalances(calendarEntry, rolloverDate, assignmentKeys);

		viewingCalendarEntry = calendarEntry;
		isInitialized = true;
		
	}
	
	private void adjustAsOfDates(CalendarEntry calendarEntry, List<CalendarEntry> calendarEntries) {
		
		LocalDate newAsOfDate = null;
		
		//determine if the bucket is being switched to the current leave year
		//also compute max end date to determine if the bucket is being switched to a future leave year. ( could use leave plan service's getRollOverDate )
		boolean switchingToCurrentLeaveYear = false;
		LocalDate maxEndDate = LocalDate.now();
		for(CalendarEntry entry : calendarEntries) {
			if(entry.getHrCalendarEntryId().equals(calendarEntry.getHrCalendarEntryId())
					&& entry.getHrCalendarId().equals(calendarEntry.getHrCalendarId())) {
				switchingToCurrentLeaveYear = true;
			}
			if(entry.getEndPeriodDate().after(maxEndDate.toDate()))
				maxEndDate = LocalDate.fromDateFields(entry.getEndPeriodDate());
		}
		
    	if(switchingToCurrentLeaveYear) {
    		Interval otherCalendarInterval = new Interval(calendarEntry.getBeginPeriodDate().getTime(), calendarEntry.getEndPeriodDate().getTime());
    		if(otherCalendarInterval.contains(LocalDate.now().toDate().getTime())) {
    			//switching to the present calendar.
    			newAsOfDate = LocalDate.now();
    		}
    		else
    			if(otherCalendarInterval.getEnd().isBefore(LocalDate.now().toDate().getTime())) {
    				//switching to a historical calendar in the current leave plan year, use calendar end date
    				newAsOfDate = otherCalendarInterval.getEnd().toLocalDate().minusDays(1);
    			}
    			else {
    				//switching to a future/planning calendar in the current leave plan year, use calendar start date.
    				newAsOfDate = otherCalendarInterval.getStart().toLocalDate().minusDays(1);
    			}
    	}
    	else
    		if(calendarEntry.getEndPeriodDate().after(maxEndDate.toDate())){
    			//switching to a leave year beyond the current leave year, same as future/planning calendar in current leave year.
    			newAsOfDate = LocalDate.fromDateFields(calendarEntry.getBeginPeriodDate()).minusDays(1);
    		}
    		else {
    			//switching to a calendar period within a past leave calendar year.
    			DateTime otherCalendarRolloverDate = HrServiceLocator.getLeavePlanService().getRolloverDayOfLeavePlan(principalCalendar.getLeavePlan(), LocalDate.fromDateFields(calendarEntry.getEndPeriodDate()));
    			//for past leave calendar years, regardless of calendar period, we require values as of the rollover day.
				newAsOfDate = LocalDate.fromDateFields(otherCalendarRolloverDate.toDate()).minusDays(1);
    		}

    	//update asOfDates and calendar period end point dates for all leave balance objects
    	for(Entry<String, List<LeaveBalance>> entry : leaveBalances.entrySet()) {
			for(LeaveBalance leaveBalance : entry.getValue()) {
				leaveBalance.asOfDate = newAsOfDate;
				leaveBalance.calendarPeriodBeginDate = LocalDate.fromDateFields(calendarEntry.getBeginPeriodDate());
				leaveBalance.calendarPeriodEndDate = LocalDate.fromDateFields(calendarEntry.getEndPeriodDate());
			}
		}
    	
    	//reset this buckets asOfDate
    	asOfDate = newAsOfDate;
    	
	}
	
	private void adjustBalances(CalendarEntry calendarEntry, DateTime rolloverDate, Set<String> assignmentKeys) throws KPMEBalanceException {
		
		//fetch calendar entries in this sequence belonging to the current leave year.
		String dateString = TKUtils.formatDate(LocalDate.now());
		List<CalendarEntry> calendarEntries = HrServiceLocator.getCalendarEntryService().getAllCalendarEntriesForCalendarIdAndYear(calendarEntry.getHrCalendarId(), dateString.substring(6, 10));

		//calendar change into a different leave calendar year
		if(rolloverDate != null) {

			//clear all balances.
	    	clearLeaveBalances();
	    	
	    	//reset the asOfDate this bucket will go off of, as well as those of the leave balances
	    	adjustAsOfDates(calendarEntry, calendarEntries);
	    	
	    	//TODO: if in current calendar year, use current date + planning months for extent of fetch.
			List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksSinceCarryOver(principalCalendar.getPrincipalId(),
					LmServiceLocator.getLeaveBlockService().getLastCarryOverBlocks(principalCalendar.getPrincipalId(), rolloverDate.minusDays(1).toLocalDate()),
					rolloverDate.plusDays(365).toLocalDate(), true);
			
			//retrieve (if any) last carryover blocks given the new asOfDate
			Map<String, LeaveBlock> carryOverBlocks = LmServiceLocator.getLeaveBlockService().getLastCarryOverBlocks(principalCalendar.getPrincipalId(), asOfDate);
			
			//method taken from leave summary service - map general leave block fetch by accrual category
			Map<String, List<LeaveBlock>> accrualCategoryMappedLeaveBlocks = mapLeaveBlocksByAccrualCategory(leaveBlocks);
			
			//merge carryOverBlock map with accrualCategoryMappedLeaveBlocks.
			for(Entry<String, LeaveBlock> entry : carryOverBlocks.entrySet()) {
				//TODO: modify CarryOverLeaveBalance to identify and make use of CARRY_OVER type leave block
				if(accrualCategoryMappedLeaveBlocks.containsKey(entry.getKey()))
					accrualCategoryMappedLeaveBlocks.get(entry.getKey()).add(entry.getValue());
				else {
					List<LeaveBlock> carryOverList = new ArrayList<LeaveBlock>();
					carryOverList.add(entry.getValue());
					accrualCategoryMappedLeaveBlocks.put(entry.getKey(), carryOverList);
				}
				
			}
			//add leave blocks, accrual category by accrual category, to this bucket.
			for(Entry<String, List<LeaveBlock>> entry : accrualCategoryMappedLeaveBlocks.entrySet()) {
				for(LeaveBlock leaveBlock : entry.getValue()) {
					try {
						addLeaveBlock(leaveBlock);
					} catch (KPMEBalanceException e) {
						InstantiationException ie = new InstantiationException();
						ie.setStackTrace(e.getStackTrace());
						ie.printStackTrace();
					}
				}
			}
    	} else {
    		//Calendar change within the same leave year.
    		
    		
			List<String> assignmentKeyList = new ArrayList<String>();
			assignmentKeyList.addAll(assignmentKeys);
			
			List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
			
			//determine which leave blocks are affected by the calendar change, remove them, and re-add them under the new asOfDate
			if(calendarEntry.getEndPeriodFullDateTime().isBefore(viewingCalendarEntry.getEndPeriodFullDateTime().getMillis())) {
				//need to remove leave blocks from otherLeaveCalendarDocument begin date to thisLeaveCalendarDocument end date
				leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalCalendar.getPrincipalId(), LocalDate.fromDateFields(calendarEntry.getBeginPeriodDateTime()), LocalDate.fromDateFields(viewingCalendarEntry.getEndPeriodDateTime()));
				//leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForLeaveCalendar(principalCalendar.getPrincipalId(), LocalDate.fromDateFields(calendarEntry.getBeginPeriodDateTime()), LocalDate.fromDateFields(viewingCalendarEntry.getEndPeriodDateTime()), assignmentKeyList);
			} else {
				//need to remove leave blocks from thisLeaveCalendarDocument begin date to otherLeaveCalendarDocument end date
				leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocks(principalCalendar.getPrincipalId(), LocalDate.fromDateFields(viewingCalendarEntry.getBeginPeriodDateTime()), LocalDate.fromDateFields(calendarEntry.getEndPeriodDateTime()));
				//leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksForLeaveCalendar(principalCalendar.getPrincipalId(), LocalDate.fromDateFields(viewingCalendarEntry.getBeginPeriodDateTime()), LocalDate.fromDateFields(calendarEntry.getEndPeriodDateTime()), assignmentKeyList);
			}
			//remove affected leave blocks
			for(LeaveBlock block : leaveBlocks) {
				removeLeaveBlock(block);
			}

			//update this bucket and its leave balances with new relative date information
			LocalDate newAsOfDate = null;
			DateTime currentLeavePlanStartDate = HrServiceLocator.getLeavePlanService().getFirstDayOfLeavePlan(principalCalendar.getLeavePlan(), LocalDate.now()); 
			if(calendarEntry.getEndPeriodDate().before(currentLeavePlanStartDate.toDate())) {
				//require balances as of the final day of the leave calendar year.
				DateTime calendarEntryLeavePlanRolloverDate = HrServiceLocator.getLeavePlanService().getRolloverDayOfLeavePlan(principalCalendar.getLeavePlan(), LocalDate.fromDateFields(calendarEntry.getEndPeriodDate()));
				newAsOfDate = LocalDate.fromDateFields(calendarEntryLeavePlanRolloverDate.toDate()).minusDays(1);
			}
			else {
				Interval interval = new Interval(calendarEntry.getBeginPeriodDateTime().getTime(),calendarEntry.getEndPeriodDateTime().getTime());
				if(interval.contains(LocalDate.now().toDate().getTime())) {
					newAsOfDate = LocalDate.now();
				}
				else
					if(calendarEntry.getBeginPeriodDate().before(LocalDate.now().toDate()))
						newAsOfDate = LocalDate.fromDateFields(calendarEntry.getEndPeriodDate()).minusDays(1);
					else // if it's in the calendar interval above, the equals case is taken care of, begin date must be after today
						newAsOfDate = LocalDate.fromDateFields(calendarEntry.getBeginPeriodDate()).minusDays(1);
			}

			asOfDate = newAsOfDate;
			
			for(Entry<String, List<LeaveBalance>> leaveBalance : leaveBalances.entrySet()) {
				for(LeaveBalance balance : leaveBalance.getValue()) {
					balance.calendarPeriodBeginDate = LocalDate.fromDateFields(calendarEntry.getBeginPeriodDateTime());
					balance.calendarPeriodEndDate = LocalDate.fromDateFields(calendarEntry.getEndPeriodDateTime());
					balance.asOfDate = newAsOfDate;
				}
			}
			//re-add the affected leave blocks under the new date / calendar information
			for(LeaveBlock block : leaveBlocks) {
				addLeaveBlock(block);
			}
    	}
	}

	private DateTime iterateOverCalendarEntries(Set<String> assignmentKeys, CalendarEntry otherCalendarEntry) {
		
		DateTime rolloverDate = null;
		
		DateTime leavePlanPrevStart = HrServiceLocator.getLeavePlanService().getFirstDayOfLeavePlan(principalCalendar.getLeavePlan(), asOfDate);
		DateTime leavePlanStart = HrServiceLocator.getLeavePlanService().getRolloverDayOfLeavePlan(principalCalendar.getLeavePlan(), asOfDate);

		if(otherCalendarEntry.getEndPeriodFullDateTime().isBefore(viewingCalendarEntry.getEndPeriodFullDateTime().getMillis())) {
			//moving backward from viewingCalendarEntry
			CalendarEntry itor = viewingCalendarEntry;
			
			while(!itor.getEndPeriodDate().before(otherCalendarEntry.getEndPeriodDate())) {
				//need to iterate over calendars to gather assignment keys
				List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(principalCalendar.getPrincipalId(), itor);
		        for (Assignment assignment : assignments) {
		        	assignmentKeys.add(assignment.getAssignmentKey());
		        }
		        //check if the iteration crosses a roll over date
		        if(itor.getEndPeriodDate().compareTo(leavePlanPrevStart.toDate()) <= 0) {
		        	rolloverDate = HrServiceLocator.getLeavePlanService().getFirstDayOfLeavePlan(principalCalendar.getLeavePlan(), LocalDate.fromDateFields(itor.getBeginPeriodDate()));
		        	leavePlanPrevStart = rolloverDate;
		        }
				itor = HrServiceLocator.getCalendarEntryService().getPreviousCalendarEntryByCalendarId(viewingCalendarEntry.getHrCalendarId(), itor);

			}
			
		}
		else if(otherCalendarEntry.getEndPeriodFullDateTime().isAfter(viewingCalendarEntry.getEndPeriodFullDateTime().getMillis())) {
			//moving forward from viewingCalendarEntry
			CalendarEntry itor = viewingCalendarEntry;
			while(!itor.getEndPeriodDate().after(otherCalendarEntry.getEndPeriodDate())) {
				
				List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(principalCalendar.getPrincipalId(), itor);
		        for (Assignment assignment : assignments) {
		        	assignmentKeys.add(assignment.getAssignmentKey());
		        }

				if(itor.getBeginPeriodDate().compareTo(leavePlanStart.toDate()) >= 0) {
					rolloverDate = HrServiceLocator.getLeavePlanService().getRolloverDayOfLeavePlan(principalCalendar.getLeavePlan(), LocalDate.fromDateFields(itor.getEndPeriodDate()));
					leavePlanStart = rolloverDate;
				}
				
				itor = HrServiceLocator.getCalendarEntryService().getNextCalendarEntryByCalendarId(viewingCalendarEntry.getHrCalendarId(), itor);
			}
		}

		return rolloverDate;
	}

	private void clearLeaveBalances() {
		for(Entry<String, List<LeaveBalance>> entry : leaveBalances.entrySet()) {
			for(LeaveBalance leaveBalance : entry.getValue()) {
				leaveBalance.clear();
			}
		}
	}
	
	public void calculateLeaveBalanceForPreviousCalendar() {
		
		
	}

	public void calculateLeaveBalanceForNextCalendar() {
		
	}
	
	public List<LeaveBalance> getLeaveBalancesForAccrualCategory(AccrualCategoryContract accrualCategory) {
		return leaveBalances.get(accrualCategory.getAccrualCategory());
	}

	public LinkedHashMap<String, List<LeaveBalance>> getLeaveBalances() {
		return leaveBalances;
	}

	public void setLeaveBalances(LinkedHashMap<String, List<LeaveBalance>> balances) {
		this.leaveBalances = balances;
	}

    private Map<String, List<LeaveBlock>> mapLeaveBlocksByAccrualCategory(List<LeaveBlock> leaveBlocks) {
        Map<String, List<LeaveBlock>> map = new HashMap<String, List<LeaveBlock>>();
        for (LeaveBlock lb : leaveBlocks) {
            if (map.containsKey(lb.getAccrualCategory())) {
                map.get(lb.getAccrualCategory()).add(lb);
            } else {
                List<LeaveBlock> splitLeaveBlocks = new ArrayList<LeaveBlock>();
                splitLeaveBlocks.add(lb);
                map.put(lb.getAccrualCategory(), splitLeaveBlocks);
            }
        }
        return map;
    }

	public CalendarEntry getLeaveCalendarDocument() {
		return viewingCalendarEntry;
	}

	public void setCalendarDocument(CalendarEntry calendarDocument) {
		if(calendarDocument != null)
			viewingCalendarEntry = calendarDocument;
		else
			throw new IllegalArgumentException("This service only accepts LeaveCalendarDocument CalendarDocument types");
	}

	public List<Class<LeaveBalance>> getBaseBalanceList() {
		return baseBalanceList;
	}

	public void setBaseBalanceList(List<Class<LeaveBalance>> baseBalanceList) {
		this.baseBalanceList = (ArrayList<Class<LeaveBalance>>) baseBalanceList;
	}


	public LeaveBlock withdrawal(AccrualCategory accrualCategory,
			BigDecimal amount) {
		return null;
	}

	public boolean isInitialized() {
		return isInitialized;
	}

}
