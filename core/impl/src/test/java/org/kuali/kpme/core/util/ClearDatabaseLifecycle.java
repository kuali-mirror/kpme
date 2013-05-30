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
package org.kuali.kpme.core.util;

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
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.lifecycle.BaseLifecycle;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class ClearDatabaseLifecycle extends BaseLifecycle {

	protected static final Logger LOG = Logger.getLogger(ClearDatabaseLifecycle.class);
    private List<String> alternativeTablesToClear = new ArrayList<String>();

	private static List<String> TABLES_TO_CLEAR = new ArrayList<String>();
	static {
		TABLES_TO_CLEAR.add("HR_CALENDAR_T");
		TABLES_TO_CLEAR.add("HR_CALENDAR_ENTRIES_T");
		TABLES_TO_CLEAR.add("HR_DEPT_T");
		TABLES_TO_CLEAR.add("TK_ASSIGN_ACCT_T");
		TABLES_TO_CLEAR.add("TK_ASSIGNMENT_T");
		TABLES_TO_CLEAR.add("TK_CLOCK_LOCATION_RL_T");
		TABLES_TO_CLEAR.add("TK_CLOCK_LOG_T");
		TABLES_TO_CLEAR.add("TK_DAILY_OVERTIME_RL_T");
		TABLES_TO_CLEAR.add("HR_EARN_CODE_SECURITY_T");
		TABLES_TO_CLEAR.add("TK_DEPT_LUNCH_RL_T");
		TABLES_TO_CLEAR.add("HR_EARN_CODE_T");
		TABLES_TO_CLEAR.add("HR_EARN_CODE_GROUP_T");
		TABLES_TO_CLEAR.add("HR_EARN_CODE_GROUP_DEF_T");
		TABLES_TO_CLEAR.add("TK_GRACE_PERIOD_RL_T");
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
		TABLE_TO_ID_MAP.put("TK_GRACE_PERIOD_RL_T", "TK_GRACE_PERIOD_RULE_ID");
		TABLE_TO_ID_MAP.put("TK_CLOCK_LOCATION_RL_T", "TK_CLOCK_LOC_RULE_ID");
		TABLE_TO_ID_MAP.put("HR_CALENDAR_ENTRIES_T", "HR_CALENDAR_ENTRY_ID");
		TABLE_TO_ID_MAP.put("TK_DOCUMENT_HEADER_T", "DOCUMENT_ID");
        TABLE_TO_ID_MAP.put("KREW_RULE_T", "RULE_ID");
        TABLE_TO_ID_MAP.put("KREW_RULE_RSP_T", "RULE_RSP_ID");
        TABLE_TO_ID_MAP.put("KREW_DLGN_RSP_T", "DLGN_RULE_ID");
        TABLE_TO_ID_MAP.put("KREW_RULE_ATTR_T", "RULE_ATTR_ID");
        TABLE_TO_ID_MAP.put("KREW_RULE_TMPL_T", "RULE_TMPL_ID");
        TABLE_TO_ID_MAP.put("KREW_DOC_TYP_T", "DOC_TYP_ID");
	}

    private static final Map<String,Integer> TABLE_START_CLEAR_ID = new HashMap<String,Integer>();
    static{
        TABLE_START_CLEAR_ID.put("KREW_DOC_TYP_T", new Integer(3000));
    }

	public static final String TEST_TABLE_NAME = "KR_UNITTEST_T";

	public static final Integer START_CLEAR_ID = 1000;

	public void start() throws Exception {
		if (new Boolean(ConfigContext.getCurrentContextConfig().getProperty("tk.use.clearDatabaseLifecycle"))) {
			final StandardXAPoolDataSource dataSource = (StandardXAPoolDataSource) HrServiceLocator.getBean("kpmeDataSource");
			final PlatformTransactionManager transactionManager = (PlatformTransactionManager) HrServiceLocator.getPlatformTransactionManager();
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
		new TransactionTemplate(transactionManager).execute(new TransactionCallback<Object>() {
            public Object doInTransaction(final TransactionStatus status) {
            	verifyTestEnvironment(dataSource);
            	return new JdbcTemplate(dataSource).execute(new StatementCallback<Object>() {
                	public Object doInStatement(Statement statement) throws SQLException {
                    		final List<String> reEnableConstraints = new ArrayList<String>();
                            List<List<String>> tableLists = new ArrayList<List<String>>(2);
                            tableLists.add(TABLES_TO_CLEAR);
                            tableLists.add(alternativeTablesToClear);
                            for (List<String> list : tableLists) {
                                for (String tableName : list) {
                                    //if there is an id name that doesnt follow convention check and limit accordingly
                                    String idName = TABLE_TO_ID_MAP.get(tableName);
                                    String deleteStatement = null;
                                    Integer clearId = TABLE_START_CLEAR_ID.get(tableName) != null ? TABLE_START_CLEAR_ID.get(tableName) : START_CLEAR_ID;
                                    if(idName == null){
                                        deleteStatement = "DELETE FROM " + tableName +" WHERE "+StringUtils.removeEnd(tableName,"_T")+"_ID"+ " >= "+clearId;
                                    } else {
                                        deleteStatement = "DELETE FROM " + tableName +" WHERE "+ idName + " >= "+clearId;
                                    }

                                    LOG.debug("Clearing contents using statement ->" + deleteStatement + "<-");
                                    statement.addBatch(deleteStatement);
                                }
                            }

                            for (final String constraint : reEnableConstraints) {
                            	LOG.debug("Enabling constraints using statement ->" + constraint + "<-");
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

    public List<String> getAlternativeTablesToClear() {
        return this.alternativeTablesToClear;
    }

}

