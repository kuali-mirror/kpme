
<%@ include file="/kr/WEB-INF/jsp/tldHeader.jsp"%>
<%@ attribute name="balanceTransfer" required="true" type="org.kuali.hr.lm.balancetransfer.BalanceTransfer"%>

<c:set var="attributes" value="${DataDictionary.BalanceTransfer.attributes}" />
	<html:form action="/BalanceTransfer.do" method="POST">
		 <div class="tab-container" align="center">
			<h3>Balance Transfer</h3>
			<table cellpadding="0" class="datatable" summary="Balance Transfer">
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${attributes.effectiveDate}" 
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${attributes.effectiveDate}"
			                  	property="balanceTransfer.effectiveDate"
			                  	readOnly="true"/>
		            </td>
				</tr>
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${attributes.principalId}"
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${attributes.principalId}"
			                  	property="balanceTransfer.principalId"
			                  	readOnly="true"/>
		            </td>
				</tr>
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${attributes.fromAccrualCategory}"
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${attributes.fromAccrualCategory}"
			                  	property="balanceTransfer.fromAccrualCategory"
			                  	readOnly="true"/>
		            </td>
				</tr>
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${attributes.toAccrualCategory}"
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${attributes.toAccrualCategory}"
			                  	property="balanceTransfer.toAccrualCategory"
			                  	readOnly="true"/>
		            </td>
				</tr>		
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${attributes.transferAmount}"
						useShortLabel="false"
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${attributes.transferAmount}"
			                  	property="balanceTransfer.transferAmount"
			                  	readOnly="false"/>
		            </td>
				</tr>
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${attributes.forfeitedAmount}"
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${attributes.forfeitedAmount}"
			                  	property="balanceTransfer.forfeitedAmount"
			                  	readOnly="true"/>
		            </td>
				</tr>
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${attributes.amountTransferred}"
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${attributes.amountTransferred}"
			                  	property="balanceTransfer.amountTransferred"
			                  	readOnly="true"/>
		            </td>
				</tr>
				<tr>
					<kul:htmlAttributeHeaderCell 
						attributeEntry="${attributes.accrualCategoryRule}"
						horizontal="true" />
					<td align="left" valign="middle">
						<kul:htmlControlAttribute 
			                  	attributeEntry="${attributes.accrualCategoryRule}"
			                  	property="balanceTransfer.accrualCategoryRule"
			                  	readOnly="true"/>
		            </td>
				</tr>
			</table>
			<input type="hidden" name="forfeitedAmount" id="forfeitedAmount" value=""/>
			<input type="hidden" name="leaveCalendarDocumentId" id="leaveCalendarDocumentId" value=""/>
			<c:choose>
				<c:when test="${balanceTransfer.onLeaveApproval}">
					<html:image property="methodToCall.balanceTransferOnLeaveApproval" src='${ConfigProperties.kew.externalizable.images.url}buttonsmall_submit.gif' styleClass="tinybutton"/>
        		</c:when>
        		<c:otherwise>
					<html:image property="methodToCall.balanceTransferOnLeaveApproval" src='${ConfigProperties.kew.externalizable.images.url}buttonsmall_submit.gif' styleClass="tinybutton"/>
				</c:otherwise>
			</c:choose>
			<html:image property="methodToCall.cancel" src='${ConfigProperties.kew.externalizable.images.url}buttonsmall_cancel.gif' styleClass="tinybutton"/>
			
	</html:form>
</div>
