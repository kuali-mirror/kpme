<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search hiddenFields="departments"
	    searchCriteria="workArea,department,description"
		resultColumns="workArea,effectiveDate,description,department,employeeType"/>
</hr:page>