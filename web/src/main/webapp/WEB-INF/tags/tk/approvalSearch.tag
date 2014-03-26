<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<%@ attribute name="calType" required="true" type="java.lang.String"%>
<%-- for time approval, set searchId to searchValue, for leave Approval, set searchId to leaveSearchValue
the id is used in approval.js--%>
<%@ attribute name="searchId" required="true" type="java.lang.String"%>

<c:choose>
	<c:when test="${calType eq 'payCalendar'}">
		<c:set var="calendarLocation" value="TimeApproval.do" />
	</c:when>
	<c:when test="${calType eq 'leaveCalendar'}">
		<c:set var="calendarLocation" value="LeaveApproval.do" />
	</c:when>
	<c:otherwise>
		<c:set var="calendarLocation" value="" />
	</c:otherwise>
</c:choose>

<fieldset style="width: 97%;">
	<legend>Search</legend>
	<table class="navigation">
		<tr>
			<td class="left">Search By : <label for="search field">
					<select id="searchField" name="searchField">
						<c:choose>
							<c:when test="${Form.searchField eq 'principalName'}">
								<option value="principalName" selected="selected">Principal
									Id</option>
							</c:when>
							<c:otherwise>
								<option value="principalName">Principal Id</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${Form.searchField eq 'documentId'}">
								<option value="documentId" selected="selected">Document
									Id</option>
							</c:when>
							<c:otherwise>
								<option value="documentId">Document Id</option>
							</c:otherwise>
						</c:choose>
				</select>
			</label>
			</td>
			<td align="left">Value : <label for="search value"> <input
					id="${searchId}" name="${searchId}" type="text"
					value="${Form.searchTerm}" placeholder="enter at least 3 chars" />
					<span id='loading-value' style="display: none;"><img
						src='images/ajax-loader.gif'></span>

			</label>
			</td>
			<td><input type="button" id='search' value="Search"
				class="ui-button ui-widget ui-state-default ui-corner-all"
				onclick="this.form.methodToCall.value='searchResult'; this.form.submit();"
				name="searchResult" /> <input type="button" id='reset'
				value="Reset"
				class="ui-button ui-widget ui-state-default ui-corner-all"
				onclick="this.form.methodToCall.value='resetSearch'; this.form.submit();"
				name="resetSearch" /></td>
		</tr>

	</table>
</fieldset>