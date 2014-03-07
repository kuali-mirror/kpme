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
package org.kuali.kpme.tklm.time.timeblock.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.*;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.block.CalendarBlockPermissions;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.api.earncode.security.EarnCodeSecurityContract;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.paytype.PayType;
import org.kuali.kpme.core.api.paytype.PayTypeContract;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributesContract;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockService;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.timeoff.SystemScheduledTimeOff;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockHistory;
import org.kuali.kpme.tklm.time.timeblock.dao.TimeBlockDao;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetailBo;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

import java.math.BigDecimal;
import java.util.*;

public class TimeBlockServiceImpl implements TimeBlockService {

    private static final Logger LOG = Logger.getLogger(TimeBlockServiceImpl.class);
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
    private TimeBlockDao timeBlockDao;

    public void setTimeBlockDao(TimeBlockDao timeBlockDao) {
        this.timeBlockDao = timeBlockDao;
    }

    //This function is used to build timeblocks that span days
    @Override
    public List<TimeBlock> buildTimeBlocksSpanDates(String principalId, CalendarEntryContract calendarEntry, AssignmentContract assignment, String earnCode, String documentId,
                                                    DateTime beginDateTime, DateTime endDateTime, BigDecimal hours, BigDecimal amount, 
                                                    Boolean getClockLogCreated, Boolean getLunchDeleted, String spanningWeeks, String userPrincipalId,
                                                    String clockLogBeginId, String clockLogEndId) {
        DateTimeZone zone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
        DateTime beginDt = beginDateTime.withZone(zone);
        DateTime endDt = beginDt.toLocalDate().toDateTime(endDateTime.withZone(zone).toLocalTime(), zone);
        if (endDt.isBefore(beginDt)) endDt = endDt.plusDays(1);
    	
        List<Interval> dayInt = TKUtils.getDaySpanForCalendarEntry(calendarEntry);
        TimeBlockBo firstTimeBlock = new TimeBlockBo();
        List<TimeBlockBo> lstTimeBlocks = new ArrayList<TimeBlockBo>();
        
        DateTime endOfFirstDay = null; // KPME-2568
        long diffInMillis = 0; // KPME-2568
        
        for (Interval dayIn : dayInt) {
            if (dayIn.contains(beginDt)) {
                if (dayIn.contains(endDt) || dayIn.getEnd().equals(endDt)) {
                	// KPME-1446 if "Include weekends" check box is checked, don't add Sat and Sun to the timeblock list
                	if (StringUtils.isEmpty(spanningWeeks) && 
                		(dayIn.getStart().getDayOfWeek() == DateTimeConstants.SATURDAY ||dayIn.getStart().getDayOfWeek() == DateTimeConstants.SUNDAY)) {
                		// Get difference in millis for the next time block - KPME-2568
                		endOfFirstDay = endDt.withZone(zone);
                		diffInMillis = endOfFirstDay.minus(beginDt.getMillis()).getMillis();
                	} else {
                        firstTimeBlock = TimeBlockBo.from(createTimeBlock(principalId, documentId, beginDateTime, endDt, assignment, earnCode,
                                hours, amount, getClockLogCreated, getLunchDeleted, userPrincipalId, clockLogBeginId, clockLogEndId));
                        lstTimeBlocks.add(firstTimeBlock);                		
                	}
                } else {
                    //TODO move this to prerule validation
                    //throw validation error if this case met error
                }
            }
        }

        DateTime endTime = endDateTime.withZone(zone);
        if (firstTimeBlock.getEndDateTime() != null) { // KPME-2568
        	endOfFirstDay = firstTimeBlock.getEndDateTime().withZone(zone);
        	diffInMillis = endOfFirstDay.minus(beginDt.getMillis()).getMillis();
        }
        DateTime currTime = beginDt.plusDays(1);
        while (currTime.isBefore(endTime) || currTime.isEqual(endTime)) {
        	// KPME-1446 if "Include weekends" check box is checked, don't add Sat and Sun to the timeblock list
        	if (StringUtils.isEmpty(spanningWeeks) && 
        		(currTime.getDayOfWeek() == DateTimeConstants.SATURDAY || currTime.getDayOfWeek() == DateTimeConstants.SUNDAY)) {
        		// do nothing
        	} else {
	            TimeBlockBo tb = TimeBlockBo.from(createTimeBlock(principalId, documentId, currTime, currTime.plus(diffInMillis), assignment, earnCode,
                        hours, amount, getClockLogCreated, getLunchDeleted, userPrincipalId, clockLogBeginId, clockLogEndId));
	            lstTimeBlocks.add(tb);
        	}
        	currTime = currTime.plusDays(1);
        }
        return ModelObjectUtils.transform(lstTimeBlocks, toTimeBlock);
    }


    public List<TimeBlock> buildTimeBlocks(String principalId, CalendarEntryContract calendarEntry, AssignmentContract assignment, String earnCode, String documentId,
    									   DateTime beginDateTime, DateTime endDateTime, BigDecimal hours, BigDecimal amount, 
                                           Boolean getClockLogCreated, Boolean getLunchDeleted, String userPrincipalId,
                                           String clockLogBeginId, String clockLogEndId) {

        //Create 1 or many timeblocks if the span of timeblocks exceed more than one
        //day that is determined by pay period day(24 hrs + period begin date)
        Interval firstDay = null;
        DateTimeZone userTimeZone = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone(principalId));
        if(userTimeZone == null)
        	userTimeZone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
        
        List<Interval> dayIntervals = TKUtils.getDaySpanForCalendarEntry(calendarEntry, userTimeZone);
//        List<Interval> dayIntervals = TKUtils.getDaySpanForCalendarEntry(timesheetDocument.getCalendarEntry());
        List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
        DateTime currentDateTime = beginDateTime;

        for (Interval dayInt : dayIntervals) {
        	// the time period spans more than one day
            if (firstDay != null) {
            	if(!dayInt.contains(endDateTime)){
            		currentDateTime = dayInt.getStart();
            	} else if((dayInt.getStartMillis() - endDateTime.getMillis()) != 0){
            		TimeBlock tb = createTimeBlock(principalId,documentId, dayInt.getStart(), endDateTime, assignment, earnCode,
                            hours, amount, getClockLogCreated, getLunchDeleted, userPrincipalId, clockLogBeginId, clockLogEndId);
            		lstTimeBlocks.add(tb);
            		break;
            	}            		
            }
            if (dayInt.contains(currentDateTime)) {
                firstDay = dayInt;
                // KPME-361
                // added a condition to handle the time block which ends at 12a, e.g. a 10p-12a timeblock
                // this is the same fix as TkTimeBlockAggregate
                if (dayInt.contains(endDateTime) || (endDateTime.getMillis() == dayInt.getEnd().getMillis())) {
                    //create one timeblock if contained in one day interval
                	TimeBlock.Builder tb = TimeBlock.Builder.create(createTimeBlock(principalId, documentId, currentDateTime, endDateTime, assignment, earnCode,
                            hours, amount, getClockLogCreated, getLunchDeleted, userPrincipalId, clockLogBeginId, clockLogEndId));
                    tb.setBeginDateTime(currentDateTime);
                    tb.setEndDateTime(endDateTime);
                    lstTimeBlocks.add(tb.build());
                    break;
                } else {
                    // create a timeblock that wraps the 24 hr day
                	TimeBlock.Builder tb = TimeBlock.Builder.create(createTimeBlock(principalId, documentId, currentDateTime, dayInt.getEnd(), assignment, earnCode,
                            hours, amount, getClockLogCreated, getLunchDeleted, userPrincipalId, clockLogBeginId, clockLogEndId));
                    tb.setBeginDateTime(currentDateTime);
                    tb.setEndDateTime(firstDay.getEnd());
                    lstTimeBlocks.add(tb.build());
                }
            }
        }
        return lstTimeBlocks;
    }

    @Override
    public List<TimeBlock> saveOrUpdateTimeBlocks(List<TimeBlock> oldTimeBlocks, List<TimeBlock> newTimeBlocks, String userPrincipalId) {
        List<TimeBlockBo> alteredTimeBlocks = new ArrayList<TimeBlockBo>();

        List<TimeBlockBo> oldTimeBlockBos = ModelObjectUtils.transform(oldTimeBlocks, toTimeBlockBo);
        List<TimeBlockBo> newTimeBlockBos = ModelObjectUtils.transform(newTimeBlocks, toTimeBlockBo);
        for (TimeBlockBo tb : newTimeBlockBos) {
            boolean persist = true;
            for (TimeBlockBo tbOld : oldTimeBlockBos) {
                HrServiceLocator.getHRPermissionService().updateTimeBlockPermissions(CalendarBlockPermissions.newInstance(tbOld.getTkTimeBlockId()));
                if (tb.equals(tbOld)) {
                    persist = false;
                    break;
                }
            }
            if (persist) {
                alteredTimeBlocks.add(tb);
            }
        }
        
        Set<String> timeBlockIds = new HashSet<String>();
        
        for (TimeBlockBo timeBlock : alteredTimeBlocks) {
        	if(timeBlock.getTkTimeBlockId() != null) {
        		timeBlockIds.add(timeBlock.getTkTimeBlockId());
        	}
        	TkServiceLocator.getTimeHourDetailService().removeTimeHourDetails(timeBlock.getTkTimeBlockId());
            timeBlock.setUserPrincipalId(userPrincipalId);
        }

        //List<TimeBlockBo> savedTimeBlocks = timeBlockDao.saveOrUpdate(alteredTimeBlocks);
        List<TimeBlockBo> savedTimeBlocks = (List<TimeBlockBo>) KRADServiceLocator.getBusinessObjectService().save(alteredTimeBlocks);

        for (TimeBlockBo timeBlock : savedTimeBlocks) {
        	if(!timeBlockIds.contains(timeBlock.getTkTimeBlockId())) {
	            timeBlock.setTimeBlockHistories(createTimeBlockHistories(timeBlock, TkConstants.ACTIONS.ADD_TIME_BLOCK));
	            KRADServiceLocator.getBusinessObjectService().save(timeBlock.getTimeBlockHistories());
        	} else {
	            timeBlock.setTimeBlockHistories(createTimeBlockHistories(timeBlock, TkConstants.ACTIONS.UPDATE_TIME_BLOCK));
	            KRADServiceLocator.getBusinessObjectService().save(timeBlock.getTimeBlockHistories());
        	}
        }
        return ModelObjectUtils.transform(savedTimeBlocks, toTimeBlock);
    }

    @Override
    public List<TimeBlock> saveTimeBlocks(List<TimeBlock> tbList) {
        List<TimeBlockBo> savedTimeBlocks = new ArrayList<TimeBlockBo>();
		 for (TimeBlock tb : tbList) {
             TimeBlockBo tbBo = TimeBlockBo.from(tb);
             if (StringUtils.isNotEmpty(tb.getTkTimeBlockId())) {
                 HrServiceLocator.getHRPermissionService().updateTimeBlockPermissions(CalendarBlockPermissions.newInstance(tb.getTkTimeBlockId()));
             }
	         TkServiceLocator.getTimeHourDetailService().removeTimeHourDetails(tb.getTkTimeBlockId());
             savedTimeBlocks.add(KRADServiceLocator.getBusinessObjectService().save(tbBo));
	         for(TimeBlockHistory tbh : tbBo.getTimeBlockHistories()){
	        	 TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(tbh);
	         }
	     }

        return ModelObjectUtils.transform(savedTimeBlocks, toTimeBlock);
    }

    @Override
    public TimeBlock updateTimeBlock(TimeBlock tb) {
        if (tb == null) {
            return null;
        }
	    return TimeBlockBo.to(KRADServiceLocator.getBusinessObjectService().save(TimeBlockBo.from(tb)));
    }


    @Override
    public TimeBlock createTimeBlock(String principalId, String documentId, DateTime beginDateTime, DateTime endDateTime, AssignmentContract assignment, String earnCode, BigDecimal hours, BigDecimal amount, Boolean clockLogCreated, Boolean lunchDeleted, String userPrincipalId) {
        DateTimeZone timezone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
        EarnCode earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(earnCode, beginDateTime.toLocalDate());

        TimeBlockBo tb = new TimeBlockBo();
        tb.setDocumentId(documentId);
        tb.setPrincipalId(principalId);
        tb.setJobNumber(assignment.getJobNumber());
        tb.setWorkArea(assignment.getWorkArea());
        tb.setTask(assignment.getTask());
        tb.setEarnCode(earnCode);
        tb.setBeginDateTime(beginDateTime);
        tb.setEndDateTime(endDateTime);
        tb.setBeginTimeDisplay(tb.getBeginDateTime().withZone(timezone));
        tb.setEndTimeDisplay(tb.getEndDateTime().withZone(timezone));
        // only calculate the hours from the time fields if the passed in hour is zero
        if(hours == null || hours.compareTo(BigDecimal.ZERO) == 0) {
        	hours = TKUtils.getHoursBetween(beginDateTime.getMillis(), endDateTime.getMillis());
        }
        tb.setAmount(amount);
        //If earn code has an inflate min hours check if it is greater than zero
        //and compare if the hours specified is less than min hours awarded for this
        //earn code

        tb.setEarnCodeType(earnCodeObj.getEarnCodeType());
        tb.setHours(hours);
        tb.setClockLogCreated(clockLogCreated);
        tb.setUserPrincipalId(userPrincipalId);
        tb.setTimestamp(TKUtils.getCurrentTimestamp());
        tb.setLunchDeleted(lunchDeleted);

        tb.setTimeHourDetails(this.createTimeHourDetails(earnCodeObj, hours, amount, tb.getTkTimeBlockId(),true));

        return TimeBlockBo.to(tb);
    }

    protected TimeBlock createTimeBlock(String principalId, String documentId, DateTime beginDateTime, DateTime endDateTime,
                                     AssignmentContract assignment, String earnCode, BigDecimal hours, BigDecimal amount,
                                     Boolean clockLogCreated, Boolean lunchDeleted, String userPrincipalId,
                                     String clockLogBeginId, String clockLogEndId) {
        TimeBlock.Builder tb = TimeBlock.Builder.create(createTimeBlock(principalId, documentId, beginDateTime, endDateTime, assignment, earnCode, hours, amount, clockLogCreated,
                lunchDeleted, userPrincipalId));
        TimeBlockBo bo = TimeBlockBo.from(tb.build());
        bo.setClockLogBeginId(clockLogBeginId);
        bo.setClockLogEndId(clockLogEndId);

        return TimeBlockBo.to(bo);
    }

    @Override
    public TimeBlock getTimeBlock(String tkTimeBlockId) {
        return TimeBlockBo.to(getTimeBlockBo(tkTimeBlockId));
    }

    protected TimeBlockBo getTimeBlockBo(String tkTimeBlockId) {
        return timeBlockDao.getTimeBlock(tkTimeBlockId);
    }

    @Override
    public void deleteTimeBlock(TimeBlock timeBlock) {
        KRADServiceLocator.getBusinessObjectService().delete(TimeBlockBo.from(timeBlock));
    }

    @Override
    public List<TimeBlock> resetTimeHourDetail(List<TimeBlock> origTimeBlocks) {
        List<TimeBlockBo> originalBos = ModelObjectUtils.transform(origTimeBlocks, toTimeBlockBo);
    	Collections.sort(originalBos,new Comparator<TimeBlockBo>() {

			@Override
			public int compare(TimeBlockBo o1, TimeBlockBo o2) {
				return o1.getEndDateTime().compareTo(o2.getEndDateTime().toInstant());
			}
    		
    	});
    	TimeBlockBo previousTimeBlock = null;
        for (TimeBlockBo tb : originalBos) {
        	EarnCode earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(tb.getEarnCode(), tb.getBeginDateTime().toLocalDate());
            if (tb.getBeginTime() != null && tb.getEndTime() != null && StringUtils.equals(tb.getEarnCodeType(), HrConstants.EARN_CODE_TIME)) {
                BigDecimal hours = TKUtils.getHoursBetween(tb.getBeginTime().getMillisOfDay(), tb.getEndTime().getMillisOfDay());

                //If earn code has an inflate min hours check if it is greater than zero
                //and compare if the hours specified is less than min hours awarded for this
                //earn code
                if(previousTimeBlock != null && StringUtils.equals(earnCodeObj.getEarnCode(),previousTimeBlock.getEarnCode()) &&
                		(tb.getBeginTime().getMillisOfDay() - previousTimeBlock.getEndTime().getMillisOfDay() == 0L)) {
                	List<TimeHourDetailBo> newDetails = new ArrayList<TimeHourDetailBo>();
        			BigDecimal prevTimeBlockHours = TKUtils.getHoursBetween(previousTimeBlock.getBeginTime().getMillisOfDay(), previousTimeBlock.getEndTime().getMillisOfDay());
        			previousTimeBlock.setHours(prevTimeBlockHours);
        			BigDecimal cummulativeHours = prevTimeBlockHours.add(hours, HrConstants.MATH_CONTEXT);
        			//remove any inflation done when resetting the previous time block's hours.
        			previousTimeBlock.setTimeHourDetails(this.createTimeHourDetails(earnCodeObj, prevTimeBlockHours, previousTimeBlock.getAmount(), previousTimeBlock.getTkTimeBlockId(),false));
                	
                	if (earnCodeObj.getInflateMinHours() != null) {
                		if ((earnCodeObj.getInflateMinHours().compareTo(BigDecimal.ZERO) != 0) &&
                				earnCodeObj.getInflateMinHours().compareTo(cummulativeHours) > 0) {
                			//if previous timeblock has no gap then assume its one block if the same earn code and divide inflated hours accordingly

                			//add previous time block's hours to this time blocks hours. If the cummulative hours is less than the value for inflate min,
                			//create time hour detail with hours equal to the hours needed to reach inflate min plus the hours for this time block.
                			if(earnCodeObj.getInflateMinHours().compareTo(cummulativeHours) > 0) {
                				//apply inflations to the cummulative hours
                				newDetails = this.createTimeHourDetails(earnCodeObj,cummulativeHours,tb.getAmount(),tb.getTkTimeBlockId(),false);
                				TimeHourDetailBo detail = newDetails.get(0);
                				//this detail's hours will be the cummulative inflated hours less the hours in previous time block detail's hours.
                				detail.setHours(earnCodeObj.getInflateMinHours().subtract(prevTimeBlockHours));
                				newDetails.clear();
                				newDetails.add(detail);
                				tb.setTimeHourDetails(newDetails);
                			}
                		}
                	}
                    //the hours for this time block may be under the inflate factor, but when combined with the hours from first "part"
                    //of their shift, it is over, thus no inflation should be requested of the time hour detail's hours.
                    if(earnCodeObj.getInflateFactor() != null && earnCodeObj.getInflateFactor().compareTo(BigDecimal.ZERO) != 0) {
                    	//apply inflate factor separately so as to not inflate "hours" if it is under the minimum
                    	TimeHourDetailBo detail = new TimeHourDetailBo();
                    	if(newDetails.isEmpty()) {
                    		//populate some default values...
                    		newDetails = this.createTimeHourDetails(earnCodeObj,hours,tb.getAmount(),tb.getTkTimeBlockId(),false);
                    	}
                		detail = newDetails.get(0);
                    	BigDecimal newHours = detail.getHours().multiply(earnCodeObj.getInflateFactor(),HrConstants.MATH_CONTEXT);
                    	detail.setHours(newHours);
                    	newDetails.clear();
                    	newDetails.add(detail);
                    	tb.setTimeHourDetails(newDetails);
                    	newDetails = previousTimeBlock.getTimeHourDetails();
                    	detail = newDetails.get(0);
                    	detail.setHours(prevTimeBlockHours.multiply(earnCodeObj.getInflateFactor(),HrConstants.MATH_CONTEXT));
                    	newDetails.clear();
                    	newDetails.add(detail);
                    	previousTimeBlock.setTimeHourDetails(newDetails);
                    }
                }
                else {
                	tb.setTimeHourDetails(this.createTimeHourDetails(earnCodeObj,tb.getHours(),tb.getAmount(),tb.getTkTimeBlockId(),true));
                }

                tb.setHours(hours);
            } else {
            	// create new time hour details for earn codes of other types
            	tb.setTimeHourDetails(this.createTimeHourDetails(earnCodeObj,tb.getHours(),tb.getAmount(),tb.getTkTimeBlockId(),true));
            }
            //reset time block history details
            for(TimeBlockHistory tbh : tb.getTimeBlockHistories()) {
            	TkServiceLocator.getTimeBlockHistoryService().addTimeBlockHistoryDetails(tbh,tb);
            }
            previousTimeBlock = tb;
        }
        return ModelObjectUtils.transform(originalBos, toTimeBlock);
    }

    private List<TimeHourDetailBo> createTimeHourDetails(EarnCode earnCode, BigDecimal hours, BigDecimal amount, String timeBlockId, boolean inflate) {
        List<TimeHourDetailBo> timeHourDetails = new ArrayList<TimeHourDetailBo>();

        TimeHourDetailBo timeHourDetail = new TimeHourDetailBo();
        timeHourDetail.setEarnCode(earnCode.getEarnCode());
        if(inflate) {
        	timeHourDetail.setHours(this.applyInflateMinHoursAndFactor(earnCode, hours));
        }
        else {
        	timeHourDetail.setHours(hours);
        }
        timeHourDetail.setAmount(amount);
        timeHourDetail.setTkTimeBlockId(timeBlockId);
        timeHourDetails.add(timeHourDetail);

        return timeHourDetails;
    }

    protected List<TimeBlockHistory> createTimeBlockHistories(TimeBlockBo tb, String actionHistory) {
        List<TimeBlockHistory> tbhs = new ArrayList<TimeBlockHistory>();

        TimeBlockHistory tbh = new TimeBlockHistory(tb);
        tbh.setActionHistory(actionHistory);
        // add time block history details to this time block history
        TkServiceLocator.getTimeBlockHistoryService().addTimeBlockHistoryDetails(tbh, tb);
        
        tbhs.add(tbh);

        return tbhs;
    }
    
    // This method now translates time based on timezone settings.
    //
    public List<TimeBlock> getTimeBlocks(String documentId) {
    	List<TimeBlockBo> timeBlocks = timeBlockDao.getTimeBlocks(documentId);
        TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
            DateTimeZone timezone = DateTimeZone.forID(HrServiceLocator.getTimezoneService().getUserTimezone(tdh.getPrincipalId()));
            if (timezone == null)
             timezone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
        for(TimeBlockBo tb : timeBlocks) {
            String earnCodeType = HrServiceLocator.getEarnCodeService().getEarnCodeType(tb.getEarnCode(), tb.getBeginDateTime().toLocalDate());
            tb.setEarnCodeType(earnCodeType);
			if(ObjectUtils.equals(timezone, TKUtils.getSystemDateTimeZone())){
				tb.setBeginTimeDisplay(tb.getBeginDateTime());
				tb.setEndTimeDisplay(tb.getEndDateTime());
			}
			else {
				tb.setBeginTimeDisplay(tb.getBeginDateTime().withZone(timezone));
				tb.setEndTimeDisplay(tb.getEndDateTime().withZone(timezone));
			}
        }

        return ModelObjectUtils.transform(timeBlocks, toTimeBlock);
    }

/*    @Override
    public List<TimeBlock> getTimeBlocksForAssignment(AssignmentContract assign) {
    	List<TimeBlockBo> timeBlocks = new ArrayList<TimeBlockBo>();
    	if(assign != null) {
        	timeBlocks = timeBlockDao.getTimeBlocksForAssignment(assign);
    	}
        DateTimeZone timezone = HrServiceLocator.getTimezoneService().getTargetUserTimezoneWithFallback();
    	 for(TimeBlockBo tb : timeBlocks) {
             String earnCodeType = HrServiceLocator.getEarnCodeService().getEarnCodeType(tb.getEarnCode(), tb.getBeginDateTime().toLocalDate());
             tb.setEarnCodeType(earnCodeType);
 			if(ObjectUtils.equals(timezone, TKUtils.getSystemDateTimeZone())){
				tb.setBeginTimeDisplay(tb.getBeginDateTime());
				tb.setEndTimeDisplay(tb.getEndDateTime());
			}
			else {
				tb.setBeginTimeDisplay(tb.getBeginDateTime().withZone(timezone));
				tb.setEndTimeDisplay(tb.getEndDateTime().withZone(timezone));
			}
         }
    	return ModelObjectUtils.transform(timeBlocks, toTimeBlock);
    }*/


	@Override
	public void deleteTimeBlocksAssociatedWithDocumentId(String documentId) {
		timeBlockDao.deleteTimeBlocksAssociatedWithDocumentId(documentId);
	}

	@Override
	// figure out if the user has permission to edit/delete the time block
	public Boolean getTimeBlockEditable(TimeBlock timeBlock) {
		String userId = GlobalVariables.getUserSession().getPrincipalId();

    	if(userId != null) {

			if(HrContext.isSystemAdmin()) {
				return true;
			}
            DateTime date = LocalDate.now().toDateTimeAtStartOfDay();
        	if (HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(userId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), timeBlock.getWorkArea(), date)
        			|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(userId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), timeBlock.getWorkArea(), date)
        			|| HrServiceLocator.getKPMERoleService().principalHasRoleInWorkArea(userId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), timeBlock.getWorkArea(), date)) {

				JobContract job = HrServiceLocator.getJobService().getJob(HrContext.getTargetPrincipalId(),timeBlock.getJobNumber(), timeBlock.getEndDateTime().toLocalDate());
				PayType payType = HrServiceLocator.getPayTypeService().getPayType(job.getHrPayType(), timeBlock.getEndDateTime().toLocalDate());
				if(StringUtils.equals(payType.getRegEarnCode(), timeBlock.getEarnCode())){
					return true;
				}

				List<? extends EarnCodeSecurityContract> deptEarnCodes = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurities(job.getDept(), job.getHrSalGroup(), job.getLocation(), timeBlock.getEndDateTime().toLocalDate());
				for(EarnCodeSecurityContract dec : deptEarnCodes){
					if(dec.isApprover() && StringUtils.equals(dec.getEarnCode(), timeBlock.getEarnCode())){
						return true;
					}
				}
			}

			if(userId.equals(HrContext.getTargetPrincipalId())) {
				JobContract job = HrServiceLocator.getJobService().getJob(HrContext.getTargetPrincipalId(),timeBlock.getJobNumber(), timeBlock.getEndDateTime().toLocalDate());
				PayType payType = HrServiceLocator.getPayTypeService().getPayType(job.getHrPayType(), timeBlock.getEndDateTime().toLocalDate());
				if(StringUtils.equals(payType.getRegEarnCode(), timeBlock.getEarnCode())){
					return true;
				}

				List<? extends EarnCodeSecurityContract> deptEarnCodes = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurities(job.getDept(), job.getHrSalGroup(), job.getLocation(), timeBlock.getEndDateTime().toLocalDate());
				for(EarnCodeSecurityContract dec : deptEarnCodes){
					if(dec.isEmployee() && StringUtils.equals(dec.getEarnCode(), timeBlock.getEarnCode())){
						return true;
					}
				}
				// if the user is the creator of this time block
			}


		}
		return false;
	}

	@Override
	public List<TimeBlock> getTimeBlocksForClockLogEndId(String tkClockLogId) {
		return ModelObjectUtils.transform(timeBlockDao.getTimeBlocksForClockLogEndId(tkClockLogId), toTimeBlock);
	}
	@Override
	public List<TimeBlock> getTimeBlocksForClockLogBeginId(String tkClockLogId) {
		return ModelObjectUtils.transform(timeBlockDao.getTimeBlocksForClockLogBeginId(tkClockLogId), toTimeBlock);
	}

	@Override
	public List<TimeBlock> getLatestEndTimestampForEarnCode(String earnCode){
		return ModelObjectUtils.transform(timeBlockDao.getLatestEndTimestampForEarnCode(earnCode), toTimeBlock);
	}

    @Override
    public List<TimeBlock> getOvernightTimeBlocks(String clockLogEndId) {
        return ModelObjectUtils.transform(timeBlockDao.getOvernightTimeBlocks(clockLogEndId), toTimeBlock);
    }

    @Override
    public boolean isOvernightTimeBlock(String clockLogEndId) {
        List<TimeBlockBo> timeBlockBos = timeBlockDao.getOvernightTimeBlocks(clockLogEndId);
        return CollectionUtils.isNotEmpty(timeBlockBos)
                && timeBlockBos.size() >= 2;
    }
    
    @Override
    public void deleteLunchDeduction(String tkTimeHourDetailId) {
        TimeHourDetail thd = TkServiceLocator.getTimeHourDetailService().getTimeHourDetail(tkTimeHourDetailId);
        TimeBlockBo tb = getTimeBlockBo(thd.getTkTimeBlockId());
        //clear any timeblock permissions
        HrServiceLocator.getHRPermissionService().updateTimeBlockPermissions(CalendarBlockPermissions.newInstance(tb.getTkTimeBlockId()));
        // mark the lunch deleted as Y
        tb.setLunchDeleted(true);
        // save the change
        KRADServiceLocator.getBusinessObjectService().save(tb);
        // remove the related time hour detail row with the lunch deduction
        TkServiceLocator.getTimeHourDetailService().removeTimeHourDetail(thd.getTkTimeHourDetailId());
    }
    /*@Override
    public List<TimeBlock> getTimeBlocksWithEarnCode(String earnCode, DateTime effDate) {
    	return ModelObjectUtils.transform(timeBlockDao.getTimeBlocksWithEarnCode(earnCode, effDate), toTimeBlock);
    }*/

    private BigDecimal applyInflateMinHoursAndFactor(EarnCode earnCodeObj, BigDecimal blockHours) {
        if(earnCodeObj != null) {
            //If earn code has an inflate min hours check if it is greater than zero
            //and compare if the hours specified is less than min hours awarded for this
            //earn code
            if (earnCodeObj.getInflateMinHours() != null) {
                if ((earnCodeObj.getInflateMinHours().compareTo(BigDecimal.ZERO) != 0) &&
                        earnCodeObj.getInflateMinHours().compareTo(blockHours) > 0) {
                    blockHours = earnCodeObj.getInflateMinHours();
                }
            }
            //If earn code has an inflate factor multiple hours specified by the factor
            if (earnCodeObj.getInflateFactor() != null) {
                if ((earnCodeObj.getInflateFactor().compareTo(new BigDecimal(1.0)) != 0)
                        && (earnCodeObj.getInflateFactor().compareTo(BigDecimal.ZERO)!= 0) ) {
                    blockHours = earnCodeObj.getInflateFactor().multiply(blockHours, HrConstants.MATH_CONTEXT).setScale(HrConstants.BIG_DECIMAL_SCALE);
                }
            }
        }
        return blockHours;
    }

	/*@Override
	public List<TimeBlock> getTimeBlocksForLookup(String documentId,
			String principalId, String userPrincipalId, LocalDate fromDate,
			LocalDate toDate) {
		return ModelObjectUtils.transform(timeBlockDao.getTimeBlocksForLookup(documentId, principalId, userPrincipalId, fromDate, toDate), toTimeBlock);
	}*/
	
	@Override
	public List<TimeBlock> applyHolidayPremiumEarnCode(String principalId, List<AssignmentContract> timeAssignments, List<TimeBlock> timeBlockList) {

		if(CollectionUtils.isNotEmpty(timeBlockList)) {
			Set<String> regularEarnCodes = new HashSet<String>();
	        for(AssignmentContract assign : timeAssignments) {
	            regularEarnCodes.add(assign.getJob().getPayTypeObj().getRegEarnCode());
	        }
            List<TimeBlockBo> bos = ModelObjectUtils.transform(timeBlockList, toTimeBlockBo);
			for(TimeBlockBo tb : bos) {
		        EarnCode earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(tb.getEarnCode(), tb.getBeginDateTime().toLocalDate());
                AssignmentContract assignment = TKUtils.getAssignmentWithKey(timeAssignments, AssignmentDescriptionKey.get(tb.getAssignmentKey()));
		        List<TimeHourDetailBo> timeHourDetails = new ArrayList<TimeHourDetailBo>();
				if(earnCodeObj.getCountsAsRegularPay().equals("Y") || regularEarnCodes.contains(earnCodeObj.getEarnCode())) {
			        if(assignment != null && assignment.getJob() != null && assignment.getJob().isEligibleForLeave()) {
			        	PrincipalHRAttributesContract principalCalendar = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, tb.getBeginDateTime().toLocalDate());
			    		if(principalCalendar != null && StringUtils.isNotEmpty(principalCalendar.getLeavePlan())) {
			    			SystemScheduledTimeOff sstoHoliday = LmServiceLocator.getSysSchTimeOffService().getSystemScheduledTimeOffByDate(principalCalendar.getLeavePlan(), tb.getBeginDateTime().toLocalDate());
			    			if(sstoHoliday != null && sstoHoliday.getPremiumHoliday().equalsIgnoreCase("Y") && StringUtils.isNotEmpty(sstoHoliday.getPremiumEarnCode())) {
			    				EarnCode premiumEarnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(sstoHoliday.getPremiumEarnCode(), tb.getBeginDateTime().toLocalDate());
			    				if(premiumEarnCodeObj != null) {
			    					timeHourDetails.addAll(this.createTimeHourDetails(earnCodeObj, BigDecimal.ZERO, tb.getAmount(), tb.getTkTimeBlockId(),true));
			    					timeHourDetails.addAll(this.createTimeHourDetails(premiumEarnCodeObj, tb.getHours(), tb.getAmount(), tb.getTkTimeBlockId(),true));
			    					tb.setTimeHourDetails(timeHourDetails);
			    				}
			    			}
			    		}
			        }
				}
			} // end of for loop
            return ModelObjectUtils.transform(bos, toTimeBlock);
		}
        return timeBlockList;
	}
}
