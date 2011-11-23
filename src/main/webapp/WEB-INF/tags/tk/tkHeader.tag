<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="tabId" required="false" %>
<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>
<jsp:useBean id="form" class="org.kuali.hr.time.base.web.TkForm"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="cache-control" content="public">
    <%--<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />--%>
    <title>Kuali Time</title>
    <tk:tkInclude/>
    <tk:tkJsInclude/>
</head>
<body>

<c:choose>
    <c:when test="${form.user.backdoorPerson ne null}">
        <c:set var="person" value="${form.user.backdoorPerson}"/>
        <c:set var="prefix" value="Backdoor"/>
        <c:set var="highlight" value="highlight"/>
        <c:set var="backdoor" value="backdoor"/>
    </c:when>
    <c:otherwise>
        <c:set var="person" value="${form.user.actualPerson}"/>
    </c:otherwise>
</c:choose>
<c:if test="${form.user.targetPerson ne null}">
	<c:set var="targetuser" value="targetuser"/>
</c:if>

<input type="hidden" id="tabId" value="${tabId}"/>

<div id="tabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all">
    <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all ${highlight} ${targetuser}">
			<span class="title ${backdoor}">
	            <img src="images/kuali_base.png" style="width:4em;"/>
	            TIME 
	        </span>
	        <span class="yellowbanner">
            	<c:if test="${form.user.targetPerson ne null}">
	               You are working on 
	                <a href="<%=request.getContextPath() %>/PersonInfo.do?methodToCall=showInfo" style="color: black;">${form.user.targetPerson.name}</a>'s calendar. 
	                <input type="button" class="button" id="return-button" value="Return" name="return"
	                 onclick="location.href='?methodToCall=clearChangeUser'" />
           		 </c:if>
	         </span>

        <div class="person-info">
            <table class="${backdoor}">
                <tr>
                    <td align="right" colspan="2">
                        <c:if test="${form.user.backdoorPerson ne null}">
                            <a href="?methodToCall=clearBackdoor" style="font-size: .8em;">Remove backdoor</a> |
                        </c:if>
                        <a href="<%=request.getContextPath() %>/Logout.do" style="font-size: .8em;">Logout</a>
                    </td>
                    <td></td>
                </tr>
                <c:if test="${form.user.targetPerson ne null}">
                    <tr>
                        <td align="right">${prefix} <bean:message key="person.info.targetEmployeeName"/>:</td>
                        <td>${form.user.targetPerson.name}</td>
                    </tr>
                </c:if>
                <tr>
                    <td align="right">${prefix} <bean:message key="person.info.employeeName"/>:</td>
                    <td><a href="<%=request.getContextPath() %>/PersonInfo.do?methodToCall=showInfo">${person.name}</a>
                    </td>
                </tr>
                <tr>
                    <td align="right">${prefix} <bean:message key="person.info.employeeId"/>:</td>
                    <td>${person.name}</td>
                </tr>
                <c:if test="${form.documentIdFromContext ne null}">
                	<tr>
                        <td align="right">${prefix} <bean:message key="approval.documentId"/>:</td>
                        <td>${form.documentIdFromContext}</td>
                    </tr>
                    <tr>
                        <td align="right">${prefix} <bean:message key="time.detail.documentStatus"/>:</td>
                        <td>${tagSupport.documentStatus[form.documentStatus]}</td>
                    </tr>
                </c:if>
            </table>
        </div>
        <tk:tabs/>
    </ul>
    <div class="msg-excol">
        <div class="left-errmsg">
            <kul:errors errorTitle="Error:"/>
            <kul:messages/>
        </div>
    </div>
    <jsp:doBody/>
</div>
<tk:tkFooter/>
</body>
</html>
