package org.kuali.kpme.edo.admin.web;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.base.web.EdoAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 2/18/14
 * Time: 10:11 AM
 */
public class EdoMemberCSVAction extends EdoAction {
    private static final Logger LOG = Logger.getLogger(EdoMemberCSVAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String header = "";
        String fileName = "";
        String headerType = "member"; // default to group member list header string

        if (request.getParameterMap().containsKey("csv") ) {
            headerType = request.getParameter("csv");
        }
        if (headerType.equals("member")) {
            header = "UNIT,SCHOOL,CAMPUS,REVIEWLEVEL,DOSSIERTYPE,CHAIR?,USERNAME" + String.format("%n");
            fileName = "group-members.csv";
        }
        if (headerType.equals("admin")) {
            header = "UNIT,USERNAME" + String.format("%n");
            fileName = "admin-members.csv";
        }

        byte[] fileContents = header.getBytes(Charset.forName("UTF-8"));
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bao.write(fileContents);

        response.setContentType("text/csv");
        response.addHeader("content-disposition", "filename=\"" + fileName + "\"");
        response.setContentLength(bao.size());
        bao.writeTo(response.getOutputStream());
        bao.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();

        return mapping.findForward("basic");
    }
}
