<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp" %>

<%@ attribute name="cal" required="true" type="org.kuali.hr.time.calendar.TkCalendar" %>
<%@ attribute name="docId" required="true" type="java.lang.String" %>

<div id="tkCal" class="ui-widget cal" style="margin: 20px auto 20px auto; width:95%;">
    <%-- Add Paging Controls for moving between Calendars --%>
    <table class="cal-header">
        <tbody>
        <tr>
            <td>
                <c:if test="${Form.prevDocumentId ne null}">
                    <button id="nav_prev" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only"
                            role="button" title="Previous">
                        <span class="ui-button-icon-primary ui-icon ui-icon-circle-triangle-w"></span>
                        <span class="ui-button-text">Previous</span>
                    </button>
                </c:if>
                <span class="header-title">${cal.calendarTitle}</span>
                <c:if test="${Form.nextDocumentId ne null}">
                    <button id="nav_next" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only"
                            role="button" title="Next">
                        <span class="ui-button-icon-primary ui-icon ui-icon-circle-triangle-e"></span>
                        <span class="ui-button-text">Next</span>
                    </button>
                </c:if>
            </td>
        </tr>
        <tr>
        <tr>
            <td align="right">
                <a href="${KualiForm.backLocation}?methodToCall=actualTimeInquiry&documentId=${Form.documentId}"
                   target="_blank" id="atiLink">Actual Time Inquiry</a>
            </td>

        </tr>
        </tr>
        </tbody>
    </table>

    <div class="global-error">
        <c:forEach var="warning" items="${Form.warnings}">
            ${warning} <br/>
        </c:forEach>
    </div>

    <div id="tkCalContent">
        <table class="cal-table">
            <thead>
            <%-- Render Day Labels, starting at FLSA Start day --%>
            <tr>
                <c:forEach var="dayString" items="${cal.calendarDayHeadings}">
                    <th class="ui-state-default week-header">${dayString}</th>
                </c:forEach>
            </tr>
            </thead>

            <tbody>
            <%-- Generate Each Week --%>
            <c:forEach var="week" items="${cal.weeks}" varStatus="rowS">
                <tr>
                        <%-- Generate Each Day --%>
                    <c:forEach var="day" items="${week.days}" varStatus="dayS">

                        <c:set var="dayStyle" value="width:14%;"/>
                        <c:set var="dayId" value="day_${day.dayNumberDelta}"/>
                        <c:if test="${day.gray}">
                            <c:set var="dayStyle" value="width:14%; background: rgb(224, 235, 225);"/>
                            <c:set var="dayId" value="gray_day"/>
                        </c:if>


                        <td id="${dayId}" class="ui-state-default" style="${dayStyle}">
                                <%-- Day Number --%>
                            <div class="day-number">${day.dayNumberString}</div>
                                <%-- Render the Time Blocks --%>
                            <div>
                                <c:forEach var="block" items="${day.blockRenderers}" varStatus="status">
                                    <c:if test="${block.timeBlock.earnCode ne TkConstants.LUNCH_EARN_CODE}">
                                        <c:choose>
                                            <c:when test="${status.last}">
                                                <c:set var="last" value="last-event"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="last" value=""/>
                                            </c:otherwise>
                                        </c:choose>


                                        <div class="event ${last} ${block.assignmentClass}">
                                            <c:set var="editableClass" value="event-title-false"/>
                                            <c:if test="${Form.docEditable}">
                                                <c:set var="editableClass" value="event-title-true"/>
                                            </c:if>

                                            <div id="timeblock_${block.timeBlock.tkTimeBlockId}"
                                                 class="${editableClass}">
                                                <c:if test="${block.timeBlock.deleteable}">
                                                    <div><img id="timeblockDelete_${block.timeBlock.tkTimeBlockId}"
                                                              class='event-delete'
                                                              src='images/delete.png'/>
                                                    </div>
                                                </c:if>

                                                <div id="show_${block.timeBlock.tkTimeBlockId}">${block.title}</div>
                                            </div>
                                                ${block.timeRange}
                                            <div>
                                                <c:if test="${block.earnCodeType ne TkConstants.EARN_CODE_AMOUNT}">
                                                    <c:forEach var="thdr" items="${block.detailRenderers}">
                                                        <c:if test="${thdr.title ne TkConstants.LUNCH_EARN_CODE}">
                                                            <div id="${thdr.timeHourDetail.tkTimeHourDetailId}"
                                                                 class="event-content">
                                                                <c:choose>
                                                                    <c:when test="${thdr.hours ne ''}">
                                                                        <c:set var="title" value="${thdr.title}"/>
                                                                        <c:choose>
                                                                            <c:when test="${thdr.overtimeEarnCode and block.timeBlock.overtimeEditable and !(thdr.title eq 'DOT')}">
                                                                                <c:set var="title"
                                                                                       value="<span id='overtime_${block.timeBlock.tkTimeBlockId}' class='overtime'>${thdr.title}</span>"/>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <c:set var="title"
                                                                                       value="<span>${thdr.title}</span>"/>
                                                                            </c:otherwise>
                                                                        </c:choose>

                                                                        <c:if test="${thdr.title eq TkConstants.HOLIDAY_EARN_CODE}">
                                                                            <div><a id="holidayNameHelp"
                                                                                    title="${thdr.holidayName}"
                                                                                    style="color:white; cursor:pointer;">${title}
                                                                                - ${thdr.hours} hours</a></div>
                                                                        </c:if>
                                                                        <c:if test="${thdr.title ne TkConstants.HOLIDAY_EARN_CODE}">
                                                                            ${title} - ${thdr.hours} hours
                                                                        </c:if>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <c:if test="${thdr.title eq TkConstants.HOLIDAY_EARN_CODE}">
                                                                            <div><a id="holidayNameHelp"
                                                                                    title="${thdr.holidayName}"
                                                                                    style="color:white; cursor:pointer;">${thdr.title}
                                                                                - $${thdr.hours}</a></div>
                                                                        </c:if>
                                                                        <c:if test="${thdr.title ne TkConstants.HOLIDAY_EARN_CODE}">
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
                                                <c:if test="${block.lunchLabel ne '' and block.timeBlock.deleteable}">
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
                                <div class="create"
                                     id="${day.dateString}"></div>
                            </div>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>