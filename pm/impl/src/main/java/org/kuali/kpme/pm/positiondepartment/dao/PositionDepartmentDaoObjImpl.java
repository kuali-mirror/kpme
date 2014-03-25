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
package org.kuali.kpme.pm.positiondepartment.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.pm.positiondepartment.PositionDepartmentBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PositionDepartmentDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionDepartmentDao {

	@Override
	public PositionDepartmentBo getPositionDepartmentById(
			String pmPositionDeptId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionDeptId", pmPositionDeptId);

        Query query = QueryFactory.newQuery(PositionDepartmentBo.class, crit);
        return (PositionDepartmentBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}


    public List<PositionDepartmentBo> getDepartmentListForPosition(String hrPositionId) {
        List<PositionDepartmentBo> departmentList = new ArrayList<PositionDepartmentBo>();
        Criteria crit = new Criteria();
        crit.addEqualTo("hrPositionId", hrPositionId);

        Query query = QueryFactory.newQuery(PositionDepartmentBo.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            departmentList.addAll(c);
        }
        return departmentList;


    }
}
