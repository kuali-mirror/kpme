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
package org.kuali.hr.core.assignment.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.hr.core.assignment.Assignment;
import org.kuali.hr.core.assignment.AssignmentDescriptionKey;
import org.kuali.hr.core.assignment.dao.AssignmentDao;
import org.kuali.hr.core.calendar.CalendarEntry;
import org.kuali.hr.core.job.Job;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.common.TKUtils;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.krad.util.GlobalVariables;

public class AssignmentServiceImpl implements AssignmentService {

    private static final Logger LOG = Logger.getLogger(AssignmentServiceImpl.class);
    private AssignmentDao assignmentDao;

    public AssignmentDao getAssignmentDao() {
        return assignmentDao;
    }

    public void setAssignmentDao(AssignmentDao assignmentDao) {
        this.assignmentDao = assignmentDao;
    }


    @Override
    public List<Assignment> getAssignments(String principalId, LocalDate asOfDate) {
        List<Assignment> assignments;

        if (asOfDate == null) {
            asOfDate = LocalDate.now();
        }

        assignments = assignmentDao.findAssignments(principalId, asOfDate);

        for (Assignment assignment : assignments) {
            populateAssignment(assignment, asOfDate);
        }

        return assignments;
    }

    public List<Assignment> getAssignments(String principalId, LocalDate beginDate, LocalDate endDate) {
        List<Assignment> assignments;

        assignments = assignmentDao.findAssignmentsWithinPeriod(principalId, beginDate, endDate);

        for (Assignment assignment : assignments) {
            populateAssignment(assignment, assignment.getEffectiveLocalDate());
        }

        return assignments;
    }


    @Override
    public List<Assignment> searchAssignments(LocalDate fromEffdt, LocalDate toEffdt, String principalId, String jobNumber,
                                           String dept, String workArea, String active, String showHistory) {
        return assignmentDao.searchAssignments(fromEffdt, toEffdt, principalId, jobNumber, dept, workArea, active, showHistory);
    }

    public List<Assignment> getAssignmentsByPayEntry(String principalId, CalendarEntry payCalendarEntry) {
    	DateTime entryEndDate = payCalendarEntry.getEndPeriodLocalDateTime().toDateTime();
        if (entryEndDate.getHourOfDay() == 0) {
            entryEndDate = entryEndDate.minusDays(1);
        }
        List<Assignment> beginPeriodAssign = getAssignments(principalId, payCalendarEntry.getBeginPeriodFullDateTime().toLocalDate());
        List<Assignment> endPeriodAssign = getAssignments(principalId, entryEndDate.toLocalDate());
        List<Assignment> assignsWithPeriod = getAssignments(principalId, payCalendarEntry.getBeginPeriodFullDateTime().toLocalDate(), entryEndDate.toLocalDate());

        List<Assignment> finalAssignments = new ArrayList<Assignment>();
        Map<String, Assignment> assignKeyToAssignmentMap = new HashMap<String, Assignment>();
        for (Assignment assign : endPeriodAssign) {
            assignKeyToAssignmentMap.put(TKUtils.formatAssignmentKey(assign.getJobNumber(), assign.getWorkArea(), assign.getTask()), assign);
            finalAssignments.add(assign);
        }

        //Compare the begin and end and add any assignments to the end thats are not there
        for (Assignment assign : beginPeriodAssign) {
            String assignKey = TKUtils.formatAssignmentKey(assign.getJobNumber(), assign.getWorkArea(), assign.getTask());
            if (!assignKeyToAssignmentMap.containsKey(assignKey)) {
                finalAssignments.add(assign);
            }
        }

        // Add the assignments within the pay period
        for (Assignment assign : assignsWithPeriod) {
            String assignKey = TKUtils.formatAssignmentKey(assign.getJobNumber(), assign.getWorkArea(), assign.getTask());
            if (!assignKeyToAssignmentMap.containsKey(assignKey)) {
                finalAssignments.add(assign);
            }
        }

        return finalAssignments;

    }
    
    public List<Assignment> getAssignmentsByCalEntryForTimeCalendar(String principalId, CalendarEntry payCalendarEntry){
        if (StringUtils.isEmpty(principalId)
                || payCalendarEntry == null) {
            return Collections.emptyList();
        }
        List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByPayEntry(principalId, payCalendarEntry);
    	List<Assignment> results = HrServiceLocator.getAssignmentService().filterAssignments(assignments, TkConstants.FLSA_STATUS_NON_EXEMPT, false);
    	return results;
    }
    
    public List<Assignment> getAssignmentsByCalEntryForLeaveCalendar(String principalId, CalendarEntry payCalendarEntry){
        if (StringUtils.isEmpty(principalId)
                || payCalendarEntry == null) {
            return Collections.emptyList();
        }
    	List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByPayEntry(principalId, payCalendarEntry);
    	List<Assignment> results = HrServiceLocator.getAssignmentService().filterAssignments(assignments, null, true);
    	return results;
    }

    public List<Assignment> filterAssignments(List<Assignment> assignments, String flsaStatus, boolean chkForLeaveEligible) {
    	List<Assignment> results = new ArrayList<Assignment>();
    	for(Assignment assignment : assignments) {
    		boolean flag = false;
    		if(StringUtils.isNotEmpty(flsaStatus)) {
    			if(assignment != null 
		    			&& assignment.getJob() != null 
		    			&& assignment.getJob().getFlsaStatus() != null 
		    			&& assignment.getJob().getFlsaStatus().equalsIgnoreCase(flsaStatus)) {    			
					if(chkForLeaveEligible) {
						if(assignment.getJob().isEligibleForLeave()) {
							flag = true;
						}
					}else {
						flag = true;
					}
	    		} 
    		}else {
    			if(chkForLeaveEligible) {
    				if(assignment != null && assignment.getJob() != null && assignment.getJob().isEligibleForLeave()) {
    					flag = true;
    				}
    			} else {
    				flag = true;
    			}
    		}
    		
			if(flag) {
				results.add(assignment);
			}
    	}
    	
    	return results;
    	
    }
    
    @Override
    public AssignmentDescriptionKey getAssignmentDescriptionKey(String assignmentKey) {
        return new AssignmentDescriptionKey(assignmentKey);
    }

    @Override
    public Map<String, String> getAssignmentDescriptions(TimesheetDocument timesheetDocument, boolean clockOnlyAssignments) {
        Map<String, String> assignmentDescriptions = new LinkedHashMap<String, String>();
    	
    	if (timesheetDocument != null) {
    		List<Assignment> assignments = timesheetDocument.getAssignments();

            for (Assignment assignment : assignments) {
            	String principalId = GlobalVariables.getUserSession().getPrincipalId();

            	if (TkServiceLocator.getTKPermissionService().canViewTimesheetAssignment(principalId, timesheetDocument.getDocumentId(), assignment)) {
	                if (!clockOnlyAssignments || assignment.isSynchronous()) {
	                    assignmentDescriptions.putAll(TKUtils.formatAssignmentDescription(assignment));
	                }
            	}
            }
        }
        
        return assignmentDescriptions;
    }

    @Override
    public Map<String, String> getAssignmentDescriptions(Assignment assignment) {
        if (assignment == null) {
            throw new RuntimeException("Assignment is null");
        }

        Map<String, String> assignmentDescriptions = new LinkedHashMap<String, String>();
        assignmentDescriptions.putAll(TKUtils.formatAssignmentDescription(assignment));

        return assignmentDescriptions;

    }

    @Override
    public Assignment getAssignment(TimesheetDocument timesheetDocument, String assignmentKey) {
        List<Assignment> assignments = timesheetDocument.getAssignments();
        AssignmentDescriptionKey desc = getAssignmentDescriptionKey(assignmentKey);

        for (Assignment assignment : assignments) {
            if (assignment.getJobNumber().compareTo(desc.getJobNumber()) == 0 &&
                    assignment.getWorkArea().compareTo(desc.getWorkArea()) == 0 &&
                    assignment.getTask().compareTo(desc.getTask()) == 0) {
                return assignment;
            }
        }

        //No assignment found so fetch the inactive ones for this payBeginDate
        Assignment assign = getAssignment(desc, timesheetDocument.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate());
        if (assign != null) {
            return assign;
        }


        LOG.warn("no matched assignment found");
        return new Assignment();
    }

    @Override
    public Assignment getAssignment(String tkAssignmentId) {
        return getAssignmentDao().getAssignment(tkAssignmentId);
    }


    @Override
    public List<Assignment> getActiveAssignmentsForWorkArea(Long workArea, LocalDate asOfDate) {
        List<Assignment> assignments = assignmentDao.getActiveAssignmentsInWorkArea(workArea, asOfDate);
        for (Assignment assignment : assignments) {
            populateAssignment(assignment, asOfDate);
        }
        return assignments;
    }

    @Override
    public List<Assignment> getActiveAssignments(LocalDate asOfDate) {
        return assignmentDao.getActiveAssignments(asOfDate);
    }

    private void populateAssignment(Assignment assignment, LocalDate asOfDate) {
        assignment.setJob(HrServiceLocator.getJobService().getJob(assignment.getPrincipalId(), assignment.getJobNumber(), asOfDate));
        assignment.setTimeCollectionRule(TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule(assignment.getJob().getDept(), assignment.getWorkArea(), assignment.getJob().getHrPayType(),asOfDate));
        assignment.setWorkAreaObj(HrServiceLocator.getWorkAreaService().getWorkArea(assignment.getWorkArea(), asOfDate));
        assignment.setDeptLunchRule(TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRule(assignment.getJob().getDept(),
                assignment.getWorkArea(), assignment.getPrincipalId(), assignment.getJobNumber(), asOfDate));
    }

    public Assignment getAssignment(String principalId, AssignmentDescriptionKey key, LocalDate asOfDate) {
        Assignment a = null;

        if (key != null) {
            a = assignmentDao.getAssignment(principalId, key.getJobNumber(), key.getWorkArea(), key.getTask(), asOfDate);
        }

        return a;
    }

    @Override
    public Assignment getAssignment(AssignmentDescriptionKey key, LocalDate asOfDate) {
        Assignment a = null;

        if (key != null) {
            a = assignmentDao.getAssignment(key.getJobNumber(), key.getWorkArea(), key.getTask(), asOfDate);
        }

        return a;
    }

    /**
     * KPME-1129 Kagata
     * Get a list of active assignments based on principalId and jobNumber as of a particular date
     */
    @Override
    public List<Assignment> getActiveAssignmentsForJob(String principalId, Long jobNumber, LocalDate asOfDate) {
        List<Assignment> assignments = assignmentDao.getActiveAssignmentsForJob(principalId, jobNumber, asOfDate);

        return assignments;
    }
    
    @Override
    public Map<String, String> getAssignmentDescriptions(LeaveCalendarDocument lcd) {
        if (lcd == null) {
            throw new RuntimeException("leave document is null.");
        }
        List<Assignment> assignments = lcd.getAssignments();
        return HrServiceLocator.getAssignmentService().getAssignmentDescriptionsForAssignments(assignments);
    }
    
    public Map<String, String> getAssignmentDescriptionsForAssignments(List<Assignment>  assignments) {
    	 Map<String, String> assignmentDescriptions = new LinkedHashMap<String, String>();
         for (Assignment assignment : assignments) {
                 assignmentDescriptions.putAll(TKUtils.formatAssignmentDescription(assignment));
         }
         return assignmentDescriptions;
    }

    @Override
    public Assignment getAssignment(LeaveCalendarDocument leaveCalendarDocument, String assignmentKey) {
        List<Assignment> assignments = leaveCalendarDocument.getAssignments();
        return getAssignment(assignments, assignmentKey, leaveCalendarDocument.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate());
    }
    
    public Assignment getAssignment(List<Assignment> assignments, String assignmentKey, LocalDate beginDate) {
        AssignmentDescriptionKey desc = getAssignmentDescriptionKey(assignmentKey);
    	if (CollectionUtils.isNotEmpty(assignments)) {
            for (Assignment assignment : assignments) {
                if (assignment.getJobNumber().compareTo(desc.getJobNumber()) == 0 &&
                        assignment.getWorkArea().compareTo(desc.getWorkArea()) == 0 &&
                        assignment.getTask().compareTo(desc.getTask()) == 0) {
                    return assignment;
                }
            }
        }

        //No assignment found so fetch the inactive ones for this payBeginDate
        Assignment assign = getAssignment(desc, beginDate);
        if (assign != null) {
            return assign;
        }

        LOG.warn("no matched assignment found");
        return new Assignment();
    }
    
    @Override
    public Assignment getMaxTimestampAssignment(String principalId) {
    	return assignmentDao.getMaxTimestampAssignment(principalId);
    }
    
	public Assignment getAssignmentToApplyScheduledTimeOff(TimesheetDocument timesheetDocument, LocalDate payEndDate) {
		Job primaryJob = HrServiceLocator.getJobService().getPrimaryJob(timesheetDocument.getPrincipalId(), payEndDate);
		for(Assignment assign : timesheetDocument.getAssignments()){
			if(assign.getJobNumber().equals(primaryJob.getJobNumber())){
				return assign;
			}
		}
		return null;
	}
	
	public List<String> getPrincipalIds(List<String> workAreaList, LocalDate effdt, LocalDate startDate, LocalDate endDate) {
		if (CollectionUtils.isEmpty(workAreaList)) {
			return new ArrayList<String>();
		}	
		return assignmentDao.getPrincipalIds(workAreaList, effdt, startDate, endDate);
	}
	
	 public List<Assignment> getAssignments(List<String> workAreaList, LocalDate effdt, LocalDate startDate, LocalDate endDate) {
		if (CollectionUtils.isEmpty(workAreaList)) {
			return new ArrayList<Assignment>();
		}	
		return assignmentDao.getAssignments(workAreaList, effdt, startDate, endDate);
	}
}
