<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search searchCriteria="chartOfAccounts,organization,account,name,managerSystemId,supervisorSystemId,subFundGroup,expirationStatus"
		resultColumns="account,name,chartOfAccounts,organization.organization,managerSystemId,supervisorSystemId,subFundGroup,expirationDate"/>
</hr:page>