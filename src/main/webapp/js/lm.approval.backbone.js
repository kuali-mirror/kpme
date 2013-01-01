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
					var daysNumber = firstMap["daysSize"] + 3;
	                var tempString = "<tr class='leaveDetailRow_" + docId + "'><th colspan='" + daysNumber + "'/><th>Period Usage</th><th>Available</th></tr>"
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
        
});