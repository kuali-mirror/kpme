<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search hiddenFields="businessUnits,salaryPlans,grades,censusCodes,jobFamilies"
		searchCriteria="setid,jobcode,description,salaryPlan,grade,censusCode,jobFamily"
		resultColumns="jobcode,setid,effectiveDate,description,salaryPlan,grade,extension.censusCode,jobFamily"/>
</hr:page>