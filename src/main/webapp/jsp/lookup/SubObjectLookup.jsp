<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search hiddenFields="objects"
	    searchCriteria="chartOfAccounts,account,object,fiscalYear,subObject,name"
		resultColumns="subObject,name,chartOfAccounts,account,object,fiscalYear"/>
</hr:page>