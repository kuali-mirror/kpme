<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<c:set var="Form" value="${EdoBackDoorForm}" scope="request" />

<edo:edoPageLayout>
	<edo:edoHeader></edo:edoHeader>

	<div style="width: 100%;">

		<edo:edoTreeNav />
		<div class="content ui-corner-all">
			<html:form action="/backdoorlogin" method="post"
				enctype="multipart/form-data">
                <c:if test="${!empty ErrorPropertyList}">
                    <div class="error">
                        <kul:errors keyMatch="error" errorTitle="Errors:" />
                        <br />
                    </div>
                </c:if>
                <html:hidden property="methodToCall" value="" />
				<c:choose>
					<c:when test="${EdoForm.useGenAdminScreen}">
						<div class="portlet">
							<br /> <br />
							<div class="header">Backdoor Person</div>
							<div class="body">
								<html:form action="/backdoorlogin" method="post"
									style="margin:0; display:inline">
									<html:text property="principalName" size="20" />
									<html:submit property="methodToCall.changeBackdoor"
										value="Submit" />
									<html:submit property="methodToCall.clearBackdoor"
										value="Remove Backdoor User" />
								</html:form>
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