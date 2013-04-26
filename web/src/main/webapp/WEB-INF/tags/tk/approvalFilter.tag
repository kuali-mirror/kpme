<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<jsp:useBean id="tagSupport" class="org.kuali.kpme.tklm.common.TagSupport"/>

<table id="approvals-filter">
    <tr>
        <td>
            Pay Calendar Group:
            <label for="pay calendar groups">
                <select id="selectedPayCalendarGroup" name="selectedPayCalendarGroup"
                        onchange="this.form.methodToCall.value='selectNewPayCalendar'; this.form.submit();">
                    <c:forEach var="payCalendarGroup" items="${Form.payCalendarGroups}">
                        <c:choose>
                            <c:when test="${Form.selectedPayCalendarGroup eq payCalendarGroup}">
                                <option value="${payCalendarGroup}" selected="selected">${payCalendarGroup}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${payCalendarGroup}">${payCalendarGroup}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </label>
        </td>
        <td>
            Department:
            <select id="selectedDept" name="selectedDept"
                    onchange="this.form.methodToCall.value='selectNewDept'; this.form.submit();">
                <option value="">-- Select a department --</option>
                <c:forEach var="dept" items="${Form.departments}">
                    <c:choose>
                        <c:when test="${Form.selectedDept eq dept}">
                            <option value="${dept}" selected="selected">${dept}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${dept}">${dept}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </td>
        <td>
            Work Area:
            <label for="work areas">
                <select id="selectedWorkArea" name="selectedWorkArea"
                        onchange="this.form.methodToCall.value='selectNewWorkArea'; this.form.submit();">
                    <option value="">Show All</option>
                    <c:forEach var="deptWorkarea" items="${Form.workAreaDescr}">
                        <c:choose>
                            <c:when test="${Form.selectedWorkArea eq deptWorkarea.key}">
                                <option value="${deptWorkarea.key}" selected="selected">${deptWorkarea.value}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${deptWorkarea.key}">${deptWorkarea.value}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </label>
        </td>
       
    </tr>
    
</table>