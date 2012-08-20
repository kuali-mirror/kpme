package org.kuali.hr.time.position.service;

import org.kuali.hr.time.position.Position;
import org.springframework.cache.annotation.Cacheable;

public interface PositionService {
    @Cacheable(value= Position.CACHE_NAME, key="'hrPositionId=' + #p0")
	public Position getPosition(String hrPositionId);

    @Cacheable(value= Position.CACHE_NAME, key="'hrPositionNbr=' + #p0")
	public Position getPositionByPositionNumber(String hrPositionNbr);
}
