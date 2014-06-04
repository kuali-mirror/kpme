<%@ tag import="org.kuali.kpme.edo.util.EdoConstants" %>
<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<%@ attribute name="itemName" required="true"%>

<c:set var="Form" value="${EdoForm}" scope="request" />
<c:set var="dossierStatusOpen" value="<%= EdoConstants.DOSSIER_STATUS.OPEN %>" />
<c:set var="dossierStatusPending" value="<%= EdoConstants.DOSSIER_STATUS.PENDING %>" />
<c:set var="dossierStatusSubmitted" value="<%= EdoConstants.DOSSIER_STATUS.SUBMITTED %>" />
<c:set var="dossierStatusReconsider" value="<%= EdoConstants.DOSSIER_STATUS.RECONSIDERATION %>" />
<c:set var="suppCategoryName" value="<%= EdoConstants.EDO_SUPPLEMENTAL_ITEM_CATEGORY_NAME %>" />
<c:set var="reconsiderCategoryName" value="<%= EdoConstants.EDO_RECONSIDERATION_ITEM_CATEGORY_NAME %>" />


<!-- only display this form content if the dossier status is OPEN, PENDING -->
<c:if test="${EdoForm.useEditDossierFunc && ( selectedCandidate.getDossierStatus().equals(dossierStatusPending) || selectedCandidate.getDossierStatus().equals(dossierStatusOpen)  || (selectedCandidate.getDossierStatus().equals(dossierStatusSubmitted) && itemName.equals(suppCategoryName)) || (selectedCandidate.getDossierStatus().equals(dossierStatusReconsider) && itemName.equals(reconsiderCategoryName) && EdoForm.canUploadReconsiderItems))}">

    <div id="dropzone" style="font-size: 16px; text-align: center; padding-top: 10px; background-color: #cccccc;">
    <blockquote>Internet Explorer users should use this form instead of the drag and drop area at the bottom.</blockquote>
    <edo:edoManualFileUpload actionPath="EdoChecklistItem.do" />

    Drag and drop files here to add to this category.
    <div id="filelist" class="message"></div>
</div>
<div id="uploadresult" style="display: none;"></div>

<script type="text/javascript" src="js/jquery/plugins/filedrop/jquery.filedrop.js"></script>
<script type="text/javascript" src="js/edoFiledrop.js"></script>

</c:if>
