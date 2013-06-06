/*
 * Copyright 2004-2013 The Kuali Foundation
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
     * ====================
     * Settings
     * ====================
     */

        // The default template variable is <%= var %>, but it conflicts with jsp, so we use <@= var @> instead
    _.templateSettings = {
        interpolate : /\<\@\=(.+?)\@\>/gim,
        evaluate : /\<\@(.+?)\@\>/gim
    };

    /**
     * ====================
     * Models
     * ====================
     */

        // Create a time block model
    EarnCodeSection = Backbone.Model;

    /**
     * ====================
     * Collections
     * ====================
     */

        // Create a time block collection that holds multiple time blocks. This is essentially a list of hashmaps.
    EarnCodeSectionCollection = Backbone.Collection.extend({
        model : EarnCodeSection,
        url : "TimeApprovalWS.do?methodToCall=getTimeSummary"
    });
    var EarnCodeSections = new EarnCodeSectionCollection;

    
    var eventJson = jQuery.parseJSON($("#outputString").val());
    totals = "<b>5</b>\n<i>(2)</i>, <b>2</b>\n<i>(3)</i>";
    
   
    
    /**
     * ====================
     * Load FullCalendar
     * ====================
     */
    var selectedPayPeriodId = $('#pceid').val();
    var startDates = $('#beginCalendarEntryDate').text().split('/');
    var endDates = $('#endCalendarEntryDate').text().split('/');
        	$('#calendar').fullCalendar({
            	header: {
    				left: '',
    				center: '',
    				right: ''
    			},
    			editable: true,
    			firstDay : 0,
    			weekMode : 'variable',
    			weekNumbers : true,
    			weekNumberCalculation: 'counter',
    			weekNumberTitle: 'Week',
    			showCustomWeekData: false,
    			weekTotalArray: totals,
    			sdate: new Date(startDates[2],startDates[0]-1, startDates[1]),
    			edate: new Date(endDates[2],endDates[0]-1, endDates[1]),
//    			sdate: new Date(2013, 3, 27),
//    			edate: new Date(2013, 4, 25),
    			titleFormat: {
    			    month: "MMM d yyyy { 'to' MMM d yyyy}"
    			},
    			events: eventJson, 
    			eventRender: function (event, element) {
    				element.draggable = false;
    				event.editable = false;
    	        }
            });

    /**
     * ====================
     * Views
     * ====================
     */

    var TimeSummaryView = Backbone.View.extend({

        el : $("body"),

        template : _.template($('#hourDetail-template').html()),

        events : {
            "click span[id^=showDetailButton]" : "showTimeSummary"
        },

        initialize : function () {
            _.bindAll(this, "render");
        },

        render : function () {
            return this;
        },

        showTimeSummary : function (e) {
            var self = this;
            var docId = e.target.id.split("_")[1];

            /**
             * There is still some DOM manipulation that can't be avoid.
             * We want to append the time hour details right after each
             * person's row, but due to the limit of the displaytag where
             * every element has to be wrapped in the <display:column> and
             * will be later converted to html. There isn't a way that I could
             * find to just append a div after the last <display:column>
             * inside the displaytag.
             *
             * To solve this issue, the current solution is to append the div
             * through DOM manipulation.
             */

            // This is to grab a person's <tr> in the table
            var $parent = ($("#" + e.target.id).closest("tr"));

            // Grab the + / - icon
            var $element = $("#" + e.target.id);
            // Toggle the + / - icon
            if ($element.hasClass('ui-icon-plus')) {
                // This triggerS the ajax call to fetch the time summary rows.
                this.fetchTimeSummary(docId);

                // Here we loop through the colleciton and insert the content to the template
                EarnCodeSections.forEach(function (earnCodeSection, index) {
                    $parent.after(self.template({
                        // This is the time summary rows
                        "section" : earnCodeSection.toJSON(),
                        // This is to check if the time summary row is the last one in the collection.
                        // If so, it will then render the earn group section.
                        "isLast" : index == 0 ? true : false,
                        // This is to give each <tr> in the time summary section an identifier,
                        // so when the minus icon is clicked, it will remove the appened html.
                        "docId" : docId
                    }));
                });

                // change the icon from - to +
                $element.removeClass('ui-icon-plus').addClass('ui-icon-minus');
            } else {
                // remove the hour details rows.
                $(".hourDetailRow_" + docId).remove();
                // change the icon from + to -
                $element.removeClass('ui-icon-minus').addClass('ui-icon-plus');
            }
        },
        /**
         * This method will make an ajax call to fetch the time summary row based on the doc id.
         * @param documentId
         */
        fetchTimeSummary : function (documentId) {
            EarnCodeSections.fetch({
                async : false,
                data : {
                    documentId : documentId
                }
            });
        }
    });

    // Initialize the view. This is the kick-off point.
    var app = new TimeSummaryView;

});