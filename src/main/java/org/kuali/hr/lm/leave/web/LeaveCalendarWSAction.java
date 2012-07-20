package org.kuali.hr.lm.leave.web;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leavecalendar.validation.LeaveCalendarValidationService;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.util.KeyValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaveCalendarWSAction extends TkAction {

    private static final Logger LOG = Logger.getLogger(LeaveCalendarWSAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.execute(mapping, form, request, response);
    }

        
    public ActionForward getEarnCodeInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	//System.out.println("Leave code info called >>>>>>>>>>>>>>>");
    	LeaveCalendarWSForm lcf = (LeaveCalendarWSForm) form;
        LOG.info(lcf.toString());
        EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCodeById(lcf.getSelectedEarnCode());
        AccrualCategory acObj = null;
    	if(earnCode.getAccrualCategory() != null) {
    		acObj = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(earnCode.getAccrualCategory(), TKUtils.getCurrentDate());
    	}
    	String unitTime = (acObj!= null ? acObj.getUnitOfTime() : earnCode.getRecordMethod()) ;
        Map<String, Object> earnCodeMap = new HashMap<String, Object>();
        earnCodeMap.put("unitOfTime", unitTime);
        earnCodeMap.put("defaultAmountofTime", earnCode.getDefaultAmountofTime());
        earnCodeMap.put("fractionalTimeAllowed", earnCode.getFractionalTimeAllowed());
        lcf.setOutputString(JSONValue.toJSONString(earnCodeMap));
        return mapping.findForward("ws");
    }

    public ActionForward getEarnCodeMap(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //System.out.println("Leave code info called >>>>>>>>>>>>>>>");
        LeaveCalendarWSForm lcf = (LeaveCalendarWSForm) form;
        LOG.info(lcf.toString());
        String principalId = TKContext.getUser().getTargetPrincipalId();
        DateTime beginDate = new DateTime(
                TKUtils.convertDateStringToTimestamp(lcf.getStartDate()));
        //Map<String, String> = TkServiceLocator.getEarnCodeService().getEarnCodesForDisplayWithEffectiveDate(principalId, );
        Map<String, String> earnCodeMap
                = TkServiceLocator.getEarnCodeService().getEarnCodesForDisplayWithEffectiveDate(principalId, new java.sql.Date(beginDate.toDate().getTime()));
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        for (Map.Entry<String, String> entry : earnCodeMap.entrySet()) {
            keyValues.add(new KeyValue(entry.getKey(), entry.getValue()));
        }
        Gson gson = new Gson();
        String json = gson.toJson(keyValues);

        lcf.setOutputString(json);
        //gson.toJson(earnCodeMap, Map.class);
        //lcf.setOutputString(JSONValue.toJSONString(keyValues));
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
