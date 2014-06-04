package org.kuali.kpme.edo.admin.web;

import org.apache.struts.upload.FormFile;
import org.kuali.kpme.edo.base.web.EdoForm;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 3/18/14
 * Time: 3:20 PM
 */
public class EdoManageAdminsForm extends EdoForm {

    private List<String> departmentSelectList;
    private List<String> schoolSelectList;
    private List<String> campusSelectList;
    //private List<String> reviewLevelSelectList;
    private Map<BigDecimal,String> reviewLevelSelectList;
    private FormFile UploadMbrFile;
    private String memberData;

    public List<String> getDepartmentSelectList() {
        return departmentSelectList;
    }

    public void setDepartmentSelectList(List<String> departmentSelectList) {
        this.departmentSelectList = departmentSelectList;
    }

    public List<String> getSchoolSelectList() {
        return schoolSelectList;
    }

    public void setSchoolSelectList(List<String> schoolSelectList) {
        this.schoolSelectList = schoolSelectList;
    }

    public List<String> getCampusSelectList() {
        return campusSelectList;
    }

    public void setCampusSelectList(List<String> campusSelectList) {
        this.campusSelectList = campusSelectList;
    }

    public Map<BigDecimal,String> getReviewLevelSelectList() {
        return reviewLevelSelectList;
    }

    public void setReviewLevelSelectList(Map<BigDecimal,String> reviewLevelSelectList) {
        this.reviewLevelSelectList = reviewLevelSelectList;
    }

    public FormFile getUploadMbrFile() {
        return UploadMbrFile;
    }

    public void setUploadMbrFile(FormFile uploadMbrFile) {
        UploadMbrFile = uploadMbrFile;
    }

    public String getMemberData() {
        return memberData;
    }

    public void setMemberData(String memberData) {
        this.memberData = memberData;
    }

}
