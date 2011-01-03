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
                  // left : "today",
                  left : "",
                  center : 'prev, title, next',
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
                
                // if the virtual day mode is true, set the start hour the same as the pay period time
                if($('#isVirtualWorkDay').val() == 'true') {
                    start.setHours(beginPeriodDateTimeObj.getHours());
                    end.setHours(endPeriodDateTimeObj.getHours());
                }
                
                // disable showing the time entry form if the date is not within the pay period
                if(start.getTime() >= beginPeriodDateTimeObj.getTime() && end.getTime() <= endPeriodDateTimeObj.getTime()) {
                    
                    // if there is only one assignment, get the earn code without selecting the assignment
                    if($('#assignment-value').html() != '') {
                        var params = {};
                        params['selectedAssignment'] = $('#assignment').val();
                        
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
                    }
                        
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
    var assignment = $('#assignment');
    var assignmentValue = $('#assignment-value').html(); 
    var earnCode = $('#earnCode');

    var fieldsToValidate = $([]).add(startTime).add(endTime).add(earnCode);
    fieldsToValidate.clearValue();

    $("#dialog-form").dialog({
        autoOpen: false,
        height: 'auto',
        width: 'auto',
        modal: true,
        beforeClose: function(event,ui) {
            // remove all the error messages
            $('.validateTips').html("").removeClass('ui-state-error');
            // restore the earn code value to the default  
            $('#earnCode').html("<option value=''>-- select an assignment --</option>");
        },
        buttons: {
            Add: function() {
                
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
                
                bValid = bValid && checkTimeEntryFields(assignment, "Assignment");
                bValid = bValid && checkTimeEntryFields(earnCode, "Earn Code");
                
                // end of time entry form validation
                //-----------------------------------
                
                if($('#hoursField').val() !== '' || bValid) {             
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
                    
                    $.ajax({
                        url: "TimeDetail.do?methodToCall=validateTimeBlocks",
                        data: params,
                        cache: false,
                        success: function(data) {
                            //var match = data.match(/\w{1,}|/g);
                            var json = jQuery.parseJSON(data);
                            // if there is no error message, submit the form to add the time block
                            if(json.length == 0) {
                                $("#time-detail").submit();
                            }
                            else {
                                // grab error messages
                                var json = jQuery.parseJSON(data);
                                var errorMsgs = "";
                                $.each (json, function (index) {
                                    errorMsgs += "Error : " + json[index] + "\n";                                    
                                });
                                
                                updateTips(errorMsgs);
                                
                                return false;
                            }
                        },
                        error: function() {
                            updateTips("Error: Can't save data.");
                        }
                    });
                    
                    fieldsToValidate.clearValue($('#assignment'));
                }
            },
            Cancel: function() {
                fieldsToValidate.clearValue();
                $(this).dialog('close');
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

$.fn.clearValue= function(elementToAdd) {
    var assignmentValue = $('#assignment-value').html();
    // clear assignment value when there is only one assignment 
    if(assignmentValue != undefined && assignmentValue != '') {
        assignmentValue.html('').removeClass('ui-state-error');    
    }
    
    // clear the error message
    $('.validateTips').html("").removeClass('ui-state-error');



    // reset the assignment when there are multiple ones 
    if(elementToAdd != undefined && elementToAdd.is("select")) {      
      // $(this).val('').removeClass('ui-state-error');
      // it doesn't seem necessary to change the value to the default empty string for now.
      $(this).add(elementToAdd).removeClass('ui-state-error');
    }
    
}

$.fn.createDeleteButton= function() {
    $("#delete-button").button({
        icons : {
            primary : 'ui-icon-close'
        },
        text : false
    });
}
