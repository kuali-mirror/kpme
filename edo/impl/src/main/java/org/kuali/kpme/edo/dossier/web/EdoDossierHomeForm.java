package org.kuali.kpme.edo.dossier.web;

import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.dossier.EdoCandidateDossier;

import java.util.LinkedList;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 2/14/13
 * Time: 10:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoDossierHomeForm extends EdoForm {

    /* private List<EdoCandidate> dossierList = new LinkedList<EdoCandidate>(); */
    private List<EdoCandidateDossier> dossierList = new LinkedList<EdoCandidateDossier>();

    /* public void setDossierList( List<EdoCandidate> list) {
        dossierList = list;
    }

    public List<EdoCandidate> getDossierList( String userName ) {
        dossierList = EdoServiceLocator.getCandidateService().getCandidateListByUsername( userName );

        return dossierList;
    }  */

    public void setDossierList( List<EdoCandidateDossier> list ) {
        dossierList = list;
    }

    public List<EdoCandidateDossier> getDossierList( String userName) {
        dossierList = EdoServiceLocator.getEdoCandidateDossierService().getCandidateDossierByUsername( userName );

        return dossierList;
    }
}
