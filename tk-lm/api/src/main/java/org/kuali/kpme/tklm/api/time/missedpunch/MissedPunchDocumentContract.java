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
package org.kuali.kpme.tklm.api.time.missedpunch;

import org.kuali.rice.krad.document.SessionDocument;
import org.kuali.rice.krad.document.TransactionalDocument;



/**
 * <p>MissedPunchDocumentContract interface</p>
 *
 */
public interface MissedPunchDocumentContract extends TransactionalDocument, SessionDocument {

	/**
	 * The primary key of a MissedPunchDocument entry saved in a database
	 * 
	 * <p>
	 * tkMissedPunchId of a MissedPunchDocument
	 * <p>
	 * 
	 * @return tkMissedPunchId for MissedPunchDocument
	 */
	public String getTkMissedPunchId();

	/**
	 * The MissedPunch object associated with the MissedPunchDocument
	 * 
	 * <p>
	 * missedPunch of a MissedPunchDocument
	 * <p>
	 * 
	 * @return missedPunch for MissedPunchDocument
	 */
	public MissedPunchContract getMissedPunch();

}
