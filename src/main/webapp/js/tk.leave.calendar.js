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
                    $("#leave-calendar").submit();
                },
                Cancel:function () {
                    $(this).dialog('close');
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
});