<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<c:set var="groupAttributes" value="${DataDictionary.WorkAreaMaintenanceDocument.attributes}" />
<c:set var="document" value="${KualiForm.workAreaMaintenanceDocument}" />
<c:set var="readOnly" value="false"/>

<br/>
${groupAttributes['workArea.workAreaId']}
<br/>
---
<br/>
${groupAttributes['workArea.deptId']}
<br/>
---

<kul:tab tabTitle="Overview" defaultOpen="true" transparentBackground="${inquiry}" tabErrorKey="">

<div class="tab-container" align="center">
	<table cellpadding="0" cellspacing="0" summary=""> 
	 	<tr>
			<th><div align="right"><kul:htmlAttributeLabel attributeEntry="${groupAttributes['workArea.workAreaId']}"/></div></th>
			<td><kul:htmlControlAttribute property="document.workArea.workAreaId" attributeEntry="${groupAttributes['workArea.workAreaId']}" readOnly="${readOnly}"/></td>
			<th><div align="right"><kul:htmlAttributeLabel attributeEntry="${groupAttributes['workArea.deptId']}"/></div></th>
	 		<td><kul:htmlControlAttribute property="document.workArea.deptId" attributeEntry="${groupAttributes['workArea.deptId']}" readOnly="${readOnly}" /></td>
	 	</tr>
	</table> 
</div>
</kul:tab>

