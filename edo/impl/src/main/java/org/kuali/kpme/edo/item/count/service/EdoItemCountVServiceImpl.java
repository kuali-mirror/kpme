package org.kuali.kpme.edo.item.count.service;

import org.kuali.kpme.edo.item.count.EdoItemCountV;
import org.kuali.kpme.edo.item.count.dao.EdoItemCountVDao;

import java.util.List;
import java.math.BigDecimal;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 6/21/13
 * Time: 10:59 AM
 */
public class EdoItemCountVServiceImpl implements EdoItemCountVService {

    private EdoItemCountVDao edoItemCountVDao;

    public List<EdoItemCountV> getItemCount(BigDecimal dossierId, BigDecimal checklistSectionId) {
        return edoItemCountVDao.getItemCount(dossierId, checklistSectionId);
    }

    public EdoItemCountVDao getEdoItemCountVDao() {
        return edoItemCountVDao;
    }

    public void setEdoItemCountVDao(EdoItemCountVDao edoItemCountVDao) {
        this.edoItemCountVDao = edoItemCountVDao;
    }
}
