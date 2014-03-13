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
package org.kuali.kpme.tklm.leave.block.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.joda.time.*;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.block.CalendarBlockPermissions;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockService;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlockBo;
import org.kuali.kpme.tklm.leave.block.LeaveBlockHistory;
import org.kuali.kpme.tklm.leave.block.dao.LeaveBlockDao;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;

import java.math.BigDecimal;
import java.util.*;

public class LeaveBlockServiceImpl implements LeaveBlockService {

    private static final Logger LOG = Logger.getLogger(LeaveBlockServiceImpl.class);
    private static final ModelObjectUtils.Transformer<LeaveBlockBo, LeaveBlock> toLeaveBlock =
            new ModelObjectUtils.Transformer<LeaveBlockBo, LeaveBlock>() {
                public LeaveBlock transform(LeaveBlockBo input) {
                    return LeaveBlockBo.to(input);
                };
            };
    private static final ModelObjectUtils.Transformer<LeaveBlock, LeaveBlockBo> toLeaveBlockBo =
            new ModelObjectUtils.Transformer<LeaveBlock, LeaveBlockBo>() {
                public LeaveBlockBo transform(LeaveBlock input) {
                    return LeaveBlockBo.from(input);
                };
            };
    private LeaveBlockDao leaveBlockDao;

    @Override
    public LeaveBlock getLeaveBlock(String leaveBlockId) {
        return LeaveBlockBo.to(getLeaveBlockBo(leaveBlockId));
    }

    protected LeaveBlockBo getLeaveBlockBo(String leaveBlockId) {
        return leaveBlockDao.getLeaveBlock(leaveBlockId);
    }

    public LeaveBlockDao getLeaveBlockDao() {
        return leaveBlockDao;
    }

    public void setLeaveBlockDao(LeaveBlockDao leaveBlockDao) {
        this.leaveBlockDao = leaveBlockDao;
    }

    public List<LeaveBlock> getLeaveBlocksForDocumentId(String documentId) {
        return ModelObjectUtils.transform(leaveBlockDao.getLeaveBlocksForDocumentId(documentId), toLeaveBlock);
    }


    @Override
    public List<LeaveBlock> getLeaveBlocks(String principalId, LocalDate beginDate,
                                   LocalDate endDate) {
        return ModelObjectUtils.transform(getLeaveBlockBos(principalId, beginDate, endDate), toLeaveBlock);
    }

    protected List<LeaveBlockBo> getLeaveBlockBos(String principalId, LocalDate beginDate,
                                           LocalDate endDate) {
        return leaveBlockDao.getLeaveBlocks(principalId, beginDate, endDate);
    }

    @Override
    public List<LeaveBlock> getLeaveBlocksWithAccrualCategory(String principalId, LocalDate beginDate,
                                           LocalDate endDate, String accrualCategory) {
        return ModelObjectUtils.transform(
                leaveBlockDao.getLeaveBlocksWithAccrualCategory(principalId, beginDate, endDate, accrualCategory), toLeaveBlock);
    }

    @Override
    public List<LeaveBlock> getLeaveBlocksWithType(String principalId, LocalDate beginDate,
                                           LocalDate endDate, String leaveBlockType) {
        return ModelObjectUtils.transform(leaveBlockDao.getLeaveBlocksWithType(principalId, beginDate, endDate, leaveBlockType), toLeaveBlock);
    }

    @Override
    public List<LeaveBlock> getLeaveBlocksSinceCarryOver(String principalId, Map<String, LeaveBlock> carryOver, LocalDate endDate, boolean includeAllAccrualCategories) {
        return ModelObjectUtils.transform(leaveBlockDao.getLeaveBlocksSinceCarryOver(principalId, carryOver, endDate, includeAllAccrualCategories), toLeaveBlock);
    }

    @Override
    public Map<String, LeaveBlock> getLastCarryOverBlocks(String principalId, LocalDate asOfDate) {
        if (StringUtils.isEmpty(principalId)) {
            return Collections.emptyMap();
        }
        Map<String, LeaveBlock> carryOver = new HashMap<String, LeaveBlock>();
        Map<String, LeaveBlockBo> fromDao = leaveBlockDao.getLastCarryOverBlocks(principalId, LMConstants.LEAVE_BLOCK_TYPE.CARRY_OVER, asOfDate);
        for (Map.Entry<String, LeaveBlockBo> entry : fromDao.entrySet()) {
            carryOver.put(entry.getKey(), LeaveBlockBo.to(entry.getValue()));
        }
        return carryOver;
    }

    @Override
    public List<LeaveBlock> saveLeaveBlocks(List<LeaveBlock> leaveBlocks) {
        List<LeaveBlockBo> bos = ModelObjectUtils.transform(leaveBlocks, toLeaveBlockBo);
    	return ModelObjectUtils.transform(saveLeaveBlockBos(bos), toLeaveBlock);
    }

    protected List<LeaveBlockBo> saveLeaveBlockBos(List<LeaveBlockBo> leaveBlocks) {
        List<LeaveBlockBo> savedLeaveBlocks = new ArrayList<LeaveBlockBo>();

        Collection<LeaveBlockBo> savedObjects = (Collection<LeaveBlockBo>) KRADServiceLocator.getBusinessObjectService().save(leaveBlocks);

        List<LeaveBlockHistory> leaveBlockHistories = new ArrayList<LeaveBlockHistory>();
        for (LeaveBlockBo leaveBlock : leaveBlocks) {
            LeaveBlockHistory lbh = new LeaveBlockHistory(leaveBlock);
            lbh.setAction(HrConstants.ACTION.ADD);
            leaveBlockHistories.add(lbh);
            HrServiceLocator.getHRPermissionService().updateLeaveBlockPermissions(CalendarBlockPermissions.newInstance(leaveBlock.getLmLeaveBlockId()));
        }

        KRADServiceLocator.getBusinessObjectService().save(leaveBlockHistories);

        savedLeaveBlocks.addAll(savedObjects);

        return savedLeaveBlocks;
    }

    @Override
    public void deleteLeaveBlock(String leaveBlockId, String principalId) {
        LeaveBlockBo leaveBlock = getLeaveBlockBo(leaveBlockId);
        
//        leaveBlock.setPrincipalIdModified(HrContext.getTargetPrincipalId());
//        leaveBlock.setTimestamp(TKUtils.getCurrentTimestamp());

        // Make entry into LeaveBlockHistory table
        LeaveBlockHistory leaveBlockHistory = new LeaveBlockHistory(leaveBlock);
        leaveBlockHistory.setPrincipalIdDeleted(principalId);
        leaveBlockHistory.setTimestampDeleted(TKUtils.getCurrentTimestamp());
        leaveBlockHistory.setAction(HrConstants.ACTION.DELETE);

        // deleting leaveblock
        KRADServiceLocator.getBusinessObjectService().delete(leaveBlock);
        
        // creating history
        KRADServiceLocator.getBusinessObjectService().save(leaveBlockHistory);
        
        
    }

    @Override
    public LeaveBlock saveLeaveBlock(LeaveBlock leaveBlock, String principalId) {
    	LeaveBlockBo savedLeaveBlock = null;
        LeaveBlockBo existingLB = LeaveBlockBo.from(leaveBlock);
    	// first delete and create new entry in the database
    	KRADServiceLocator.getBusinessObjectService().delete(existingLB);
    	
    	// create new 
        existingLB.setLmLeaveBlockId(null);
        existingLB.setTimestamp(TKUtils.getCurrentTimestamp());
        existingLB.setPrincipalIdModified(principalId);
    	savedLeaveBlock = KRADServiceLocator.getBusinessObjectService().save(existingLB);

        // save history
        LeaveBlockHistory lbh = new LeaveBlockHistory(existingLB);
        lbh.setAction(HrConstants.ACTION.MODIFIED);
        LmServiceLocator.getLeaveBlockHistoryService().saveLeaveBlockHistory(lbh);
        return LeaveBlockBo.to(savedLeaveBlock);
    }

    @Override
    public void addLeaveBlocks(DateTime beginDate, DateTime endDate, CalendarEntryContract ce, String selectedEarnCode,
    		BigDecimal hours, String description, Assignment selectedAssignment, String spanningWeeks, String leaveBlockType, String principalId) {
    	
    	DateTimeZone timezone = HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
        DateTime calBeginDateTime = beginDate;
    	DateTime calEndDateTime = endDate;
    	
        if(ce != null) {
        	calBeginDateTime = ce.getBeginPeriodLocalDateTime().toDateTime();
        	calEndDateTime = ce.getEndPeriodLocalDateTime().toDateTime();
        } else {
        	LOG.error("Calendar Entry parameter is null.");
        	return;
//          throw new RuntimeException("Calendar Entry parameter is null.");
        }
        
        Interval calendarInterval = new Interval(calBeginDateTime, calEndDateTime);
       
        // To create the correct interval by the given begin and end dates,
        // we need to plus one day on the end date to include that date
        List<Interval> leaveBlockIntervals = TKUtils.createDaySpan(beginDate.toLocalDate().toDateTimeAtStartOfDay(), endDate.toLocalDate().toDateTimeAtStartOfDay().plusDays(1), TKUtils.getSystemDateTimeZone());

        // need to use beginDate and endDate of the calendar to find all leaveBlocks since LeaveCalendarDocument Id is not always available
        List<LeaveBlockBo> currentLeaveBlocks = getLeaveBlockBos(principalId, calBeginDateTime.toLocalDate(), calEndDateTime.toLocalDate());
    
        // use the current calendar's begin and end date to figure out if this pay period has a leaveDocument
        LeaveCalendarDocumentHeader lcdh = LmServiceLocator.getLeaveCalendarDocumentHeaderService()
        		.getDocumentHeader(principalId, ce.getBeginPeriodLocalDateTime().toDateTime(), ce.getEndPeriodLocalDateTime().toDateTime());
        String docId = lcdh == null ? null : lcdh.getDocumentId();
        
        // TODO: need to integrate with the scheduled timeoff.
    	Interval firstDay = null;
    	DateTime currentDate = beginDate;
        
    	EarnCode earnCodeObj = HrServiceLocator.getEarnCodeService().getEarnCode(selectedEarnCode, endDate.toLocalDate());
        for (Interval leaveBlockInt : leaveBlockIntervals) {
            if (calendarInterval.contains(leaveBlockInt)) {
            	// KPME-1446 if "Include weekends" check box is checked, don't add Sat and Sun to the leaveblock list
            	if (StringUtils.isEmpty(spanningWeeks) && 
                	(leaveBlockInt.getStart().getDayOfWeek() == DateTimeConstants.SATURDAY ||leaveBlockInt.getStart().getDayOfWeek() == DateTimeConstants.SUNDAY)) {
            		
            		// do nothing
            	} else {
            		
            		 // Currently, we store the accrual category value in the leave code table, but store accrual category id in the leaveBlock.
                    // That's why there is a two step server call to get the id. This might be changed in the future.

                    CalendarEntryContract calendarEntry = HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(ce.getHrCalendarId(), new LocalDate().toDateTimeAtStartOfDay());
                    DateTime leaveBlockDate = leaveBlockInt.getStart();
                    String requestStatus = HrConstants.REQUEST_STATUS.USAGE;
                    if (LmServiceLocator.getLeaveApprovalService().isActiveAssignmentFoundOnJobFlsaStatus(principalId, HrConstants.FLSA_STATUS_NON_EXEMPT, true)) {
                    	TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaderForDate(principalId, leaveBlockDate);
                    	if (tdh != null) {
                    		if (DateUtils.isSameDay(leaveBlockDate.toDate(), tdh.getEndDate()) || leaveBlockDate.isAfter(tdh.getEndDateTime())) {
        	            		requestStatus = HrConstants.REQUEST_STATUS.PLANNED;
        	            	}
     	               } else {
     	            	  requestStatus = HrConstants.REQUEST_STATUS.PLANNED;
     	               }
                    } else {
                    	if (DateUtils.isSameDay(leaveBlockDate.toDate(), calendarEntry.getEndPeriodDateTime()) || leaveBlockDate.isAfter(calendarEntry.getEndPeriodFullDateTime())) {
                    		requestStatus = HrConstants.REQUEST_STATUS.PLANNED;
                    	}
                    }
                                                      
                    if(earnCodeObj != null && earnCodeObj.getRecordMethod().equals(HrConstants.RECORD_METHOD.TIME)) {
	                    if (firstDay != null) {
	                    	if(!leaveBlockInt.contains(endDate)){
	                    		currentDate = leaveBlockInt.getStart();
	                    	} else if((leaveBlockInt.getStartMillis() - endDate.getMillis()) != 0){
	                    		
	                            hours = TKUtils.getHoursBetween(leaveBlockInt.getStartMillis(), endDate.getMillis());
	                            if(leaveBlockType.equalsIgnoreCase(LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR)) {
	                              hours = applyInflateMinHoursAndFactor(earnCodeObj, hours);
	                            }
                                hours = negateHoursIfNecessary(leaveBlockType, hours);
	                    		
	                    		LeaveBlockBo leaveBlock = buildLeaveBlock(leaveBlockInt.getStart().toLocalDate(), docId, principalId, selectedEarnCode, hours, description, earnCodeObj.getAccrualCategory(), selectedAssignment, requestStatus, leaveBlockType, leaveBlockInt.getStart(), endDate);
	                            
			                    if (!currentLeaveBlocks.contains(leaveBlock)) {
			                        currentLeaveBlocks.add(leaveBlock);
			                    }
	                    		break;
	                    	}            		
	                    }
	                    if (leaveBlockInt.contains(currentDate)) {
	                    	
	                        firstDay = leaveBlockInt;

	                        if (leaveBlockInt.contains(endDate) || (endDate.getMillis() == leaveBlockInt.getEnd().getMillis())) {

	                        	hours = TKUtils.getHoursBetween(currentDate.getMillis(), endDate.getMillis());
	                        	 if(leaveBlockType.equalsIgnoreCase(LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR)) {
		                              hours = applyInflateMinHoursAndFactor(earnCodeObj, hours);
		                         }
                                hours = negateHoursIfNecessary(leaveBlockType, hours);
	                    		
	                    		LeaveBlockBo leaveBlock = buildLeaveBlock(leaveBlockInt.getStart().toLocalDate(), docId, principalId, selectedEarnCode, hours, description, earnCodeObj.getAccrualCategory(), selectedAssignment, requestStatus, leaveBlockType, currentDate, endDate);
	                            
			                    if (!currentLeaveBlocks.contains(leaveBlock)) {
			                        currentLeaveBlocks.add(leaveBlock);
			                    }

	                            break;
	                            
	                        } else {
	                            // create a leave block that wraps the 24 hr day
	                        	hours = TKUtils.getHoursBetween(currentDate.getMillis(), firstDay.getEndMillis());
	                        	if(leaveBlockType.equalsIgnoreCase(LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR)) {
		                              hours = applyInflateMinHoursAndFactor(earnCodeObj, hours);
		                        }
                                hours = negateHoursIfNecessary(leaveBlockType, hours);
	                    		
	                    		LeaveBlockBo leaveBlock = buildLeaveBlock(leaveBlockInt.getStart().toLocalDate(), docId, principalId, selectedEarnCode, hours, description, earnCodeObj.getAccrualCategory(), selectedAssignment, requestStatus, leaveBlockType, currentDate, firstDay.getEnd());
	                            
			                    if (!currentLeaveBlocks.contains(leaveBlock)) {
			                        currentLeaveBlocks.add(leaveBlock);
			                    }

	                        }
	                    }
                    } else {
                       
                        if(leaveBlockType.equalsIgnoreCase(LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR)) {
                            hours = applyInflateMinHoursAndFactor(earnCodeObj, hours);
                        }
                        hours = negateHoursIfNecessary(leaveBlockType, hours);
		                LeaveBlockBo leaveBlock = buildLeaveBlock(leaveBlockInt.getStart().toLocalDate(), docId, principalId, selectedEarnCode, hours, description, earnCodeObj.getAccrualCategory(),
		                		selectedAssignment, requestStatus, leaveBlockType, null, null);
	                    if (!currentLeaveBlocks.contains(leaveBlock)) {
	                        currentLeaveBlocks.add(leaveBlock);
	                    }
                    }
            	}
            }
        }
        saveLeaveBlockBos(currentLeaveBlocks);
    }

    private BigDecimal negateHoursIfNecessary(String leaveBlockType, BigDecimal hours) {
        if ((leaveBlockType.equals(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR)
                || leaveBlockType.equals((LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR)))
                && BigDecimal.ZERO.compareTo(hours) < 0) {
            hours = hours.negate();
        }
        return hours;
    }
    
    
    public LeaveBlockBo buildLeaveBlock(LocalDate leaveDate, String docId, String principalId, String selectedEarnCode,
    		BigDecimal hours, String description, String accrualCategory, Assignment selectedAssignment, String requestStatus, String leaveBlockType, DateTime beginDateTime, DateTime endDateTime) {
    	
    	LeaveBlockBo leaveBlock = new LeaveBlockBo.Builder(leaveDate, docId, principalId, selectedEarnCode, hours)
        .description(description)
        .principalIdModified(principalId)
        .timestamp(TKUtils.getCurrentTimestamp())
        .scheduleTimeOffId("0")
        .accrualCategory(accrualCategory)
        .workArea(selectedAssignment.getWorkArea())
        .jobNumber(selectedAssignment.getJobNumber())
        .task(selectedAssignment.getTask())
        .requestStatus(requestStatus)
        .leaveBlockType(leaveBlockType)
        .build();
    	
    	leaveBlock.setBeginDateTime(beginDateTime);
        leaveBlock.setEndDateTime(endDateTime);
    	
    	return leaveBlock;
    }
    // KPME-1447
    @Override
    public void updateLeaveBlock(LeaveBlock leaveBlock, String principalId) {
        LeaveBlockBo leaveBlockBo = LeaveBlockBo.from(leaveBlock);
    	//verify that if leave block is usage, leave amount is negative
        leaveBlockBo.setLeaveAmount(negateHoursIfNecessary(leaveBlock.getLeaveBlockType(), leaveBlock.getLeaveAmount()));
        // Make entry into LeaveBlockHistory table
        LeaveBlockHistory leaveBlockHistory = new LeaveBlockHistory(leaveBlockBo);
        leaveBlockHistory.setPrincipalIdDeleted(principalId);
        leaveBlockHistory.setTimestampDeleted(TKUtils.getCurrentTimestamp());
        leaveBlockHistory.setAction(HrConstants.ACTION.MODIFIED);

        KRADServiceLocator.getBusinessObjectService().save(leaveBlockBo);
        
        // creating history
        KRADServiceLocator.getBusinessObjectService().save(leaveBlockHistory); 
    }    

    public static List<Interval> createDaySpan(DateTime beginDateTime, DateTime endDateTime, DateTimeZone zone) {
        beginDateTime = beginDateTime.toDateTime(zone);
        endDateTime = endDateTime.toDateTime(zone);
        List<Interval> dayIntervals = new ArrayList<Interval>();

        DateTime currDateTime = beginDateTime;
        while (currDateTime.isBefore(endDateTime)) {
            DateTime prevDateTime = currDateTime;
            currDateTime = currDateTime.plusDays(1);
            Interval daySpan = new Interval(prevDateTime, currDateTime);
            dayIntervals.add(daySpan);
        }

        return dayIntervals;
    }

	@Override
	public List<LeaveBlock> getLeaveBlocks(String principalId, String leaveBlockType, String requestStatus, LocalDate currentDate) {
		return ModelObjectUtils.transform(leaveBlockDao.getLeaveBlocks(principalId, leaveBlockType, requestStatus, currentDate), toLeaveBlock);
	}

    @Override
    public List<LeaveBlock> getLeaveBlocks(String principalId, String leaveBlockType, String requestStatus, LocalDate beginDate, LocalDate endDate) {
        return ModelObjectUtils.transform(leaveBlockDao.getLeaveBlocks(principalId, leaveBlockType, requestStatus, beginDate, endDate), toLeaveBlock);
    }

	@Override
	public List<LeaveBlock> getLeaveBlocksForDate(String principalId, LocalDate leaveDate) {
		return ModelObjectUtils.transform(leaveBlockDao.getLeaveBlocksForDate(principalId, leaveDate), toLeaveBlock);
	}

	@Override
	public List<LeaveBlock> getNotAccrualGeneratedLeaveBlocksForDate(String principalId, LocalDate leaveDate) {
		return ModelObjectUtils.transform(leaveBlockDao.getNotAccrualGeneratedLeaveBlocksForDate(principalId, leaveDate), toLeaveBlock);
	}

	public List<LeaveBlock> getLeaveBlocksForTimeCalendar(String principalId, LocalDate beginDate, LocalDate endDate, List<String> assignmentKeys) {
		List<LeaveBlock> col = ModelObjectUtils.transform(leaveBlockDao.getCalendarLeaveBlocks(principalId, beginDate, endDate), toLeaveBlock);
		return filterLeaveBlocksForTimeCalendar(col, assignmentKeys);
	}
	
	public List<LeaveBlock> getLeaveBlocksForLeaveCalendar(String principalId, LocalDate beginDate, LocalDate endDate, List<String> assignmentKeys) {
        List<LeaveBlock> col = getLeaveBlocks(principalId, beginDate, endDate);
		return filterLeaveBlocksForLeaveCalendar(col, assignmentKeys);
	}
	
	public List<LeaveBlock> filterLeaveBlocksForTimeCalendar(List<LeaveBlock> lbs, List<String> assignmentKeys) {
		if(CollectionUtils.isEmpty(assignmentKeys)) {
			return lbs;
		}
    	List<LeaveBlock> results = new ArrayList<LeaveBlock> ();
    	for(LeaveBlock lb : lbs) {
    		if(lb != null) {
	    		if (StringUtils.equals(lb.getLeaveBlockType(), LMConstants.LEAVE_BLOCK_TYPE.ACCRUAL_SERVICE)
	    				&& StringUtils.isNotEmpty(lb.getScheduleTimeOffId()) 
	    				&& lb.getLeaveAmount().compareTo(BigDecimal.ZERO) < 0) {
					// display usage leave blocks generated by system scheduled time off
					results.add(lb);
				} else if(StringUtils.isNotEmpty(lb.getAssignmentKey()) && assignmentKeys.contains(lb.getAssignmentKey())) {
	    			if (StringUtils.equals(lb.getLeaveBlockType(), LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR)) {
	    				// only add approved leave blocks that are created from leave calendar
	    				if (StringUtils.equals(lb.getRequestStatus(), HrConstants.REQUEST_STATUS.APPROVED)) {	
	    					results.add(lb);
	    				}
	    			} else if(StringUtils.equals(lb.getLeaveBlockType(), LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR)) {
	    				results.add(lb);
	    			}
	    		}
    		}
    	}
    	return results;
    }

    @Override
	public List<LeaveBlock> filterLeaveBlocksForLeaveCalendar(List<LeaveBlock> lbs, List<String> assignmentKeys) {
		if(CollectionUtils.isEmpty(assignmentKeys)) {
			return lbs;
		}
		List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
		for(LeaveBlock lb : lbs) {
  		   if(lb != null) {
  			   if(lb.getLeaveBlockType().equals(LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR)) {
  				  if(StringUtils.isNotEmpty(lb.getAssignmentKey()) && assignmentKeys.contains(lb.getAssignmentKey())) {
  					  leaveBlocks.add(lb);
  				  }
  			   } else {
  				   leaveBlocks.add(lb);
  			   }
  		   }
  		   
  		  
  	   	}
    	return leaveBlocks;
    }

    @Override
    public void deleteLeaveBlocksForDocumentId(String documentId){
        leaveBlockDao.deleteLeaveBlocksForDocumentId(documentId);
    }
    
    
    @Override
    public List<LeaveBlock> getAccrualGeneratedLeaveBlocks(String principalId, LocalDate beginDate, LocalDate endDate) {
    	return ModelObjectUtils.transform(leaveBlockDao.getAccrualGeneratedLeaveBlocks(principalId, beginDate, endDate), toLeaveBlock);
    }
    
    @Override
    public List<LeaveBlock> getSSTOLeaveBlocks(String principalId, String sstoId, LocalDate accruledDate) {
    	return ModelObjectUtils.transform(leaveBlockDao.getSSTOLeaveBlocks(principalId, sstoId, accruledDate), toLeaveBlock);
    }
    
    @Override
    public List<LeaveBlock> getABELeaveBlocksSinceTime(String principalId, DateTime lastRanDateTime) {
    	return ModelObjectUtils.transform(leaveBlockDao.getABELeaveBlocksSinceTime(principalId, lastRanDateTime), toLeaveBlock);
    }
    
    protected BigDecimal applyInflateMinHoursAndFactor(EarnCode earnCodeObj, BigDecimal blockHours) {
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

	@Override
	public List<LeaveBlock> getTimeCalendarLeaveBlocksForTimeBlockLookup(
			String documentId, String principalId, String userPrincipalId,
			LocalDate fromDate, LocalDate toDate) {
		return ModelObjectUtils.transform(leaveBlockDao.getTimeCalendarLeaveBlocksForTimeBlockLookup(documentId, principalId, userPrincipalId, fromDate, toDate), toLeaveBlock);
	}
	
	@Override
	public List<LeaveBlock> getLeaveBlocksForLookup(
			String documentId, String principalId, String userPrincipalId,
			LocalDate fromDate, LocalDate toDate,String leaveBlockType) {
		return ModelObjectUtils.transform(leaveBlockDao.getLeaveBlocksForLookup(documentId, principalId, userPrincipalId, fromDate, toDate, leaveBlockType), toLeaveBlock);
	}
	
}
