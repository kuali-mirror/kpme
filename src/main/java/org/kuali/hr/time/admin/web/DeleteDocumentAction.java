package org.kuali.hr.time.admin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import java.util.List;

public class DeleteDocumentAction extends TkAction {
	
	private static final Logger LOG = Logger.getLogger(DeleteDocumentAction.class);

    public ActionForward deleteDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	DeleteDocumentForm deleteDocumentForm = (DeleteDocumentForm) form;
    	String documentId = deleteDocumentForm.getDeleteDocumentId();


    	if (StringUtils.isNotBlank(documentId)) {
            TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
            if(tdh!=null){
                TkServiceLocator.getTimeBlockService().deleteTimeBlocksAssociatedWithDocumentId(documentId);
    		    TkServiceLocator.getTimesheetService().deleteTimesheet(documentId);
            } else {
                List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocks(tdh.getPrincipalId(),tdh.getBeginDate(),tdh.getEndDate());
                for(LeaveBlock leaveBlock : leaveBlocks){
                    TkServiceLocator.getLeaveBlockService().deleteLeaveBlock(Long.parseLong(leaveBlock.getLmLeaveBlockId()));
                }

                TkServiceLocator.getLeaveCalendarDocumentHeaderService().deleteLeaveCalendarHeader(documentId);
            }
    		LOG.debug("Deleting document: " + documentId);
    	}
    	
    	return mapping.findForward("basic");
    }

}