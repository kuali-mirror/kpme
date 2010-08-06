<%--
 Copyright 2005-2006 The Kuali Foundation.
 
 Licensed under the Educational Community License, Version 1.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl1.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>
<c:set var="Form" value="${AdminActionForm}" scope="request"/>
<c:set var="path" value="CacheAdministration" />

<tk:tkHeader tabId="cache">

<html:form action="/CacheAdministration" method="post">
<html:hidden property="methodToCall" value=""/>

    <a href="${path}.do?methodToCall=start">Show All</a>
    <br/>
    <br/>
    <table width="75%" border="2" cellpadding="4" cellspacing="4" class="datatable">
        <tr>
            <th align=left>Group Key</th>
            <th align=left>Group Action</th>
        </tr>
        <c:forEach items="${groups}" var="mapEntry" varStatus="groupStatus">
        <tr>
            <td valign=top>${mapEntry.key}</td>
			<td>
			 <table border="2" cellpadding="2" cellspacing="2" class="datatable">
			 <tr>
			     <td align="left" valign="top">
				    <a href="${path}.do?methodToCall=flushGroup&amp;groupKey=${mapEntry.key}">Flush</a>
			     </td>
                 <td align="right">
                 <c:choose>
                    <c:when test="${KualiForm.groupKey != mapEntry.key}">
                        <a href="${path}.do?methodToCall=showGroup&amp;groupKey=${mapEntry.key}">Show</a>
                    </c:when>
                    <c:otherwise>
	                    <table width="25%" border="2" cellpadding="2" cellspacing="2" class="datatable" align="right">
	                    <tr>
	                        <th align=left>Item Key</th>
	                        <th align=left>Item Action</th>
	                    </tr>
	                    <c:forEach items="${mapEntry.value}" var='valueEntry' varStatus="objectStatus">
	                    <tr>
	                        <td valign=top>${valueEntry}</td>
	                        <td align="center">
	                        <a href="${path}.do?methodToCall=flushItem&amp;itemKey=${valueEntry}">Flush</a>
	                        </td>
	                    </tr>
	                    </c:forEach>
	                    </table>
                    </c:otherwise>
                 </c:choose>   
                 </td>
			 </tr>
			 </table>
			</td>
        </tr>
        </c:forEach>
    </table>
</html:form>
</tk:tkHeader>