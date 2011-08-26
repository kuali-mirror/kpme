<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>
<c:set var="Form" value="${BatchJobActionForm}" scope="request"/>
<c:set var="KualiForm" value="${BatchJobActionForm}" scope="request"/>

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
                                    <option value="${job}">${job}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th><label>Pay calendar period : </label></th>
                        <td>
                            <input type="text" name="hrPyCalendarEntryId" id="hrPyCalendarEntryId" value="${Form.hrPyCalendarEntryId}"/>
                        </td>
                        <td>
                        	<kul:lookup boClassName="org.kuali.hr.time.paycalendar.PayCalendarEntries"
                					fieldConversions="hrPyCalendarEntriesId:hrPyCalendarEntryId"
                					lookupParameters="" />
                        </td>
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
                        <td><input type="text" name="hrPyCalendarEntryId"/></td>
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
                    <display:table name="${Form.batchJobEntries}" excludedParams="*" pagesize="10"
                                   requestURI="BatchJob.do"
                                   requestURIcontext="false" id="r">
                        <display:column property="tkBatchJobEntryId" title="Job Entry Id"/>
                        <display:column property="batchJobName" title="Job Name"/>
                        <display:column property="tkBatchJobEntryId" title="Job Entry Status"/>
                        <display:column property="hrPyCalendarEntryId" title="Pay Calendar Id"/>
                        <display:column title="IP Address">
                            <tk:ipAddress batchJobEntryId="${r.tkBatchJobEntryId}"
                                          selectedIpAdd="${r.ipAddress}"/>
                        </display:column>
                        <display:column property="documentId" title="Document Id"/>
                        <display:column property="principalId" title="Principal Id"/>
                    </display:table>
                </div>
            </fieldset>
        </html:form>
    </div>


</tk:tkHeader>