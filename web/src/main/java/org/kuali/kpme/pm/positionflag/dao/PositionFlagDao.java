package org.kuali.kpme.pm.positionflag.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionflag.PositionFlag;

public interface PositionFlagDao {
	
	public PositionFlag getPositionFlagById(String pmPositionFlagId);
	
	public List<String> getAllActiveFlagCategories();
	
	public List<PositionFlag> getAllActivePositionFlags(String category, String name, LocalDate effDate);
	
	public List<PositionFlag> getAllActivePositionFlagsWithCategory(String category, LocalDate effDate);
}
