<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp" %>
<c:set var="Form" value="${EdoChangeTargetPersonForm}" scope="request"/>
<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>

    <div style="width: 100%;">
        <edo:edoTreeNav/>
        <div class="content ui-corner-all">
            <html:form action="/changeTargetPerson" method="post"
                       enctype="multipart/form-data">
                <html:hidden property="methodToCall" value=""/>
                <c:if test="${!empty ErrorPropertyList}">
                    <div class="error">
                        <kul:errors keyMatch="error" errorTitle="Errors:"/>
                        <br/>
                    </div>
                </c:if>
                <c:choose>
                    <c:when test="${EdoForm.useGenAdminScreen}">
                        <div class="portlet">
                            <br/> <br/>

                            <div class="header">Change Target User</div>
                            <div class="body">

                                <html:text property="principalName" size="20"/>
                                <html:submit property="methodToCall.changeTargetPerson"
                                             value="Submit"/>
                                <html:submit property="methodToCall.clearTargetPerson"
                                             value="Remove Target User"/>

                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        You don't have enough permissions to view this screen.
                    </c:otherwise>
                </c:choose>


            </html:form>
        </div>
    </div>
</edo:edoPageLayout>