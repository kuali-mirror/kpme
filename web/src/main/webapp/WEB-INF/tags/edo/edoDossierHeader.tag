<%@ tag import="org.kuali.kpme.edo.util.EdoConstants" %>
<%@ tag import="org.kuali.kpme.edo.util.EdoContext" %>
<%@ taglib prefix="x_rt" uri="http://java.sun.com/jstl/xml_rt" %>
<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<c:set var="Form" value="${EdoForm}" scope="request"/>
<c:set var="isCandidate" value="<%= EdoContext.isCandidate() %>" />
<c:set var="canSubmit" value="<%= EdoContext.canSubmitSelectedDossier() %>" />
<c:set var="canSeeSubmitDossierButton" value="<%= EdoContext.canSeeSubmitDossierButton() %>" />
<c:set var="canRoute"  value="<%= EdoContext.canRouteSelectedDossier() %>" />
<c:set var="canReturn" value="<%= EdoContext.canReturnSelectedDossier() %>" />
<c:set var="canSUApprove" value="<%= EdoContext.canSUApproveSelectedDossier() %>" />
<c:set var="canSUReturn" value="<%= EdoContext.canSUReturnSelectedDossier() %>" />
<c:set var="canEditAoe" value="<%= EdoContext.canEditAoeForSelectedDossier() %>"/>
<c:set var="canSubmitSupplemental" value="<%= EdoContext.canSubmitSupplemental() %>" />
<c:set var="canSeeHisOwnSubmitSupplemental" value="<%= EdoContext.canSeeHisOwnSupplementalButton() %>" />
<c:set var="canApproveSupplemental" value="<%= EdoContext.canApproveSuppDoc() %>" />
<c:set var="dossierStatusOpen" value="<%= EdoConstants.DOSSIER_STATUS.OPEN %>" />
<c:set var="dossierStatusPending" value="<%= EdoConstants.DOSSIER_STATUS.PENDING %>" />
<c:set var="dossierStatusSubmitted" value="<%= EdoConstants.DOSSIER_STATUS.SUBMITTED %>" />
<c:set var="dossierStatusClosed" value="<%= EdoConstants.DOSSIER_STATUS.CLOSED %>" />
<c:set var="dossierStatusReconsider" value="<%= EdoConstants.DOSSIER_STATUS.RECONSIDERATION %>" />
<c:set var="canSubmitReconsider" value="<%= EdoContext.canSubmitReconsider() %>" />
<c:set var="canRouteReconsider" value="<%= EdoContext.canRouteReconsider() %>" />
<c:set var="canUpdateDossierStatus" value="<%= EdoContext.canUpdateDossierstatus() %>" />
<c:set var="isSubmitButtonActive" value="<%= EdoContext.isSubmitButtonActive() %>" />



<div id="dossierhdr">
    <table class="noborders" width="60%">
        <tbody>
            <tr>
                <td align="right" bgcolor="#eeeeee">Candidate</td><td align="left"><strong>${selectedCandidate.candidateLastname}, ${selectedCandidate.candidateFirstname}</strong></td>
            </tr>
            <tr>
                <td align="right" bgcolor="#eeeeee">Department</td><td align="left"><strong>${selectedCandidate.candidateDepartmentID}</strong></td>
            </tr>
            <tr>
                <td align="right" bgcolor="#eeeeee">School</td><td align="left"><strong>${selectedCandidate.candidateSchoolID}</strong></td>
            </tr>
            <tr>
                <td align="right" bgcolor="#eeeeee">Dossier Type</td><td align="left"><strong>${selectedCandidate.dossierTypeName}</strong></td>
            </tr>
            <tr>
                <td align="right" bgcolor="#eeeeee">Dossier Status</td><td align="left"><strong>${selectedCandidate.dossierStatus}</strong></td>
            </tr>
            <tr>
                <td align="right" bgcolor="#eeeeee">Rank Sought</td><td align="left"><strong>${selectedCandidate.rankSought}</strong></td>
            </tr>
            <tr>
            <td align="right" bgcolor="#eeeeee">Area of Excellence</td><td align="left">
                    <html:form action="/EdoCandidateSelect">
                        <html:hidden property="methodToCall" value="updateAoe"/>
                        <html:hidden property="cid" value="${selectedCandidate.candidateID}"/>
                        <html:hidden property="candidateUsername" value="${selectedCandidate.candidateUsername}"/>
                        <html:hidden property="dossier" value="${selectedCandidate.candidateDossierID}"/>
                        <c:choose>
                            <c:when test="${canEditAoe}">
                                <select name="selectedAoe">
                                    <option value="">- Select -</option>
                                    <c:forEach var="aoe" items="${Form.getAoe()}">
                                        <c:choose>
                                            <c:when test="${selectedCandidate.aoe eq aoe.key}">
                                                <option value="${aoe.key}" selected="selected">${aoe.value}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${aoe.key}">${aoe.value}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                                <input type="image" alt="Update Area of Excellence"
                                       name="updateAoe"
                                       src="images/buttonsmall_save.gif"
                                       onclick="this.form.submit"
                                       style="border: none; vertical-align: middle;" />
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="aoe" items="${Form.getAoe()}">
                                    <c:if test="${selectedCandidate.aoe eq aoe.key}">
                                        <strong>${aoe.value}</strong>
                                    </c:if>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </html:form>
                </td>
            </tr>
            <c:if test="${selectedCandidate.dossierStatus == dossierStatusOpen}" >
                <tr>
                    <td align="right" bgcolor="#eeeeee">Dossier Status</td>
                    <td align="left">
                        <c:if test="${isValidDossier == 1}"> Ready for submission. </c:if>
                        <c:if test="${isValidDossier == 0}"> Not ready for submission. <br />Please review <a href="${guidelineURL}" target="_guidelines">requirements for submission</a>.</c:if>
                    </td>
                </tr>
            </c:if>
            <c:if test="${canSubmit and isValidDossier == 1 and canSeeSubmitDossierButton and isSubmitButtonActive}">
                <tr>
                    <td align="right" bgcolor="#eeeeee">Submit your Dossier</td><td align="left">
                        <html:form action="/EdoDossierRoute">
                            <html:hidden property="methodToCall" value="routeDocument"/>
                            <html:hidden property="cid" value="${selectedCandidate.candidateID}"/>
                            <html:hidden property="candidateUsername" value="${selectedCandidate.candidateUsername}"/>
                            <html:hidden property="dossierId" value="${selectedCandidate.candidateDossierID}"/>
                            <html:hidden property="dossierType" value="${selectedCandidate.dossierTypeName}"/>

                            <input type="image" alt="Submit Dossier"
                                   name="submitDossier" id="submitDossier"
                                   src="images/buttonsmall_submit.gif"
                                   onclick="this.form.submit"
                                   style="border: none; vertical-align: middle;" />
                           	<img src="images/ajax-loader.gif" id="ajaxSubmit" style="display: none;">
                               
                        </html:form>
                    </td>
                </tr>
            </c:if>

            <%-- submit button for candidate sign-off --%>
            <c:if test="${isCandidate and isValidDossier == 1 and canSeeSubmitDossierButton}">
                <tr>
                <td align="right" bgcolor="#eeeeee">Submit your Dossier</td><td align="left">
                <html:form action="/EdoDossierRoute">
                    <html:hidden property="methodToCall" value="submitForSignOff"/>
                    <html:hidden property="cid" value="${selectedCandidate.candidateID}"/>
                    <html:hidden property="candidateUsername" value="${selectedCandidate.candidateUsername}"/>
                    <html:hidden property="dossierId" value="${selectedCandidate.candidateDossierID}"/>
                    <html:hidden property="dossierType" value="${selectedCandidate.dossierTypeName}"/>

                    <input type="image" alt="Submit Dossier"
                           name="submitDossier" id="submitDossier"
                           src="images/buttonsmall_submit.gif"
                           onclick="this.form.submit"
                           style="border: none; vertical-align: middle;" />
                    <img src="images/ajax-loader.gif" id="ajaxSubmit" style="display: none;">

                </html:form>
            </td>
            </tr>
            </c:if>

            <c:if test="${(selectedCandidate.dossierStatus ne dossierStatusOpen) && !dossierReadyForRoute && canRoute}">
                <tr>
                    <td align="right" bgcolor="#eeeeee">Route Dossier</td>
                    <td align="left">Not ready for routing. <br />Please review requirements for routing.</td>
                </tr>
            </c:if>
			<c:if test="${canRouteReconsider}">
			 
	            <c:if test="${canRoute && dossierReadyForRoute}">
	                <tr>
	                    <td align="right" bgcolor="#eeeeee">Route Dossier</td><td align="left">
	                        <div style="float: left; padding-right: 10px;">
	                            <html:form action="/EdoDossierRoute">
	                                <html:hidden property="methodToCall" value="routeDocument"/>
	                                <html:hidden property="cid" value="${selectedCandidate.candidateID}"/>
	                                <html:hidden property="candidateUsername" value="${selectedCandidate.candidateUsername}"/>
	                                <html:hidden property="dossierId" value="${selectedCandidate.candidateDossierID}"/>
	                                <html:hidden property="dossierType" value="${selectedCandidate.dossierTypeName}"/>
	
	                                <input type="image" alt="Route Dossier"
	                                       name="routeDossier" id="routeDossier"
	                                       src="images/buttonsmall_route.gif"
	                                       onclick="this.form.submit"
	                                       style="border: none; vertical-align: middle;" />
	                                <img src="images/ajax-loader.gif" id="ajaxSubmit" style="display: none;">
                               
	                            </html:form>
	                        </div>
	                        <c:if test="${canReturn}">
	                            <div style="float: left">
	                                <html:form action="/EdoDossierRoute">
	                                    <html:hidden property="methodToCall" value="returnToCandidate"/>
	                                    <html:hidden property="cid" value="${selectedCandidate.candidateID}"/>
	                                    <html:hidden property="candidateUsername" value="${selectedCandidate.candidateUsername}"/>
	                                    <html:hidden property="dossierId" value="${selectedCandidate.candidateDossierID}"/>
	                                    <html:hidden property="dossierType" value="${selectedCandidate.dossierTypeName}"/>
	
	                                    <input type="image" alt="Cancel Dossier"
	                                           name="cancelDossier" id="cancelDossier"
	                                           src="images/buttonsmall_rtrntcandidate.gif"
	                                           onclick="this.form.submit"
	                                           style="border: none; vertical-align: middle;" />
	                                     <img src="images/ajax-loader.gif" id="ajaxSubmit" style="display: none;">
	                                </html:form>
	                            </div>
	                        </c:if>
	                    </td>
	                </tr>
	            </c:if>
	        </c:if>
            <c:if test="${canSUApprove or canSUReturn}">
                <tr>
                    <td align="right" bgcolor="#eeeeee">Super User Actions</td><td align="left">
                        <c:if test="${canSUApprove}">
                            <div style="float: left; padding-right: 10px;">
                                <html:form action="/EdoDossierRoute">
                                    <html:hidden property="methodToCall" value="superUserApprove"/>
                                    <html:hidden property="cid" value="${selectedCandidate.candidateID}"/>
                                    <html:hidden property="candidateUsername" value="${selectedCandidate.candidateUsername}"/>
                                    <html:hidden property="dossierId" value="${selectedCandidate.candidateDossierID}"/>
                                    <html:hidden property="dossierType" value="${selectedCandidate.dossierTypeName}"/>

                                    <html:select property="node">
                                        <c:forEach var="futureNode" items="${Form.futureNodes}">
                                            <html:option value="${futureNode}"></html:option>
                                        </c:forEach>
                                    </html:select>

                                    <input type="image" alt="Super User Route Dossier"
                                           name="supeUserApprove" id="supeUserApprove"
                                           src="images/buttonsmall_route.gif"
                                           onclick="this.form.submit"
                                           style="border: none; vertical-align: middle;" />
                                     <img src="images/ajax-loader.gif" id="ajaxSubmit" style="display: none;">
                                </html:form>
                            </div>
                        </c:if>
                        <c:if test="${canSUReturn}">
                            <div style="float: left; padding-right: 10px;">
                                <html:form action="/EdoDossierRoute">
                                    <html:hidden property="methodToCall" value="superUserReturn"/>
                                    <html:hidden property="cid" value="${selectedCandidate.candidateID}"/>
                                    <html:hidden property="candidateUsername" value="${selectedCandidate.candidateUsername}"/>
                                    <html:hidden property="dossierId" value="${selectedCandidate.candidateDossierID}"/>
                                    <html:hidden property="dossierType" value="${selectedCandidate.dossierTypeName}"/>

                                    <html:select property="node">
                                        <c:forEach var="previousNode" items="${Form.previousNodes}">
                                            <html:option value="${previousNode}"></html:option>
                                        </c:forEach>
                                    </html:select>

                                    <input type="image" alt="Super User Return"
                                           name="superUserReturn" id="superUserReturn"
                                           src="images/buttonsmall_retprevrtlevel.gif"
                                           onclick="this.form.submit"
                                           style="border: none; vertical-align: middle;" />
                                    <img src="images/ajax-loader.gif" id="ajaxSubmit" style="display: none;">
                                </html:form>
                            </div>
                        </c:if>
                    </td>
                </tr>
            </c:if>
            <%--added authorization check to display submit supplemental button to the candidates --%>
            <c:if test="${selectedCandidate.dossierStatus == dossierStatusSubmitted and canSubmitSupplemental and dossierHasSupplementalsPending and canSeeHisOwnSubmitSupplemental}">
                <tr>
                    <td align="right" bgcolor="#eeeeee">Submit your Supplemental Items</td><td align="left">
                    <html:form action="/EdoDossierRoute">
                        <html:hidden property="methodToCall" value="routeSupplementalDocument"/>
                        <html:hidden property="cid" value="${selectedCandidate.candidateID}"/>
                        <html:hidden property="candidateUsername" value="${selectedCandidate.candidateUsername}"/>
                        <html:hidden property="dossierId" value="${selectedCandidate.candidateDossierID}"/>
                        <html:hidden property="dossierType" value="${selectedCandidate.dossierTypeName}"/>

                        <input type="image" alt="Submit Supplemental Items"
                               name="submitDossier" id="submitDossier"
                               src="images/buttonsmall_submit.gif"
                               onclick="this.form.submit"
                               style="border: none; vertical-align: middle;" />
                        <img src="images/ajax-loader.gif" id="ajaxSubmit" style="display: none;">
                    </html:form>
                </td>
                </tr>
            </c:if>
            <c:if test="${selectedCandidate.dossierStatus == dossierStatusReconsider and canSubmitReconsider and dossierHasReconsiderPending}">
                <tr>
                    <td align="right" bgcolor="#eeeeee">Submit your Reconsider Items</td><td align="left">
                    <html:form action="/EdoDossierRoute">
                        <html:hidden property="methodToCall" value="routeReconsiderDocument"/>
                        <html:hidden property="cid" value="${selectedCandidate.candidateID}"/>
                        <html:hidden property="candidateUsername" value="${selectedCandidate.candidateUsername}"/>
                        <html:hidden property="dossierId" value="${selectedCandidate.candidateDossierID}"/>
                        <html:hidden property="dossierType" value="${selectedCandidate.dossierTypeName}"/>

                        <input type="image" alt="Submit Reconsider Items"
                               name="submitReconsider" id="submitReconsider"
                               src="images/buttonsmall_submit.gif"
                               onclick="this.form.submit"
                               style="border: none; vertical-align: middle;" />
                        <img src="images/ajax-loader.gif" id="ajaxSubmit" style="display: none;">
                    </html:form>
                </td>
                </tr>
            </c:if>
            <tr>
            	<td></td>
            </tr>
            
          	<%-- the below button has to show up only when the loggend in reviewer has the authorization to view this  --%>
          	<c:if test="${canApproveSupplemental}">
                  <c:set var="action_button_image" value="acknowledge_action_disabled.png" />
                  <c:set var="ack_button_image" value="acknowledge.png" />
                  <c:set var="ack_button_disabled" value="" />
                  <c:set var="action_button_disable" value="disabled" />
                  <c:if test="${hasTakenSupplementalAction}">
                      <c:set var="action_button_image" value="acknowledge_action.png" />
                      <c:set var="ack_button_image" value="acknowledge_disabled.png" />
                      <c:set var="ack_button_disabled" value="disabled" />
                      <c:set var="action_button_disable" value="" />
                  </c:if>
              <html:form action="/EdoDossierRoute">
               <html:hidden property="methodToCall" value="routeSupplementalDocument"/>
                <html:hidden property="cid" value="${selectedCandidate.candidateID}"/>
                <html:hidden property="candidateUsername" value="${selectedCandidate.candidateUsername}"/>
                <html:hidden property="dossierId" value="${selectedCandidate.candidateDossierID}"/>
                <html:hidden property="dossierType" value="${selectedCandidate.dossierTypeName}"/>
              <tr>
              		<td align="right"><input ${ack_button_disabled} type="image" name="Acknowledge" src="images/${ack_button_image}" style="border: none; vertical-align: middle" onclick="this.form.methodToCall.value='approveSupplemental';"></td>
              		<td align="left">Choosing Acknowledge means that upon review, no further action will be taken. </td>
              </tr>
              <tr>
              		<td></td>
              </tr>
                <%-- <c:if test : the below button should be displayed only after the reviewer changes his vote record or upload a review letter --%>
               <tr>
              		<td align="right"><input ${action_button_disable} type="image" name="AcknowledgeWithAction" id="AcknowledgeWithAction" src="images/${action_button_image}" style="border: none; vertical-align: middle" onclick="this.form.methodToCall.value='approveSupplementalWithAction';" /></td>
              		<td align="left">Choosing Acknowledge with Action means that upon review, a formal response (e.g., letter and possible re-vote) will be uploaded. </td>
              </tr>
                  
              
               </html:form>
              
              </c:if>
               <%-- close buttons and reconsider buttons havr to be displaye to the super users --%>
                <c:if test="${canUpdateDossierStatus}">
             
               		<html:form action="/EdoCandidateSelect">
               		  <html:hidden property="methodToCall" value=""/>
	                        <html:hidden property="cid" value="${selectedCandidate.candidateID}"/>
	                        <html:hidden property="candidateUsername" value="${selectedCandidate.candidateUsername}"/>
	                        <html:hidden property="dossier" value="${selectedCandidate.candidateDossierID}"/>
               			  <tr>
			                <td align="right" bgcolor="#eeeeee">Update Dossier Status</td>
			                <td align="left">
			                	<input type="image" alt="Close Dossier"
			                                       name="closeDossier" id="closeDossier" src="images/close.png"
			                                       onclick="this.form.methodToCall.value='closeDossier'"
			                                       style="border: none; vertical-align: middle;" />
			                    <img src="images/ajax-loader.gif" id="ajaxSubmit" style="display: none;">
			               
			                 	<input type="image" alt="Reconsider Dossier"
			                                       name="reconsiderDossier" id="reconsiderDossier" src="images/reconsider.png"
			                                        onclick="this.form.methodToCall.value='reconsiderDossier'"
			                                       style="border: none; vertical-align: middle;" />
			                    <img src="images/ajax-loader.gif" id="ajaxSubmit" style="display: none;">
			                                       
			                 </td>
			               </tr>
	                 </html:form>
            	</c:if>
        </tbody>
    </table>
    
</div>
 
