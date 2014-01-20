/*
 * Copyright 2004-2014 The Kuali Foundation
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
	LeaveRequest = Backbone.Model;

    // Create a leave block collection that holds multiple time blocks. This is essentially a list of hashmaps.
    LeaveRequestCollection = Backbone.Collection.extend({
        model : LeaveRequest
    });
    
    // Convert the leave request json string to a json object
    var leaveRequestJson = jQuery.parseJSON($("#leaveRequestString").val());
    var leaveRequestCollection = new LeaveRequestCollection(leaveRequestJson);
	
	/**
     * leave request employee row
     */
		
    // create a LeaveRequestApproval view
    var LeaveRequestApprovalView = Backbone.View.extend({
    	 el : $("body"),
    	 events : {
	    	 "click input[id^=actionOn_]" : "takeAction",
	    	 "click input[id^=leaveReqDoc_]" : "doNothing",
	    	 "click div[id^=leaveRequest]" : "showLeaveRequestApprovalDialog",
	         "click input[id=checkAllApprove]" : "checkAllApprove"
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
         takeAction: function (e) {
        	e.stopPropagation();
        	var key = _(e).parseActionKey();
        	var principalId = key.principalId;	// get the principalId from the id of the "take action" button       
        	// get all the checked leave request, build multiple lists and submit them to action
        	var actionList = '';
        	var actionVar = principalId;
//	        	var validationEle = $("#validation_"+principalId);	// display error messages
        	var checkboxCells = $("input:checkbox[id^=leaveReqDoc_]");
        	var docSeparator = "----";  // seperater doc actions
        	var errors = "";
        	var formId = document.forms[0].id;
        	// reset all error fields
        	this.resetErrorFields('#'+formId);
        	this.resetErrorFields('#'+document.forms[1].id);
        	// client side validation for checkbox
        	checkboxCells.each(function() {
        		if($(this).is(':checked')) {
        			var checkboxKey = _(this).parseCheckBoxKey();
        			var docId = checkboxKey.documentId;
        			actionList += docId + docSeparator;
        		}
        	});
        	
        	// Validate Leave Request.
        	var isValid = true;
        	isValid = this.validateLeaveRequest(formId, actionVar, actionList);
        	if(isValid) {
        		var params = {};
        		params['actionList'] = actionList;
        		params['action'] = actionVar;
            	$.ajax({
                    url: "LeaveRequestApproval.do?methodToCall=takeAction",
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

        	}
        	return isValid;
	     },
	     
	     render : function () {
	            // If there is anything you want to render when the view is initiated, place them here.
	            // A good convention is to return this at the end of render to enable chained calls.
	            return this;
	     },

	     doNothing : function (e) {
	           e.stopPropagation();
	     },


	     showLeaveRequestApprovalDialog : function (e) {
	    	 var key = _(e).parseEventKey();
	    	 var self = this;
	    	 var docId = key.id;
	    	 var actionList = '';
	    	 var leaveRequest = leaveRequestCollection.get(docId);
	    	 $("#dialog-form").dialog({
	                title : "Take Action on Leave Request",
	                closeOnEscape : true,
	                autoOpen : true,
	                width : 'inherit',
	                modal : true,
	                open : function () {
	                	self.resetErrorFields("#"+document.forms[1].id);
	                	self.resetErrorFields("#"+document.forms[0].id);
	                	var dfd = $.Deferred();
	                    dfd.done(_(leaveRequest).fillInForm());
	                },
	                close : function () {
	                    //reset values on the form
	                    self.resetLeaveRequestDialog($("#timesheet-panel"));
	                    self.resetErrorFields("#"+document.forms[1].id);
	                },
	                buttons : {
	                    "Submit" : function () {
	                    	var docSeparator = "----";  // seperater doc actions
	                    	var radioCells = $("input:radio[id=action]");
	                    	radioCells.each(function() {
	                    		if($(this).attr('checked') == "checked") {
	                    			actionList += docId + docSeparator;
	                    		}
	                    	});
	                    	var actionValue = $('input[name="action"]:checked').val();
	                    	var formId = document.forms[1].id;
	                    	var isValid = self.validateLeaveRequest(formId, actionValue, actionList);
	                    	if(isValid && actionList.length > 0 ) {
	                        	$('#'+formId+' #actionList').val(actionList);
	                        	$('#'+formId+' #navigationString').val(actionList);
		                        if (isValid) {
		                            $("#leaveRequestApproval-form").submit();
		                            $(this).dialog("close");
		                        }
	                    	}
	                    },
	                    "Cancel" : function () {
	                    	//reset values on the form
	                        self.resetLeaveRequestDialog($("#timesheet-panel"));
	                        self.resetErrorFields("#"+document.forms[0].id);
	                    	self.resetErrorFields("#"+document.forms[1].id);
	                    	$(this).dialog("close");
	                    }
	                }
	    	 }).height("auto"); 
	     },
	     
        validateLeaveRequest : function (formId, action, actionList) {
            var isValid = true;
            var checkrequest = true;
            if(action == undefined) {
            	isValid = false;
            	checkrequest = false;
            	this.displayErrorMessages("#"+formId, "Please choose any action.");
            }
            
            if(checkrequest){ 
            	isValid = (isValid && actionList.length > 0) ? true : false;
            	if(!isValid) 
            		this.displayErrorMessages("#"+formId, "Please choose any request to approve.");
            }
            if(isValid && action != 'Approve')
            	isValid = isValid  && this.checkEmptyField("#"+formId, $("#reason"), "Reason");
            	
            if(isValid) {
            	var params = {};
            	params['actionList'] = actionList;
            	params['action'] = action;
            	params['reason'] = $('#'+formId+' #reason').val();
            	 // validate leaveblocks
                $.ajax({
                    async : false,
                    url: "LeaveRequestApproval.do?methodToCall=validateNewActions",	// server side validation
                    data : params,
                    cache : false,
                    type : "post",
                    success : function (data) {
                        //var match = data.match(/\w{1,}|/g);
                        var json = jQuery.parseJSON(data);
                        // if there is no error message, submit the form to add the time block
                        if (json.length == 0) {
                            return true;
                        }
                        else {
                            // if there is any error, grab error messages (json) and display them
                            var json = jQuery.parseJSON(data);
                            var errorMsgs = '';
                            $.each(json, function (index) {
                                errorMsgs += "Error : " + json[index] + "<br/>";
                            });
                            self.displayErrorMessages('#'+formId, errorMsgs);
                            isValid = false;
                        }
                    },
                    error : function () {
                        self.displayErrorMessages('#'+formId, "Error: Can't save data.");
                        isValid = false;
                    }
                });
            }
            return isValid;
        },
        
        checkEmptyField : function (formId, o, field) {
            var val = o.val();
            if (val == '' || val == undefined) {
                this.displayErrorMessages(formId, field + " field cannot be empty", o);
                return false;
            }
            return true;
        },
        
	    displayErrorMessages : function (formId, t, object) {
	        // add the error class ane messages
	        $(formId+' #validation').html(t)
	                .addClass('error-messages');
	
	        // highlight the field
	        if (!_.isUndefined(object)) {
	            object.addClass('ui-state-error');
	        }
	        return false;
	    },

	    checkAllApprove : function(e) {
			var checkboxCells = $("input:checkbox[id^=leaveReqDoc_]");
			// when checkbox is checked, select approve for all radio buttons of employee
			if($('#checkAllApprove').is(':checked')){
				checkboxCells.each(function() {
						$(this).attr("checked", "checked");
				});
			} else {
				checkboxCells.each(function() {
					$(this).removeAttr('checked');
				});
			}
	     },
         
         
         /**
          * Reset the values on the leaveblock entry form.
          * @param fields
          */
         resetLeaveRequestDialog : function (leaveRequestDiv) {
        	 document.getElementById('leaveRequestApproval-form').reset();
         },
         
         /**
          * Reset Error Fields
          * @param formId
          */
         resetErrorFields : function(formId) {
        	 $(formId +" #validation")
		            .text('')
		            .removeClass('ui-state-error').removeClass('error-messages');
        	 var reasonCells = $('input:text[id^="reason"]');
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
        parseCheckBoxKey : function (e) {
            var id = e.id;
            return {
                action : id.split("_")[0],
                documentId : id.split("_")[1]
            };
        },
        
        /**
         * Parse the div id to get the timeblock id and the action.
         * @param event
         */
        parseEventKey : function (e) {
            var id = (e.target || e.srcElement).id;
            return {
                action : id.split("_")[0],
                id : id.split("_")[1]
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
        },
        /**
         * Fill in the leave entry form by the leaveblock
         * @param leaveBlock
         */
        fillInForm : function (leaveRequest) {
            $('#leaveDate').text(leaveRequest.get("leaveDate"));
            $('#leaveHours').text(leaveRequest.get("leaveHours"));
            $('#documentId').val(leaveRequest.get("documentId"));
            $('#leaveCode').text(leaveRequest.get("leaveCode"));
            $('#assignmentTitle').text(leaveRequest.get("assignmentTitle"));
            $('#principalId').text(leaveRequest.get("principalId"));
            $('#principalName').text(leaveRequest.get("principalName"));
        }
    });

});