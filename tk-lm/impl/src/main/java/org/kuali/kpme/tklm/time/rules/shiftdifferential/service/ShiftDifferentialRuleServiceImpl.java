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
package org.kuali.kpme.tklm.time.rules.shiftdifferential.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockService;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ShiftDifferentialRule;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.dao.ShiftDifferentialRuleDao;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.shift.Shift;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.shift.ShiftBlock;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.shift.ShiftCalendarInterval;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.util.TkTimeBlockAggregate;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.action.ActionRequest;
import org.kuali.rice.kew.api.action.ActionRequestStatus;
import org.kuali.rice.kew.api.action.ActionRequestType;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.api.action.AdHocToPrincipal;
import org.kuali.rice.kew.api.action.DocumentActionParameters;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ShiftDifferentialRuleServiceImpl implements ShiftDifferentialRuleService {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(ShiftDifferentialRuleServiceImpl.class);
	/**
	 * The maximum allowable time between timeblocks before we consider them to
	 * be day-boundary single time blocks.
	 */
	private ShiftDifferentialRuleDao shiftDifferentialRuleDao = null;


	private Map<Long,Set<ShiftDifferentialRule>> getJobNumberToShiftRuleMap(TimesheetDocument timesheetDocument) {
		Map<Long,Set<ShiftDifferentialRule>> jobNumberToShifts = new HashMap<Long,Set<ShiftDifferentialRule>>();
		PrincipalHRAttributes principalCal = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(timesheetDocument.getPrincipalId(),timesheetDocument.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate());

		for (Job job : timesheetDocument.getJobs()) {
			List<ShiftDifferentialRule> shiftDifferentialRules = getShiftDifferentalRules(job.getGroupKey().getLocationId(),job.getHrSalGroup(),job.getPayGrade(),principalCal.getPayCalendar(), 
					timesheetDocument.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate());
			if (shiftDifferentialRules.size() > 0)
				jobNumberToShifts.put(job.getJobNumber(), new HashSet<ShiftDifferentialRule>(shiftDifferentialRules));
		}

		return jobNumberToShifts;
	}


    private List<ShiftCalendarInterval> getShiftCalendarIntervals(TimesheetDocument timesheetDocument, DateTimeZone zone) {
        PrincipalHRAttributes principalCal = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(timesheetDocument.getPrincipalId(),timesheetDocument.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate());

        Map<String, Set<Long>> jobsForShiftRule = new HashMap<>();
        Map<String, ShiftDifferentialRule> shiftDifferentialRuleMap = new HashMap<>();
        for (Job job : timesheetDocument.getJobs()) {
            List<ShiftDifferentialRule> shiftDifferentialRules = getShiftDifferentalRules(job.getGroupKey().getLocationId(),job.getHrSalGroup(),job.getPayGrade(),principalCal.getPayCalendar(),
                    timesheetDocument.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate());
            for (ShiftDifferentialRule sdr : shiftDifferentialRules) {
                shiftDifferentialRuleMap.put(sdr.getId(), sdr);
                if (jobsForShiftRule.containsKey(sdr.getId())) {
                    jobsForShiftRule.get(sdr.getId()).add(job.getJobNumber());
                } else {
                    Set<Long> tempSet = new HashSet<Long>();
                    tempSet.add(job.getJobNumber());
                    jobsForShiftRule.put(sdr.getId(), tempSet);
                }
            }
        }

        List<ShiftCalendarInterval> sci = new ArrayList<>();
        CalendarEntry ce = timesheetDocument.getCalendarEntry();
        for (Map.Entry<String, Set<Long>> entry : jobsForShiftRule.entrySet()) {
            sci.add(new ShiftCalendarInterval(entry.getValue(), shiftDifferentialRuleMap.get(entry.getKey()), ce.getBeginPeriodLocalDateTime(), ce.getEndPeriodLocalDateTime(), zone));
        }

        return sci;
    }

	protected Map<Long,List<TimeBlock.Builder>> getPreviousPayPeriodLastDayJobToTimeBlockMap(TimesheetDocument timesheetDocument, Map<Long,Set<ShiftDifferentialRule>> jobNumberToShifts) {
		Map<Long, List<TimeBlock.Builder>> jobNumberToTimeBlocksPreviousDay = null;

		// Get the last day of the last week of the previous pay period.
		// This is the only day that can have impact on the current day.
		List<TimeBlock> prevBlocks = TkServiceLocator.getTimesheetService().getPrevDocumentTimeBlocks(timesheetDocument.getPrincipalId(), timesheetDocument.getDocumentHeader().getBeginDateTime());
		if (prevBlocks.size() > 0) {
			TimesheetDocumentHeader prevTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader(timesheetDocument.getPrincipalId(), timesheetDocument.getDocumentHeader().getBeginDateTime().toDateTime());
			if (prevTdh != null) {
				CalendarEntry prevPayCalendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarDatesByPayEndDate(timesheetDocument.getPrincipalId(), prevTdh.getEndDateTime(), HrConstants.PAY_CALENDAR_TYPE);
                Calendar calendar = HrServiceLocator.getCalendarService().getCalendar(prevPayCalendarEntry.getHrCalendarId());
				TkTimeBlockAggregate prevTimeAggregate = new TkTimeBlockAggregate(prevBlocks, prevPayCalendarEntry, calendar, true);
				List<List<TimeBlock>> dayBlocks = prevTimeAggregate.getDayTimeBlockList();
				List<TimeBlock> previousPeriodLastDayBlocks = dayBlocks.get(dayBlocks.size() - 1);
				// Set back to null if there is nothing in the list.
				if (previousPeriodLastDayBlocks.size() > 0) {
					jobNumberToTimeBlocksPreviousDay = new HashMap<Long, List<TimeBlock.Builder>>();

					for (TimeBlock block : previousPeriodLastDayBlocks) {
						// Job Number to TimeBlock for Last Day of Previous Time
						// Period
						Long jobNumber = block.getJobNumber();
						if (jobNumberToShifts.containsKey(jobNumber)
                                && block.getBeginDateTime().compareTo(block.getEndDateTime()) != 0) {
							// we have a useful timeblock.
							List<TimeBlock.Builder> jblist = jobNumberToTimeBlocksPreviousDay.get(jobNumber);
							if (jblist == null) {
								jblist = new ArrayList<TimeBlock.Builder>();
								jobNumberToTimeBlocksPreviousDay.put(jobNumber, jblist);
							}
							jblist.add(TimeBlock.Builder.create(block));
						}
					}
				}
			}
		}

		return jobNumberToTimeBlocksPreviousDay;
	}

    @Override
    public List<TimeBlock> getTimeblocksOverlappingTimesheetShift(TimesheetDocument timesheetDocument) {
        Map<Long,Set<ShiftDifferentialRule>> jobNumberToShifts = getJobNumberToShiftRuleMap(timesheetDocument);
        DateTimeZone zone = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone(timesheetDocument.getPrincipalId()));
        CalendarEntry calEntry = timesheetDocument.getCalendarEntry();
        Interval beforeCalEntry = null;
        Interval afterCalEntry = null;
        if (jobNumberToShifts.isEmpty()) {
            return Collections.emptyList();
        }
        DateTime calEntryBegin = calEntry.getBeginPeriodLocalDateTime().toDateTime(zone);
        DateTime calEntryEnd = new DateTime(calEntry.getEndPeriodLocalDateTime().toDateTime(zone));
        boolean checkBefore = true;
        TimesheetDocumentHeader previousHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader(timesheetDocument.getPrincipalId(), timesheetDocument.getCalendarEntry().getBeginPeriodFullDateTime());
        if (previousHeader != null
                && (DocumentStatus.FINAL.getCode().equals(previousHeader.getDocumentStatus())
                || DocumentStatus.CANCELED.getCode().equals(previousHeader.getDocumentStatus())
                || DocumentStatus.DISAPPROVED.getCode().equals(previousHeader.getDocumentStatus()))) {
            checkBefore = false;
        }
        for (Map.Entry<Long, Set<ShiftDifferentialRule>> entry : jobNumberToShifts.entrySet()) {
            for (ShiftDifferentialRule rule : entry.getValue()) {
                //Set<String> fromEarnGroup = TkServiceLocator.getEarnCodeGroupService().getEarnCodeListForEarnCodeGroup(rule.getFromEarnGroup(), TKUtils.getTimelessDate(timesheetDocument.getCalendarEntry().getBeginPeriodDateTime()));

                LocalTime ruleStart = new LocalTime(rule.getBeginTime());
                LocalTime ruleEnd = new LocalTime(rule.getEndTime());

                DateTime beginShiftEnd = ruleEnd.toDateTime(calEntryBegin.minusDays(1));
                DateTime beginShiftStart = ruleStart.toDateTime(calEntryBegin.minusDays(1));

                Interval beginShift = adjustShiftDates(beginShiftStart, beginShiftEnd);
                if (beforeCalEntry == null) {
                    beforeCalEntry = new Interval(beginShift.getStart(), calEntryBegin.minusMillis(1));
                } else {
                    if (beginShift.getStart().isBefore(beforeCalEntry.getStart())) {
                        beforeCalEntry = new Interval(beginShift.getStart(), calEntryBegin.minusMillis(1));
                    }
                }

                DateTime endShiftEnd = ruleEnd.toDateTime(calEntryEnd);
                DateTime endShiftBegin = ruleStart.toDateTime(calEntryEnd);

                Interval endShift = adjustShiftDates(endShiftBegin, endShiftEnd);
                if (afterCalEntry == null) {
                    afterCalEntry = new Interval(calEntryEnd.plusMillis(1), endShift.getEnd());
                } else {
                    if (endShift.getEnd().isAfter(afterCalEntry.getEnd())) {
                        afterCalEntry = new Interval(calEntryEnd.plusMillis(1), endShift.getEnd());
                    }
                }
            }
        }
        //get timeblocks that occur within these shifts
        List<TimeBlock> intersectingTimeBlocks = new ArrayList<TimeBlock>();
        TimeBlockService tbService = TkServiceLocator.getTimeBlockService();
        if (checkBefore) {
            if (beforeCalEntry != null) {
                intersectingTimeBlocks.addAll(tbService.getIntersectingTimeBlocks(timesheetDocument.getPrincipalId(), beforeCalEntry.getStart(), beforeCalEntry.getEnd()));
            }
        }
        if (afterCalEntry != null) {
            intersectingTimeBlocks.addAll(tbService.getIntersectingTimeBlocks(timesheetDocument.getPrincipalId(), afterCalEntry.getStart(), afterCalEntry.getEnd()));
        }

        return intersectingTimeBlocks;
    }

    private Interval adjustShiftDates(DateTime start, DateTime end) {
        if (end.isBefore(start) || end.isEqual(start)) {
            end = end.plusDays(1);
        }
        return new Interval(start, end);
    }

    @Override
    public void processShiftDifferentialRules(TimesheetDocument timesheetDocument, TkTimeBlockAggregate aggregate) {
        List<TimeBlock> overlapBlocks = getTimeblocksOverlappingTimesheetShift(timesheetDocument);
        List<TimeBlock> flattenedList = aggregate.getFlattenedTimeBlockList();
        if (CollectionUtils.isNotEmpty(overlapBlocks)) {
            for (TimeBlock overlapTimeBlock : overlapBlocks) {
                if (!flattenedList.contains(overlapTimeBlock)) {
                    flattenedList.add(overlapTimeBlock);
                }
            }
        }
        List<List<TimeBlock>> shiftedBlockDays = processShiftDifferentialRulesForTimeBlocks(timesheetDocument, flattenedList, aggregate.getFlattenedLeaveBlockList(), true);
        //aggregate = new TkTimeBlockAggregate(flattenedList, aggregate.getFlattenedLeaveBlockList(), aggregate.getPayCalendarEntry(), aggregate.getPayCalendar(), true);
        //List<List<TimeBlock>> shiftedBlockDays = processShift(timesheetDocument, aggregate.getDayTimeBlockList(), false);
        if (shiftedBlockDays != null) {
            aggregate.setDayTimeBlockList(shiftedBlockDays);
        }
    }

    public List<List<TimeBlock>> processShiftDifferentialRulesForTimeBlocks(TimesheetDocument timesheetDocument, List<TimeBlock> timeBlocks, List<LeaveBlock> leaveBlocks, boolean modifyPrevious) {
        //build List of day intervals for timeblocks (payperiod + 1 day at beginning and end
        CalendarEntry calEntry = timesheetDocument.getCalendarEntry();
        Calendar cal = HrServiceLocator.getCalendarService().getCalendar(calEntry.getHrCalendarId());
        DateTimeZone zone = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone(timesheetDocument.getPrincipalId()));
        List<Interval> dayIntervals = TKUtils.getDaySpanForCalendarEntry(calEntry, zone);
        Interval firstInterval = dayIntervals.get(0);
        Interval lastInterval = dayIntervals.get(dayIntervals.size()-1);
        if (modifyPrevious) {
            dayIntervals.add(0, new Interval(firstInterval.getStart().minusDays(1), firstInterval.getEnd().minusDays(1)));
        }
        dayIntervals.add(new Interval(lastInterval.getStart().plusDays(1), lastInterval.getEnd().plusDays(1)));

        //build aggregate object (mainly to reuse code)
        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(timeBlocks, leaveBlocks, calEntry, cal, true, dayIntervals);
        return processShift(timesheetDocument, aggregate.getDayTimeBlockList(), modifyPrevious);
        //if (shiftedBlockDays != null) {
        //    aggregate.setDayTimeBlockList(shiftedBlockDays);
        //}
        //return shiftedBlockDays;

    }

    protected List<List<TimeBlock>> processShift(TimesheetDocument timesheetDocument, List<List<TimeBlock>> blockDays, boolean allowPriorPayPeriod) {
        String principalId = timesheetDocument.getPrincipalId();
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
        DateTime periodStartDateTime = timesheetDocument.getCalendarEntry().getBeginPeriodLocalDateTime().toDateTime(zone);
        if (allowPriorPayPeriod) {
            periodStartDateTime = periodStartDateTime.minusDays(1);
        } else {
            //we may need to add extra blocks...

        }

		Map<Long,Set<ShiftDifferentialRule>> jobNumberToShifts = getJobNumberToShiftRuleMap(timesheetDocument);

        List<ShiftCalendarInterval> shiftCalendars = getShiftCalendarIntervals(timesheetDocument, zone);
        for (ShiftCalendarInterval sci : shiftCalendars) {
            for (List<TimeBlock> blocks : blockDays) {
                sci.placeTimeBlocks(blocks, zone);
            }
        }

        // If there are no shift differential rules, we have an early exit.
        if (jobNumberToShifts.isEmpty()) {
            return null;
        }

        //convert to builders
        List<List<TimeBlock.Builder>> builderBlockDays = new ArrayList<List<TimeBlock.Builder>>();
        for (List<TimeBlock> tbList : blockDays) {
            List<TimeBlock.Builder> tempList = new ArrayList<TimeBlock.Builder>();
            for (TimeBlock t : tbList) {
                tempList.add(TimeBlock.Builder.create(t));
            }
            builderBlockDays.add(tempList);
        }

        //process!
        for (ShiftCalendarInterval sci : shiftCalendars) {
            for (Shift shift : sci.getShifts()) {
                shift.processShift();
            }
            Map<TimeBlock, List<ShiftBlock>> timeBlocksForShift = sci.getTimeBlockMap();

            if (MapUtils.isNotEmpty(timeBlocksForShift)) {
                //apply the shift to time detail history
                // the nested for loops look really bad, but the data is pretty limited
                for (List<TimeBlock.Builder> dayTimeBlocks : builderBlockDays) {
                    for (TimeBlock.Builder tb : dayTimeBlocks) {
                        List<ShiftBlock> shiftBlocks = timeBlocksForShift.get(tb.build());
                        if (CollectionUtils.isNotEmpty(shiftBlocks)) {
                            //only dealing with a single shift type at this point, so combine shift hours and apply

                            long duration = 0L;
                            String earnCode = sci.getRule().getEarnCode();
                            for (ShiftBlock sb : shiftBlocks) {
                                duration += sb.getShiftBlockDurationMillis();
                            }
                            BigDecimal durationHours = TKUtils.convertMillisToHours(duration);
                            if (duration > 0) {
                                addPremiumTimeHourDetail(tb, durationHours, earnCode);
                            }
                        }
                    }
                }
            }
        }

        //convert to TimeBlock
        List<List<TimeBlock>> shiftedBlockDays = new ArrayList<List<TimeBlock>>();
        for (List<TimeBlock.Builder> builderList : builderBlockDays) {
            shiftedBlockDays.add(ModelObjectUtils.<TimeBlock>buildImmutableCopy(builderList));
        }
        return shiftedBlockDays;
	}

    @Override
    public List<ShiftDifferentialRule> getShiftDifferentialRules(String userPrincipalId, String location, String hrSalGroup, String payGrade, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist) {
    	List<ShiftDifferentialRule> results = new ArrayList<ShiftDifferentialRule>();
        
    	List<ShiftDifferentialRule> shiftDifferentialRuleObjs = shiftDifferentialRuleDao.getShiftDifferentialRules(location, hrSalGroup, payGrade, fromEffdt, toEffdt, active, showHist);
    
    	for (ShiftDifferentialRule shiftDifferentialRuleObj : shiftDifferentialRuleObjs) {
        	Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), shiftDifferentialRuleObj.getLocation());
        	
        	Map<String, String> permissionDetails = new HashMap<String, String>();
        	permissionDetails.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, KRADServiceLocatorWeb.getDocumentDictionaryService().getMaintenanceDocumentTypeName(ShiftDifferentialRule.class));
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), permissionDetails)
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), permissionDetails, roleQualification)) {
        		results.add(shiftDifferentialRuleObj);
        	}
    	}
    	
    	return results;
    }

	protected void addPremiumTimeHourDetail(TimeBlock.Builder block, BigDecimal hours, String earnCode) {
        List<TimeHourDetail.Builder> details = block.getTimeHourDetails();
        TimeHourDetail.Builder premium = TimeHourDetail.Builder.create();
		premium.setHours(hours);
		premium.setEarnCode(earnCode);
		premium.setTkTimeBlockId(block.getTkTimeBlockId());
        if (!details.contains(premium)) {
            details.add(premium);
	    }
    }

	public void setShiftDifferentialRuleDao(ShiftDifferentialRuleDao shiftDifferentialRuleDao) {
		this.shiftDifferentialRuleDao = shiftDifferentialRuleDao;
	}

	@Override
	public ShiftDifferentialRule getShiftDifferentialRule(String tkShiftDifferentialRuleId) {
		return this.shiftDifferentialRuleDao.findShiftDifferentialRule(tkShiftDifferentialRuleId);
	}

	@Override
	public List<ShiftDifferentialRule> getShiftDifferentalRules(String location, String hrSalGroup, String payGrade, String pyCalendarGroup, LocalDate asOfDate) {
		List<ShiftDifferentialRule> sdrs = new ArrayList<ShiftDifferentialRule>();

		// location, sal group, pay grade, calendar
	    sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, hrSalGroup, payGrade, pyCalendarGroup, asOfDate));

		// location, sal group, *, calendar
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, hrSalGroup, "%", pyCalendarGroup, asOfDate));

		// location, *, pay grade, calendar
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, "%", payGrade, pyCalendarGroup, asOfDate));

		// location, *, *, calendar
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, "%", "%", pyCalendarGroup, asOfDate));

		// *, sal group, pay grade, calendar
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", hrSalGroup, payGrade, pyCalendarGroup, asOfDate));

		// *, sal group, *, calendar
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", hrSalGroup, "%", pyCalendarGroup, asOfDate));

		// *, *, pay grade, calendar
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", "%", payGrade, pyCalendarGroup, asOfDate));

		// *, *, *, calendar
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", "%", "%", pyCalendarGroup, asOfDate));

		// location, sal group, pay grade, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, hrSalGroup, payGrade, "%", asOfDate));

		// location, sal group, *, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, hrSalGroup, "%", "%", asOfDate));

		// location, *, pay grade, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, "%", payGrade, "%", asOfDate));
		
		// location, *, *, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules(location, "%", "%", "%", asOfDate));

		// *, sal group, pay grade, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", hrSalGroup, payGrade, "%", asOfDate));

		// *, sal group, *, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", hrSalGroup, "%", "%", asOfDate));

		// *, *, pay grade, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", "%", payGrade, "%", asOfDate));

		// *, *, *, *
		sdrs.addAll(shiftDifferentialRuleDao.findShiftDifferentialRules("%", "%", "%", "%", asOfDate));

		return sdrs;
	}

	private boolean dayIsRuleActive(DateTime currentDate, ShiftDifferentialRule sdr) {
		boolean active = false;

		switch (currentDate.getDayOfWeek()) {
		case DateTimeConstants.MONDAY:
			active = sdr.isMonday();
			break;
		case DateTimeConstants.TUESDAY:
			active = sdr.isTuesday();
			break;
		case DateTimeConstants.WEDNESDAY:
			active = sdr.isWednesday();
			break;
		case DateTimeConstants.THURSDAY:
			active = sdr.isThursday();
			break;
		case DateTimeConstants.FRIDAY:
			active = sdr.isFriday();
			break;
		case DateTimeConstants.SATURDAY:
			active = sdr.isSaturday();
			break;
		case DateTimeConstants.SUNDAY:
			active = sdr.isSunday();
			break;
		}

		return active;
	}

	@Override
	public void saveOrUpdate(List<ShiftDifferentialRule> shiftDifferentialRules) {
		shiftDifferentialRuleDao.saveOrUpdate(shiftDifferentialRules);
	}

	@Override
	public void saveOrUpdate(ShiftDifferentialRule shiftDifferentialRule) {
		shiftDifferentialRuleDao.saveOrUpdate(shiftDifferentialRule);
	}

    //@Override
    public void checkForNonActiveTimesheetChanges(String currentDocumetId, List<TimeBlock> extraBlocks, List<TimeBlock> newTimeBlocks) {
        Map<String, TimeBlock> nonActiveTimesheetBlocks = new HashMap<String, TimeBlock>();
        Map<String, Boolean> sendNotificationForDoc = new HashMap<String, Boolean>();
        if (currentDocumetId == null) {
            return;
        }
        for (TimeBlock tb : newTimeBlocks) {
            if (!tb.getDocumentId().equals(currentDocumetId)) {
                if (!sendNotificationForDoc.containsKey(tb.getDocumentId())) {
                    DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(tb.getDocumentId());
                    if (DocumentStatus.ENROUTE.equals(documentStatus)) {
                        List<ActionTaken> actions = KewApiServiceLocator.getWorkflowDocumentService().getActionsTaken(tb.getDocumentId());
                        boolean containsApprove = false;
                        for (ActionTaken action : actions) {
                            if (action.getActionTaken().equals(ActionType.APPROVE)) {
                                containsApprove = true;
                                break;
                            }
                        }
                        if(containsApprove) {
                            sendNotificationForDoc.put(tb.getDocumentId(), Boolean.TRUE);
                        } else {
                            sendNotificationForDoc.put(tb.getDocumentId(), Boolean.FALSE);
                        }
                    } else {
                        sendNotificationForDoc.put(tb.getDocumentId(), Boolean.FALSE);
                    }
                }
                nonActiveTimesheetBlocks.put(tb.getTkTimeBlockId(), tb);
            }
        }

        for (TimeBlock extra : extraBlocks) {
            if (sendNotificationForDoc.containsKey(extra.getDocumentId())
                    && sendNotificationForDoc.get(extra.getDocumentId())) {

                if (nonActiveTimesheetBlocks.containsKey(extra.getTkTimeBlockId())) {
                    TimeBlock nonActiveTimesheetBlock = nonActiveTimesheetBlocks.get(extra.getTkTimeBlockId());
                    if (extra.getTimeHourDetails().size() != nonActiveTimesheetBlock.getTimeHourDetails().size()) {
                        //send fyi
                        sendShiftFYI(extra.getDocumentId());
                        break;
                    }
                    for (TimeHourDetail hourDetail : extra.getTimeHourDetails()) {
                        if (!nonActiveTimesheetBlock.getTimeHourDetails().contains(hourDetail)) {
                            //send fyi
                            sendShiftFYI(extra.getDocumentId());
                            break;
                        }
                    }
                }
            }
        }
    }

    protected void sendShiftFYI(String docId) {
        String annotationTemplate = "Document was modified by a rules operation on a subsequent timesheet after it was approved.";
        Set<String> approverIds = new HashSet<String>();
        Set<String> doNotFYIAgainIds = new HashSet<String>();
        // get approver's node name
        List<ActionRequest> rootActionRequests = KewApiServiceLocator.getWorkflowDocumentService().getRootActionRequests(docId);
        for (ActionRequest actionRequest : rootActionRequests) {
            List<ActionRequest> childRequests = actionRequest.getChildRequests();
            for (ActionRequest childRequest : childRequests) {
                if (ActionRequestType.APPROVE.equals(childRequest.getActionRequested())) {
                    approverIds.add(childRequest.getPrincipalId());
                }
                if (ActionRequestType.FYI.equals(childRequest.getActionRequested())
                        && annotationTemplate.equals(childRequest.getAnnotation())
                        && !ActionRequestStatus.DONE.equals(childRequest.getStatus())) {
                    doNotFYIAgainIds.add(childRequest.getPrincipalId());
                }
            }
        }

        for (String principalId : approverIds) {
            if (!doNotFYIAgainIds.contains(principalId)) {
                DocumentActionParameters.Builder documentActionParametersBuilder = DocumentActionParameters.Builder.create(docId, principalId);
                documentActionParametersBuilder.setAnnotation(annotationTemplate);
                AdHocToPrincipal.Builder adhocToPrincipalBuilder = AdHocToPrincipal.Builder.create(ActionRequestType.fromCode(KewApiConstants.ACTION_REQUEST_FYI_REQ), null, principalId);
                adhocToPrincipalBuilder.setResponsibilityDescription("approver");
                KewApiServiceLocator.getWorkflowDocumentActionsService().adHocToPrincipal(documentActionParametersBuilder.build(), adhocToPrincipalBuilder.build());
            }
        }
    }
}
