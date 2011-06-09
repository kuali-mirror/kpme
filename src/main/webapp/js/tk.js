/**
 * If you need to change the theme base, the css file is: jquery-ui-1.8.1.custom.css
 */

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

    // datepicker
    $('#date-range-begin, #date-range-end, #bdRow1, #edRow1').datepicker({
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

    // hide the date picker by default
    // https://jira.kuali.org/browse/KPME-395
    $('#ui-datepicker-div').css('display', 'none');

    // select All
    $('#selectAll').click(function() {
        var checked_status = this.checked;
        $("input[name=selectedEmpl]").each(function() {
            this.checked = checked_status;
            $("input[name=selectedEmpl]").parent().parent()
                    .addClass("ui-state-focus");
        });

        if (checked_status == false) {
            $("input[name=selectedEmpl]").parent().parent()
                    .removeClass("ui-state-focus");
        }
    });

    // highlight row
    $("input[name=selectedEmpl]").click(function() {

        var checked_status = this.checked;

        if (checked_status) {
            $(this).parent().parent().addClass("ui-state-focus");
        } else {
            $(this).parent().parent().removeClass("ui-state-focus");
        }
    });

    // clock
    var currentServerTime = parseFloat($("#currentServerTime").val());
    var options = {
        format : '%I:%M:%S %p',
        utc: true,
        utc_offset: -(new Date(currentServerTime).getTimezoneOffset() / 60)
    };
    $(".jClock").jclock(options);

    // elapsed time
    // http://keith-wood.name/countdown.html
//    if($("#clock-button").val() == "Clock Out") {
//        $("#lastClockedInTime").val("");
//        $("#elapsed-time").val("00:00:00");
//    }
    var lastClockedInTime = $("#lastClockedInTime").val();
    var clockAction = $("#clockAction").val();
    var startTime = clockAction == 'CO' ? new Date(lastClockedInTime) : new Date(currentServerTime);

    $('.elapsedTime').countdown({
                since : startTime,
                compact : true,
                format : 'dHMS',
                description : ''
            })

    // tooltip
    // http://flowplayer.org/tools/tooltip/index.html
    $("#beginTimeHelp, #endTimeHelp, #beginTimeHelp1, #endTimeHelp1").tooltip({

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

    // note accordion
    $("#note").accordion({
                collapsible : true,
                active : 2
            });

    // person detail accordion
    $("#person-detail-accordion").accordion({
                collapsible : true,
                active : 0,
                autoHeight: false
            });

    /**
     * Approval pages
     */

    // sort by column
    $('#approvals-table tr th').click(function() {

        var field = $(this).html().replace(/ /, '');
        var rows = $('#approvals-table tbody tr').length;
        var isAscending = getParameterByName("ascending");

        if (isAscending == null) {
            isAscending = true;
        } else if (isAscending == "true") {
            isAscending = false;
        } else {
            isAscending = true;
        }

        window.location = 'TimeApproval.do?sortField=' + field + '&ascending=' + isAscending + '&rows=' + rows;
    });

    /**
     * render the sorting icons
     */
    $('#approvals-table tr th').filter(
            function(index) {
                return $(this).html().replace(/ /, '') == "DocumentId" || $(this).html().replace(/ /, '') == "PrincipalName";
            }).addClass("sort");

    if (getParameterByName("ascending") != '') {
        var class = getParameterByName("ascending") == "true" ? 'headerSortDown' : 'headerSortUp';

        $('#approvals-table tr th').filter(
                function(index) {
                    return $(this).html().replace(/ /, '') == getParameterByName("sortField");
                }).addClass(class);
    }


    // fetch more document headers
    $('a#next').click(function() {
        $('div#loader').html('<img src="images/ajax-loader.gif">');
        $.post('TimeApproval.do?methodToCall=getMoreDocument&lastDocumentId=' + $('span.document:last').attr('id'),
                function(data) {
                    // remove blank lines
                    data = data.replace(/[\s\r\n]+$/, '');
                    if (data != 0) {
                        //$('span.document:last').hide().append(data).fadeIn();
                        // scroll to where the link is
                        //window.scrollTo(0, $('a#next').position().top);
                        // append the data to the table body through ajax
                        $('#approval tbody').append(data);
                        // let the plugin know that we made a update
                        //$('#approval').trigger('update');
                        // An array of instructions for per-column sorting and direction in the format: [[columnIndex, sortDirection], ... ]
                        // where columnIndex is a zero-based index for your columns left-to-right and sortDirection is 0 for Ascending and 1 for Descending.
                        // A valid argument that sorts ascending first by column 1 and then column 2 looks like: [[0,0],[1,0]]
                        var sorting = [
                            [0,0]
                        ];
                        // sort on the first column
                        //$('#approval').trigger('sorton', [sorting]);
                    }
                    else {
                        // if there is no more document available, remove the link and scroll to the bottom
                        $('a#next').remove();
                        //window.scrollTo(0, $('span.document:last').position().top);
                    }
                    $('div#loader').empty();
                });

    });

    $('#searchValue').autocomplete({
                // For some reason, when I use the $('#searchField').val() in the line below,
                // it always gets the first option value instead of the selected one.
                // Therefore, I have to use a callback function to feed the source and handle the respone/request by myself.
                //source: "TimeApproval.do?methodToCall=searchDocumentHeaders" + $('#searchField').val(),
                source: function(request, response) {

                    $.post('TimeApproval.do?methodToCall=searchDocumentHeaders&searchField=' + $('#searchField').val() + '&term=' + request.term,
                            function(data) {
                                response($.map(jQuery.parseJSON(data), function(item) {
                                    return {
                                        value: item
                                    }
                                }));
                            });
                },
                minLength: 3,
                select: function(event, data) {
                    var rows = $('#approval tbody tr').length;
                    var orderBy = getParameterByName("orderBy");
                    var orderDirection = getParameterByName("orderDirection");

                    window.location = 'TimeApproval.do?orderBy=' + toCamelCase(orderBy) + '&orderDirection=' + orderDirection + '&rows=' + rows +
                            '&searchField=' + $('#searchField').val() + '&term=' + data.item.value;
                },
                open: function() {
                    $(this).removeClass("ui-corner-all");
                },
                close: function() {
                    $(this).removeClass("ui-corner-top");
                }
            });

    // show-hide earn codes in the approval page
    $("#fran-button").click(function() {
        $(".fran").toggle();
        $("#fran-button span").toggleClass('ui-icon-minus');
    });
    $("#frank-button").click(function() {
        $(".frank").toggle();
        $("#frank-button span").toggleClass('ui-icon-minus');
    });

    /**
     * end of approval page
     */

    // apply time entry widget to the tabular view
    $(".timesheet-table-week1 :input, .timesheet-table-week2 :input").blur(
            function() {
                magicTime(this);
            }).focus(function() {
                if (this.className != 'error') this.select();
            });

    // Button for iFrame show/hide to show the missed punch items
    // The iFrame is added to the missed-punch-dialog as a child element.
    // tdocid is a variable that is set from the form value in 'clock.jsp'
    $('#missed-punch-iframe-button').click(function() {

        $('#missed-punch-dialog').empty();
        $('#missed-punch-dialog').append('<iframe width="800" height="500" src="kr/maintenance.do?businessObjectClassName=org.kuali.hr.time.missedpunch.MissedPunch&methodToCall=start&tdocid=' + tdocid + '"></iframe>');

        $('#missed-punch-dialog').dialog({
                    autoOpen: true,
                    height: 'auto',
                    width: 'auto',
                    modal: true,
                    buttons: {
                        //"test" : function() {
                        //}
                    }
                });
    });

    $("#bdRow1, #edRow1").change(function() {
        $(this).removeClass('ui-state-error');
        recalculateHrs(1);
    });

    $("#btRow1, #etRow1").change(function() {
        $(this).removeClass('ui-state-error');
        magicTime($(this));
        recalculateHrs(1);
    });


    $('#saveTimeBlock').click(function() {
//	   function updateTips(t) {
//		   $('#validation').text(t)
//		   			.addClass('ui-state-error')
//		   			.css({'color':'red','font-weight':'bold'});
//	   }
        var validFlag = true;
        var validation = $('#validation');

        function updateValidationMessage(t) {
            validation.text(t)
                    .addClass('ui-state-error')
                    .css({'color':'red','font-weight':'bold'});
        }

//	   function checkLength(o, n, min, max) {
//	         if (o.val().length > max || o.val().length < min) {
//	             o.addClass('ui-state-error');
//	             updateValidationMessage(n + " field is not valid");
//	             return false;
//	         }
//	         return true;
//	   }
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


        var errorMsgs = '';
        var totalHrs = 0;
        if (rowLength <= 2) {
            updateTips("Please use Add button to add entries or click Cancel to close the window.");
            return false;
        }
        for (var i = 1; i < rowLength - 1; i++) {
            var assignValue = $("#assignmentRow" + i).val();
            var beginDate = $("#bdRow" + i).val();
            var endDate = $("#edRow" + i).val();
            var beginTime = $("#btRow" + i).val();
            var endTime = $("#etRow" + i).val();
            var hrs = $("#hrRow" + i).val();

            assignValueCol += assignValue + valueSeperator;
            beginDateCol += beginDate + valueSeperator;
            endDateCol += endDate + valueSeperator;
            hrsCol += hrs + valueSeperator;

            validFlag &= checkLength($("#bdRow" + i), "Date/Time", 10, 10);
            validFlag &= checkLength($("#edRow" + i), "Date/Time", 10, 10);
            validFlag &= checkLength($("#btRow" + i), "Date/Time", 8, 8);
            validFlag &= checkLength($("#etRow" + i), "Date/Time", 8, 8);

            if (validFlag) {
                var dateString = beginDate + ' ' + beginTime;
                var beginTimeTemp = $.fullCalendar.parseDate(dateString);
                var bTimeFormated = beginTimeTemp.getHours() + ':' + beginTimeTemp.getMinutes();
                beginTimeCol += bTimeFormated + valueSeperator;

                dateString = endDate + ' ' + endTime;
                var endTimeTemp = $.fullCalendar.parseDate(dateString);
                var eTimeFormated = endTimeTemp.getHours() + ':' + endTimeTemp.getMinutes();
                endTimeCol += eTimeFormated + valueSeperator;

                var hrsDifferent = endTimeTemp - beginTimeTemp;
                if (hrsDifferent <= 0) {
                    updateTips("Hours for item " + i + "not valid");
                    var hrs = hrsDifferent / 3600000;
                    $("#hrRow" + i).val(hrs);
                    return false;
                }
                var hrs = Math.round(hrsDifferent * 100 / 3600000) / 100;
                $("#hrRow" + i).val(hrs);
                totalHrs += hrs;
            }// end of if
            else {
                return false;
            }

        }// end of for loop
        if (totalHrs != originalHrs) {
            updateTips("Total Hours entered not equel to the hours of the original time block");
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

        var params = {};
        params['newAssignDesCol'] = assignValueCol;
        params['newBDCol'] = beginDateCol;
        params['newEDCol'] = endDateCol;
        params['newBTCol'] = beginTimeCol;
        params['newETCol'] = endTimeCol;
        params['newHrsCol'] = hrsCol;
        params['tbId'] = timeBlockId;

        $.ajax({
                    url: "Clock.do?methodToCall=validateNewTimeBlock",
                    data: params,
                    cache: false,
                    success: function(data) {
                        var json = jQuery.parseJSON(data);
                        // if there is no error message, submit the form and save the new time blocks
                        if (json.length == 0) {
                            $('#methodToCall').val('saveNewTimeBlocks');
                            $('#editTimeBlockForm').submit();
                            window.close();
                            return false;
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
                        return false;
                    }
                });

    });

    // submit the form for changing the ip address in the batch job page
    $('.changeIpAddress').click(function() {
        var value = $(this).siblings().val().split("_");
        var ipToChange = value[0];
        var tkBatchJobEntryId = value[1];

        window.location = "BatchJob.do?methodToCall=changeIpAddress&ipToChange=" + ipToChange + "&tkBatchJobEntryId=" + tkBatchJobEntryId;
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

function updateTips(t) {
    $('#validation').text(t)
            .addClass('ui-state-error')
            .css({'color':'red','font-weight':'bold'});
}

function addTimeBlockRow(form, tempArr) {

    var tbl = document.getElementById('tblNewTimeBlocks');
    var lastRow = tbl.rows.length;
    // if there's no header row in the table, then iteration = lastRow + 1
    var iteration = lastRow - 1;

    var row = tbl.insertRow(iteration);

    //Assignment Dropdown list
    var cellCount = row.insertCell(0);
    var textNode = document.createTextNode(iteration);
    cellCount.appendChild(textNode);

    var cellAssignment = row.insertCell(1);
    var sel = document.createElement('select');
    var idString = 'assignmentRow' + iteration;
    sel.name = idString;
    sel.id = idString;

//	var initString = form.assignmentList.value;
//	var subString1 = initString.substring(1, initString.length-1);
//	var assignList= subString1.split(',');

//	for(var i=0; i< assignList.length; i++) {
//		var tempString = ltrim(assignList[i]);
//		tempString = rtrim(tempString);
//		sel.options[i] = new Option(tempString, tempString);
//		if(tempString == originalAssign) {
//			sel.options[i].selected=true;
//		}
//	}

    var originalAssign = form.originalAssignment.value;
    var initString = form.assignKeyList.value;
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
    var cellBeginDate = row.insertCell(2);
    var el = document.createElement('input');
    idString = 'bdRow' + iteration;
    el.type = "text";
    el.name = idString;
    el.id = idString;
    el.size = 10;
    var beginDate = $.fullCalendar.parseDate(form.beginTimestamp.value);
    var formatedDate = $.fullCalendar.formatDate(beginDate, "MM/dd/yyyy");
    el.value = formatedDate;
    cellBeginDate.appendChild(el);

    var datePickerId = '#' + idString;

    var cellBeginTime = row.insertCell(3);
    var el = document.createElement('input');
    idString = 'btRow' + iteration;
    el.name = idString;
    el.id = idString;
    el.size = 10;
    var beginTime = $.fullCalendar.formatDate(beginDate, "hh:mm TT");
    el.value = beginTime;
    cellBeginTime.appendChild(el);
    var timeChangeId = '#' + idString;
    var timeFormatMessage = "Supported formats:<br/>9a, 9 am, 9 a.m.,  9:00a, 9:45a, 3p, 0900, 15:30, 1530";
// help button for time format
    el = document.createElement('input');
    el.type = 'button';
    el.style.width = "20px";
    el.style.height = "23px";
//	el.class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-primary ui-button-icon-only ui-state-hover";
//	el.class ="ui-button-icon-primary ui-icon ui-icon-help";
    el.title = timeFormatMessage;
    el.id = "beginTimeHelp" + iteration;
    el.value = "?";
    cellBeginTime.appendChild(el);
    el.removeAttribute("tabindex");
    var timeHelpId = '#' + el.id; 	// for tooltip

    //end date/time
    var cellEndDate = row.insertCell(4);
    var el = document.createElement('input');
    idString = 'edRow' + iteration;
    el.name = idString;
    el.id = idString;
    el.size = 10;
    var endDate = $.fullCalendar.parseDate(form.endTimestamp.value);
    var formatedDate = $.fullCalendar.formatDate(endDate, "MM/dd/yyyy");
    el.value = formatedDate;
    cellEndDate.appendChild(el);
    datePickerId += ', #' + idString;

    var cellEndTime = row.insertCell(5);
    var el = document.createElement('input');
    idString = 'etRow' + iteration;
    el.name = idString;
    el.id = idString;
    el.size = 10;
    var endTime = $.fullCalendar.formatDate(endDate, "hh:mm TT");
    el.value = endTime;
    cellEndTime.appendChild(el);
    timeChangeId += ', #' + idString;

    el = document.createElement('input');
    el.type = 'button';
    el.style.width = "20px";
    el.style.height = "23px";
    el.title = timeFormatMessage;
    el.id = "endTimeHelp" + iteration;
    el.removeAttribute("tabindex");
//	el.tabindex="999";
    el.value = "?";
    cellEndTime.appendChild(el);
    timeHelpId += ', #' + el.id; 	// for tooltip

    var cellHours = row.insertCell(6);
    var el = document.createElement('input');
    idString = 'hrRow' + iteration;
    el.name = idString;
    el.id = idString;
    el.size = 5;
    el.value = form.hours.value;
    el.readOnly = true;
    cellHours.appendChild(el);
    var hrId = '#' + idString;

    row.insertCell(7);
    recalculateTotal();

    // datepicker
    $(datePickerId).datepicker({
                changeMonth : true,
                changeYear : true,
                showOn : 'button',
                showAnim : 'fadeIn',
                buttonImage : 'kr/static/images/cal.gif',
                buttonImageOnly : true,
                buttonText : 'Select a date',
                showButtonPanel : true,
                constrainInput : true,
                minDate : new Date($('#beginDate').val()),
                maxDate : new Date($('#endDate').val())
            });

    //time format helper
    $(timeHelpId).tooltip({
                // place tooltip on the right edge
                position : "center right",
                // a little tweaking of the position
                offset : [-2, 10],
                // use the built-in fadeIn/fadeOut effect
                effect : "fade",
                // custom opacity setting
                opacity : 0.7,
                fadeInSpeed : 100
            });

    $(datePickerId).change(function() {
        $(this).removeClass('ui-state-error');
        recalculateHrs(iteration);
    });

    $(timeChangeId).change(function() {
        $(this).removeClass('ui-state-error');
        magicTime($(this));
        recalculateHrs(iteration);
    });

}

function recalculateHrs(itr) {
    var beginDate = $("#bdRow" + itr).val();
    var endDate = $("#edRow" + itr).val();
    var beginTime = $("#btRow" + itr).val();
    var endTime = $("#etRow" + itr).val();
    var validFlag = true;

    validFlag &= checkLength($("#bdRow" + itr), "Date/Time", 10, 10);
    validFlag &= checkLength($("#edRow" + itr), "Date/Time", 10, 10);
    validFlag &= checkLength($("#btRow" + itr), "Date/Time", 8, 8);
    validFlag &= checkLength($("#etRow" + itr), "Date/Time", 8, 8);

    if (validFlag) {
        var dateString = beginDate + ' ' + beginTime;
        var beginTimeTemp = $.fullCalendar.parseDate(dateString);
        var bTimeFormated = beginTimeTemp.getHours() + ':' + beginTimeTemp.getMinutes();

        dateString = endDate + ' ' + endTime;
        var endTimeTemp = $.fullCalendar.parseDate(dateString);
        var eTimeFormated = endTimeTemp.getHours() + ':' + endTimeTemp.getMinutes();

        var hrsDifferent = endTimeTemp - beginTimeTemp;
        if (hrsDifferent <= 0) {
            updateTips("Hours for item " + itr + "not valid");
            var hrs = hrsDifferent / 3600000;
            $("#hrRow" + itr).val(hrs);
            return false;
        }
        var hrs = Math.round(hrsDifferent * 100 / 3600000) / 100;
        $("#hrRow" + itr).val(hrs);

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
        var hrs = $("#hrRow" + i).val();
        totalHrs += parseFloat(hrs);
    }
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



