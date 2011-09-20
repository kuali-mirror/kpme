package org.kuali.hr.time.position.dao;

import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.position.PositionNumber;

public interface PositionDao {
	public Position getPosition(Long hrPositionId);

    public PositionNumber getNextUniquePositionNumber();

    void saveOrUpdate(PositionNumber positionNumber);
}
