<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search hiddenFields="businessUnits"
		searchCriteria="setid,department,description"
		resultColumns="department,effectiveDate,description,location"/>
</hr:page>