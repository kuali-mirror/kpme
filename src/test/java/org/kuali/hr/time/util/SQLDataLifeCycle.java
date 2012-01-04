package org.kuali.hr.time.util;

import junit.framework.Assert;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.enhydra.jdbc.pool.StandardXAPoolDataSource;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.ken.core.BaseLifecycle;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
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
        final StandardXAPoolDataSource dataSource = (StandardXAPoolDataSource) TkServiceLocator.CONTEXT.getBean("tkDataSource");
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
		new TransactionTemplate(transactionManager).execute(    new TransactionCallback() {
            public Object doInTransaction(final TransactionStatus status) {
            	verifyTestEnvironment(dataSource);
            	return new JdbcTemplate(dataSource).execute(new StatementCallback() {
                	public Object doInStatement(Statement statement) throws SQLException {
                        List<String> sqlStatements = getTestDataSQLStatements("src/test/config/sql/tk-test-data.sql");
                        //
                        // djunk - add a per-class special test data loader,
                        // loads <testclassname>.sql from the same directory
                        // as the other SQL loaded.
                        if (callingTestClass != null) {
                            sqlStatements.addAll(getTestDataSQLStatements("src/test/config/sql/" + callingTestClass.getSimpleName() + ".sql"));
                        }
                		for(String sql : sqlStatements){
                            if (!sql.startsWith("#") && !sql.startsWith("//") && !StringUtils.isEmpty(sql.trim())) {
                                // ignore comment lines in our sql reader.
                			    statement.addBatch(sql);
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
