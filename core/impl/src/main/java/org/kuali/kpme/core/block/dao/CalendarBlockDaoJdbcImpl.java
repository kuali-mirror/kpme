package org.kuali.kpme.core.block.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.block.CalendarBlock;
import org.kuali.rice.core.framework.persistence.jdbc.dao.PlatformAwareDaoBaseJdbc;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.springframework.jdbc.core.RowMapper;

public class CalendarBlockDaoJdbcImpl extends PlatformAwareDaoBaseJdbc implements CalendarBlockDao {

	@Override
	public List<CalendarBlock> getAllCalendarBlocks() {
		List<CalendarBlock> calendarBlocks = this.getJdbcTemplate().query("" +
				"SELECT begin_ts, end_ts, lm_leave_block_id as c_block_id, 'Leave' as c_block_type, document_id, job_number, obj_id, ver_nbr, principal_id, principal_id_modified, timestamp, task, work_area, earn_code " +
				 "FROM lm_leave_block_t " +
				 "UNION " +
				 "SELECT begin_ts, end_ts, tk_time_block_id as c_block_id, 'Time' as c_block_type, document_id, job_number, obj_id, ver_nbr, principal_id, user_principal_id, timestamp, task, work_area, earn_code " +
				 "FROM tk_time_block_t;", new CalendarBlockRowMapper());
		return calendarBlocks;
	}

	@Override
	public List<CalendarBlock> getActiveCalendarBlocksForDate(LocalDate asOfDate) {
		List<CalendarBlock> calendarBlocks = this.getJdbcTemplate().query("" +
				"SELECT begin_ts, end_ts, lm_leave_block_id as c_block_id, 'Leave' as c_block_type, document_id, job_number, obj_id, ver_nbr, principal_id, principal_id_modified, timestamp, task, work_area, earn_code " +
				 "FROM lm_leave_block_t " +
				 "UNION " +
				 "SELECT begin_ts, end_ts, tk_time_block_id as c_block_id, 'Time' as c_block_type, document_id, job_number, obj_id, ver_nbr, principal_id, user_principal_id, timestamp, task, work_area, earn_code " +
				 "FROM tk_time_block_t;", new CalendarBlockRowMapper());
		return calendarBlocks;
	}

	private class CalendarBlockRowMapper implements RowMapper<CalendarBlock> {

		@Override
		public CalendarBlock mapRow(ResultSet rs, int rowNum) throws SQLException {
			CalendarBlock cBlock = new CalendarBlock();
			cBlock.setBeginTimestamp(rs.getTimestamp("BEGIN_TS"));
			cBlock.setEndTimestamp(rs.getTimestamp("END_TS"));
			cBlock.setConcreteBlockId(rs.getString("C_BLOCK_ID"));
			cBlock.setConcreteBlockType(rs.getString("C_BLOCK_TYPE"));
			cBlock.setDocumentId(rs.getString("DOCUMENT_ID"));
			cBlock.setEarnCode(rs.getString("EARN_CODE"));
			cBlock.setJobNumber(rs.getLong("JOB_NUMBER"));
			cBlock.setObjectId(rs.getString("OBJ_ID"));
			cBlock.setVersionNumber(rs.getLong("VER_NBR"));
			cBlock.setPrincipalId(rs.getString("PRINCIPAL_ID"));
			cBlock.setPrincipalIdModified(rs.getString("PRINCIPAL_ID_MODIFIED"));
			cBlock.setTask(rs.getLong("TASK"));
			cBlock.setWorkArea(rs.getLong("WORK_AREA"));
			cBlock.setTimestamp(rs.getTimestamp("TIMESTAMP"));
			return cBlock;
		}
		
	}
	
}
