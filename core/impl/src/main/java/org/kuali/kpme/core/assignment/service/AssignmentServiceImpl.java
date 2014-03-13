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
package org.kuali.kpme.core.assignment.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.assignment.service.AssignmentService;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.api.task.TaskContract;
import org.kuali.kpme.core.api.workarea.WorkArea;
import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.core.assignment.dao.AssignmentDao;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.task.TaskBo;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import java.util.*;

public class AssignmentServiceImpl implements AssignmentService {

    private static final Logger LOG = Logger.getLogger(AssignmentServiceImpl.class);
    private AssignmentDao assignmentDao;

    public AssignmentDao getAssignmentDao() {
        return assignmentDao;
    }

    public void setAssignmentDao(AssignmentDao assignmentDao) {
        this.assignmentDao = assignmentDao;
    }

    protected List<Assignment> convertToImmutable(List<AssignmentBo> bos) {
        return ModelObjectUtils.transform(bos, AssignmentBo.toAssignment);
    }


    @Override
    public List<Assignment> getAssignments(String principalId, LocalDate asOfDate) {
        List<AssignmentBo> assignments;

        if (asOfDate == null) {
            asOfDate = LocalDate.now();
        }

        assignments = assignmentDao.findAssignments(principalId, asOfDate);

        for (AssignmentBo assignment : assignments) {
            assignment = populateAssignment(assignment, asOfDate);
        }

        return convertToImmutable(assignments);
    }

    //@Override
    public List<Assignment> getAssignments(String principalId, LocalDate beginDate, LocalDate endDate) {
        List<AssignmentBo> assignments;

        assignments = assignmentDao.findAssignmentsWithinPeriod(principalId, beginDate, endDate);

        for (AssignmentBo assignment : assignments) {
            assignment = populateAssignment(assignment, assignment.getEffectiveLocalDate());
        }

        return convertToImmutable(assignments);
    }


    @Override
    public List<Assignment> searchAssignments(String userPrincipalId, LocalDate fromEffdt, LocalDate toEffdt, String principalId, String jobNumber,
                                           String dept, String workArea, String active, String showHistory) {
        List<AssignmentBo> results = new ArrayList<AssignmentBo>();
        
    	List<AssignmentBo> assignmentObjs = assignmentDao.searchAssignments(fromEffdt, toEffdt, principalId, jobNumber, dept, workArea, active, showHistory);
    	
    	for (AssignmentBo assignmentObj : assignmentObjs) {
        	String department = assignmentObj.getDept();
        	Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, assignmentObj.getEffectiveLocalDate());
        	String location = departmentObj != null ? departmentObj.getLocation() : null;
        	
        	Map<String, String> roleQualification = new HashMap<String, String>();
        	roleQualification.put(KimConstants.AttributeConstants.PRINCIPAL_ID, userPrincipalId);
        	roleQualification.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
        	roleQualification.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
        	
        	if (!KimApiServiceLocator.getPermissionService().isPermissionDefinedByTemplate(KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>())
    		  || KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(userPrincipalId, KPMENamespace.KPME_WKFLW.getNamespaceCode(),
    				  KPMEPermissionTemplate.VIEW_KPME_RECORD.getPermissionTemplateName(), new HashMap<String, String>(), roleQualification)) {
        		results.add(assignmentObj);
        	}
    	}
    	
    	return convertToImmutable(results);
    }

    public List<Assignment> getAssignmentsByPayEntry(String principalId, CalendarEntryContract payCalendarEntry) {
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
            assignKeyToAssignmentMap.put(AssignmentDescriptionKey.getAssignmentKeyString(assign), assign);
            finalAssignments.add(assign);
        }

        //Compare the begin and end and add any assignments to the end thats are not there
        for (Assignment assign : beginPeriodAssign) {
            String assignKey = AssignmentDescriptionKey.getAssignmentKeyString(assign);
            if (!assignKeyToAssignmentMap.containsKey(assignKey)) {
                finalAssignments.add(assign);
            }
        }

        // Add the assignments within the pay period
        for (Assignment assign : assignsWithPeriod) {
            String assignKey = AssignmentDescriptionKey.getAssignmentKeyString(assign);
            if (!assignKeyToAssignmentMap.containsKey(assignKey)) {
                finalAssignments.add(assign);
            }
        }

        return finalAssignments;

    }
    
    public List<Assignment> getAssignmentsByCalEntryForTimeCalendar(String principalId, CalendarEntryContract payCalendarEntry){
        if (StringUtils.isEmpty(principalId)
                || payCalendarEntry == null) {
            return Collections.emptyList();
        }	
        List<Assignment> assignments = getAssignmentsByPayEntry(principalId, payCalendarEntry);
    	List<Assignment> results = filterAssignments(assignments, HrConstants.FLSA_STATUS_NON_EXEMPT, false);
    	return results;
    }
    
    public List<Assignment> getAssignmentsByCalEntryForLeaveCalendar(String principalId, CalendarEntryContract payCalendarEntry){
        if (StringUtils.isEmpty(principalId)
                || payCalendarEntry == null) {
            return Collections.emptyList();
        }
    	List<Assignment> assignments = getAssignmentsByPayEntry(principalId, payCalendarEntry);
    	List<Assignment> results = filterAssignments(assignments, null, true);
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
        return AssignmentDescriptionKey.get(assignmentKey);
    }

    @Override
    public Map<String, String> getAssignmentDescriptions(Assignment assignment) {
    	Map<String, String> assignmentDescriptions = new LinkedHashMap<String, String>();
        if (assignment == null) {
        	LOG.warn("Assignment is null");
//            throw new RuntimeException("Assignment is null");
        } else { 
	        assignmentDescriptions.putAll(TKUtils.formatAssignmentDescription(assignment));
        }	
        return assignmentDescriptions;

    }

    @Override
    public Assignment getAssignment(String tkAssignmentId) {
        return AssignmentBo.to(assignmentDao.getAssignment(tkAssignmentId));
    }


    @Override
    public List<Assignment> getActiveAssignmentsForWorkArea(Long workArea, LocalDate asOfDate) {
        List<AssignmentBo> assignments = assignmentDao.getActiveAssignmentsInWorkArea(workArea, asOfDate);
        for (AssignmentBo assignment : assignments) {
            assignment = populateAssignment(assignment, asOfDate);
        }
        return convertToImmutable(assignments);
    }

    @Override
    public List<String> getPrincipalIdsInActiveAssignmentsForWorkArea(Long workArea, LocalDate asOfDate) {
        List<AssignmentBo> assignments = assignmentDao.getActiveAssignmentsInWorkArea(workArea, asOfDate);
        Set<String> principalIds = new HashSet<String>();
        for (AssignmentBo assignment : assignments) {
            principalIds.add(assignment.getPrincipalId());
        }
        return new ArrayList<String>(principalIds);
    }

    @Override
    public List<String> getPrincipalIdsInActiveAssignmentsForWorkAreas(List<Long> workAreas, LocalDate asOfDate) {
        if (org.springframework.util.CollectionUtils.isEmpty(workAreas)) {
            return Collections.emptyList();
        }
        List<AssignmentBo> assignments = assignmentDao.getActiveAssignmentsInWorkAreas(workAreas, asOfDate);
        Set<String> principalIds = new HashSet<String>();
        for (AssignmentBo assignment : assignments) {
            principalIds.add(assignment.getPrincipalId());
        }
        return new ArrayList<String>(principalIds);
    }

    @Override
    public List<Assignment> getActiveAssignments(LocalDate asOfDate) {
        return convertToImmutable(assignmentDao.getActiveAssignments(asOfDate));
    }

    protected AssignmentBo populateAssignment(AssignmentBo assignment, LocalDate asOfDate) {
        assignment.setJob(JobBo.from(HrServiceLocator.getJobService().getJob(assignment.getPrincipalId(), assignment.getJobNumber(), asOfDate)));
        assignment.setWorkAreaObj(WorkAreaBo.from(HrServiceLocator.getWorkAreaService().getWorkArea(assignment.getWorkArea(), asOfDate)));
        assignment.setTaskObj(TaskBo.from(HrServiceLocator.getTaskService().getTask(assignment.getTask(), asOfDate)));
        return assignment;
    }

    public Assignment getAssignment(String principalId, AssignmentDescriptionKey key, LocalDate asOfDate) {
        AssignmentBo a = null;

        if (key != null) {
            a = assignmentDao.getAssignment(principalId, key.getJobNumber(), key.getWorkArea(), key.getTask(), asOfDate);
        }
        if (a != null) {
            a = populateAssignment(a, asOfDate);
        }

        return AssignmentBo.to(a);
    }

    @Override
    public Assignment getAssignmentForTargetPrincipal(AssignmentDescriptionKey key, LocalDate asOfDate) {
        AssignmentBo a = null;

        if (key != null) {
            a = assignmentDao.getAssignmentForTargetPrincipal(key.getJobNumber(), key.getWorkArea(), key.getTask(), asOfDate);
        }

        return AssignmentBo.to(a);
    }

    /**
     * KPME-1129 Kagata
     * Get a list of active assignments based on principalId and jobNumber as of a particular date
     */
    @Override
    public List<Assignment> getActiveAssignmentsForJob(String principalId, Long jobNumber, LocalDate asOfDate) {
        List<AssignmentBo> assignments = assignmentDao.getActiveAssignmentsForJob(principalId, jobNumber, asOfDate);

        return convertToImmutable(assignments);
    }
    
    @Override
    public Map<String, String> getAssignmentDescriptionsForAssignments(List<Assignment> assignments) {
    	 Map<String, String> assignmentDescriptions = new LinkedHashMap<String, String>();
         for (Assignment assignment : assignments) {
                 assignmentDescriptions.putAll(TKUtils.formatAssignmentDescription(assignment));
         }
         return assignmentDescriptions;
    }

    @Override
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
        Assignment assign = getAssignmentForTargetPrincipal(desc, beginDate);
        if (assign != null) {
            return assign;
        }

        LOG.warn("no matched assignment found");
        return null;
    }
    
    @Override
    public Assignment getMaxTimestampAssignment(String principalId) {
    	return AssignmentBo.to(assignmentDao.getMaxTimestampAssignment(principalId));
    }
	
	public List<String> getPrincipalIds(List<String> workAreaList, LocalDate effdt, LocalDate startDate, LocalDate endDate) {
		if (CollectionUtils.isEmpty(workAreaList)) {
			return new ArrayList<String>();
		}	
		return assignmentDao.getPrincipalIds(workAreaList, effdt, startDate, endDate);
	}
	
	 public List<Assignment> getAssignments(List<String> workAreaList, LocalDate effdt, LocalDate startDate, LocalDate endDate) {
		if (CollectionUtils.isEmpty(workAreaList)) {
			return Collections.emptyList();
		}	
		return convertToImmutable(assignmentDao.getAssignments(workAreaList, effdt, startDate, endDate));
	}

    @Override
    public String getAssignmentDescription(String principalId, Long jobNumber, Long workArea, Long task, LocalDate asOfDate) {
        StringBuilder builder = new StringBuilder();

        if (jobNumber != null && workArea != null && task != null) {
            JobContract jobObj = HrServiceLocator.getJobService().getJob(principalId, jobNumber, asOfDate);
            WorkArea workAreaObj = HrServiceLocator.getWorkAreaService().getWorkArea(workArea, asOfDate);
            TaskContract taskObj = HrServiceLocator.getTaskService().getTask(task, asOfDate);

            String workAreaDescription = workAreaObj != null ? workAreaObj.getDescription() : StringUtils.EMPTY;
            KualiDecimal compensationRate = jobObj != null ? jobObj.getCompRate() : KualiDecimal.ZERO;
            String department = jobObj != null ? jobObj.getDept() : StringUtils.EMPTY;
            String taskDescription = taskObj != null && !HrConstants.TASK_DEFAULT_DESP.equals(taskObj.getDescription()) ? taskObj.getDescription() : StringUtils.EMPTY;

            builder.append(workAreaDescription).append(" : $").append(compensationRate).append(" Rcd ").append(jobNumber).append(" ").append(department);
            if (StringUtils.isNotBlank(taskDescription)) {
                builder.append(" ").append(taskDescription);
            }
        }

        return builder.toString();
    }

}
