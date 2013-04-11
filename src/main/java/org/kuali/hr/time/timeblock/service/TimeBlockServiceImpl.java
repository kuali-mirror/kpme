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
package org.kuali.hr.time.timeblock.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.earncodesec.EarnCodeSecurity;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.paytype.PayType;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timeblock.dao.TimeBlockDao;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

public class TimeBlockServiceImpl implements TimeBlockService {

    private static final Logger LOG = Logger.getLogger(TimeBlockServiceImpl.class);
    private TimeBlockDao timeBlockDao;

    public void setTimeBlockDao(TimeBlockDao timeBlockDao) {
        this.timeBlockDao = timeBlockDao;
    }

    //This function is used to build timeblocks that span days
    public List<TimeBlock> buildTimeBlocksSpanDates(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument,
                                                    Timestamp beginTimestamp, Timestamp endTimestamp, BigDecimal hours, BigDecimal amount, 
                                                    Boolean isClockLogCreated, Boolean isLunchDeleted, String spanningWeeks, String userPrincipalId) {
        DateTimeZone zone = TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
        DateTime beginDt = new DateTime(beginTimestamp.getTime(), zone);
        DateTime endDt = beginDt.toLocalDate().toDateTime((new DateTime(endTimestamp.getTime(), zone)).toLocalTime(), zone);
        if (endDt.isBefore(beginDt)) endDt = endDt.plusDays(1);
    	
        List<Interval> dayInt = TKUtils.getDaySpanForCalendarEntry(timesheetDocument.getCalendarEntry());
        TimeBlock firstTimeBlock = new TimeBlock();
        List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
        for (Interval dayIn : dayInt) {
            if (dayIn.contains(beginDt)) {
                if (dayIn.contains(endDt) || dayIn.getEnd().equals(endDt)) {
                	// KPME-1446 if "Include weekends" check box is checked, don't add Sat and Sun to the timeblock list
                	if (StringUtils.isEmpty(spanningWeeks) && 
                		(dayIn.getStart().getDayOfWeek() == DateTimeConstants.SATURDAY ||dayIn.getStart().getDayOfWeek() == DateTimeConstants.SUNDAY)) {
                		// do nothing
                	} else {
                        firstTimeBlock = createTimeBlock(timesheetDocument, beginTimestamp, new Timestamp(endDt.getMillis()), assignment, earnCode, hours, amount, false, isLunchDeleted, userPrincipalId);
                        lstTimeBlocks.add(firstTimeBlock);                		
                	}
                } else {
                    //TODO move this to prerule validation
                    //throw validation error if this case met error
                }
            }
        }

        DateTime endTime = new DateTime(endTimestamp.getTime(), zone);
        DateTime endOfFirstDay = new DateTime(firstTimeBlock.getEndTimestamp(), zone);
        long diffInMillis = endOfFirstDay.minus(beginDt.getMillis()).getMillis();
        DateTime currTime = beginDt.plusDays(1);
        while (currTime.isBefore(endTime) || currTime.isEqual(endTime)) {
        	// KPME-1446 if "Include weekends" check box is checked, don't add Sat and Sun to the timeblock list
        	if (StringUtils.isEmpty(spanningWeeks) && 
        		(currTime.getDayOfWeek() == DateTimeConstants.SATURDAY || currTime.getDayOfWeek() == DateTimeConstants.SUNDAY)) {
        		// do nothing
        	} else {
	            Timestamp begin = new Timestamp(currTime.getMillis());
	            Timestamp end = new Timestamp((currTime.plus(diffInMillis).getMillis()));
	            TimeBlock tb = createTimeBlock(timesheetDocument, begin, end, assignment, earnCode, hours, amount, false, isLunchDeleted, userPrincipalId);
	            lstTimeBlocks.add(tb);
        	}
        	currTime = currTime.plusDays(1);
        }
        return lstTimeBlocks;
    }


    public List<TimeBlock> buildTimeBlocks(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument,
                                           Timestamp beginTimestamp, Timestamp endTimestamp, BigDecimal hours, BigDecimal amount, 
                                           Boolean isClockLogCreated, Boolean isLunchDeleted, String userPrincipalId) {

        //Create 1 or many timeblocks if the span of timeblocks exceed more than one
        //day that is determined by pay period day(24 hrs + period begin date)
        Interval firstDay = null;
        List<Interval> dayIntervals = TKUtils.getDaySpanForCalendarEntry(timesheetDocument.getCalendarEntry());
        List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
        Timestamp beginTemp = beginTimestamp;

        for (Interval dayInt : dayIntervals) {
        	// the time period spans more than one day
            if (firstDay != null) {
            	if(!dayInt.contains(endTimestamp.getTime())){
            		beginTemp = new Timestamp(dayInt.getStartMillis());
            	} else if((dayInt.getStartMillis() - endTimestamp.getTime()) != 0){
            		TimeBlock tb = createTimeBlock(timesheetDocument, new Timestamp(dayInt.getStartMillis()), endTimestamp, assignment, earnCode, hours, amount, isClockLogCreated, isLunchDeleted, userPrincipalId);
            		lstTimeBlocks.add(tb);
            		break;
            	}            		
            }
            if (dayInt.contains(beginTemp.getTime())) {
                firstDay = dayInt;
                // KPME-361
                // added a condition to handle the time block which ends at 12a, e.g. a 10p-12a timeblock
                // this is the same fix as TkTimeBlockAggregate
                if (dayInt.contains(endTimestamp.getTime()) || (endTimestamp.getTime() == dayInt.getEnd().getMillis())) {
                    //create one timeblock if contained in one day interval
                	TimeBlock tb = createTimeBlock(timesheetDocument, beginTemp, endTimestamp, assignment, earnCode, hours, amount, isClockLogCreated, isLunchDeleted, userPrincipalId);
                    tb.setBeginTimestamp(beginTemp);
                    tb.setEndTimestamp(endTimestamp);
                    lstTimeBlocks.add(tb);
                    break;
                } else {
                    // create a timeblock that wraps the 24 hr day
                	TimeBlock tb = createTimeBlock(timesheetDocument, beginTemp, new Timestamp(dayInt.getEndMillis()), assignment, earnCode, hours, amount, isClockLogCreated, isLunchDeleted, userPrincipalId);
                    tb.setBeginTimestamp(beginTemp);
                    tb.setEndTimestamp(new Timestamp(firstDay.getEndMillis()));
                    lstTimeBlocks.add(tb);
                }
            }
        }
        return lstTimeBlocks;
    }

    public void saveTimeBlocks(List<TimeBlock> oldTimeBlocks, List<TimeBlock> newTimeBlocks, String userPrincipalId) {
        List<TimeBlock> alteredTimeBlocks = new ArrayList<TimeBlock>();
        for (TimeBlock tb : newTimeBlocks) {
            boolean persist = true;
            for (TimeBlock tbOld : oldTimeBlocks) {
                if (tb.equals(tbOld)) {
                    persist = false;
                    break;
                }
            }
            if (persist) {
                alteredTimeBlocks.add(tb);
            }
        }
        
        for (TimeBlock timeBlock : alteredTimeBlocks) {
            TkServiceLocator.getTimeHourDetailService().removeTimeHourDetails(timeBlock.getTkTimeBlockId());
            timeBlock.setUserPrincipalId(userPrincipalId);
        }
        
        List<TimeBlock> savedTimeBlocks = (List<TimeBlock>) KRADServiceLocator.getBusinessObjectService().save(alteredTimeBlocks);
        
        for (TimeBlock timeBlock : savedTimeBlocks) {
            timeBlock.setTimeBlockHistories(createTimeBlockHistories(timeBlock, TkConstants.ACTIONS.ADD_TIME_BLOCK));
            KRADServiceLocator.getBusinessObjectService().save(timeBlock.getTimeBlockHistories());
        }
    }

    public void saveTimeBlocks(List<TimeBlock> tbList) {
		 for (TimeBlock tb : tbList) {
	         TkServiceLocator.getTimeHourDetailService().removeTimeHourDetails(tb.getTkTimeBlockId());
	         timeBlockDao.saveOrUpdate(tb);
	         for(TimeBlockHistory tbh : tb.getTimeBlockHistories()){
	        	 TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(tbh);
	         }
	     }
    }
    
    public void updateTimeBlock(TimeBlock tb) {
	         timeBlockDao.saveOrUpdate(tb);
    }


    public TimeBlock createTimeBlock(TimesheetDocument timesheetDocument, Timestamp beginTime, Timestamp endTime, Assignment assignment, String earnCode, BigDecimal hours, BigDecimal amount, Boolean clockLogCreated, Boolean lunchDeleted, String userPrincipalId) {
        DateTimeZone timezone = TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
        EarnCode earnCodeObj = TkServiceLocator.getEarnCodeService().getEarnCode(earnCode, timesheetDocument.getAsOfDate());

        TimeBlock tb = new TimeBlock();
        tb.setDocumentId(timesheetDocument.getDocumentHeader().getDocumentId());
        tb.setPrincipalId(timesheetDocument.getPrincipalId());
        tb.setJobNumber(assignment.getJobNumber());
        tb.setWorkArea(assignment.getWorkArea());
        tb.setTask(assignment.getTask());
        tb.setEarnCode(earnCode);
        tb.setBeginTimestamp(beginTime);
        tb.setBeginTimestampTimezone(timezone.getID());
        tb.setEndTimestamp(endTime);
        tb.setEndTimestampTimezone(timezone.getID());
        tb.setBeginTimeDisplay(new DateTime(tb.getBeginTimestamp(), timezone));
        tb.setEndTimeDisplay(new DateTime(tb.getEndTimestamp(), timezone));
        // only calculate the hours from the time fields if the passed in hour is zero
        if(hours == null || hours.compareTo(BigDecimal.ZERO) == 0) {
        	hours = TKUtils.getHoursBetween(beginTime.getTime(), endTime.getTime());
        }
        tb.setAmount(amount);
        //If earn code has an inflate min hours check if it is greater than zero
        //and compare if the hours specified is less than min hours awarded for this
        //earn code
        if (earnCodeObj.getInflateMinHours() != null) {
            if ((earnCodeObj.getInflateMinHours().compareTo(BigDecimal.ZERO) != 0) &&
                    earnCodeObj.getInflateMinHours().compareTo(hours) > 0) {
                hours = earnCodeObj.getInflateMinHours();
            }
        }
        //If earn code has an inflate factor multiple hours specified by the factor
        if (earnCodeObj.getInflateFactor() != null) {
            if ((earnCodeObj.getInflateFactor().compareTo(new BigDecimal(1.0)) != 0)
            		&& (earnCodeObj.getInflateFactor().compareTo(BigDecimal.ZERO)!= 0) ) {
                hours = earnCodeObj.getInflateFactor().multiply(hours, TkConstants.MATH_CONTEXT).setScale(TkConstants.BIG_DECIMAL_SCALE);
            }
        }

        tb.setEarnCodeType(earnCodeObj.getEarnCodeType());
        tb.setHours(hours);
        tb.setClockLogCreated(clockLogCreated);
        tb.setUserPrincipalId(userPrincipalId);
        tb.setTimestamp(new Timestamp(System.currentTimeMillis()));
        tb.setLunchDeleted(lunchDeleted);

        tb.setTimeHourDetails(this.createTimeHourDetails(tb.getEarnCode(), tb.getHours(), tb.getAmount(), tb.getTkTimeBlockId()));

        return tb;
    }

    public TimeBlock getTimeBlock(String tkTimeBlockId) {
        return timeBlockDao.getTimeBlock(tkTimeBlockId);
    }

    @Override
    public void deleteTimeBlock(TimeBlock timeBlock) {
        timeBlockDao.deleteTimeBlock(timeBlock);

    }

    public void resetTimeHourDetail(List<TimeBlock> origTimeBlocks) {
        for (TimeBlock tb : origTimeBlocks) {
            tb.setTimeHourDetails(createTimeHourDetails(tb.getEarnCode(), tb.getHours(), tb.getAmount(), tb.getTkTimeBlockId()));
            //reset time block history details
            for(TimeBlockHistory tbh : tb.getTimeBlockHistories()) {
            	TkServiceLocator.getTimeBlockHistoryService().addTimeBlockHistoryDetails(tbh,tb);
            }
        }
    }

    private List<TimeHourDetail> createTimeHourDetails(String earnCode, BigDecimal hours, BigDecimal amount, String timeBlockId) {
        List<TimeHourDetail> timeHourDetails = new ArrayList<TimeHourDetail>();

        TimeHourDetail timeHourDetail = new TimeHourDetail();
        timeHourDetail.setEarnCode(earnCode);
        timeHourDetail.setHours(hours);
        timeHourDetail.setAmount(amount);
        timeHourDetail.setTkTimeBlockId(timeBlockId);
        timeHourDetails.add(timeHourDetail);

        return timeHourDetails;
    }

    public List<TimeBlockHistory> createTimeBlockHistories(TimeBlock tb, String actionHistory) {
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
    	List<TimeBlock> timeBlocks = timeBlockDao.getTimeBlocks(documentId);
        TkServiceLocator.getTimezoneService().translateForTimezone(timeBlocks);
        for(TimeBlock tb : timeBlocks) {
            String earnCodeType = TkServiceLocator.getEarnCodeService().getEarnCodeType(tb.getEarnCode(), new java.sql.Date(tb.getBeginTimestamp().getTime()));
            tb.setEarnCodeType(earnCodeType);
        }

        return timeBlocks;
    }

    public List<TimeBlock> getTimeBlocksForAssignment(Assignment assign) {
    	List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();
    	if(assign != null) {
        	timeBlocks = timeBlockDao.getTimeBlocksForAssignment(assign);
    	}
    	TkServiceLocator.getTimezoneService().translateForTimezone(timeBlocks);
    	 for(TimeBlock tb : timeBlocks) {
             String earnCodeType = TkServiceLocator.getEarnCodeService().getEarnCodeType(tb.getEarnCode(), new java.sql.Date(tb.getBeginTimestamp().getTime()));
             tb.setEarnCodeType(earnCodeType);
         }
    	return timeBlocks;
    }


	@Override
	public void deleteTimeBlocksAssociatedWithDocumentId(String documentId) {
		timeBlockDao.deleteTimeBlocksAssociatedWithDocumentId(documentId);
	}

	@Override
	// figure out if the user has permission to edit/delete the time block
	public Boolean isTimeBlockEditable(TimeBlock tb) {
		String userId = GlobalVariables.getUserSession().getPrincipalId();

    	if(userId != null) {

			if(TKUser.isSystemAdmin()) {
				return true;
			}

			if(TKUser.isTimesheetApprover() && TKUser.getApproverWorkAreas().contains(tb.getWorkArea())
					|| TKUser.isTimesheetReviewer() && TKUser.getReviewerWorkAreas().contains(tb.getWorkArea())) {
				Job job = TkServiceLocator.getJobService().getJob(TKContext.getTargetPrincipalId(),tb.getJobNumber(), tb.getEndDate());
				PayType payType = TkServiceLocator.getPayTypeService().getPayType(job.getHrPayType(), tb.getEndDate());
				if(StringUtils.equals(payType.getRegEarnCode(), tb.getEarnCode())){
					return true;
				}

				List<EarnCodeSecurity> deptEarnCodes = TkServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurities(job.getDept(), job.getHrSalGroup(), job.getLocation(), tb.getEndDate());
				for(EarnCodeSecurity dec : deptEarnCodes){
					if(dec.isApprover() && StringUtils.equals(dec.getEarnCode(), tb.getEarnCode())){
						return true;
					}
				}
			}

			if(userId.equals(TKContext.getTargetPrincipalId())) {
				Job job = TkServiceLocator.getJobService().getJob(TKContext.getTargetPrincipalId(),tb.getJobNumber(), tb.getEndDate());
				PayType payType = TkServiceLocator.getPayTypeService().getPayType(job.getHrPayType(), tb.getEndDate());
				if(StringUtils.equals(payType.getRegEarnCode(), tb.getEarnCode())){
					return true;
				}

				List<EarnCodeSecurity> deptEarnCodes = TkServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurities(job.getDept(), job.getHrSalGroup(), job.getLocation(), tb.getEndDate());
				for(EarnCodeSecurity dec : deptEarnCodes){
					if(dec.isEmployee() && StringUtils.equals(dec.getEarnCode(), tb.getEarnCode())){
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
		return timeBlockDao.getTimeBlocksForClockLogEndId(tkClockLogId);
	}
	@Override
	public List<TimeBlock> getTimeBlocksForClockLogBeginId(String tkClockLogId) {
		return timeBlockDao.getTimeBlocksForClockLogBeginId(tkClockLogId);
	}

	public List<TimeBlock> getTimeBlocks(){
		return timeBlockDao.getTimeBlocks();
	}
	
	public List<TimeBlock> getLatestEndTimestampForEarnCode(String earnCode){
		return timeBlockDao.getLatestEndTimestampForEarnCode(earnCode);
	}

    @Override
    public List<TimeBlock> getOvernightTimeBlocks(String clockLogEndId) {
        return timeBlockDao.getOvernightTimeBlocks(clockLogEndId);
    }
    
    @Override
    public void deleteLunchDeduction(String tkTimeHourDetailId) {
        TimeHourDetail thd = TkServiceLocator.getTimeHourDetailService().getTimeHourDetail(tkTimeHourDetailId);
        TimeBlock tb = getTimeBlock(thd.getTkTimeBlockId());
        
        // mark the lunch deleted as Y
        tb.setLunchDeleted(true);
        // save the change
        timeBlockDao.saveOrUpdate(tb);
        // remove the related time hour detail row with the lunch deduction
        TkServiceLocator.getTimeHourDetailService().removeTimeHourDetail(thd.getTkTimeHourDetailId());
    }
    @Override
    public List<TimeBlock> getTimeBlocksWithEarnCode(String earnCode, Date effDate) {
    	return timeBlockDao.getTimeBlocksWithEarnCode(earnCode, effDate);
    }
}
