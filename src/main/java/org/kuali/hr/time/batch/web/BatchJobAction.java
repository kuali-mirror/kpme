package org.kuali.hr.time.batch.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.batch.BatchApproveMissedPunchJobRunnable;
import org.kuali.hr.time.batch.BatchJobEntry;
import org.kuali.hr.time.batch.EmployeeApprovalBatchJobRunnable;
import org.kuali.hr.time.batch.InitiateBatchJobRunnable;
import org.kuali.hr.time.batch.PayPeriodEndBatchJobRunnable;
import org.kuali.hr.time.batch.SupervisorApprovalBatchJobRunnable;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

public class BatchJobAction extends TkAction {

    private static final Logger LOG = Logger.getLogger(BatchJobAction.class);

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

    public ActionForward runBatchJob(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BatchJobActionForm bjaf = (BatchJobActionForm) form;

        Map<String, Object> searchCrit = new HashMap<String, Object>();
        searchCrit.put("batchJobName", bjaf.getSelectedBatchJob());
        searchCrit.put("hrPyCalendarEntryId", bjaf.getHrPyCalendarEntryId());
        searchCrit.put("batchJobEntryStatus", TkConstants.BATCH_JOB_ENTRY_STATUS.SCHEDULED);

        List<BatchJobEntry> batchJobEntries = TkServiceLocator.getBatchJobEntryService().getBatchJobEntries(searchCrit);

        if (batchJobEntries.size() > 0) {

            for (BatchJobEntry entry : batchJobEntries) {

                long startTime = System.currentTimeMillis();
                LOG.debug("Before run.");
                entry.setBatchJobEntryStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.RUNNING);
                TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(entry);

                if (StringUtils.equals(entry.getBatchJobName(), TkConstants.BATCH_JOB_NAMES.APPROVE)) {
                    LOG.debug("Creating EmployeeApprovalBatchJobRunnable.");
                    new EmployeeApprovalBatchJobRunnable(entry).doWork();
                } else if (StringUtils.equals(entry.getBatchJobName(), TkConstants.BATCH_JOB_NAMES.PAY_PERIOD_END)) {
                    LOG.debug("Creating PayPeriodEndBatchJobRunnable.");
                    new PayPeriodEndBatchJobRunnable(entry).doWork();
                } else if (StringUtils.equals(entry.getBatchJobName(), TkConstants.BATCH_JOB_NAMES.SUPERVISOR_APPROVAL)) {
                    LOG.debug("Creating SupervisorApprovalBatchJobRunnabble.");
                    new SupervisorApprovalBatchJobRunnable(entry).doWork();
                } else if (StringUtils.equals(entry.getBatchJobName(), TkConstants.BATCH_JOB_NAMES.INITIATE)) {
                    LOG.debug("Creating InitiateBatchJobRunnable.");
                    new InitiateBatchJobRunnable(entry).doWork();
                } else if (StringUtils.equals(entry.getBatchJobName(), TkConstants.BATCH_JOB_NAMES.BATCH_APPROVE_MISSED_PUNCH)) {
                    LOG.debug("Creating BatchApproveMissedPunchJobRunnable.");
                    new BatchApproveMissedPunchJobRunnable(entry).doWork();
                } else {
                    LOG.warn("Unknown BatchJobEntryRunnable found in BatchJobEntry table. Unable to create Runnable.");
                }

                long endTime = System.currentTimeMillis();
                long runtime = endTime - startTime;
                runtime = (runtime > 0) ? runtime : 1; // hack around 0 length job... just in case.
                LOG.debug("Job finished in " + runtime / 1000 + " seconds.");

                if (StringUtils.isEmpty(entry.getBatchJobException())) {
                    entry.setBatchJobEntryStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.FINISHED);
                } else {
                    entry.setBatchJobEntryStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.EXCEPTION);
                }
                TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(entry);
            }

        }
        return mapping.findForward("basic");
    }


}
