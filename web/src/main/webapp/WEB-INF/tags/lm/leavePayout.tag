
<%@ include file="/kr/WEB-INF/jsp/tldHeader.jsp"%>
<%@ attribute name="leavePayout" required="true" type="org.kuali.hr.lm.leavepayout.LeavePayout"%>
<div>
<c:set var="payoutAttributes" value="${DataDictionary.LeavePayout.attributes}" />
	<html:form action="/LeavePayout.do" method="POST">
		 <div class="tab-container" align="center">
			<h3>Leave Payout</h3>
			<table cellpadding="0" class="datatable" summary="Leave Payout">
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${payoutAttributes.effectiveDate}" 
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${payoutAttributes.effectiveDate}"
			                  	property="leavePayout.effectiveDate"
			                  	readOnly="true"/>
		            </td>
				</tr>
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${payoutAttributes.principalId}"
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${payoutAttributes.principalId}"
			                  	property="leavePayout.principalId"
			                  	readOnly="true"/>
		            </td>
				</tr>
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${payoutAttributes.fromAccrualCategory}"
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${payoutAttributes.fromAccrualCategory}"
			                  	property="leavePayout.fromAccrualCategory"
			                  	readOnly="true"/>
		            </td>
				</tr>
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${payoutAttributes.payoutAmount}"
						useShortLabel="false"
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${payoutAttributes.payoutAmount}"
			                  	property="leavePayout.payoutAmount"
			                  	readOnly="false"/>
		            </td>
				</tr>
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${payoutAttributes.forfeitedAmount}"
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${payoutAttributes.forfeitedAmount}"
			                  	property="leavePayout.forfeitedAmount"
			                  	readOnly="true"/>
		            </td>
				</tr>
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${payoutAttributes.earnCode}"
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${payoutAttributes.earnCode}"
			                  	property="leavePayout.earnCode"
			                  	readOnly="true"/>
		            </td>
				</tr>		
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${payoutAttributes.accrualCategoryRule}"
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${payoutAttributes.accrualCategoryRule}"
			                  	property="leavePayout.accrualCategoryRule"
			                  	readOnly="true"/>
		            </td>
				</tr>
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${payoutAttributes.leaveCalendarDocumentId}"
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${payoutAttributes.leaveCalendarDocumentId}"
			                  	property="leavePayout.leaveCalendarDocumentId"
			                  	readOnly="true"/>
		            </td>
				</tr>
			</table>
			<input type="hidden" name="forfeitedAmount" id="forfeitedAmount" value=""/>
			<input type="hidden" name="leaveCalendarDocumentId" id="leaveCalendarDocumentId" value=""/>
			<c:choose>
				<c:when test="${leavePayout.onLeaveApproval}">
					<html:image property="methodToCall.leavePayoutOnLeaveApproval" src='${ConfigProperties.kew.externalizable.images.url}buttonsmall_submit.gif' styleClass="tinybutton"/>
        		</c:when>
        		<c:otherwise>
					<html:image property="methodToCall.leavePayoutOnLeaveApproval" src='${ConfigProperties.kew.externalizable.images.url}buttonsmall_submit.gif' styleClass="tinybutton"/>
				</c:otherwise>
			</c:choose>
			<html:image property="methodToCall.cancel" src='${ConfigProperties.kew.externalizable.images.url}buttonsmall_cancel.gif' styleClass="tinybutton"/>
		</div>
	</html:form>
</div>
