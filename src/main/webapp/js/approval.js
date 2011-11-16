$(document).ready(function() {

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


    // sort by the clicked column
//    $('#approvals-table tr th').click(function() {
//    	// replace all occurrence of white space, including space, tab, newline, etc
//        var field = $(this).text().replace(/\s/g, '');
//        //no sorting for notes and warnings pop-up
//        if(field == "Creator" || field == "CreatedDate" || field == "Content" || field == "Notes:" || field == "Warnings:") {
//        	return false;
//        }
//        var rows = $('#approvals-table tbody tr').length;
//        var isAscending = getParameterByName("ascending");
//
//        if (isAscending == null) {
//            isAscending = true;
//        } else if (isAscending == "true") {
//            isAscending = false;
//        } else {
//            isAscending = true;
//        }
//
//        // submit the page for sorting
//        if (field != 'Action' && field != 'Select') {
//            window.location = 'TimeApproval.do?sortField=' + field + '&ascending=' + isAscending + '&rowsToShow=' + rows + "&hrPyCalendarId=" + $("#pcid").val() + "&hrPyCalendarEntriesId=" + $("#pceid").val() + "&selectedCalendarGroup=" + $("#selectedCalendarGroup").val();
//        }
//    });

    // this is to determine which fields are sortable
//    $('#approvals-table tr th').filter(
//            function(index) {
//                return $(this).html().replace(/ /, '') == "DocumentId" || $(this).html().replace(/ /, '') == "PrincipalName" || $(this).html().replace(/ /, '') == "Status";
//            }).addClass("sort");
//
//    // add acs/desc icon to a sorted field
//    if (getParameterByName("ascending") != '') {
//        var klass = getParameterByName("ascending") == "true" ? 'headerSortDown' : 'headerSortUp';
//
//        $('#approvals-table tr th').filter(
//                function(index) {
//                    return $(this).html().replace(/ /, '') == getParameterByName("sortField");
//                }).addClass(klass);
//    }


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
//        source: 'TimeApprovalWS.do?methodToCall=searchApprovalRows&searchField=' + $('#searchField').val() + '&searchTerm=' + $(this).val() + '&payBeginDate=' + payBeginDate + '&payEndDate=' + payEndDate,
        source: function(request, response) {
            $('#loading-value').ajaxStart(function() {
                $(this).show();
            });
            $('#loading-value').ajaxStop(function() {
                $(this).hide();
            });
            //var payBeginDate = $('#payBeginDateForSearch').val();
            //var payEndDate = $('#payEndDateForSearch').val();
            var hrPyCalendarEntriesId = $("#pceid").val();
            var selectedCalendarGroup = $("#selectedCalendarGroup").val();

            $.ajax({
                url: 'TimeApprovalWS.do?methodToCall=searchApprovalRows&searchField=' + $('#searchField').val() + '&searchTerm=' + request.term + "&payBeginDateForSearch=" + $("#beginDate").html() + "&payEndDateForSearch=" + $("#endDate").html() +
//                        '&hrPyCalendarEntriesId=' + hrPyCalendarEntriesId +
                        '&selectedCalendarGroup=' + selectedCalendarGroup,
                dataType: "json",
                success: function(data) {
                    response($.map(data, function(item) {
                        return {
                            value: item.id,
                            id: item.result
                        };
                    }));
                }
            });
        },
        minLength: 3,
        select: function(event, data) {
            var rows = $('#approvals-table tbody tr').length;
            var isAscending = getParameterByName("ascending");
            window.location = 'TimeApproval.do?searchField=principalName&searchTerm=' + data.item.id;
        },
        open: function() {
            $(this).removeClass("ui-corner-all");
        },
        close: function() {
            $(this).removeClass("ui-corner-top");
        }
    });

    // check if the approve button is disabled. if so, disable the select checkbox as well
    //  $('#actions input[type=button]').filter(
    //        function(index) {
    //          return $(this).prop("disabled");
    //    }).parent().parent().parent().find("input[type=checkbox]").attr("disabled", "disabled");

    // select the whole row when the select checkbox is checked
    $('.selectedEmpl').click(function() {
        $(this).parent().parent().find("td").toggleClass("highlight")
    });

    // buttons for prev / next pay calendar entries
    $('.prev').button({
        icons: {
            primary: "ui-icon-circle-triangle-w"
        },
        text: false
    });

    $('.next').button({
        icons: {
            primary: "ui-icon-circle-triangle-e"
        },
        text: false
    });

    $('.approve').button({
        text: false
    });


    $(" .approvals-warning, .approvals-note, .approvals-status").tooltip({ effect: 'slide'});

    // toggle the button for the assigment details
    $('.rowInfo').click(function() {
        var seq = $(this).closest("span").attr("id").split("_")[1];
        $('.assignmentDetails_' + seq).find('td').css('background-color', '#F6F6F6');

        if ($(this).hasClass('ui-icon-plus')) {
            // $('.assignmentDetails_' + seq).show();
            $("#approvals-table").find($('.timeSummaryRow_' + seq)).show().css("font-size", "1.2em");

            // figure out the tds in the approval table
            var approvalTable = $("#approvals-table").clone();
            var tds = $(approvalTable).children('tbody').children('tr').children('td').length;
            // set the colspan dynicamically for the summary table
            $("td.rowCount").attr("colspan", tds);
            // remove the header of the summary table
            $('.summaryTitle').remove();

            $(this).removeClass('ui-icon-plus').addClass('ui-icon-minus');
        }
        else {
            //$('.assignmentDetails_' + seq).hide();
            $("#approvals-table").find($('.timeSummaryRow_' + seq)).hide();
            $(this).removeClass('ui-icon-minus').addClass('ui-icon-plus');
        }
    });

    // add css styles to the note and warning buttons
//    $(".rowInfo").hover(function() {
//        $(this).addClass("ui-state-hover");
//    }, function() {
//        $(this).removeClass("ui-state-hover");
//    });


//    $("#selectedCalendarGroup").change(function() {
//    	$.post("TimeApproval.do?methodToCall=selectNewCalendarGroup&selectedCalendarGroup=" + $("#selectedCalendarGroup").val());
//    });
});
