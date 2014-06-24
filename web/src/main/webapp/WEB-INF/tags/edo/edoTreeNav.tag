<%@ tag import="org.kuali.kpme.edo.util.EdoConstants" %>
<%@include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<c:set var="Form" value="${EdoForm}" scope="request" />
<c:set var="checklistVoteRecordID" value="<%= EdoConstants.CHECKLIST_ITEM_VOTE_RECORD_ID %>" />
<c:set var="checklistReviewLettersID" value="<%= EdoConstants.CHECKLIST_ITEM_REVIEW_LETTERS_ID %>" />
<c:set var="checklistExternalLettersID" value="<%= EdoConstants.CHECKLIST_ITEM_EXTERNAL_LETTERS_ID %>" />
<c:set var="checklistSolicitedLettersID" value="<%= EdoConstants.CHECKLIST_ITEM_SOLICITED_LETTERS_ID %>" />
<c:set var="checklistSolicitedLetters1ID" value="<%= EdoConstants.CHECKLIST_ITEM_SOLICITED_LETTERS_1_ID %>" />
<c:set var="checklistSolicitedLetters2ID" value="<%= EdoConstants.CHECKLIST_ITEM_SOLICITED_LETTERS_2_ID %>" />
<c:set var="checklistSolicitedLetters3ID" value="<%= EdoConstants.CHECKLIST_ITEM_SOLICITED_LETTERS_3_ID %>" />
<c:set var="checklistSecondUnitID" value="<%= EdoConstants.CHECKLIST_ITEM_SECOND_UNIT_LETTERS_ID %>" />

<c:set var="checklistVoteRecordLabel" value="<%= EdoConstants.EDO_CHECKLIST_VOTE_RECORD_LABEL %>" />
<c:set var="checklistInternalLettersLabel" value="<%= EdoConstants.EDO_CHECKLIST_INTERNAL_LETTERS_LABEL %>" />
<c:set var="checklistExternalLettersLabel" value="<%= EdoConstants.EDO_CHECKLIST_EXTERNAL_LETTERS_LABEL %>" />
<c:set var="checklistSecondUnitLabel" value="<%= EdoConstants.EDO_CHECKLIST_SECOND_UNIT_LABEL %>" />
<c:set var="checklistSolicitedMainLabel" value="<%= EdoConstants.EDO_CHECKLIST_SOLICTED_MAIN_LABEL %>" />
<c:set var="checklistSolicitedTeachingLabel" value="<%= EdoConstants.EDO_CHECKLIST_SOLICITED_TEACHING_LABEL %>" />
<c:set var="checklistSolicitedReseachLabel" value="<%= EdoConstants.EDO_CHECKLIST_SOLICITED_RESEARCH_LABEL %>" />
<c:set var="checklistSolicitedServiceLabel" value="<%= EdoConstants.EDO_CHECKLIST_SOLICITED_SERVICE_LABEL %>" />

<c:set var="dossierStatusOpen" value="<%= EdoConstants.DOSSIER_STATUS.OPEN %>" />
<c:set var="dossierStatusPending" value="<%= EdoConstants.DOSSIER_STATUS.PENDING %>" />
<c:set var="dossierStatusSubmitted" value="<%= EdoConstants.DOSSIER_STATUS.SUBMITTED %>" />
<c:set var="dossierStatusClosed" value="<%= EdoConstants.DOSSIER_STATUS.CLOSED %>" />
<c:set var="dossierStatusReconsider" value="<%= EdoConstants.DOSSIER_STATUS.RECONSIDERATION %>" />
<c:set var="suppSectionName" value="<%= EdoConstants.EDO_SUPPLEMENTAL_ITEM_SECTION_NAME %>" />
<c:set var="suppCategoryName" value="<%= EdoConstants.EDO_SUPPLEMENTAL_ITEM_CATEGORY_NAME %>" />
<c:set var="reconsiderSectionName" value="<%= EdoConstants.EDO_RECONSIDERATION_ITEM_SECTION_NAME %>" />
<c:set var="reconsiderCategoryName" value="<%= EdoConstants.EDO_RECONSIDERATION_ITEM_CATEGORY_NAME %>" />

<jsp:useBean id="tagSupport" class="org.kuali.kpme.edo.util.TagSupport" />

<edo:jq_tree_init div_id="treenav" initially_select="${param.nid}" />

<div id="navdiv" class="ui-corner-all treenav">
	<div id="treenav">
		<ul>

            <c:if test="${EdoForm.useAssignDelegateFunc && EdoForm.hasCandidateRole}">
                <li id="cand_0_1">
                    <a href="EdoAssignCandidateDelegate.do?tabId=dossier">Assign a Candidate Delegate</a>
                </li>
            </c:if>

            <c:if test="${EdoForm.useAssignDelegateFunc && EdoForm.hasChairRole}">
                <li id="cand_0_5">
                    <a href="EdoAssignChairDelegate.do?tabId=dossier">Assign a Chair Delegate</a>
                </li>
            </c:if>

            <c:if test="${EdoForm.useAssignGuestFunc}">
                <li id="cand_0_2"><a href="EdoAssignCandidateGuest.do?tabId=dossier">Assign a Guest</a> </li>
            </c:if>

			<c:if test="${EdoForm.hasSuperUserRole}">
				<c:if test='${tagSupport.stagingOrDevOrSnd}'>
					<li id="cand_0_3">
                        <a href="backdoorlogin.do?tabId=gadmin">Back Door User</a>
                    </li>
				</c:if>

				<li id="cand_0_4">
                    <a href="changeTargetPerson.do?tabId=gadmin">Change Target User</a>
                </li>
                
                <li id="cand_0_5">
                    <a href="EdoDisplaySubmitButton.do?tabId=gadmin">Maintain Submit Dossier</a>
                </li>
			</c:if>

			<c:if test="${EdoForm.useGenAdminScreen && tabId == 'gadmin'}">
				<li id="gadm_0_0"><a href="#">Admin Tasks</a>
					<ul>
						<%-- <li id="gadm_0_2">
                            <c:url value="EdoCandidateAdd.do?tabId=gadmin" var="c_add" />
                            <a href="${c_add}">Add a Candidate</a>
                        </li> --%>
                        <c:if test="${EdoForm.canManageGroups}">
                            <li id="gadm_0_6">
                                <c:url value="EdoAdminGroups.do?tabId=gadmin" var="grps" />
                                <a href="${grps}">Manage Groups</a>
                            </li>
                        </c:if>
                        <li id="gadm_0_7">
                            <c:url value="EdoAdminGroupMembers.do?tabId=gadmin" var="grp_mbrs" />
                            <a href="${grp_mbrs}">Manage Group Members</a>
                        </li>
                        <li id="gadm_0_8">
                            <c:url value="EdoPTReport.do?tabId=gadmin" var="rpt_pt" />
                            <a href="${rpt_pt}">P&T Summary Report</a>
                        </li>
                    </ul>
                </li>
            </c:if>

            <c:if test="${EdoForm.useReviewerScreen}">
                <li id="rev_0_0"><a href="#">Review Tasks</a>
                    <ul>
                        <li id="rev_0_1">
                            <c:url value="EdoCandidateList.do?tabId=reviews" var="c_list" />
                            <a href="${c_list}">View Current Candidates</a>
                        </li>
                    </ul>
                </li>
            </c:if>

            <c:if test="${isCandidateSelected}">
                <li id="cklst_0_0">
                    <a href="EdoCandidateSelect.do?nid=cklst_0&cid=${selectedCandidate.candidateID}&dossier=${selectedCandidate.candidateDossierID}">Dossier
                        (${selectedCandidate.getCandidateLastname()},
                        ${selectedCandidate.getCandidateFirstname()})</a>
                    <ul>
                        <c:if test="${EdoForm.useGenAdminScreen || EdoForm.useReviewerScreen}">
                            <c:if test="${(selectedCandidate.getDossierStatus().equals(dossierStatusSubmitted) || selectedCandidate.getDossierStatus().equals(dossierStatusClosed) || selectedCandidate.getDossierStatus().equals(dossierStatusReconsider))}">

                                <c:if test="${EdoForm.hasViewVoteRecordCurrentDossier}">
                                    <li id="cklst_1_${checklistVoteRecordID}">
                                        <a href="EdoVoteRecord.do?">${checklistVoteRecordLabel}</a></li>
                                </c:if>
                                <c:if test="${EdoForm.hasViewReviewLetterCurrentDossier}">
                                    <li id="cklst_2_${checklistReviewLettersID}">
                                        <a href="EdoReviewLetter.do?">${checklistInternalLettersLabel}</a></li>
                                </c:if>
                                <c:if test="${EdoForm.hasViewReviewLetterCurrentDossier}">
                                    <li id="cklst_9_${checklistSecondUnitID}">
                                        <a href="EdoSecondUnit.do?">${checklistSecondUnitLabel}</a></li>
                                </c:if>

                                <c:if test="${EdoForm.hasViewReviewLetterCurrentDossier || EdoForm.hasUploadExternalLetterByDept}">
                                    <li id="cklst_3_${checklistExternalLettersID}">
                                        <a href="EdoExternalLetter.do?">${checklistExternalLettersLabel}</a></li>
                                </c:if>
                                <c:if test="${EdoForm.hasViewReviewLetterCurrentDossier || EdoForm.hasUploadExternalLetterByDept}">
                                    <li id="cklst_5_${checklistSolicitedLettersID}"><a href="#">${checklistSolicitedMainLabel}</a>
                                    <ul>
                                        <li id="cklst_6_${checklistSolicitedLetters1ID}">
                                            <a href="EdoSolicitedLetter.do?">
                                                ${checklistSolicitedTeachingLabel}</a></li>
                                        <li id="cklst_7_${checklistSolicitedLetters2ID}">
                                            <a href="EdoSolicitedLetter.do?">
                                                ${checklistSolicitedReseachLabel}</a></li>
                                        <li id="cklst_8_${checklistSolicitedLetters3ID}">
                                            <a href="EdoSolicitedLetter.do?">
                                                ${checklistSolicitedServiceLabel}</a></li>
                                    </ul>
                                </c:if>

                            </c:if>
                        </c:if>
                        <c:forEach var="ck_section" items="${checklisthash}">
                            <c:set var="ck_section_name"
                                value="${ck_section.key.substring(2)}"></c:set>

                            <c:if test="${!(ck_section_name.equals(suppSectionName) || ck_section_name.equals(reconsiderSectionName)) || (ck_section_name.equals(suppSectionName) && (selectedCandidate.getDossierStatus().equals(dossierStatusSubmitted) || selectedCandidate.getDossierStatus().equals(dossierStatusClosed))) || ( ck_section_name.equals(reconsiderSectionName) && selectedCandidate.getDossierStatus().equals(dossierStatusReconsider))}">

                                <li id="${ck_section_name.replaceAll(' |/', '-')}_${ck_section.value[0].getChecklistSectionOrdinal()}_${ck_section.value[0].getChecklistSectionID()}"><a
                                    href="EdoSectionSummary.do?">${ck_section_name}</a>

                                    <ul>
                                        <c:forEach items="${ck_section.value}" var="ck_item">
                                            <c:set var="cli_desc_title"
                                                value="${ck_item.getChecklistItemName()}" />
                                            <c:set var="cli_item_label"
                                                value="${ck_item.getChecklistItemName()}" />
                                            <c:if test="${ck_item.getChecklistItemName().length() > 24}">
                                                <c:set var="cli_item_label"
                                                    value="${ck_item.getChecklistItemName().substring(0,24)}..." />
                                            </c:if>
                                            <li
                                                id="${ck_section_name.replaceAll(' |/', '-')}_${ck_item.getChecklistItemOrdinal()}_${ck_item.getChecklistItemID()}">
                                                <a href="EdoChecklistItem.do?" title="${cli_desc_title}">${cli_item_label}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </c:if>

                        </c:forEach>
                    </ul></li>
            </c:if>
        </ul>
    </div>
</div>

<script>
    $("#${param.nid}").addClass("ui-state-active");
</script>
