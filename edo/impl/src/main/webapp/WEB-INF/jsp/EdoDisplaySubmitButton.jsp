<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<c:set var="Form" value="${EdoDisplaySubmitButtonForm}"
	scope="request" />

<edo:edoPageLayout>
	<edo:edoHeader></edo:edoHeader>

	<div style="width: 100%;">

		<edo:edoTreeNav />
		<div class="content">
			<h3>Activate Submit Dossier Button</h3>
			<html:form action="EdoDisplaySubmitButton.do" method="post"
				enctype="multipart/form-data">
				<html:hidden property="methodToCall" value="" />
				
				<c:if test="${!empty ErrorPropertyList}">
					<div class="error">
						<kul:errors keyMatch="error" errorTitle="Errors:" />
						<br />
					</div>
				</c:if>
				
				 <fieldset>
                    <legend>
                        <i><b>Activate Submit Button for a particular campus</b></i>
                    </legend>
               	<table>
                 	
                        <tbody>

                        <tr>
                            <td class="headerright">Campus Code</td>
                            <td>
                            	<html:select property="campusCode">
		                            
		                            <option value="">--SELECT--</option>
                                    <option value="BL">BL</option>
                                    <option value="IN">IN</option>
                                    <option value="EA">EA</option>
                                    <option value="NW">NW</option>
                                    <option value="SB">SB</option>
                                    <option value="KO">KO</option>
                                    <option value="SE">SE</option>
                                    <option value="FW">FW</option>
                        		</html:select>
                        		
                        	</td>
                        </tr>
                         <tr>
                            <td></td>
                        </tr>

                        <tr>
                            <td>Status</td>
                            <td>
                            	<html:select property="activeFlag">
		                            
		                            <option value="">--SELECT--</option>
                                    <option value="true">Active</option>
                                    <option value="false">InActive</option>
                                </html:select>
                      		</td>
                        </tr>
                       
                        <tr>
                            <td></td>
                        </tr>
                        

                        <tr>
                                
                            <td><input type="image" alt="Activate Submit Button"
                                       name="Activate Submit Button"
                                       src="images/buttonsmall_save.gif"
                                       onclick="this.form.methodToCall.value='save';"
                                       style="border: none; vertical-align: middle;" /></td>

                                <%-- Cancel --%>
                            <td><input type="image" alt="Cancel" name="cancel"
                                       src="images/buttonsmall_cancel.gif" alt="Cancel" border="0"
                                       onclick="this.form.methodToCall.value='cancel';"
                                       style="border: none;" /></td>
                        </tr>
                        
                        <tr>
						<td><c:if test="${Form.added}">
								<span style="color: red;">The status has been updated.</span>
							</c:if></td>
					</tr>
                        </tbody>
                    </table>

                </fieldset>
                  <fieldset>
                    <legend>
                        <i><b>Submit Button status for the campuses</b></i>
                    </legend>

                    <br />
                   
                    <br/>

                     <table id="delegate-table">
                        <thead>
                        <tr>
                            <th>Campus</th>
                            <th>Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${fn:length(Form.submitButtonList) < 1}">
                                <tr>
                                    <td>none</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="entry" items="${Form.submitButtonList}">
                                    <tr>
                                        <td>${entry.campusCode}</td>
                                        <td>
                                        <c:if test="${entry.activeFlag eq true}"> Active
                                        </c:if>
                                         <c:if test="${entry.activeFlag eq false}"> InActive
                                        </c:if>
                                        </td>
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
