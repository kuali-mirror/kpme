<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<c:set var="Form" value="${EdoAdminGroupMembersForm}"
       scope="request" />

<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>

    <div style="width: 100%;">

        <edo:edoTreeNav />
        <div class="content">
            <script>
                function fnSetDeptSchoolCampus() {
                    $('#deptTitle').html(document.getElementById('departmentCode').options[document.getElementById('departmentCode').options.selectedIndex].value);
                    $('#schoolTitle').html(document.getElementById('schoolCode').options[document.getElementById('schoolCode').options.selectedIndex].value);
                    $('#campusTitle').html(document.getElementById('campusCode').options[document.getElementById('campusCode').options.selectedIndex].value);
                    return false;
                }

                function dispatch(frm, methodName) {
                    frm.methodToCall.value = methodName;
                    frm.submit();
                }

            </script>

            <c:if test="${!empty ErrorPropertyList}">
                <div class="error">
                    <kul:errors keyMatch="error" errorTitle="Errors:" />
                    <br />
                </div>
            </c:if>

            <fieldset>
            <legend>
                <strong>Department/School/Campus Selection</strong>
            </legend>
            Department Code
            <select name="departmentCode" id="departmentCode">
                <option value="">-- No department --</option>
                <c:forEach var="code" items="${Form.departmentSelectList}">
                    <option value="${code}">${code}</option>
                </c:forEach>
            </select>
            School Code
            <select name="schoolCode" id="schoolCode">

                <c:forEach var="code" items="${Form.schoolSelectList}">
                    <option value="${code}">${code}</option>
                </c:forEach>
            </select>
            Campus Code
            <select name="campusCode" id="campusCode">

                <c:forEach var="code" items="${Form.campusSelectList}">
                    <option value="${code}">${code}</option>
                </c:forEach>
            </select>

            <button name="setunit" id="setunit" onclick="javascript:fnSetDeptSchoolCampus();" style="font-size: 14px;">Set</button>

        </fieldset>
        <br>
        <fieldset>
            <legend>
                <strong>Add a Member</strong>
            </legend>
            <table width="100%">
                <thead style="background-color: #7D110C; color: #FFFFFF; font-size: 12px;">
                <td>Department</td>
                <td>School</td>
                <td>Campus</td>
                <td>Review Level</td>
                <td>Tenure/Promotion</td>
                <td>Chair?</td>
                <td>Username</td>
                <td>&nbsp;</td>
                </thead>
                <tr>
                    <td><span id="deptTitle"></span></td>
                    <td><span id="schoolTitle"></span></td>
                    <td><span id="campusTitle"></span></td>
                    <td>
                        <select name="reviewLevelSelectList" id="rl_list">

                            <c:forEach var="level" items="${Form.reviewLevelSelectList}">
                                <option value="${level.key}">${level.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select name="tnpSelect" id="tnp">
                            <option value="TENURE">Tenure</option>
                            <option value="PROMOTION">Promotion</option>
                        </select>
                    </td>
                    <td>
                        <select name="chair" id="chair">
                            <option value="FYI">No</option>
                            <option value="APPROVE">Yes</option>
                        </select>
                    </td>
                    <td>
                        <input type="text" name="username" id="username" size="15" />
                    </td>
                    <td>
                        <button name="addmember" id="add_member_btn" onclick="javascript:add_member();" style="font-size: 14px;">Add</button>
                    </td>
                </tr>
                <tr>
                    <td colspan="8">&nbsp;</td>
                </tr>
            </table>
            <br>
            <edo:jq_membertable_init />

            <table id="member_list"></table>
        </fieldset>
            <p>
        <button name="getmembers" id="get_members_btn" onclick="javascript:get_members();" style="font-size: 14px;">Add members to groups</button>
        </p>
        <br>
        <fieldset>
            <legend><strong>Bulk Load Members</strong></legend>
            <p>Use this <strong><a href="EdoMemberCSV.do">CSV file template</a></strong> to create your bulk load member list.
               It is important that you KEEP the header line in your CSV; the upload system expects that line to be present.</p>

            <html:form action="/EdoAdminGroupMembers.do?tabId=gadmin" method="post" styleId="mainForm"
                       enctype="multipart/form-data">
            <html:hidden property="methodToCall" value="" />
            <html:hidden property="memberData" value="" />
            <input type="file" name="uploadMbrFile" id="uploadMbrFile" size="60" />
            <button name="uploadmbrsubmit" onclick="javascript:dispatch(document.forms[0], 'uploadMbrCSV');">Upload file</button> <br>
            </html:form>

        </fieldset>

        <c:if test="${not empty successfulMembers}">
        <h2>Results</h2>
            <table style="background-color: #000000;" cellspacing="1">
                <thead style="background-color: #7D110C; color: #FFFFFF; font-size: 12px;">
                <td>Members successfully added to groups</td>
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
