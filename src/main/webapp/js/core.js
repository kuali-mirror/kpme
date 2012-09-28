/*
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

function setDateValues(object) {
	if(document.getElementById && document.getElementById("document.newMaintainableObject.add.roles.effectiveDate")) {
		document.getElementById("document.newMaintainableObject.add.roles.effectiveDate").value = object.value;
		for(var i=0; i>=0; i++){
			if(document.getElementById && document.getElementById("document.newMaintainableObject.roles["+i+"].effectiveDate")){
				document.getElementById("document.newMaintainableObject.roles["+i+"].effectiveDate").value = object.value;
			}else{
				break;
			}
		}	
	}
	if(document.getElementById && document.getElementById("document.newMaintainableObject.add.tasks.effectiveDate")) {
		document.getElementById("document.newMaintainableObject.add.tasks.effectiveDate").value = object.value;
		for(var i=0; i>=0; i++){
			if(document.getElementById && document.getElementById("document.newMaintainableObject.tasks["+i+"].effectiveDate")){
				document.getElementById("document.newMaintainableObject.tasks["+i+"].effectiveDate").value = object.value;
			}else{
				break;
			}
		}	
	}
}

function setAssignAccountActiveState(object){
	if(document.getElementById && document.getElementById("document.newMaintainableObject.add.assignmentAccounts.active")) {
		document.getElementById("document.newMaintainableObject.add.assignmentAccounts.active").checked = object.checked;
		for(var i=0; i>=0; i++){
			if(document.getElementById && document.getElementById("document.newMaintainableObject.assignmentAccounts["+i+"].active")){
				document.getElementById("document.newMaintainableObject.assignmentAccounts["+i+"].active").checked = object.checked;
			}else{
				break;
			}
		}	
	}
}

function refreshPage(object) {
	var frm=object.form;
	var methodToCallElement=document.createElement("input");
	methodToCallElement.setAttribute("type","hidden");
	methodToCallElement.setAttribute("name","methodToCall");
	methodToCallElement.setAttribute("value","refresh");
	frm.appendChild(methodToCallElement);
	frm.submit();
}

