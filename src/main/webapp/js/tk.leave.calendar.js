$(document).ready(function () {

    // Show the ledger entry form when clicking on a valid date.
    // .not() filters out the dates beyond the current period.
    $(".leaveCalendar-table td").not("#gray_day").click(function (event) {

        // initiate a jQuery dialog
        $("#dialog-form").dialog({
            autoOpen:false,
            height:'auto',
            width:'auto',
            modal:true,
            beforeClose:function (event, ui) {
            },
            buttons:{
                "Add":function () {
                    $("#ledger-entry-form").submit();
                },
                Cancel:function () {
                    $(this).dialog('close');
                    event.stopPropagation();
                    event.preventDefault();
                }
            }
        });

        // Set begin / end dates on the input fields.
        // The div id of a day(td) is : day_11/01/2011.
        var selectedDay = $(this).attr('id').split("_")[1];
        $("#date-range-begin, #date-range-end").val(selectedDay);
        // open the dialog
        $("#dialog-form").dialog('open');
    });

    // disable the entry form when the delete button is clicked
    $(".ledger-delete").click(function (event) {
        var ledgerId = $(this).attr('id').split("_")[1];

        if (confirm('You are about to delete a ledger entry. Click OK to confirm the delete.')) {
            window.location = "LeaveCalendar.do?methodToCall=deleteLedger&ledgerId=" + ledgerId;
        }
        event.stopPropagation();
        event.preventDefault();
    });

});