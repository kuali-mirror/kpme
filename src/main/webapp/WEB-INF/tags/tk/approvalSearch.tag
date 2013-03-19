<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<%@ attribute name="calType" required="true" type="java.lang.String" %>
<%-- for time approval, set searchId to searchValue, for leave Approval, set searchId to leaveSearchValue
the id is used in approval.js--%>
<%@ attribute name="searchId" required="true" type="java.lang.String" %>


<table class="navigation">   
        <tr>
            <td class="left">
                Search By :
                <label for="search field">
                    <select id="searchField" name="searchField">
                        <option value="principalName">Principal Id</option>
                        <option value="documentId">Document Id</option>
                    </select>
                </label>
                Value :
                <label for="search value">
           			<input id="${searchId}" name="${searchId}" type="text" value="${Form.searchTerm}" placeholder="enter at least 3 chars" />
                    <span id='loading-value' style="display:none;"><img src='images/ajax-loader.gif'></span>
                    <input type="button" id='search' value="Search" class="ui-button ui-widget ui-state-default ui-corner-all" 
                           onclick="this.form.methodToCall.value='searchResult'; this.form.submit();" name="searchResult" />
                </label>
            </td>
            <td>
                <div style="text-align: center">
                    <c:if test="${Form.prevPayCalendarId ne null}">
                        <button id="nav_prev_ac" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only" role="button" title="Previous">
                            <span class="ui-button-text">Previous</span>
                        </button>
                        <%--<input type="button" class="prev" value="Previous" name="Previous"--%>
                               <%--onclick="this.form.hrPyCalendarEntriesId.value='${Form.prevPayCalendarId}'; this.form.methodToCall.value='loadApprovalTab'; this.form.submit();"/>--%>
                    </c:if>
                    <span id="payBeginDate" style="font-size: 1.5em; vertical-align: middle;"><fmt:formatDate
                            value="${Form.payBeginDate}" pattern="MM/dd/yyyy"/></span> -
                    <span id="payEndDate" style="font-size: 1.5em; vertical-align: middle;"><fmt:formatDate
                            value="${Form.payEndDate}" pattern="MM/dd/yyyy"/></span>
                    <c:if test="${Form.nextPayCalendarId ne null}">
                        <button id="nav_next_ac" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only" role="button" title="Next">
                            <span class="ui-button-text">Next</span>
                        </button>

                        <%--<input type="button" class="next" value="Next" name="Next"--%>
                               <%--onclick="this.form.hrPyCalendarEntriesId.value='${Form.nextPayCalendarId}'; this.form.methodToCall.value='loadApprovalTab'; this.form.submit();"/>--%>
                    </c:if>
                </div>
            </td>
            <td>
            	<tk:payCalendarSelect calType="${calType}" />
            </td>
            <td></td>
        </tr>
        <tr>
        	<td></td>
            <td align="center">
        	<c:if test="${!Form.onCurrentPeriod}" >
	        		<a href="${KualiForm.backLocation}?methodToCall=gotoCurrentPayPeriod"
	                  	 target="_self" id="cppLink">Go to Current Period</a>
        	</c:if>
            </td>
            <td align="center">
            	<c:choose>
            		<c:when test="${calType == 'payCalendar'}">
            			<fieldset style="width:75%;">
                    		<legend>Weekly Status</legend>
                    		<div>Pay Period Week Total: <span style="font-weight: bold;">bold</span></div>
                    		<div>FLSA Week Total: <span style="font-style: italic;">italics</span></div>
						</fieldset>
					</c:when>
					<c:when test="${calType == 'leaveCalendar'}">
                    	<fieldset style="width:75%;">
                        	<legend>Leave Request Status</legend>
                        	<div>Approved/Usage: <span class="approvals-approved">bold</span></div>
                        	<div>Planned/Defered: <span class="approvals-requested">italics</span></div>
                    	</fieldset>
                	</c:when>
                </c:choose>
            </td>
        </tr>
</table>