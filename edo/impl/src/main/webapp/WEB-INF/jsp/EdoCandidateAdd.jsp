<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>

<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>

    <div style="width: 100%;">

    <edo:edoTreeNav />
    <div class="content">
        <h3>Add Candidate</h3>
       
        <html:form action="/EdoCandidateAdd.do" method="post" >
            <table border="0">
                <tr><td>Last Name</td><td><html:text property="lastName" /></td></tr>

                <tr><td>First Name</td><td><html:text property="firstName" /></td></tr>

                <tr><td>Current Rank</td><td><html:text property="currentRank" /></td></tr>

                <tr><td>Campus</td><td><html:select property="candidacyCampus">
                    <html:option value="XX">Select Campus</html:option>
                    <html:option value="BL">IUB</html:option>
                    <html:option value="IN">IUPUI</html:option>
                    <html:option value="EA">IUE</html:option>
                    <html:option value="SB">IUSB</html:option>
                    <html:option value="NW">IUN</html:option>
                    <html:option value="SE">IUS</html:option>
                    <html:option value="KO">IUK</html:option>
                    <html:option value="CO">IUPUC</html:option>
                </html:select></td></tr>

                <tr><td>School</td><td><html:text property="candidacySchool" /></td></tr>

                <tr><td>Candidacy Year</td><td><html:text property="candidacyYear" /></td></tr>

                <tr><td>Primary Department</td><td><html:text property="primaryDepartmentID" /></td></tr>
                <tr><td>Tenure/Promotion Department</td><td><html:text property="tnpDepartmentID" /></td></tr>
            </table>
            <html:submit value="Add Candidate" />
        </html:form>
    </div>
    </div>
</edo:edoPageLayout>
