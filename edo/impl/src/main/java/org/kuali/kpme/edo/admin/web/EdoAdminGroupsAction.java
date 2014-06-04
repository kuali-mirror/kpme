package org.kuali.kpme.edo.admin.web;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.group.EdoGroup;
import org.kuali.kpme.edo.group.EdoGroupTracking;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoRule;
import org.kuali.rice.kim.api.group.Group;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;
import org.kuali.rice.krad.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.*;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 12/6/12
 * Time: 8:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoAdminGroupsAction extends EdoAction {
    private static final Logger LOG = Logger.getLogger(EdoGenAdminAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoAdminGroupsForm groupsForm = (EdoAdminGroupsForm) form;
        //groupsForm.setWorkflowDisplayList(EdoServiceLocator.getEdoWorkflowDefinitionService().getWorkflowsForDisplay());

        return super.execute(mapping, form, request, response);
    }

    public ActionForward uploadDeptCSV(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoAdminGroupsForm groupsForm = (EdoAdminGroupsForm) form;
        MessageMap msgmap = GlobalVariables.getMessageMap();

        FormFile uploadFile = groupsForm.getUploadGrpFile();

        if (ServletFileUpload.isMultipartContent(request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(EdoConstants.FILE_UPLOAD_PARAMETERS.THRESHHOLD_SIZE);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(EdoConstants.FILE_UPLOAD_PARAMETERS.MAX_FILE_SIZE);
            upload.setSizeMax(EdoConstants.FILE_UPLOAD_PARAMETERS.REQUEST_SIZE);

            int uploadSize       = uploadFile.getFileSize();

            // uploaded file is empty or non-existent; flag the error
            if (uploadSize < 1) {
                LOG.error("File size is zero.");
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.fileUpload.empty");
                ActionForward fwd = mapping.findForward("basic");
                return fwd;
            }

            String CSVfile = new String(uploadFile.getFileData(), "UTF-8");
            ((EdoAdminGroupsForm) form).setUnitList(CSVfile);
        }
        ActionForward fwd = mapping.findForward("basic");

        return fwd;
    }

    public ActionForward uploadMbrCSV(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoAdminGroupsForm groupsForm = (EdoAdminGroupsForm) form;
        MessageMap msgmap = GlobalVariables.getMessageMap();

        FormFile uploadFile = groupsForm.getUploadMbrFile();

        if (ServletFileUpload.isMultipartContent(request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(EdoConstants.FILE_UPLOAD_PARAMETERS.THRESHHOLD_SIZE);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(EdoConstants.FILE_UPLOAD_PARAMETERS.MAX_FILE_SIZE);
            upload.setSizeMax(EdoConstants.FILE_UPLOAD_PARAMETERS.REQUEST_SIZE);

            int uploadSize       = uploadFile.getFileSize();

            // uploaded file is empty or non-existent; flag the error
            if (uploadSize < 1) {
                LOG.error("File size is zero.");
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.fileUpload.empty");
                ActionForward fwd = mapping.findForward("basic");
                return fwd;
            }

            String CSVfile = new String(uploadFile.getFileData(), "UTF-8");
            ((EdoAdminGroupsForm) form).setGroupMembers(CSVfile);

            request.setAttribute("groupNameTitle", ((EdoAdminGroupsForm) form).getAddToGroupName());
            request.setAttribute("groupJSON", ((EdoAdminGroupsForm) form).getGroupJSON());
        }
        ActionForward fwd = mapping.findForward("basic");

        return fwd;
    }

    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        MessageMap msgmap = GlobalVariables.getMessageMap();
        Map<String,String> unitListHash = new HashMap<String, String>();

        request.setAttribute("foundEntries", false);
        String unitList = "";
        String groupList = "";
        String groupJSON = "";

        String deptCode = ((EdoAdminGroupsForm) form).getDepartmentCode();
        String schoolCode = ((EdoAdminGroupsForm) form).getSchoolCode();
        String campusCode = ((EdoAdminGroupsForm) form).getCampusCode();
        String workflowId = EdoConstants.EDO_DEFAULT_WORKFLOW_ID;

        if (StringUtils.isEmpty(deptCode)) {
            deptCode = "%";
        }
        if (StringUtils.isEmpty(schoolCode)) {
            schoolCode = "%";
        }
        if (StringUtils.isEmpty(campusCode)) {
            campusCode = "%";
        }
        List<EdoGroupTracking> foundEntries = EdoServiceLocator.getEdoGroupTrackingService().findEdoGroupTrackingEntries(deptCode, schoolCode, campusCode);
        if (CollectionUtils.isNotEmpty(foundEntries)) {
            request.setAttribute("foundEntries", true);
            for (EdoGroupTracking groupTracking : foundEntries) {
                String unitString = "";
                String tmpDeptId = "";
                String tmpSchoolId = "";
                String tmpCampusId = "";
                if (!StringUtils.isEmpty(groupTracking.getDepartmentId()) ) {
                    tmpDeptId = groupTracking.getDepartmentId();
                }
                unitString = unitString.concat(tmpDeptId);
                unitString = unitString.concat(",");

                if (!StringUtils.isEmpty(groupTracking.getSchoolId())) {
                    tmpSchoolId = groupTracking.getSchoolId();
                }
                unitString = unitString.concat(tmpSchoolId);
                unitString = unitString.concat(",");

                if (!StringUtils.isEmpty(groupTracking.getCampusId())) {
                    tmpCampusId = groupTracking.getCampusId();
                }
                unitString = unitString.concat(tmpCampusId);
                unitString = unitString.concat(String.format("%n"));

                unitListHash.put(unitString,"1");

                String revLevelDescription = "";
                EdoReviewLayerDefinition rld = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinition(workflowId, groupTracking.getReviewLevelName());
                if (ObjectUtils.isNotNull(rld)) {
                    revLevelDescription = rld.getDescription();
                }
                groupList = groupList.concat(groupTracking.getGroupName());
                groupList = groupList.concat(",");
                groupList = groupList.concat(revLevelDescription);
                groupList = groupList.concat(String.format("%n"));
                List<String> gList = new LinkedList<String>();
                gList.add(groupTracking.getGroupName());
                gList.add(revLevelDescription);
                groupJSON = groupJSON.concat(getStringListJSONString(gList) + ",");
            }
            for (String key : unitListHash.keySet()) {
                unitList = unitList.concat(key);
            }

            ((EdoAdminGroupsForm) form).setUnitList(unitList);
            ((EdoAdminGroupsForm) form).setGroupList(groupList);
            ((EdoAdminGroupsForm) form).setGroupJSON(groupJSON);

            request.setAttribute("groupJSON", groupJSON);
        } else {
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.search.empty");
        }

        return mapping.findForward("basic");
    }

    public ActionForward addToList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        MessageMap msgmap = GlobalVariables.getMessageMap();
        String groupString = "";
        String currentUnitList = ((EdoAdminGroupsForm) form).getUnitList();
        String deptCode = ((EdoAdminGroupsForm) form).getDepartmentCode();
        String schoolCode = ((EdoAdminGroupsForm) form).getSchoolCode();
        String campusCode = ((EdoAdminGroupsForm) form).getCampusCode();
        String workflowId = EdoConstants.EDO_DEFAULT_WORKFLOW_ID;

        if (StringUtils.isEmpty(currentUnitList)) {
            currentUnitList = "";
        }

        if (StringUtils.isEmpty(deptCode)) {
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.search.empty");
            return mapping.findForward("basic");
        }
        if (StringUtils.isEmpty(schoolCode)) {
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.search.empty");
            return mapping.findForward("basic");
        }
        if (StringUtils.isEmpty(campusCode)) {
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.search.empty");
            return mapping.findForward("basic");
        }

        if (deptCode.contains("%") || deptCode.contains("*")) {
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.add.nowildcard");
            return mapping.findForward("basic");
        }
        if (schoolCode.contains("%") || schoolCode.contains("*")) {
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.add.nowildcard");
            return mapping.findForward("basic");
        }
        if (campusCode.contains("%") || campusCode.contains("*")) {
            msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.add.nowildcard");
            return mapping.findForward("basic");
        }

        groupString = groupString.concat(deptCode + ",");
        groupString = groupString.concat(schoolCode + ",");
        groupString = groupString.concat(campusCode + String.format("%n"));
        //groupString = groupString.concat(workflowId +
        //groupString = groupString.concat(String.format("%n"));

        ((EdoAdminGroupsForm) form).setUnitList(currentUnitList + String.format("%n") + groupString);

        return mapping.findForward("basic");
    }

    public ActionForward buildList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        MessageMap msgmap = GlobalVariables.getMessageMap();
        EdoAdminGroupsForm groupsForm = (EdoAdminGroupsForm) form;
        List<EdoGroup> grpList = new LinkedList<EdoGroup>();
        String groupListing = "";
        String groupJSON = "";

        String instCode = groupsForm.getInstitutionCode();
        String workflowId = EdoConstants.EDO_DEFAULT_WORKFLOW_ID;

        BufferedReader rdr = new BufferedReader(new StringReader(groupsForm.getUnitList()));
        List<String> lines = new ArrayList<String>();
        for (String line = rdr.readLine(); line != null; line = rdr.readLine()) {
            lines.add(line);
        }
        rdr.close();

        // remove header line before processing
        if (lines.get(0).equals("DEPARTMENTCODE")) {
            lines.remove(0);
        }

        for (String line : lines) {
            boolean isValid = true;
            String[] codes = line.split(",");
            if (codes.length != 3) {
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.import.line", line);
                LOG.info("Import line does not match expected values: " + line);
                continue;
            }
            if (!EdoRule.validateDepartmentId(codes[0]) ) {
                isValid = false;
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.validate.code", "Department ID", line);
                LOG.info("Invalid department ID: " + codes[0]);
            }
            if (!EdoRule.validateSchoolId(codes[1]) ) {
                isValid = false;
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.validate.code", "School ID", line);
                LOG.info("Invalid school ID: " + codes[1]);
            }
            if (!EdoRule.validateCampusId(codes[2]) ) {
                isValid = false;
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.validate.code", "Campus ID", line);
                LOG.info("Invalid campus ID: " + codes[2]);
            }
            if (isValid) {
                grpList.addAll(EdoServiceLocator.getEdoGroupService().createKimGroups(workflowId, codes[0], codes[1], codes[2], instCode));
                //workflowId = codes[3];
            }
        }

        for (EdoGroup grp : grpList) {
            EdoGroupTracking grpTracking = EdoServiceLocator.getEdoGroupTrackingService().getEdoGroupTrackingByGroupName(grp.getGroupName());
            EdoReviewLayerDefinition rld = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinition(workflowId, grpTracking.getReviewLevelName());

            groupListing = groupListing.concat(grp.getGroupName() + "," + rld.getDescription() + String.format("%n"));
            List<String> gList = new LinkedList<String>();
            gList.add(grp.getGroupName());
            gList.add(rld.getDescription());

            groupJSON = groupJSON.concat(getStringListJSONString(gList) + ",");
            EdoServiceLocator.getEdoGroupService().addGroupToRole(grp);

        }
        groupsForm.setGroupList(groupListing);
        request.setAttribute("groupJSON", groupJSON);

        return mapping.findForward("basic");
    }

    public ActionForward getGroupMembers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoAdminGroupsForm cliForm = (EdoAdminGroupsForm) form;

        String list = "";
        Group grp = KimApiServiceLocator.getGroupService().getGroupByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, request.getParameter("name"));
        List<String> members = EdoServiceLocator.getEdoGroupService().findGroupMembers(grp.getId());
        List<String> usernames = new LinkedList<String>();

        for (String mbr : members) {
            Principal principal = KimApiServiceLocator.getIdentityService().getPrincipal(mbr);
            usernames.add(principal.getPrincipalName());
            list = list.concat(principal.getPrincipalName() + String.format("%n"));
        }

        cliForm.setGroupMembersJSON(list);

        return mapping.findForward("ws");
    }

    public ActionForward addMembersToGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MessageMap msgmap = GlobalVariables.getMessageMap();
        EdoAdminGroupsForm groupsForm = (EdoAdminGroupsForm) form;
        List<String> successful = new LinkedList<String>();

        String groupJSON = groupsForm.getGroupJSON();
        String groupName = groupsForm.getAddToGroupName();

        BufferedReader rdr = new BufferedReader(new StringReader(groupsForm.getGroupMembers()));
        List<String> lines = new ArrayList<String>();
        for (String line = rdr.readLine(); line != null; line = rdr.readLine()) {
            lines.add(line);
        }
        rdr.close();

        for (String line : lines) {
            if (line.matches(".*[\\W].*")) {
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.import.invalid.char", line);
                LOG.info("Import line has non-valid characters: " + line);
                continue;
            }
            String[] user = line.split(",");
            if (user.length != 1) {
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.import.line", line);
                LOG.info("Import line does not match expected values: " + line);
                continue;
            }

            if (!StringUtils.isEmpty(user[0])) {
                Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(user[0]);

                if (ObjectUtils.isNull(principal)) {
                    LOG.info("No such principal: " + user[0]);
                    msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.addmember.noprincipal", user[0]);
                    continue;
                }
                Group grp = KimApiServiceLocator.getGroupService().getGroupByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, groupName);
                if (ObjectUtils.isNull(grp)) {
                    LOG.info("No such group " + groupName);
                    msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.addmember.nogroup", groupName);
                    continue;
                }
                boolean isMember = EdoServiceLocator.getEdoGroupService().isMemberOfGroup(principal.getPrincipalName(), groupName);
                if (!isMember) {
                    boolean success = EdoServiceLocator.getEdoGroupService().addMemberToGroup(principal.getPrincipalId(), grp.getId());
                    if (success) {
                        successful.add(principal.getPrincipalName());
                    } else {
                        msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.search.empty", user[0], groupsForm.getAddToGroupName());
                    }
                }
            }
        }

        request.setAttribute("groupJSON", groupJSON);
        request.setAttribute("groupNameTitle", groupName);
        request.setAttribute("addsuccess", successful.toString());

        return mapping.findForward("basic");
    }
    // private methods
    private String getGroupJSONString(String groupName) {
        ArrayList<String> tmp = new ArrayList<String>();
        Type tmpType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();

        tmp.add(groupName);

        return gson.toJson(tmp, tmpType).toString();
    }

    private String getStringListJSONString(List<String> stringList) {
        Gson gson = new Gson();

        return gson.toJson(stringList).toString();
    }

}
