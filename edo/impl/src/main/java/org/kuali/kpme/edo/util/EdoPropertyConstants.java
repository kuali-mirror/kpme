package org.kuali.kpme.edo.util;

public class EdoPropertyConstants {
	
	public static class EdoPromotionAndTenureReportFields {
		public static final String DOSSIER_ID = "dossierId";
		public static final String DOSSIER_TYPE_NAME = "dossierTypeName";
		public static final String DOSSIER_TYPE_ID = "dossierTypeId";
		public static final String ROUTE_LEVEL = "routeLevel";
		public static final String WORKFLOW_ID = "workflowId";
		public static final String VOTE_ROUND = "voteRound";
		public static final String VOTE_ROUND_NAME = "voteRoundName";
		
		public static final String LAST_NAME = "iuLastNamePref";
		public static final String FIRST_NAME = "iuFirstNamePref";
		public static final String EARLY = "early";
		public static final String AREA_OF_EXCELLENCE = "optionValue";
		public static final String CURRENT_RANK = "currentRank";
		public static final String RANK_SOUGHT = "rankSought";
		public static final String DEPARTMENT_ID = "departmentId";
		public static final String SCHOOL_ID = "schoolId";
		public static final String GENDER = "gender";
		public static final String ETHNICITY = "ethnicity";
		public static final String CITIZENSHIP_STATUS = "visaPermitType";
	}
	
	public static class EdoReviewLayerDefinitionFields {
        public static final String DEPARTMENT_ID = "departmentId";
        public static final String SCHOOL_ID = "schoolId";
        public static final String CAMPUS_ID = "campusId";
        public static final String REVIEW_LEVEL = "reviewLevel";
		public static final String ROUTE_LEVEL = "routeLevel";
		public static final String WORKFLOW_ID = "workflowId";
        public static final String NODE_NAME = "nodeName";
		public static final String VOTE_TYPE = "voteType";
	}

    public static class EdoSuppReviewLayerDefinitionFields {
        public static final String DEPARTMENT_ID = "departmentId";
        public static final String SCHOOL_ID = "schoolId";
        public static final String CAMPUS_ID = "campusId";
        public static final String WORKFLOW_ID = "edoWorkflowId";
        public static final String NODE_NAME = "suppNodeName";
    }

}
