<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<c:set var="Form" value="${EdoAdminGroupsForm}"
       scope="request" />

<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>

    <div style="width: 100%;">

        <edo:edoTreeNav />
        <div class="content">
            <script>
                function dispatch(frm, methodName) {
                    frm.methodToCall.value = methodName;
                    frm.submit();
                }

                function resetForm(frm) {
                    frm.departmentCode.value = '';
                    frm.schoolCode.value = '';
                    frm.campusCode.value = '';
                    frm.unitList.value = '';
                    frm.groupMembers.value = '';
                    $('#group_list').datatable().fnClearTable();
                }
            </script>

            <h3>Administrative Groups</h3>

            <c:if test="${!empty ErrorPropertyList}">
                <div class="error">
                    <kul:errors keyMatch="error" errorTitle="Errors:" />
                    <br />
                </div>
            </c:if>

            <fieldset>
                <legend>
                    <em><strong>Department, School and Campus Codes</strong></em>
                </legend>
                <html:form action="/EdoAdminGroups.do?tabId=gadmin" method="post"
                           enctype="multipart/form-data">
                <html:hidden property="methodToCall" value="" />
                Department Code
                <html:text property="departmentCode" size="12" />&nbsp;&nbsp;
                School Code
                <html:text property="schoolCode" size="12" />&nbsp;&nbsp;
                Campus Code
                <html:text property="campusCode" size="12" />&nbsp;&nbsp;
                <input type="image" alt="Search" name="search"
                       src="images/buttonsmall_search.gif"
                       onclick="javascript:dispatch(forms[0], 'search');"
                       style="border: none; vertical-align: middle;" />
                <button name="reset" onclick="javascript:resetForm(forms[0]);">Reset</button>
                <c:if test="${not empty foundEntries}">
                    <c:if test="${not foundEntries}">
                        <button name="searchsubmit" onclick="javascript:dispatch(forms[0], 'addToList');"><strong>Add this group to the list?</strong></button>
                    </c:if>
                </c:if>
                </br>
                <br>Department/School/Campus List </br>
                <html:textarea property="unitList" rows="10" cols="40" /> <br>
                <p>Or, upload a comma-separated list of departments/schools/campuses.</p>
                <p>Use this <strong><a href="EdoGroupCSV.do">CSV file template</a></strong> to create your bulk load member list.
                    It is important that you KEEP the header line in your CSV; the upload system expects that line to be present.</p>

                <input type="file" name="uploadGrpFile" id="uploadGrpFile" size="60" />
                <button name="uploadgrpsubmit" onclick="javascript:dispatch(forms[0], 'uploadDeptCSV');">Upload file</button>
                <div style="background-color: #8C8C8C; padding: 8px;">
                    <button name="buildgrpsubmit" onclick="javascript:dispatch(forms[0], 'buildList');"><strong>Build Groups Based on This List</strong></button>
                </div>
            </fieldset>

            <fieldset>
                <legend>
                    <em><strong>Group List</strong></em>
                </legend>
                <edo:jq_grouptable_init />
                <table id="group_list"></table>
            </fieldset>
                <%--
               <fieldset>
                   <legend id="grpMemberLegend">
                       <em><strong>Group Members</strong></em>
                   </legend>
                   <div id="memberButtonDisplay" style="display: none;">
                       <span id="groupNameTitle"></span>
                       <html:textarea property="groupMembers" styleId="groupMembers" rows="10" cols="40" /><br>
                       Or upload a comma-separated list of usernames for this group:

                       <input id="addToGroupName" type="hidden" name="addToGroupName" value="" />
                       <input type="file" name="uploadMbrFile" id="uploadMbrFile" size="60" />
                       <button name="uploadgrpsubmit" onclick="javascript:dispatch(forms[0], 'uploadMbrCSV');">Upload file</button> <br>

                       <div style="background-color: #8C8C8C; padding: 3px;">
                           <button name="submitmembers" onclick="javascript:dispatch(forms[0], 'addMembersToGroup');">
                           <strong>Update group <span id="groupNameButton"></span> with this list</strong>
                           </button>
                       </div>
                   </div>
               </fieldset> --%>
            </html:form>

        </div>
        <br style="clear: both;" />
    </div>
    <edo:edoFooter/>
</edo:edoPageLayout>
