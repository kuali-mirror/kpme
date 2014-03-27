/*
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
function processChangeSubmitForm(methodToCall) {
    var additionalData = {};
    additionalData["oldProcess"] = jQuery('#position_process_control').data("oldProcess");
    additionalData["newProcess"] = jQuery('#position_process_control').val();
    closeLightbox();
    ajaxSubmitForm(methodToCall,additionalData);
}

function highlightProcessRequiredChanges() {
    if (jQuery('#position_process_control').val() == '') {
        jQuery('.ui-datepicker-trigger').hide();
    } else {
        jQuery('.ui-datepicker-trigger').show();
    }

    if (jQuery("#position_process_control").val() == "Reorganization") {
        jQuery("input[name='document.newMaintainableObject.dataObject.primaryDepartment']").css("border","2px solid orange");
        jQuery("input[name='document.newMaintainableObject.dataObject.reportsToPositionId']").css("border","2px solid orange");
    } else {
        jQuery("input[name='document.newMaintainableObject.dataObject.primaryDepartment']").css("border","");
        jQuery("input[name='document.newMaintainableObject.dataObject.reportsToPositionId']").css("border","");
    }

    if (jQuery("#position_process_control").val() == "Reclassification") {
        jQuery("input[name='document.newMaintainableObject.dataObject.positionClass']").css("border","2px solid orange");
    } else {
        jQuery("input[name='document.newMaintainableObject.dataObject.positionClass']").css("border","");}

    if (jQuery("#position_process_control").val() == "Change Status") {
        jQuery("select[name='document.newMaintainableObject.dataObject.positionStatus']").css("border","2px solid orange");
    } else {
        jQuery("select[name='document.newMaintainableObject.dataObject.positionStatus']").css("border","");
    }
}

