package org.kuali.kpme.edo.group;

import java.math.BigDecimal;
import java.util.Date;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/14/14
 * Time: 3:33 PM
 */
public class EdoGroupTracking {
    private BigDecimal groupTrackingId;
    private String departmentId;
    private String schoolId;
    private String campusId;
    private String reviewLevelName;
    private String groupName;
    private Date dateAdded;

    public BigDecimal getGroupTrackingId() {
        return groupTrackingId;
    }

    public void setGroupTrackingId(BigDecimal groupTrackingId) {
        this.groupTrackingId = groupTrackingId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getCampusId() {
        return campusId;
    }

    public void setCampusId(String campusId) {
        this.campusId = campusId;
    }

    public String getReviewLevelName() {
        return reviewLevelName;
    }

    public void setReviewLevelName(String reviewLevelName) {
        this.reviewLevelName = reviewLevelName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
