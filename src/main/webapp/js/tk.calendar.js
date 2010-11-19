$(document).ready(function() {

    // calendar
    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    var beginPeriodDate = $("#beginPeriodDate").val() !== undefined ? $("#beginPeriodDate").val() : d + "/" + m + "/" + y;
    var endPeriodDate = $("#endPeriodDate").val() !== undefined ? $("#endPeriodDate").val() : d + "/" + m + "/" + y;
    var beginPeriodDateTimeObj = new Date($("#beginPeriodDate").val()); 
    var endPeriodDateTimeObj = new Date($("#endPeriodDate").val());

    var calendar = $('#cal').fullCalendar({
            beginPeriodDate : beginPeriodDate,
            endPeriodDate : endPeriodDate,
            theme : true,
            aspectRatio : 5, // the value here is just to match the height with the add time block panel
            allDaySlot : false,
            multidaySelect : true,
            header: {
                  left : "prev, today",
                  center : 'title',
                  right : ''
            },
            selectable: true,
            selectHelper: true,
            select: function(start, end, allDay) {
                
                // clear any existing values
                $('#beginTimeField, #endTimeField, #hoursField, #acrossDaysField').val("");
                $('#acrossDaysField').attr('checked','');
                // check acrossDaysField if multiple days are selected
                if(start.getTime() != end.getTime()) {
                    $('#acrossDaysField').attr('checked','checked');
                }
                // disable showing the time entry form if the date is out of the pay period
                if(start.getTime() >= beginPeriodDateTimeObj.getTime() && end.getTime() <= endPeriodDateTimeObj.getTime()) {
                    $('#dialog-form').dialog('open');
                }
            },
            editable: false,
            events : "TimeDetail.do?methodToCall=getTimeblocks",
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

    var fieldsToValidate = $([]).add(startTime).add(endTime);
    fieldsToValidate.val('').removeClass('ui-state-error');

    $("#dialog-form").dialog({
        autoOpen: false,
        height: 'auto',
        width: 'auto',
        modal: true,
        buttons: {
            Add: function() {

                var bValid = true;
                fieldsToValidate.removeClass('ui-state-error');
                tips.val("");

                function updateTips(t) {
                    tips
                        .text(t)
                        .addClass('ui-state-error');                        ;
                    setTimeout(function() {
                        tips.removeClass('ui-state-error', 1500);
                    }, 1000);
                }

                function checkLength(o,n,min,max) {
                    if ( o.val().length > max || o.val().length < min ) {
                        o.addClass('ui-state-error');
                        updateTips(n + " field can't be empty");
                        return false;
                    } else {
                        return true;
                    }
                }
                
                function checkTimeEntryFields(o,n) {
                    var val = o.val();
                    o.addClass('ui-state-error');
                    if(val == '') {
                        updateTips(n + " field can't be empty");
                        return false;
                    }
                    return true;
                }

                function checkRegexp(o,regexp,n) {
                    if ( !( regexp.test( o.val() ) ) ) {
                        o.addClass('ui-state-error');
                        updateTips(n);
                        return false;
                    } else {
                        return true;
                    }
                }

                if($('#hoursField').val() === '') {
                	bValid = bValid && checkLength(startTime,"In",8,8);
                    bValid = bValid && checkLength(endTime,"Out",8,8);
                }
                
                bValid = bValid && checkTimeEntryFields($('#assignment'), "Assignment");
                bValid = bValid && checkTimeEntryFields($('#earnCode'), "Earn Code");
                
                if($('#hoursField').val() !== '' || bValid) {             
                    var params = {};
                    var dateRangeStart = $("#date-range-begin").val().split("/");
                    var dateRangeEnd = $("#date-range-end").val().split("/");
                    var start = startTime.parseTime();
                    var end = endTime.parseTime();
                    
                    var startDateTime = new Date();
                    startDateTime.setFullYear(dateRangeStart[2], dateRangeStart[0]-1, dateRangeStart[1]);
                    startDateTime.setHours(start.hour);
                    startDateTime.setMinutes(start.minute);
                    startDateTime.setSeconds(0);
                    startDateTime.setMilliseconds(0);
                    var endDateTime = new Date();
                    endDateTime.setFullYear(dateRangeEnd[2], dateRangeEnd[0]-1, dateRangeEnd[1]);
                    endDateTime.setHours(end.hour);
                    endDateTime.setMinutes(end.minute);
                    endDateTime.setSeconds(0);
                    endDateTime.setMilliseconds(0);
                    
                    $("#methodToCall").val("addTimeBlock");
                    $("#startDate").val($("#date-range-begin").val());
                    $("#endDate").val($("#date-range-end").val());
                    $("#startTime").val(startDateTime.getTime());
                    $("#endTime").val(endDateTime.getTime());
                    $("#hours").val($('#hoursField').val());
                    $("#selectedEarnCode").val($("#earnCode").val().split("_")[0]);
                    $("#selectedAssignment").val($("#assignment").val());
                    $("#acrossDays").val($('#acrossDaysField').is(':checked') ? 'y' : 'n');
                    
                    $("#time-detail").submit();
                    
                    fieldsToValidate.val('').removeClass('ui-state-error');
                }
            },
            Cancel: function() {
                $('#beginTimeField').val("");
                $('#endTimeField').val("");
                $(this).dialog('close');
                fieldsToValidate.val('').removeClass('ui-state-error');
            }
        }
    });
    
    // earn code
    $("select#earnCode").change(function(){

        $('#hoursField').attr('readonly',false).css('background',"white").val("");

        var fieldType = $(this).val().split("_")[1];

        if(fieldType == 'HOUR') {
            $('#beginTimeField,#endTimeField').val("");
            $('#clockIn, #clockOut').hide();
            $('#hoursSection').show();
        }
        // TODO: need to handle the amount field
        else {
            $('#hours').val("");
            $('#clockIn, #clockOut').show();
            $('#hoursSection').hide();
        }

        $("select#earnCode option[value='" + $(this).val() +"']").attr("selected", "selected");
    });

    // filter earn codes
    $('#assignment').change(function(){
        // remove the error style
        $('#assignment').removeClass('ui-state-error');
        
        var params = {};
        params['selectedAssignment'] = $(this).val();
        
        $.ajax({
            url: "TimeDetail.do?methodToCall=getEarnCodes",
            data: params,
            cache: false,
            success: function(data) {
                $('#earnCode').html(data);
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
    });

    // error
    $("#1").addClass('block-error');

    // use keyboard to open the form
    var isCtrl,isAlt = false;

    // ctrl+alt+a will open the form
    $(this).keydown(function(e){

        if(e.ctrlKey) isCtrl = true;
        if(e.altKey) isAlt = true;

        if(e.keyCode == 65 && isCtrl && isAlt) {
            $("#dialog-form").dialog('open');
        }

    }).keyup(function(e){
        isCtrl = false;
        isAlt = false;
    });

});

$.fn.createDeleteButton= function() {
    $("#delete-button").button({
        icons : {
            primary : 'ui-icon-close'
        },
        text : false
    });
}
