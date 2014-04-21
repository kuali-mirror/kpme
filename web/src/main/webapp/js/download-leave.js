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

function preSubmitDownloadForm(methodToCall) {
	var valid = validateForm();
	if(valid){
		ajaxSubmitForm(methodToCall);
		setupOnChangeRefresh('calendarCount', 'eventCount', '');
		var delay=500;
		setTimeout(function(){
			showLightboxComponent('Download-DialogGroup-ProgressiveDialogGroup');
		},delay);
	}
	return true;	
}

function onConfirm(methodToCall){
	nonAjaxSubmitForm(methodToCall);
	var delay=500;
	setTimeout(function(){
		var hash = top.window.location.toString();
		hash = hash.replace('&lightbox=true', '');
		hash = hash.replace('lightbox=true&', '');
		top.window.location.replace(hash);
	},delay);
	
}