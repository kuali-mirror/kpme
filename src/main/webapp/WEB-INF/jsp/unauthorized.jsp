<%--

    Copyright 2004-2012 The Kuali Foundation

    Licensed under the Educational Community License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.opensource.org/licenses/ecl2.php

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

--%>
<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<html>
<head>
<c:forEach items="${fn:split(ConfigProperties.css.files, ',')}" var="cssFile">
    <c:if test="${fn:length(fn:trim(cssFile)) > 0}">
        <link href="${pageContext.request.contextPath}/${cssFile}" rel="stylesheet" type="text/css" />
    </c:if>
</c:forEach>
</head>
<title>KPME TIME</title>

<body>

<div class="headerarea" id="headerarea"></div>

<br><br>

<div align="center" id="main">
<table width="95%" cellpadding="0" cellspacing="0" style="border-style: ridge; border-color: #7d1100; border-width: thin">
    <tr>
        <td style="background-color: #F0F0F0">

        <table width="100%">
            <tr>
                <td width="33%" valign="top">
                <div class="portlet">
                <div class="header">
                <center><h2>You are not authorized to access this portion of the application.</h2></center>
                </div>
                <div class="portlet-content">${error.message} 
                <c:if test="${not empty stacktrace }">
                    <hr />
                    <pre style="font-size:11px; font-family: Verdana, Arial, Helvetica, sans-serif;">${stacktrace}</font></pre>
                </c:if>
                </div>
                
                </td>
            </tr>
        </table>
        <br>
        <br>
        </td>
    </tr>
</table>
</body>

</html>