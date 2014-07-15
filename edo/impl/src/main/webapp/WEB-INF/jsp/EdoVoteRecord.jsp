<%@ page import="org.kuali.kpme.edo.util.EdoConstants" %>
<%@ page import="org.kuali.kpme.edo.util.EdoContext" %>
<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp" %>
<c:set var="Form" value="${EdoVoteRecordForm}" scope="request"/>
<c:set var="canViewVoteRecords" value="<%= EdoContext.canViewVoteRecordsForSelectedDossier() %>" />
<c:set var="canVote" value="<%= EdoContext.canVoteForSelectedDossier() %>" />
<c:set var="canRoute"  value="<%= EdoContext.canRouteSelectedDossier() %>" />

<c:set var="voteTypeMultiple" value="<%= EdoConstants.VOTE_TYPE_MULTIPLE %>" />
<c:set var="voteTypeSingle" value="<%= EdoConstants.VOTE_TYPE_SINGLE %>" />
<c:set var="dossierTypeTenure" value="<%= EdoConstants.VoteType.VOTE_TYPE_TENURE %>" />
<c:set var="dossierTypePromotion" value="<%= EdoConstants.VoteType.VOTE_TYPE_PROMOTION %>" />
<c:set var="dossierTypeTenurePromotion" value="<%= EdoConstants.VoteType.VOTE_TYPE_TENURE_PROMOTION %>" />

<edo:edoPageLayout>
    <edo:edoHeader />

    <div style="width: 100%;">

        <edo:edoTreeNav/>
        <c:if test="${canViewVoteRecords}" >
            <div class="content">
                <div style="font-weight: bold; font-size: 1.2em; padding: 10px; margin: 10px 0 0 3px; background-color: #FADDDE; color: #DE3F45;">
                    As a reviewer, you may wish to save and/or print for 'offline' review materials a candidate submitted.<br/>
                    However, you should not save, print, or share any external or evaluative letters or votes.
                </div>
                <br>

                <table>
                    <tr>
                        <td><b>Candidate Name:</b> ${Form.selectedCandidate.candidateLastname}, ${Form.selectedCandidate.candidateFirstname}</td>
                    </tr>
                    <tr>
                        <td><b>Area of Excellence:</b> ${Form.selectedCandidate.aoeDescription}</td>
                    </tr>
                    <tr>
                        <td><b>Rank Sought:</b> ${Form.selectedCandidate.rankSought}</td>
                    </tr>
                </table>
                <br />
                <div id="vote-record">

                    <table>
                        <thead>
                        <tr>
                            <th rowspan="2">Review Layer</th>
                            <th rowspan="2">Round</th>
                            <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenure) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                            <th colspan="4">Tenure</th>
                            </c:if>
                            <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypePromotion) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                            <th colspan="4">Promotion</th>
                            </c:if>
                            <th rowspan="2">Area of Excellence</th>
                        </tr>
                        <tr>
                            <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenure) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                            <th>Yes</th>
                            <th>No</th>
                            <th>Abstain</th>
                            <th>Absent</th>
                            </c:if>

                            <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypePromotion) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                            <th>Yes</th>
                            <th>No</th>
                            <th>Abstain</th>
                            <th>Absent</th>
                            </c:if>
                        </tr>
                        </thead>
                        <tbody>
                            <c:if test="${Form.voteRecords == null or Form.voteRecords.size() < 1}" >
                                <tr>
                                    <td colspan="14">No vote records recorded.</td>
                                </tr>
                            </c:if>
                            <c:forEach var="voteRecord" items="${Form.voteRecords}">
                                <tr>
                                    <td class="role">${voteRecord.reviewLayerDefinition.description}</td>
                                    <td>${voteRecord.voteRound}.${voteRecord.voteSubRound}</td>

                                <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenure) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                    <td>${voteRecord.yesCountTenure}</td>
                                    <td>${voteRecord.noCountTenure}</td>
                                    <c:if test="${voteRecord.reviewLayerDefinition.voteType == voteTypeMultiple}" >
                                        <td>${voteRecord.abstainCountTenure}</td>
                                        <td>${voteRecord.absentCountTenure}</td>
                                    </c:if>
                                    <c:if test="${voteRecord.reviewLayerDefinition.voteType == voteTypeSingle}">
                                        <td class="gray"></td>
                                        <td class="gray"></td>
                                    </c:if>
                                </c:if>

                                <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypePromotion) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                    <td>${voteRecord.yesCountPromotion}</td>
                                    <td>${voteRecord.noCountPromotion}</td>
                                    <c:if test="${voteRecord.reviewLayerDefinition.voteType == voteTypeMultiple}" >
                                        <td>${voteRecord.abstainCountPromotion}</td>
                                        <td>${voteRecord.absentCountPromotion}</td>
                                    </c:if>
                                    <c:if test="${voteRecord.reviewLayerDefinition.voteType == voteTypeSingle}">
                                        <td class="gray"></td>
                                        <td class="gray"></td>
                                    </c:if>
                                </c:if>

                                    <td class="area-of-excellence">${voteRecord.aoeDescription}</td>

                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <br />
                    <br />
                    <c:if test="${!empty ErrorPropertyList}">
                        <div class="error">
                            <kul:errors keyMatch="error" errorTitle="Errors:" />
                            <br />
                        </div>
                    </c:if>

                    <c:if test="${canVote && !hasReconsiderStatus}">

                        <html:form action="/EdoVoteRecord" method="post"
                                   enctype="multipart/form-data">
                            <html:hidden property="methodToCall" value="saveVoteRecord" />
                            <html:hidden property="reviewLayerDefinitionId" value="${Form.currentReviewLayerDefinition.reviewLayerDefinitionId}"/>
                            <html:hidden property="voteRecordId" value="${Form.currentVoteRecord.voteRecordId}"/>
                            <p><b>Record vote record for current review layer.</b></p>
                            <table>
                                <thead>
                                <tr>
                                    <th rowspan="2">Review Layer</th>
                                    <th rowspan="2">Round</th>
                                    <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenure) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                    <th colspan="4">Tenure</th>
                                    </c:if>
                                    <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypePromotion) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                    <th colspan="4">Promotion</th>
                                    </c:if>
                                    <th rowspan="2">Area of Excellence</th>
                                </tr>
                                <tr>
                                <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenure) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                    <th>Yes</th>
                                    <th>No</th>
                                    <th>Abstain</th>
                                    <th>Absent</th>
                                </c:if>
                                <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypePromotion) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                    <th>Yes</th>
                                    <th>No</th>
                                    <th>Abstain</th>
                                    <th>Absent</th>
                                </c:if>
                                </tr>

                                </thead>
                                <tbody>
                                <c:if test="${Form.currentReviewLayerDefinition.voteType == voteTypeMultiple}" >
                                    <tr>
                                        <td class="role">${Form.currentReviewLayerDefinition.description}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${empty Form.currentVoteRecord.voteRound}">
                                                    <input type="hidden" name="voteRoundString" value="1.0">
                                                    1.0
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="hidden" name="voteRoundString" value="${Form.currentVoteRecord.voteRound}.${Form.currentVoteRecord.voteSubRound}">
                                                    ${Form.currentVoteRecord.voteRound}.${Form.currentVoteRecord.voteSubRound}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>

                                    <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenure) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                        <td><input type="text" name="yesCountTenure" size="5" value="${Form.currentVoteRecord.yesCountTenure}"></td>
                                        <td><input type="text" name="noCountTenure" size="5" value="${Form.currentVoteRecord.noCountTenure}"></td>
                                        <td><input type="text" name="abstainCountTenure" size="5" value="${Form.currentVoteRecord.abstainCountTenure}"></td>
                                        <td><input type="text" name="absentCountTenure" size="5" value="${Form.currentVoteRecord.absentCountTenure}"></td>
                                    </c:if>
                                    <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypePromotion) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                        <td><input type="text" name="yesCountPromotion" size="5" value="${Form.currentVoteRecord.yesCountPromotion}"></td>
                                        <td><input type="text" name="noCountPromotion" size="5" value="${Form.currentVoteRecord.noCountPromotion}"></td>
                                        <td><input type="text" name="abstainCountPromotion" size="5" value="${Form.currentVoteRecord.abstainCountPromotion}"></td>
                                        <td><input type="text" name="absentCountPromotion" size="5" value="${Form.currentVoteRecord.absentCountPromotion}"></td>
                                    </c:if>
                                        <td>
                                            <select name="aoeCode">
                                                <option value="" selected="selected">-- select an area of excellence --</option>
                                                <c:forEach var="aoe" items="${Form.areaOfExcellenceChoices}">
                                                    <c:choose>
                                                        <c:when test="${aoe.key == Form.currentVoteRecord.aoeCode}">
                                                            <option value="${aoe.key}" selected="true">${aoe.value}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${aoe.key}">${aoe.value}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${Form.currentReviewLayerDefinition.voteType == voteTypeSingle}">
                                    <tr>
                                        <td class="role">${Form.currentReviewLayerDefinition.description}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${empty Form.currentVoteRecord.voteRound}">
                                                    <input type="hidden" name="voteRoundString" value="1.0">
                                                    1.0
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="hidden" name="voteRoundString" value="${Form.currentVoteRecord.voteRound}.${Form.currentVoteRecord.voteSubRound}">
                                                    ${Form.currentVoteRecord.voteRound}.${Form.currentVoteRecord.voteSubRound}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>

                                    <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenure) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                        <td><input type="text" name="yesCountTenure" size="5" value="${Form.currentVoteRecord.yesCountTenure}"></td>
                                        <td><input type="text" name="noCountTenure" size="5" value="${Form.currentVoteRecord.noCountTenure}"></td>
                                        <td class="gray"></td>
                                        <td class="gray"></td>
                                    </c:if>
                                    <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypePromotion) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                        <td><input type="text" name="yesCountPromotion" size="5" value="${Form.currentVoteRecord.yesCountPromotion}"></td>
                                        <td><input type="text" name="noCountPromotion" size="5" value="${Form.currentVoteRecord.noCountPromotion}"></td>
                                        <td class="gray"></td>
                                        <td class="gray"></td>
                                    </c:if>
                                        <td>
                                            <select name="aoeCode">
                                                <option value="" selected="selected">-- select an area of excellence --</option>
                                                <c:forEach var="aoe" items="${Form.areaOfExcellenceChoices}">
                                                    <c:choose>
                                                        <c:when test="${aoe.key == Form.currentVoteRecord.aoeCode}">
                                                            <option value="${aoe.key}" selected="true">${aoe.value}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${aoe.key}">${aoe.value}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                            <br />
                            <div style="float:left; padding-right: 5px;">
                                <input type="image" alt="Save Vote Record"
                                   name="saveVoteRecord" id="saveVoteRecord"
                                   src="images/buttonsmall_save.gif"
                                   onclick="this.form.submit"
                                   style="border: none; vertical-align: middle;" />
                               	<img src="images/ajax-loader.gif" id="ajaxSubmit" style="display: none;">
                               
                            </div>
                        </html:form>
                        <%--
                        <c:if test="${canRoute && dossierReadyForRoute}">
                            <div style="float:left;">
                                <html:form action="/EdoDossierRoute">
                                    <html:hidden property="methodToCall" value="routeDocument"/>
                                    <html:hidden property="cid" value="${selectedCandidate.candidateID}"/>
                                    <html:hidden property="candidateUsername" value="${selectedCandidate.candidateUsername}"/>
                                    <html:hidden property="dossierId" value="${selectedCandidate.candidateDossierID}"/>
                                    <html:hidden property="dossierType" value="${selectedCandidate.getDossierTypeName()}"/>

                                    <input type="image" alt="Route"
                                       name="route"
                                       src="images/buttonsmall_route.gif"
                                       onclick="this.form.submit"
                                       style="border: none; vertical-align: middle;" />
                                </html:form>
                            </div>
                        </c:if> --%>
                    </c:if>

                    <c:if test="${hasSupplementalWaiting || (canVote && hasReconsiderStatus)}">

                        <html:form action="/EdoVoteRecord" method="post"
                                   enctype="multipart/form-data">
                            <html:hidden property="methodToCall" value="saveVoteRecord" />
                            <html:hidden property="reviewLayerDefinitionId" value="${Form.principalReviewLayerDefinition.reviewLayerDefinitionId}"/>
                            <c:if test="${hasSupplementalWaiting}">
                                <p><b>Record vote for supplemental material round.</b></p>
                            </c:if>
                            <c:if test="${hasReconsiderStatus}">
                                <p><b>Record vote for reconsideration round.</b></p>
                            </c:if>
                            <table>
                                <thead>
                                <tr>
                                    <th rowspan="2">Review Layer</th>
                                    <th rowspan="2">Round</th>
                                    <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenure) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                        <th colspan="4">Tenure</th>
                                    </c:if>
                                    <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypePromotion) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                        <th colspan="4">Promotion</th>
                                    </c:if>
                                    <th rowspan="2">Area of Excellence</th>
                                </tr>
                                <tr>
                                    <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenure) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                        <th>Yes</th>
                                        <th>No</th>
                                        <th>Abstain</th>
                                        <th>Absent</th>
                                    </c:if>
                                    <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypePromotion) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                        <th>Yes</th>
                                        <th>No</th>
                                        <th>Abstain</th>
                                        <th>Absent</th>
                                    </c:if>
                                </tr>

                                </thead>
                                <tbody>
                                <c:if test="${Form.principalReviewLayerDefinition.voteType == voteTypeMultiple}" >
                                    <tr>
                                        <td class="role">${Form.principalReviewLayerDefinition.description}</td>
                                        <td>
                                            <c:if test="${hasReconsiderStatus}">
                                                <input type="hidden" name="voteRoundString" value="2.0">
                                                2.0
                                            </c:if>
                                            <c:if test="${hasSupplementalWaiting}">
                                                <input type="hidden" name="voteRoundString" value="1.${Form.currentVoteSubRound + 1}">
                                                    1.${Form.currentVoteSubRound + 1}
                                            </c:if>
                                        </td>

                                        <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenure) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                            <td><input type="text" name="yesCountTenure" size="5" value=""></td>
                                            <td><input type="text" name="noCountTenure" size="5" value=""></td>
                                            <td><input type="text" name="abstainCountTenure" size="5" value=""></td>
                                            <td><input type="text" name="absentCountTenure" size="5" value=""></td>
                                        </c:if>
                                        <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypePromotion) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                            <td><input type="text" name="yesCountPromotion" size="5" value=""></td>
                                            <td><input type="text" name="noCountPromotion" size="5" value=""></td>
                                            <td><input type="text" name="abstainCountPromotion" size="5" value=""></td>
                                            <td><input type="text" name="absentCountPromotion" size="5" value=""></td>
                                        </c:if>
                                        <td>
                                            <select name="aoeCode">
                                                <option value="" selected="selected">-- select an area of excellence --</option>
                                                <c:forEach var="aoe" items="${Form.areaOfExcellenceChoices}">
                                                    <option value="${aoe.key}">${aoe.value}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${Form.principalReviewLayerDefinition.voteType == voteTypeSingle}">
                                    <tr>
                                        <td class="role">${Form.principalReviewLayerDefinition.description}</td>
                                        <td>
                                            <c:if test="${hasReconsiderStatus}">
                                                <input type="hidden" name="voteRoundString" value="2.0">
                                                2.0
                                            </c:if>
                                            <c:if test="${hasSupplementalWaiting}">
                                                <input type="hidden" name="voteRoundString" value="1.${Form.currentVoteSubRound + 1}">
                                                1.${Form.currentVoteSubRound + 1}
                                            </c:if>
                                        </td>

                                        <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenure) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                            <td><input type="text" name="yesCountTenure" size="5" value=""></td>
                                            <td><input type="text" name="noCountTenure" size="5" value=""></td>
                                            <td class="gray"></td>
                                            <td class="gray"></td>
                                        </c:if>
                                        <c:if test="${Form.selectedCandidate.getDossierTypeName().equals(dossierTypePromotion) || Form.selectedCandidate.getDossierTypeName().equals(dossierTypeTenurePromotion)}">
                                            <td><input type="text" name="yesCountPromotion" size="5" value=""></td>
                                            <td><input type="text" name="noCountPromotion" size="5" value=""></td>
                                            <td class="gray"></td>
                                            <td class="gray"></td>
                                        </c:if>
                                        <td>
                                            <select name="aoeCode">
                                                <option value="" selected="selected">-- select an area of excellence --</option>
                                                <c:forEach var="aoe" items="${Form.areaOfExcellenceChoices}">
                                                    <option value="${aoe.key}">${aoe.value}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                            <br />
                            <div style="float:left; padding-right: 5px;">
                                <input type="image" alt="Save Vote Record"
                                       name="saveVoteRecord" id="saveVoteRecord"
                                       src="images/buttonsmall_save.gif"
                                       onclick="this.form.submit"
                                       style="border: none; vertical-align: middle;" />
                                <img src="images/ajax-loader.gif" id="ajaxSubmit" style="display: none;">
                                
                            </div>
                        </html:form>
                    </c:if>
                </div>
            </div>
            <br style="clear: both;"/>
        </c:if>
    </div>
    <edo:edoFooter/>
</edo:edoPageLayout>
