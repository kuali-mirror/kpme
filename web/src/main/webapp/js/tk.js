/*
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Datejs date/time format:
 *  Format  Description                                                                  Example
    ------  ---------------------------------------------------------------------------  -----------------------
    s      The seconds of the minute between 0-59.                                      "0" to "59"
    ss     The seconds of the minute with leading zero if required.                     "00" to "59"

    m      The minute of the hour between 0-59.                                         "0"  or "59"
    mm     The minute of the hour with leading zero if required.                        "00" or "59"

    h      The hour of the day between 1-12.                                            "1"  to "12"
    hh     The hour of the day with leading zero if required.                           "01" to "12"

    H      The hour of the day between 0-23.                                            "0"  to "23"
    HH     The hour of the day with leading zero if required.                           "00" to "23"

    d      The day of the month between 1 and 31.                                       "1"  to "31"
    dd     The day of the month with leading zero if required.                          "01" to "31"
    ddd    Abbreviated day name. Date.CultureInfo.abbreviatedDayNames.                  "Mon" to "Sun"
    dddd   The full day name. Date.CultureInfo.dayNames.                                "Monday" to "Sunday"

    M      The month of the year between 1-12.                                          "1" to "12"
    MM     The month of the year with leading zero if required.                         "01" to "12"
    MMM    Abbreviated month name. Date.CultureInfo.abbreviatedMonthNames.              "Jan" to "Dec"
    MMMM   The full month name. Date.CultureInfo.monthNames.                            "January" to "December"

    yy     The year as a two-digit number.                                              "99" or "08"
    yyyy   The full four digit year.                                                    "1999" or "2008"

    t      Displays the first character of the A.M./P.M. designator.                    "A" or "P"
         $C.amDesignator or Date.CultureInfo.pmDesignator
    tt     Displays the A.M./P.M. designator.                                           "AM" or "PM"
         $C.amDesignator or Date.CultureInfo.pmDesignator

    S      The ordinal suffix ("st, "nd", "rd" or "th") of the current day.            "st, "nd", "rd" or "th"

    Format  Description                                                                  Example ("en-US")
    ------  ---------------------------------------------------------------------------  -----------------------
    d      The CultureInfo shortDate Format Pattern                                     "M/d/yyyy"
    D      The CultureInfo longDate Format Pattern                                      "dddd, MMMM dd, yyyy"
    F      The CultureInfo fullDateTime Format Pattern                                  "dddd, MMMM dd, yyyy h:mm:ss tt"
    m      The CultureInfo monthDay Format Pattern                                      "MMMM dd"
    r      The CultureInfo rfc1123 Format Pattern                                       "ddd, dd MMM yyyy HH:mm:ss GMT"
    s      The CultureInfo sortableDateTime Format Pattern                              "yyyy-MM-ddTHH:mm:ss"
    t      The CultureInfo shortTime Format Pattern                                     "h:mm tt"
    T      The CultureInfo longTime Format Pattern                                      "h:mm:ss tt"
    u      The CultureInfo universalSortableDateTime Format Pattern                     "yyyy-MM-dd HH:mm:ssZ"
    y      The CultureInfo yearMonth Format Pattern                                     "MMMM, yyyy"
 *
 */

// CONSTANTS
var CONSTANTS = {
    OVERTIME_EARNCODE :{
        DAILY : "DOT",
        OVERTIME : "OVT"
    },
    EARNCODE_TYPE : {
        HOUR: "H",
        TIME: "T",
        AMOUNT: "A"
    },
    EARNCODE_UNIT: {
    	HOUR: "H",
    	DAY: "D",
    	TIME: "T"
    },
    ACTIONS : {
        UPDATE_TIME_BLOCK : "updateTimeBlock",
        ADD_TIME_BLOCK: "addTimeBlock",
        ADD_LEAVE_BLOCK: "addLeaveBlock",
        UPDATE_LEAVE_BLOCK: "updateLeaveBlock"
    },
    TIME_FORMAT : {
        DATE_FOR_OUTPUT : 'M/d/yyyy',
        TIME_FOR_VALIDATION: ['hh:mm tt', 'hh:mmtt', 'h:mm tt', 'h:mmtt', 'hh:mmt', 'h:mmt', 'HH:mm', 'HHmm', 'H:mm', 'h tt', 'htt', 'Hmm', 'ht'],
        TIME_FOR_OUTPUT : 'hh:mm tt',
        TIME_FOR_SYSTEM : 'H:mm'
    }
}

$(document).ready(function() {
    // tabs
    /**
     * This is the default tab function provided by jQuery
     */
    // var $tabs = $('#tabs').tabs({ selected: 4});
    /**
     * the default tab function provided by jQuery doesn't work well for us.
     * It works very well if you have all the contents in the same page or load contents through ajax.
     * Since we want the direct link for each tab, the function below takes advantages from the jQuery css theme/
     *
     * Noted that round corners for the tabs and the panel work in every browser except IE 7 and 8.
     */
    $("#tabs li").hover(function() {
        $(this).addClass("ui-state-hover");
    }, function() {
        $(this).removeClass("ui-state-hover");
    });

    var tabId = $("#tabId").val();
    if (tabId != undefined) {
        $("#tab-section > #" + tabId)
                .addClass("ui-tabs-selected ui-state-focus ui-state-active");
    }
    // end of tab

    // buttons
    $('.button').button();

    $('button').button({
        icons : {
            primary : 'ui-icon-help'
        },
        text : false
    });

    $('.expand').button({
        icons: {
            primary: 'ui-icon-plus'
        }
    });

    var prevDocumentId = $('input[name=prevDocumentId]').val();
    var nextDocumentId = $('input[name=nextDocumentId]').val();
    var prevHrCalendarEntryId = $('input[name=prevHrCalendarEntryId]').val();
    var nextHrCalendarEntryId = $('input[name=nextHrCalendarEntryId]').val();

    // create navigation buttons for timesheet
    $('#nav_prev').button({
        icons: {
            primary: "ui-icon-triangle-1-w"
        },
        text: false
    });

    $('#nav_prev').click(function() {
        this.form.documentId.value = prevDocumentId;
        this.form.submit();
    });

    $('#nav_next').button({
        icons: {
            primary: "ui-icon-triangle-1-e"
        },
        text: false
    });

    $('#nav_next').click(function() {
        this.form.documentId.value = nextDocumentId;
        this.form.submit();
    });
    
    // create navigation buttons for leave calendar    
    $('#nav_prev_lc').button({
        icons: {
            primary: "ui-icon-triangle-1-w"
        },
        text: false
    });

    $('#nav_prev_lc').click(function() {
        this.form.documentId.value = prevDocumentId;
        this.form.hrCalendarEntryId.value = prevHrCalendarEntryId;
        this.form.submit();
    });

    $('#nav_next_lc').button({
        icons: {
            primary: "ui-icon-triangle-1-e"
        },
        text: false
    });

    $('#nav_next_lc').click(function() {
        this.form.documentId.value = nextDocumentId;
        this.form.hrCalendarEntryId.value = nextHrCalendarEntryId;
        this.form.submit();
    });

    //create navigation for approval tab
   $('#nav_prev_ac').button({
        icons: {
            primary: "ui-icon-triangle-1-w"
        },
        text: false
    });

    $('#nav_prev_ac').click(function() {
        this.form.hrCalendarEntryId.value = prevHrCalendarEntryId;
        this.form.submit();
    });

    $('#nav_next_ac').button({
        icons: {
            primary: "ui-icon-triangle-1-e"
        },
        text: false
    });

    $('#nav_next_ac').click(function() {
        this.form.hrCalendarEntryId.value = nextHrCalendarEntryId;
        this.form.submit();
    });


    // create navigation buttons for leave block display
    $('#nav_lb_prev').button({
        icons: {
            primary: "ui-icon-triangle-1-w"
        },
        text: false
    });


    $('#nav_lb_next').button({
        icons: {
            primary: "ui-icon-triangle-1-e"
        },
        text: false
    });

   

    // datepicker
    $('#startDate, #endDate').datepicker({
        changeMonth : true,
        changeYear : true,
        showOn : 'button',
        showAnim : 'fadeIn',
        buttonImage : 'kr/static/images/cal.gif',
        buttonImageOnly : true,
        buttonText : 'Select a date',
        showButtonPanel : true,
        //numberOfMonths : 2,
        // set default month based on the current browsing month
        // appendText : '<br/>format: mm/dd/yyyy',
        constrainInput : true,
        minDate : new Date($('#beginPeriodDate').val()),
        maxDate : new Date($('#endPeriodDate').val())
    });
    
    //KPME-1542
    $("#startdatepicker, #enddatepicker").datepicker();
    
    // hide the date picker by default
    // https://jira.kuali.org/browse/KPME-395
    $('#ui-datepicker-div').css('display', 'none');

    // clock
    var currentServerTime = parseFloat($("#currentServerTime").val());
    var currentUserUTCOffset = parseFloat($("#currentUserUTCOffset").val());
    var lastClockedInTime = $("#lastClockedInTime").val();
    var clockAction = $("#clockAction").val();
    
    var options = {
        format : '%I:%M:%S %p',
        utc: true,
        utc_offset: currentUserUTCOffset
    };
    $(".jClock").jclock(options);

    var startTime = clockAction == 'CO' ? new Date(lastClockedInTime) : new Date(currentServerTime);

    $('.elapsedTime').countdown({
        since : startTime,
        compact : true,
        format : 'dHMS',
        description : ''
    })


    // tooltip
    // http://flowplayer.org/tools/tooltip/index.html
    $(" .holidayNameHelp").tooltip({ effect: 'slide'});

    $(".beginTimeHelp, .endTimeHelp").tooltip({

        // place tooltip on the right edge
        position : "center right",

        // a little tweaking of the position
        offset : [-2, 10],

        // use the built-in fadeIn/fadeOut effect
        effect : "fade",

        // custom opacity setting
        opacity : 0.7,

        fadeInSpeed : 500
    });

    // person detail accordion
    $("#person-detail-accordion").accordion({
        collapsible : true,
        active : 0,
        autoHeight: false
    });

    // apply time entry widget to the tabular view
    $(".timesheet-table-week1 :input, .timesheet-table-week2 :input").blur(
            function() {
            	validateTime(this);
            }).focus(function() {
                if (this.className != 'error') this.select();
            });

    $("#tblNewTimeBlocks").on("change", ".assignmentRow", function() {
        $(this).removeClass('ui-state-error');
        cleanTips();
    });
    
    $("#tblNewTimeBlocks").on("change", ".bdRow, .edRow", function() {
        $(this).removeClass('ui-state-error');
        recalculateHrs($(this).closest("tr").index() + 1);
    });

    $("#tblNewTimeBlocks").on("change", ".btRow, .etRow", function() {
        $(this).removeClass('ui-state-error');
        cleanTips();
        formatTime($(this));
        recalculateHrs($(this).closest("tr").index() + 1);
    });

    $('#saveTimeBlock').click(function() {
        var validFlag = true;
        var validation = $('#validation');
        var tbl = document.getElementById('tblNewTimeBlocks');
        var rowLength = tbl.rows.length;
        var assignValueCol = '';
        var beginDateCol = '';
        var endDateCol = '';
        var beginTimeCol = '';
        var endTimeCol = '';
        var hrsCol = '';
        var valueSeperator = '****';
        var timeBlockId = $("#tbId").val();
        var originalHrs = $("#originHrs").val();
        var lunchDeductionHours = $("#lunchDeductionHours").val();
        var tsDocId = $("#tsDocId").val();


        var errorMsgs = '';
        var totalHrs = 0;
        if (rowLength <= 2) {
            updateTips("Please use Add button to add entries or click Cancel to close the window.");
            return false;
        }
        var form1 = document.forms[0];
        var originalEndDateTime = new Date(form1.endTimestamp.value);
        var originalBeginDateTime = new Date(form1.beginTimestamp.value);
        var previousAssignValue = '';
        for (var i = 1; i < rowLength - 1; i++) {
            var assignValue = $("#tblNewTimeBlocks tbody tr:nth-child(" + i + ") td .assignmentRow").val();
            var beginDate = $("#tblNewTimeBlocks tbody tr:nth-child(" + i + ") td .bdRow").val();
            var endDate = $("#tblNewTimeBlocks tbody tr:nth-child(" + i + ") td .edRow").val();
            var beginTime = $("#tblNewTimeBlocks tbody tr:nth-child(" + i + ") td .btRow").val();
            var endTime = $("#tblNewTimeBlocks tbody tr:nth-child(" + i + ") td .etRow").val();
            var hrs = $("#tblNewTimeBlocks tbody tr:nth-child(" + i + ") td .hrRow").val();
            
            if(!checkAdjacentAssignments($("#tblNewTimeBlocks tbody tr:nth-child(" + i + ") td .assignmentRow"), assignValue, previousAssignValue)) {
            	return false;
            }
            previousAssignValue = assignValue;
            
            assignValueCol += assignValue + valueSeperator;
            beginDateCol += beginDate + valueSeperator;
            endDateCol += endDate + valueSeperator;
            hrsCol += hrs + valueSeperator;

            validFlag &= checkLength($("#tblNewTimeBlocks tbody tr:nth-child(" + i + ") td .bdRow"), "Date/Time", 10, 10);
            validFlag &= checkLength($("#tblNewTimeBlocks tbody tr:nth-child(" + i + ") td .edRow"), "Date/Time", 10, 10);
            validFlag &= checkLength($("#tblNewTimeBlocks tbody tr:nth-child(" + i + ") td .btRow"), "Date/Time", 8, 8);
            validFlag &= checkLength($("#tblNewTimeBlocks tbody tr:nth-child(" + i + ") td .etRow"), "Date/Time", 8, 8);

            if (validFlag) {
                var dateString = beginDate + ' ' + beginTime;
                var beginTimeTemp = new Date(dateString);
                var bTimeFormated = beginTimeTemp.getHours() + ':' + beginTimeTemp.getMinutes();
                // new tbs should not go beyond the original begin and end Date/Time
			    // beginTimeTemp.setSeconds(beginTimeTemp.getSeconds() + originalBeginDateTime.getSeconds());
			      
			    aString = form1.beginDateOnly.value + ' ' + form1.beginTimeOnly.value;
			    var formBeginDate = new Date(aString);
	            
	//                if (compareBeginDate < originalBeginDateTime) {
	    		if (beginTimeTemp < formBeginDate) {
	              updateTips("Begin Date/Time for item " + i + " goes beyond the original Begin Date/Time");
	              return false;
                }

                dateString = endDate + ' ' + endTime;
                var endTimeTemp = new Date(dateString);
                
                dateString = form1.endDateOnly.value + ' ' + form1.endTimeOnly.value;
                var formEndDate = new Date(dateString);
//                if (endTimeTemp > originalEndDateTime) {
                if (endTimeTemp > formEndDate) {
                    updateTips("End Date/Time for item " + i + " goes beyond the original End Date/Time");
                    return false;
                }

                var eTimeFormated = endTimeTemp.getHours() + ':' + endTimeTemp.getMinutes();
                endTimeCol += eTimeFormated + valueSeperator;

                if (i == rowLength - 2) { // endTime of last row should include seconds of originalEndTime
                    var eTimeFormated = endDate + ' ' + endTimeTemp.getHours() + ':' + endTimeTemp.getMinutes() + ':' + originalEndDateTime.getSeconds();
                    var newEndDate = new Date(eTimeFormated);
                    var hrsDifferent = newEndDate - beginTimeTemp;
                } else if (i == 1) { // beginTime of first row should use begin time plus seconds from originalBeginTime
                    var bTime = beginDate + ' ' + beginTimeTemp.getHours() + ':' + beginTimeTemp.getMinutes() + ':' + originalBeginDateTime.getSeconds();
                    var newBeginDate = new Date(bTime);
                    var hrsDifferent = endTimeTemp - newBeginDate;
                    var bTimeFormated = beginTimeTemp.getHours() + ':' + beginTimeTemp.getMinutes() + ':' + originalBeginDateTime.getSeconds();
                } else {
                    var hrsDifferent = endTimeTemp - beginTimeTemp;
                }

                beginTimeCol += bTimeFormated + valueSeperator;

                if (hrsDifferent <= 0) {
                    updateTips("Hours for item " + i + "not valid");
                    var hrs = hrsDifferent / 3600000;
                    $("#tblNewTimeBlocks tbody tr:nth-child(" + i + ") td .hrRow").val(hrs);
                    return false;
                }
                var hrs = Math.round(hrsDifferent * 100 / 3600000) / 100;
                $("#tblNewTimeBlocks tbody tr:nth-child(" + i + ") td .hrRow").val(hrs);
                totalHrs += parseFloat(hrs);
            }// end of if
            else {
                return false;
            }

        }// end of for loop
        
        totalHrs += parseFloat(lunchDeductionHours);
        totalHrs = totalHrs.toFixed(2);
        if (totalHrs != originalHrs) {
            updateTips("Total Hours entered not equal to the hours of the original time block");
            return false;
        }

        // for form submit
        $("#newAssignDesCol").val(assignValueCol);
        $("#newBDCol").val(beginDateCol);
        $("#newEDCol").val(endDateCol);
        $("#newBTCol").val(beginTimeCol);
        $("#newETCol").val(endTimeCol);
        $("#newHrsCol").val(hrsCol);
        $("#tbId").val(timeBlockId);
        $("#tsDocId").val(tsDocId);

        var params = {};
        params['newAssignDesCol'] = assignValueCol;
        params['newBDCol'] = beginDateCol;
        params['newEDCol'] = endDateCol;
        params['newBTCol'] = beginTimeCol;
        params['newETCol'] = endTimeCol;
        params['newHrsCol'] = hrsCol;
        params['tbId'] = timeBlockId;
        params['tsDocId'] = tsDocId;
        
        $.ajax({
            url: "Clock.do?methodToCall=validateNewTimeBlock",
            data: params,
            cache: false,
            success: function(data) {
                var json = jQuery.parseJSON(data);
                // if there is no error message, submit the form and save the new time blocks
                if (json == null || json.length == 0) {
                    $.ajax({
                        url: "Clock.do?methodToCall=saveNewTimeBlocks",
                        data: params,
                        cache: false,
                        success: function(data) {
                            // save is successful, close the window
                            window.close();
                            return false;
                        },
                        error: function() {
                            updateTips("Cannot save the time blocks");
                            return false;
                        }

                    });
                } else {
                    // if there is any error, grab error messages (json) and put them in the error message
                    var json = jQuery.parseJSON(data);
                    $.each(json, function (index) {
                        errorMsgs += "Error : " + json[index] + "\n";
                    });
                    updateTips(errorMsgs);
                    return false;
                }
            },
            error: function() {
                updateTips("Validation failed");
                return false;
            }
        })

    });

    // submit the form for changing the ip address in the batch job page
    $('.changeIpAddress').click(function() {
        var value = $(this).siblings().val().split("_");
        var ipToChange = value[0];
        var tkBatchJobEntryId = value[1];

        window.location = "batchJob.do?methodToCall=changeIpAddress&ipToChange=" + ipToChange + "&tkBatchJobEntryId=" + tkBatchJobEntryId;
    });


    $('#pay_period_prev').button({
        icons: {
            primary: 'ui-icon-circle-triangle-w'
        },
        text: false
    });

    $('#pay_period_next').button({
        icons: {
            primary: "ui-icon-circle-triangle-e"
        },
        text: false
    });
    
    // Leave Request page accordions
    
    // Planned Request
    $("#leave-planned-request").accordion({
        collapsible : true,
        active : 0,
        autoHeight: false
    });

    // Pending Request
    $("#leave-pending-request").accordion({
        collapsible : true,
        active : 0,
        autoHeight: false
    });

    // Approved Request
    $("#leave-approved-request").accordion({
        collapsible : true,
        active : 0,
        autoHeight: false
    });

    // Disapproved Request
    $("#leave-disapproved-request").accordion({
        collapsible : true,
        active : 0,
        autoHeight: false
    });
    
    // Leave Block Display
    $("#leave-block-display-accordion").accordion({
        collapsible : true,
        active : 0,
        autoHeight: false
    });

});

$.fn.parseTime = function() {
    var parsedTime = new Array();
    var timeAndAmPm = $(this).val().split(" ");
    var time = timeAndAmPm[0].split(":");

    if (timeAndAmPm == '') {
        parsedTime['hour'] = 00;
        parsedTime['minute'] = 00;
    }
    else {
        if (timeAndAmPm[1] == 'PM') {
            parsedTime['hour'] = Number(time[0]) == 12 ? time[0] : Number(time[0]) + 12;
        }
        else {
            parsedTime['hour'] = Number(time[0]);
        }
        parsedTime['minute'] = Number(time[1]);
    }
    return parsedTime;
}
function extractUrlBase() {
    url = window.location.href;
    pathname = window.location.pathname;
    idx1 = url.indexOf(pathname);
    idx2 = url.indexOf("/", idx1 + 1);
    extractUrl = url.substr(0, idx2);
    return extractUrl;
}

function checkLength(o, n, min, max) {
    if (o.val().length > max || o.val().length < min) {
        o.addClass('ui-state-error');
        updateValidationMessage(n + " field is not valid");
        return false;
    }
    return true;
}

function checkAdjacentAssignments(o, assignment, previousAssignment) {
    if (assignment == previousAssignment) {
        o.addClass('ui-state-error');
        updateValidationMessage("Adjacent distributed assignments should all be different.");
        return false;
    }
    return true;
}

function updateTips(t) {
    $('#validation').text(t)
            .addClass('ui-state-error')
            .css({'color':'red','font-weight':'bold'});
}

function cleanTips() {
    $('#validation')
            .text('')
            .removeClass('ui-state-error');
}

function updateValidationMessage(t) {
	$('#validation').text(t)
            .addClass('ui-state-error')
            .css({'color':'red','font-weight':'bold'});
}

function addTimeBlockRow(form, tempArr) {
    var timeFormatMessage = "Supported formats:<br/>9a, 9 am, 9:00a, 9:00 am, 3p, 3 pm, 3:00p, 3:00 pm, 900, 15:00";
    
    var tbl = document.getElementById('tblNewTimeBlocks');
    var lastRow = tbl.rows.length;
    var iteration = lastRow - 2;

    var row = tbl.insertRow(iteration);
    var prevRow = tbl.rows[iteration-1];
    //Assignment Dropdown list
    var cellAssignment = row.insertCell(0);
    var sel = document.createElement('select');
    sel.id = 'assignmentRow' + iteration;
    sel.id = 'assignmentRow';
    sel.className = 'assignmentRow';
    var originalAssign = form.originalAssignment.value;
    var initString = form.distributeAssignList.value;
    var string1 = initString.substring(1, initString.length - 1);
    var keyValueList = string1.split(',');
    for (var i = 0; i < keyValueList.length; i++) {
        var aSet = keyValueList[i].split('=');
        var trimedKey = ltrim(aSet[0]);
        trimedKey = rtrim(trimedKey);
        var trimedVal = ltrim(aSet[1]);
        trimedVal = rtrim(trimedVal);
        sel.options[i] = new Option(trimedKey, trimedVal);
        if (trimedKey == originalAssign) {
            sel.options[i].selected = true;
        }
    }

    cellAssignment.appendChild(sel);

    // begin date/time
    var cellBeginDate = row.insertCell(1);
    var el = document.createElement('input');
    el.id = 'bdRow' + iteration;
    el.className = 'bdRow';
    el.size = 10;
    el.value = form.beginDateOnly.value;
    el.disabled = true;
    cellBeginDate.appendChild(el);

    var cellBeginTime = row.insertCell(2);
    cellBeginTime.colSpan = 2;
    var el = document.createElement('input');
    el.id = 'btRow' + iteration;
    el.className = 'btRow';
    el.size = 10;
    el.value = $("#tblNewTimeBlocks tbody tr:nth-child(" + (iteration - 1) + ") td .etRow").val()
    cellBeginTime.appendChild(el);
    el = document.createElement('input');
    el.type = 'button';
    el.style.width = "20px";
    el.style.height = "23px";
    el.title = timeFormatMessage;
    el.className = "beginTimeHelp";
    el.value = "?";
    cellBeginTime.appendChild(el);
    el.removeAttribute("tabindex");

    //end date/time
    var cellEndDate = row.insertCell(3);
    var el = document.createElement('input');
    el.id = 'edRow' + iteration;
    el.className = 'edRow';
    el.size = 10;
    el.value = form.beginDateOnly.value;
    el.disabled = true;
    cellEndDate.appendChild(el);

    var cellEndTime = row.insertCell(4);
    cellEndTime.colSpan = 2;
    var el = document.createElement('input');
    el.id = 'etRow' + iteration;
    el.className = 'etRow';
    el.size = 10;
    cellEndTime.appendChild(el);
    el = document.createElement('input');
    el.type = 'button';
    el.style.width = "20px";
    el.style.height = "23px";
    el.title = timeFormatMessage;
    el.className = "endTimeHelp";
    el.removeAttribute("tabindex");
    el.value = "?";
    cellEndTime.appendChild(el);

    var cellHours = row.insertCell(5);
    var el = document.createElement('input');
    el.id = 'hrRow' + iteration;
    el.className = 'hrRow';
    el.size = 5;
    el.disabled = true;
    cellHours.appendChild(el);

    var deleteButton = row.insertCell(6);
    var el = document.createElement('input');
    el.id = 'deleteButton'+iteration;
    el.type = 'button';
    el.value = 'Delete';
    el.className += 'button ui-button ui-widget ui-state-default ui-corner-all';
    el.onclick =function() {
    	row.parentNode.removeChild(row);
    	recalculateTotal();
    };
    deleteButton.appendChild(el);
    recalculateTotal();
}

function recalculateHrs(itr) {
    var beginDate = $("#tblNewTimeBlocks tbody tr:nth-child(" + itr + ") td .bdRow").val();
    var endDate = $("#tblNewTimeBlocks tbody tr:nth-child(" + itr + ") td .edRow").val();
    var beginTime = $("#tblNewTimeBlocks tbody tr:nth-child(" + itr + ") td .btRow").val();
    var endTime = $("#tblNewTimeBlocks tbody tr:nth-child(" + itr + ") td .etRow").val();
    var validFlag = true;
    var tbl = document.getElementById('tblNewTimeBlocks');
    var rowLength = tbl.rows.length;
    var form1 = document.forms[0];
    var originalEndDateTime = new Date(form1.endTimestamp.value);
    var originalBeginDateTime = new Date(form1.beginTimestamp.value);

    validFlag &= checkLength($("#tblNewTimeBlocks tbody tr:nth-child(" + itr + ") td .bdRow"), "Date/Time", 10, 10);
    validFlag &= checkLength($("#tblNewTimeBlocks tbody tr:nth-child(" + itr + ") td .edRow"), "Date/Time", 10, 10);
    validFlag &= checkLength($("#tblNewTimeBlocks tbody tr:nth-child(" + itr + ") td .btRow"), "Date/Time", 8, 8);
    validFlag &= checkLength($("#tblNewTimeBlocks tbody tr:nth-child(" + itr + ") td .etRow"), "Date/Time", 8, 8);

    if (validFlag) {
        var dateString = beginDate + ' ' + beginTime;
        var beginTimeTemp = new Date(dateString);
        dateString = endDate + ' ' + endTime;
        var endTimeTemp = new Date(dateString);

        if (itr == rowLength - 2) { // endTime of last row should include seconds of originalEndTime
            var eTimeFormated = endDate + ' ' + endTimeTemp.getHours() + ':' + endTimeTemp.getMinutes() + ':' + originalEndDateTime.getSeconds();
            var newEndDate = new Date(eTimeFormated);
            var hrsDifferent = newEndDate - beginTimeTemp;
        } else if (itr == 1) { // beginTime of first row should use begin time plus seconds from originalBeginTime
            var bTime = beginDate + ' ' + beginTimeTemp.getHours() + ':' + beginTimeTemp.getMinutes() + ':' + originalBeginDateTime.getSeconds();
            var newBeginDate = new Date(bTime);
            var hrsDifferent = endTimeTemp - newBeginDate;
        } else {
            var hrsDifferent = endTimeTemp - beginTimeTemp;
        }

        if (hrsDifferent <= 0) {
            updateTips("Hours for item " + itr + " not valid");
            var hrs = hrsDifferent / 3600000;
            hrs = Math.round(hrs * 100) / 100;
            $("#tblNewTimeBlocks tbody tr:nth-child(" + itr + ") td .hrRow").val(hrs);
            return false;
        }
        var hrs = Math.round(hrsDifferent * 100 / 3600000) / 100;
        $("#tblNewTimeBlocks tbody tr:nth-child(" + itr + ") td .hrRow").val(hrs);

        recalculateTotal();
    }
    else {
        return false;
    }
}

function recalculateTotal() {
    var tbl = document.getElementById('tblNewTimeBlocks');
    var rowLength = tbl.rows.length;
    var totalHrs = 0;
    for (var i = 1; i < rowLength - 1; i++) {
        var hrs = $("#tblNewTimeBlocks tbody tr:nth-child(" + i + ") td .hrRow").val();
        if (hrs != undefined && hrs != "") {
            totalHrs += parseFloat(hrs);
        }
    }
    totalHrs = totalHrs.toFixed(2);
    $("#hrsTotal").val(totalHrs);
}

function ltrim(str) {
    return str.replace(/^\s+/, '');
}
function rtrim(str) {
    return str.replace(/\s+$/, '');
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regexS = "[\\?&]" + name + "=([^&#]*)";
    var regex = new RegExp(regexS);
    var results = regex.exec(window.location.href);
    if (results == null)
        return "";
    else
        return decodeURIComponent(results[1].replace(/\+/g, " "));
}

function toCamelCase(str) {
    return str.replace(/^.?/g, function(match) {
        return match.toLowerCase();
    });
}

function toggle(eleId) {
	var ele = document.getElementById(eleId);
	if(ele.style.display == "block") {
    	ele.style.display = "none";
  	}
	else {
		ele.style.display = "block";
	}
}
// validate the time format
function validateTime(input){
    $("#" + input.attr('id') + '-error').html("");
    try {
    	parseTimeString(input.val());
    }
    catch (e) {
        return false;
    }
}
function parseTimeString(s) {
    for (var i = 0; i < timeParsePatterns.length; i++) {
        var re = timeParsePatterns[i].re;
        var handler = timeParsePatterns[i].handler;
        var bits = re.exec(s);
        if (bits) {
            // this is to debug which regex it actually used
            //console.log(i);
            return handler(bits);
        }
    }
    throw new Error("Invalid time format");
}

function formatTime(input) {
	var id = input.attr('id');
	var value = input.val();
    // Use Datejs to parse the value
    var dateTime = Date.parse(value);
    if (dateTime == null) {
        // Date.js returns null if it couldn't understand the format from user's input.
        $("#" + id).addClass("block-error").val(value);
        return;
    } else {
        // Remove the red border if user enters something
        $("#" + id).removeClass("block-error").val("");
    }
    // This magic line first finds the element by the id.
    // Uses Datejs (a 3rd party js lib) to parse user's input and update the value by the specifed format.
    // See the list of the formats in tk.js.
    $("#" + id).val(dateTime.toString(CONSTANTS.TIME_FORMAT.TIME_FOR_OUTPUT));
}






