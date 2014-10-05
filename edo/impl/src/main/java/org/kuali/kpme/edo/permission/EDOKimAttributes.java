package org.kuali.kpme.edo.permission;

import org.kuali.rice.kim.bo.impl.KimAttributes;

public class EDOKimAttributes extends KimAttributes {

	private static final long serialVersionUID = 1L;
	public static final String ROLE_ADMINISTRATOR_DEPT_ID = "edoRoleAdministratorDeptId";
	public static final String ROLE_ADMINISTRATOR_SCHOOL_ID = "edoRoleAdministratorSchoolId";
	public static final String ROLE_ADMINISTRATOR_CAMPUS_ID = "edoRoleAdministratorCampusId";
	
	public static final String ROLE_CHAIR_DEPT_ID = "edoRoleChairDeptId";
	public static final String ROLE_CHAIR_SCHOOL_ID = "edoRoleChairSchoolId";
	public static final String ROLE_CHAIR_CAMPUS_ID = "edoRoleChairCampusId";
	public static final String ROLE_CHAIR_UNIVERSITY_ID = "edoRoleChairUniversityId";

    public static final String ROLE_DOCUMENT_MANAGER_DEPT_ID = "edoRoleDocumentManagerDeptId";
    public static final String ROLE_DOCUMENT_MANAGER_SCHOOL_ID = "edoRoleDocumentManagerSchoolId";
    public static final String ROLE_DOCUMENT_MANAGER_CAMPUS_ID = "edoRoleDocumentManagerCampusId";

	public static final String ROLE_CHAIR_DELEGATE_ID = "edoRoleChairDelegateId";
	
	public static final String ROLE_CANDIDATE_DELEGATE_DOSSIER_ID= "edoRoleCandidateDelegateDossierId";
	public static final String ROLE_GUEST_DOSSIER_ID = "edoRoleGuestDossierId";
	
	//DossierId attrbute is added to the candidate_guest role
	public static final String EDO_CANDIDATE_DOSSIER_ID = "DossierID";
	
	public static final String ROLE_REVIEWER_DEPT_ID = "edoRoleReviewerDeptId";
	public static final String ROLE_REVIEWER_SCHOOL_ID = "edoRoleReviewerSchoolId";
	public static final String ROLE_REVIEWER_CAMPUS_ID = "edoRoleReviewerCampusId";
	
	public static final String EDO_REVIEW_LEVEL = "edoReviewLevel";
	public static final String EDO_DEPARTMENT_ID = "edoDepartmentId";
	public static final String EDO_SCHOOL_ID = "edoSchoolId";
	public static final String EDO_CAMPUS_ID = "edoCampusId";
	public static final String EDO_INSTITUTION_ID = "edoInstitutionId";
	public static final String EDO_DOSSIER_ID = "edoDossierID";
	public static final String EDO_DOSSIER_TYPE = "edoDossierType";

    private String edoRoleAdministratorDeptId;
	private String edoRoleAdministratorSchoolId;
	private String edoRoleAdministratorCampusId;

    private String edoRoleDocumentManagerDeptId;
    private String edoRoleDocumentManagerSchoolId;
    private String edoRoleDocumentManagerCampusId;

	private String edoRoleChairDeptId;
	private String edoRoleChairSchoolId;
	private String edoRoleChairCampusId;
	private String edoRoleChairUniversityId;
	
	private String edoRoleChairDelegateId;
	private String edoRoleCandidateDelegateDossierId;
	private String edoRoleGuestDossierId;
	
	private String edoRoleReviewerDeptId;
	private String edoRoleReviewerSchoolId;
	private String edoRoleReviewerCampusId;
	
	//new kim attributes for newly created kim types
	private String edoReviewLevel;
	private String edoDepartmentId;
	private String edoSchoolId;
	private String edoCampusId;
	private String edoInstitutionId;
	private String edoDossierID;
	private String edoDossierType;

	public String getEdoRoleAdministratorDeptId() {
		return edoRoleAdministratorDeptId;
	}
	public void setEdoRoleAdministratorDeptId(String edoRoleAdministratorDeptId) {
		this.edoRoleAdministratorDeptId = edoRoleAdministratorDeptId;
	}
	public String getEdoRoleAdministratorSchoolId() {
		return edoRoleAdministratorSchoolId;
	}
	public void setEdoRoleAdministratorSchoolId(String edoRoleAdministratorSchoolId) {
		this.edoRoleAdministratorSchoolId = edoRoleAdministratorSchoolId;
	}
	public String getEdoRoleAdministratorCampusId() {
		return edoRoleAdministratorCampusId;
	}
	public void setEdoRoleAdministratorCampusId(String edoRoleAdministratorCampusId) {
		this.edoRoleAdministratorCampusId = edoRoleAdministratorCampusId;
	}
	public String getEdoRoleChairDeptId() {
		return edoRoleChairDeptId;
	}
	public void setEdoRoleChairDeptId(String edoRoleChairDeptId) {
		this.edoRoleChairDeptId = edoRoleChairDeptId;
	}
	public String getEdoRoleChairSchoolId() {
		return edoRoleChairSchoolId;
	}
	public void setEdoRoleChairSchoolId(String edoRoleChairSchoolId) {
		this.edoRoleChairSchoolId = edoRoleChairSchoolId;
	}
	public String getEdoRoleChairCampusId() {
		return edoRoleChairCampusId;
	}
	public void setEdoRoleChairCampusId(String edoRoleChairCampusId) {
		this.edoRoleChairCampusId = edoRoleChairCampusId;
	}
	public String getEdoRoleChairUniversityId() {
		return edoRoleChairUniversityId;
	}
	public void setEdoRoleChairUniversityId(String edoRoleChairUniversityId) {
		this.edoRoleChairUniversityId = edoRoleChairUniversityId;
	}

    public String getEdoRoleDocumentManagerDeptId() {
        return edoRoleDocumentManagerDeptId;
    }

    public void setEdoRoleDocumentManagerDeptId(String edoRoleDocumentManagerDeptId) {
        this.edoRoleDocumentManagerDeptId = edoRoleDocumentManagerDeptId;
    }

    public String getEdoRoleDocumentManagerSchoolId() {
        return edoRoleDocumentManagerSchoolId;
    }

    public void setEdoRoleDocumentManagerSchoolId(String edoRoleDocumentManagerSchoolId) {
        this.edoRoleDocumentManagerSchoolId = edoRoleDocumentManagerSchoolId;
    }

    public String getEdoRoleDocumentManagerCampusId() {
        return edoRoleDocumentManagerCampusId;
    }

    public void setEdoRoleDocumentManagerCampusId(String edoRoleDocumentManagerCampusId) {
        this.edoRoleDocumentManagerCampusId = edoRoleDocumentManagerCampusId;
    }

    public String getEdoRoleChairDelegateId() {
		return edoRoleChairDelegateId;
	}
	public void setEdoRoleChairDelegateId(String edoRoleChairDelegateId) {
		this.edoRoleChairDelegateId = edoRoleChairDelegateId;
	}
	public String getEdoRoleCandidateDelegateDossierId() {
		return edoRoleCandidateDelegateDossierId;
	}
	public void setEdoRoleCandidateDelegateDossierId(
			String edoRoleCandidateDelegateDossierId) {
		this.edoRoleCandidateDelegateDossierId = edoRoleCandidateDelegateDossierId;
	}
	public String getEdoRoleGuestDossierId() {
		return edoRoleGuestDossierId;
	}
	public void setEdoRoleGuestDossierId(String edoRoleGuestDossierId) {
		this.edoRoleGuestDossierId = edoRoleGuestDossierId;
	}
	public String getEdoRoleReviewerDeptId() {
		return edoRoleReviewerDeptId;
	}
	public void setEdoRoleReviewerDeptId(String edoRoleReviewerDeptId) {
		this.edoRoleReviewerDeptId = edoRoleReviewerDeptId;
	}
	public String getEdoRoleReviewerSchoolId() {
		return edoRoleReviewerSchoolId;
	}
	public void setEdoRoleReviewerSchoolId(String edoRoleReviewerSchoolId) {
		this.edoRoleReviewerSchoolId = edoRoleReviewerSchoolId;
	}
	public String getEdoRoleReviewerCampusId() {
		return edoRoleReviewerCampusId;
	}
	public void setEdoRoleReviewerCampusId(String edoRoleReviewerCampusId) {
		this.edoRoleReviewerCampusId = edoRoleReviewerCampusId;
	}
	//getters and setters
	public String getEdoReviewLevel() {
		return edoReviewLevel;
	}
	public void setEdoReviewLevel(String edoReviewLevel) {
		this.edoReviewLevel = edoReviewLevel;
	}
	public String getEdoDepartmentId() {
		return edoDepartmentId;
	}
	public void setEdoDepartmentId(String edoDepartmentId) {
		this.edoDepartmentId = edoDepartmentId;
	}
	public String getEdoSchoolId() {
		return edoSchoolId;
	}
	public void setEdoSchoolId(String edoSchoolId) {
		this.edoSchoolId = edoSchoolId;
	}
	public String getEdoCampusId() {
		return edoCampusId;
	}
	public void setEdoCampusId(String edoCampusId) {
		this.edoCampusId = edoCampusId;
	}
	public String getEdoInstitutionId() {
		return edoInstitutionId;
	}
	public void setEdoInstitutionId(String edoInstitutionId) {
		this.edoInstitutionId = edoInstitutionId;
	}
	public String getEdoDossierID() {
		return edoDossierID;
	}
	public void setEdoDossierID(String edoDossierID) {
		this.edoDossierID = edoDossierID;
	}
	public String getEdoDossierType() {
		return edoDossierType;
	}
	public void setEdoDossierType(String edoDossierType) {
		this.edoDossierType = edoDossierType;
	}

	
	
}

