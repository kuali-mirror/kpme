package org.kuali.kpme.edo.reports.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.common.util.StringUtils;
import org.kuali.kpme.edo.reports.EdoPromotionAndTenureReport;
import org.kuali.kpme.edo.reports.dao.EdoPromotionAndTenureReportViewDao;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.reviewlayerdef.service.EdoReviewLayerDefinitionService;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoPropertyConstants;
import org.kuali.rice.krad.service.DataDictionaryService;
import org.kuali.rice.krad.util.ObjectUtils;

public class EdoPromotionAndTenureReportViewServiceImpl implements EdoPromotionAndTenureReportViewService {

    private EdoPromotionAndTenureReportViewDao edoPromotionAndTenureReportViewDao;
    private EdoReviewLayerDefinitionService edoReviewLayerDefinitionService;

	@Override
	public List<EdoPromotionAndTenureReport> getAllPromotionAndTenureReports() {
		return getEdoPromotionAndTenureReportViewDao().getAllPromotionAndTenureReports();
	}        

	@Override
	public List<EdoPromotionAndTenureReport> getPromotionAndTenureReportsByDossierType(String dossierTypeName) {
		return getEdoPromotionAndTenureReportViewDao().getPromotionAndTenureReportsByDossierType(dossierTypeName);
	}
	
	@Override
	public List<EdoPromotionAndTenureReport> getPromotionAndTenureReportsBySearchCriteria(Map<String, ? extends Object> searchCriteria) {
		return getEdoPromotionAndTenureReportViewDao().getPromotionAndTenureReportsBySearchCriteria(searchCriteria);
	}	
    
	@Override
	public List<EdoPromotionAndTenureReport> getFormattedPromotionAndTenureReports() {
		
		return getFormattedPromotionAndTenureReports(null);
	}
	
	@Override
	public List<EdoPromotionAndTenureReport> getFormattedPromotionAndTenureReports(Map<String, ? extends Object> searchCriteria) {
		
		//set up a map of vote records based on Dossier Ids
		Map<BigDecimal, EdoPromotionAndTenureReport> candidateDossierMap = new HashMap<BigDecimal, EdoPromotionAndTenureReport>();	

		List<EdoPromotionAndTenureReport> voteRecords = new ArrayList<EdoPromotionAndTenureReport>();
		
		if (ObjectUtils.isNull(searchCriteria) || searchCriteria.isEmpty()) { 
			voteRecords = getAllPromotionAndTenureReports();
		}
		else {		
			voteRecords = getPromotionAndTenureReportsBySearchCriteria(searchCriteria);
		}
		
		for(EdoPromotionAndTenureReport voteRecord : voteRecords) {						
			addVoteRecordToCandidateDossierMap(candidateDossierMap, voteRecord);
		}
		
		List<EdoPromotionAndTenureReport> dossiers = new ArrayList<EdoPromotionAndTenureReport>();
		dossiers.addAll(candidateDossierMap.values());
		return dossiers;
	}
	
	@Override
	public String getPromotionAndTenureReportHeadersByWorkflow(String workflowId) {
		
    	String headers = "";
    	
    	int index = -1;
    	
    	headers =                getPromotionAndTenureReportHeaderForAttribute(++index, EdoPropertyConstants.EdoPromotionAndTenureReportFields.LAST_NAME, true);
    	headers = headers.concat(getPromotionAndTenureReportHeaderForAttribute(++index, EdoPropertyConstants.EdoPromotionAndTenureReportFields.FIRST_NAME, true));
    	headers = headers.concat(getPromotionAndTenureReportHeaderForAttribute(++index, EdoPropertyConstants.EdoPromotionAndTenureReportFields.EARLY, true));
    	headers = headers.concat(getPromotionAndTenureReportHeaderForAttribute(++index, EdoPropertyConstants.EdoPromotionAndTenureReportFields.AREA_OF_EXCELLENCE, true));
    	headers = headers.concat(getPromotionAndTenureReportHeaderForAttribute(++index, EdoPropertyConstants.EdoPromotionAndTenureReportFields.CURRENT_RANK, true));
    	headers = headers.concat(getPromotionAndTenureReportHeaderForAttribute(++index, EdoPropertyConstants.EdoPromotionAndTenureReportFields.RANK_SOUGHT, true));
    	headers = headers.concat(getPromotionAndTenureReportHeaderForAttribute(++index, EdoPropertyConstants.EdoPromotionAndTenureReportFields.DEPARTMENT_ID, true));
    	headers = headers.concat(getPromotionAndTenureReportHeaderForAttribute(++index, EdoPropertyConstants.EdoPromotionAndTenureReportFields.SCHOOL_ID, true));
    	headers = headers.concat(getPromotionAndTenureReportHeaderForAttribute(++index, EdoPropertyConstants.EdoPromotionAndTenureReportFields.GENDER, true));
    	headers = headers.concat(getPromotionAndTenureReportHeaderForAttribute(++index, EdoPropertyConstants.EdoPromotionAndTenureReportFields.ETHNICITY, true));
    	headers = headers.concat(getPromotionAndTenureReportHeaderForAttribute(++index, EdoPropertyConstants.EdoPromotionAndTenureReportFields.CITIZENSHIP_STATUS, true));
    	
        for(EdoReviewLayerDefinition reviewLayer : getEdoReviewLayerDefinitionService().getReviewLayerDefinitionsByWorkflowId(workflowId)) {
        	
        	String label = reviewLayer.getDescription().trim();
        	
        	if (reviewLayer.getVoteType().equals(EdoConstants.VOTE_TYPE_SINGLE)) {
            	headers = headers.concat(getPromotionAndTenureReportHeaderForReviewLayer(++index, label, "", false));
        	}
        	else {
            	headers = headers.concat(getPromotionAndTenureReportHeaderForReviewLayer(++index, label, EdoConstants.VOTE_RECORD_VALUE_YES, false));
            	headers = headers.concat(getPromotionAndTenureReportHeaderForReviewLayer(++index, label, EdoConstants.VOTE_RECORD_VALUE_NO, false));
            	headers = headers.concat(getPromotionAndTenureReportHeaderForReviewLayer(++index, label, EdoConstants.VOTE_RECORD_VALUE_ABSTAIN, false));
            	headers = headers.concat(getPromotionAndTenureReportHeaderForReviewLayer(++index, label, EdoConstants.VOTE_RECORD_VALUE_ABSENT, false));
        	}
        }
    	
    	headers = headers.concat(getPromotionAndTenureReportHeaderForAttribute(++index, EdoPropertyConstants.EdoPromotionAndTenureReportFields.DOSSIER_TYPE_NAME, true));
    	headers = headers.concat(getPromotionAndTenureReportHeaderForAttribute(++index, EdoPropertyConstants.EdoPromotionAndTenureReportFields.VOTE_ROUND_NAME, true));
    	
        return headers;
	}
	
	protected String getPromotionAndTenureReportHeaderForAttribute(int index, String reportAttribute, boolean isVisible) {
		
		String attributeLabel = getDataDictionaryService().getAttributeLabel(EdoPromotionAndTenureReport.class, reportAttribute);
		
		String header = "{   aTargets: ["+ index +"], sTitle: \""+ attributeLabel +"\", bVisible: "+ isVisible +" },";
		
		return header;
	}
	
	protected String getPromotionAndTenureReportHeaderForReviewLayer(int index, String label, String voteRecordValue, boolean isVisible) {
		
		if (!StringUtils.isEmpty(voteRecordValue)) {
			voteRecordValue = " "+voteRecordValue;
		}
		
		return "{   aTargets: ["+ index +"], sTitle: \""+ label + voteRecordValue +"\", bVisible: "+ isVisible +" },";				
	}

	@Override
	public List<String> getDistinctDossierTypeList() {
		return getEdoPromotionAndTenureReportViewDao().getDistinctDossierTypeList();
	}
	
	@Override
	public List<String> getDistinctDepartmentList() {
		return getEdoPromotionAndTenureReportViewDao().getDistinctDepartmentList();
	}

	@Override
	public List<String> getDistinctSchoolList() {
		return getEdoPromotionAndTenureReportViewDao().getDistinctSchoolList();
	}

	@Override
	public List<String> getDistinctWorkflowList() {
		return getEdoPromotionAndTenureReportViewDao().getDistinctWorkflowList();
	}

	@Override
	public Map<Integer, String> getDistinctVoteRoundList() {
		Map<Integer, String> voteRounds = new HashMap<Integer, String>();
		
		List<Integer> voteRoundCodes = getEdoPromotionAndTenureReportViewDao().getDistinctVoteRoundList();
		
		for(Integer voteRound : voteRoundCodes) {
            voteRounds.put(voteRound, EdoConstants.VOTE_ROUNDS.get(voteRound));
		}
		
		return voteRounds;
	}

	protected void addVoteRecordToCandidateDossierMap(Map<BigDecimal, EdoPromotionAndTenureReport> candidateDossierMap, EdoPromotionAndTenureReport voteRecord) {
		
		EdoPromotionAndTenureReport candidateDossier = candidateDossierMap.get(voteRecord.getDossierId());
		
		//if the candidate's dossier is not in the map, add it
		if (ObjectUtils.isNull(candidateDossier)) {			
			candidateDossier = voteRecord;
			
			//set the list of route levels which contain review levels
			candidateDossier.setReviewRouteLevels(getEdoReviewLayerDefinitionService().getReviewLayerDefinitionsByWorkflowId(voteRecord.getWorkflowId()));
			
			candidateDossierMap.put(candidateDossier.getDossierId(),  candidateDossier);
		}				
		
		//add this vote record to the candidate's dossier
		addVoteRecordToRouteLevelMap(candidateDossier, voteRecord);
	}
	
	protected void addVoteRecordToRouteLevelMap(EdoPromotionAndTenureReport candidateDossier, EdoPromotionAndTenureReport voteRecord) {
		
		Map<BigDecimal, EdoPromotionAndTenureReport> voteRecordsByRouteLevelMap = candidateDossier.getVoteRecordMap();
		
		//if the vote record map for this dossier is null create one and add it to the candidate's dossier
		if (ObjectUtils.isNull(voteRecordsByRouteLevelMap)) {
			voteRecordsByRouteLevelMap = new HashMap<BigDecimal, EdoPromotionAndTenureReport>();
			candidateDossier.setVoteRecordMap(voteRecordsByRouteLevelMap);
		}
		
		//add the vote record to the vote record map for the appropriate route level
		voteRecordsByRouteLevelMap.put(voteRecord.getRouteLevel(),  voteRecord);		
	}

	public EdoPromotionAndTenureReportViewDao getEdoPromotionAndTenureReportViewDao() {
		return edoPromotionAndTenureReportViewDao;
	}

	public void setEdoPromotionAndTenureReportViewDao(EdoPromotionAndTenureReportViewDao edoPromotionAndTenureReportViewDao) {
		this.edoPromotionAndTenureReportViewDao = edoPromotionAndTenureReportViewDao;
	}

	public EdoReviewLayerDefinitionService getEdoReviewLayerDefinitionService() {
		return edoReviewLayerDefinitionService;
	}

	public void setEdoReviewLayerDefinitionService(EdoReviewLayerDefinitionService edoReviewLayerDefinitionService) {
		this.edoReviewLayerDefinitionService = edoReviewLayerDefinitionService;
	}    	

    public DataDictionaryService getDataDictionaryService() {
    	return EdoServiceLocator.getDataDictionaryService();
    }
}
