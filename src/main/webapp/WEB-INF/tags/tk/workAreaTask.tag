<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<%@ attribute name="taskList" required="true" description="The list of tasks" type="java.util.List" %>
<%@ attribute name="inquiry"  required="true" %>

<c:set var="taskAttributes" value="${DataDictionary.Task.attributes}" />
<c:set var="document" value="${KualiForm.document}" />
<c:set var="readOnly" value="false"/>

<kul:tab tabTitle="Tasks" defaultOpen="true" transparentBackground="${inquiry}" tabErrorKey="newTask*">
<div class="tab-container" align="center">
	<table cellpadding="0" cellspacing="0" summary="">
		<tr>
			<th>&nbsp;</th> 
			<kul:htmlAttributeHeaderCell attributeEntry="${taskAttributes.description}" horizontal="false" />
			<kul:htmlAttributeHeaderCell attributeEntry="${taskAttributes.adminDescription}" horizontal="false" />
			<kul:htmlAttributeHeaderCell attributeEntry="${taskAttributes.effectiveDate}" horizontal="false" />
			<kul:htmlAttributeHeaderCell attributeEntry="${taskAttributes.active}" horizontal="false" />
			<th>action</th>
		</tr>
		
		<tr>
			<th>add</th>
			<td align="left" valign="middle">
				<div align="center">
				<kul:htmlControlAttribute property="newTask.description" attributeEntry="${taskAttributes.description}" readOnly="${readOnly}"/>
				</div>
			</td>
			<td align="left" valign="middle">
				<div align="center">
				<kul:htmlControlAttribute property="newTask.adminDescription" attributeEntry="${taskAttributes.adminDescription}" readOnly="${readOnly}"/>
				</div>
			</td>
			<td align="left" valign="middle">
				<div align="center">
				<kul:htmlControlAttribute datePicker="true" property="newTask.effectiveDate" attributeEntry="${taskAttributes.effectiveDate}" readOnly="${readOnly}"/>
				</div>
			</td>
			<td align="left" valign="middle">
				<div align="center">
				<kul:htmlControlAttribute property="newTask.active" attributeEntry="${taskAttributes.active}" readOnly="${readOnly}"/>
				</div>
			</td>			
			<td>
				<div align="center">
				<html:image property="methodToCall.addTask.anchor${tabKey}" src='${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif' styleClass="tinybutton"/>
				</div>			
			</td>
		</tr>

		<c:forEach var="task" items="${document.workArea.tasks}" varStatus="statusMember">
		<tr>
			<th class="infoline" valign="top">
				<c:out value="${statusMember.index+1}"/>
			</th>
			<td align="left" valign="middle">
				<div align="center">
				<kul:htmlControlAttribute property="document.workArea.tasks[${statusMember.index}].description" attributeEntry="${taskAttributes.description}" readOnly="${readOnly}"/>
				</div>
			</td>
			<td align="left" valign="middle">
				<div align="center">
				<kul:htmlControlAttribute property="document.workArea.tasks[${statusMember.index}].adminDescription" attributeEntry="${taskAttributes.adminDescription}" readOnly="${readOnly}"/>
				</div>
			</td>			
			<td align="left" valign="middle">
				<div align="center">
				<kul:htmlControlAttribute datePicker="true" property="document.workArea.tasks[${statusMember.index}].effectiveDate" attributeEntry="${taskAttributes.effectiveDate}" readOnly="${readOnly}"/>
				</div>			
			</td>
			<td align="left" valign="middle">
				<div align="center">
				<kul:htmlControlAttribute property="document.workArea.tasks[${statusMember.index}].active" attributeEntry="${taskAttributes.active}" readOnly="${readOnly}"/>
				</div>
			</td>			
			<td>
			<div align="center">
				<html:image property='methodToCall.removeTask.line${statusMember.index}.anchor${currentTabIndex}' src='${ConfigProperties.kr.externalizable.images.url}tinybutton-delete1.gif' styleClass='tinybutton'/>
			</div>
			</td>			
		</tr>
		</c:forEach>
	</table>
</div>
</kul:tab>