package org.kuali.kpme.edo.group.service;

import org.apache.log4j.Logger;
import org.kuali.kpme.edo.group.EdoGroupTracking;
import org.kuali.kpme.edo.group.dao.EdoGroupTrackingDao;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/14/14
 * Time: 3:31 PM
 */
public class EdoGroupTrackingServiceImpl extends PlatformAwareDaoBaseOjb implements EdoGroupTrackingService {

    private static final Logger LOG = Logger.getLogger(EdoGroupTrackingServiceImpl.class);
    private EdoGroupTrackingDao edoGroupTrackingDao;

    public EdoGroupTrackingDao getEdoGroupTrackingDao() {
        return edoGroupTrackingDao;
    }

    public void setEdoGroupTrackingDao(EdoGroupTrackingDao edoGroupTrackingDao) {
        this.edoGroupTrackingDao = edoGroupTrackingDao;
    }

    public EdoGroupTracking getEdoGroupTracking(Integer groupTrackingId) {
        return edoGroupTrackingDao.getEdoGroupTracking(groupTrackingId);
    }

    public EdoGroupTracking getEdoGroupTrackingByGroupName(String groupName) {
        return edoGroupTrackingDao.getEdoGroupTrackingByGroupName(groupName);
    }

    public List<EdoGroupTracking> getEdoGroupTrackingByDepartmentId(String departmentId) {
        return edoGroupTrackingDao.getEdoGroupTrackingByDepartmentId(departmentId);
    }

    public List<EdoGroupTracking> getEdoGroupTrackingBySchoolId(String schoolId) {
        return edoGroupTrackingDao.getEdoGroupTrackingBySchoolId(schoolId);
    }

    public List<EdoGroupTracking> getEdoGroupTrackingByCampusId(String campusId) {
        return edoGroupTrackingDao.getEdoGroupTrackingByCampusId(campusId);
    }

    public boolean kimGroupTrackingExists(String groupName) {
        boolean result = false;
        EdoGroupTracking grp = edoGroupTrackingDao.getEdoGroupTrackingByGroupName(groupName);
        if (ObjectUtils.isNotNull(grp)) {
            result = true;
        }
        return result;
    }

    public void saveOrUpdate(EdoGroupTracking groupTracking) {
        this.edoGroupTrackingDao.saveOrUpdate(groupTracking);
    }

    public List<EdoGroupTracking> findEdoGroupTrackingEntries(String departmentId, String schoolId, String campusId) {

        return edoGroupTrackingDao.findEdoGroupTrackingEntries(departmentId, schoolId, campusId);
    }

    public List<String> getDistinctDepartmentList() {
        List<String> deptList = new LinkedList<String>();

        Map<String,String> hash = new HashMap<String, String>();

        List<EdoGroupTracking> allEntries = edoGroupTrackingDao.getGroupTrackingEntries();
        for (EdoGroupTracking edoGroupTracking : allEntries) {
            if (edoGroupTracking.getDepartmentId() != null) {
                hash.put(edoGroupTracking.getDepartmentId(),"1");
            }
        }
        for (String str : hash.keySet()) {
            deptList.add(str);
        }
        return deptList;
    }

    public List<String> getDistinctSchoolList() {
        List<String> schoolList = new LinkedList<String>();

        Map<String,String> hash = new HashMap<String, String>();

        List<EdoGroupTracking> allEntries = edoGroupTrackingDao.getGroupTrackingEntries();
        for (EdoGroupTracking edoGroupTracking : allEntries) {
            if (edoGroupTracking.getSchoolId() != null) {
                hash.put(edoGroupTracking.getSchoolId(),"1");
            }
        }
        for (String str : hash.keySet()) {
            schoolList.add(str);
        }
        return schoolList;
    }

    public List<String> getDistinctCampusList() {
        List<String> campusList = new LinkedList<String>();

        Map<String,String> hash = new HashMap<String, String>();

        List<EdoGroupTracking> allEntries = edoGroupTrackingDao.getGroupTrackingEntries();
        for (EdoGroupTracking edoGroupTracking : allEntries) {
            if (edoGroupTracking.getCampusId() != null) {
                hash.put(edoGroupTracking.getCampusId(),"1");
            }
        }
        for (String str : hash.keySet()) {
            campusList.add(str);
        }
        return campusList;
    }

}
