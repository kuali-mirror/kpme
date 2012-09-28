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

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class DatabaseCleanupDataLifecycle extends SQLDataLifeCycle {

    public DatabaseCleanupDataLifecycle(Class caller) {
        super(caller);
    }

    public void loadData(final PlatformTransactionManager transactionManager, final DataSource dataSource, final String schemaName) {
        Assert.assertNotNull("DataSource could not be located.", dataSource);

        if (schemaName == null || schemaName.equals("")) {
            Assert.fail("Empty schema name given");
        }
        new TransactionTemplate(transactionManager).execute(new TransactionCallback() {
            public Object doInTransaction(final TransactionStatus status) {
                verifyTestEnvironment(dataSource);
                return new JdbcTemplate(dataSource).execute(new StatementCallback() {
                    public Object doInStatement(Statement statement) throws SQLException {
                        List<String> sqlStatements = new ArrayList<String>();
                        //
                        // djunk - add a per-class special test data loader,
                        // loads <testclassname>.sql from the same directory
                        // as the other SQL loaded.
                        if (callingTestClass != null) {
                            sqlStatements.addAll(getTestDataSQLStatements("src/test/config/sql/" + callingTestClass.getSimpleName() + "-cleanup.sql"));
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
}
