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
package org.kuali.hr.time.position.dao;

import org.kuali.hr.time.position.Position;

import java.sql.Date;
import java.util.List;
//import org.kuali.hr.time.position.PositionNumber;

public interface PositionDao {
	public Position getPosition(String hrPositionId);
	public Position getPositionByPositionNumber(String hrPositionNbr);
    //public PositionNumber getNextUniquePositionNumber();

    //void saveOrUpdate(PositionNumber positionNumber);

    public List<Position> getPositions(String positionNum, String workArea, String positionDescr, Date fromEffdt, Date toEffdt, String active, String showHistory);
}
