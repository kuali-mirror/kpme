package org.kuali.kpme.edo.dossier.dao;

import org.kuali.kpme.edo.dossier.EdoCandidateDossier;

import java.math.BigDecimal;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 5/13/13
 * Time: 9:53 AM
 */
public interface EdoCandidateDossierDao {

    public List<EdoCandidateDossier> getEdoCandidateDossierList ();
    public List<EdoCandidateDossier> getCurrentCandidateDossierList ();
    public EdoCandidateDossier getCandidateDossier(BigDecimal dossierID);
    public List<EdoCandidateDossier> getCandidateDossierByUsername(String userName);
    public List<EdoCandidateDossier> getCurrentCandidateDossierByUsername(String userName);
    public List<EdoCandidateDossier> getCandidateDelegateDossierByUsername(String userName);
    //for guest role
    public EdoCandidateDossier getCandidateDossierByDossierId(String dossierId);
    public List<EdoCandidateDossier> getCandidateDossiers(List<String> campuses, List<String> schools, List<String> departments);
}
