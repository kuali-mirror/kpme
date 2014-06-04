package org.kuali.kpme.edo.dossier.type.web;

import java.math.BigDecimal;
import java.util.List;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.base.web.EdoAction;
import org.kuali.kpme.edo.dossier.type.EdoDossierType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/20/12
 * Time: 10:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoDossierTypeAction extends EdoAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String dossierTypeID = null;
        EdoDossierType dossierTypeObj = new EdoDossierType();
        String dossierTypeListJSON = "";

        // prepare the dossier type list
        List<EdoDossierType> dossierTypeList = ((EdoDossierTypeForm)form).getEdoDossierTypeList();

        for ( EdoDossierType dossType : dossierTypeList ) {
            dossierTypeListJSON = dossierTypeListJSON.concat(dossType.getEdoDossierTypeJSONString() + ",");
        }
        request.setAttribute("dossierTypesJSON", dossierTypeListJSON);

        dossierTypeObj.setDossierTypeID(((EdoDossierTypeForm) form).dossierTypeID);
        dossierTypeObj.setDossierTypeCode(((EdoDossierTypeForm) form).dossierTypeCode);
        dossierTypeObj.setDossierTypeName(((EdoDossierTypeForm) form).dossierTypeName);
        dossierTypeObj.setLastUpdated(((EdoDossierTypeForm) form).lastUpdated);

        if (dossierTypeObj.getDossierTypeID() != null) {
            if (dossierTypeObj.getDossierTypeID().equals(BigDecimal.ZERO)) {
                dossierTypeObj.setDossierTypeID(null);
            }
            EdoServiceLocator.getEdoDossierTypeService().saveOrUpdate(dossierTypeObj);
        }

        //prepare the add form, if necessary

        if ( request.getParameter("dtid") != null ) {
            dossierTypeID = request.getParameter("dtid").toString();
            dossierTypeObj = ((EdoDossierTypeForm)form).getEdoDossierType(BigDecimal.valueOf(Integer.parseInt(dossierTypeID)));
            if (dossierTypeObj != null) {
                ((EdoDossierTypeForm) form).dossierTypeCode = dossierTypeObj.getLastUpdated().toString();
                ((EdoDossierTypeForm) form).dossierTypeCode = dossierTypeObj.getDossierTypeCode();
                ((EdoDossierTypeForm) form).dossierTypeName =  dossierTypeObj.getDossierTypeName();
                ((EdoDossierTypeForm) form).dossierTypeID = dossierTypeObj.getDossierTypeID();
            }
        }

        return super.execute(mapping, form, request, response);
    }
}

