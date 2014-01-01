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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.assignment.service.AssignmentService;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.assignment.dao.AssignmentDao;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

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
    public List<Assignment> searchAssignments(String userPrincipalId, LocalDate fromEffdt, LocalDate toEffdt, String principalId, String jobNumber,
                                           String dept, String workArea, String active, String showHistory) {
        List<Assignment> results = new ArrayList<Assignment>();
        
    	List<Assignment> assignmentObjs = assignmentDao.searchAssignments(fromEffdt, toEffdt, principalId, jobNumber, dept, workArea, active, showHistory);
    	
    	for (Assignment assignmentObj : assignmentObjs) {
        	String department = assignmentObj.getDept();
        	DepartmentContract departmentObj = HrServiceLocator.getDepartmentService().getDepartmentWithoutRoles(department, assignmentObj.getEffectiveLocalDate());
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
    	
    	return results;
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
        List<Assignment> assignments = (List<Assignment>)HrServiceLocator.getAssignmentService().getAssignmentsByPayEntry(principalId, payCalendarEntry);
    	List<Assignment> results =(List<Assignment>) HrServiceLocator.getAssignmentService().filterAssignments(assignments, HrConstants.FLSA_STATUS_NON_EXEMPT, false);
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

    public List<Assignment> filterAssignments(List<? extends AssignmentContract> assignments, String flsaStatus, boolean chkForLeaveEligible) {
    	List<Assignment> results = new ArrayList<Assignment>();
    	for(AssignmentContract assignment : assignments) {
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
				results.add((Assignment)assignment);
			}
    	}
    	
    	return results;
    	
    }
    
    @Override
    public AssignmentDescriptionKey getAssignmentDescriptionKey(String assignmentKey) {
        return AssignmentDescriptionKey.get(assignmentKey);
    }

    @Override
    public Map<String, String> getAssignmentDescriptions(AssignmentContract assignment) {
    	Map<String, String> assignmentDescriptions = new LinkedHashMap<String, String>();
        if (assignment == null) {
        	LOG.warn("Assignment is null");
//            throw new RuntimeException("Assignment is null");
        } else { 
	        assignmentDescriptions.putAll(TKUtils.formatAssignmentDescription((Assignment) assignment));
        }	
        return assignmentDescriptions;

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
    public List<String> getPrincipalIdsInActiveAssignmentsForWorkArea(Long workArea, LocalDate asOfDate) {
        List<Assignment> assignments = assignmentDao.getActiveAssignmentsInWorkArea(workArea, asOfDate);
        Set<String> principalIds = new HashSet<String>();
        for (Assignment assignment : assignments) {
            principalIds.add(assignment.getPrincipalId());
        }
        return new ArrayList<String>(principalIds);
    }

    @Override
    public List<String> getPrincipalIdsInActiveAssignmentsForWorkAreas(List<Long> workAreas, LocalDate asOfDate) {
        if (org.springframework.util.CollectionUtils.isEmpty(workAreas)) {
            return Collections.emptyList();
        }
        List<Assignment> assignments = assignmentDao.getActiveAssignmentsInWorkAreas(workAreas, asOfDate);
        Set<String> principalIds = new HashSet<String>();
        for (Assignment assignment : assignments) {
            principalIds.add(assignment.getPrincipalId());
        }
        return new ArrayList<String>(principalIds);
    }

    @Override
    public List<Assignment> getActiveAssignments(LocalDate asOfDate) {
        return assignmentDao.getActiveAssignments(asOfDate);
    }

    private void populateAssignment(Assignment assignment, LocalDate asOfDate) {
        assignment.setJob((Job)HrServiceLocator.getJobService().getJob(assignment.getPrincipalId(), assignment.getJobNumber(), asOfDate));
        assignment.setWorkAreaObj((WorkArea)HrServiceLocator.getWorkAreaService().getWorkArea(assignment.getWorkArea(), asOfDate));
    }

    public Assignment getAssignment(String principalId, AssignmentDescriptionKey key, LocalDate asOfDate) {
        Assignment a = null;

        if (key != null) {
            a = assignmentDao.getAssignment(principalId, key.getJobNumber(), key.getWorkArea(), key.getTask(), asOfDate);
        }

        return a;
    }

    @Override
    public Assignment getAssignmentForTargetPrincipal(AssignmentDescriptionKey key, LocalDate asOfDate) {
        Assignment a = null;

        if (key != null) {
            a = assignmentDao.getAssignmentForTargetPrincipal(key.getJobNumber(), key.getWorkArea(), key.getTask(), asOfDate);
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
    public Map<String, String> getAssignmentDescriptionsForAssignments(List<? extends AssignmentContract> assignments) {
    	 Map<String, String> assignmentDescriptions = new LinkedHashMap<String, String>();
         for (AssignmentContract assignment : assignments) {
                 assignmentDescriptions.putAll(TKUtils.formatAssignmentDescription(assignment));
         }
         return assignmentDescriptions;
    }
    
    public Assignment getAssignment(List<? extends AssignmentContract> assignments, String assignmentKey, LocalDate beginDate) {
        AssignmentDescriptionKey desc = getAssignmentDescriptionKey(assignmentKey);
    	if (CollectionUtils.isNotEmpty(assignments)) {
            for (AssignmentContract assignment : assignments) {
                if (assignment.getJobNumber().compareTo(desc.getJobNumber()) == 0 &&
                        assignment.getWorkArea().compareTo(desc.getWorkArea()) == 0 &&
                        assignment.getTask().compareTo(desc.getTask()) == 0) {
                    return (Assignment)assignment;
                }
            }
        }

        //No assignment found so fetch the inactive ones for this payBeginDate
        Assignment assign = getAssignmentForTargetPrincipal(desc, beginDate);
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
