package org.kuali.kpme.edo.service;

import org.kuali.kpme.edo.authorization.service.AuthorizationService;
import org.kuali.kpme.edo.candidate.dao.EdoCandidateDao;
import org.kuali.kpme.edo.candidate.service.CandidateService;
import org.kuali.kpme.edo.checklist.dao.EdoChecklistDao;
import org.kuali.kpme.edo.checklist.dao.EdoChecklistItemDao;
import org.kuali.kpme.edo.checklist.service.EdoChecklistItemService;
import org.kuali.kpme.edo.checklist.service.EdoChecklistService;
import org.kuali.kpme.edo.dossier.dao.EdoDossierDao;
import org.kuali.kpme.edo.dossier.service.EdoCandidateDossierService;
import org.kuali.kpme.edo.dossier.service.EdoDossierService;
import org.kuali.kpme.edo.dossier.type.dao.EdoDossierTypeDao;
import org.kuali.kpme.edo.dossier.type.service.EdoDossierTypeService;
import org.kuali.kpme.edo.group.service.EdoGroupDefinitionService;
import org.kuali.kpme.edo.group.service.EdoGroupService;
import org.kuali.kpme.edo.group.service.EdoGroupTrackingService;
import org.kuali.kpme.edo.group.service.EdoRoleResponsibilityService;
import org.kuali.kpme.edo.item.count.service.EdoItemCountVService;
import org.kuali.kpme.edo.item.dao.EdoItemDao;
import org.kuali.kpme.edo.item.dao.EdoItemVDao;
import org.kuali.kpme.edo.item.service.EdoItemService;
import org.kuali.kpme.edo.item.service.EdoItemVService;
import org.kuali.kpme.edo.item.type.dao.EdoItemTypeDao;
import org.kuali.kpme.edo.item.type.service.EdoItemTypeService;
import org.kuali.kpme.edo.notification.service.EdoNotificationService;
import org.kuali.kpme.edo.reports.dao.EdoPromotionAndTenureReportViewDao;
import org.kuali.kpme.edo.reports.service.EdoPromotionAndTenureReportViewService;
import org.kuali.kpme.edo.reviewlayerdef.service.EdoReviewLayerDefinitionService;
import org.kuali.kpme.edo.role.service.EdoRoleMaintenanceService;
import org.kuali.kpme.edo.submitButton.service.EdoDisplaySubmitButtonService;
import org.kuali.kpme.edo.supplemental.service.EdoSupplementalTrackingService;
import org.kuali.kpme.edo.vote.service.EdoVoteRecordService;
import org.kuali.kpme.edo.workflow.service.DossierProcessDocumentHeaderService;
import org.kuali.kpme.edo.workflow.service.EdoSupplementalPendingStatusService;
import org.kuali.kpme.edo.workflow.service.EdoWorkflowDefinitionService;
import org.kuali.rice.krad.service.DataDictionaryService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class EdoServiceLocator implements ApplicationContextAware {

	public static String SPRING_BEANS = "classpath:org/kuali/kpme/pm/config/EdoSpringBeans.xml";
	public static ApplicationContext SPRING_APPLICATION_CONTEXT;
	public static final String EDO_DATASOURCE = "dataSource";
	public static final String KR_DATASOURCE = "riceDataSource";
	public static final String DATA_DICTIONARY_SERVICE = "dataDictionaryService";
	
	public static final String EDO_CANDIDATE_LIST_DAO = "edoCandidateListDao";
    public static final String EDO_CHECKLIST_VIEW_DAO = "edoChecklistViewDao";
    public static final String EDO_CHECKLIST_ITEM_DAO = "edoChecklistItemDao";
	public static final String EDO_ITEM_DAO = "edoItemDao";
    public static final String EDO_ITEM_V_DAO = "edoItemVDao";
    public static final String EDO_ITEM_COUNT_V_DAO = "edoItemCountVDao";
    public static final String EDO_DOSSIER_TYPE_DAO = "edoDossierTypeDao";
    public static final String EDO_ITEM_TYPE_DAO = "edoItemTypeDao";
    public static final String EDO_DOSSIER_DAO = "edoDossier";
    public static final String EDO_PROMOTIONANDTENURE_REPORT_VIEW_DAO = "edoPromotionAndTenureReportViewDao";

    public static final String CANDIDATE_SERVICE = "candidateService";
    public static final String CHECKLIST_VIEW_SERVICE = "edoChecklistViewService";
    public static final String CHECKLIST_ITEM_SERVICE = "edoChecklistItemService";
//    public static final String CHECKLIST_SERVICE = "edoChecklistService";
    public static final String ITEM_SERVICE = "edoItemService";
    public static final String ITEM_V_SERVICE = "edoItemVService";
    public static final String ITEM_COUNT_V_SERVICE = "edoItemCountVService";
    public static final String DOSSIER_TYPE_SERVICE = "edoDossierTypeService";
    public static final String DOSSIER_SERVICE = "edoDossierService";
    public static final String CANDIDATEDOSSIER_SERVICE = "edoCandidateDossierService";
    public static final String ITEM_TYPE_SERVICE = "edoItemTypeService";
    public static final String VOTE_RECORD_SERVICE = "edoVoteRecordService";
    public static final String REVIEW_LAYER_DEFINITION_SERVICE = "edoReviewLayerDefinitionService";
    public static final String DIGITAL_ASSET_SERVICE = "DigitalAssetService";
	public static final String DOSSIER_PROCESS_DOCUMENT_HEADER_SERVICE = "dossierProcessDocumentHeaderService";
    public static final String DOSSIER_PENDING_SUPPLEMENTAL_STATUS_SERVICE = "edoPendingSupplementalStatusService";
	public static final String SUPP_TRACKING_SERVICE = "edoSupplementalTrackingService";
    public static final String WORKFLOW_DEFINITION_SERVICE = "edoWorkflowDefinitionService";
	//EDO-33
    private static final String AUTHORIZATION_SERVICE = "authorizationService";
    private static final String MAINTENANCE_SERVICE = "maintenanceService";

    public static final String NOTIFICATION_SERVICE = "edoNotificationService";

    public static final String EDO_GROUP_SERVICE = "edoGroupService";
    public static final String EDO_GROUP_DEFINITION_SERVICE = "edoGroupDefinitionService";
    public static final String EDO_GROUP_TRACKING_SERVICE = "edoGroupTrackingService";
    public static final String EDO_ROLE_RESPONSIBILITY_SERVICE = "edoRoleResponsibilityService";
    public static final String EDO_ROLE_MAINTENANCE_SERVICE = "edoRoleMaintenanceService";
    public static final String EDO_DISPLAY_SUBMIT_BUTTON_SERVICE= "edoDisplaySubmitButtonService";

    
    public static final String EDO_PROMOTIONANDTENURE_REPORT_VIEW_SERVICE = "edoPromotionAndTenureReportViewService";

    public static DigitalAssetService getDigitalAssetService() {
        return (DigitalAssetService) SPRING_APPLICATION_CONTEXT.getBean(DIGITAL_ASSET_SERVICE);
    }

    public static DataSource getDataSource() {
		return (DataSource) SPRING_APPLICATION_CONTEXT.getBean(EDO_DATASOURCE);
	}

	public static DataSource getKrDataSource() {
		return (DataSource) SPRING_APPLICATION_CONTEXT.getBean(KR_DATASOURCE);
	}

	public static DataSource getDataSource(String name) {
		return (DataSource) SPRING_APPLICATION_CONTEXT.getBean(name);
	}
	
	public static DataDictionaryService getDataDictionaryService() {
		return (DataDictionaryService) SPRING_APPLICATION_CONTEXT.getBean(DATA_DICTIONARY_SERVICE);
	}
	
	public static EdoCandidateDao getEdoCandidateListDao(){
		return (EdoCandidateDao)SPRING_APPLICATION_CONTEXT.getBean(EDO_CANDIDATE_LIST_DAO);
	}

	public static CandidateService getCandidateService(){
		return (CandidateService)SPRING_APPLICATION_CONTEXT.getBean(CANDIDATE_SERVICE);
	}

    public static EdoChecklistDao getEdoChecklistViewDao(){
        return (EdoChecklistDao)SPRING_APPLICATION_CONTEXT.getBean(EDO_CHECKLIST_VIEW_DAO);
    }

    public static EdoChecklistItemDao getEdoChecklistItemDao() {
        return (EdoChecklistItemDao)SPRING_APPLICATION_CONTEXT.getBean(EDO_CHECKLIST_ITEM_DAO);
    }

    public static EdoChecklistService getChecklistViewService(){
        return (EdoChecklistService)SPRING_APPLICATION_CONTEXT.getBean(CHECKLIST_VIEW_SERVICE);
    }

    public static EdoChecklistItemService getChecklistItemService() {
        return (EdoChecklistItemService) SPRING_APPLICATION_CONTEXT.getBean(CHECKLIST_ITEM_SERVICE);
    }
//    public static EdoChecklistService getChecklistService(){
//        return (EdoChecklistService)SPRING_APPLICATION_CONTEXT.getBean(CHECKLIST_SERVICE);
//    }

    public static EdoItemDao getEdoItemDao() {
        return (EdoItemDao)SPRING_APPLICATION_CONTEXT.getBean(EDO_ITEM_DAO);
    }

    public static EdoItemService getEdoItemService(){
        return (EdoItemService)SPRING_APPLICATION_CONTEXT.getBean(ITEM_SERVICE);
    }

    public static EdoItemVDao getEdoItemVDao() {
        return (EdoItemVDao)SPRING_APPLICATION_CONTEXT.getBean(EDO_ITEM_V_DAO);
    }

    public static EdoItemVService getEdoItemVService(){
        return (EdoItemVService)SPRING_APPLICATION_CONTEXT.getBean(ITEM_V_SERVICE);
    }

    public static EdoItemCountVService getEdoItemCountVService(){
        return (EdoItemCountVService)SPRING_APPLICATION_CONTEXT.getBean(ITEM_COUNT_V_SERVICE);
    }

    public static EdoDossierTypeDao getEdoDossierTypeDao() {
        return (EdoDossierTypeDao)SPRING_APPLICATION_CONTEXT.getBean(EDO_DOSSIER_TYPE_DAO);
    }

    public static EdoDossierTypeService getEdoDossierTypeService(){
        return (EdoDossierTypeService)SPRING_APPLICATION_CONTEXT.getBean(DOSSIER_TYPE_SERVICE);
    }

    public static EdoDossierDao getEdoDossierDao() {
        return (EdoDossierDao)SPRING_APPLICATION_CONTEXT.getBean(EDO_DOSSIER_DAO);
    }

    public static EdoDossierService getEdoDossierService(){
        return (EdoDossierService)SPRING_APPLICATION_CONTEXT.getBean(DOSSIER_SERVICE);
    }

    public static EdoCandidateDossierService getEdoCandidateDossierService() {
        return (EdoCandidateDossierService)SPRING_APPLICATION_CONTEXT.getBean(CANDIDATEDOSSIER_SERVICE);
    }

    public static EdoItemTypeDao getEdoItemTypeDao() {
        return (EdoItemTypeDao)SPRING_APPLICATION_CONTEXT.getBean(EDO_ITEM_TYPE_DAO);
    }

    public static EdoItemTypeService getEdoItemTypeService(){
        return (EdoItemTypeService)SPRING_APPLICATION_CONTEXT.getBean(ITEM_TYPE_SERVICE);
    }

    public static EdoVoteRecordService getEdoVoteRecordService() {
            return (EdoVoteRecordService)SPRING_APPLICATION_CONTEXT.getBean(VOTE_RECORD_SERVICE);
        }

    public static EdoReviewLayerDefinitionService getEdoReviewLayerDefinitionService() {
        return (EdoReviewLayerDefinitionService)SPRING_APPLICATION_CONTEXT.getBean(REVIEW_LAYER_DEFINITION_SERVICE);
    }

    //EDO-33
	public static AuthorizationService getAuthorizationService(){
		return (AuthorizationService)SPRING_APPLICATION_CONTEXT.getBean(AUTHORIZATION_SERVICE);
	}
	public static MaintenanceService getEdoMaintenanceService(){
		return (MaintenanceService) SPRING_APPLICATION_CONTEXT.getBean(MAINTENANCE_SERVICE);
	}
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SPRING_APPLICATION_CONTEXT = applicationContext;
	}

    public static JdbcTemplate getEdoJdbcTemplate() {
        return (JdbcTemplate) SPRING_APPLICATION_CONTEXT.getBean("edoJdbcTemplate");
    }
    //work flow service
    public static DossierProcessDocumentHeaderService getDossierProcessDocumentHeaderService() {
    	 return (DossierProcessDocumentHeaderService)SPRING_APPLICATION_CONTEXT.getBean(DOSSIER_PROCESS_DOCUMENT_HEADER_SERVICE);
	}

    public static EdoSupplementalPendingStatusService getEdoSupplementalPendingStatusService() {
        return (EdoSupplementalPendingStatusService)SPRING_APPLICATION_CONTEXT.getBean(DOSSIER_PENDING_SUPPLEMENTAL_STATUS_SERVICE);
    }

    public static EdoNotificationService getEdoNotificationService() {
        return (EdoNotificationService) SPRING_APPLICATION_CONTEXT.getBean(NOTIFICATION_SERVICE);
    }
	//Supplemental Tracking Service
    public static EdoSupplementalTrackingService getEdoSupplementalTrackingService() {
        return (EdoSupplementalTrackingService)SPRING_APPLICATION_CONTEXT.getBean(SUPP_TRACKING_SERVICE);
    }

    public static EdoGroupService getEdoGroupService(){
        return (EdoGroupService) SPRING_APPLICATION_CONTEXT.getBean(EDO_GROUP_SERVICE);
    }

    public static EdoGroupDefinitionService getEdoGroupDefinitionService() {
        return (EdoGroupDefinitionService) SPRING_APPLICATION_CONTEXT.getBean(EDO_GROUP_DEFINITION_SERVICE);
    }

    public static EdoGroupTrackingService getEdoGroupTrackingService() {
        return (EdoGroupTrackingService) SPRING_APPLICATION_CONTEXT.getBean(EDO_GROUP_TRACKING_SERVICE);
    }

    public static EdoRoleResponsibilityService getEdoRoleResponsibilityService() {
        return (EdoRoleResponsibilityService) SPRING_APPLICATION_CONTEXT.getBean(EDO_ROLE_RESPONSIBILITY_SERVICE);
    }

    public static EdoRoleMaintenanceService getEdoRoleMaintenanceService() {
        return (EdoRoleMaintenanceService) SPRING_APPLICATION_CONTEXT.getBean(EDO_ROLE_MAINTENANCE_SERVICE);
    }
    
    public static EdoPromotionAndTenureReportViewDao getEdoPromotionAndTenureReportViewDao() {
    	return (EdoPromotionAndTenureReportViewDao) SPRING_APPLICATION_CONTEXT.getBean(EDO_PROMOTIONANDTENURE_REPORT_VIEW_DAO);
    }
    
    public static EdoPromotionAndTenureReportViewService getEdoPromotionAndTenureReportViewService() {
    	return (EdoPromotionAndTenureReportViewService) SPRING_APPLICATION_CONTEXT.getBean(EDO_PROMOTIONANDTENURE_REPORT_VIEW_SERVICE);
    }

    public static EdoWorkflowDefinitionService getEdoWorkflowDefinitionService() {
        return (EdoWorkflowDefinitionService) SPRING_APPLICATION_CONTEXT.getBean(WORKFLOW_DEFINITION_SERVICE);
    }
    
    public static EdoDisplaySubmitButtonService getEdoDisplaySubmitButtonService() {
        return (EdoDisplaySubmitButtonService) SPRING_APPLICATION_CONTEXT.getBean(EDO_DISPLAY_SUBMIT_BUTTON_SERVICE);
    }
    
}
