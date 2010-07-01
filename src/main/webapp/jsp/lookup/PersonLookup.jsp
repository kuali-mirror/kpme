<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search searchCriteria="universityId,lastName,firstName,nationalId,birthdate"
		resultColumns="universityId,primaryFirstName,primaryMiddleName,primaryLastName,preferredFirstName,preferredMiddleName,preferredLastName"/>
</hr:page>