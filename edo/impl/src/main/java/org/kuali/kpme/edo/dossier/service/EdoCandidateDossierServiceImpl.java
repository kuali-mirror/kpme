package org.kuali.kpme.edo.dossier.service;

import org.kuali.kpme.edo.dossier.EdoCandidateDossier;
import org.kuali.kpme.edo.dossier.dao.EdoCandidateDossierDao;

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
public class EdoCandidateDossierServiceImpl implements EdoCandidateDossierService {

    private EdoCandidateDossierDao edoCandidateDossierDao;

    public List<EdoCandidateDossier> getCandidateDossierByUsername(String userName) {
        return edoCandidateDossierDao.getCandidateDossierByUsername(userName);
    }
    //for guest role
    public EdoCandidateDossier getCandidateDossierByDossierId(String dossierId) {
        return edoCandidateDossierDao.getCandidateDossierByDossierId(dossierId);
    }


    public List<EdoCandidateDossier> getCurrentCandidateDossierByUsername(String userName) {
        return edoCandidateDossierDao.getCurrentCandidateDossierByUsername(userName);
    }

    public List<EdoCandidateDossier> getCandidateDossierList() {
        return edoCandidateDossierDao.getEdoCandidateDossierList();
    }

    public List<EdoCandidateDossier> getCurrentCandidateDossierList() {
        return edoCandidateDossierDao.getCurrentCandidateDossierList();
    }

    public EdoCandidateDossier getCandidateDossier(BigDecimal dossierId) {
        return edoCandidateDossierDao.getCandidateDossier(dossierId);
    }

    public EdoCandidateDossierDao getEdoCandidateDossierDao() {
        return edoCandidateDossierDao;
    }

    public void setEdoCandidateDossierDao(EdoCandidateDossierDao edoCandidateDossierDao) {
        this.edoCandidateDossierDao = edoCandidateDossierDao;
    }
    //show up only open dossier of a candidate when a candidate delegate logs in
    public List<EdoCandidateDossier> getCandidateDelegateDossierByUsername(String userName) {
        return edoCandidateDossierDao.getCandidateDelegateDossierByUsername(userName);
    }

    public List<EdoCandidateDossier> getCandidateDossiers(List<String> campuses, List<String> schools, List<String> departments) {
        return edoCandidateDossierDao.getCandidateDossiers(campuses, schools, departments);
    }
}
