package org.kuali.kpme.edo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lee55
 * Date: 1/29/13
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoConstants {
    public static final class CHECK_LIST_TYPE {
        public static final String VOTE_RECORD = "vote";
    }

    public static final class VOTE_RECORD_ROLES {
        public static final String PRIMARY_UNIT = "Primary Unit/Dept. Committee";
        public static final String CHAIR = "CHAIR";
        public static final String DEPARTMENT = "Department/School Committee";
        public static final String DEAN = "DEAN";
        public static final String CAMPUS_COMMITTEE = "Campus Committee";
        public static final String VACC = "VACC/Vice Provost";
        public static final String CHANCELLOR = "Chancellor/Provost";
    }

    public static final String SELECTED_CANDIDATE = "selectedCandidate";

    public static final Map<String, String> AREA_OF_EXCELLENCE = new HashMap<String, String>(4);

    static {
        AREA_OF_EXCELLENCE.put("R", "Research/Creative Activity");
        AREA_OF_EXCELLENCE.put("T", "Teaching");
        AREA_OF_EXCELLENCE.put("S", "Service/Engagement");
        AREA_OF_EXCELLENCE.put("B", "Balanced Case");
    }

    public static class ErrorKeys {
		public static final String ERROR_KEYS = "error";
	}
  	public static final String ROLE_ADMINISTRATOR_DEPT_ID = "edoRoleAdministratorDeptId";
	public static final String ROLE_ADMINISTRATOR_SCHOOL_ID = "edoRoleAdministratorSchoolId";
	public static final String ROLE_ADMINISTRATOR_CAMPUS_ID = "edoRoleAdministratorCampusId";

    public static final String ROLE_DOCUMENT_MANAGER_DEPT_ID = "edoRoleDocumentManagerDeptId";
    public static final String ROLE_DOCUMENT_MANAGER_SCHOOL_ID = "edoRoleDocumentManagerSchoolId";
    public static final String ROLE_DOCUMENT_MANAGER_CAMPUS_ID = "edoRoleDocumentManagerCampusId";

    public static final String ROLE_CHAIR_DEPT_ID = "edoRoleChairDeptId";
	public static final String ROLE_CHAIR_SCHOOL_ID = "edoRoleChairSchoolId";
	public static final String ROLE_CHAIR_CAMPUS_ID = "edoRoleChairCampusId";
	public static final String ROLE_CHAIR_UNIVERSITY_ID = "edoRoleChairUniversityId";
	
	
	public static final String ROLE_CANDIDATE_DELEGATE_DOSSIER_ID= "edoRoleCandidateDelegateDossierId";
	//created new qualifier for candidate_delegate_role and candidate_guest
	public static final String ROLE_GUEST_DOSSIER_ID = "edoDossierID";
	public static final String ROLE_CHAIR_DELEGATE_ID = "edoRoleChairDelegateId";

	public static final String ROLE_REVIEWER_DEPT_ID = "edoRoleReviewerDeptId";
	public static final String ROLE_REVIEWER_SCHOOL_ID = "edoRoleReviewerSchoolId";
	public static final String ROLE_REVIEWER_CAMPUS_ID = "edoRoleReviewerCampusId";
	
	public static final String ROLE_SECOND_UNIT_REVIEWER_DOSSIER_ID = "edoDossierID";

    public static final String ROLE_REVIEWER_CAMPUS = "Campus Reviewer";
    public static final String ROLE_REVIEWER_DEPT = "Department Reviewer";
    public static final String ROLE_REVIEWER_SCHOOL = "School Reviewer";

    public static final String ROLE_CANDIDATE = "Candidate";
    public static final String ROLE_CANDIDATE_DELEGATE = "Candidate Delegate";
	public static final String ROLE_SUPER_USER = "Super User";
	public static final String ROLE_FINAL_ADMINISTRATOR = "Final Administrator";
	public static final String ROLE_SECONDARY_UNIT_REVIEWER = "Candidate Second Unit Reviewer";

	//actions
	public static final String ACTION_DELETE = "DELETE";
	public static final String ACTION_ADD = "ADD";
	
	//edo roles constants
    public static final String EDO_SUPERUSER_ROLE = "EDO_SUPERUSER";
	public static final String CANDIDATE_DELEGATE_ROLE = "EDO_CANDIDATE_DELEGATE";
	public static final String CANDIDATE_GUEST_ROLE = "EDO_GUEST";
    public static final String CHAIR_DELEGATE_ROLE = "EDO_CHAIR_DELEGATE";
    public static final String CHAIR_COMMITTEE_ROLE = "EDO_COMMITTEE_CHAIR";
    public static final String CHAIR_COMMITTEE_TENURE_LEVEL1_ROLE = "EDO_COMMITTEE_CHAIR_TENURE_LEVEL1";
    public static final String CHAIR_DELEGATE_LEVEL1_ROLE = "EDO_CHAIR_DELEGATE_LEVEL1";
    public static final String CHAIR_DELEGATE_LEVEL2_ROLE = "EDO_CHAIR_DELEGATE_LEVEL2";
    public static final String CHAIR_DELEGATE_LEVEL3_ROLE = "EDO_CHAIR_DELEGATE_LEVEL3";
    public static final String CHAIR_DELEGATE_LEVEL4_ROLE = "EDO_CHAIR_DELEGATE_LEVEL4";
    public static final String CHAIR_DELEGATE_LEVEL5_ROLE = "EDO_CHAIR_DELEGATE_LEVEL5";
    public static final String CHAIR_DELEGATE_LEVEL6_ROLE = "EDO_CHAIR_DELEGATE_LEVEL6";
    public static final String CHAIR_DELEGATE_LEVEL7_ROLE = "EDO_CHAIR_DELEGATE_LEVEL7";
    public static final String CHAIR_DELEGATE_LEVEL8_ROLE = "EDO_CHAIR_DELEGATE_LEVEL8";
    public static final String SECOND_UNIT_REVIEWER_ROLE =  "EDO_SECOND_UNIT_REVIEWER";

    //edo name space
	public static final String EDO_NAME_SPACE = "EDO";

    // edo default workflow ID
    public static final String EDO_DEFAULT_WORKFLOW_ID = "DEFAULT";
	
	//edo permissions
	public static final String EDO_CANDIDATE_PERMISSION = "Use Edossier Candidate Screen";
	public static final String EDO_REVIEWER_PERMISSION = "Use Edossier Reviewer Screen";
	public static final String EDO_GEN_ADMIN_PERMISSION = "Use Edossier Admin Screen";
    public static final String EDO_ALL_SCREEN_PERMISSION = "Use all screens";
	public static final String EDO_ASSIGN_DELEGATE_PERMISSION = "Assign Delegate";
	public static final String EDO_ASSIGN_GUEST_PERMISSION = "Assign Guest";
	public static final String EDO_SUBMIT_DOSSIER_PERMISSION = "Submit Dossier";
	public static final String EDO_EDIT_DOSSIER_PERMISSION = "Edit Dossier";
    public static final String EDO_RECALL_DOSSIER_PERMISSION = "Recall Dossier";
    public static final String EDO_SUPER_USER_APPROVE = "Super User Approve";
    public static final String EDO_SUPER_USER_DISAPPROVE = "Super User Disapprove";
    public static final String EDO_UPLOAD_EXTERNAL_LETTER_TPL = "Upload External Letter";
    public static final String EDO_UPLOAD_REVIEW_LETTER_TPL = "Upload Review Letter";
    public static final String EDO_MANAGE_EXTERNAL_LETTERS ="Manage External Letters";

    public static final String EDO_ATTRIBUTE_DEPARTMENT_ID = "edoDepartmentId";
    public static final String EDO_ATTRIBUTE_SCHOOL_ID = "edoSchoolId";
    public static final String EDO_ATTRIBUTE_CAMPUS_ID = "edoCampusId";
    public static final String EDO_ATTRIBUTE_INSTITUTION_ID = "edoInstitutionId";

    public static final String EDO_ATTRIBUTE_REVIEW_LEVEL = "edoReviewLevel";

	//edo target person
	 public static final String EDO_TARGET_USER_RETURN = "edoTargetReturn";
	 public static final String EDO_TARGET_USER_PERSON = "edoTargetPerson";
	 
	 //edo login permission
	 public static final String EDO_LOGIN_PERMISSION = "Login Permission";
	 
	 public static final String EDO_SUPER_USER_APPROVE_TENURE_SUPP_PERMISSION = "Super User Approve TenureSupplementalProcessDocument";
	 public static final String EDO_SUPER_USER_APPROVE_PROMOTION_SUPP_PERMISSION = "Super User Approve PromotionSupplementalProcessDocument";
    
	 public static final class FILE_UPLOAD_PARAMETERS {
        public static final int THRESHHOLD_SIZE = 1024 * 1024 * 3;
        public static final int MAX_FILE_SIZE = 1024 * 1024 * 1024 * 4;
        public static final int REQUEST_SIZE = 1024 * 1024 * 1024 * 4;
        public static final String DEFAULT_MIME_TYPE = "application/octet-stream";
        public static final int MAX_FILENAME_LENGTH = 255;
        public static final String KUALI_MAX_FILE_UPLOAD_SIZE = "4000M";
    }

    // edo content strings
    public static final String EDO_GENERAL_SECTION_NAME = "General";
    public static final String EDO_SUPPLEMENTAL_ITEM_SECTION_NAME = "Supplemental Items";
    public static final String EDO_SUPPLEMENTAL_ITEM_CATEGORY_NAME = "Supplemental Supporting Items";
    public static final String EDO_RECONSIDERATION_ITEM_CATEGORY_NAME = "Reconsideration Supporting Items";
    public static final String EDO_RECONSIDERATION_ITEM_SECTION_NAME = "Reconsideration Items";
    public static final String EDO_ITEM_TYPE_NAME_ADDENDUM = "Addendumxx";
    public static final String EDO_ITEM_TYPE_NAME_REVIEW_LETTER = "Review Letter";
    public static final String EDO_ITEM_TYPE_NAME_SUPPORTING_DOCUMENT = "Supporting Document";
    public static final String EDO_ITEM_TYPE_NAME_SUPPORTING_EXT_DOCUMENT = "External Supporting Document";

    public static final String EDO_DEFAULT_CHECKLIST_NODE_ID = "cklst_0_0";

    public static final String EDO_CHECKLIST_VOTE_RECORD_LABEL = "Vote Record";
    public static final String EDO_CHECKLIST_INTERNAL_LETTERS_LABEL = "Internal Letters";
    public static final String EDO_CHECKLIST_EXTERNAL_LETTERS_LABEL = "External Letters";
    public static final String EDO_CHECKLIST_SECOND_UNIT_LABEL = "Secondary Unit Letter";
    public static final String EDO_CHECKLIST_SOLICTED_MAIN_LABEL = "Solicited Letters";
    public static final String EDO_CHECKLIST_SOLICITED_TEACHING_LABEL = "Teaching";
    public static final String EDO_CHECKLIST_SOLICITED_RESEARCH_LABEL = "Research";
    public static final String EDO_CHECKLIST_SOLICITED_SERVICE_LABEL = "Service";

    // hopefully, temporary until a better solution is implemented
    public static final int EDO_SUPPLEMENTAL_ITEM_CATEGORY_COUNT = 5;

    public static final String VOTE_TYPE_MULTIPLE = "Multiple";
    public static final String VOTE_TYPE_SINGLE = "Single";
    public static final String VOTE_TYPE_NONE = "None";
    public static final List<String> VOTE_TYPES = new ArrayList<String>(2);

    static {
        VOTE_TYPES.add(VOTE_TYPE_NONE);
        VOTE_TYPES.add(VOTE_TYPE_MULTIPLE);
        VOTE_TYPES.add(VOTE_TYPE_SINGLE);
    }
    
    public static final String VOTE_RECORD_VALUE_YES = "Yes";
    public static final String VOTE_RECORD_VALUE_NO = "No";
    public static final String VOTE_RECORD_VALUE_ABSTAIN = "Abstain";
    public static final String VOTE_RECORD_VALUE_ABSENT = "Absent";    
    public static final List<String> VOTE_RECORD_VALUES = new ArrayList<String>();
    
    static {
    	VOTE_RECORD_VALUES.add(VOTE_RECORD_VALUE_YES);
    	VOTE_RECORD_VALUES.add(VOTE_RECORD_VALUE_NO);
    	VOTE_RECORD_VALUES.add(VOTE_RECORD_VALUE_ABSTAIN);
    	VOTE_RECORD_VALUES.add(VOTE_RECORD_VALUE_ABSENT);
    }

    // IU campus codes
    public static final List<String> IU_CAMPUS_CODES = new ArrayList<String>();

    static {
        IU_CAMPUS_CODES.add("BL");
        IU_CAMPUS_CODES.add("IN");
        IU_CAMPUS_CODES.add("SE");
        IU_CAMPUS_CODES.add("KO");
        IU_CAMPUS_CODES.add("EA");
        IU_CAMPUS_CODES.add("SB");
        IU_CAMPUS_CODES.add("NW");
        IU_CAMPUS_CODES.add("FW");
        IU_CAMPUS_CODES.add("CO");
    }

    // dossier status
    public static final class DOSSIER_STATUS {
        public static final String OPEN = "OPEN";
        public static final String PENDING = "PENDING";
        public static final String SUBMITTED = "SUBMITTED";
        public static final String CLOSED = "CLOSED";
        public static final String RECONSIDERATION = "RECONSIDER";
    }

    public static final List<String> DOSSIER_STATUS_CURRENT = new ArrayList<String>(2);
    //added RECONSIDER and CLOSED to DOSSIER_STATUS_CURRENT structure
    //this accommodates super users and admins to see all the dossiers of the candidates.
    static {
        DOSSIER_STATUS_CURRENT.add(DOSSIER_STATUS.OPEN);
        DOSSIER_STATUS_CURRENT.add(DOSSIER_STATUS.PENDING);
        DOSSIER_STATUS_CURRENT.add(DOSSIER_STATUS.SUBMITTED);
        DOSSIER_STATUS_CURRENT.add(DOSSIER_STATUS.CLOSED);
        DOSSIER_STATUS_CURRENT.add(DOSSIER_STATUS.RECONSIDERATION);
    }
   
    public static final Map<String, String> EDO_DOSSIER_STATUS = new HashMap<String, String>(5);
    static {
    	EDO_DOSSIER_STATUS.put("OPEN", "OPEN");
    	EDO_DOSSIER_STATUS.put("PENDING", "PENDING");
    	EDO_DOSSIER_STATUS.put("SUBMITTED", "SUBMITTED");
    	EDO_DOSSIER_STATUS.put("CLOSED", "CLOSED");
    	EDO_DOSSIER_STATUS.put("RECONSIDERATION", "RECONSIDERATION");
    }
    

    public static final class ROUTING_NODE_NAMES {
        public static final String INITIATED = "Initiated";
        public static final String LEVELX = "LevelX";
        public static final String LEVEL1 = "Level1";
        public static final String LEVEL2 = "Level2";
        public static final String LEVEL3 = "Level3";
        public static final String LEVEL4 = "Level4";
        public static final String LEVEL5 = "Level5";
        public static final String LEVEL6 = "Level6";
        public static final String LEVEL7 = "Level7";
        public static final String LEVEL8 = "Level8";
    }

    public static final class VoteType {
    	public static final String VOTE_TYPE_TENURE = "Tenure";
    	public static final String VOTE_TYPE_PROMOTION = "Promotion";
        public static final String VOTE_TYPE_TENURE_PROMOTION = "Tenure+Promotion";
    }

    public static final String SUPPLEMENTAL_DOC_TYPE_TENURE = "TenureSupplementalProcessDocument";
    public static final String SUPPLEMENTAL_DOC_TYPE_PROMOTION = "PromotionSupplementalProcessDocument";

    public static final class DOSSIER_TAB {
        public static final String DOSSIER = "dossier";
        public static final String REVIEWS = "reviews";
        public static final String GENERAL_ADMIN = "gadmin";
        public static final String HELP = "help";
    }

    public static final int CHECKLIST_ITEM_VOTE_RECORD_ID = 10900;
    public static final int CHECKLIST_ITEM_REVIEW_LETTERS_ID = 10901;
    public static final int CHECKLIST_ITEM_EXTERNAL_LETTERS_ID = 10902;
    public static final int CHECKLIST_ITEM_SOLICITED_LETTERS_ID = 10903;
    public static final int CHECKLIST_ITEM_SOLICITED_LETTERS_1_ID = 10905;
    public static final int CHECKLIST_ITEM_SOLICITED_LETTERS_2_ID = 10906;
    public static final int CHECKLIST_ITEM_SOLICITED_LETTERS_3_ID = 10907;
    public static final int CHECKLIST_ITEM_SECOND_UNIT_LETTERS_ID = 10908;

    public static final String MAIL_USER = "user_mail";
    public static final String MAIL_PWD = "user_pwd";
    
    public static final String TEST_EMAIL_NOTIFICATION = "test.email.notification";
    
    public static class ConfigSettings {
		public static final String SESSION_TIMEOUT = "session.timeout";
		//public static final String TEST_MODE = "test.mode";
	}
    public static final String EDO_FINAL_ADMINSTRATORS_GRP = "Final Administrators";
    
    public static final Integer VOTE_ROUND_SUBMITTED_CODE = new Integer(1);
    public static final String VOTE_ROUND_SUBMITTED_NAME = "Submitted";
    public static final Integer VOTE_ROUND_RECONSIDERATION_CODE = new Integer(2);
    public static final String VOTE_ROUND_RECONSIDERATION_NAME = "Reconsideration";
    
    public static final Map<Integer, String> VOTE_ROUNDS = new HashMap<Integer, String>(2);
    static {
        VOTE_ROUNDS.put(VOTE_ROUND_SUBMITTED_CODE, VOTE_ROUND_SUBMITTED_NAME);
        VOTE_ROUNDS.put(VOTE_ROUND_RECONSIDERATION_CODE, VOTE_ROUND_RECONSIDERATION_NAME);
    }
    
    public static final Map<String, String> RANK = new HashMap<String, String>(3);
    static {
    	RANK.put("Assistant Professor", "Assistant Professor");
    	RANK.put("Associate Professor", "Associate Professor");
    	RANK.put("Professor", "Professor");
    }
    
    public static final String KHR_EDO_PORTAL_LINK_CONFIG = "khr.edo.portal.link.visible";
    
    
}
