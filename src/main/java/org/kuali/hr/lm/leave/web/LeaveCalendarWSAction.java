package org.kuali.hr.lm.leave.web;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leavecalendar.validation.LeaveCalendarValidationService;
import org.kuali.hr.lm.leavecalendar.web.LeaveCalendarForm;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaveCalendarWSAction extends TkAction {

    private static final Logger LOG = Logger.getLogger(LeaveCalendarWSAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.execute(mapping, form, request, response);
    }

        
    public ActionForward getLeaveCodeInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	//System.out.println("Leave code info called >>>>>>>>>>>>>>>");
    	LeaveCalendarWSForm lcf = (LeaveCalendarWSForm) form;
        LOG.info(lcf.toString());
        LeaveCode leaveCode = TkServiceLocator.getLeaveCodeService().getLeaveCode(lcf.getSelectedLeaveCode());
        String unitOfTime = leaveCode.getUnitOfTime();
        AccrualCategory acObj = null;
    	if(leaveCode.getAccrualCategory() != null) {
    		acObj = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(leaveCode.getAccrualCategory(), TKUtils.getCurrentDate());
    	}
    	String unitTime = (acObj!= null ? acObj.getUnitOfTime() : unitOfTime) ;
        Map<String, Object> leaveCodeMap = new HashMap<String, Object>();
        leaveCodeMap.put("unitOfTime", unitTime);
        leaveCodeMap.put("defaultAmountofTime", leaveCode.getDefaultAmountofTime());
        leaveCodeMap.put("fractionalTimeAllowed", leaveCode.getFractionalTimeAllowed());
        lcf.setOutputString(JSONValue.toJSONString(leaveCodeMap));
        return mapping.findForward("ws");
    }
    
    /**
     * This is an ajax call triggered after a user submits the leave entry form.
     * If there is any error, it will return error messages as a json object.
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return jsonObj
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ActionForward validateLeaveEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	LeaveCalendarWSForm lcf = (LeaveCalendarWSForm) form;
        JSONArray errorMsgList = new JSONArray();

        List<String> errors = LeaveCalendarValidationService.validateLaveEntryDetails(lcf);
        errorMsgList.addAll(errors);

        lcf.setOutputString(JSONValue.toJSONString(errorMsgList));
        
        return mapping.findForward("ws");
    }

}
