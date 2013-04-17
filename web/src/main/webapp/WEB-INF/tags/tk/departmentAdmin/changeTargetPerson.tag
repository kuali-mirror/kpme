<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<div class="portlet">
    <div class="header">Change Target Person</div>
    <div class="body">
        <html:form action="/changeTargetPerson" method="post" style="margin:0; display:inline">
            <html:text property="principalName" size="20" />
            <kul:lookup boClassName="org.kuali.rice.kim.impl.identity.PersonImpl" fieldConversions="principalName:principalName" />
            <html:submit property="methodToCall.changeTargetPerson" value="Submit" />
            <html:submit property="methodToCall.clearTargetPerson" value="Clear" />
        </html:form>
    </div>
</div>