<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search searchCriteria="chartOfAccounts,organization,project,name,managerSystemId"
		resultColumns="project,name,organization.chartOfAccounts,organization.organization,managerSystemId"/>
</hr:page>