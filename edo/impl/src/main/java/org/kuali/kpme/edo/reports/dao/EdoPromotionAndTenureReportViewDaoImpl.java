package org.kuali.kpme.edo.reports.dao;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.kpme.edo.reports.EdoPromotionAndTenureReport;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.krad.util.ObjectUtils;

import static org.kuali.kpme.edo.util.EdoPropertyConstants.EdoPromotionAndTenureReportFields;

public class EdoPromotionAndTenureReportViewDaoImpl extends PlatformAwareDaoBaseOjb implements EdoPromotionAndTenureReportViewDao {
	
	@Override
	public List<EdoPromotionAndTenureReport> getAllPromotionAndTenureReports() {
		
        List<EdoPromotionAndTenureReport> reports = new ArrayList<EdoPromotionAndTenureReport>();
		
		Criteria criteria = new Criteria();		

		ReportQueryByCriteria query = QueryFactory.newReportQuery(EdoPromotionAndTenureReport.class, criteria);
		query.addOrderByAscending(EdoPromotionAndTenureReportFields.DOSSIER_ID);
		query.addOrderByAscending(EdoPromotionAndTenureReportFields.ROUTE_LEVEL);
		
        Collection results = getPersistenceBrokerTemplate().getCollectionByQuery(query);
        
        if (ObjectUtils.isNotNull(results) && !results.isEmpty()) {
        	reports.addAll(results);
        }
        
        return reports;
	}

	@Override
	public List<EdoPromotionAndTenureReport> getPromotionAndTenureReportsByDossierType(String dossierTypeName) {
		
        List<EdoPromotionAndTenureReport> reports = new ArrayList<EdoPromotionAndTenureReport>();
		
		Criteria criteria = new Criteria();
		criteria.addEqualTo(EdoPromotionAndTenureReportFields.DOSSIER_TYPE_NAME, dossierTypeName);
		
		ReportQueryByCriteria query = QueryFactory.newReportQuery(EdoPromotionAndTenureReport.class, criteria);
		query.addOrderByAscending(EdoPromotionAndTenureReportFields.DOSSIER_ID);
		query.addOrderByAscending(EdoPromotionAndTenureReportFields.ROUTE_LEVEL);

        Collection results = getPersistenceBrokerTemplate().getCollectionByQuery(query);
        
        if (ObjectUtils.isNotNull(results) && !results.isEmpty()) {
        	reports.addAll(results);
        }
        
        return reports;
	}	
	
	@Override
	public List<EdoPromotionAndTenureReport> getPromotionAndTenureReportsBySearchCriteria(Map<String, ? extends Object> searchCriteria) {
		
        List<EdoPromotionAndTenureReport> reports = new ArrayList<EdoPromotionAndTenureReport>();
		
		Criteria criteria = new Criteria();
		for(String key : searchCriteria.keySet()) {
			criteria.addEqualTo(key, searchCriteria.get(key));			
		}
		
		ReportQueryByCriteria query = QueryFactory.newReportQuery(EdoPromotionAndTenureReport.class, criteria);
		query.addOrderByAscending(EdoPromotionAndTenureReportFields.DOSSIER_ID);
		query.addOrderByAscending(EdoPromotionAndTenureReportFields.ROUTE_LEVEL);

        Collection results = getPersistenceBrokerTemplate().getCollectionByQuery(query);
        
        if (ObjectUtils.isNotNull(results) && !results.isEmpty()) {
        	reports.addAll(results);
        }
        
        return reports;
	}

	@Override
	public List<String> getDistinctDossierTypeList() {
		List<String> dossierTypes = new ArrayList<String>();

		//Retrieve the distinct list of dossier types 		
		Criteria criteria = new Criteria();
		
		String[] attributes = new String[] {EdoPromotionAndTenureReportFields.DOSSIER_TYPE_NAME};				
		ReportQueryByCriteria query = QueryFactory.newReportQuery(EdoPromotionAndTenureReport.class, attributes, criteria, true);
		query.addOrderByAscending(EdoPromotionAndTenureReportFields.DOSSIER_TYPE_NAME);
		
        Iterator results = getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(query);
        while (results.hasNext()) {
        	Object[] row = (Object[])results.next();
        	dossierTypes.add((String)row[0]);
        }
        
        return dossierTypes;
	}

	@Override
	public List<String> getDistinctDepartmentList() {
		List<String> departments = new ArrayList<String>();

		//Retrieve the distinct list of departments 		
		Criteria criteria = new Criteria();
		
		String[] attributes = new String[] {EdoPromotionAndTenureReportFields.DEPARTMENT_ID};				
		ReportQueryByCriteria query = QueryFactory.newReportQuery(EdoPromotionAndTenureReport.class, attributes, criteria, true);
		query.addOrderByAscending(EdoPromotionAndTenureReportFields.DEPARTMENT_ID);
		
        Iterator results = getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(query);
        while (results.hasNext()) {
        	Object[] row = (Object[])results.next();
        	departments.add((String)row[0]);
        }
        
        return departments;
	}

	@Override
	public List<String> getDistinctSchoolList() {
		List<String> schools = new ArrayList<String>();

		//Retrieve the distinct list of schools 		
		Criteria criteria = new Criteria();
		
		String[] attributes = new String[] {EdoPromotionAndTenureReportFields.SCHOOL_ID};				
		ReportQueryByCriteria query = QueryFactory.newReportQuery(EdoPromotionAndTenureReport.class, attributes, criteria, true);
		query.addOrderByAscending(EdoPromotionAndTenureReportFields.SCHOOL_ID);
		
        Iterator results = getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(query);
        while (results.hasNext()) {
        	Object[] row = (Object[])results.next();
        	schools.add((String)row[0]);
        }
        
        return schools;
	}

	@Override
	public List<String> getDistinctWorkflowList() {
		List<String> workflows = new ArrayList<String>();

		//Retrieve the distinct list of workflows 		
		Criteria criteria = new Criteria();
		
		String[] attributes = new String[] {EdoPromotionAndTenureReportFields.WORKFLOW_ID};				
		ReportQueryByCriteria query = QueryFactory.newReportQuery(EdoPromotionAndTenureReport.class, attributes, criteria, true);
		query.addOrderByAscending(EdoPromotionAndTenureReportFields.WORKFLOW_ID);
		
        Iterator results = getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(query);
        while (results.hasNext()) {
        	Object[] row = (Object[])results.next();
        	workflows.add((String)row[0]);
        }
        
        return workflows;
	}

	@Override
	public List<Integer> getDistinctVoteRoundList() {
		List<Integer> voteRounds = new ArrayList<Integer>();

		//Retrieve the distinct list of vote rounds 		
		Criteria criteria = new Criteria();
		
		String[] attributes = new String[] {EdoPromotionAndTenureReportFields.VOTE_ROUND};				
		ReportQueryByCriteria query = QueryFactory.newReportQuery(EdoPromotionAndTenureReport.class, attributes, criteria, true);
		query.addOrderByAscending(EdoPromotionAndTenureReportFields.VOTE_ROUND);
		
        Iterator results = getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(query);
        while (results.hasNext()) {
        	Object[] row = (Object[])results.next();
        	//OJB thinks this is a BigDecimal instead of an Integer, but I don't know why
        	BigDecimal voteRound = (BigDecimal)row[0];        	
        	voteRounds.add(new Integer(voteRound.intValue()));
        }
        
        return voteRounds;
	}	
	
}
