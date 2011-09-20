package org.kuali.hr.time.position.service;

import org.kuali.hr.time.position.Position;

public interface PositionService {
	public Position getPosition(Long hrPositionId);
    public String getNextUniquePositionNumber();

    void updatePositionNumber(String currentPositionNumber);
}
