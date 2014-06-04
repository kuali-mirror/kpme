package org.kuali.kpme.edo.admin.web;

import org.apache.struts.upload.FormFile;
import org.kuali.kpme.edo.base.web.EdoForm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 12/6/12
 * Time: 8:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoAdminGroupsForm extends EdoForm {

    private String departmentCode;
    private String schoolCode;
    private String campusCode;
    private String institutionCode = "IU";
    private String unitList;
    private String groupList;
    private List<String> workflowIds = new LinkedList<String>();
    private Map<String,String> workflowDisplayList = new HashMap<String, String>();
    private FormFile uploadGrpFile;
    private FormFile uploadMbrFile;
    private String formData;
    private String groupMembers;
    private String addToGroupName;
    private String groupMembersJSON;
    private String groupJSON;

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(String campusCode) {
        this.campusCode = campusCode;
    }

    public String getUnitList() {
        return unitList;
    }

    public void setUnitList(String unitList) {
        this.unitList = unitList;
    }

    public List<String> getWorkflowIds() {
        return workflowIds;
    }

    public void setWorkflowIds(List<String> workflowIds) {
        this.workflowIds = workflowIds;
    }

    public FormFile getUploadGrpFile() {
        return uploadGrpFile;
    }

    public void setUploadGrpFile(FormFile uploadFile) {
        this.uploadGrpFile = uploadFile;
    }

    public FormFile getUploadMbrFile() {
        return uploadMbrFile;
    }

    public void setUploadMbrFile(FormFile uploadMbrFile) {
        this.uploadMbrFile = uploadMbrFile;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    public String getGroupList() {
        return groupList;
    }

    public void setGroupList(String groupList) {
        this.groupList = groupList;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getGroupMembersJSON() {
        return groupMembersJSON;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    public String getAddToGroupName() {
        return addToGroupName;
    }

    public void setAddToGroupName(String addToGroupName) {
        this.addToGroupName = addToGroupName;
    }

    public void setGroupMembersJSON(String groupMembersJSON) {
        this.groupMembersJSON = groupMembersJSON;
    }

    public String getGroupJSON() {
        return groupJSON;
    }

    public void setGroupJSON(String groupJSON) {
        this.groupJSON = groupJSON;
    }

    public Map<String, String> getWorkflowDisplayList() {
        return workflowDisplayList;
    }

    public void setWorkflowDisplayList(Map<String, String> workflowDisplayList) {
        this.workflowDisplayList = workflowDisplayList;
    }
}
