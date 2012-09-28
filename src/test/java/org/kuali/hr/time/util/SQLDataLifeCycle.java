/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.enhydra.jdbc.pool.StandardXAPoolDataSource;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.core.api.lifecycle.BaseLifecycle;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class SQLDataLifeCycle  extends BaseLifecycle {
	protected static final Logger LOG = Logger.getLogger(SQLDataLifeCycle.class);

	public static final String TEST_TABLE_NAME = "KR_UNITTEST_T";
    Class callingTestClass = null;

    public SQLDataLifeCycle() {

    }

    public SQLDataLifeCycle(Class caller) {
        this.callingTestClass = caller;
    }

    public void start() throws Exception {
        final StandardXAPoolDataSource dataSource = (StandardXAPoolDataSource) TkServiceLocator.CONTEXT.getBean("kpmeDataSource");
        final PlatformTransactionManager transactionManager = (PlatformTransactionManager) TkServiceLocator.CONTEXT.getBean("transactionManager");
        final String schemaName = dataSource.getUser().toUpperCase();
        loadData(transactionManager, dataSource, schemaName);
        super.start();
    }

	public void loadData(final PlatformTransactionManager transactionManager, final DataSource dataSource, final String schemaName) {
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
                        if (callingTestClass != null) {
                        	 List<String> sqlStatements = getTestDataSQLStatements("src/test/config/sql/" + callingTestClass.getSimpleName() + ".sql");
                        
                     		for(String sql : sqlStatements){
                                if (!sql.startsWith("#") && !sql.startsWith("//") && !StringUtils.isEmpty(sql.trim())) {
                                    // ignore comment lines in our sql reader.
                    			    statement.addBatch(sql);
                                }
                    		}
                        }
                		statement.executeBatch();
                		return null;
                	}
            	});
            }
        });
	}

	void verifyTestEnvironment(final DataSource dataSource) {
		Assert.assertTrue("No table named '" + TEST_TABLE_NAME + "' was found in the configured database.  " + "You are attempting to run tests against a non-test database!!!",
		isTestTableInSchema(dataSource));
	}

	Boolean isTestTableInSchema(final DataSource dataSource) {
	    Assert.assertNotNull("DataSource could not be located.", dataSource);
	    return (Boolean) new JdbcTemplate(dataSource).execute(new ConnectionCallback() {
			public Object doInConnection(final Connection connection) throws SQLException {
				final ResultSet resultSet = connection.getMetaData().getTables(null, null, TEST_TABLE_NAME, null);
				return new Boolean(resultSet.next());
			}
		});
	}

	List<String> getTestDataSQLStatements(String fname){
		List<String> testDataSqlStatements = new ArrayList<String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(fname));
			String str;
			while ((str = in.readLine()) != null) {
				testDataSqlStatements.add(str);
			}
		} catch (FileNotFoundException e) {
			LOG.warn("No file found for " + fname);
		} catch (IOException e) {
			LOG.error("IO exception in test data loading");
		}
		return testDataSqlStatements;
	}

}
