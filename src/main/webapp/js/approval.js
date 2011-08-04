$(document).ready(function() {
    // sort by the clicked column
    $('#approvals-table tr th').click(function() {

        var field = $(this).text().replace(/ /, '');
        var rows = $('#approvals-table tbody tr').length;
        var isAscending = getParameterByName("ascending");

        if (isAscending == null) {
            isAscending = true;
        } else if (isAscending == "true") {
            isAscending = false;
        } else {
            isAscending = true;
        }

        // submit the page for sorting
        if (field != 'Action' && field != 'Select') {
            window.location = 'TimeApproval.do?sortField=' + field + '&ascending=' + isAscending + '&rowsToShow=' + rows + "&payCalendarId=" + $("#pcid").val() + "&payCalendarEntriesId=" + $("#pceid").val() + "&selectedPayCalendarGroup=" + $("#selectedPayCalendarGroup").val();
        }
    });

    // this is to determine which fields are sortable
    $('#approvals-table tr th').filter(
            function(index) {
                return $(this).html().replace(/ /, '') == "DocumentId" || $(this).html().replace(/ /, '') == "PrincipalName" || $(this).html().replace(/ /, '') == "Status";
            }).addClass("sort");

    // add acs/desc icon to a sorted field
    if (getParameterByName("ascending") != '') {
        var klass = getParameterByName("ascending") == "true" ? 'headerSortDown' : 'headerSortUp';

        $('#approvals-table tr th').filter(
                function(index) {
                    return $(this).html().replace(/ /, '') == getParameterByName("sortField");
                }).addClass(klass);
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

            $.post('TimeApproval.do?methodToCall=searchApprovalRows&searchField=' + $('#searchField').val() + '&searchTerm=' + request.term +
                    '&selectedPayCalendarGroup=' + $('#selectedPayCalendarGroup').val() + "&ajaxCall=true&payCalendarId=" + $("#pcid").val() + "&payCalendarEntriesId=" + $("#pceid").val() + "&selectedPayCalendarGroup=" + $("#selectedPayCalendarGroup").val(),
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
            var rows = $('#approvals-table tbody tr').length;
            var isAscending = getParameterByName("ascending");

            window.location = 'TimeApproval.do?searchField=' + $('#searchField').val() + '&searchTerm=' + data.item.value +
                    '&sortField=' + $('#searchField').val() + '&ascending=' + isAscending + '&rowsToShow=' + rows + "&payCalendarId=" + $("#pcid").val() + "&payCalendarEntriesId=" + $("#pceid").val() + "&selectedPayCalendarGroup=" + $("#selectedPayCalendarGroup").val();
        },
        open: function() {
            $(this).removeClass("ui-corner-all");
        },
        close: function() {
            $(this).removeClass("ui-corner-top");
        }
    });

    // check if the approve button is disabled. if so, disable the select checkbox as well
    $('#actions input[type=button]').filter(
            function(index) {
                return $(this).prop("disabled");
            }).parent().parent().parent().find("input[type=checkbox]").attr("disabled", "disabled");

    // select the whole row when the select checkbox is checked
    $('.selectedEmpl').click(function() {
        $(this).parent().parent().find("td").toggleClass("highlight")
    });

    // select All
    $('#selectAll').click(function() {
        $("input[name=selectedEmpl]").each(function() {
            // only select the rows where the docs are in route
            if ($(this).prop("disabled") !== true) {
                this.checked = true;
                $(this).parent().parent().find("td").addClass("highlight");
            }
        });

        if ($(this).prop("checked") == false) {
            $("#approvals-table tr td").removeClass("highlight");
            $("input[name=selectedEmpl]").attr("checked", false);
        }
    });

    // buttons for prev / next pay calendar entries
    $('.prev').button({
        icons: {
            primary: "ui-icon-circle-triangle-w"
        },
        text: false
    });

    $('.prev').click(function() {
        window.location = "TimeApproval.do?calNav=prev&payCalendarId=" + $("#pcid").val() + "&payCalendarEntriesId=" + $("#pceid").val() + "&selectedPayCalendarGroup=" + $("#selectedPayCalendarGroup").val();
    });

    $('.next').button({
        icons: {
            primary: "ui-icon-circle-triangle-e"
        },
        text: false
    });

    $('.next').click(function() {
        window.location = "TimeApproval.do?calNav=next&payCalendarId=" + $("#pcid").val() + "&payCalendarEntriesId=" + $("#pceid").val()  + "&selectedPayCalendarGroup=" + $("#selectedPayCalendarGroup").val();
    });

    $(" .approvals-warning, .approvals-note").tooltip({ effect: 'slide'});

    // toggle the button for the assigment details
    $('.rowInfo').click(function() {
        if ($(this).hasClass('ui-icon-plus')) {
            $(this).removeClass('ui-icon-plus').addClass('ui-icon-minus');
        }
        else {
            $(this).removeClass('ui-icon-minus').addClass('ui-icon-plus');
        }
    });

    // add css styles to the not and warning buttons
//    $(".rowInfo").hover(function() {
//        $(this).addClass("ui-state-hover");
//    }, function() {
//        $(this).removeClass("ui-state-hover");
//    });

// show-hide earn codes in the approval page
    $("#fran-button").click(function() {
        $(".fran").toggle();
        $("#fran-button span").toggleClass('ui-icon-minus');
    });
    $("#frank-button").click(function() {
        $(".frank").toggle();
        $("#frank-button span").toggleClass('ui-icon-minus');
    });
});

function showHideRow(count) {
	var els = document.getElementsByTagName('*');
	var className='assignmentDetails' + count;

	for (i = 0; i < els.length; i++) {
		if(els[i].className == className) {
			if(els[i].style.display == "table-row") {
				els[i].style.display = "none";
		  	} else {
		  		els[i].style.display = "table-row";
			}
		}
	}
	var tempString = 'showDetailButton' + count;
	var ele = document.getElementById(tempString);
	if(ele.value=="Hide Assignments") {
		ele.value="Show Assignments"
	} else {
		ele.value="Hide Assignments"
	}
} 

