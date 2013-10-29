package org.kuali.kpme.core.api.bo.dao;

import java.util.List;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;

public interface HrBusinessObjectDao {

	<T extends HrBusinessObjectContract> List<T> getNewerVersionsOf(T bo);
}
