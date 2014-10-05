package org.kuali.kpme.edo.reports.web;

import java.util.List;
import java.util.Map;

import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.reports.EdoPromotionAndTenureReport;


/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 2/19/14
 * Time: 3:02 PM
 */
public class EdoPTReportForm extends EdoForm {

	private String dossierTypeName;
	private List<String> dossierTypes;
	private String departmentId;
	private List<String> departments;
	private String schoolId;
	private List<String> schools;
	private String workflowId;
	private List<String> workflows;
	private Integer voteRoundCode;
	private Map<Integer, String> voteRounds;
	
	private List<EdoPromotionAndTenureReport> reports;

	public String getDossierTypeName() {
		return dossierTypeName;
	}

	public void setDossierTypeName(String dossierTypeName) {
		this.dossierTypeName = dossierTypeName;
	}

	public List<String> getDossierTypes() {
		return dossierTypes;
	}

	public void setDossierTypes(List<String> dossierTypes) {
		this.dossierTypes = dossierTypes;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public List<String> getDepartments() {
		return departments;
	}

	public void setDepartments(List<String> departments) {
		this.departments = departments;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public List<String> getSchools() {
		return schools;
	}

	public void setSchools(List<String> schools) {
		this.schools = schools;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public List<String> getWorkflows() {
		return workflows;
	}

	public void setWorkflows(List<String> workflows) {
		this.workflows = workflows;
	}

	public Integer getVoteRoundCode() {
		return voteRoundCode;
	}

	public void setVoteRoundCode(Integer voteRoundCode) {
		this.voteRoundCode = voteRoundCode;
	}

	public Map<Integer, String> getVoteRounds() {
		return voteRounds;
	}

	public void setVoteRounds(Map<Integer, String> voteRounds) {
		this.voteRounds = voteRounds;
	}

	public List<EdoPromotionAndTenureReport> getReports() {
		return reports;
	}

	public void setReports(List<EdoPromotionAndTenureReport> reports) {
		this.reports = reports;
	}
}
