<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search searchCriteria="chartOfAccounts,account,subAccount,name,type,costShareSourceAccount"
		resultColumns="subAccount,name,parent.chartOfAccounts,parent.account,extension.type,extension.costShareSourceAccount"/>
</hr:page>