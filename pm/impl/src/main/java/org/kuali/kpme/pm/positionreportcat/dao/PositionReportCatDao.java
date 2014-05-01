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
package org.kuali.kpme.pm.positionreportcat.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategoryBo;

public interface PositionReportCatDao {
	
	public PositionReportCategoryBo getPositionReportCatById(String pmPositionReportCatId);
	
	public List<PositionReportCategoryBo> getPositionReportCatList(String positionReportCat, String positionReportType, String groupKeyCode, LocalDate asOfDate);
	
	public PositionReportCategoryBo getPositionReportCat(String positionReportCat, LocalDate asOfDate);
}
