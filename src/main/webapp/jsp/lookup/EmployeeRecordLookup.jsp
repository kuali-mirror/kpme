<%@ page language="java"%>
<%@ taglib tagdir="/WEB-INF/tags/shared" prefix="hr"%>

<hr:page>
	<hr:search 
		hiddenFields="onlyInTimeDepartments,contractEmployee,compensationRateShown,jobIndicatorShown,effectiveSequenceShown"
		searchCriteria="universityId,employeeRecord,effectiveDate,department,businessUnit,paygroup"
		resultColumns="${StrutsActionForm.resultColumns}"
	/>
</hr:page>