package org.kuali.kpme.edo.reports.web;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.reports.EdoPromotionAndTenureReport;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoPropertyConstants;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 2/19/14
 * Time: 3:03 PM
 */
public class EdoPTReportAction extends EdoAction {
    private static final Logger LOG = Logger.getLogger(EdoPTReportAction.class);
    private static final String FORWARD_MAPPING_BASIC = "basic";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoPTReportForm reportForm = (EdoPTReportForm)form;
        
        List<String> dossierTypes = EdoServiceLocator.getEdoPromotionAndTenureReportViewService().getDistinctDossierTypeList();
        reportForm.setDossierTypes(dossierTypes);
        
        List<String> departments = EdoServiceLocator.getEdoPromotionAndTenureReportViewService().getDistinctDepartmentList();
        reportForm.setDepartments(departments);
        
        List<String> schools = EdoServiceLocator.getEdoPromotionAndTenureReportViewService().getDistinctSchoolList();
        reportForm.setSchools(schools);
        
        List<String> workflows = EdoServiceLocator.getEdoReviewLayerDefinitionService().getDistinctWorkflowIds();
        reportForm.setWorkflows(workflows);
        
        Map<Integer, String> voteRounds = EdoServiceLocator.getEdoPromotionAndTenureReportViewService().getDistinctVoteRoundList();
        reportForm.setVoteRounds(voteRounds);
        
        return super.execute(mapping, form, request, response);
    }
    
    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	EdoPTReportForm reportForm = (EdoPTReportForm)form;
    	
    	Map<String, Object> searchCriteria = convertSearchCriteriaToMap(reportForm);        	
    	
        String reportListJSON = "";
        String reportHeadersList = EdoServiceLocator.getEdoPromotionAndTenureReportViewService().getPromotionAndTenureReportHeadersByWorkflow((String)searchCriteria.get(EdoPropertyConstants.EdoReviewLayerDefinitionFields.WORKFLOW_ID));

        if (EdoContext.getUser().getCurrentRoleList().contains(EdoConstants.ROLE_SUPER_USER)) {        	
        	
        	List<EdoPromotionAndTenureReport> reports = EdoServiceLocator.getEdoPromotionAndTenureReportViewService().getFormattedPromotionAndTenureReports(searchCriteria);
        	reportForm.setReports(reports);
        	
            if (!reports.isEmpty()) {
                for (EdoPromotionAndTenureReport report : reports) {
                    reportListJSON = reportListJSON.concat(report.getPromotionAndTenureReportJSONString() + ",");
                }
            }            
        }
        
//        LOG.info(reportListJSON);                
        request.setAttribute("reportHeaders", reportHeadersList);
        request.setAttribute("reportJSON", reportListJSON);
    	
        return mapping.findForward(FORWARD_MAPPING_BASIC);
    }
    
    protected Map<String, Object> convertSearchCriteriaToMap(EdoPTReportForm form) {
    	Map<String, Object> searchCriteria = new HashMap<String, Object>();
    	
    	if (ObjectUtils.isNotNull(form.getDossierTypeName()) && StringUtils.isNotEmpty(form.getDossierTypeName())) {
    		searchCriteria.put(EdoPropertyConstants.EdoPromotionAndTenureReportFields.DOSSIER_TYPE_NAME, form.getDossierTypeName());
    	}
    	
    	if (ObjectUtils.isNotNull(form.getDepartmentId()) && StringUtils.isNotEmpty(form.getDepartmentId())) {
    		searchCriteria.put(EdoPropertyConstants.EdoPromotionAndTenureReportFields.DEPARTMENT_ID, form.getDepartmentId());
    	}
    	
    	if (ObjectUtils.isNotNull(form.getSchoolId()) && StringUtils.isNotEmpty(form.getSchoolId())) {
    		searchCriteria.put(EdoPropertyConstants.EdoPromotionAndTenureReportFields.SCHOOL_ID, StringUtils.upperCase(form.getSchoolId()));
    	}
    	
    	if (ObjectUtils.isNotNull(form.getWorkflowId()) && StringUtils.isNotEmpty(form.getWorkflowId())) {
    		searchCriteria.put(EdoPropertyConstants.EdoPromotionAndTenureReportFields.WORKFLOW_ID, form.getWorkflowId());
    	}
    	
    	if (ObjectUtils.isNotNull(form.getVoteRoundCode())) {
    		searchCriteria.put(EdoPropertyConstants.EdoPromotionAndTenureReportFields.VOTE_ROUND, form.getVoteRoundCode());
    	}
    	
    	return searchCriteria;
    }
}
