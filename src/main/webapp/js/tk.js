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
	$(".button").button();

	$("button").button({
		icons : {
			primary : 'ui-icon-help'
		},
		text : false
	});

    $(".expand").button({
        icons: {
            primary: 'ui-icon-plus'
        }            
    });
    
	// datepicker
	$('#date-range-begin, #date-range-end').datepicker({
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
//    if($("#clock-button").val() == "Clock Out") {
//        $("#lastClockedInTime").val("");
//        $("#elapsed-time").val("00:00:00");
//    }
    var lastClockedInTime = $("#lastClockedInTime").val();
    var clockAction = $("#clockAction").val();
    var startTime = clockAction == 'CO' ?  new Date(lastClockedInTime) : new Date() ;

	$('.elapsedTime').countdown({
				since : startTime,
				compact : true,
				format : 'dHMS',
				description : ''
	})

	// tooltip
    // http://flowplayer.org/tools/tooltip/index.html
	$("#beginTimeHelp, #endTimeHelp").tooltip({

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
	

    // summary table
    // $('a#basic').click(function(){
    //     var options = {};
    //     $('#timesheet-table-basic').fadeIn();
    //     $('#timesheet-table-advance').hide();
    // });
    // 
    // $('a#advance').click(function(){
    //     var options = {};
    //     $('#timesheet-table-advance').fadeIn();
    //     $('#timesheet-table-basic').hide();
    // });
    
    // show-hide earn codes in the approval page
    $("#fran-button").click(function() {
        $(".fran").toggle();
        $("#fran-button span").toggleClass('ui-icon-minus');
    });
    $("#frank-button").click(function() {
        $(".frank").toggle();
        $("#frank-button span").toggleClass('ui-icon-minus');
    });
    // apply time entry widget to the tabular view
    $(".timesheet-table-week1 :input, .timesheet-table-week2 :input").blur(function(){
        magicTime(this);
    }).focus(function(){
        if(this.className != 'error') this.select();
    });
});

$.fn.parseTime= function() {
    var parsedTime = new Array();
    var timeAndAmPm = $(this).val().split(" ");
    var time = timeAndAmPm[0].split(":");
    
    if(timeAndAmPm == '') {
    	parsedTime['hour'] = 00;
    	parsedTime['minute'] = 00;
    }
    else {
	    if(timeAndAmPm[1] == 'PM') {
	        parsedTime['hour'] = Number(time[0]) == 12 ? time[0] : Number(time[0]) + 12;
	    }
	    else {
	        parsedTime['hour'] = Number(time[0]);
	    }
	    parsedTime['minute'] = Number(time[1]);
    }
    return parsedTime;
}