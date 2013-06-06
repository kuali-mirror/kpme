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

    /**
     * Leave Block
     */
        // Create a leave block model
    LeaveBlock = Backbone.Model;

    // Create a leave block collection that holds multiple time blocks. This is essentially a list of hashmaps.
    LeaveBlockCollection = Backbone.Collection.extend({
        model : LeaveBlock
    });
    
    // Convert the leave block json string to a json object
    var leaveBlockJson = jQuery.parseJSON($("#leaveBlockString").val());
    var leaveBlockCollection = new LeaveBlockCollection(leaveBlockJson);

    //EarnCode = Backbone.Model.extend({
   	// url : "LeaveCalendarWS.do?methodToCall=getEarnCodeInfo"
    //});

    /**
     * Earn Code
     */
        // Create an earn code model
    EarnCode = Backbone.Model;

    var hrCalendarEntryId = $("#selectedPayPeriod option:selected").val();
    // Create a collection for earn codes
    EarnCodeCollection = Backbone.Collection.extend({
        model : EarnCode,
        url : "LeaveCalendarWS.do?methodToCall=getEarnCodeJson&hrCalendarEntryId=" + hrCalendarEntryId
    });

    var EarnCodes = new EarnCodeCollection;

    var earnCodeObj = new EarnCode;

    /**
     * ====================
     * Views
     * ====================
     */

    // create a timeblock view
    var LeaveBlockView = Backbone.View.extend({
        // Set the element that our dialog form wants to bind to. This setting is necessary.
        el : $("body"),

        // Events are in the jQuery format.
        // The first part is an event and the second part is a jQuery selector.
        // Check out this page for more information about the jQuery selectors : http://api.jquery.com/category/selectors/
        events : {
        	"click div[id*=show]" : "showLeaveBlock", // KPME-1447
            "click .create" : "showLeaveBlockEntryDialog",
            "click img[id^=leaveBlockDelete]" : "deleteLeaveBlock",
            "click .leaveBlock" : "doNothing",
            "mousedown .create" : "tableCellMouseDown",
            "mouseup .create" : "tableCellMouseUp",
            "change #selectedEarnCode" : "showFieldByEarnCodeType",
            "keypress #selectedEarnCode" : "showFieldByEarnCodeType",
            "change #selectedAssignment" : "changeAssignment",
            "keypress #selectedAssignment" : "changeAssignment",
            "click input[id^=lm-transfer-button]" : "showOnDemandBalanceTransferDialog",
            "click input[id^=lm-payout-button]" : "showOnDemandBalancePayoutDialog",
            "blur #startTimeHourMinute, #endTimeHourMinute" : "formatTime",
            "click #ts-route-button" : "forfeitBalanceOnSubmit"
        },

        initialize : function () {

            // This step binds the functions to the view so you can call the methods like : this.addOneEarnCode
            _.bindAll(this, "addAllEarnCodes", "render");

            // Bind the events onto the earncode collection, so these events will be triggered
            // when an earncode collection is created.
            EarnCodes.bind('add', this.addAllEarnCodes);
            EarnCodes.bind('reset', this.addAllEarnCodes);

//            OvertimeEarnCodes.bind('add', this.addAllOvertimeEarnCodes);
//            OvertimeEarnCodes.bind('reset', this.addAllOvertimeEarnCodes);
        },

        render : function () {
            // If there is anything you want to render when the view is initiated, place them here.
            // A good convention is to return this at the end of render to enable chained calls.
            return this;
        },

        doNothing : function (e) {
            e.stopPropagation();
        },

        formatTime : function (e) {

            var id = (e.target || e.srcElement).id;
            var value = (e.target || e.srcElement).value;
            // Use Datejs to parse the value
            var dateTime = Date.parseExact(value, CONSTANTS.TIME_FORMAT.TIME_FOR_VALIDATION);
            if (_.isNull(dateTime)) {
                // Date.js returns null if it couldn't understand the format from user's input.
                // If that's the case, clear the values on the form and make the border red.
                $("#" + id).addClass("block-error").val("");
                $("#" + id.split("Hour")[0]).val("");
            } else {
                // Remove the red border if user enters something
                $("#" + id).removeClass("block-error").val("");
            }
            // This magic line first finds the element by the id.
            // Uses Datejs (a 3rd party js lib) to parse user's input and update the value by the specifed format.
            // See the list of the formats in tk.js.
            $("#" + id).val(dateTime.toString(CONSTANTS.TIME_FORMAT.TIME_FOR_OUTPUT));

            // set the value to the military format on a different field for further timeblock actions.
            $("#" + id.split("Hour")[0]).val(dateTime.toString(CONSTANTS.TIME_FORMAT.TIME_FOR_SYSTEM));
        },

        showLeaveBlockEntryDialog : function (startDate, endDate) {
            // check user permmissions before opening the dialog.
            var isValid = this.checkPermissions(startDate, endDate);
            var self = this;
            if (isValid) {
              $("#dialog-form").dialog({
                title : "Add Leave Blocks : ",
                closeOnEscape : true,
                autoOpen : true,
                width : 'inherit',
                modal : true,
                open : function () {
                    // Set the selected date on start/end time fields
                    // This statmement can tell is showTimeEntryDialog() by other methods or triggered directly by backbone.

                    if (!_.isUndefined(startDate) && !_.isUndefined(endDate)) {
                        $("#startDate").val(startDate);
                        $("#endDate").val(endDate);
                    } else {
                        // If this is triggered directly by backbone, i.e. user clicked on the white area to create a new timeblock,
                        // Set the date by grabbing the div id.
                        var currentDay = new Date(beginPeriodDateTimeObj);
                        var targetDay = currentDay.addDays(parseInt((startDate.target.id).split("_")[1]));
                        $("#startDate, #endDate").val(Date.parse(targetDay).toString(CONSTANTS.TIME_FORMAT.DATE_FOR_OUTPUT));
                       
                        // Check if there is only one assignment
                        // Placing this code block here will prevent fetching earn codes twice
                        // when showTimeEntryDialog() is called by showTimeBlock()
//                            if ($("#selectedAssignment").is("input")) {
//                                var dfd = $.Deferred();
//                                dfd.done(self.fetchEarnCode(_.getSelectedAssignmentValue()))
//                                        .done(self.showFieldByEarnCodeType());
//                            }
                    }
                    if ($("#selectedAssignment").is("input")) {
                        var dfd = $.Deferred();
                        dfd.done(self.fetchEarnCode(_.getSelectedAssignmentValue()))
                            .done(self.showFieldByEarnCodeType());
                    }

                    var startSpanDate = Date.parse($("#startDate").val()).clearTime();
                    var endSpanDate = Date.parse($("#endDate").val()).clearTime();
                    var spanningWeeks = false;
                    for (var currentSpanDate = startSpanDate; currentSpanDate.isBefore(endSpanDate) || currentSpanDate.equals(endSpanDate); currentSpanDate.addDays(1)) {
                        if (currentSpanDate.is().saturday() || currentSpanDate.is().sunday()) {
                           spanningWeeks = true;
                        } else {
                            spanningWeeks = false;
                            break;
                        }
                    }
                    $("#spanningWeeks").prop("checked", spanningWeeks);
                },
                close : function () {
                	$('.cal-table td').removeClass('ui-selected');
                    //reset values on the form
                    self.resetLeaveBlockDialog($("#timesheet-panel"));
                    self.resetState($("#dialog-form"));
                },
                buttons : {
                    "Add" : function () {
                       // If the end time is 12:00 am, change the end date to the next day if it has not already been done
                        if (!_.isEmpty($("#startDate").val()) && !_.isEmpty($("#endDate").val()) && !_.isEmpty($("#endTime").val())) {
                            var midnight = Date.parse($('#endDate').val()).set({
                                hour : 0,
                                minute : 0,
                                second : 0,
                                millisecond : 0
                            });

                            var startDate = Date.parse($('#startDate').val());
                            var endDate = Date.parse($('#endDate').val());
                            var endDateTime = Date.parse($('#endDate').val() + " " + $('#endTime').val());
                            
                            // Compare and see if the end time is at midnight
                            if (Date.compare(startDate, endDate) == 0 && Date.compare(midnight, endDateTime) == 0) {
                                $('#endDate').val(endDateTime.add(1).days().toString(CONSTANTS.TIME_FORMAT.DATE_FOR_OUTPUT));
                            }
                        }
                        
                        var isValid = true;
                        $('#acrossDays').val($('#acrossDays').is(':checked') ? 'y' : 'n');
                        $('#spanningWeeks').val($('#spanningWeeks').is(':checked') ? 'y' : 'n');  // KPME-1446
                        isValid = self.validateLeaveBlock();
                        if (!_.isEmpty($("#leaveBlockId").val())) {
                        	$('#methodToCall').val(CONSTANTS.ACTIONS.UPDATE_LEAVE_BLOCK);	
                        } else {
                        	$("#methodToCall").val(CONSTANTS.ACTIONS.ADD_LEAVE_BLOCK);
                        }
                        
                        if (isValid) {
                            $('#leaveBlock-form').submit();
                            $(this).dialog("close");
                        }
                    },
                    Cancel : function () {
                        $('.block-error').removeClass();
                    	$('.cal-table td').removeClass('ui-selected');
                        $(this).dialog("close");

                    }
                }
             }).height("auto");
            }
        },


        showLeaveBlock : function (e) {
            var key = _(e).parseEventKey();
            
            // Retrieve the selected leaveblock
            var leaveBlock = leaveBlockCollection.get(key.id);
            
            // We only have one date in leave block
            //this.showTimeEntryDialog(leaveBlock.get("startDate"), leaveBlock.get("endDate"));
            this.showLeaveBlockEntryDialog(leaveBlock.get("leaveDate"), leaveBlock.get("leaveDate"));
            _.replaceDialogButtonText("Add", "Update");
            
            // for pending leave request blocks, show dates as read only
            if(leaveBlock.get("requestStatus") == "R") {
		         $('#startDate').attr('disabled', 'disabled');
		         $('#startDate').datepicker("destroy");
		         $('#endDate').attr('disabled', 'disabled');
		         $('#endDate').datepicker("destroy");
            }
            
            // Deferred is a jQuery method which makes sure things happen in the order you want.
            // For more informaiton : http://api.jquery.com/category/deferred-object/
            // Here we want to fire the ajax call first to grab the earn codes.
            // After that is done, we fill out the form and make the entry field show / hide based on the earn code type.
            var dfd = $.Deferred();
            dfd.done(_(leaveBlock).fillInForm())
               .done(this.fetchEarnCode(_.getSelectedAssignmentValue()))
               .done($("#selectedEarnCode option[value='" + leaveBlock.get("earnCode") + "']").attr("selected", "selected"))
               .done(this.showFieldByEarnCodeType())
               .done($("#leaveAmount").attr("value", leaveBlock.get("leaveAmount")));
        },
        
        deleteLeaveBlock : function (e) {
            var key = _(e).parseEventKey();
            var calId = $('#hrCalendarEntryId').val();
            
//            if (this.checkPermissions()) {
            	if (confirm('You are about to delete a leave block. Click OK to confirm the delete.')) {
            	var leaveBlock = leaveBlockCollection.get(key.id);
            	var canTransferLB = leaveBlock.get("canTransfer") ? true : false;
            	if(canTransferLB) {
	            	$('#lm-transfer-empty').empty();
		            $('#lm-transfer-dialog').append('<iframe width="800" height="600" src="BalanceTransfer.do?methodToCall=deleteSSTOLeaveBlock&leaveBlockId=' + key.id +'"></iframe>');
		 
		            $('#lm-transfer-dialog').dialog({
		                autoOpen: true,
		                height: 'auto',
		                width: 'auto',
		                modal: true,
		                buttons: {
		                },
		                beforeClose: function(event, ui) {
		                    var URL = unescape(window.parent.location);
		                    window.parent.location.href = URL;
		                    window.close();
		                }
	           		});
            	} else {
            		window.location = "LeaveCalendar.do?methodToCall=deleteLeaveBlock&leaveBlockId=" + key.id + "&hrCalendarEntryId=" + calId;
            	}
              }	
//            }
        },
        
        forfeitBalanceOnSubmit : function () {
        	var docId = $('#documentId').val();
        	if ($('#loseOnSubmit').val() == 'true') {
        		$('#confirm-forfeiture-dialog').dialog({
        			autoOpen: true,
        			height: 'auto',
        			width: 'auto',
        			modal: true,
        			open : function () {
        				// Set the selected date on start/end time fields
        				// This statmement can tell is showTimeEntryDialog() by other methods or triggered directly by backbone.
        			},
        			close : function () {

        			},
        			buttons : {
        				"Forfeit" : function () {
        					window.location = "LeaveCalendarSubmit.do?methodToCall=approveLeaveCalendar&documentId=" + docId + "&action=R";
        					$(this).dialog("close");
        				},
        				Cancel : function () {
        					$(this).dialog("close");
        				}
        			}
        		});
    		}
    	},
        
        showOnDemandBalanceTransferDialog : function (e) {
        	var docId = $('#documentId').val();
        	var accrualRuleId = _(e).parseEventKey().id;
            $('#lm-transfer-empty').empty();
            $('#lm-transfer-dialog').append('<iframe width="800" height="600" src="BalanceTransfer.do?methodToCall=balanceTransferOnDemand&docTypeName=BalanceTransferDocumentType&timesheet=false&accrualRuleId='+ accrualRuleId +'&documentId='+docId+'"></iframe>');

            $('#lm-transfer-dialog').dialog({
                autoOpen: true,
                height: 'auto',
                width: 'auto',
                modal: true,
                buttons: {
                    //"test" : function() {
                    //}
                },
                beforeClose: function(event, ui) {
                    var URL = unescape(window.parent.location.pathname);
                    window.parent.location.href = URL;
                    window.close();
                }
            });
        },
        
        // Button for iFrame show/hide to show the missed punch items
        // The iFrame is added to the missed-punch-dialog as a child element.
        // tdocid is a variable that is set from the form value in 'clock.jsp'
        showOnDemandBalancePayoutDialog : function (e) {
        	var docId = $('#documentId').val();
        	var accrualRuleId = _(e).parseEventKey().id;
            $('#lm-payout-empty').empty();
            $('#lm-payout-dialog').append('<iframe width="800" height="600" src="LeavePayout.do?methodToCall=leavePayoutOnDemand&docTypeName=LeavePayoutDocumentType&timesheet=false&accrualRuleId=' + accrualRuleId + '&documentId='+docId+'"></iframe>');

            $('#lm-payout-dialog').dialog({
                autoOpen: true,
                height: 'auto',
                width: 'auto',
                modal: true,
                buttons: {
                    //"test" : function() {
                    //}
                },
                beforeClose: function(event, ui) {
                    var URL = unescape(window.parent.location.pathname);
                    window.parent.location.href = URL;
                    window.close();
                }
            });
        },
/*
        // Button for iFrame show/hide to show the leave payout items
        // The iFrame is added as a child element.
        // tdocid is a variable that is set from the form value in 'clock.jsp'
        showLeavePayoutDialog : function (e) {

            $('#lm-leavepayout-empty').empty();
            $('#lm-leavepayout-dialog').append('<iframe width="800" height="600" src="LeavePayout.do?methodToCall=leavePayout&command=initiate&docTypeName=LeavePayoutDocumentType&documentId="></iframe>');

            $('#lm-leavepayout-dialog').dialog({
                autoOpen: true,
                height: 'auto',
                width: 'auto',
                modal: true,
                buttons: {
                    //"test" : function() {
                    //}
                },
                beforeClose: function(event, ui) {
                    var URL = unescape(window.parent.location.pathname);
                    window.parent.location.href = URL;
                    window.close();
                }
            });
        },*/

        tableCellMouseDown : function(e) {
            //alert(this.index);
            if ($('#docEditable').val() == 'false') {
                return null;
            }
            var key = parseInt(_(e).parseEventKey().id);
            if (mouseDownIndex == undefined) {
                mouseDownIndex = key;
            }
            var lower;
            var higher;
            //grab start location
            tableCells.bind('mouseenter',function(e){
                //currentMouseIndex = parseInt(_(e).parseEventKey().id);
                currentMouseIndex = parseInt(this.id.split("_")[1]);
                lower = mouseDownIndex < currentMouseIndex ? mouseDownIndex : currentMouseIndex;
                higher = currentMouseIndex > mouseDownIndex ? currentMouseIndex : mouseDownIndex;

                for (var i=0; i<tableCells.length;i++) {
                    if (i < lower || i > higher) {
                        $("#day_"+i).removeClass("ui-selecting");
                    } else {
                        $("#day_"+i).addClass("ui-selecting");
                    }
                }
            });
        },

        tableCellMouseUp : function(e) {
            tableCells.unbind('mouseenter');
            tableCells.removeClass("ui-selecting");
            if ($('#docEditable').val() == 'false') {
                return null;
            }
            if (_(e).parseEventKey().action == "leaveBlockDelete") {
                return null;
            }

            var currentDay = new Date(beginPeriodDateTimeObj);
            var startDay = new Date(currentDay);
            var endDay = new Date(currentDay);

            var lower = mouseDownIndex < currentMouseIndex ? mouseDownIndex : currentMouseIndex;
            var higher = currentMouseIndex > mouseDownIndex ? currentMouseIndex : mouseDownIndex;
            if (lower == undefined) {
                lower = higher;
            }

            startDay.addDays(lower);
            endDay.addDays(higher);

            startDay = Date.parse(startDay).toString(CONSTANTS.TIME_FORMAT.DATE_FOR_OUTPUT);
            endDay = Date.parse(endDay).toString(CONSTANTS.TIME_FORMAT.DATE_FOR_OUTPUT);

            app.showLeaveBlockEntryDialog(startDay, endDay,null);

            // https://uisapp2.iu.edu/jira-prd/browse/TK-1593
            if ($("#selectedAssignment").is("input")) {
                app.fetchEarnCodeAndLoadFields();
            }
            mouseDownIndex = null;
            currentMouseIndex = null;

        },
        
        /**
         * Reset the values on the leaveblock entry form.
         * @param fields
         */
        resetLeaveBlockDialog : function (timeBlockDiv) {
        	 $("#leaveAmount").val("");
        	 $("#selectedEarnCode").val("");
        	 $("#description").val("");
        	 $("#leaveBlockId").val("");
        },
        
        /**
         * Remove the error class from the given fields.
         * @param fields
         */
        resetState : function (fields) {
            // Remove the error / warning texts
            $("#validation").text("").removeClass("error-messages");

            // Remove the error classs
            $("[class*=error]", fields).each(function () {
                $(this).removeClass("ui-state-error");
            });

            // Remove the sylte for multi-day selection
            $('.cal-table td').removeClass('ui-selected');

            // Remove all the readonly / disabled states
            $("input, select", "#timesheet-panel").attr("disabled", false);
            $("input, select", "#timesheet-panel").attr("readonly", false);

            // This is mainly to solve the issue where the change event on the assignment was unbound
            // when the user can only change the assignment on the timeblock.
            // Reset the events by calling the built-in delegateEvents function.
            this.delegateEvents(this.events);

            // show date pickers
            $(".ui-datepicker-trigger").show();
        },

        changeAssignment : function () {
            this.fetchEarnCodeAndLoadFields();
        },

        fetchEarnCodeAndLoadFields : function () {
            var dfd = $.Deferred();
            dfd.done(this.fetchEarnCode(_.getSelectedAssignmentValue()))
                .done(this.showFieldByEarnCodeType());
        },

        validateEarnCode : function () {
            var isValid = true;
            isValid = isValid && this.checkEmptyField($("#selectedEarnCode"), "Earn Code");

            // couldn't find an easier way to get the earn code json, so we validate by the field id
            // The method below will get a list of not hidden fields' ids
            var ids = $("#dialog-form input").not(":hidden").map(
                    function () {
                        return this.id;
                    }).get();
             if (_.contains(ids, "leaveAmount")) {
                var hours = $('#leaveAmount');
                isValid = isValid && (this.checkEmptyField(hours, "Leave amount") && this.checkZeroValue(hours, 'Leave amount cannot be zero') && this.checkNumeric(hours, /^-?\d+(\.\d{1,})?$/, 'Enter valid leave amount'));
                if(isValid) {
                	var type = this.getEarnCodeUnit(EarnCodes.toJSON(), $('#selectedEarnCode option:selected').val());
                	if (type == 'D') {
                		isValid = isValid && (this.checkRangeValue(hours, 1, "Leave amount Days"));
                	} else if (type == 'H') {
                		isValid = isValid && (this.checkRangeValue(hours, 24, "Leave amount Hours"));
                	} 
                	var fieldLength = 0;
                	var fracLength = 0;
                	// check fraction digit
                	if(isValid) {
                		var fraction = this.getEarnCodeFractionalAllowedTime(EarnCodes.toJSON(), $('#selectedEarnCode option:selected').val());
                		if(typeof fraction != 'undefined' && fraction != '') {
                			var fractionAr = fraction.split(".");
                			var hoursAr = hours.val().split(".");
                			if(hoursAr.length > 1) {
                				fieldLength = hoursAr[1].length;
                			} else {
    	        				fieldLength = 0;
    	        			}
                			if(fractionAr.length > 1) {
                				fracLength = fractionAr[1].length;
                			} else {
    	        				fracLength = 0 ;
    	        			}
                			if(fieldLength > fracLength) {
                				isValid = false;
                				this.displayErrorMessages("Leave Amount field should be in the format of "+fraction);
                			}
                		}
                	}
                }
            } else if (_.contains(ids, "startTimeHourMinute") && _.contains(ids, "endTimeHourMinute")) {
                     // the format has to be like "12:00 AM"
                     isValid = isValid && this.checkLength($('#startTimeHourMinute'), "Time entry", 8, 8);
                     isValid = isValid && this.checkLength($('#endTimeHourMinute'), "Time entry", 8, 8);
                    
            }
            return isValid;
        },

        fetchEarnCode : function (e, isTimeBlockReadOnly) {

            isTimeBlockReadOnly = _.isUndefined(isTimeBlockReadOnly) ? false : isTimeBlockReadOnly;

            // When the method is called with a passed in value, the assignment is whatever that value is;
            // If the method is called WITHOUT a passed in value, the assignment is an event.
            // We want to be able to use this method in creating and editing timeblocks.
            // If assignment is not a string, we'll grab the selected assignment on the form.
            var assignment = _.isString(e) ? e : this.$("#selectedAssignment option:selected").val();
            // We want to remember what the previous selected earn code was.
            var earnCode = this.$('#selectedEarnCode option:selected').val();
            var startDate = this.$("#startDate").val();

            // Fetch earn codes based on the selected assignment
            // The fetch function is provided by backbone.js which also supports jQuery.ajax options.
            // For more information: http://documentcloud.github.com/backbone/#Collection-fetch
            EarnCodes.fetch({
                // Make the ajax call not async to be able to mark the earn code selected
                async : false,
                data : {
                    selectedAssignment : assignment,
                    startDate : startDate,
                    timeBlockReadOnly : isTimeBlockReadOnly
                }
            });
            // If there is an earn code in the newly created collection that matches the old
            // earn code, keep the earn code selected.
            if(_.contains(EarnCodes.pluck('earnCode'),earnCode)) {
                $("#selectedEarnCode option:selected").attr("selected",false);
//              $("#selectedEarnCode option[value='" + earnCode + "']").attr("selected", "selected");
                $("#selectedEarnCode option:first").attr("selected", "selected");
            }
        },


        addAllEarnCodes : function () {
            var view = new EarnCodeView({collection : EarnCodes});
            // Append the earn code to <select>
            $("#earnCode-section").append(view.render().el);

        },

        changeEarnCode : function(e) {
            // validate leaveblocks
        	var earnCodeString = _.isString(e) ? e : this.$("#selectedEarnCode option:selected").val();
        	earnCodeObj.fetch({
                // Make the ajax call not async to be able to mark the earn code selected
                async : false,
                data : {
                	selectedEarnCode : earnCodeString
                }  
            });
        	this.showFieldByEarnCodeType();
        },
                
        showFieldByEarnCodeType : function () {
        	var fieldSections = [".clockInSection", ".clockOutSection", ".leaveAmountSection"];
  			if($("#selectedEarnCode option:selected").val() == '') {
  				return;
  			}
        	var earnCodeUnit = this.getEarnCodeUnit(EarnCodes.toJSON(), $("#selectedEarnCode option:selected").val());
        	if (earnCodeUnit == CONSTANTS.EARNCODE_UNIT.DAY) {
        		$('#unitOfTime').text('* Days');
        		$(_.without(fieldSections, ".leaveAmountSection").join(",")).hide();
                $(fieldSections[2]).show();
        	} else if (earnCodeUnit == CONSTANTS.EARNCODE_UNIT.HOUR) {
        		$('#unitOfTime').text('* Hours');
        		$(_.without(fieldSections, ".leaveAmountSection").join(",")).hide();
                $(fieldSections[2]).show();
        	} else if (earnCodeUnit == CONSTANTS.EARNCODE_UNIT.TIME) {
        		 $(_.without(fieldSections, ".clockInSection", ".clockOutSection").join(",")).hide();
                 $(fieldSections[0] + "," + fieldSections[1]).show();
                 $('#leaveAmount').val("");
        	}
            var defaultTime = this.getEarnCodeDefaultTime(EarnCodes.toJSON(), $("#selectedEarnCode option:selected").val());
            $('#leaveAmount').val(defaultTime);
            
        },
        
        getEarnCodeUnit : function (earnCodeJson, earnCode) {
            var matchedEarnCode = _.filter(earnCodeJson, function (json) {
                return json["earnCode"] == earnCode
            });
            return _.first(matchedEarnCode).unitOfTime;
        },
        
        getEarnCodeDefaultTime : function (earnCodeJson, earnCode) {
        	 var matchedEarnCode = _.filter(earnCodeJson, function (json) {
                 return json["earnCode"] == earnCode
             });
             return _.first(matchedEarnCode).defaultAmountofTime;
        },
        
        getEarnCodeFractionalAllowedTime : function (earnCodeJson, earnCode) {
        	 var matchedEarnCode = _.filter(earnCodeJson, function (json) {
                 return json["earnCode"] == earnCode
             });
             return _.first(matchedEarnCode).fractionalTimeAllowed;
        },
        

        validateLeaveBlock : function () {
            var self = this;
            var isValid = true;
            
            isValid = isValid && this.checkEmptyField($("#startDate"), "Start Date");
            isValid = isValid && this.checkEmptyField($("#endDate"), "End Date");
            isValid = isValid && this.checkStartEndDateFields($("#startDate"), $("#endDate"),"Start Date");
            isValid = isValid && this.validateEarnCode();
             
            if (isValid) {
           
                var docId = $('#documentId').val();
                var calId = $('#hrCalendarEntryId').val();
                var params = {};
                params['startDate'] = $('#startDate').val();
                params['endDate'] = $('#endDate').val();
                params['startTime'] = $('#startTime').val();
                params['endTime'] = $('#endTime').val();
                params['leaveAmount'] = $('#leaveAmount').val();
                params['selectedEarnCode'] = $('#selectedEarnCode option:selected').val();
                params['spanningWeeks'] = $('#spanningWeeks').is(':checked') ? 'y' : 'n'; // KPME-1446
                params['leaveBlockId'] = $('#leaveBlockId').val();
                params['hrCalendarEntryId'] = $('#hrCalendarEntryId').val();

                // validate leaveblocks
                $.ajax({
                    async : false,
                    url : "LeaveCalendarWS.do?methodToCall=validateLeaveEntry&documentId=" + docId + "&hrCalendarEntryId=" + calId,
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

                            self.displayErrorMessages(errorMsgs);
                            isValid = false;
                        }
                    },
                    error : function () {
                        self.displayErrorMessages("Error: Can't save data.");
                        isValid = false;
                    }
                });
            }
            
            return isValid;
        },
        
        checkLength : function (o, n, min, max) {
            if (o.val().length > max || o.val().length < min) {
                this.displayErrorMessages(n + " field cannot be empty", o);
                return false;
            }
            return true;
        },

        checkEmptyField : function (o, field) {
            var val = o.val();
            if (val == '' || val == undefined) {
                this.displayErrorMessages(field + " field cannot be empty", o);
                return false;
            }
            return true;
        },
        
        checkStartEndDateFields : function (o1, o2, field) {
            var val1 = new Date(o1.val());
            var val2 = new Date(o2.val());
            if (val1 > val2) {
                this.displayErrorMessages(field + " is later than end date.", o1);
                return false;
            }
            return true;
        },

        checkRegexp : function (o, regexp, n) {
            if (( o.val().match(regexp) )) {
                this.displayErrorMessages(n, o);
                return false;
            }
            return true;
        },
        
        checkNumeric : function (o, regexp, n) {
            if (!( o.val().match(regexp) )) {
                this.displayErrorMessages(n, o);
                return false;
            }
            return true;
        },

        checkSpecificValue : function (o, value, n) {
            if (o.val() != value) {
                this.displayErrorMessages(n);
                return false;
            }
            return true;
        },
        
        checkRangeValue : function (o, value1, field) {
            if (o.val() > value1) {
            	this.displayErrorMessages(field + " field should not exceed " + value1, o);
            	return false;
            }
            return true;
        },
        
        checkZeroValue : function (o, n) {
            if (o.val() ==  0) {
            	this.displayErrorMessages(n, o);
            	return false;
            }
            return true;
        },

        displayErrorMessages : function (t, object) {
            // add the error class ane messages
            $('#validation').html(t)
                    .addClass('error-messages');

            // highlight the field
            if (!_.isUndefined(object)) {
                object.addClass('ui-state-error');
            }
        },
        
        checkPermissions : function(startDate, endDate) {
			var isValid = false;
			var curStartDate = $("#beginPeriodDateTime").val();
			var curEndDate = $("#endPeriodDateTime").val();
			var currentDay = new Date(beginPeriodDateTimeObj);
			var targetDay;
			if(!_.isUndefined(startDate) && !_.isUndefined(endDate)) {
    			targetDay = new Date(startDate);
            } else {
            	targetDay = currentDay.addDays(parseInt((startDate.target.id).split("_")[1]));
            }
			
			var clickDate = new Date(targetDay).getTime();
			// Can't add a new timeblock is the doc is not editable.
			if ($('#docEditable').val() == "false") {
				if(!_.isUndefined(curStartDate) && !_.isUndefined(curEndDate)){
					   var sd = new Date(curStartDate).getTime();
					   var ed = new Date(curEndDate).getTime();
						if(!_.isUndefined(clickDate) && (clickDate >= sd) && (clickDate <= ed)) {
							isValid = true;
						} else {
							isValid = false;
						}
				} 
			} else {
				isValid = true;
			}
			if(!isValid) {
			 $('.cal-table td').removeClass('ui-selected');
			}
			return isValid;
		}

    });

    var EarnCodeView = Backbone.View.extend({
        el : $("#selectedEarnCode"),

        template : _.template($('#earnCode-template').html()),

        initialize : function () {
            _.bindAll(this, "render");
        },

        render : function () {
            var self = this;
            $("#selectedEarnCode").html("");
            $(self.el).append("<option value=''>-- select an earn code --</option>");
            this.collection.each(function (earnCode) {
                $(self.el).append(self.template(earnCode.toJSON()));
            });

            return this;
        }
    });

    // Initialize the view. This is the kick-off point.
    var app = new LeaveBlockView;

    /**
     * Custom util functions.
     * This is the section where you can create your own util methods and inject them to Underscore.
     *
     * The usage of the custom functions is like _(event.target.id).parseEventKey();
     */
    _.mixin({
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
         * Fill in the leave entry form by the leaveblock
         * @param leaveBlock
         */
        fillInForm : function (leaveBlock) {
            $('#startDate').val(leaveBlock.get("leaveDate"));
            $('#endDate').val(leaveBlock.get("leaveDate"));
            $('#startTime').val(leaveBlock.get("startTime"));
            $('#endTime').val(leaveBlock.get("endTime"));
            $('#startTimeHourMinute').val(leaveBlock.get("startTimeHourMinute"));
            $('#endTimeHourMinute').val(leaveBlock.get("endTimeHourMinute"));
            $('#leaveAmount').val(leaveBlock.get("leaveAmount"));
            $('#leaveBlockId').val(leaveBlock.get("lmLeaveBlockId"));
            $("#selectedAssignment option[value='" + leaveBlock.get("assignment") + "']").attr("selected", "selected");
            $("#selectedEarnCode option[value='" + leaveBlock.get("earnCode") + "']").attr("selected", "selected");
            $('#description').val(leaveBlock.get("description"));
            if (leaveBlock.get("editable") == false) {
                $('#startDate').attr('disabled', 'disabled');
                $('#endDate').attr('disabled', 'disabled');
                $('#startTime').attr('disabled', 'disabled');
                $('#endTime').attr('disabled', 'disabled');
                $('#startTime').val(leaveBlock.get("startTime"));
                $('#endTime').val(leaveBlock.get("endTime"));
                $('#startTimeHourMinute').val(leaveBlock.get("startTimeHourMinute"));
                $('#endTimeHourMinute').val(leaveBlock.get("endTimeHourMinute"));
                $('#selectedAssignment').attr('disabled', 'disabled');
                $('#selectedEarnCode').attr('disabled', 'disabled');
                $('#leaveAmount').attr('disabled', 'disabled');
                $('#leaveBlockId').attr('disabled', 'disabled');
                $('#description').attr('disabled', 'disabled');
                $('#spanningWeeks').attr('disabled', 'disabled');
            }
        },
        /**
         * Provides a helper method to change the button name on the time entry dialog.
         * @param oriText
         * @param newText
         */
        replaceDialogButtonText : function (oriText, newText) {
            $(".ui-button-text:contains('" + oriText + "')").text(newText);
        },

        /**
         * The selected assignment field can be a hidden text field if there is only one assignment, or a dropdown if there are multiple assignments
         * This helper method will check which type of field is presented and return the value
         */
        getSelectedAssignmentValue : function () {
            var $selectedAssignment = $("#selectedAssignment")
            if ($selectedAssignment.is("input")) {
                return $selectedAssignment.val();
            } else {
                return $("#selectedAssignment option:selected").val();
            }
        }
    });

    /**
     * Setup for making calendar cells selectable
     */
    var mouseDownIndex = null;
    var currentMouseIndex = null;
    var beginPeriodDateTimeObj = $('#beginPeriodDateTime').val() !== undefined ? new Date($('#beginPeriodDateTime').val()) : d + '/' + m + '/' + y;
    var endPeriodDateTimeObj = $('#beginPeriodDateTime').val() !== undefined ? new Date($('#endPeriodDateTime').val()) : d + '/' + m + '/' + y;
    var tableCells = $('td[id^="day_"]');
    tableCells.disableSelection();

	// KPME-1390 Requested shortcut for 1.5 Leave Calendar
    // use keyboard to open the form
    var isCtrl,isAlt = false;
	
	// ctrl+alt+a will open the form
    $(this).keydown(
        function(e) {

            if (e.ctrlKey) isCtrl = true;
            if (e.altKey) isAlt = true;
            
			var startDate = new Date();
			var endDate = new Date();
			
			startDate = Date.parse(startDate).toString(CONSTANTS.TIME_FORMAT.DATE_FOR_OUTPUT);
			endDate = Date.parse(endDate).toString(CONSTANTS.TIME_FORMAT.DATE_FOR_OUTPUT);
			
            if (e.keyCode == 65 && isCtrl && isAlt) {
                app.showLeaveBlockEntryDialog(startDate,endDate);
            }

        }).keyup(function(e) {
            isCtrl = false;
            isAlt = false;
        });
    // End: KPME-1390
    
    if ($('#docEditable').val() == 'false') {
        $(".cal-table").selectable("destroy");
        tableCells.unbind("mousedown");
        tableCells.unbind('mouseenter');
        tableCells.unbind('mouseup');
    }
});
