package org.kuali.kpme.edo.reports.dao;

import java.util.List;
import java.util.Map;

import org.kuali.kpme.edo.reports.EdoPromotionAndTenureReport;


public interface EdoPromotionAndTenureReportViewDao {

	public List<EdoPromotionAndTenureReport> getAllPromotionAndTenureReports();
	
	public List<EdoPromotionAndTenureReport> getPromotionAndTenureReportsByDossierType(String dossierTypeName);
	
	public List<EdoPromotionAndTenureReport> getPromotionAndTenureReportsBySearchCriteria(Map<String, ? extends Object> searchCriteria);
	
	public List<String> getDistinctDossierTypeList();
	
	public List<String> getDistinctDepartmentList();
	
	public List<String> getDistinctSchoolList();
	
	public List<String> getDistinctWorkflowList();
	
	public List<Integer> getDistinctVoteRoundList();
}
