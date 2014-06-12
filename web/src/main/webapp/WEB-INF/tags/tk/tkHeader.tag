<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<%@ attribute name="tabId" required="false"%>
<%@ attribute name="nocache" required="false"%>
<jsp:useBean id="tagSupport" class="org.kuali.kpme.tklm.common.TagSupport" />
<jsp:useBean id="form" class="org.kuali.kpme.core.web.KPMEForm" />

<c:if test="${nocache == 'true'}">
    <%
      response.setHeader("Cache-Control","no-cache");
      response.setHeader("Pragma","no-cache");
      response.setDateHeader ("Expires", -1);
    %>
</c:if>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Kuali HR</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<tk:tkInclude />
<tk:tkJsInclude />
<c:if test="${nocache == 'true'}">
    <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
    <META HTTP-EQUIV="Expires" CONTENT="-1">
</c:if>
</head>
<body>
	<c:if test="${!empty UserSession.loggedInUserPrincipalName}">
		<c:set var="employeeName" value="${UserSession.person.name}" />
		<c:set var="backdoorInUse" value="${UserSession.backdoorInUse == 'true'}" />
		<c:set var="targetInUse" value='<%=org.kuali.kpme.core.util.HrContext.isTargetInUse()%>' />
	</c:if>

	<c:if test="${backdoorInUse}">
		<c:set var="prefix" value="Backdoor" />
		<c:set var="highlight" value="highlight" />
		<c:set var="backdoor" value="backdoor" />
	</c:if>
	<c:if test="${targetInUse}">
		<c:set var="targetuser" value="targetuser" />
		<c:set var="targetName" value='<%=org.kuali.kpme.core.util.HrContext.getTargetName()%>' />
	</c:if>
	
	<c:set var="systemAdmin" value='<%=org.kuali.kpme.core.util.HrContext.isSystemAdmin()%>' />
    <c:set var="locationAdmin" value='<%=org.kuali.kpme.tklm.time.util.TkContext.isLocationAdmin()%>' />


	<input type="hidden" id="tabId" value="${tabId}" />

	<div id="tabs"
		class="ui-tabs ui-widget ui-widget-content ui-corner-all">
		<ul
			class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all ${highlight} ${targetuser}">
			<div>
				<div style="float: left;">
					<span class="title ${backdoor}"> <img
						src="images/khr_logo.png" style="width: 4em;" />
					</span> <span class="yellowbanner"> <c:if test="${targetInUse}">
	               You are working on 
	                <a
								href="<%=request.getContextPath()%>/PersonInfo.do?methodToCall=showInfo"
								style="color: black;">${targetName}</a>'s calendar. 
	                <input type="button" class="button" id="return-button"
								value="Return" name="return"
								onclick="location.href='<%=request.getContextPath()%>/changeTargetPerson.do?methodToCall=clearTargetPerson'" />
						</c:if>
					</span>
					<div class="go-to-portal">
						<c:if test="${systemAdmin || locationAdmin}">
							<table align="left" width="100%">
								<tr>
									<td width="10%"></td>	
									<td><select id="goToPortal" name="goToPortal"
										ONCHANGE="location = this.options[this.selectedIndex].value;"
										style="width: 150px">
											<option value="" selected="selected">Go To...</option>
											<option value="${ConfigProperties.application.url}/portal.do?selectedTab=main">
												KHR Menu</option>
											<option value="${ConfigProperties.application.url}/portal.do?channelTitle=Action%20List&channelUrl=${ConfigProperties.application.url}/kew/ActionList.do">
												Action List</option>	
											<option value="${ConfigProperties.application.url}/portal.do?channelTitle=Document%20Search&channelUrl=${ConfigProperties.application.url}/kew/DocumentSearch.do?docFormKey=88888888&returnLocation=${ConfigProperties.application.url}/portal.do">
												Doc Search</option>
											<option value="${ConfigProperties.application.url}/portal.do?selectedTab=kpmemaintenance">
												KHR Maintenance</option>
											<option value="${ConfigProperties.application.url}/portal.do?selectedTab=riceadministration">
												Rice Administration</option>
									</select></td>
								</tr>
							</table>
						</c:if>
					</div>
				</div>
				<div class="person-info" style="float: right;">

					<table class="${backdoor}">

						<tr>
							<td align="right" colspan="2"><c:if test="${backdoorInUse}">
									<a
										href="<%=request.getContextPath()%>/backdoorlogin.do?methodToCall=logout"
										style="font-size: .8em;">Remove backdoor</a> |
                        </c:if> <a
								href="<%=request.getContextPath()%>/logout.do"
								style="font-size: .8em;">Logout</a></td>
							<td></td>
						</tr>
						<c:if test="${targetInUse}">
							<tr>
								<td align="right">${prefix} <bean:message
										key="person.info.targetEmployeeName" />:
								</td>
								<td>${targetName}</td>
							</tr>
						</c:if>
						<tr>
							<td align="right">${prefix} <bean:message
									key="person.info.employeeName" />:
							</td>
							<td><a
								href="<%=request.getContextPath()%>/PersonInfo.do?methodToCall=showInfo">${employeeName}</a>
							</td>
						</tr>
						<tr>
							<td align="right">${prefix} <bean:message
									key="person.info.employeeId" />:
							</td>
							<td>${employeeName}</td>
						</tr>
						<c:if test="${Form.documentId ne null}">
							<tr>
								<td align="right">${prefix} <bean:message
										key="approval.documentId" />:
								</td>
								<td>${Form.documentId}</td>
							</tr>
							<tr>
								<td align="right">${prefix} <bean:message
										key="time.detail.documentStatus" />:
								</td>
								<td>${Form.documentStatus}</td>
							</tr>
						</c:if>
					</table>

				</div>
			</div>
			<tk:tabs />
		</ul>
		<div class="msg-excol">
			<div class="left-errmsg">
				<kul:errors errorTitle="Error:" />
				<kul:messages />
			</div>
		</div>
		<jsp:doBody />
	</div>
	<tk:tkFooter />
</body>
</html>
