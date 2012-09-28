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
package org.kuali.hr.time.position.service;

import org.kuali.hr.core.cache.CacheUtils;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class PositionMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @Override
    public HrBusinessObject getObjectById(String id) {
        return TkServiceLocator.getPositionService().getPosition(id);
    }

    @Override
    public void saveBusinessObject() {
        Position position = (Position) this.getBusinessObject();
        //String nextUniqueNumber = TkServiceLocator.getPositionService().getNextUniquePositionNumber();
        //position.setPositionNumber(nextUniqueNumber);

        position = KRADServiceLocator.getBusinessObjectService().save(position);
        CacheUtils.flushCache(Position.CACHE_NAME);
    }
}
