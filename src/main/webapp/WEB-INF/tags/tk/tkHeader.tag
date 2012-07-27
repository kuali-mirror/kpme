<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="tabId" required="false" %>
<jsp:useBean id="tagSupport" class="org.kuali.hr.time.util.TagSupport"/>
<jsp:useBean id="form" class="org.kuali.hr.time.base.web.TkForm"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="cache-control" content="public">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <title>Kuali Time</title>
    <tk:tkInclude/>
    <tk:tkJsInclude/>
</head>
<body>

<c:if test="${!empty UserSession.loggedInUserPrincipalName}">
    <c:set var="employeeName" value="${UserSession.person.name}" />
    <c:set var="backdoorInUse" value="${UserSession.backdoorInUse == 'true'}" />
    <c:set var="targetInUse" value='<%=org.kuali.hr.time.util.TKUser.isTargetInUse()%>' />
</c:if>

<c:if test="${backdoorInUse}">
    <c:set var="prefix" value="Backdoor"/>
    <c:set var="highlight" value="highlight"/>
    <c:set var="backdoor" value="backdoor"/>
</c:if>
<c:if test="${targetInUse}">
	<c:set var="targetuser" value="targetuser"/>
	<c:set var="targetName" value='<%=org.kuali.hr.time.util.TKUser.getCurrentTargetPerson().getName()%>' />
</c:if>

<input type="hidden" id="tabId" value="${tabId}"/>

<div id="tabs" class="ui-tabs ui-widget ui-widget-content ui-corner-all">
    <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all ${highlight} ${targetuser}">
			<span class="title ${backdoor}">
	            <img src="images/kpme_logo.png" style="width:4em;"/> 
	        </span>
	        <span class="yellowbanner">
            	<c:if test="${targetInUse}">
	               You are working on 
	                <a href="<%=request.getContextPath() %>/PersonInfo.do?methodToCall=showInfo" style="color: black;">${targetName}</a>'s calendar. 
	                <input type="button" class="button" id="return-button" value="Return" name="return"
	                       onclick="location.href='<%=request.getContextPath() %>/changeTargetPerson.do?methodToCall=clearTargetPerson'" />
           		 </c:if>
	         </span>

        <div class="person-info">
            <table class="${backdoor}">
                <tr>
                    <td align="right" colspan="2">
                        <c:if test="${backdoorInUse}">
                            <a href="?methodToCall=clearBackdoor" style="font-size: .8em;">Remove backdoor</a> |
                        </c:if>
                        <a href="<%=request.getContextPath() %>/logout.do" style="font-size: .8em;">Logout</a>
                    </td>
                    <td></td>
                </tr>
                <c:if test="${targetInUse}">
                    <tr>
                        <td align="right">${prefix} <bean:message key="person.info.targetEmployeeName"/>:</td>
                        <td>${targetName}</td>
                    </tr>
                </c:if>
                <tr>
                    <td align="right">${prefix} <bean:message key="person.info.employeeName"/>:</td>
                    <td><a href="<%=request.getContextPath() %>/PersonInfo.do?methodToCall=showInfo">${employeeName}</a>
                    </td>
                </tr>
                <tr>
                    <td align="right">${prefix} <bean:message key="person.info.employeeId"/>:</td>
                    <td>${employeeName}</td>
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
                <c:if test="${form.leaveDocumentIdFromContext ne null}">
                	<tr>
                        <td align="right">${prefix} <bean:message key="approval.documentId"/>:</td>
                        <td>${form.leaveDocumentIdFromContext}</td>
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
