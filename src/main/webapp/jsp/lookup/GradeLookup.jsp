<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search hiddenFields="businessUnits,salaryPlans"
		searchCriteria="setid,description,salaryPlan,grade"
		resultColumns="setid,effectiveDate,description,salaryPlan,grade"/>
</hr:page>