<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search 
		searchCriteria="universityId,employeeRecord,effectiveDate,department,businessUnit,paygroup"
		resultColumns="universityId,employeeRecord,effectiveDate,department"
	/>
</hr:page>