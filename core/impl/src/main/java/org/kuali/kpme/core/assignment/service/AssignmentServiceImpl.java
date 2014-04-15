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
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.assignment.service.AssignmentService;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.permission.KPMEPermissionTemplate;
import org.kuali.kpme.core.api.task.TaskContract;
import org.kuali.kpme.core.api.workarea.WorkArea;
import org.kuali.kpme.core.assignment.AssignmentBo;
import org.kuali.kpme.core.assignment.dao.AssignmentDao;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.task.TaskBo;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class AssignmentServiceImpl implements AssignmentService {

    private static final Logger LOG = Logger.getLogger(AssignmentServiceImpl.class);
    private AssignmentDao assignmentDao;

    public void setAssignmentDao(AssignmentDao assignmentDao) {
        this.assignmentDao = assignmentDao;
    }

    protected List<Assignment> convertToImmutable(List<AssignmentBo> bos) {
        return ModelObjectUtils.transform(bos, AssignmentBo.toAssignment);
    }


    @Override
    public List<Assignment> getAssignments(String principalId, LocalDate asOfDate) {
        List<Assignment> assigns = new ArrayList<Assignment>();
        if (asOfDate == null) {
            asOfDate = LocalDate.now();
        }

        List<AssignmentBo> assignments = assignmentDao.findAssignments(principalId, asOfDate);

        for (AssignmentBo assignment : assignments) {
            assigns.add(AssignmentBo.to(populateAssignment(assignment, asOfDate)));
        }

        return assigns;
    }

    //@Override
    public List<Assignment> getAssignments(String principalId, LocalDate beginDate, LocalDate endDate) {
        List<AssignmentBo> assignments = assignmentDao.findAssignmentsWithinPeriod(principalId, beginDate, endDate);
        List<Assignment> assigns = new ArrayList<Assignment>();
        for (AssignmentBo assignment : assignments) {
            assigns.add(AssignmentBo.to(populateAssignment(assignment, assignment.getEffectiveLocalDate())));
        }

        return assigns;
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

    public Map<LocalDate, List<Assignment>> getAssignmentsByCalEntryForTimeCalendar(String principalId, CalendarEntry payCalendarEntry){
        if (StringUtils.isEmpty(principalId)
                || payCalendarEntry == null) {
            return Collections.emptyMap();
        }

        return getAssignmentHistoryBetweenDaysInternal(principalId, payCalendarEntry.getBeginPeriodFullDateTime().toLocalDate(), payCalendarEntry.getEndPeriodFullDateTime().toLocalDate(), HrConstants.FLSA_STATUS_NON_EXEMPT, false);
    }

    public List<Assignment> getAllAssignmentsByCalEntryForTimeCalendar(String principalId, CalendarEntry payCalendarEntry){
        Map<LocalDate, List<Assignment>> history = getAssignmentsByCalEntryForTimeCalendar(principalId, payCalendarEntry);
        return getUniqueAssignments(history);
    }
    
    public Map<LocalDate, List<Assignment>> getAssignmentsByCalEntryForLeaveCalendar(String principalId, CalendarEntry payCalendarEntry){
        if (StringUtils.isEmpty(principalId)
                || payCalendarEntry == null) {
            return Collections.emptyMap();
        }
        return getAssignmentHistoryBetweenDaysInternal(principalId, payCalendarEntry.getBeginPeriodFullDateTime().toLocalDate(), payCalendarEntry.getEndPeriodFullDateTime().toLocalDate(), null, true);
    }

    public List<Assignment> getAllAssignmentsByCalEntryForLeaveCalendar(String principalId, CalendarEntry payCalendarEntry){
        Map<LocalDate, List<Assignment>> history = getAssignmentsByCalEntryForLeaveCalendar(principalId, payCalendarEntry);
        return getUniqueAssignments(history);
    }

    protected List<Assignment> getUniqueAssignments(Map<LocalDate, List<Assignment>> history) {
        if (MapUtils.isEmpty(history)) {
            return Collections.emptyList();
        }
        Set<Assignment> allAssignments = new HashSet<Assignment>();
        for (List<Assignment> assignList : history.values()) {
            allAssignments.addAll(assignList);
        }
        return new ArrayList<Assignment>(allAssignments);
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
        List<Assignment> assigns = new ArrayList<Assignment>(assignments.size());
        for (AssignmentBo assignment : assignments) {
            assigns.add(AssignmentBo.to(populateAssignment(assignment, asOfDate)));
        }
        return assigns;
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
        assignment.populateAssignmentDescription(asOfDate);
        return assignment;
    }

    public Assignment getAssignment(String principalId, AssignmentDescriptionKey key, LocalDate asOfDate) {
        AssignmentBo a = null;

        if (key != null) {
            a = assignmentDao.getAssignment(principalId, key.getGroupKeyCode(), key.getJobNumber(), key.getWorkArea(), key.getTask(), asOfDate);
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
            a = assignmentDao.getAssignmentForTargetPrincipal(key.getGroupKeyCode(), key.getJobNumber(), key.getWorkArea(), key.getTask(), asOfDate);
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
    public String getAssignmentDescription(String principalId, String groupKeyCode, Long jobNumber, Long workArea, Long task, LocalDate asOfDate) {
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

    @Override
    public String getAssignmentDescriptionForAssignment(Assignment assignment, LocalDate asOfDate) {
        return getAssignmentDescription(assignment.getGroupKeyCode(), assignment.getPrincipalId(), assignment.getJobNumber(), assignment.getWorkArea(), assignment.getTask(), asOfDate);
    }



    /*@Override
    public Map<String, String> getAssignmentDescriptionsForDay(String principalId, boolean clockOnlyAssignments, LocalDate asOfDate ) {
        Map<LocalDate, List<Assignment>> history = getAssignmentHistoryBetweenDays(principalId, asOfDate, asOfDate);
        List<Assignment> assignments = history.get(asOfDate);
        if (CollectionUtils.isEmpty(assignments)) {
            return Collections.emptyMap();
        }
        return filterAssignmentKeys(assignments, clockOnlyAssignments, asOfDate);
    }*/

    @Override
    public Map<LocalDate, List<Assignment>> getAssignmentHistoryBetweenDays(String principalId, LocalDate beginDate, LocalDate endDate) {
        return getAssignmentHistoryBetweenDaysInternal(principalId, beginDate, endDate, HrConstants.FLSA_STATUS_NON_EXEMPT, false);
    }

    @Override
    public List<Assignment> filterAssignmentListForUser(String userPrincipalId, List<Assignment> assignments) {
        if (CollectionUtils.isEmpty(assignments)) {
            return Collections.emptyList();
        }
        List<Assignment> filteredAssignments = new ArrayList<Assignment>();
        //TkUserRoles roles = TkUserRoles.getUserRoles(TKContext.getPrincipalId());
        //boolean systemAdmin = HrContext.isSystemAdmin();
        boolean systemAdmin = HrServiceLocator.getKPMEGroupService().isMemberOfSystemAdministratorGroup(userPrincipalId, LocalDate.now().toDateTimeAtStartOfDay());

        List<String> roleIds = new ArrayList<String>();
        RoleService roleService = KimApiServiceLocator.getRoleService();
        roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName()));
        roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName()));
        roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName()));
        roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR.getRoleName()));
        roleIds.add(roleService.getRoleIdByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.PAYROLL_PROCESSOR_DELEGATE.getRoleName()));
        List<Long> reportingWorkAreas = HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRoles(userPrincipalId, roleIds, LocalDate.now().toDateTimeAtStartOfDay(), true);
        for (Assignment assignment : assignments) {
            //if the user is not the same as the timesheet and does not have approver access for the assignment
            //do not add to the display
            if (!StringUtils.equals(userPrincipalId, assignment.getPrincipalId())) {
                if (!systemAdmin && !reportingWorkAreas.contains(assignment.getWorkArea())) {
                    continue;
                }
            }

            filteredAssignments.add(assignment);
        }

        return filteredAssignments;
    }


    @Override
    public Map<LocalDate, List<Assignment>> getAssignmentHistoryForCalendarEntry(String principalId, CalendarEntry calendarEntry) {
        return getAssignmentHistoryBetweenDaysInternal(principalId, calendarEntry.getBeginPeriodFullDateTime().toLocalDate(), calendarEntry.getEndPeriodFullDateTime().toLocalDate(), HrConstants.FLSA_STATUS_NON_EXEMPT, false);
    }

    protected Map<LocalDate, List<Assignment>> getAssignmentHistoryBetweenDaysInternal(String principalId, LocalDate beginDate, LocalDate endDate, String flsaStatus, boolean checkLeaveEligible) {
        Map<LocalDate, List<Assignment>> history = new HashMap<LocalDate, List<Assignment>>();

        //get active assignments for period begin date
        List<Assignment> beginAssignments = getAssignments(principalId, beginDate);

        //might as well set the first day, since we have it already
        history.put(beginDate, filterAssignments(new ArrayList<Assignment>(beginAssignments), flsaStatus, checkLeaveEligible));

        if(beginDate.equals(endDate)) {
            return history;
        }
        //get all assignment activity between start and end dates
        List<AssignmentBo> assignmentChanges = assignmentDao.findAssignmentsHistoryForPeriod(principalId, beginDate, endDate);

        //let's put this in a map for easier access!!!
        Map<LocalDate, List<Assignment>> assignmentChangeMap = new HashMap<LocalDate, List<Assignment>>();
        for (AssignmentBo change : assignmentChanges) {
            LocalDate key = change.getEffectiveLocalDate();
            if (assignmentChangeMap.containsKey(key)) {
                assignmentChangeMap.get(key).add(AssignmentBo.to(change));
            } else {
                List<Assignment> changeList = new ArrayList<Assignment>();
                changeList.add(AssignmentBo.to(change));
                assignmentChangeMap.put(key, changeList);
            }
        }

        //we now have a list, in order of the active at the start, and all changes between... now lets try to map it out per day....
        List<LocalDate> localDates = new ArrayList<LocalDate>();
        int days = Days.daysBetween(beginDate, endDate).getDays()+1;
        for (int i=0; i < days; i++) {
            LocalDate d = beginDate.withFieldAdded(DurationFieldType.days(), i);
            localDates.add(d);
        }

        LocalDate previousDay = beginDate;
        for (LocalDate ldate : localDates) {
            if (!ldate.equals(beginDate)) {
                List<Assignment> previousAssignments = history.get(previousDay);
                List<Assignment> todaysAssignments = new ArrayList<Assignment>(previousAssignments);

                if (assignmentChangeMap.containsKey(ldate)) {
                    //what changed??? Figure it out and filter
                    for (Assignment a : assignmentChangeMap.get(ldate)) {
                        if (!a.isActive()) {
                            // try to remove from list
                            Iterator<Assignment> iter = todaysAssignments.iterator();
                            while (iter.hasNext()) {
                                Assignment iterAssign = iter.next();
                                if (iterAssign.getAssignmentKey().equals(a.getAssignmentKey())) {
                                    iter.remove();
                                }
                            }
                        } else {
                            //if it already exists, remove before adding new
                            ListIterator<Assignment> iter = todaysAssignments.listIterator();
                            boolean replaced = false;
                            while (iter.hasNext()) {
                                Assignment iterAssign = iter.next();
                                if (iterAssign.getAssignmentKey().equals(a.getAssignmentKey())) {
                                    iter.set(a);
                                    replaced = true;
                                }
                            }

                            //add to list if not already replaced
                            if (!replaced) {
                                todaysAssignments.add(a);
                            }
                        }
                    }
                    todaysAssignments = filterAssignments(todaysAssignments, flsaStatus, checkLeaveEligible);
                }

                history.put(ldate, todaysAssignments);
                previousDay = ldate;
            }
        }

        return history;
    }

/*    protected Map<String, String> filterAssignmentKeys(List<Assignment> assignments, boolean clockOnlyAssignments, LocalDate asOfDate) {
        String principalId = "";
        if (CollectionUtils.isEmpty(assignments)) {
            return Collections.emptyMap();
        } else {
            principalId = assignments.get(0).getPrincipalId();
        }
        //sorting the assignment list
        Collections.sort(assignments, new Comparator<Assignment>() {
            @Override
            public int compare(Assignment a1, Assignment a2) {
                int sComp = a1.getJobNumber().compareTo(a2.getJobNumber());

                if (sComp != 0 ) {
                    return sComp;
                } else {                 //comparing workarea descriptions   TK-1945
                    String w1 = a1.getWorkAreaObj() == null ? "" : a1.getWorkAreaObj().getDescription();
                    String w2 = a2.getWorkAreaObj() == null ? "" : a2.getWorkAreaObj().getDescription();
                    return w1.compareTo(w2);
                }
            }
        });

        Map<String, String> assignmentDescriptions = new LinkedHashMap<String, String>();
        //TkUserRoles roles = TkUserRoles.getUserRoles(TKContext.getPrincipalId());
        boolean systemAdmin = HrContext.isSystemAdmin();
        Set<Long> reportingWorkAreas = new HashSet<Long>();
        reportingWorkAreas.addAll(HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), asOfDate.toDateTimeAtStartOfDay(), true));
        reportingWorkAreas.addAll(HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), asOfDate.toDateTimeAtStartOfDay(), true));
        reportingWorkAreas.addAll(HrServiceLocator.getKPMERoleService().getWorkAreasForPrincipalInRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.REVIEWER.getRoleName(), asOfDate.toDateTimeAtStartOfDay(), true));

        for (Assignment assignment : assignments) {
            //if the user is not the same as the timesheet and does not have approver access for the assignment
            //do not add to the display
            if (!StringUtils.equals(HrContext.getTargetPrincipalId(), HrContext.getPrincipalId())) {
                if (!systemAdmin && !reportingWorkAreas.contains(assignment.getWorkArea())) {
                    continue;
                }
            }

            //only add to the assignment list if they are synchronous assignments
            //or clock only assignments is false
            if (!clockOnlyAssignments || assignment.isSynchronous()) {
                assignmentDescriptions.putAll(getAssignmentDescriptions(assignment));
            }
        }

        return assignmentDescriptions;
    }*/
}
