/*
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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