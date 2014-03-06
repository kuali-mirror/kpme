<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="day" required="true" type="org.kuali.kpme.core.calendar.web.CalendarDay" %>
<%-- display time blocks for the day --%>
<c:forEach var="block" items="${day.blockRenderers}" varStatus="status">
    <c:if test="${block.timeBlock.earnCode ne HrConstants.LUNCH_EARN_CODE}">
        <c:choose>
            <c:when test="${status.last}">
                <c:set var="last" value="last-event"/>
            </c:when>
            <c:otherwise>
                <c:set var="last" value=""/>
            </c:otherwise>
        </c:choose>

 		<c:set var="editableClass" value="event-title-false"/>
        <c:set var="timeBlockDivId" value="doNotShow_${block.timeBlock.tkTimeBlockId}" />
        <c:if test="${Form.docEditable && block.timeBlockEditable}">
        	<c:set var="editableClass" value="event-title-true"/>
            <c:set var="timeBlockDivId" value=""/>
        </c:if>

        <c:forEach var="action" items="${Form.actionMessages}">
            <c:if test="${fn:containsIgnoreCase(action,block.timeBlock.tkTimeBlockId)}">
                <c:set var="error" value="block-error"/>
            </c:if>
        </c:forEach>

        <div id="${timeBlockDivId}" class="approvals-table event ${last} ${block.assignmentClass} ${error}">
            <div id="timeblock_${block.timeBlock.tkTimeBlockId}"
            	 class="${editableClass}">
            	 <c:if test="${block.timeBlock.clockedByMissedPunch}">
 	 	 	 	 	<!-- <div class="missed-punch-marker">
 	 	 	 			<span class='icon-file2'/>
 	 	 	 		</div> -->
 	 	 	 		<div class="missed-punch-marker">
						<span id="approvals-missedpunch" class='approvals-missedpunch'>
							<span class="icon-file2"></span>
						</span>
					</div>
					<div id="approvals-missedpunch-details"
						class="approvals-missedpunch-details"
						style="display: none; float: right; margin-left: 20px;">
						<table>
							<thead>
								<tr>
									<th colspan="3"
										style="font-size: 1.2em; font-weight: bold; text-align: left; border-bottom: none;">
										Missed Punch:
									</th>
								</tr>
								<tr>
									<th>Date</th>
									<th>Clock Action</th>
									<th>Details</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td style="width: 250px;height: auto;">${block.timeBlock.actionDateTime}</td>
									<td style="width: 30px; height: auto;">${block.timeBlock.clockAction}</td>
									<td style="height: auto">
										<div class="warning-note-message">
                        					${block.timeBlock.assignmentValue}
											<br/>Doc Id: ${block.timeBlock.missedPunchDocId}
											&nbsp;Doc Status: ${block.timeBlock.missedPunchDocStatus}
									</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</c:if>
                <c:if test="${Form.docEditable && block.timeBlockEditable && block.deletable}">
                    <div><img id="timeblockDelete_${block.timeBlock.tkTimeBlockId}"
                              class='event-delete'
                              src='images/delete.png'/>
                    </div>
                </c:if>
                <c:choose>
                    <c:when test="${block.timeBlockEditable}">
                        <div id="show_${block.timeBlock.tkTimeBlockId}">${block.title}</div>
                    </c:when>
                    <c:otherwise>
                        <div>${block.title}</div>
                    </c:otherwise>
                </c:choose>
            </div>
                ${block.timeRange}
            <div>
                <c:if test="${block.earnCodeType ne HrConstants.EARN_CODE_AMOUNT}">
                    <c:forEach var="thdr" items="${block.detailRenderers}">
                        <c:if test="${thdr.title ne HrConstants.LUNCH_EARN_CODE}">
                            <div id="${thdr.timeHourDetail.tkTimeHourDetailId}"
                                 class="event-content">
                                <c:choose>
                                    <c:when test="${thdr.hours ne ''}">
                                        <c:set var="title" value="${thdr.title}"/>
                                        <c:choose>
                                            <c:when test="${thdr.overtimeEarnCode && Form.docEditable && block.timeBlock.overtimeEditable && !(thdr.title eq 'DOT')}">
                                                <c:set var="title"
                                                       value="<span id='overtime_${block.timeBlock.tkTimeBlockId}' class='overtime'>${thdr.title}</span>"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="title"
                                                       value="<span>${thdr.title}</span>"/>
                                            </c:otherwise>
                                        </c:choose>

                                        <c:if test="${thdr.title eq HrConstants.HOLIDAY_EARN_CODE}">
                                            <div><a id="holidayNameHelp"
                                                    title="${thdr.holidayName}"
                                                    style="color:white; cursor:pointer;">${title}
                                                - ${thdr.hours} hours</a></div>
                                        </c:if>
                                        <c:if test="${thdr.title ne HrConstants.HOLIDAY_EARN_CODE}">
                                            ${title} - ${thdr.hours} hours
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${thdr.title eq HrConstants.HOLIDAY_EARN_CODE}">
                                            <div><a id="holidayNameHelp"
                                                    title="${thdr.holidayName}"
                                                    style="color:white; cursor:pointer;">${thdr.title}
                                                - $${thdr.hours}</a></div>
                                        </c:if>
                                        <c:if test="${thdr.title ne HrConstants.HOLIDAY_EARN_CODE}">
                                            ${thdr.title} - $${thdr.hours}
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:if>
                    </c:forEach>
                </c:if>
            </div>
            <div class="lunch">
                <c:if test="${block.lunchLabel ne '' and block.deletable}">
                    <div><img id="lunchDelete_${block.lunchLabelId}"
                              class='event-delete'
                              src='images/delete.png'/>
                    </div>
                </c:if>
                    ${block.lunchLabel}
            </div>
            ${block.amount}
        </div>
    </c:if>
</c:forEach>

<%-- display leave blocks for the day --%>
<c:forEach var="block" items="${day.leaveBlockRenderers}" varStatus="status">
    <div class="event ${block.assignmentClass}">
		<c:set var="editableClass" value="event-title-false"/>
	    <c:if test="${Form.docEditable && block.leaveBlock.editable}">
	        <c:set var="editableClass" value="event-title-true"/>
	    </c:if>	
	    <div id="leaveblock_${block.leaveBlockId}" class="${editableClass}">
	        <c:if test="${block.deletable}">
	            <div><img id="leaveBlockDelete_${block.leaveBlockId}"
	                      class='event-delete'
	                      src='images/delete.png'/>
	            </div>
	        </c:if>
			<c:choose>
                <c:when test="${block.leaveBlock.editable}">
                    <div id="leaveShow_${block.leaveBlockId}">${block.assignmentTitle}</div>
                </c:when>
                <c:otherwise>
                    <div>${block.assignmentTitle}</div>
                </c:otherwise>
            </c:choose>
	    </div>
	    <div>
	    ${block.timeRange}
	    </div>
	    ${block.earnCode} (${(-1) * block.hours})
    </div>
</c:forEach>