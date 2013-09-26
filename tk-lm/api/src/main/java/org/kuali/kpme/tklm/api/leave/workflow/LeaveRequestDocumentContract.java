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
package org.kuali.kpme.tklm.api.leave.workflow;

import org.kuali.kpme.core.api.assignment.Assignable;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.rice.krad.document.SessionDocument;
import org.kuali.rice.krad.document.TransactionalDocument;



/**
 * <p>LeaveRequestDocumentContract interface</p>
 *
 */
public interface LeaveRequestDocumentContract extends TransactionalDocument, SessionDocument, Assignable {
    
	/**
	 * The lmLeaveBlockId associated with the LeaveRequestDocument
	 * 
	 * <p>
	 * lmLeaveBlockId of a LeaveRequestDocument
	 * <p>
	 * 
	 * @return lmLeaveBlockId for LeaveRequestDocument
	 */
	public String getLmLeaveBlockId();
   
	/**
	 * The actionCode associated with the LeaveRequestDocument
	 * 
	 * <p>
	 * actionCode of a LeaveRequestDocument
	 * <p>
	 * 
	 * @return actionCode for LeaveRequestDocument
	 */
    public String getActionCode() ;
   
    /**
	 * The description associated with the LeaveRequestDocument
	 * 
	 * <p>
	 * description of a LeaveRequestDocument
	 * <p>
	 * 
	 * @return description for LeaveRequestDocument
	 */
    public String getDescription();

    /**
	 * The LeaveBlock object associated with the LeaveRequestDocument
	 * 
	 * <p>
	 * LeaveBlock of lmLeaveBlockId of a LeaveRequestDocument
	 * <p>
	 * 
	 * @return LeaveBlock of lmLeaveBlockId for LeaveRequestDocument
	 */
    public LeaveBlockContract getLeaveBlock();

}
