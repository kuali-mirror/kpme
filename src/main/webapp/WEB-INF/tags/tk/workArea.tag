<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<c:set var="groupAttributes" value="${DataDictionary.WorkAreaMaintenanceDocument.attributes}" />
<c:set var="document" value="${KualiForm.workAreaMaintenanceDocument}" />

${groupAttributes}
<br/>
---
${groupAttributes['workArea.workAreaId']}
---
${document.foo }

<kul:tab tabTitle="Overview" defaultOpen="true" transparentBackground="${inquiry}" tabErrorKey="">

<div class="tab-container" align="center">
	<table cellpadding="0" cellspacing="0" summary=""> 
	 	<tr>
			<th><div align="right"><kul:htmlAttributeLabel attributeEntry="${groupAttributes['workArea.workAreaId']}"/></div></th>
			<td>
				<kul:htmlControlAttribute property="document.foo" attributeEntry="${groupAttributes.description}" readOnly="true" readOnlyBody="true" >
				${document.foo}
				</kul:htmlControlAttribute>
			</td>
			<td>
				<kul:htmlControlAttribute property="document.foo" attributeEntry="${groupAttributes.description}" readOnly="true" readOnlyBody="true"/>
			</td>
	 	</tr>
	</table> 
</div>
</kul:tab>

