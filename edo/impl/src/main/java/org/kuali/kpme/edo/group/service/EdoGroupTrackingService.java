package org.kuali.kpme.edo.group.service;

import org.kuali.kpme.edo.group.EdoGroupTracking;

import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/14/14
 * Time: 3:31 PM
 */
public interface EdoGroupTrackingService {

    public EdoGroupTracking getEdoGroupTracking(Integer groupTrackingId);
    public EdoGroupTracking getEdoGroupTrackingByGroupName(String groupName);
    public List<EdoGroupTracking> getEdoGroupTrackingByDepartmentId(String departmentId);
    public List<EdoGroupTracking> getEdoGroupTrackingBySchoolId(String schoolId);
    public List<EdoGroupTracking> getEdoGroupTrackingByCampusId(String campusId);
    public boolean kimGroupTrackingExists(String groupName);
    public void saveOrUpdate(EdoGroupTracking groupTracking);
    public List<EdoGroupTracking> findEdoGroupTrackingEntries(String departmentId, String schoolId, String campusId);
    public List<String> getDistinctDepartmentList();
    public List<String> getDistinctSchoolList();
    public List<String> getDistinctCampusList();
}
