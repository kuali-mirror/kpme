$(function () {

    /**
     * Handle the cell / event overlapping on the timeblock issue.
     * This is to solve the problem where clicking on a timeblock also triggers the blank dlalog form,
     * since the timeblock is part of the cell.
     * The solution is to create a div that fills up the white space of the cell.
     * After that, the blank dialog will only be triggered when users click on the white area of a cell.
     * This solution will also make every element independent to reduce any unexpected chaining events.
     *
     */

    var cellHeight = $("#day_0").height();
    // fill up the div with the padding div
    $.each($(".create").parent(), function(index, div) {
        // divHeight is the height of the timeblocks
        var eventHeight = $(div).height();
        // cellHeight is the height of the td
        $(".create", this).height(cellHeight - eventHeight + 10);
    });

});