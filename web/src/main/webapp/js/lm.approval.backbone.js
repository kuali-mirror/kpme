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
    // section for each accrual category
    AccrualCategorySection = Backbone.Model;

    /**
     * ====================
     * Collections
     * ====================
     */
    // For leave approval table plus button details
    AccrualCategorySectionCollection = Backbone.Collection.extend({
        model : AccrualCategorySection,
        url : "LeaveApprovalWS.do?methodToCall=getLeaveSummary"
    });
    var AccrualCategorySections = new AccrualCategorySectionCollection;
    
//    var Event = Backbone.Model.extend();
//    
//    var Events = Backbone.Collection.extend({
//        model: Event,
//        url: "events"
//    });
//    
    
    var eventJson = jQuery.parseJSON($("#outputString").val());
    if(eventJson == null) {
    	eventJson = "[]";
    }
     
    /**
     * ====================
     * Load FullCalendar
     * ====================
     */
    var selectedPayPeriodId = $('#pceid').val();
    var startDates = $('#beginCalendarEntryDate').text().split('/');
    var endDates = $('#endCalendarEntryDate').text().split('/');
//    var EventsView = Backbone.View.extend({
//        initialize: function(){
//            _.bindAll(this);
//            this.collection.bind('reset', this.addAll);
//        },
//        render: function() {
        	$('#calendar').fullCalendar({
            	header: {
    				left: '',
    				center: '',
    				right: ''
    			},
    			editable: true,
    			weekMode : 'variable',
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
//        },
//        addAll: function(){
//            this.el.fullCalendar('addEventSource', this.collection.toJSON());
//        }
//    });
        	
        	/**
             * ====================
             * Views
             * ====================
             */
    // view for Leave Calendar Summary. 
    var LeaveSummaryView = Backbone.View.extend({
        el : $("body"),
        
        template : _.template($('#leaveDetail-template').html()),
        
        events : {
            "click span[id^=showLeaveDetailButton]" : "showLeaveSummary"
        },
        
        initialize : function () {
            _.bindAll(this, "render");
        },
        
        render : function () {
            return this;
        },

        showLeaveSummary : function (e) {
            var self = this;
            var docId = e.target.id.split("_")[1];
            // This is to grab a person's <tr> in the table
            var $parent = ($("#" + e.target.id).closest("tr"));

            // Grab the + / - icon
            var $element = $("#" + e.target.id);
            // Toggle the + / - icon
            if ($element.hasClass('ui-icon-plus')) {
                // This triggerS the ajax call to fetch the leave summary rows.
                this.fetchLeaveSummary(docId);
                // Here we loop through the colleciton and insert the content to the template
                if(AccrualCategorySections.size() >= 1) {
	                AccrualCategorySections.each(function (accrualCetegorySection, index) {
	                	var afterString = self.template({
	                        "section" : accrualCetegorySection.toJSON(),
	                        // This is to give each <tr> in the leave summary section an identifier,
	                        // so when the minus icon is clicked, it will remove the appened html.
	                        "docId" : docId
	                    });
	                    $parent.after(afterString);
	                });
	                // add the title row for the detail section
					var firstMap = AccrualCategorySections.first().toJSON();
					var daysNumber = firstMap["daysSize"] + 2;
	                var tempString = "<tr class='leaveDetailRow_" + docId + "'><th colspan='3'/><th>Period Usage</th><th>Available</th></tr>"
	                $parent.after(tempString);
                }
                // change the icon from - to +
                $element.removeClass('ui-icon-plus').addClass('ui-icon-minus');
            } else {
                // remove the leave details rows.
                $(".leaveDetailRow_" + docId).remove();
                // change the icon from + to -
                $element.removeClass('ui-icon-minus').addClass('ui-icon-plus');
            }
        },
        /**
         * This method will make an ajax call to fetch the leave summary row based on the doc id.
         * @param documentId
         */
        fetchLeaveSummary : function (documentId) {
            AccrualCategorySections.fetch({
                async : false,
                data : {
                    documentId : documentId
                }
            });
        }
    });
    // Initialize the view.
    var leaveView = new LeaveSummaryView;
        
//    var events = new Events();
//    new EventsView({el: $("#calendar"), collection: events}).render();
//    events.fetch();
    
});