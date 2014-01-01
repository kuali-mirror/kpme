/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.utils;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
@Ignore
public class TestHarnessNonWebBase extends TestHarnessBase {
    
    private static final Logger LOG = Logger.getLogger(TestHarnessNonWebBase.class);
    
	
    @Override
    @Before
    public void setUp() throws Exception {
        try{
            //TkServiceLocator.start();
            // TODO - Start up Minimal set of services here, perhaps depending on test case need.
        } catch(Exception e){
		LOG.error("Failed to start TK app lifecycle", e);
		throw new RuntimeException("Failed to start TK app lifecycle", e);
        }
        super.setUp();
    }
    
    @Override
    @After
    public void tearDown() throws Exception {
	super.tearDown();
    }

}
