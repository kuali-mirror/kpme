<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<c:set var="Form" value="${EdoManageAdminsForm}"
       scope="request" />

<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>

    <div style="width: 100%;">

        <edo:edoTreeNav />
        <div class="content">
            <script>
                function fnResetSelect(oSelect) {
                    document.getElementById(oSelect).options.selectedIndex = 0;
                }

                function fnSingleSelect(oReset1, oReset2) {
                    fnResetSelect(oReset1);
                    fnResetSelect(oReset2);
                }

                function dispatch(frm, methodName) {
                    frm.methodToCall.value = methodName;
                    frm.submit();
                }

            </script>
            <h2>Manage Staff Administrators</h2>
            <c:if test="${!empty ErrorPropertyList}">
                <div class="error">
                    <kul:errors keyMatch="error" errorTitle="Errors:" />
                    <br />
                </div>
            </c:if>

            <fieldset>
                <legend>
                    <strong><span style="font-size: 14pt;">Step 1: Define Staff Administrators</span></strong>
                </legend>
                <blockquote>Select the organizational level for the staff administrator from the drop down lists.  Select only one
                    level (department, school, or campus).  If you select more than one level, we'll use the lowest level
                    unit for the staff administrator.  Then click "Set."<br><br>
                    Once the staff administrator definition is set, click "Add" to include the administrator to your build list.  Keep
                    adding staff administrator definitions until you have the list you want.
                </blockquote>
                <br>
                <strong>Department Code</strong>
                <select name="departmentCode" id="departmentCode" onchange="fnSingleSelect('schoolCode', 'campusCode')">
                    <option value></option>
                    <c:forEach var="code" items="${Form.departmentSelectList}">
                        <option value="${code}">${code}</option>
                    </c:forEach>
                </select>
                <strong>School Code</strong>
                <select name="schoolCode" id="schoolCode" onchange="fnSingleSelect('departmentCode', 'campusCode')">
                    <option value></option>
                    <c:forEach var="code" items="${Form.schoolSelectList}">
                        <option value="${code}">${code}</option>
                    </c:forEach>
                </select>
                <strong>Campus Code</strong>
                <select name="campusCode" id="campusCode" onchange="fnSingleSelect('schoolCode', 'departmentCode')">
                    <option value></option>
                    <c:forEach var="code" items="${Form.campusSelectList}">
                        <option value="${code}">${code}</option>
                    </c:forEach>
                </select>
                <strong>Username</strong>
                <input type="text" name="username" id="username" size="15" />

                <button name="addmember" id="add_member_btn" onclick="javascript:add_member();" style="font-size: 14px;">Add</button>
                <br>
            </fieldset>
            <br>
            <fieldset>
                <legend><strong><span style="font-size: 14pt;">Step 2: Assign the Staff Administrators to eDossier</span></strong></legend>
                <blockquote>
                    With your list built, simply click the button below this table to add these staff administrators to the appropriate roles in eDossier.
                </blockquote>
                <edo:jq_admintable_init />

                <table id="admin_list"></table>
                <button name="getmembers" id="get_members_btn" onclick="javascript:get_members();" style="font-size: 14px;">Add staff administrators</button>
            </fieldset>
            <br>
            <fieldset>
                <legend><strong><span style="font-size: 14pt;">Bulk Load Option</span></strong></legend>
                <blockquote>
                    Instead of creating the list of staff administrators one by one, as above, you can upload a file containing the list
                    of staff administrators and their appropriate unit levels.<br>
                    Use this <strong><a href="EdoMemberCSV.do?csv=admin">CSV file template</a></strong> to create your bulk load administrators list.
                    It is important that you KEEP the header line in your CSV; the upload system expects that line to be present.
                </blockquote>

                <html:form action="/EdoManageAdmins.do?tabId=gadmin" method="post" styleId="mainForm"
                           enctype="multipart/form-data">
                    <html:hidden property="methodToCall" value="" />
                    <html:hidden property="memberData" value="" />
                    <input type="file" name="uploadAdmFile" id="uploadAdmFile" size="60" />
                    <button name="uploadadmsubmit" onclick="javascript:dispatch(document.forms[0], 'uploadAdmCSV');"  style="font-size: 14px;">
                        Upload file
                    </button>
                </html:form>

            </fieldset>

            <c:if test="${not empty successfulMembers}">
                <h2>Results</h2>
                <table style="background-color: #000000;" cellspacing="1">
                    <thead style="background-color: #7D110C; color: #FFFFFF; font-size: 12px;">
                    <td>Administrators successfully added to groups</td>
                    </thead>
                    <c:forEach var="user" items="${successfulMembers}">
                        <tr>
                            <td style="background-color: #FFFFFF; color: #000000;">${user}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>

    </div>
    <edo:edoFooter/>
</edo:edoPageLayout>
