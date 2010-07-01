<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search searchCriteria="location,positionType,documentType,salaryPlan"
		resultColumns="location,positionType,documentType,salaryPlan,effectiveDate,effectiveSequence,active,workgroup"/>
</hr:page>