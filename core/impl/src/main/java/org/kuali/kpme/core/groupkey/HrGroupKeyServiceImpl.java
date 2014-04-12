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
package org.kuali.kpme.core.groupkey;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.groupkey.HrGroupKeyService;
import org.kuali.kpme.core.api.institution.Institution;
import org.kuali.kpme.core.api.institution.service.InstitutionService;
import org.kuali.kpme.core.api.location.Location;
import org.kuali.kpme.core.api.location.service.LocationService;
import org.kuali.kpme.core.groupkey.dao.HrGroupKeyDao;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.location.api.campus.Campus;
import org.kuali.rice.location.api.campus.CampusService;

import java.util.Collections;


public class HrGroupKeyServiceImpl implements HrGroupKeyService {
    private HrGroupKeyDao hrGroupKeyDao;
    private LocationService locationService;
    private InstitutionService institutionService;
    private CampusService campusService;
    private BusinessObjectService boService;

    @Override
    public HrGroupKey getHrGroupKeyById(String id) {
        return HrGroupKeyBo.to(boService.findByPrimaryKey(HrGroupKeyBo.class, Collections.singletonMap("id", id)));
    }

    @Override
    public HrGroupKey getHrGroupKey(String groupKeyCode, LocalDate asOfDate) {
        return HrGroupKeyBo.to(hrGroupKeyDao.getHrGroupKey(groupKeyCode, asOfDate));
    }

    @Override
    public HrGroupKey populateSubObjects(HrGroupKey groupKey, LocalDate asOfDate) {
        HrGroupKey.Builder builder = HrGroupKey.Builder.create(groupKey);

        if (asOfDate == null) {
            asOfDate = groupKey.getEffectiveLocalDate();
        }
        if (StringUtils.isNotEmpty(builder.getLocationId())) {
            builder.setLocation(Location.Builder.create(locationService.getLocation(builder.getLocationId(), asOfDate)));
        }
        if (StringUtils.isNotEmpty(builder.getInstitutionCode())) {
            builder.setInstitution(Institution.Builder.create(institutionService.getInstitution(builder.getInstitutionCode(), asOfDate)));
        }
        if (StringUtils.isNotEmpty(builder.getCampusCode())) {
            builder.setCampus(Campus.Builder.create(campusService.getCampus(builder.getCampusCode())));
        }
        return builder.build();
    }


    public void setHrGroupKeyDao(HrGroupKeyDao hrGroupKeyDao) {
        this.hrGroupKeyDao = hrGroupKeyDao;
    }

    public void setBoService(BusinessObjectService boService) {
        this.boService = boService;
    }

    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public void setCampusService(CampusService campusService) {
        this.campusService = campusService;
    }

    public void setInstitutionService(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }
}
