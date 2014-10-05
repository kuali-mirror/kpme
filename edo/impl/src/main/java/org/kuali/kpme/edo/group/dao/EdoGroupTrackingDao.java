package org.kuali.kpme.edo.group.dao;

import org.kuali.kpme.edo.group.EdoGroupTrackingBo;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/14/14
 * Time: 3:30 PM
 */
public interface EdoGroupTrackingDao {

    public EdoGroupTrackingBo getEdoGroupTracking(String edoGroupTrackingId);
    public EdoGroupTrackingBo getEdoGroupTrackingByGroupName(String groupName);
    public List<EdoGroupTrackingBo> getEdoGroupTrackingByDepartmentId(String departmentId);
    public List<EdoGroupTrackingBo> getEdoGroupTrackingBySchoolId(String schoolId);
    public List<EdoGroupTrackingBo> getEdoGroupTrackingByCampusId(String campusId);
    public void saveOrUpdate(EdoGroupTrackingBo groupTracking);
    public List<EdoGroupTrackingBo> findEdoGroupTrackingEntries(String departmentId, String schoolId, String campusId);
    public List<EdoGroupTrackingBo> getGroupTrackingEntries();
}
