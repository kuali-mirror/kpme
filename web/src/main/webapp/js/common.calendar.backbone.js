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
	
    // create a CalendarSelect view
    var CalendarSelectView = Backbone.View.extend({
    	 el : $("body"),
    	 events : {
             "change #selectedCalendarYear" : "changeCalendarYear",
             "change #selectedPayPeriod" : "changeCalendarEntry"
         },
         initialize : function () {
        	 return this;
         },
         render : function () {
             return this;
         },
    
         changeCalendarYear : function () {
         	var selectedCalendarYear = $("#selectedCalendarYear option:selected").text();
         	var newLoc = window.location.pathname + '?selectedCalendarYear=' + selectedCalendarYear;
             window.location = newLoc;
             
         },
         
         changeCalendarEntry : function () {
          	var hrCalendarEntryId = $("#selectedPayPeriod option:selected").val();
        	var newLoc = window.location.pathname + '?hrCalendarEntryId=' + hrCalendarEntryId ;
            window.location = newLoc ;
         }
    
    });
    
    var app = new CalendarSelectView;
    
//    _.mixin({
//    	getSelectedCalendarYear : function () {
//	        return $("#selectedCalendarYear option:selected").val();
//    	},
//    	getSelectedPayPeriod : function () {
//	        return $("#selectedPayPeriod option:selected").val();
//    	}
//});
	
});