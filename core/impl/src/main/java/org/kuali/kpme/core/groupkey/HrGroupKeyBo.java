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


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.groupkey.HrGroupKeyContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.institution.InstitutionBo;
import org.kuali.kpme.core.location.LocationBo;
import org.kuali.rice.location.api.campus.Campus;

import java.util.Map;

public class HrGroupKeyBo extends HrBusinessObject implements HrGroupKeyContract {
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(Elements.GROUP_KEY_CODE)
            .build();
    private static final long serialVersionUID = 4566214396709772458L;
    private String id;
    private String groupKeyCode;
    private String institutionCode;
    private String description;

    private String locationId;
    private String campusCode;

    private LocationBo location;
    private Campus campus;
    private InstitutionBo institution;


    @Override
    public Map<String, Object> getBusinessKeyValuesMap() {
        return new ImmutableMap.Builder<String, Object>()
                .put(Elements.GROUP_KEY_CODE, this.getGroupKeyCode())
                .build();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected String getUniqueKey() {
        return getGroupKeyCode();
    }

    @Override
    public String getGroupKeyCode() {
        return groupKeyCode;
    }

    public void setGroupKeyCode(String groupKeyCode) {
        this.groupKeyCode = groupKeyCode;
    }

    @Override
    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @Override
    public String getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(String campusCode) {
        this.campusCode = campusCode;
    }


    public LocationBo getLocation() {
        return location;
    }

    public void setLocation(LocationBo location) {
        this.location = location;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public InstitutionBo getInstitution() {
        return institution;
    }

    public void setInstitution(InstitutionBo institution) {
        this.institution = institution;
    }

    public static HrGroupKeyBo from(HrGroupKey im) {
        if (im == null) {
            return null;
        }
        HrGroupKeyBo dg = new HrGroupKeyBo();
        dg.setId(im.getId());
        dg.setGroupKeyCode(im.getGroupKeyCode());
        dg.setInstitutionCode(im.getInstitutionCode());
        dg.setDescription(im.getDescription());
        dg.setLocationId(im.getLocationId());
        dg.setCampusCode(im.getCampusCode());

        dg.setLocation(im.getLocation() == null ? null : LocationBo.from(im.getLocation()));
        dg.setCampus(im.getCampus());
        dg.setInstitution(im.getInstitution() == null ? null : InstitutionBo.from(im.getInstitution()));

        dg.setEffectiveDate(im.getEffectiveLocalDate() == null ? null : im.getEffectiveLocalDate().toDate());
        dg.setActive(im.isActive());
        if (im.getCreateTime() != null) {
            dg.setTimestamp(new java.sql.Timestamp(im.getCreateTime().getMillis()));
        }
        dg.setUserPrincipalId(im.getUserPrincipalId());
        dg.setVersionNumber(im.getVersionNumber());
        dg.setObjectId(im.getObjectId());

        return dg;
    }

    public static HrGroupKey to(HrGroupKeyBo bo) {
        if (bo == null) {
            return null;
        }

        return HrGroupKey.Builder.create(bo).build();
    }

    static class Elements {
        final static String GROUP_KEY_CODE = "groupKeyCode";
    }


}
