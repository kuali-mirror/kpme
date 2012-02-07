package org.kuali.hr.time.warning;

import java.util.List;
import java.sql.Date;

import org.kuali.hr.time.timeblock.TimeBlock;

public interface TkWarningService {
	public List<String> getWarnings(String documentNumber);
	public List<String> getWarnings(String pId, List<TimeBlock> tbList, Date date);
}
