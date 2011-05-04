<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${BatchJobActionForm}" scope="request"/>

<tk:tkHeader tabId="batchJob">

    <div id="batch-content">
        <html:form action="/BatchJob.do" styleId="batch-job">
            <fieldset id="batch-run">
                <legend>Run Batch Job</legend>
                <table>
                    <tr>
                        <th><label>Batch Job :</label></th>
                        <td>
                            <select name="batchJobNames" id="batchJobNames">
                                <c:forEach var="job" items="${Form.batchJobNames}">
                                    <option value="${job.key}">${job.value}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th><label>Pay calendar period : </label></th>
                        <td><input type="text" name="payCalendarPeriod"/></td>
                        <td><input type="submit" value="Run"/></td>
                    </tr>
                </table>
                <br/>
            </fieldset>
        </html:form>

        <html:form action="/BatchJob.do?methodToCall=getBatchJobEntryStatus" styleId="batch-job">
            <fieldset id="batch-status">
                <legend>Batch Job Entry Status</legend>
                <table>
                    <tr>
                        <th><label>Job Id : </label></th>
                        <td><input type="text" name="tkBatchJobId"/></td>
                    </tr>
                    <tr>
                        <th><label>Job Name : </label></th>
                        <td><input type="text" name="batchJobName"/></td>
                    </tr>
                    <tr>
                        <th><label>Job Status : </label></th>
                        <td><input type="text" name="batchJobEntryStatus"/></td>
                    </tr>
                    <tr>
                        <th><label>Pay Calendar Id : </label></th>
                        <td><input type="text" name="payCalendarEntryId"/></td>
                    </tr>
                    <tr>
                        <th><label>IP Address : </label></th>
                        <td><input type="text" name="ipAddress"/></td>
                    </tr>
                    <tr>
                        <th><label>Document Id : </label></th>
                        <td><input type="text" name="documentId"/></td>
                    </tr>
                    <tr>
                        <th><label>Principal Id : </label></th>
                        <td><input type="text" name="principalId"/></td>
                        <td><input type="submit" value="Search"/></td>
                    </tr>
                </table>
            </fieldset>

            <fieldset>
                <legend>Result</legend>
                <div id="batch-result">
                    <table>
                        <tr>
                            <th>Job Entry Id</th>
                            <th>Job Name</th>
                            <th>Job Entry Status</th>
                            <th>Pay Calendar Id</th>
                            <th>IP Address</th>
                            <th>Document Id</th>
                            <th>Principal Id</th>
                        </tr>
                        <c:forEach var="entry" items="${Form.batchJobEntries}">
                            <tr>
                                <td>${entry.tkBatchJobEntryId}</td>
                                <td>${entry.batchJobName}</td>
                                <td>${entry.tkBatchJobEntryId}</td>
                                <td>${entry.payCalendarEntryId}</td>
                                <td><tk:ipAddress batchJobEntryId="${entry.tkBatchJobEntryId}"
                                                  selectedIpAdd="${entry.ipAddress}"/></td>
                                <td>${entry.documentId}</td>
                                <td>${entry.principalId}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </fieldset>
        </html:form>
    </div>
</tk:tkHeader>