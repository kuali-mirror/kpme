package org.kuali.hr.time.batch.web;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.batch.BatchJobEntry;
import org.kuali.hr.time.service.base.TkServiceLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class BatchJobAction extends TkAction {

    public ActionForward getBatchJobEntryStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BatchJobActionForm bjaf = (BatchJobActionForm) form;
        Map<String, Object> searchCrit = new HashMap<String, Object>();
        searchCrit.put("tkBatchJobId", bjaf.getBatchJobId());
        searchCrit.put("batchJobName", bjaf.getBatchJobName());
        searchCrit.put("batchJobEntryStatus", bjaf.getBatchJobEntryStatus());
        searchCrit.put("hrPyCalendarEntryId", bjaf.getHrPyCalendarEntryId());
        searchCrit.put("ipAddress", bjaf.getIpAddress());
        searchCrit.put("documentId", bjaf.getDocumentId());
        searchCrit.put("principalId", bjaf.getPrincipalId());

        bjaf.setBatchJobEntries(TkServiceLocator.getBatchJobEntryService().getBatchJobEntries(searchCrit));

        return mapping.findForward("basic");
    }

    public ActionForward changeIpAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BatchJobActionForm bjaf = (BatchJobActionForm) form;

        BatchJobEntry batchJobEntry = TkServiceLocator.getBatchJobEntryService().getBatchJobEntry(Long.valueOf(bjaf.getTkBatchJobEntryId()));
        batchJobEntry.setIpAddress(bjaf.getIpToChange());
        TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(batchJobEntry);

        return mapping.findForward("basic");
    }
}
