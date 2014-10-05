package org.kuali.kpme.edo.dossier.service;

import org.kuali.kpme.edo.dossier.EdoCandidateDossier;

import java.math.BigDecimal;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 5/13/13
 * Time: 10:03 AM
 */
public interface EdoCandidateDossierService {

    public List<EdoCandidateDossier> getCandidateDossierByUsername(String userName);
    public List<EdoCandidateDossier> getCurrentCandidateDossierByUsername(String userName);
    public List<EdoCandidateDossier> getCandidateDossierList();
    public List<EdoCandidateDossier> getCurrentCandidateDossierList();
    public EdoCandidateDossier getCandidateDossier(BigDecimal dossierId);
    //show only open dossier to the candidate delegate
    public List<EdoCandidateDossier> getCandidateDelegateDossierByUsername(String userName);
    //dossier for guest
    public EdoCandidateDossier getCandidateDossierByDossierId(String dossierId);
    public List<EdoCandidateDossier> getCandidateDossiers(List<String> campuses, List<String> schools, List<String> departments);
}

