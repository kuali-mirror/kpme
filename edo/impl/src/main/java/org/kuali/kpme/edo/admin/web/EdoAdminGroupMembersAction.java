package org.kuali.kpme.edo.admin.web;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.group.EdoGroup;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinitionBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
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
import java.math.BigDecimal;
import java.util.*;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 2/13/14
 * Time: 11:31 AM
 */
public class EdoAdminGroupMembersAction extends EdoAction {
    private static final Logger LOG = Logger.getLogger(EdoAdminGroupMembersAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoAdminGroupMembersForm adminForm = (EdoAdminGroupMembersForm)form;

        adminForm.setDepartmentSelectList(EdoServiceLocator.getEdoGroupTrackingService().getDistinctDepartmentList());
        adminForm.setSchoolSelectList(EdoServiceLocator.getEdoGroupTrackingService().getDistinctSchoolList());
        adminForm.setCampusSelectList(EdoServiceLocator.getEdoGroupTrackingService().getDistinctCampusList());
        adminForm.setReviewLevelSelectList(EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitionDescriptionsByWorkflow(EdoConstants.EDO_DEFAULT_WORKFLOW_ID));

        request.setAttribute("reviewLevelSelectList", EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitionDescriptionsByWorkflow(EdoConstants.EDO_DEFAULT_WORKFLOW_ID));

        return super.execute(mapping, form, request, response);
    }

    public ActionForward loadMemberData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EdoAdminGroupMembersForm adminForm = (EdoAdminGroupMembersForm)form;
        MessageMap msgmap = GlobalVariables.getMessageMap();
        List<List<String>> member_records = new ArrayList<List<String>>();
        List<String> successfulMembers = new LinkedList<String>();

        String memberData = adminForm.getMemberData(); // this is a JSON string, an array of array of strings
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        JsonParser parser = new JsonParser();

        array = parser.parse(memberData).getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            List<String> record = new ArrayList<String>();

            JsonArray ar = array.get(i).getAsJsonArray();
            if (ar.size() != 11 ) {
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.import.line", ar.getAsString());
                continue;
            }
            for (int j = 0; j < ar.size(); j++) {
                record.add(ar.get(j).getAsString());
            }
            member_records.add(record);
        }

        successfulMembers.addAll(processMemberRecords(member_records));

        // dept, school, campus, level number, level description, tnp value, tnp (T/P), chair value, chair (Y/N), username, placeholder
        request.setAttribute("successfulMembers", successfulMembers);
        return mapping.findForward("basic");
    }

    public ActionForward uploadMbrCSV(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        EdoAdminGroupMembersForm memberForm = (EdoAdminGroupMembersForm) form;
        MessageMap msgmap = GlobalVariables.getMessageMap();

        FormFile uploadFile = memberForm.getUploadMbrFile();

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

            BufferedReader rdr = new BufferedReader(new StringReader(CSVfile));
            List<String> lines = new ArrayList<String>();
            List<List<String>> member_records = new ArrayList<List<String>>();

            for (String line = rdr.readLine(); line != null; line = rdr.readLine()) {
                List<String> record = Arrays.asList(line.split(","));
                member_records.add(record);
                lines.add(line);
            }
            rdr.close();
            // the first record MUST be the header:  UNIT,SCHOOL,CAMPUS,REVIEWLEVEL,DOSSIERTYPE,CHAIR?,USERNAME
            member_records.remove(0);
            List<List<String>> convertedMemberRecords = convertCSVRecordsToMemberRecords(member_records);
            List<String> successfulMembers = processMemberRecords(convertedMemberRecords);

            request.setAttribute("successfulMembers", successfulMembers);
        }

        ActionForward fwd = mapping.findForward("basic");

        return fwd;
    }

    private List<List<String>> convertCSVRecordsToMemberRecords(List<List<String>> csv_records) {
        List<List<String>> member_records = new ArrayList<List<String>>();
        MessageMap msgmap = GlobalVariables.getMessageMap();

        Collection<EdoReviewLayerDefinition> rlds = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions();
        Map<String,BigDecimal> rldMap = new HashMap<String, BigDecimal>();

        for (EdoReviewLayerDefinition rld : rlds) {
            if (rld.getVoteType().equals("None")) {
                continue;
            }
            rldMap.put(rld.getDescription().toUpperCase(), new BigDecimal(rld.getEdoReviewLayerDefinitionId()));
        }

        for (List<String> csv_rec : csv_records) {
            List<String> tmp_csv_rec = new ArrayList<String>();

            String levelDescription = csv_rec.get(3).toUpperCase();
            String tnpFlag = csv_rec.get(4).toUpperCase();
            String chairFlag = csv_rec.get(5).toUpperCase();

            String levelValue = rldMap.get(levelDescription).toString();
            String tnpValue = null;
            if (tnpFlag.equals("T") || tnpFlag.equals("TENURE")) {
                tnpValue = "TENURE";
            }
            if (tnpFlag.equals("P") || tnpFlag.equals("PROMOTION")) {
                tnpValue = "PROMOTION";
            }
            if (tnpValue.isEmpty()) {
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.import.invalid.char", csv_rec.toString());
                continue;
            }
            String chairValue = "FYI";
            if (chairFlag.equals("Y") || chairFlag.equals("YES")) {
                chairValue = "APPROVE";
            }
            tmp_csv_rec.add(csv_rec.get(0));
            tmp_csv_rec.add(csv_rec.get(1));
            tmp_csv_rec.add(csv_rec.get(2));
            tmp_csv_rec.add(levelValue);
            tmp_csv_rec.add(csv_rec.get(3));
            tmp_csv_rec.add(tnpValue);
            tmp_csv_rec.add(csv_rec.get(4));
            tmp_csv_rec.add(chairValue);
            tmp_csv_rec.add(csv_rec.get(5));
            tmp_csv_rec.add(csv_rec.get(6));
            tmp_csv_rec.add(""); // placeholder position from member table
            member_records.add(tmp_csv_rec);
        }
        return member_records;
    }

    private List<String> processMemberRecords(List<List<String>> member_records) {

        List<String> successfulMembers = new LinkedList<String>();
        MessageMap msgmap = GlobalVariables.getMessageMap();

        // dept, school, campus, level number, level description, tnp value, tnp (T/P), chair value, chair (Y/N), username, placeholder
        for (List<String> rec : member_records) {
            // verify username as a known principal
            String username = rec.get(9);
            Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(username);

            if (ObjectUtils.isNull(principal)) {
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.addmember.noprincipal", username);
                continue;
            }
            EdoReviewLayerDefinition rld = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitionById(rec.get(3));
            String nodeName = rld.getNodeName().toUpperCase();
            BigDecimal reviewLevel = new BigDecimal(rld.getReviewLevel());
            String dossierType = rec.get(5);
            String workflowAction = rec.get(7);

            String unitName = rec.get(0);
            // translates to level number, chair value, tnp value, dept/school for group naming
            // if the review level is greater than 2, then set the unitname to the school id
            if (reviewLevel.compareTo(BigDecimal.valueOf(2)) == 1) {
                unitName = rec.get(1);
            }
            // if the review level is greater than 4, then set the unitname to the campus id
            if (reviewLevel.compareTo(BigDecimal.valueOf(4)) == 1) {
                unitName = rec.get(2);
            }
            if (reviewLevel.compareTo(BigDecimal.valueOf(5)) == 1) {
                dossierType = "";
            }
            String groupName = EdoServiceLocator.getEdoGroupService().buildGroupName(nodeName,workflowAction,dossierType,unitName);

            // just create the group list anyway
            List<EdoGroup> grpList = EdoServiceLocator.getEdoGroupService().createKimGroups(EdoConstants.EDO_DEFAULT_WORKFLOW_ID, rec.get(0), rec.get(1), rec.get(2), "IU");
            Group grp = KimApiServiceLocator.getGroupService().getGroupByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE,groupName);
            if (ObjectUtils.isNotNull(grp)) {
                if (!EdoServiceLocator.getEdoGroupService().addMemberToGroup(principal.getPrincipalId(),grp.getId())) {
                    msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.addmember.error", principal.getPrincipalName(),grp.getName());
                    continue;
                }
                successfulMembers.add(principal.getPrincipalName());
            } else {
                msgmap.putError(EdoConstants.ErrorKeys.ERROR_KEYS, "error.group.addmember.nogroup", groupName);
            }
        }
        return successfulMembers;
    }
}
