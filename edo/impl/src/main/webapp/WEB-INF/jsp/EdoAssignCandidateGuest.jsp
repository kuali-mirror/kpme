<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>

<c:set var="Form" value="${EdoAssignCandidateGuestForm}"
	scope="request" />

<edo:edoPageLayout>
	<edo:edoHeader></edo:edoHeader>

	<div style="width: 100%;">

		<edo:edoTreeNav />
		<div class="content">
			<h3>Assign a Guest</h3>
			<html:form action="/EdoAssignCandidateGuest" method="post"
				enctype="multipart/form-data">
				<html:hidden property="methodToCall" value="" />
				<html:hidden property="emplToAdd" value="" />
				<html:hidden property="emplToDelete" value="" />
				<html:hidden property="dossierIdToDelete" value="" />

                <c:if test="${!empty ErrorPropertyList}">
                    <div class="error">
                        <kul:errors keyMatch="error" errorTitle="Errors:" />
                        <br />
                    </div>
                </c:if>

				<fieldset>
					<legend>
						<i><b>Search Users and add them as Guests</b></i>
					</legend>
					<br> IU Username :
					<html:text property="userId" size="50" />
					<input type="image" alt="Search" name="search"
						src="images/buttonsmall_search.gif"
						onclick="this.form.methodToCall.value='search';"
						style="border: none; vertical-align: middle;" /> <br />

					<c:if test="${Form.candidateGuestAdded}">
						<span style="color: red;">Guest has been added</span>
					</c:if>
					
					<c:if test="${Form.alreadyCandidateGuest}">
						<span style="color: red;">Is already a Guest for you</span>
					</c:if>
					<br />
					<br />
					 <table>
                        <tbody>
                           
                            <tr>
                                <td class="headerright">Name :</td>
                                <td><input type="text" name="name" value='${Form.name}' readonly> </td>
                                
                            </tr>
                            <%-- dossier drop down --%>
                            <tr>
                            	  <td class="headerright">Select Dossier from the drop down :</td>
                            	  <%-- 
								<td>
									  <html:select property="dossierId">
										<html:option value="select">--SELECT--</html:option>
		                                <c:forEach var="option"
		                                           items="${EdoAssignCandidateGuestForm.dossierDropDown}">
		                                    <option value="${option.dossierID}">${option.dossierStatus}</option>
		                                </c:forEach>
		                                 </html:select>
                                </td>
                                --%>
                                <td>
                                 <select name="dossierId">
                                 	<c:if test="${fn:length(Form.dossierDropDown) eq 1}">
                                 	 <c:forEach var="dossierList" items="${Form.dossierDropDown}">
			                            
			                                    <option value="${dossierList.dossierID}" selected="selected">${dossierList.dossierStatus} - ${dossierList.dossierType.dossierTypeName}</option>
			                               
			                        </c:forEach>
			                        </c:if>
                                 	
                                 	<c:if test="${fn:length(Form.dossierDropDown) gt 1}">
			                        <option value="">- Select -</option>
			                        <c:forEach var="dossierList" items="${Form.dossierDropDown}">
			                            
			                                    <option value="${dossierList.dossierID}">${dossierList.dossierStatus} - ${dossierList.dossierType.dossierTypeName}</option>
			                               
			                        </c:forEach>
			                        </c:if>
			                    </select>
			                    </td>
 							</tr>
                            <tr>
                                <td class="headerright">Start Date :</td>
                            <td><html:text property="startDate" styleId="startDate" readonly='true' /></td>
                            </tr>
                            <tr>
                                <td class="headerright">End Date :</td>
                               <td><html:text property="endDate" styleId="endDate"  readonly='true' /></td>
                            </tr>
                            <tr>
                            	<td></td>
                            </tr>
                           
                            <tr>
                          
									<td>
									<input type="image"
											alt="Add Delegate for Candidate"
											name="Add Delegate for Candidate"
											src="images/buttonsmall_save.gif"
											onclick="return validateDateRange(document.getElementById('startDate').value,document.getElementById('endDate').value,'${Form.emplId}');" 
											style="border: none; vertical-align: middle;" />
									</td>
								
								 <%-- Cancel --%>
								 <td>
    								<input type="image" alt="Cancel" name="cancel" src="images/buttonsmall_cancel.gif" alt="Cancel" border="0" onclick="this.form.methodToCall.value='cancel';" style="border: none;"/>
                            	</td>
                            </tr>
                        </tbody>
                    </table>

				</fieldset>

                <br />
                <br />

                <fieldset>
                    <legend>
                        <i><b>Your current eDossier Guests</b></i>
                    </legend>

                    <br />
                    <c:if test="${Form.candidateGuestDeleted}">
                        <span style="color:red;">Guest has been removed.</span>
                    </c:if>
                    <br/>

                    <table id="delegate-table">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Assigned Dossier Status </th>
                            <th>Assigned Dossier Type</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${fn:length(Form.candidateGuests) < 1}">
                                <tr>
                                    <td>none</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="entry" items="${Form.candidateGuests}">
                                    <tr>

                                        <td>${entry.guestFullName}</td>
                                        <td>${entry.startDate}</td>
                                        <td>${entry.endDate}</td>
                                            <%--    <td>${entry.guestDossierId['edoDossierID']}</td> --%>
                                        <td>${entry.dossierStatus}</td>
                                        <td>${entry.dossierType}</td>
                                        <td><input type="image"
                                                   alt="Delete Guest for Candidate"
                                                   name="Delete Guest for Candidate"
                                                   src="images/buttonsmall_delete.gif"
                                                   onclick="this.form.methodToCall.value='deleteFacultyGuest'; this.form.emplToDelete.value='${entry.guestPrincipalName}';this.form.dossierIdToDelete.value='${entry.guestDossierId['edoDossierID']}';"
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
