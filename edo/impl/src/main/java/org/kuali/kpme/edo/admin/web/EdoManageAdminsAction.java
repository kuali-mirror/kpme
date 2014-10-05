package org.kuali.kpme.edo.admin.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 3/18/14
 * Time: 3:21 PM
 */
public class EdoManageAdminsAction extends EdoAction {

    private static final Logger LOG = Logger.getLogger(EdoManageAdminsAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoManageAdminsForm adminForm = (EdoManageAdminsForm)form;

        adminForm.setDepartmentSelectList(EdoServiceLocator.getEdoGroupTrackingService().getDistinctDepartmentList());
        adminForm.setSchoolSelectList(EdoServiceLocator.getEdoGroupTrackingService().getDistinctSchoolList());
        adminForm.setCampusSelectList(EdoServiceLocator.getEdoGroupTrackingService().getDistinctCampusList());
        adminForm.setReviewLevelSelectList(EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitionDescriptionsByWorkflow("DEFAULT"));

        return super.execute(mapping, form, request, response);
    }

    public ActionForward loadAdminData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoManageAdminsForm adminForm = (EdoManageAdminsForm)form;
        MessageMap msgmap = GlobalVariables.getMessageMap();
        List<List<String>> member_records = new ArrayList<List<String>>();
        List<String> successfulMembers = new LinkedList<String>();

        String memberData = adminForm.getMemberData(); // this is a JSON string, an array of array of strings
        JsonParser parser = new JsonParser();

        JsonArray array = parser.parse(memberData).getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            List<String> record = new ArrayList<String>();

            JsonArray ar = array.get(i).getAsJsonArray();
            if (ar.size() != 5 ) {
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.role.import.line", ar.getAsString());
                continue;
            }
            for (int j = 0; j < ar.size(); j++) {
                if (!ar.get(j).getAsString().equals("-")) {
                    record.add(ar.get(j).getAsString());
                }
            }
            member_records.add(record);
        }

        successfulMembers.addAll(processMemberRecords(member_records));

        request.setAttribute("successfulMembers", successfulMembers);
        return mapping.findForward("basic");
    }

    // private methods
    private List<String> processMemberRecords(List<List<String>> member_records) {

        List<String> successfulMembers = new LinkedList<String>();
        MessageMap msgmap = GlobalVariables.getMessageMap();

        // dept/school/campus, username
        for (List<String> rec : member_records) {
            String unit = rec.get(0);
            String username = rec.get(1);
            // verify username as a known principal
            Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(username);

            if (ObjectUtils.isNull(principal)) {
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.role.validate.noprincipal", username);
                continue;
            }
            if (StringUtils.isBlank(unit)) {
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.role.validate.empty");
                continue;
            }
            Map<String,String> quals = EdoServiceLocator.getEdoRoleMaintenanceService().buildAdminQualifications(unit);

            if (CollectionUtils.isNotEmpty(quals.keySet())) {
                RoleMember roleMember = EdoServiceLocator.getEdoRoleMaintenanceService().assignStaffAdmin(principal.getPrincipalId(),quals);
                if (!ObjectUtils.isNotNull(roleMember)) {
                    msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.role.assign.error", principal.getPrincipalName());
                    continue;
                }
                successfulMembers.add(principal.getPrincipalName());
            }
        }
        return successfulMembers;
    }

}
