$(document).ready(function() {

    // calendar
    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    var beginPeriodDateTimeObj = $("#beginPeriodDate").val() !== undefined ? new Date($("#beginPeriodDate").val()) : d + "/" + m + "/" + y;
    var endPeriodDateTimeObj = $("#endPeriodDate").val() !== undefined ? new Date($("#endPeriodDate").val()) : d + "/" + m + "/" + y;

    // end period time has to be set to the previous day, sicne the boundary ends at 00:00:00
    endPeriodDateTimeObj.setDate(endPeriodDateTimeObj.getDate()-1);

    var docId = $("#documentId").val();
    var eventUrl = "TimeDetail.do?methodToCall=getTimeBlocks&documentId=" + docId;

    var calendar = $('#cal').fullCalendar({
        beginPeriodDate : beginPeriodDateTimeObj,
        endPeriodDate : endPeriodDateTimeObj,
        theme : true,
        aspectRatio : 5, // the value here is just to match the height with the add time block panel
        allDaySlot : false,
        multidaySelect : true,
        header: {
            // left : "today",
            left : "",
            center : 'prev, title, next',
            right : ''
        },
        eventClick: function(calEvent, jsEvent) {

            var targetId = jsEvent.target.id;

            if(targetId == 'timeblock-delete') {
                window.location = "TimeDetail.do?methodToCall=deleteTimeBlock&tkTimeBlockId=" + calEvent.id;
            }
            if(targetId == 'timeblock-edit' || targetId == '') {
                var dateFormat = "MM/dd/yyyy";
                var timeFormat = "hh:mm TT";

                $('#dialog-form').dialog('open');
                $('#date-range-begin').val($.fullCalendar.formatDate(calEvent.start, dateFormat));
                $('#date-range-end').val($.fullCalendar.formatDate(calEvent.end, dateFormat));
                $("select#assignment option[value='" + calEvent.assignment + "']").attr("selected", "selected");
                $('#earnCode').loadEarnCode($('#assignment').val(), calEvent.earnCode + "_" + calEvent.earnCodeType);
                $('#beginTimeField').val($.fullCalendar.formatDate(calEvent.start, timeFormat));
                $('#endTimeField').val($.fullCalendar.formatDate(calEvent.end, timeFormat));
                $('#tkTimeBlockId').val(calEvent.tkTimeBlockId);
                $('#hoursField').val(calEvent.hours);
                // the month value in the javascript date object is the actual month minus 1.
                $('#beginTimeField-messages').val(calEvent.start.getMonth()+1 + "/" + calEvent.start.getDate() + "/" +
                        calEvent.start.getFullYear() + " " + calEvent.start.getHours() + ":" + calEvent.start.getMinutes());
                $('#endTimeField-messages').val(calEvent.end.getMonth()+1 + "/" + calEvent.end.getDate() + "/" +
                        calEvent.end.getFullYear() + " " + calEvent.end.getHours() + ":" + calEvent.end.getMinutes());
            }
        },
        selectable: true,
        selectHelper: true,
        select: function(start, end, allDay) {

            // clear any existing values
            $('#beginTimeField, #endTimeField, #hoursField, #acrossDaysField').val("");
            $('#acrossDaysField').attr('checked', '');
            // check acrossDaysField if multiple days are selected
            if (start.getTime() != end.getTime()) {
                $('#acrossDaysField').attr('checked', 'checked');
            }

            // if the virtual day mode is true, set the start hour the same as the pay period time
            if ($('#isVirtualWorkDay').val() == 'true') {
                start.setHours(beginPeriodDateTimeObj.getHours());
                end.setHours(endPeriodDateTimeObj.getHours());
            }

            // disable showing the time entry form if the date is not within the pay period
            if (start.getTime() >= beginPeriodDateTimeObj.getTime() && end.getTime() <= endPeriodDateTimeObj.getTime()) {

                // if there is only one assignment, get the earn code without selecting the assignment
                if ($('#assignment-value').html() != '') {
                    $('#earnCode').loadEarnCode($('#assignment').val());
                }

                $("#tkTimeBlockId").val("");
                $('#dialog-form').dialog('open');

            }
        },
        editable: false,
        events : eventUrl,
        loading: function(bool) {
            if (bool) {
                $('#loading').show();
            }
            else {
                $('#loading').hide();
            }
        }
    });

    var tips = $(".validateTips");
    var startTime = $('#beginTimeField');
    var endTime = $('#endTimeField');
    var assignment = $('#assignment');
    var assignmentValue = $('#assignment-value').html();
    var earnCode = $('#earnCode');

    var fieldsToValidate = $([]).add(startTime).add(endTime).add(earnCode);
    fieldsToValidate.clearValue();
    //------------------------
    // buttons on the form
    //------------------------
    var buttons = {};
    buttons["Add"] = function() {

        //-----------------------------------
        // time entry form validation
        //-----------------------------------
        var bValid = true;
        tips.val("");

        function updateTips(t) {
            tips
                    .text(t)
                    .addClass('ui-state-error')
                    .css({'color':'red','font-weight':'bold'});
            // setTimeout(function() {
            //     tips.removeClass('ui-state-error', 1500);
            // }, 1000);
        }

        function checkLength(o, n, min, max) {
            if (o.val().length > max || o.val().length < min) {
                o.addClass('ui-state-error');
                updateTips(n + " field can't be empty");
                return false;
            } else {
                return true;
            }
        }

        function checkTimeEntryFields(o, n) {
            var val = o.val();
            o.addClass('ui-state-error');
            if (val == '') {
                updateTips(n + " field can't be empty");
                return false;
            }
            return true;
        }

        function checkRegexp(o, regexp, n) {
            if (!( regexp.test(o.val()) )) {
                o.addClass('ui-state-error');
                updateTips(n);
                return false;
            } else {
                return true;
            }
        }

        if ($('#hoursField').val() === '') {
            bValid = bValid && checkLength(startTime, "In", 8, 8);
            bValid = bValid && checkLength(endTime, "Out", 8, 8);
        }

        bValid = bValid && checkTimeEntryFields(assignment, "Assignment");
        bValid = bValid && checkTimeEntryFields(earnCode, "Earn Code");

        // end of time entry form validation
        //-----------------------------------

        if ($('#hoursField').val() !== '' || bValid) {
            var params = {};

            // these are for the submitted form
            $("#methodToCall").val("addTimeBlock");
            $("#startDate").val($("#date-range-begin").val());
            $("#endDate").val($("#date-range-end").val());
            $("#startTime").val($('#beginTimeField-messages').val());
            $("#endTime").val($('#endTimeField-messages').val());
            $("#hours").val($('#hoursField').val());
            $("#selectedEarnCode").val($("#earnCode").val().split("_")[0]);
            $("#selectedAssignment").val($("#assignment").val());
            $("#acrossDays").val($('#acrossDaysField').is(':checked') ? 'y' : 'n');

            // these are for the validation
            params['startDate'] = $("#date-range-begin").val();
            params['endDate'] = $("#date-range-end").val();
            params['startTime'] = $('#beginTimeField-messages').val();
            params['endTime'] = $('#endTimeField-messages').val();
            params['hours'] = $('#hoursField').val();
            params['selectedEarnCode'] = $("#earnCode").val().split("_")[0];
            params['selectedAssignment'] = $("#assignment").val();
            params['acrossDays'] = $('#acrossDaysField').is(':checked') ? 'y' : 'n';
            params['tkTimeBlockId'] = $("#tkTimeBlockId").val();

            // validate timeblocks
            $.ajax({
                url: "TimeDetail.do?methodToCall=validateTimeBlocks",
                data: params,
                cache: false,
                success: function(data) {
                    //var match = data.match(/\w{1,}|/g);
                    var json = jQuery.parseJSON(data);
                    // if there is no error message, submit the form to add the time block
                    if (json.length == 0) {
                        $("#time-detail").submit();
                    }
                    else {
                        // grab error messages
                        var json = jQuery.parseJSON(data);
                        var errorMsgs = "";
                        $.each(json, function (index) {
                            errorMsgs += "Error : " + json[index] + "\n";
                        });

                        updateTips(errorMsgs);
                        return false;
                    }
                },
                error: function() {
                    updateTips("Error: Can't save data.");
                    return false;
                }
            });

            fieldsToValidate.clearValue($('#assignment'));
        }
    };

    buttons["Cancel"] = function() {
        fieldsToValidate.clearValue();
        $(this).dialog('close');
    };

    //end of the buttons
    //------------------------

    $("#dialog-form").dialog({
        autoOpen: false,
        height: 'auto',
        width: 'auto',
        modal: true,
        beforeClose: function(event, ui) {
            // remove all the error messages
            $('.validateTips').html("").removeClass('ui-state-error');
            // restore the earn code value to the default  
            $('#earnCode').html("<option value=''>-- select an assignment --</option>");
        },
        buttons: buttons
    });

    // when the date field(s) is changed, it should update the time field as well
    $("#date-range-begin, #date-range-end").change(function() {
        magicTime($("#beginTimeField"));
        magicTime($("#endTimeField"));
    });

    // when the time fields(s) is changed, reset the hour/amount field
    $("#beginTimeField, #endTimeField").change(function() {
        $("#hoursField").val("");
    });

    // when selecting an earn code, hide / show the time/hour/amount field as well
    $("select#earnCode").change(function() {
        $(this).loadFields($(this).val());
    });


    // filter earn codes
    $('#assignment').change(function() {
        // remove the error style
        $('#assignment').removeClass('ui-state-error');
        $("#earnCode").loadEarnCode($(this).val());
    });

    // use keyboard to open the form
    var isCtrl,isAlt = false;

    // ctrl+alt+a will open the form
    $(this).keydown(
                   function(e) {

                       if (e.ctrlKey) isCtrl = true;
                       if (e.altKey) isAlt = true;

                       if (e.keyCode == 65 && isCtrl && isAlt) {
                           $("#dialog-form").dialog('open');
                       }

                   }).keyup(function(e) {
        isCtrl = false;
        isAlt = false;
    });

});

//-------------------
// custom functions
//-------------------

$.fn.loadEarnCode = function(assignment, selectedEarnCode) {
    var params = {};
    params['selectedAssignment'] = assignment;

    $.ajax({
        url: "TimeDetail.do?methodToCall=getEarnCodes",
        data: params,
        cache: true,
        success: function(data) {
            $('#earnCode').html(data);
            if (selectedEarnCode != undefined && selectedEarnCode != '') {
                $("select#earnCode option[value='" + selectedEarnCode + "']").attr("selected", "selected");
            }

            $('#earnCode').loadFields($('#earnCode').val());
        },
        error: function() {
            $('#earnCode').html("Error: Can't get earn codes.");
        }
    });

    $('#loading-earnCodes').ajaxStart(function() {
        $(this).show();
    });
    $('#loading-earnCodes').ajaxStop(function() {
        $(this).hide();
    });
}

$.fn.loadFields = function(earnCode) {

    var fieldType = earnCode.split("_")[1];

    if (fieldType == 'HOUR') {
        $('#beginTimeField,#endTimeField').val("");
        $('#clockIn, #clockOut').hide();
        $('#hoursSection').show();
        $('#hoursField').validateNumeric();
    }
    // TODO: need to handle the amount field
    else {
        $('#hours').val("");
        $('#clockIn, #clockOut').show();
        $('#hoursSection').hide();
    }
}

$.fn.deleteTimeBlock = function(tkTimeBlockId) {
    var params = {};
    params['methodToCall'] = "deleteTimeBlock";
    params['tkTimeBlockId'] = tkTimeBlockId;

    $.ajax({
        url: "TimeDetail.do",
        data: params,
        cache: true,
        success: function(data) {
            return true;
        },
        error: function() {
            return false;
        }
    });
}

$.fn.clearValue = function(elementToAdd) {
    var assignmentValue = $('#assignment-value').html();
    // clear assignment value when there is only one assignment
    if (assignmentValue != undefined && assignmentValue != '') {
        //$('#assignment-value').html('').removeClass('ui-state-error');
    }

    // clear the error message
    $('.validateTips').html("").removeClass('ui-state-error');

    // reset the assignment when there are multiple ones 
    if (elementToAdd != undefined && elementToAdd.is("select")) {
        // $(this).val('').removeClass('ui-state-error');
        // it doesn't seem necessary to change the value to the default empty string for now.
        $(this).add(elementToAdd).removeClass('ui-state-error');
    }

}

$.fn.validateNumeric = function() {

    $(this).keyup(function() {
        var val = $(this).val();
        // replace anything with "" unless it's 0-9 or .
        val = val.replace(/[^0-9\.]*/g, "");
        // deal with the case when there is only .
        val = val.replace(/^\.$/g, "");
        $(this).val(val);
    });
}

$.fn.createDeleteButton = function() {
    $("#delete-button").button({
        icons : {
            primary : 'ui-icon-close'
        },
        text : false
    });
}
