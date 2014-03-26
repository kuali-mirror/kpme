<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<div class="portlet">
    <div class="header">Change Target Person</div>
    <div class="body">
     <html:form action="/changeTargetPerson" method="post" style="margin:0; display:inline">
	            <table>
                        <tr>
                            <td>Principal Name:</td>
                            <td>
                                <html:text property="principalName" size="20" />
                                <input type="hidden" name="fromAction" value="DepartmentAdmin.do"/>
					            <kul:lookup boClassName="org.kuali.rice.kim.impl.identity.PersonImpl" fieldConversions="principalName:principalName" />
                            </td>
                        </tr>
                 </table>
	            
	            <html:submit property="methodToCall.changeTargetPerson" value="Submit" />
	            <html:submit property="methodToCall.clearTargetPerson" value="Clear" />
	            <br/>
	            
	  </html:form>
	  <div style="color: red">
	  ${ChangeTargetPersonForm.message}
	  </div>
    </div>
</div>

