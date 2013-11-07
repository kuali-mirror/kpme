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
package org.kuali.kpme.core.api.block;



import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.kuali.kpme.core.api.util.HrApiConstants;

public class CalendarBlockPermissions implements Serializable {
    public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "CalendarBlockPermission";

    private String blockId;
    private Map<String, Boolean> canEdit = new HashMap<String, Boolean>();
    private Map<String, Boolean> canEditAllFields = new HashMap<String, Boolean>();
    private Map<String, Boolean> canDelete = new HashMap<String, Boolean>();
    private Map<String, Boolean> canEditOvertimeEarnCode = new HashMap<String, Boolean>();

    public static CalendarBlockPermissions newInstance(String timeBlockId) {
        return new CalendarBlockPermissions(timeBlockId);
    }

    public CalendarBlockPermissions(String blockId) {
        this.blockId = blockId;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public Map<String, Boolean> getCanEdit() {
        return canEdit;
    }

    public Boolean isPrincipalCanEdit(String principalId) {
        return getCanEdit().get(principalId);
    }

    public void setCanEdit(Map<String, Boolean> canEdit) {
        this.canEdit = canEdit;
    }

    public void putCanEdit(String principalId, Boolean edit) {
        this.canEdit.put(principalId, edit);
    }

    public Map<String, Boolean> getCanEditAllFields() {
        return canEditAllFields;
    }

    public Boolean isPrincipalCanEditAllFields(String principalId) {
        return getCanEditAllFields().get(principalId);
    }

    public void setCanEditAllFields(Map<String, Boolean> canEditAllFields) {
        this.canEditAllFields = canEditAllFields;
    }

    public void putCanEditAllFields(String principalId, Boolean editAll) {
        this.canEditAllFields.put(principalId, editAll);
    }

    public Map<String, Boolean> getCanDelete() {
        return canDelete;
    }

    public Boolean isPrincipalCanDelete(String principalId) {
        return getCanDelete().get(principalId);
    }

    public void setCanDelete(Map<String, Boolean> canDelete) {
        this.canDelete = canDelete;
    }

    public void putCanDelete(String principalId, Boolean delete) {
        this.canDelete.put(principalId, delete);
    }


    public Map<String, Boolean> getCanEditOvertimeEarnCode() {
        return canEditOvertimeEarnCode;
    }

    public Boolean isPrincipalCanEditOvertimeEarnCode(String principalId) {
        return getCanEditOvertimeEarnCode().get(principalId);
    }

    public void setCanEditOvertimeEarnCode(Map<String, Boolean> canEdit) {
        this.canEditOvertimeEarnCode = canEdit;
    }

    public void putCanEditOvertimeEarnCode(String principalId, Boolean edit) {
        this.canEditOvertimeEarnCode.put(principalId, edit);
    }

//    public Boolean getCanEditRegEarnCode() {
//        return canEditRegEarnCode;
//    }
//
//    public void setCanEditRegEarnCode(Boolean canEditRegEarnCode) {
//        this.canEditRegEarnCode = canEditRegEarnCode;
//    }
}
