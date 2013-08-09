/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.core.block.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.block.CalendarBlock;
import org.kuali.rice.core.framework.persistence.jdbc.dao.PlatformAwareDaoBaseJdbc;
import org.springframework.jdbc.core.RowMapper;

public class CalendarBlockDaoJdbcImpl extends PlatformAwareDaoBaseJdbc implements CalendarBlockDao {

	@Override
	public List<CalendarBlock> getAllCalendarBlocks() {
		List<CalendarBlock> calendarBlocks = this.getJdbcTemplate().query("" +
				"SELECT begin_ts, end_ts, lm_leave_block_id as c_block_id, 'Leave' as c_block_type, document_id, job_number, obj_id, ver_nbr, principal_id, principal_id_modified as user_principal_id, timestamp, task, work_area, earn_code, " +
				"'N' as lunch_deleted, null as overtime_pref, null as hours, leave_amount as amount " +
				 "FROM lm_leave_block_t " +
				 "UNION " +
				 "SELECT begin_ts, end_ts, tk_time_block_id as c_block_id, 'Time' as c_block_type, document_id, job_number, obj_id, ver_nbr, principal_id, user_principal_id, timestamp, task, work_area, earn_code, " +
				"lunch_deleted, ovt_pref as overtime_pref, hours, amount " +
				 "FROM tk_time_block_t;", new CalendarBlockRowMapper());
		return calendarBlocks;
	}

	@Override
	public List<CalendarBlock> getActiveCalendarBlocksForDate(LocalDate asOfDate) {
		List<CalendarBlock> calendarBlocks = this.getJdbcTemplate().query("" +
				 "SELECT begin_ts, end_ts, tk_time_block_id as c_block_id, 'Time' as c_block_type, document_id, job_number, obj_id, ver_nbr, principal_id, user_principal_id, timestamp, task, work_area, earn_code " +
				 "FROM tk_time_block_t", new CalendarBlockRowMapper());
		return calendarBlocks;
	}
	
	@Override
	public List<CalendarBlock> getCalendarBlocksForTimeBlockLookup(String documentId, String principalId, String userPrincipalId, LocalDate fromDate, LocalDate toDate) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT begin_ts, end_ts, lm_leave_block_id as c_block_id, 'Leave' as c_block_type, document_id, 'N' as lunch_deleted, ");
		sql.append("null as hours, leave_amount as amount, null as overtime_pref, ");
		sql.append("job_number, obj_id, ver_nbr, principal_id, principal_id_modified as user_principal_id, timestamp, task, work_area, earn_code ");
		sql.append("FROM lm_leave_block_t WHERE leave_block_type = 'TC'");
		if(documentId != null) {
			sql.append(" and document_id = '" + documentId + "'");
		}
		if(principalId != null) {
			sql.append(" and principal_id = '" + principalId + "'");
		}
		if(userPrincipalId != null) {
			sql.append(" and principal_id_modified = '" + userPrincipalId + "'");
		}
		if(fromDate != null) {
			//TODO: ensure correct formatter used for date
			sql.append(" and begin_ts >= '" + fromDate.toString() + "'");
		}
		if(toDate != null) {
			sql.append(" and end_ts <= '" + toDate.toString() + "'");
		}
		List<CalendarBlock> leaveBlocks = this.getJdbcTemplate().query(sql.toString(), new CalendarBlockRowMapper());
		
		sql = new StringBuffer();
		sql.append("SELECT begin_ts, end_ts, tk_time_block_id as c_block_id, 'Time' as c_block_type, document_id, lunch_deleted, ");
		sql.append("ovt_pref as overtime_pref, hours, amount, ");
		sql.append("job_number, obj_id, ver_nbr, principal_id, user_principal_id, timestamp, task, work_area, earn_code ");
		sql.append("FROM tk_time_block_t ");
		boolean whereAdded = false;
		if(documentId != null) {
			if(whereAdded == false) {
				sql.append("WHERE document_id = '" + documentId + "'");
				whereAdded = true;
			} else {
				sql.append(" and document_id = '" + documentId + "'");
			}
		}
		if(principalId != null) {
			if(whereAdded == false) {
				sql.append("WHERE principal_id = '" + principalId + "'");
				whereAdded = true;
			} else {
				sql.append(" and principal_id = '" + principalId + "'");
			}
		}
		if(userPrincipalId != null) {
			if(whereAdded == false) {
				sql.append("WHERE user_principal_id = '" + userPrincipalId + "'");
				whereAdded = true;
			} else {
				sql.append(" and user_principal_id = '" + userPrincipalId + "'");
			}
		}
		if(fromDate != null) {
			//TODO: ensure correct formatter used for date
			if(whereAdded == false) {
				sql.append("WHERE begin_ts >= '" + fromDate.toString() + "'");
				whereAdded = true;
			} else {
				sql.append(" and begin_ts >= '" + fromDate.toString() + "'");
			}
		}
		if(toDate != null) {
			if(whereAdded == false) {
				sql.append("WHERE end_ts <= '" + toDate.toString() + "'");
				whereAdded = true;
			} else {
				sql.append(" and end_ts <= '" + toDate.toString() + "'");
			}
		}
		
		List<CalendarBlock> timeBlocks = this.getJdbcTemplate().query(sql.toString(), new CalendarBlockRowMapper());

		List<CalendarBlock> calendarBlocks = leaveBlocks;
		calendarBlocks.addAll(timeBlocks);
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
			cBlock.setUserPrincipalId(rs.getString("USER_PRINCIPAL_ID"));
			cBlock.setHours(rs.getBigDecimal("HOURS"));
			cBlock.setAmount(rs.getBigDecimal("AMOUNT"));
			cBlock.setOvertimePref(rs.getString("OVERTIME_PREF"));
			cBlock.setLunchDeleted(rs.getBoolean("LUNCH_DELETED"));
			cBlock.setTask(rs.getLong("TASK"));
			cBlock.setWorkArea(rs.getLong("WORK_AREA"));
			cBlock.setTimestamp(rs.getTimestamp("TIMESTAMP"));
			return cBlock;
		}
		
	}
	
	@Override
	public DateTime getLatestEndTimestampForEarnCode(String earnCode, String calendarBlockType) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT max(end_ts) ");
        if (StringUtils.equals(calendarBlockType, "Time")) {
            sql.append("FROM tk_time_block_t ");
        } else if (StringUtils.equals(calendarBlockType, "Leave")) {
            sql.append("FROM lm_leave_block_t ");
        }
        else {
        	throw new IllegalArgumentException("calendarBlockType must be one of 'Time' or 'Leave'");
        }
        sql.append("WHERE earn_code = '").append(earnCode).append("' ");


	  Timestamp maxDate = this.getJdbcTemplate().queryForObject(sql.toString(), Timestamp.class);
	  if(maxDate == null) {
		  return null;
	  }
	  else {
		  return new DateTime(maxDate.getTime());
	  }
	}
	
}
