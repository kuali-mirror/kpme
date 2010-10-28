<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<%@ attribute name="tabId" required="false"%>

<jsp:useBean id="form" class="org.kuali.hr.time.base.web.TkForm"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Kuali Time</title>
		<tk:tkInclude/>
		<tk:tkJsInclude/>
	</head>
	<body>

	<c:choose>
		<c:when test="${form.user.backdoorPerson ne null}">
			<c:set var="person" value="${form.user.backdoorPerson}" />
			<c:set var="prefix" value="Backdoor" />
			<c:set var="highlight" value="highlight" />
			<c:set var="backdoor" value="backdoor" />
		</c:when>
		<c:otherwise>
			<c:set var="person" value="${form.user.actualPerson}" />
		</c:otherwise>
	</c:choose>
<input type="hidden" id="tabId" value="${tabId}"/>
	<div id="tabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all">
		<ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all ${highlight}">
			<span class="title ${backdoor}" >
	            <img src="images/kuali_base.png" style="width:4em;"/>
	            TIME
	        </span>
            <div class="person-info">
                <table class="${backdoor}">
                    <tr>
                        <td align="right">${prefix} <bean:message key="person.info.employeeName"/>:</td>
                        <td><a href="<%=request.getContextPath() %>/PersonInfo.do?methodToCall=showInfo">${person.name}</a> <a href="<%=request.getContextPath() %>/Logout.do"/>Logout</td>
                    </tr>
                    <tr>
                        <td align="right">${prefix} <bean:message key="person.info.employeeId"/>:</td>
                        <td>${person.name}</td>
                    </tr>
                    <c:if test="${form.user.backdoorPerson ne null}">
                        <tr>
                            <td colspan="2" align="right">
                            <a href="?methodToCall=clearBackdoor"><input type="button" class="button" value="Clear Backdoor" name="clearBackdoor" style="font-size:.7em;"></a>
                            </td>
                        </tr>
                    </c:if>
                </table>
            </div>
            <tk:tabs/>
		</ul>

		<jsp:doBody />
	<tk:note/>
	</div>
	<tk:tkFooter/>
	</body>
</html>
