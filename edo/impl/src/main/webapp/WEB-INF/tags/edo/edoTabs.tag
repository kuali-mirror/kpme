<%@include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<c:set var="Form" value="${EdoForm}"
	scope="request" />

<div id="tab-section">

<%-- <c:if test="${EdoForm.useGenAdminScreen || EdoForm.useReviewerScreen || EdoForm.useCandidateScreen || EdoForm.useHelpScreen}"> --%>
<c:if test="${EdoForm.useHelpScreen}">
<li id="help" class="ui-state-default ui-corner-top"><a href="EdoHelpGuide.do?tabId=help&nid=cklst_0_0" target="helpwin">Help</a></li>
</c:if>


<%-- <c:if test="${isSystemAdmin}"> --%>

<c:if test="${EdoForm.useGenAdminScreen}">
    <li id="gadmin" class="ui-state-default ui-corner-top"><a href="EdoGenAdminHome.do?tabId=gadmin&nid=gadmin_0_0">General Admin</a></li>
</c:if>

<%-- <c:if test="${isReviewer}"> --%>
<c:if test="${EdoForm.useReviewerScreen}">
    <li id="reviews" class="ui-state-default ui-corner-top"><a href="EdoCandidateList.do?tabId=reviews&nid=rev_0_1">Reviews</a></li>
</c:if>

<%-- <c:if test="${isCandidate}"> --%>
<c:if test="${EdoForm.useCandidateScreen}">
    <li id="dossier" class="ui-state-default ui-corner-top"><a href="EdoDossierHome.do?tabId=dossier&nid=cklst_0_0">Dossiers</a></li>
</c:if>

</div>

<script>
    var tabElem = document.getElementById("${tabId}");
    if (tabElem) {
        tabElem.className = "ui-state-active ui-tab-active ui-corner-top";
    }
</script>
