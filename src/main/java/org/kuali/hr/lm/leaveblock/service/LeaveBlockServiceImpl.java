package org.kuali.hr.lm.leaveblock.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.lm.leaveblock.dao.LeaveBlockDao;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class LeaveBlockServiceImpl implements LeaveBlockService {

    private static final Logger LOG = Logger.getLogger(LeaveBlockServiceImpl.class);

    private LeaveBlockDao leaveBlockDao;

    @Override
    public LeaveBlock getLeaveBlock(Long leaveBlockId) {
        return leaveBlockDao.getLeaveBlock(leaveBlockId);
    }

    public LeaveBlockDao getLeaveBlockDao() {
        return leaveBlockDao;
    }

    public void setLeaveBlockDao(LeaveBlockDao leaveBlockDao) {
        this.leaveBlockDao = leaveBlockDao;
    }

    public List<LeaveBlock> getLeaveBlocksForDocumentId(String documentId) {
        return leaveBlockDao.getLeaveBlocksForDocumentId(documentId);
    }

    @Override
    public List<LeaveBlock> getLeaveBlocks(String principalId, Date beginDate,
                                   Date endDate) {
        return leaveBlockDao.getLeaveBlocks(principalId, beginDate, endDate);
    }

    @Override
    public void saveLeaveBlocks(List<LeaveBlock> leaveBlocks) {
        for (LeaveBlock leaveBlock : leaveBlocks) {
        	leaveBlockDao.saveOrUpdate(leaveBlock);
        	// create leaveblock history
        	LeaveBlockHistory lbh = new LeaveBlockHistory(leaveBlock);
        	lbh.setAction(LMConstants.ACTION.ADD);
        	TkServiceLocator.getLeaveBlockHistoryService().saveLeaveBlockHistory(lbh);
        }
    }

    @Override
    public void deleteLeaveBlock(long leaveBlockId) {
        LeaveBlock leaveBlock = TkServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);
        
//        leaveBlock.setPrincipalIdModified(TKContext.getTargetPrincipalId());
//        leaveBlock.setTimestamp(TKUtils.getCurrentTimestamp());
        
        // Make entry into LeaveBlockHistory table
        LeaveBlockHistory leaveBlockHistory = new LeaveBlockHistory(leaveBlock);
        leaveBlockHistory.setPrincipalIdDeleted(TKContext.getPrincipalId());
        leaveBlockHistory.setTimestampDeleted(new Timestamp(System.currentTimeMillis()));
        leaveBlockHistory.setAction(LMConstants.ACTION.DELETE);

        // deleting leaveblock
        KNSServiceLocator.getBusinessObjectService().delete(leaveBlock);
        
        // creating history
        KNSServiceLocator.getBusinessObjectService().save(leaveBlockHistory); 
        
        
    }

    @Override
    public void saveLeaveBlock(LeaveBlock leaveBlock) {

    	// first delete and create new entry in the database
    	KNSServiceLocator.getBusinessObjectService().delete(leaveBlock);
    	
    	// create new 
        leaveBlock.setLmLeaveBlockId(null);
    	leaveBlock.setTimestamp(new Timestamp(System.currentTimeMillis()));
    	leaveBlock.setPrincipalIdModified(TKContext.getPrincipalId());
        KNSServiceLocator.getBusinessObjectService().save(leaveBlock);

        // save history
        LeaveBlockHistory lbh = new LeaveBlockHistory(leaveBlock);
        lbh.setAction(LMConstants.ACTION.MODIFIED);
        TkServiceLocator.getLeaveBlockHistoryService().saveLeaveBlockHistory(lbh);
        
    }

    @Override
    public void addLeaveBlocks(DateTime beginDate, DateTime endDate, CalendarEntries ce, String selectedLeaveCode, BigDecimal hours, String description, Assignment selectedAssignment) {
        String princpalId = TKContext.getTargetPrincipalId();
        DateTimeZone zone = TkServiceLocator.getTimezoneService().getUserTimezoneWithFallback();
        DateTime calBeginDateTime = beginDate;
    	DateTime calEndDateTime = endDate;
        if(ce != null) {
        	calBeginDateTime = ce.getBeginLocalDateTime().toDateTime(zone);
        	calEndDateTime = ce.getEndLocalDateTime().toDateTime(zone);
        }
        Interval calendarInterval = new Interval(calBeginDateTime, calEndDateTime);
        // Currently, we store the accrual category value in the leave code table, but store accrual category id in the leaveBlock.
        // That's why there is a two step server call to get the id. This might be changed in the future.
        LeaveCode leaveCodeObj = TkServiceLocator.getLeaveCodeService().getLeaveCode(selectedLeaveCode);
        AccrualCategory accrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(leaveCodeObj.getAccrualCategory(), TKUtils.getCurrentDate());

        // To create the correct interval by the given begin and end dates,
        // we need to plus one day on the end date to include that date
        List<Interval> leaveBlockIntervals = TKUtils.createDaySpan(beginDate, endDate.plusDays(1), zone);
        // need to use beginDate and endDate of the calendar to find all leaveBlocks since LeaveCalendarDocument Id is not always available
        List<LeaveBlock> currentLeaveBlocks =TkServiceLocator.getLeaveBlockService().getLeaveBlocks(princpalId, calBeginDateTime.toDate(), calEndDateTime.toDate());
    
        // use the current calendar's begin and end date to figure out if this pay period has a leaveDocument
        LeaveCalendarDocumentHeader lcdh = TkServiceLocator.getLeaveCalendarDocumentHeaderService()
        		.getDocumentHeader(princpalId, ce.getBeginLocalDateTime().toDateTime().toDate(), ce.getEndLocalDateTime().toDateTime().toDate());
        String docId = lcdh == null ? null : lcdh.getDocumentId();
        
        // TODO: need to integrate with the scheduled timeoff.
        for (Interval leaveBlockInt : leaveBlockIntervals) {
            if (calendarInterval.contains(leaveBlockInt)) {
                LeaveBlock leaveBlock = new LeaveBlock.Builder(new DateTime(leaveBlockInt.getStartMillis()), docId, princpalId, leaveCodeObj.getLeaveCode(), hours)
                        .description(description)
                        .principalIdModified(princpalId)
                        .timestamp(TKUtils.getCurrentTimestamp())
                        .leaveCodeId(leaveCodeObj.getLmLeaveCodeId())
                        .scheduleTimeOffId("0")
                        .accrualCategoryId(accrualCategory.getLmAccrualCategoryId())
                        .workArea(selectedAssignment.getWorkArea())
                        .jobNumber(selectedAssignment.getJobNumber())
                        .task(selectedAssignment.getTask())
                        .build();
                currentLeaveBlocks.add(leaveBlock);

            }
        }

        TkServiceLocator.getLeaveBlockService().saveLeaveBlocks(currentLeaveBlocks);
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
	public List<LeaveBlock> getLeaveBlocks(String principalId, String requestStatus, Date currentDate) {
		return leaveBlockDao.getLeaveBlocks(principalId, requestStatus, currentDate);
	}
	
	@Override
	public List<LeaveBlock> getLeaveBlocksForDate(String principalId, Date leaveDate) {
		return leaveBlockDao.getLeaveBlocksForDate(principalId, leaveDate);
	}
	
	@Override
	public Double calculateAccrualbalance(Date leaveDate, String accrualCategoryId, String principalId){
		Double totalAccrualBal = 0.0;
		List<LeaveBlock> leaveBlocks = leaveBlockDao.getLeaveBlocks(leaveDate, accrualCategoryId, principalId);
		if(leaveBlocks != null && !leaveBlocks.isEmpty()) {
			for(LeaveBlock lb : leaveBlocks) {
				totalAccrualBal += lb.getLeaveAmount().doubleValue();
			}
		}
		return totalAccrualBal;
				
	}
}
