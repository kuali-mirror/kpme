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
                  left : 'prev, today',
                  center : 'title',
                  right : 'month,agendaWeek,agendaDay'
            },
            selectable: true,
            selectHelper: true,
            select: function(start, end, allDay) {

                // clear any existing values
                $('#beginTimeField, #endTimeField, #hoursField').val("");

                $('#dialog-form').dialog('enable');

                var form = $('#dialog-form').dialog('open');

                form.dialog({
                    beforeclose: function(event, ui) {
                        var title;
                        var startTime = $('#beginTimeField');
                        var endTime = $('#endTimeField');
                        var hours = $('#hoursField');

                        // this is for non-clock in / out earn codes, like vac, sick, etc.
                        if(hours.val() != '') {
                            title = $('#assignment').val() + " - " + $('#earnCode').val();

                            start.setHours(9);
                            start.setMinutes(0);

                            end.setHours(9+Number(hours.val()));
                            end.setMinutes(0);

                            calendar.fullCalendar('renderEvent',
                              {
                                  title: title,
                                  start: start,
                                  end: end,
                                  allDay: false
                              },
                              true // make the event "stick"
                            );
                            calendar.fullCalendar('unselect');
                        }

                        // this is for stardard clock in / out earn codes
                        if(startTime.val() != '' && endTime.val() != '') {

                            startTime = $('#beginTimeField').parseTime();
                            endTime = $('#endTimeField').parseTime();

                            title = $('#assignment').val() + " - " + $('#earnCode').val();

                            start.setHours(startTime['hour']);
                            start.setMinutes(startTime['minute']);

                            end.setHours(endTime['hour']);
                            end.setMinutes(endTime['minute']);

                            calendar.fullCalendar('renderEvent',
                              {
                                  title: title,
                                  start: start,
                                  end: end,
                                  allDay: false
                              },
                              true // make the event "stick"
                            );
                            calendar.fullCalendar('unselect');
                        }

                        //TODO: need to deal with week and day view where the clock in and out fields should be hidden
                        // var view = calendar.fullCalendar('getView');
                    }
                });
            },
            editable: false,
	        events :
            [
                {
					title : 'HRMS Java developer: RGN',
					start : new Date(y, m, d, 8, 00),
					end : new Date(y, m, d, 17, 00),
					allDay : false,
					id : 1 // this could be the unique sequence number from the table
				},
                {
					title : 'HRMS PS developer: RGN',
					start : new Date(y, m, d, 12, 0),
					end : new Date(y, m, d, 13, 0),
					allDay : false,
					id : 2
			    }
            ]
        });

    $(".delete-button").click(function(){
// var id = $(this).val();
// calendar.fullCalendar('removeEvent');
        // TODO: do an ajax call do delete the event
    });

    var tips = $(".validateTips");
    var startTime = $('#beginTimeField');
    var endTime = $('#endTimeField');

    var fieldsToValidate = $([]).add(startTime).add(endTime);
    fieldsToValidate.val('').removeClass('ui-state-error');

    $("#dialog-form").dialog({
        autoOpen: false,
        height: 400,
        width: 400,
        modal: true,
        buttons: {
            Add: function() {

                var bValid = true;
                fieldsToValidate.removeClass('ui-state-error');

                function updateTips(t) {
                    tips
                        .text(t)
                        .addClass('ui-state-highlight');
                    setTimeout(function() {
                        tips.removeClass('ui-state-highlight', 1500);
                    }, 500);
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

                bValid = bValid && checkLength(startTime,"In",8,8);
                bValid = bValid && checkLength(endTime,"Out",8,8);

                if(bValid) {
                    $(this).dialog('close');
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
    }).createDeleteButton();

});

$.fn.createDeleteButton= function() {
    $(".delete-button").button({
        icons : {
            primary : 'ui-icon-close'
        },
        text : false
    });
}