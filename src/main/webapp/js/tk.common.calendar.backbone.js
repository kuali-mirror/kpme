$(function () {
	
    // create a CalendarSelect view
    var CalendarSelectView = Backbone.View.extend({
    	 el : $("body"),
    	 events : {
             "change #selectedCalendarYear" : "changeCalendarYear",
             "change #selectedPayPeriod" : "changePayPeriod"
         },
         initialize : function () {
        	 return this;
         },
         render : function () {
             return this;
         },
    
         changeCalendarYear : function () {
         	var selectedCY = $("#selectedCalendarYear option:selected").text();
         	var docId = $('#documentId').val();
         	var newLoc = window.location.pathname + '?methodToCall=changeCalendarYear&selectedCY=' + selectedCY + '&documentId=' + docId;
             window.location = newLoc;
             
         },
         
         changePayPeriod : function () {
          	var selectedPP = $("#selectedPayPeriod option:selected").val();
        	var newLoc = window.location.pathname + '?methodToCall=changePayPeriod&selectedPP=' + selectedPP ;
            window.location = newLoc ;
         },
    
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