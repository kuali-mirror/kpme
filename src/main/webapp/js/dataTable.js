var oTable;
var oRowCount;

$(document).ready(function() {

    $('.timesheet-table-week1').createTimesheetTable('timesheet-table-week1',1);
    $('.timesheet-table-week2').createTimesheetTable('timesheet-table-week2',2);

    // TODO: integrate the time entry widget to the text field
    $('#timesheetForm').submit( function() {
        var sData = $('input', oTable.fnGetNodes()).serializeArray();
        console.log(sData);

        return false;
    });

});

$.fn.createTimesheetTable = function(id, seq) {
        // timesheet table
    oTable = $("." + id).dataTable({
                'bJQueryUI' : true,
                "bPaginate" : false,
                "bLengthChange" : false,
                "bFilter" : false,
                "bSort" : false,
                "bInfo" : false,
                "bAutoWidth" : false,
                "sDom" : '<"toolbar-' + id +'">frtip',

                "fnDrawCallback" : function(oSettings) {
                    oRowCount = oSettings.aiDisplay.length;
                }
            });
    // add a toolbar for the table and create a add row button
    $("div.toolbar-" + id)
            .html('<span style="font-size:1em; font-weight: bold;">Week #' + seq +' :: </span>' +
                  '<button class="add-' + seq + '" style="font-size:.7em;">Clock In/Out</button>' +
                  '<button class="add-lun-' + seq + '" style="font-size:.7em;">Lunch</button>' +
                  '<button class="add-vac-' + seq + '" style="font-size:.7em;">Vacation</button>' +
                  '<button class="buttons" style="font-size:.7em;">Other Actions</button>');

    // apply jquery icons to the toolbar and add an adding a row function to the button
    $('.add-'+ seq +', .add-vac-' + seq +', .add-lun-' + seq ).button({
        icons : {
            primary : 'ui-icon-plus'
        }
    });

    $('.buttons').button({
        icons : {
            primary : 'ui-icon-plus',
            secondary: 'ui-icon-triangle-1-s'
        }
    });

    $('.add-vac-' + seq).click(function(){
        var $newRow = $('.' + id +' tbody>tr:first').clone().insertAfter('.' + id +' tbody>tr:last');

        // add a remove button to the new row
        $newRow.addRemoveRowButton();
        // use a static earncode and action
        $('td', $newRow).eq(2).html('VAC: Vacation').next().html('Hours Taken');
        $('.' + id +' tbody>tr:last td :input:odd').remove();

        return false;
    });

    $('.add-lun-' + seq).click(function(){
        var $newRow = $('.' + id +' tbody>tr:first').clone().insertAfter('.' + id +' tbody>tr:last');

        // add a remove button to the new row
        $newRow.addRemoveRowButton();
        // use a static earncode and action
        $('td', $newRow).eq(2).html('LUN: Lunch');

        return false;
    });


    $('.add-' + seq).click(function(){
        var $newRow = $('.' + id +' tbody>tr:first').clone().insertAfter('.' + id +' tbody>tr:last');
        $newRow.addRemoveRowButton();
        return false;
//      console.log(jQuery($('#timesheet-table tbody>tr:last').clone(true).find('td:first-child')));
    });

}

$.fn.addRemoveRowButton = function() {
    $('td:first', this)
            // add a close button
        .html('<button class="remove" style="font-size:.7em;">Remove a row</button>')

        // find the close button
        .find(".remove")

        // make the button a jQuery button
        .button({
            icons : {
                primary : 'ui-icon-close'
            },
            text : false
        })

        // apply a "remove a row" function to the button
        .click(function(){
            $($(this).closest("tr")).remove();
        });
}