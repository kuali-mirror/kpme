package org.kuali.kpme.edo.item.count.dao;

import org.kuali.kpme.edo.item.count.EdoItemCountV;

import java.util.List;
import java.math.BigDecimal;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 6/21/13
 * Time: 10:44 AM
 */
public interface EdoItemCountVDao {

    public List<EdoItemCountV> getItemCount(BigDecimal dossierId, BigDecimal checklistSectionId);
}
