package org.kuali.kpme.edo.dossier.dao;

import org.kuali.kpme.edo.dossier.EdoDossierBo;

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

    public EdoDossierBo getCurrentDossier( String userName );
    public List<EdoDossierBo> getDossierList();
    public EdoDossierBo getDossierById( BigDecimal dossierId );
    public void saveOrUpdate(EdoDossierBo edoDossier);
    public List<EdoDossierBo> getDossierListByUserName(String userName);
    public EdoDossierBo getDossierByDossierId( String dossierId );
    public EdoDossierBo getDossier(String documentId);
}
