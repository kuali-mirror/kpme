package org.kuali.kpme.pm.positiontype.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positiontype.PositionType;

public interface PositionTypeDao {
	
	public PositionType getPositionTypeById(String pmPositionTypeId);
	
	public List<PositionType> getPositionTypeList(String ositionType, String institution, String campus, LocalDate asOfDate);
}
