<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search hiddenFields="businessUnits,departments,salaryPlans,positionTypes,positionStatuses"
		searchCriteria="position,description,businessUnit,department,jobcode,salaryPlan,positionType,positionStatus"
		resultColumns="position,effectiveDate,description,salaryPlan.extension.positionType,businessUnit,department,jobcode.jobcode,salaryPlan.salaryPlan,jobcode.grade"/>
</hr:page>