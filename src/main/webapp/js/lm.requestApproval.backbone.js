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
	 * Models
	 * ====================
	 */
	/**
     * leave request employee row
     */
		
    // create a LeaveRequestApproval view
    var LeaveRequestApprovalView = Backbone.View.extend({
    	 el : $("body"),
    	 events : {
	    	 "click input[id^=takeAction]" : "takeActionOnEmployee",
	         "click input[id^=approveAll]" : "approveAllForEmployee",	
         },
         initialize : function () {
        	 return this;
         },
         render : function () {
             return this;
         },
      
	    /*
	     * The logic in following functions heavily depends on the id of the fields in LeaveRequestApproval.jsp
	     * If you need to make changes to the name or id of any fields in the jsp, make sure they are changed in these functions too 
	     */
         takeActionOnEmployee : function (e) {
        	
        	var key = _(e).parseActionKey();
        	var principalId = key.principalId;	// get the principalId from the id of the "take action" button        	
        	// get all the checked leave request, build multiple lists and submit them to action
        	var approveList = '';
        	var disapproveList = '';
        	var deferList = '';
        	var validationEle = $("#validation_"+principalId);	// display error messages
        	var radioCells = $('input:radio[id^="action_" + principalId]');
        	var docSeparator = "----";  // separater doc actions
        	var idSeparator = "____";	// four "_", separator for for documentId and reason string
        	var errors = "";
        	// reset all error fields
        	this.resetErrorFields(principalId);
        	
        	// client side validation
        	radioCells.each(function() {
        		if($(this).attr('checked') == "checked") {
        			var radioKey = _(this).parseRadioEleKey();
        			var reasonEle = $("#reason_" + principalId + "_" + radioKey.documentId);
        			// if value = noAction, ignore this row
        			if($(this).attr('value') == "noAction") {
        				// do nothing
        			} else if($(this).attr('value') == "approve") {
        				// add this row to the approve list
        				approveList += radioKey.documentId + idSeparator + reasonEle.val() + docSeparator;
        			} else if($(this).attr('value') == "disapprove") {
        				// check if reason field is empty
        				if(!reasonEle.val()) {
        					reasonEle.addClass('ui-state-error');
        					errors = "Reason needed for Disapprove action.";
        					return false;
        				} else {
        					disapproveList += radioKey.documentId + idSeparator + reasonEle.val() + docSeparator;
        				}
        			} else if($(this).attr('value') == "defer") {
        				// check if reason field is empty
        				if(!reasonEle.val()) {
        					reasonEle.addClass('ui-state-error');
        					errors = "Reason needed for Defer action.";
        					return false;
        				} else {
        					deferList += radioKey.documentId + idSeparator + reasonEle.val() + docSeparator;
        				}
        			}
        		}
        	});
        	if(errors.length != 0) {
        		this.updateValidation(validationEle, errors);
        		return false;
        	}
        	// if no action found, display error and return back to GUI
        	if(approveList.length ==0 && disapproveList.length == 0 && deferList.length == 0) {
        		this.updateValidation(validationEle, "No action selected!");
        		return false;
        	}
        	// submit the request to form
        	var params = {};
            params['principalId'] = principalId;	// may not be needed
            params['approveList'] = approveList;
            params['disapproveList'] = disapproveList;
            params['deferList'] = deferList;       

            var errorMsgs = "";
            $.ajax({
                url: "LeaveRequestApproval.do?methodToCall=validateActions",	// server side validation
                data: params,
                cache: false,
                async : false,
                success: function(data) {
                    var json = jQuery.parseJSON(data);
                    // if there is no error message, submit the form and save the new time blocks
                    if (json == null || json.length == 0) {
                    	$.ajax({
                            url: "LeaveRequestApproval.do?methodToCall=takeActionOnEmployee",
                            data: params,
                            cache: false,
                            async : false,
                            success : function(data) {
                                // successful
                                return;
                            },
                            error : function() {
                            	errorMsgs += "Error occurred. Please try again.";
                                return false;
                            }
                      }); 
                    } else {
                         $.each(json, function (index) {
                             errorMsgs += "Error : " + json[index] + "\n";
                         }); 
                         return false;
                    }
            	}
            })
            if(errorMsgs != 0 ) {
            	this.updateValidation(validationEle, errorMsgs);
                return false;
            }
	        return;
	     },
         
         approveAllForEmployee : function (e) {
			var key = _(e).parseActionKey();
			var principalId = key.principalId;	// get the principalId from the id of the "take action" button
			var approveList = '';
			var radioCells = $('input:radio[id^="action_" + principalId]');
        	var docSeparator = "----";  // separater doc actions
        	var idSeparator = "____";	// four "_", separator for for documentId and reason string
        	// reset all error fields
        	this.resetErrorFields(principalId);
        	var validationEle = $("#validation_"+principalId);	// display error messages
        	
        	radioCells.each(function() {
        		if($(this).attr('checked') == "checked") {
        			var radioKey = _(this).parseRadioEleKey();
        			var reasonEle = $("#reason_" + principalId + "_" + radioKey.documentId);
        			// no need to check the radio button value since we are going to approve all requests
        			approveList += radioKey.documentId + idSeparator + reasonEle.val() + docSeparator;
        		}
        	});
			var employeeName = $('#employeeName').val();
        	if (confirm('You are about to approve all leave requests for employee \"' + employeeName + '\". Click OK to coninue.' )) {
        		var params = {};
                params['principalId'] = principalId;	// may not be needed
                params['approveList'] = approveList;
                
                var errorMsgs = "";
                $.ajax({
                    url: "LeaveRequestApproval.do?methodToCall=validateActions",
                    data: params,
                    cache: false,
                    async : false,
                    success: function(data) {
                        var json = jQuery.parseJSON(data);
                        // if there is no error message, submit the form and save the new time blocks
                        if (json == null || json.length == 0) {
			            	$.ajax({
			                      url: "LeaveRequestApproval.do?methodToCall=approveAllForEmployee",
			                      data: params,
			                      cache: false,
			                      type : "post",
			                      async : false,
			                      success : function(data) {
			                          // save is successful
			                          return;
			                      },
			                      error : function() {
			                    	  errorMsgs += "Error occurred. Please try again.";
			                          return false;
			                      }
			                }); 
			            } else {
	                         $.each(json, function (index) {
	                             errorMsgs += "Error : " + json[index] + "\n";
	                         }); 
	                         return false;
		                }
		            }
                })
                if(errorMsgs != 0 ) {
	            	this.updateValidation(validationEle, errorMsgs);
	                return false;
                }
        	} else {
        		return false;
        	}

          return; 
         }, 
         
//       update the validation field for this employee table
         updateValidation : function(e, t) {
        	    e.text(t)
        	            .css({'color':'red','font-weight':'bold','font-size': '1.2em','text-align':'center'});
         }, 
         resetErrorFields : function(t) {
        	 $("#validation_" + t)
		            .text('')
		            .removeClass('ui-state-error');
        	 var reasonCells = $('input:text[id^="reason_" + t]');
        	 reasonCells.each(function() {
        		 $(this).removeClass('ui-state-error');
        	 });
         }
    
    });
    
    var app = new LeaveRequestApprovalView;
    _.mixin({
        /**
         * Parse the element id to get the action and principalId
         * @param event
         */
        parseActionKey : function (e) {
            var id = (e.target || e.srcElement).id;

            return {
                action : id.split("_")[0],
                principalId : id.split("_")[1]
            };
        }, 
        /**
         * Parse the element id to get the action, principalId and documentId
         * @param event
         */
        parseRadioEleKey : function (e) {
            var id = e.id;

            return {
                action : id.split("_")[0],
                principalId : id.split("_")[1],
                documentId : id.split("_")[2]
            };
        }
    });

});