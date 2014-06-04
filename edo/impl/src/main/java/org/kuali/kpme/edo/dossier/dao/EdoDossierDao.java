package org.kuali.kpme.edo.dossier.dao;

import org.kuali.kpme.edo.dossier.EdoDossier;

import java.math.BigDecimal;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/17/13
 * Time: 1:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EdoDossierDao {

    public EdoDossier getCurrentDossier( String userName );
    public List<EdoDossier> getDossierList();
    public EdoDossier getDossierById( BigDecimal dossierId );
    public void saveOrUpdate(EdoDossier edoDossier);
    public List<EdoDossier> getDossierListByUserName(String userName);
    public EdoDossier getDossierByDossierId( String dossierId );
    public EdoDossier getDossier(String documentId);
}
