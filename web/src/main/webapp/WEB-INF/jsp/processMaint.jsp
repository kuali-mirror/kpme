<%--

    Copyright 2004-2013 The Kuali Foundation

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
<%@ include file="/rice-portal/jsp/sys/riceTldHeader.jsp"%>
<c:set var="Form" value="${ProcessMaintForm}" scope="request"/>
<c:set var="positionId" value="{Form.positionId}" scope="request" />
<channel:portalChannelTop channelTitle="Process Maintenance Documents" />
	<html:form action="/ProcessMaint.do" >
		<div class="body">
    		<div id="content">
    			<html:hidden property="positionId"/>
    			<c:out value="You are about to edit Maintenance document for Position 1" />
					<table style="border-collapse:collapse;border: 1px solid #000;">
						<thead>
						</thead>
						<tbody>								
								<tr>
									<td>Process Category:</td>
									<td>
										<html:select property="category">
											<option value="reorg">Reorganization</option>
											<option value="reclass">Reclassification</option>
											<option value="update">Data Update</option>
											<option value="status">Change Status</option>
										</html:select>
									</td>
								</tr>
								<tr>
									<td>Reason</td>
									<td>
									<html:text property="reason" />
									</td>
								</tr>
								<tr>
			                        <td>
			                            <html:submit property="methodToCall.processMaintDocument" value="Process Document" />
			                        </td>
			                    </tr>
						</tbody>
					</table>
		        
		  </div>
		</div>
	</html:form>

   	 		
<channel:portalChannelBottom />