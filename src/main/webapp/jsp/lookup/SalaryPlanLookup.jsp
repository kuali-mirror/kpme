<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search hiddenFields="businessUnits"
		searchCriteria="setid,salaryPlan,description"
		resultColumns="salaryPlan,setid,description"/>
</hr:page>