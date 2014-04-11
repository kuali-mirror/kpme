package org.kuali.kpme.core.groupkey.dao;


import org.joda.time.LocalDate;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;

public interface HrGroupKeyDao {

    HrGroupKeyBo getHrGroupKey(String groupKeyCode, LocalDate asOfDate);
}
