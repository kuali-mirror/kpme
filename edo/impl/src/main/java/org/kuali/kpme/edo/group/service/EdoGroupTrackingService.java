package org.kuali.kpme.edo.group.service;

import java.util.List;

import org.kuali.kpme.edo.api.group.EdoGroupTracking;
import org.kuali.kpme.edo.group.EdoGroupTrackingBo;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/14/14
 * Time: 3:31 PM
 */
public interface EdoGroupTrackingService {

    public EdoGroupTracking getEdoGroupTracking(String edoGroupTrackingId);
    public EdoGroupTracking getEdoGroupTrackingByGroupName(String groupName);
    public List<EdoGroupTracking> getEdoGroupTrackingByDepartmentId(String departmentId);
    public List<EdoGroupTracking> getEdoGroupTrackingBySchoolId(String schoolId);
    public List<EdoGroupTracking> getEdoGroupTrackingByCampusId(String campusId);
    public boolean kimGroupTrackingExists(String groupName);
    public void saveOrUpdate(EdoGroupTrackingBo groupTracking);
    public List<EdoGroupTracking> findEdoGroupTrackingEntries(String departmentId, String schoolId, String campusId);
    public List<String> getDistinctDepartmentList();
    public List<String> getDistinctSchoolList();
    public List<String> getDistinctCampusList();
}
