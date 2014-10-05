package org.kuali.kpme.edo.reports.service;

import java.util.List;
import java.util.Map;

import org.kuali.kpme.edo.reports.EdoPromotionAndTenureReport;


public interface EdoPromotionAndTenureReportViewService {

	public List<EdoPromotionAndTenureReport> getAllPromotionAndTenureReports();

	public List<EdoPromotionAndTenureReport> getPromotionAndTenureReportsByDossierType(String dossierTypeName);
	
	public List<EdoPromotionAndTenureReport> getPromotionAndTenureReportsBySearchCriteria(Map<String, ? extends Object> searchCriteria);
	
	public List<EdoPromotionAndTenureReport> getFormattedPromotionAndTenureReports();
	
	public List<EdoPromotionAndTenureReport> getFormattedPromotionAndTenureReports(Map<String, ? extends Object> searchCriteria);
	
	public String getPromotionAndTenureReportHeadersByWorkflow(String workflowId);
	
	public List<String> getDistinctDossierTypeList();
	
	public List<String> getDistinctDepartmentList();
	
	public List<String> getDistinctSchoolList();
	
	public List<String> getDistinctWorkflowList();
	
	public Map<Integer, String> getDistinctVoteRoundList();
}
