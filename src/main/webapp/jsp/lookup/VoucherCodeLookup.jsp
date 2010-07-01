<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search 
		searchCriteria="department,voucherCode,description"
		resultColumns="voucherCode,effectiveDate,department,description"/>
</hr:page>