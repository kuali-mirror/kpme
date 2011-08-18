
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

