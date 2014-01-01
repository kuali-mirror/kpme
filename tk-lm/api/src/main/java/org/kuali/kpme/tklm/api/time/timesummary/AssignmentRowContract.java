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
package org.kuali.kpme.tklm.api.time.timesummary;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>AssignmentRowContract interface</p>
 *
 */
public interface AssignmentRowContract {
	
	/**
	 * The description associated with the AssignmentRow
	 * 
	 * <p>
	 * descr for an AssignmentRow
	 * <p>
	 * 
	 * @return descr for AssignmentRow
	 */
	public String getDescr();

	/**
	 * The assignment key associated with the AssignmentRow
	 * 
	 * <p>
	 * assignmentKey for an AssignmentRow
	 * <p>
	 * 
	 * @return assignmentKey for AssignmentRow
	 */
	public String getAssignmentKey();
	
	/**
	 * The cssClass associated with the AssignmentRow
	 * 
	 * <p>
	 * cssClass for an AssignmentRow
	 * <p>
	 * 
	 * @return cssClass for AssignmentRow
	 */
	public String getCssClass();
	
	/**
	 * The EarnCodeSection object associated with the AssignmentRow
	 * 
	 * <p>
	 * earnCodeSection for an AssignmentRow
	 * <p>
	 * 
	 * @return earnCodeSection for AssignmentRow
	 */
	public EarnCodeSectionContract getEarnCodeSection();
	
	/**
	 * The period total associated with the AssignmentRow
	 * 
	 * <p>
	 * periodTotal for an AssignmentRow
	 * <p>
	 * 
	 * @return periodTotal for AssignmentRow
	 */
	public BigDecimal getPeriodTotal();

	/**
	 * The map of AssignmentColumn objects associated with the AssignmentRow
	 * 
	 * <p>
	 * assignmentColumns for an AssignmentRow
	 * <p>
	 * 
	 * @return assignmentColumns for AssignmentRow
	 */
	public  Map<Integer, ? extends AssignmentColumnContract> getAssignmentColumns();

}
