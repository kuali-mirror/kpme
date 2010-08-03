<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<%@ attribute name="roleList" required="true" description="The list of roles" type="java.util.List" %>
<%@ attribute name="inquiry"  required="true" %>

<c:set var="roleAssignmentAttributes" value="${DataDictionary.TkRoleAssign.attributes}" />
<c:set var="document" value="${KualiForm.document}" />
<c:set var="readOnly" value="false"/>

<kul:tab tabTitle="Role Assignments" defaultOpen="true" transparentBackground="${inquiry}" tabErrorKey="">
<div class="tab-container" align="center">
	<table cellpadding="0" cellspacing="0" summary="">
		<tr>
			<th>&nbsp;</th> 
			<kul:htmlAttributeHeaderCell attributeEntry="${roleAssignmentAttributes.principalId}" horizontal="false" />
			<kul:htmlAttributeHeaderCell attributeEntry="${DataDictionary.PersonImpl.attributes.name}" horizontal="false" />
			<kul:htmlAttributeHeaderCell attributeEntry="${roleAssignmentAttributes.roleName}" horizontal="false" />
			<th>action</th>
		</tr>
		
		<tr>
			<th>add</th>
			<td align="left" valign="middle">
				<div align="center">
				<input type="text" name"placeholder"/>
				<kul:lookup boClassName="org.kuali.hr.time.clock.location.ClockLocationRule"  anchor="${tabKey}" />
				</div>
			</td>
			<td align="left" valign="middle">
				<div align="center">
				(username)
				</div>
			</td>
			<td align="left" valign="middle">
				<div align="center">
				<select>
					<option>TK_APPROVER</option>
					<option>SOMETHING_ELSE</option>
				</select>
				</div>
			</td>
			<td>
				<div align="center">
				<html:image property="methodToCall.addMember.anchor${tabKey}" src='${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif' styleClass="tinybutton"/>
				</div>			
			</td>
		</tr>

		<c:forEach var="member" items="${roleList}" varStatus="statusMember">
		<tr>
			<th class="infoline" valign="top">
				<c:out value="${statusMember.index+1}"/>
			</th>
			<td align="left" valign="middle">
				<div align="center">${member.principalId}</div>
			</td>
			<td align="left" valign="middle">
				<div align="center">(users name)</div>
			</td>			
			<td align="left" valign="middle">
				<div align="center">${member.roleName}</div>
			</td>
			<td>
			<div align="center">
				<html:image property='methodToCall.deleteMember.line${statusMember.index}.anchor${currentTabIndex}' src='${ConfigProperties.kr.externalizable.images.url}tinybutton-delete1.gif' styleClass='tinybutton'/>
			</div>
			</td>
			
			
		</tr>
		</c:forEach>
	</table>
</div>
</kul:tab>