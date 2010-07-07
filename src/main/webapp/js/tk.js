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
		$("#tabs ul > #" + tabId)
				.addClass("ui-tabs-selected ui-state-focus ui-state-active");
	}
	// end of tab

	// buttons
	$(".button").button();

	$("button").button({
				icons : {
					primary : 'ui-icon-help'
				},
				text : false
			});

	// calendar
	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();

//	$('#cal').fullCalendar({
//				theme : true,
//				aspectRatio : 5, // the value here is just to match the height with the add time block panel
//				allDaySlot : false,
//				header : {
//					left : 'prev, today',
//					center : 'title',
//					right : 'payPeriod'
//				},
//                selectable: true,
//	            selectHelper: true,
//	            select: function(start, end, allDay) {
//	                var title = prompt('Event Title:');
//	                if (title) {
//	                    calendar.fullCalendar('renderEvent',
//	                        {
//	                            title: title,
//	                            start: start,
//	                            end: end,
//	                            allDay: allDay
//	                        },
//	                        true // make the event "stick"
//	                    );
//	                }
//	                calendar.fullCalendar('unselect');
//	            },
//                editable: true,
//				events : [{
//							title : 'HRMS Java developer: RGN',
//							start : new Date(y, m, d, 8, 00),
//							end : new Date(y, m, d, 17, 00),
//							allDay : false
//						}, {
//							title : 'HRMS PS developer: RGN',
//							start : new Date(y, m, d, 12, 0),
//							end : new Date(y, m, d, 13, 0),
//							allDay : false
//						}
//				]
//			});
    var calendar = $('#cal').fullCalendar({
            theme : true,
            aspectRatio : 5, // the value here is just to match the height with the add time block panel
            allDaySlot : false,
            header: {
                  left : 'prev, today',
                  center : 'title',
                  right : 'payPeriod'
            },
            selectable: true,
            selectHelper: true,
            select: function(start, end, allDay) {
//                var title = prompt('Event Title:');
//                if (title) {
//                    calendar.fullCalendar('renderEvent',
//                        {
//                            title: title,
//                            start: start,
//                            end: end,
//                            allDay: allDay
//                        },
//                        true // make the event "stick"
//                    );
//                }
                $('#dialog-form').dialog('open');
                calendar.fullCalendar('unselect');
            },
            editable: true,
           events : [{
                          title : 'HRMS Java developer: RGN',
                          start : new Date(y, m, d, 8, 00),
                          end : new Date(y, m, d, 17, 00),
                          allDay : false
                      }, {
                          title : 'HRMS PS developer: RGN',
                          start : new Date(y, m, d, 12, 0),
                          end : new Date(y, m, d, 13, 0),
                          allDay : false
                      }
            ]
        });

    $("#dialog-form").dialog({
        autoOpen: false,
        height: 420,
        width: 400,
        modal: true,
        buttons: {
            'Add time': function() {
            },
            Cancel: function() {
                $(this).dialog('close');
            }
        },
        close: function() {
            allFields.val('').removeClass('ui-state-error');
        }
    });

	// datepicker
	$('#timesheet-beginDate, #timesheet-endDate').datepicker({
				changeMonth : true,
				changeYear : true,
				showOn : 'button',
				showAnim : 'fadeIn',
				buttonImage : 'kr/static/images/cal.gif',
				buttonImageOnly : true,
				buttonText : 'Choose a date',
				showButtonPanel : true,
				numberOfMonths : 2,
				// set default month based on the current browsing month
				// appendText : '<br/>format: mm/dd/yyyy',
				constrainInput : false,
				minDate : -7,
				maxDate : +7
			});

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
	var options = {
		format : '%I:%M:%S %p'
	};
	$(".jClock").jclock(options);

	// elapsed time
	// http://keith-wood.name/countdown.html

	var lastClockedInTime = $("#lastClockedInTime").val();
	var startTime = new Date(lastClockedInTime);
	$('.elapsedTime').countdown({
				since : startTime,
				compact : true,
				format : 'dHMS',
				description : ''
			});

	// tooltip
	$("#beginTimeHelp, #endTimeHelp").tooltip({

				// place tooltip on the right edge
				position : "center right",

				// a little tweaking of the position
				offset : [-2, 10],

				// use the built-in fadeIn/fadeOut effect
				effect : "fade",

				// custom opacity setting
				opacity : 0.7

			});

	// note
	$("#note").accordion({
				collapsible : true,
				active : 2
			});

    // summary table
    $('a#basic').click(function(){
        var options = {};
        $('#timesheet-table-basic').fadeIn();
        $('#timesheet-table-advance').hide();
    });

    $('a#advance').click(function(){
        var options = {};
        $('#timesheet-table-advance').fadeIn();
        $('#timesheet-table-basic').hide();
    });

});