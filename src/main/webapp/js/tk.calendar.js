$(document).ready(function() {

    // calendar
    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    // grab the pay period begin/end date from the form. if they are emtpy (which shouldn't), return the current date
    var beginPeriodDateTimeObj = $('#beginPeriodDate').val() !== undefined ? new Date($('#beginPeriodDate').val()) : d + '/' + m + '/' + y;
    var endPeriodDateTimeObj = $('#endPeriodDate').val() !== undefined ? new Date($('#endPeriodDate').val()) : d + '/' + m + '/' + y;
    // this is used to store the original values of the clicked event
    var oriTimeDetail = {};

    // end period time has to be set to the previous day if it's not in the virtual work day mode, sicne the boundary ends at 00:00:00
    if ($('#isVirtualWorkDay').val() == 'false') {
        endPeriodDateTimeObj.setDate(endPeriodDateTimeObj.getDate() - 1);
    }
    var docId = $('#documentId').val();
    var thing = $('#tkCal').click( function(event) {
        if (event.target.id.indexOf("_") > -1) {
            var actionA = event.target.id.split("_");
            if (actionA.length == 2) {
                var action = actionA[0];
                var actionVal = actionA[1];
                console.log(action);
                console.log(actionVal);

                if (action == "nav") {
                    // Handle nav click
                    // Handle nav click
                    // Handle nav click
                    if (actionVal == "next") {
                        window.location='TimeDetail.do?calNav=next&documentId=' + docId;
                    } else if (actionVal == "prev") {
                        window.location='TimeDetail.do?calNav=prev&documentId=' + docId;
                    }
                } else if (action == "delete") {
                    // Handle delete
                    // Handle delete
                    // Handle delete
            	    if(confirm('You are about to delete a time block. Click OK to confirm the delete.')){
            		    window.location = "TimeDetail.do?methodToCall=deleteTimeBlock&tkTimeBlockId=" + actionVal;
            	    }
                } else if (action == "day") {
                    // Handle new entries
                    // Handle new entries
                    // Handle new entries
                     oriTimeDetail = {};
                    $('#beginTimeField, #endTimeField, #hoursField, #acrossDaysField').val('');
                    $('#acrossDaysField').attr('checked', '');

                    console.log("handle new day timeblock:");
                    var currentDay = new Date(beginPeriodDateTimeObj);
                    currentDay.setDate(currentDay.getDate() + parseInt(actionVal));

                    $('#date-range-begin').val($.datepicker.formatDate('mm/dd/yy', currentDay));
                    $('#date-range-end').val($.datepicker.formatDate('mm/dd/yy', currentDay));
                    $('#acrossDaysField').attr('checked', '');
                    if ($('#assignment-value').html() != '') {
                        $('#earnCode').loadEarnCode($('#assignment').val());
                    }

                    $('#tkTimeBlockId').val('');
                    $('#dialog-form').dialog('open');
                } else if (action == "block") {
                    // Handle existing timeblocks
                    // Handle existing timeblocks
                    // Handle existing timeblocks
                    var timeBlockId = parseInt(actionVal);
                    var tblocks = jQuery.parseJSON($('#timeBlockString').val());
                    var calEvent = tblocks[timeBlockId];
                    calEvent.start = Date.parse(calEvent.start);
                    calEvent.end = Date.parse(calEvent.end);
                    console.log(calEvent.start);

                    $('#dialog-form').dialog('open');
                    $('#date-range-begin').val(calEvent.start.toString('MM/dd/yyyy'));
                    $('#date-range-end').val(calEvent.end.toString('MM/dd/yyyy'));
                    $("select#assignment option[value='" + calEvent.assignment + "']").attr("selected", "selected");
                    $('#earnCode').loadEarnCode($('#assignment').val(), calEvent.earnCode + "_" + calEvent.earnCodeType);
                    $('#beginTimeField').val(calEvent.start.toString('hh:mm tt'));
                    $('#endTimeField').val(calEvent.end.toString('hh:mm tt'));
                    $('#tkTimeBlockId').val(calEvent.tkTimeBlockId);
                    $('#hoursField').val(calEvent.hours == '0' ? '' : calEvent.hours);
                    $('#amountField').val(calEvent.amount == '0' ? '' : calEvent.amount);
                    // the month value in the javascript date object is the actual month minus 1.
                    $('#beginTimeField-messages').val(calEvent.start.getHours() + ':' + calEvent.start.getMinutes());
                    $('#endTimeField-messages').val(calEvent.end.getHours() + ':' + calEvent.end.getMinutes());

                    // push existing timeblock values to a hash for the later comparison against the modified values
                    oriTimeDetail = {
                        'startDate' : $('#date-range-begin').val(),
                        'endDate' : $('#date-range-end').val(),
                        'selectedAssignment' : calEvent.assignment,
                        'selectedEarnCode' : calEvent.earnCode,
                        'startTime' : $('#beginTimeField-messages').val(),
                        'endTime' : $('#endTimeField-messages').val(),
                        'hours' : $('#hoursField').val(),
                        'amount' : $('#amountField').val(),
                        'acrossDays' : $('#acrossDaysField').val()
                    };
                }
            }
        }
    });

    var validation = $('#validation');
    var startTime = $('#beginTimeField');
    var endTime = $('#endTimeField');
    var assignment = $('#assignment');
    var assignmentValue = $('#assignment-value').html();
    var earnCode = $('#earnCode');

    //------------------------
    // buttons on the form
    //------------------------
    var buttons = {};
    buttons['Add'] = function() {

        //-----------------------------------
        // time entry form validation
        //-----------------------------------
        var bValid = true;

        function updateTips(t) {
            validation.text(t)
                    .addClass('ui-state-error')
                    .css({'color':'red','font-weight':'bold'});
            // setTimeout(function() {
            //     tips.removeClass('ui-state-error', 1500);
            // }, 1000);
        }

        function checkLength(o, n, min, max) {

            if (o.val().length > max || o.val().length < min) {
                o.addClass('ui-state-error');
                updateTips(n + " field cannot be empty");
                return false;
            }
            return true;
        }

        function checkMinLength(o, n, min) {
            if (o.val().length < min) {
                o.addClass('ui-state-error');
                updateTips(n + " field's value is incorrect");
                return false;
            }
            return true;
        }

        function checkEmptyField(o, n) {
            var val = o.val();
            if (val == '') {
                o.addClass('ui-state-error');
                updateTips(n + " field cannot be empty");
                return false;
            }
            return true;
        }

        function checkRegexp(o, regexp, n) {
            if (( o.val().match(regexp) )) {
                o.addClass('ui-state-error');
                updateTips(n);
                return false;
            }
            return true;
        }

        function checkSpecificValue(o, value, n) {
            if (o.val() != value) {
                o.addClass('ui-state-error');
                updateTips(n);
                return false;
            }
            return true;
        }

        /**
         * Entry field validation
         */
        // if the hour field is empty, there has to be values in the time fields and vice versa
        if ($('#earnCode').getEarnCodeType() === 'TIME') {
            // the format has to be like "12:00 AM"
            bValid &= checkLength(startTime, "Time entry", 8, 8);
            bValid &= checkLength(endTime, "Time entry", 8, 8);
        }
        else if ($('#earnCode').getEarnCodeType() === 'HOUR') {
            var hours = $('#hoursField');
            bValid &= checkEmptyField(hours, "Hour") && checkMinLength(hours, "Hour", 1) && checkRegexp(hours, '/0/', 'Hours cannot be zero');
        }
        else {
            var amount = $('#amountField');
            bValid &= checkEmptyField(amount, "Amount") && checkMinLength(amount, "Amount", 1) && checkRegexp(amount, '/0/', 'Amount cannot be zero');
        }

        bValid &= checkEmptyField(assignment, "Assignment");
        bValid &= checkEmptyField(earnCode, "Earn Code");
        // ------------------- end of time entry form validation -------------------

        if (bValid) {

            var params = {};
            // if the end time is 12:00 am, move the end date to the next day
            var endTimeValue = $('#endTimeField-messages').val().toUpperCase();
            var endDateValue = $('#date-range-end').val();
            if (endTimeValue == "0:0") {
                var dateRangeField = endDateValue.split("/");
                var dateString = parseInt(dateRangeField[1]) + 1;
                endDateValue = dateRangeField[0] + "/" + dateString + "/" + dateRangeField[2];
            }
            // these are for the submitted form
            $('#methodToCall').val('addTimeBlock');
            $('#startDate').val($('#date-range-begin').val());
            $('#endDate').val(endDateValue);
            $('#startTime').val($('#beginTimeField-messages').val());
            $('#endTime').val(endTimeValue);
            $('#hours').val($('#hoursField').val());
            $('#amount').val($('#amountField').val());
            $('#selectedEarnCode').val($('#earnCode').getEarnCode());
            $('#selectedAssignment').val($('#assignment').val());
            $('#acrossDays').val($('#acrossDaysField').is(':checked') ? 'y' : 'n');

            // these are for the validation
            params['startDate'] = $('#date-range-begin').val();
            params['endDate'] = endDateValue;
            params['startTime'] = $('#beginTimeField-messages').val();
            params['endTime'] = endTimeValue;
            params['hours'] = $('#hoursField').val();
            params['amount'] = $('#amountField').val();
            params['selectedEarnCode'] = $('#earnCode').getEarnCode();
            params['selectedAssignment'] = $('#assignment').val();
            params['acrossDays'] = $('#acrossDaysField').is(':checked') ? 'y' : 'n';
            params['tkTimeBlockId'] = $('#tkTimeBlockId').val();

            // compare the original values with the modified ones. if there is no change, close the form instead of submitting it
            var isNotChanged = true;
            for (var key in oriTimeDetail) {
                if (params[key] != oriTimeDetail[key]) {
                    isNotChanged = false;
                    break;
                }
            }

            if (!$.isEmptyObject(oriTimeDetail) && isNotChanged) {
                $(this).dialog('close');
                return false;
            }

            // validate timeblocks
            $.ajax({
                url: "TimeDetail.do?methodToCall=validateTimeEntry",
                data: params,
                cache: false,
                success: function(data) {
                    //var match = data.match(/\w{1,}|/g);
                    var json = jQuery.parseJSON(data);
                    // if there is no error message, submit the form to add the time block
                    if (json.length == 0) {
                        $('#time-detail').submit();
                    }
                    else {
                        // if there is any error, grab error messages (json) and display them
                        var json = jQuery.parseJSON(data);
                        var errorMsgs = '';
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
        }
    };

    buttons['Cancel'] = function() {
        $(this).dialog('close');
        $(this).resetState();
    };
    // ------------------- end of the buttons -------------------

    $('#dialog-form').dialog({
        autoOpen: false,
        height: 'auto',
        width: 'auto',
        modal: true,
        beforeClose: function(event, ui) {
            // restore the earn code value to the default
            $('#earnCode').html("<option value=''>-- select an assignment first --</option>");
        },
        buttons: buttons
    });

    /**
     * The section below is to handle the time/hour input
     * 1. When the form is submitted, it should only contain either begin/end time (for regular earn codes) or hours (for vac, sck, etc.)
     *    - When the a regular earn code is selected, the hour/amount field will be hidden and vice versa
     *    - When the value is changed in the time field, the value in the hour/amount field should be deleted and vice versa
     */

    // when the value of time fields(s) is changed, reset the hour/amount field and vice versa
    $('#beginTimeField, #endTimeField, #hoursField, #amountField').change(function() {
        if ($(this).get(0).id == 'beginTimeField' || $(this).get(0).id == 'endTimeField') {
            $('#hoursField').val('');
            $('#amountField').val('');
            // do the input conversion
            magicTime($(this));
        }
        else {
            $('#beginTimeField, #endTimeField').val('');
        }
        $(this).resetState();
    });
    // ------------------- end of the time/hour input validation section -------------------

    // load the earn code based on the selected assignment
    $('#assignment').change(function() {
        // if there is error triggered previously, reset the
        $('#assignment').removeClass('ui-state-error');
        $('#earnCode').loadEarnCode($(this).val());
        $(this).resetState();
    });

    var currentEarnCodeType = null;

    $('select#earnCode').click(function() {
        currentEarnCodeType = $(this).getEarnCodeType();
    });

    // hide/show the related fields based on the earn code type
    $('select#earnCode').change(function() {
        $(this).loadFields();
        // KPME-204
        // clear the existing values only when the earn code type is different
        if ($(this).getEarnCodeType() != currentEarnCodeType) {
            $('#beginTimeField, #endTimeField, #hoursField, #amountField').val('');
        }
        $(this).resetState();
    });

    // check the acrossDay box if the start/end dates are different
    $('#date-range-begin, #date-range-end').change(function() {
        var startDate = $('#date-range-begin').val();
        var endDate = $('#date-range-end').val();

        if (startDate != endDate) {
            $('#acrossDaysField').attr('checked', 'checked');
        }
    });

    /**
     * Misc. section
     */

    // use keyboard to open the form
    var isCtrl,isAlt = false;

    // ctrl+alt+a will open the form
    $(this).keydown(
            function(e) {

                if (e.ctrlKey) isCtrl = true;
                if (e.altKey) isAlt = true;

                if (e.keyCode == 65 && isCtrl && isAlt) {
                    $('#dialog-form').dialog('open');
                }

            }).keyup(function(e) {
        isCtrl = false;
        isAlt = false;
    });

    // ------------------- end of the misc. section -------------------

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

            $('#earnCode').loadFields();
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

$.fn.loadFields = function() {

    // get the earn code type
    var fieldType = $(this).val().split("_")[1];

    // if the field type equals hour, hide the begin/end time field and set the time to 12a
    if (fieldType == 'HOUR') {
        $('#clockIn, #clockOut, #amountSection').hide();
        $('#beginTimeField-messages,#endTimeField-messages').val("23:59");
        $('#amountSection').val('');
        $('#hoursSection').show();
        $('#hoursField').validateNumeric();
    }
    else if (fieldType == 'AMOUNT') {
        $('#clockIn, #clockOut, #hoursSection').hide();
        $('#beginTimeField-messages,#endTimeField-messages').val("23:59");
        $('#hoursSection').val('');
        $('#amountSection').show();
        $('#amountField').validateNumeric();
    }
    else {
        $('#clockIn, #clockOut').show();
        $('#hoursSection').hide();
        $('#amountSection').hide();
    }
}

$.fn.deleteTimeBlock = function(tkTimeBlockId) {
    var params = {};
    params['methodToCall'] = 'deleteTimeBlock';
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

// reset the field state to the default
$.fn.resetState = function() {

    // clear the red background for fields with wrong inputs
    // .end() is a jQuery built-in function which does:
    // "End the most recent filtering operation in the current chain and return the set of matched elements to its previous state."
    $('#timesheet-panel').find('input').end().find('select').each(function() {
        $(this).removeClass('ui-state-error');
    });

    // clear the error messages
    $('.error').html('');
    // remove the error message and error state
    $('#validation').html('').removeClass('ui-state-error');
}

// clear the value of the specific field. If the field is not provided, all the input values in the $('#timesheet-panel') will be reset.
$.fn.resetValue = function(field) {
    if (field != undefined) {
        field.val('');
    } else {
        $('#timesheet-panel').find('input').val('');
    }
}

$.fn.validateNumeric = function() {

    $(this).keyup(function() {
        var val = $(this).val();
        // replace anything with "" unless it's 0-9 or .
        val = val.replace(/[^0-9\.]*/g, '');
        // deal with the case when there is only .
        val = val.replace(/^\.$/g, '');
        $(this).val(val);
    });
}

$.fn.getEarnCode = function() {
    return $(this).val().split("_")[0];
}

$.fn.getEarnCodeType = function() {
    return $(this).val().split("_")[1];
}