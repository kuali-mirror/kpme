$(document).ready(function() {

    // calendar
    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    var beginPeriodDate = $("#beginPeriodDate").val() != undefined ? $("#beginPeriodDate").val() : d + "/" + m + "/" + y;
    var endPeriodDate = $("#endPeriodDate").val() != undefined ? $("#endPeriodDate").val() : d + "/" + m + "/" + y;

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
                  right : 'month,agendaWeek,agendaDay'
            },
            selectable: true,
            selectHelper: true,
            select: function(start, end, allDay) {
                
                // clear any existing values
                $('#beginTimeField, #endTimeField, #hoursField').val("");
                $('#dialog-form').dialog('enable');

                var title;
                var startTime = $('#beginTimeField');
                var endTime = $('#endTimeField');
                var hours = $('#hoursField');

                // reset assignment and earn code
                $('#assignment > option:first').attr('selected','selected');
                
            	var params = {};
            	
                params['selectedAssignment'] = $('#selectedAssignment').val() || $('#assignment:first').val();
            	
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
        		
                var form = $('#dialog-form').dialog('open');

                form.dialog({
                    beforeclose: function(event, ui) {
                        //TODO: need to deal with week and day view where the clock in and out fields should be hidden
                        // var view = calendar.fullCalendar('getView');
                    }
                });
            },
            editable: false,
//            eventClick: function(calEvent, jsEvent, view) {
//                alert('clicked');
//            },
//	        events :
//            [
//                {
//					title : 'HRMS Java developer: RGN',
//					start : new Date(y, m, d, 8, 00),
//					end : new Date(y, m, d, 17, 00),
//					allDay : false,
//					id : 1 // this could be the unique sequence number from the table
//				},
//                {
//					title : 'HRMS PS developer: RGN',
//					start : new Date(y, m, d, 12, 0),
//					end : new Date(y, m, d, 13, 0),
//					allDay : false,
//					id : 2
//			    }
//            ]
            events : "TimeDetail.do?methodToCall=getTimeblocks",
            loading: function(bool) {
                if (bool) $('#loading').show();
                else $('#loading').hide();
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
                        .addClass('ui-state-highlight');
                    setTimeout(function() {
                        tips.removeClass('ui-state-highlight', 1500);
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

                

                if($('#hoursField').val() == '') {
                	bValid = bValid && checkLength(startTime,"In",8,8);
                    bValid = bValid && checkLength(endTime,"Out",8,8);
                }
                
                if($('#hoursField').val() != '' || bValid) {             
                    var params = {};
                    var dateRangeStart = $("#date-range-begin").val().split("/");
                    var dateRangeEnd = $("#date-range-end").val().split("/");
                    var start = startTime.parseTime();
                    var end = endTime.parseTime();
                    
                    var startDateTime = new Date();
                    startDateTime.setFullYear(dateRangeStart[2], dateRangeStart[0]-1, dateRangeStart[1]);
                    startDateTime.setHours(start['hour']);
                    startDateTime.setMinutes(start['minute']);
                    startDateTime.setSeconds(0)
                    startDateTime.setMilliseconds(0);
                    var endDateTime = new Date();
                    endDateTime.setFullYear(dateRangeEnd[2], dateRangeEnd[0]-1, dateRangeEnd[1]);
                    endDateTime.setHours(end['hour']);
                    endDateTime.setMinutes(end['minute']);
                    endDateTime.setSeconds(0)
                    endDateTime.setMilliseconds(0);
                    
                    $("#methodToCall").val("addTimeBlock");
                    $("#startDate").val($("#date-range-begin").val());
                    $("#endDate").val($("#date-range-end").val());
                    $("#startTime").val(startDateTime.getTime());
                    $("#endTime").val(endDateTime.getTime());
                    $("#hours").val($('#hoursField').val());
                    $("#selectedEarnCode").val($("#earnCode").val());
                    $("#selectedAssignment").val($("#assignment").val());
                    $("#acrossDays").val($('#acrossDaysField').is(':checked') ? 'y' : 'n');
                    
                    $("#time-detail").submit();
                    
                    // params['startDate'] = $("#date-range-begin").val();
                    // params['endDate'] = $("#date-range-end").val();
                    // params['startTime'] = startDateTime.getTime();
                    // params['endTime'] = endDateTime.getTime();
                    // params['hours'] = $('#hoursField').val();
                    // params['selectedEarnCode'] = $("#earnCode").val();
                    // params['selectedAssignment'] = $("#assignment").val();
                    // params['acrossDays'] = $('#acrossDays').is(':checked') ? 'y' : 'n';

	                //                     $.ajax({
	                //     url: "TimeDetail.do?methodToCall=addTimeBlock",
	                //     data: params,
	                //     cache: false,
	                //     success: function() {
	                //     	$("#dialog-form").dialog('close');
	                //     	calendar.fullCalendar('refetchEvents');
	                //     },
	                //                         error: function() {
	                //                         	updateTips("Error: Can't save data.");
	                //                         }
	                // });

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