<%@ include file="/kr/WEB-INF/jsp/tldHeader.jsp"%>

<%@ attribute name="editingMode" required="true" description="used to decide if items may be edited" type="java.util.Map"%>
<c:set var="readOnly" value="${!KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />

<kul:tab tabTitle="Missed Punch" defaultOpen="true">

<c:set var="boeAttributes" value="${DataDictionary.MissedPunchDocument.attributes}" />
 <div class="tab-container" align=center>
	<h3>Missed Punch</h3>
	<table cellpadding=0 class="datatable" summary="Missed Punch">
		<tr>
			<kul:htmlAttributeHeaderCell attributeEntry="${boeAttributes.timesheetDocumentId}">
			<kul:htmlControlAttribute 
                  	attributeEntry="${boeAttributes.timesheetDocumentId}"
                  	property="document.timesheetDocumentId"
                  	readOnly="true"/>
            </kul:htmlAttributeHeaderCell>
		</tr>
		<tr>
			<kul:htmlAttributeHeaderCell attributeEntry="${boeAttributes.principalId}">
			<kul:htmlControlAttribute 
                  	attributeEntry="${boeAttributes.principalId}"
                  	property="document.principalId"
                  	readOnly="true"/>
            </kul:htmlAttributeHeaderCell>
		</tr>
		<tr>
			<kul:htmlAttributeHeaderCell attributeEntry="${boeAttributes.assignment}">
			<kul:htmlControlAttribute 
                  	attributeEntry="${boeAttributes.assignment}"
                  	property="document.assignment"
                  	readOnly="${readOnly}"/>
            </kul:htmlAttributeHeaderCell>
		</tr>
		<tr>
			<kul:htmlAttributeHeaderCell attributeEntry="${boeAttributes.clockAction}">
			<kul:htmlControlAttribute 
                  	attributeEntry="${boeAttributes.clockAction}"
                  	property="document.clockAction"
                  	readOnly="${readOnly}"/>
            </kul:htmlAttributeHeaderCell>
		</tr>		
		<tr>
			<kul:htmlAttributeHeaderCell attributeEntry="${boeAttributes.actionDate}">
			<kul:htmlControlAttribute 
                  	attributeEntry="${boeAttributes.actionDate}"
                  	property="document.actionDate"
                  	readOnly="${readOnly}"/>
            </kul:htmlAttributeHeaderCell>
		</tr>			
		<tr>
			<kul:htmlAttributeHeaderCell attributeEntry="${boeAttributes.actionTime}">
			<kul:htmlControlAttribute 
                  	attributeEntry="${boeAttributes.actionTime}"
                  	property="document.actionTime"
                  	readOnly="${readOnly}"/>
            </kul:htmlAttributeHeaderCell>
		</tr>							
	</table>
</div>
</kul:tab>
