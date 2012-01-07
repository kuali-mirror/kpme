package org.kuali.hr.time.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.enhydra.jdbc.pool.StandardXAPoolDataSource;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.core.lifecycle.BaseLifecycle;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class ClearDatabaseLifecycle extends BaseLifecycle {

	protected static final Logger LOG = Logger.getLogger(ClearDatabaseLifecycle.class);

	private static List<String> TABLES_TO_CLEAR = new ArrayList<String>();

	static {
		TABLES_TO_CLEAR.add("HR_PY_CALENDAR_T");
		TABLES_TO_CLEAR.add("HR_PY_CALENDAR_ENTRIES_T");
		TABLES_TO_CLEAR.add("HR_DEPT_T");
		TABLES_TO_CLEAR.add("TK_ASSIGN_ACCT_T");
		TABLES_TO_CLEAR.add("TK_ASSIGNMENT_T");
		TABLES_TO_CLEAR.add("TK_CLOCK_LOCATION_RL_T");
		TABLES_TO_CLEAR.add("TK_CLOCK_LOG_T");
		TABLES_TO_CLEAR.add("TK_DAILY_OVERTIME_RL_T");
		TABLES_TO_CLEAR.add("HR_DEPT_EARN_CODE_T");
		TABLES_TO_CLEAR.add("TK_DEPT_LUNCH_RL_T");
		TABLES_TO_CLEAR.add("HR_EARN_CODE_T");
		TABLES_TO_CLEAR.add("HR_EARN_GROUP_T");
		TABLES_TO_CLEAR.add("HR_EARN_GROUP_DEF_T");
		TABLES_TO_CLEAR.add("TK_GRACE_PERIOD_RL_T");
		TABLES_TO_CLEAR.add("HR_HOLIDAY_CALENDAR_DATES_T");
		TABLES_TO_CLEAR.add("HR_HOLIDAY_CALENDAR_T");
		TABLES_TO_CLEAR.add("TK_HOUR_DETAIL_HIST_T");
		TABLES_TO_CLEAR.add("TK_HOUR_DETAIL_T");
		TABLES_TO_CLEAR.add("HR_ROLES_T");
		TABLES_TO_CLEAR.add("HR_SAL_GROUP_T");
		TABLES_TO_CLEAR.add("TK_SHIFT_DIFFERENTIAL_RL_T");
		TABLES_TO_CLEAR.add("TK_SYSTEM_LUNCH_RL_T");
		TABLES_TO_CLEAR.add("TK_TASK_T");
		TABLES_TO_CLEAR.add("TK_TIME_BLOCK_HIST_T");
		TABLES_TO_CLEAR.add("TK_TIME_BLOCK_T");
		TABLES_TO_CLEAR.add("TK_TIME_COLLECTION_RL_T");
		TABLES_TO_CLEAR.add("TK_WEEKLY_OVERTIME_RL_T");
		TABLES_TO_CLEAR.add("TK_WORK_AREA_T");
		TABLES_TO_CLEAR.add("HR_JOB_T");
		TABLES_TO_CLEAR.add("HR_PAYTYPE_T");
//		TABLES_TO_CLEAR.add("HR_WORK_SCHEDULE_ENTRY_T");
//		TABLES_TO_CLEAR.add("HR_WORK_SCHEDULE_T");
		TABLES_TO_CLEAR.add("TK_DOCUMENT_HEADER_T");
		TABLES_TO_CLEAR.add("HR_POSITION_T");
	}

	private static final Map<String,String> TABLE_TO_ID_MAP = new HashMap<String,String>();
	static{
		TABLE_TO_ID_MAP.put("TK_TIME_COLLECTION_RL_T", "TK_TIME_COLL_RULE_ID");
		TABLE_TO_ID_MAP.put("TK_SHIFT_DIFFERENTIAL_RL_T", "TK_SHIFT_DIFF_RL_ID");
		//TABLE_TO_ID_MAP.put("HR_HOLIDAY_CALENDAR_T", "HR_HOLIDAY_CALENDAR_ID");
		//TABLE_TO_ID_MAP.put("HR_HOLIDAY_CALENDAR_DATES_T", "HR_HOLIDAY_CALENDAR_DATES_ID");
		TABLE_TO_ID_MAP.put("TK_GRACE_PERIOD_RL_T", "TK_GRACE_PERIOD_RULE_ID");
		TABLE_TO_ID_MAP.put("TK_CLOCK_LOCATION_RL_T", "TK_CLOCK_LOC_RULE_ID");
		TABLE_TO_ID_MAP.put("HR_PY_CALENDAR_ENTRIES_T", "HR_PY_CALENDAR_ENTRY_ID");
		TABLE_TO_ID_MAP.put("TK_DOCUMENT_HEADER_T", "DOCUMENT_ID");
	}


	public static final String TEST_TABLE_NAME = "KR_UNITTEST_T";

	public static final Integer START_CLEAR_ID = 1000;

	public void start() throws Exception {
		if (new Boolean(ConfigContext.getCurrentContextConfig().getProperty("tk.use.clearDatabaseLifecycle"))) {
			final StandardXAPoolDataSource dataSource = (StandardXAPoolDataSource) TkServiceLocator.CONTEXT.getBean("tkDataSource");
			final PlatformTransactionManager transactionManager = (PlatformTransactionManager) TkServiceLocator.CONTEXT.getBean("transactionManager");
			final String schemaName = dataSource.getUser().toUpperCase();
			clearTables(transactionManager, dataSource, schemaName);
			super.start();
		}
	}

	protected Boolean isTestTableInSchema(final DataSource dataSource) {
	Assert.assertNotNull("DataSource could not be located.", dataSource);
	return (Boolean) new JdbcTemplate(dataSource).execute(new ConnectionCallback() {
			public Object doInConnection(final Connection connection) throws SQLException {
				final ResultSet resultSet = connection.getMetaData().getTables(null, null, TEST_TABLE_NAME, null);
				return new Boolean(resultSet.next());
			}
		});
	}

	protected void verifyTestEnvironment(final DataSource dataSource) {
		Assert.assertTrue("No table named '" + TEST_TABLE_NAME + "' was found in the configured database.  " + "You are attempting to run tests against a non-test database!!!",
		isTestTableInSchema(dataSource));
	}

	protected void clearTables(final PlatformTransactionManager transactionManager, final DataSource dataSource, final String schemaName) {
		LOG.info("Clearing tables for schema " + schemaName);
		Assert.assertNotNull("DataSource could not be located.", dataSource);

		if (schemaName == null || schemaName.equals("")) {
			Assert.fail("Empty schema name given");
		}
		new TransactionTemplate(transactionManager).execute(new TransactionCallback() {
            public Object doInTransaction(final TransactionStatus status) {
            	verifyTestEnvironment(dataSource);
            	return new JdbcTemplate(dataSource).execute(new StatementCallback() {
                	public Object doInStatement(Statement statement) throws SQLException {
                    		final List<String> reEnableConstraints = new ArrayList<String>();
                    		for (String tableName : TABLES_TO_CLEAR) {
                    			//if there is an id name that doesnt follow convention check and limit accordingly
                    			String idName = TABLE_TO_ID_MAP.get(tableName);
                    			String deleteStatement = null;
                    			if(idName == null){
                    				deleteStatement = "DELETE FROM " + tableName +" WHERE "+StringUtils.removeEnd(tableName,"_T")+"_ID"+ " >= "+START_CLEAR_ID;
                    			} else {
                    				deleteStatement = "DELETE FROM " + tableName +" WHERE "+ idName + " >= "+START_CLEAR_ID;
                    			}

                            	LOG.error("Clearing contents using statement ->" + deleteStatement + "<-");
                            	statement.addBatch(deleteStatement);
                            }

                            for (final String constraint : reEnableConstraints) {
                            	LOG.info("Enabling constraints using statement ->" + constraint + "<-");
                            	statement.addBatch(constraint);
                            }
                            statement.executeBatch();
                            return null;
                      }
                });
            }
		});
		LOG.info("Tables successfully cleared for schema " + schemaName);
	}

	public List<String> getTablesToClear() {
	return TABLES_TO_CLEAR;
	}

}

