<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp" %>

<c:set var="Form" value="${TkForm}" scope="request"/>
<c:set var="KualiForm" value="${TkForm}" scope="request"/>
<c:set var="TkForm" value="${TkForm}" scope="request"/>



<edo:edoHeader>
	
	    <div style="width: 100%;">
	
	        <edo:edoTreeNav />
	        <div class="content">
	            <c:if test="${!empty ErrorPropertyList}">
	                <div class="error">
	                    <kul:errors keyMatch="error" errorTitle="Errors:" />
	                    <br />
	                </div>
	            </c:if>
	
	            <h3>My Dossiers</h3>
	            <br>
	
	            <edo:jq_dossiertable_init addLink = "" selectLink="EdoCandidateSelect.do?nid=cklst_0_0"/>
	            <table id="dossier_list" />
	
	        </div>
	        <br style="clear: both;" />
	    </div>

</edo:edoHeader>


