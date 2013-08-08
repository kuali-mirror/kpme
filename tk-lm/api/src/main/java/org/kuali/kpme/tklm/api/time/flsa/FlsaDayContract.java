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
package org.kuali.kpme.tklm.api.time.flsa;

import java.util.List;
import java.util.Map;

import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;


/**
 * <p>FlsaDayContract interface</p>
 *
 */
public interface FlsaDayContract {
	
	/**
	 * The map of earn code to time blocks associated with the FlsaDay
	 * 
	 * <p>
	 * earnCodeToTimeBlocks of a FlsaDay
	 * </p>
	 * 
	 * @return earnCodeToTimeBlocks for FlsaDay
	 */
	public Map<String, ? extends List<? extends TimeBlockContract>> getEarnCodeToTimeBlocks();
	
	/**
	 * The list of applied TimeBlock objects associated with the FlsaDay
	 * 
	 * <p>
	 * appliedTimeBlocks of a FlsaDay
	 * </p>
	 * 
	 * @return appliedTimeBlocks for FlsaDay
	 */
	public List<? extends TimeBlockContract> getAppliedTimeBlocks();
	
	/**
	 * The map of earn code to leave blocks associated with the FlsaDay
	 * 
	 * <p>
	 * earnCodeToLeaveBlocks of a FlsaDay
	 * </p>
	 * 
	 * @return earnCodeToLeaveBlocks for FlsaDay
	 */
	public Map<String, ? extends List<? extends LeaveBlockContract>> getEarnCodeToLeaveBlocks();

	/**
	 * The list of applied LeaveBlock objects associated with the FlsaDay
	 * 
	 * <p>
	 * appliedLeaveBlocks of a FlsaDay
	 * </p>
	 * 
	 * @return appliedLeaveBlocks for FlsaDay
	 */
	public List<? extends LeaveBlockContract> getAppliedLeaveBlocks();

}
