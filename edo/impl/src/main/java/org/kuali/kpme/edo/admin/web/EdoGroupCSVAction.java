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
 * Date: 2/19/14
 * Time: 11:36 AM
 */
public class EdoGroupCSVAction extends EdoAction {
    private static final Logger LOG = Logger.getLogger(EdoGroupCSVAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String header = "DEPARTMENTCODE,SCHOOLCODE,CAMPUSCODE" + String.format("%n");

        byte[] fileContents = header.getBytes(Charset.forName("UTF-8"));
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bao.write(fileContents);

        response.setContentType("text/csv");
        response.addHeader("content-disposition", "filename=\"edossier-groups.csv\"");
        response.setContentLength(bao.size());
        bao.writeTo(response.getOutputStream());
        bao.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();

        return mapping.findForward("basic");
    }

}
