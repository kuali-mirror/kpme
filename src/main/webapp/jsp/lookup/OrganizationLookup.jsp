<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search searchCriteria="chartOfAccounts,organization,responsibilityCenter,name,type,reportsToChartOfAccounts,reportsToOrganization,managerSystemId"
		resultColumns="organization,chartOfAccounts,responsibilityCenter,name,type,reportsToOrganization.chartOfAccounts,reportsToOrganization.organization,managerSystemId"/>
</hr:page>
