<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<c:set var="groupAttributes" value="${DataDictionary.WorkAreaMaintenanceDocument.attributes}" />
<c:set var="document" value="${KualiForm.document}" />
<c:set var="readOnly" value="false"/>

<kul:tab tabTitle="Overview" defaultOpen="true" transparentBackground="${inquiry}" tabErrorKey="">
<div class="tab-container" align="center">
	<table cellpadding="0" cellspacing="0" summary=""> 
	 	<tr>
			<th><div align="left"><kul:htmlAttributeLabel attributeEntry="${groupAttributes['workArea.deptId']}"/></div></th>
			<td><kul:htmlControlAttribute property="document.workArea.deptId" attributeEntry="${groupAttributes['workArea.deptId']}" readOnly="true"/></td>	 	
			<th><div align="right"><kul:htmlAttributeLabel attributeEntry="${groupAttributes['workArea.workAreaId']}"/></div></th>
			<td><kul:htmlControlAttribute property="document.workArea.workAreaId" attributeEntry="${groupAttributes['workArea.workAreaId']}" readOnly="true"/></td>
		</tr>
		<tr>
			<th><div align="left"><kul:htmlAttributeLabel attributeEntry="${groupAttributes['workArea.description']}"/></div></th>
	 		<td><kul:htmlControlAttribute property="document.workArea.description" attributeEntry="${groupAttributes['workArea.description']}" readOnly="${readOnly}" /></td>
	 		<th><div align="right"><kul:htmlAttributeLabel attributeEntry="${groupAttributes['workArea.adminDescr']}"/></div></th>
	 		<td><kul:htmlControlAttribute property="document.workArea.adminDescr" attributeEntry="${groupAttributes['workArea.adminDescr']}" readOnly="${readOnly}" /></td>
	 	</tr>
	 	<tr>
	 		<th><div align="left"><kul:htmlAttributeLabel attributeEntry="${groupAttributes['workArea.active']}"/></div></th>
	 		<td><kul:htmlControlAttribute property="document.workArea.active" attributeEntry="${groupAttributes['workArea.active']}" readOnly="${readOnly}" /></td>	 	
	 	</tr>
	</table> 
</div>
</kul:tab>

