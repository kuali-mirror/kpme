<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<c:set var="Form" value="${EdoPTReportForm}" scope="request" />

<edo:edoPageLayout>
    <edo:edoHeader></edo:edoHeader>

	<div style="width: 100%;">

        <edo:edoTreeNav />
        <div class="content">
        <h2>Promotion and Tenure Summary Report</h2>        
        <br style="clear: both;" />
        
			<html:form action="/EdoPTReport" method="post" enctype="multipart/form-data">
				<html:hidden property="methodToCall"/>
				<html:hidden property="tabId"/>

                <c:if test="${!empty ErrorPropertyList}">
                    <div class="error">
                        <kul:errors keyMatch="error" errorTitle="Errors:" />
                        <br />
                    </div>
                </c:if>

				<fieldset>
					<legend>
						<i><b>Search Dossiers</b></i>
					</legend>
					<table>
						<tr>
							<td>Workflow :</td>
							<td>
								<html:select property="workflowId">
									<html:options name="EdoForm" property="workflows"/>
				                </html:select>
		                	</td>
		                </tr>
						<tr>
							<td>Dossier Type :</td>
							<td>
								<html:select property="dossierTypeName">
									<html:option value=""></html:option>
									<html:options name="EdoForm" property="dossierTypes"/>
				                </html:select>
		                	</td>
		                </tr>
						<tr>
							<td>Vote Round :</td>
							<td>
								<html:select property="voteRoundCode">
									<html:option value=""></html:option>
									<html:optionsCollection name="EdoForm" property="voteRounds" label="value" value="key"/>
				                </html:select>
		                	</td>
		                </tr>
		                <tr>
		                	<td>Primary Unit :</td>
		                	<td>
								<html:select property="departmentId">
									<html:option value=""></html:option>
									<html:options name="EdoForm" property="departments"/>
				                </html:select>
		                	</td>
		                </tr>
		                <tr>
		                	<td>School :</td>
		                	<td>
								<html:select property="schoolId">
									<html:option value=""></html:option>
									<html:options name="EdoForm" property="schools"/>
				                </html:select>
		                	</td>
		                </tr>
		                <tr>
		                	<td>
								<input type="image" alt="Search" name="search"
									src="images/buttonsmall_search.gif"
									onclick="this.form.methodToCall.value='search';"
									style="border: none; vertical-align: middle;" />
							</td>
						</tr>
					</table>
				</fieldset>
			
			
			</html:form>
        
        <br style="clear: both;" />
        
        <edo:jq_ptreporttable_init />
        <table id="pt_report"></table>		

        </div>
        
        <br style="clear: both;" />        
    </div>
    <edo:edoFooter/>
</edo:edoPageLayout>
