<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<c:set var="Form" value="${EdoAssignChairDelegateForm}"
       scope="request" />

<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>

    <div style="width: 100%;">

        <edo:edoTreeNav />
        <div class="content">
            <h3>Assign a Delegate</h3>
            <html:form action="/EdoAssignChairDelegate" method="post"
                       enctype="multipart/form-data">
                <html:hidden property="methodToCall" value="" />
                <html:hidden property="emplToAdd" value="" />
                <html:hidden property="emplToDelete" value="" />

                <c:if test="${!empty ErrorPropertyList}">
                    <div class="error">
                        <kul:errors keyMatch="error" errorTitle="Errors:" />
                        <br />
                    </div>
                </c:if>

                <fieldset>
                    <legend>
                        <i><b>Search Users</b></i>
                    </legend>
                    <br> IU Username :
                    <html:text property="userId" size="50" />
                    <input type="image" alt="Search" name="search"
                           src="images/buttonsmall_search.gif"
                           onclick="this.form.methodToCall.value='search';"
                           style="border: none; vertical-align: middle;" /> <br />

                    <c:if test="${Form.chairDelegateAdded}">
                        <span style="color: red;">Delegate has been added</span>
                    </c:if>

                    <c:if test="${Form.alreadyChairDelegate}">
                        <span style="color: red;">Is already a Delegate for you</span>
                    </c:if>

                    <br /> <br />
                    <table>
                        <tbody>

                        <tr>
                            <td class="headerright">Name :</td>
                            <td><input type="text" name="name" value='${Form.name}'
                                       readonly></td>

                        </tr>

                        <tr>
                            <td class="headerright">Start Date :</td>
                            <%--      <td><html:text property="startDate" styleId="startDate" onblur="isDate(document.getElementById('startDate').value);" /></td> --%>
                            <td><html:text property="startDate" styleId="startDate"
                                           readonly='true' /></td>
                        </tr>
                        <tr>
                            <td class="headerright">End Date :</td>
                            <%--  <td><html:text property="endDate" styleId="endDate" onblur="isDate(document.getElementById('endDate').value);" /></td> --%>
                            <td><html:text property="endDate" styleId="endDate"
                                           readonly='true' /></td>
                        </tr>
                        <tr>
                            <td></td>
                        </tr>

                        <tr>
                            <%--   <td> <input type="image"
                        alt="Add Delegate for Chair"
                        name="Add Delegate for Chair"
                        src="images/buttonsmall_save.gif"
                        onclick="this.form.methodToCall.value='save'; this.form.emplToAdd.value='${Form.emplId}';"
                        style="border: none; vertical-align: middle;" /></td>--%>
                            <td><input type="image" alt="Add Delegate for Chair"
                                       name="Add Delegate for Chair"
                                       src="images/buttonsmall_save.gif"
                                       onclick="return validateRange(document.getElementById('startDate').value,document.getElementById('endDate').value,'${Form.emplId}');"
                                       style="border: none; vertical-align: middle;" /></td>

                            <%-- Cancel --%>
                            <td><input type="image" alt="Cancel" name="cancel"
                                       src="images/buttonsmall_cancel.gif" alt="Cancel" border="0"
                                       onclick="this.form.methodToCall.value='cancel';"
                                       style="border: none;" /></td>
                        </tr>
                        </tbody>
                    </table>

                </fieldset>

                <br />
                <br />

                <fieldset>
                    <legend>
                        <i><b>Your current delegates</b></i>
                    </legend>

                    <br />
                    <c:if test="${Form.chairDelegateDeleted}">
                        <span style="color: red;">Delegate has been removed.</span>
                    </c:if>
                    <br />

                    <table id="delegate-table">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${fn:length(Form.edoChairDelegatesInfo) < 1}">
                                <tr>
                                    <td colspan="4">none</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="entry" items="${Form.edoChairDelegatesInfo}">
                                    <tr>
                                        <td>${entry.delegateFullName}</td>
                                        <td>${entry.startDate}</td>
                                        <td>${entry.endDate}</td>
                                        <td><input type="image"
                                                   alt="Delete Delegate for Chair"
                                                   name="Delete Delegate for Chair"
                                                   src="images/buttonsmall_delete.gif"
                                                   onclick="this.form.methodToCall.value='deleteChairDelegate'; this.form.emplToDelete.value='${entry.delegatePrincipalName}';"
                                                   style="border: none; vertical-align: middle;" /></td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
                </fieldset>
            </html:form>
        </div>
    </div>
</edo:edoPageLayout>
