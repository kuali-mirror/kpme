package org.kuali.kpme.edo.group.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.kpme.edo.api.group.EdoGroupTracking;
import org.kuali.kpme.edo.group.EdoGroupTrackingBo;
import org.kuali.kpme.edo.group.dao.EdoGroupTrackingDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.krad.util.ObjectUtils;

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
    
    protected List<EdoGroupTracking> convertToImmutable(List<EdoGroupTrackingBo> bos) {
		return ModelObjectUtils.transform(bos, EdoGroupTrackingBo.toImmutable);
	}

    public EdoGroupTrackingDao getEdoGroupTrackingDao() {
        return edoGroupTrackingDao;
    }

    public void setEdoGroupTrackingDao(EdoGroupTrackingDao edoGroupTrackingDao) {
        this.edoGroupTrackingDao = edoGroupTrackingDao;
    }

    public EdoGroupTracking getEdoGroupTracking(String edoGroupTrackingId) {
        return EdoGroupTrackingBo.to(edoGroupTrackingDao.getEdoGroupTracking(edoGroupTrackingId));
    }

    public EdoGroupTracking getEdoGroupTrackingByGroupName(String groupName) {        
        return EdoGroupTrackingBo.to(edoGroupTrackingDao.getEdoGroupTrackingByGroupName(groupName));
    }

    public List<EdoGroupTracking> getEdoGroupTrackingByDepartmentId(String departmentId) {
        List<EdoGroupTrackingBo> bos = edoGroupTrackingDao.getEdoGroupTrackingByDepartmentId(departmentId);
    	return convertToImmutable(bos);
    }

    public List<EdoGroupTracking> getEdoGroupTrackingBySchoolId(String schoolId) {
    	List<EdoGroupTrackingBo> bos = edoGroupTrackingDao.getEdoGroupTrackingBySchoolId(schoolId);
    	return convertToImmutable(bos);
    }

    public List<EdoGroupTracking> getEdoGroupTrackingByCampusId(String campusId) {
    	List<EdoGroupTrackingBo> bos = edoGroupTrackingDao.getEdoGroupTrackingByCampusId(campusId);
        return convertToImmutable(bos);
    }

    public boolean kimGroupTrackingExists(String groupName) {
        boolean result = false;
        EdoGroupTrackingBo grp = edoGroupTrackingDao.getEdoGroupTrackingByGroupName(groupName);
        if (ObjectUtils.isNotNull(grp)) {
            result = true;
        }
        return result;
    }

    public void saveOrUpdate(EdoGroupTrackingBo groupTracking) {
        this.edoGroupTrackingDao.saveOrUpdate(groupTracking); 
    }

    public List<EdoGroupTracking> findEdoGroupTrackingEntries(String departmentId, String schoolId, String campusId) {

    	List<EdoGroupTrackingBo> bos = edoGroupTrackingDao.findEdoGroupTrackingEntries(departmentId, schoolId, campusId);
        return convertToImmutable(bos);
    }

    public List<String> getDistinctDepartmentList() {
        List<String> deptList = new LinkedList<String>();

        Map<String,String> hash = new HashMap<String, String>();

        List<EdoGroupTrackingBo> allEntries = edoGroupTrackingDao.getGroupTrackingEntries();
        for (EdoGroupTrackingBo edoGroupTracking : allEntries) {
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

        List<EdoGroupTrackingBo> allEntries = edoGroupTrackingDao.getGroupTrackingEntries();
        for (EdoGroupTrackingBo edoGroupTracking : allEntries) {
            if (edoGroupTracking.getOrganizationCode() != null) {
                hash.put(edoGroupTracking.getOrganizationCode(),"1");
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

        List<EdoGroupTrackingBo> allEntries = edoGroupTrackingDao.getGroupTrackingEntries();
        for (EdoGroupTrackingBo edoGroupTracking : allEntries) {
            if (edoGroupTracking.getGroupKey().getCampusCode() != null) {
                hash.put(edoGroupTracking.getGroupKey().getCampusCode(),"1");
            }
        }
        for (String str : hash.keySet()) {
            campusList.add(str);
        }
        return campusList;
    }

}
