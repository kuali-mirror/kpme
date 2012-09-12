$(function () {
    /**
     *
     * README:
     *
     * TODO:
     * 1) explain how the flow works
     *
     * Notes:
     * 1) When editing a timeblock, why didn't we use a template?
     *
     *    A Underscore template is surrounded by <script type="text/template">.
     *    The advantage of using that is we can use the custom syntax : <%= foo %>
     *    to fill out the values in the template, e.g.
     *
     *    this.$('#timesheet-panel').html(this.timeEntrytemplate({
     *      foo = "test"
     *    });
     *
     *    This approach works fine if there is no extra javascript in the template.
     *    In our case, we have a jQuery datepicker and an hour entering helper on
     *    the form. They won't work in the template since a template is considered
     *    as a big chunk of text.
     *
     * 2) We don't use Backbone's router here. See the reasons here: http://bit.ly/Ajbnog
     */

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
     * Timeblock
     */
        // Create a time block model
    TimeBlock = Backbone.Model;


    // Create a time block collection that holds multiple time blocks. This is essentially a list of hashmaps.
    TimeBlockCollection = Backbone.Collection.extend({
        model : TimeBlock
    });

    // Convert the time block json string to a json object
    var timeBlockJson = jQuery.parseJSON($("#timeBlockString").val());
    // Cass in the json object to create a Backbone time block collection
    var timeBlockCollection = new TimeBlockCollection(timeBlockJson);

    /**
     * Earn Code
     */
        // Create an earn code model
    EarnCode = Backbone.Model;

    // Create a collection for earn codes
    EarnCodeCollection = Backbone.Collection.extend({
        model : EarnCode,
        url : "TimeDetailWS.do?methodToCall=getEarnCodeJson"
    });

    var EarnCodes = new EarnCodeCollection;

    /**
     * Overtime Earn Code
     */

        // create an overtime earn code model
    OvertimeEarnCode = Backbone.Model;

    // Create a collecton for overtime earn codes
    OvertimeEarnCodeCollection = Backbone.Collection.extend({
        model : OvertimeEarnCode,
        url : "TimeDetailWS.do?methodToCall=getOvertimeEarnCodes"
    });

    OvertimeEarnCodes = new OvertimeEarnCodeCollection;

    /**
     * ====================
     * Views
     * ====================
     */

    // create a timeblock view
    var TimeBlockView = Backbone.View.extend({
        // Set the element that our dialog form wants to bind to. This setting is necessary.
        el : $("body"),

        // Events are in the jQuery format.
        // The first part is an event and the second part is a jQuery selector.
        // Check out this page for more information about the jQuery selectors : http://api.jquery.com/category/selectors/
        events : {
            "click div[id*=show]" : "showTimeBlock",
            "click img[id^=timeblockDelete]" : "deleteTimeBlock",
            "click img[id^=lunchDelete]" : "deleteLunchDeduction",
            "click img[id^=leaveBlockDelete]" : "deleteLeaveBlock",
            // .create is the div that fills up the white sapce and .day-number is the div with the day number on it.
            // <div class="create"></div> is in calendar.tag.
            // We want to trigger the show event on any white space areas.
            "click .event" : "doNothing",
            "click .create" : "showTimeEntryDialog",
            "click span[id*=overtime]" : "showOverTimeDialog",
            "blur #startTimeHourMinute, #endTimeHourMinute" : "formatTime",
            // TODO: figure out how to chain the events
            "change #selectedAssignment" : "changeAssignment",
            "keypress #selectedAssignment" : "changeAssignment",
            "change #selectedEarnCode" : "showFieldByEarnCodeType",
            "keypress #selectedEarnCode" : "showFieldByEarnCodeType"
        },

        initialize : function () {

            // This step binds the functions to the view so you can call the methods like : this.addOneEarnCode
            _.bindAll(this, "addAllEarnCodes", "addAllOvertimeEarnCodes", "render");

            // Bind the events onto the earncode collection, so these events will be triggered
            // when an earncode collection is created.
            EarnCodes.bind('add', this.addAllEarnCodes);
            EarnCodes.bind('reset', this.addAllEarnCodes);

            OvertimeEarnCodes.bind('add', this.addAllOvertimeEarnCodes);
            OvertimeEarnCodes.bind('reset', this.addAllOvertimeEarnCodes);
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
            var dateTime = Date.parse(value);
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

        showTimeEntryDialog : function (startDate, endDate) {
            // check user permmissions before opening the dialog.
            var isValid = this.checkPermissions();

            var self = this;
            if (isValid) {
                $("#dialog-form").dialog({
                    title : "Add Time Blocks : ",
                    closeOnEscape : true,
                    autoOpen : true,
                    height : 'auto',
                    width : '450',
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
                            if ($("#selectedAssignment").is("input")) {
                                var dfd = $.Deferred();
                                dfd.done(self.fetchEarnCode(_.getSelectedAssignmentValue()))
                                        .done(self.showFieldByEarnCodeType());
                            }
                        }

                    },
                    close : function () {
                        // reset values on the form
                        self.resetTimeBlockDialog($("#timesheet-panel"));
                        self.resetState($("#dialog-form"));
                    },
                    buttons : {
                        "Add" : function () {
                            /**
                             * In case we have more needs to auto-adjust user's input, we should consider moving them to a separate method.
                             */

                            if (!_.isEmpty($("#endTime").val())) {
                                // If the end time is 12:00 am, change the end date to the next day
                                var midnight = Date.parse($('#endDate').val()).set({
                                    hour : 0,
                                    minute : 0,
                                    second : 0,
                                    millisecond : 0
                                });

                                // Parse the date by the format like 1/2/2011 8:0
                                var endDateTime = Date.parse($('#endDate').val() + " " + $('#endTime').val());

                                // Compare and see if the end time is at midnight
                                if (Date.compare(midnight, endDateTime) == 0) {
                                    $('#endDate').val(endDateTime.add(1).days().toString(CONSTANTS.TIME_FORMAT.DATE_FOR_OUTPUT));
                                }
                            }

                            $('#acrossDays').val($('#acrossDays').is(':checked') ? 'y' : 'n');
                            $('#spanningWeeks').val($('#spanningWeeks').is(':checked') ? 'y' : 'n');  // KPME-1446

                            var isValid = true;
                            // If the user can only update the assignment, there is no need to do the validations.
                            var canEditAssignmentOnly = $("#selectedEarnCode").is('[disabled]');
                            if (canEditAssignmentOnly) {
                                $('#methodToCall').val(CONSTANTS.ACTIONS.UPDATE_TIME_BLOCK);
                            } else {
                                isValid = self.validateTimeBlock();
                                $("#methodToCall").val(CONSTANTS.ACTIONS.ADD_TIME_BLOCK);
                            }

                            if (isValid) {
                                $('#time-detail').submit();
                                $(this).dialog("close");
                            }
                        },
                        Cancel : function () {
                            $(this).dialog("close");

                        }
                    }
                });
            }
        },

        showOverTimeDialog : function (e) {
            var self = this;
            // convert the event to a jQuery event.
            var id = (e.target || e.srcElement).id

            var key = _(e).parseEventKey();
            var timeBlock = timeBlockCollection.get(key.id);
            var currentOvertimePref = $.trim($("#" + id).text());
            var dfd = $.Deferred();

            // The content of the overtimePref is in a separate template,
            // but when we submit the form, we will want to keep all the original values.
            // That's why we call the fillInform() method below, so all the values will still be there when the form is submitted.
            dfd.done(self.fetchOvertimeEarnCode())
                    .done($("#overtimePref option[value='" + currentOvertimePref + "']").attr("selected", "selected"))
                    .done(_(timeBlock).fillInForm());

            $("#overtime-section").dialog({
                title : "Change the overtime earn code : ",
                autoOpen : true,
                closeOnEscape : true,
                height : 'auto',
                width : '450',
                modal : true,
                close : function () {
                    self.resetTimeBlockDialog($("#timesheet-panel"));
                },
                buttons : {
                    "Update" : function () {
                        $('#methodToCall').val(CONSTANTS.ACTIONS.ADD_TIME_BLOCK);

                        // selected earn code and overtimePref are two special cases where the DOMs are managed by the backbone templates.
                        // So when the dialog is poped up, the dropdown options are not appended.
                        // That's why we have to do it manually to append those two fields to the time-detial form.
                        $("#selectedEarnCode").append("<option value=" + timeBlock.get('earnCode') + " selected='selected'></option>").clone().appendTo("#time-detail");
                        $("#overtimePref").appendTo("#time-detail");

                        $('#time-detail').submit();
                        $(this).dialog("close");
                    },
                    Cancel : function () {
                        $(this).dialog("close");

                    }
                }
            });
        },

        showTimeBlock : function (e) {
            var key = _(e).parseEventKey();
            // Retrieve the selected timeblock
            var timeBlock = timeBlockCollection.get(key.id);
            this.showTimeEntryDialog(timeBlock.get("startDate"), timeBlock.get("endDate"));
            _.replaceDialogButtonText("Add", "Update");

            // Deferred is a jQuery method which makes sure things happen in the order you want.
            // For more informaiton : http://api.jquery.com/category/deferred-object/
            // Here we want to fire the ajax call first to grab the earn codes.
            // After that is done, we fill out the form and make the entry field show / hide based on the earn code type.
            var dfd = $.Deferred();

            // https://uisapp2.iu.edu/jira-prd/browse/TK-1577
            // A sync user can't change the RGH earncode on a sync timeblock but there is a special case.
            // When the timeblock is readonly, which means the user can only change the assignment,
            // the user should be able to _see_ the RGH earn code.
            var isTimeBlockReadOnly = timeBlock.get("canEditTBAssgOnly") ? true : false;

            dfd.done(this.fetchEarnCode(timeBlock.get("assignment"), isTimeBlockReadOnly))
                    .done($("#selectedEarnCode option[value='" + timeBlock.get("earnCode") + "']").attr("selected", "selected"))
                    .done(this.showFieldByEarnCodeType())
                    .done(_(timeBlock).fillInForm())
                    .done(this.applyRules(timeBlock));
        },
        /**
         * Check user permissions.
         * @param e
         */
        checkPermissions : function () {
            var isValid = true;

            // Can't add a new timeblock is the doc is not editable.
            if ($('#docEditable').val() == "false") {
                isValid = false;
            }

            // Can't add / edit / delete a timeblock is canAddTimeBlock is false
            // This happens when the login user has a view only role, or a dept / location admin
            if ($('#canAddTimeBlock').val() == "false") {
                isValid = false;
            }

            return isValid;
        },
        /**
         * Apply rules on the time block entry form:
         * 1) Permissions to update / delete an existing timeblock.
         * @param e
         */
        applyRules : function (e) {
            /**
             * Employee- can only edit the assign on sync timeblocks
             can edit/delete all fields for async timeblocks
             whether or not they can modify the OVT earn code is decided by the defined role on the work area

             Reviewer/Approver/Admin-
             can add/edit/delete timeblocks only for the assign they are associated with, on those timeblocks they can edit all fields regardless of
             how timeblocks created (including ovt earn codes)
             any timeblock they are not assoc w/ they can only edit the assign (and can not delete)
             */

            // Rendering the delete button or not has been taken care of by the server side.
            // We only need to worry about making fields disabled.
            if (e.get("canEditTBAssgOnly")) {
                // Make everything read only except the assignment
                $("input, select", $("#timesheet-panel")).attr("disabled", true);
                // hide the date picker
                $(".ui-datepicker-trigger").hide();
                $("#selectedAssignment", $("#timesheet-panel")).attr("disabled", false);

                // Unbind the change events.
                // The events will be bound again when resetState() is called.
                $(this.el).undelegate("#selectedAssignment", "change");
                $(this.el).undelegate("#selectedAssignment", "keypress");
            }

        },

        deleteTimeBlock : function (e) {
            var key = _(e).parseEventKey();
            var timeBlock = timeBlockCollection.get(key.id);

            if (this.checkPermissions()) {
                if (confirm('You are about to delete a time block. Click OK to confirm the delete.')) {
                    window.location = "TimeDetail.do?methodToCall=deleteTimeBlock&documentId=" + timeBlock.get("documentId") + "&tkTimeBlockId=" + key.id;
                }
            }
        },

        deleteLunchDeduction : function (e) {
            var key = _(e).parseEventKey();
//            var timeBlock = timeBlockCollection.get(key.id);
            if (this.checkPermissions()) {
                if (confirm('You are about to delete the lunch deduction. Click OK to confirm the delete.')) {
                    window.location = "TimeDetail.do?methodToCall=deleteLunchDeduction&documentId=" + $("#documentId").val() + "&tkTimeHourDetailId=" + key.id;
                }
            }
        },

        deleteLeaveBlock : function (e) {
            var key = _(e).parseEventKey();
        	if (confirm('You are about to delete a leave block. Click OK to confirm the delete.')) {
        		window.location = "TimeDetail.do?methodToCall=deleteLeaveBlock&lmLeaveBlockId=" + key.id;
        	}
        },

        fetchEarnCode : function (e, isTimeBlockReadOnly) {

            isTimeBlockReadOnly = _.isUndefined(isTimeBlockReadOnly) ? false : isTimeBlockReadOnly;

            // When the method is called with a passed in value, the assignment is whatever that value is;
            // If the method is called WITHOUT a passed in value, the assignment is an event.
            // We want to be able to use this method in creating and editing timeblocks.
            // If assignment is not a string, we'll grab the selected assignment on the form.
            var assignment = _.isString(e) ? e : this.$("#selectedAssignment option:selected").val();

            // Fetch earn codes based on the selected assignment
            // The fetch function is provided by backbone.js which also supports jQuery.ajax options.
            // For more information: http://documentcloud.github.com/backbone/#Collection-fetch
            EarnCodes.fetch({
                // Make the ajax call not async to be able to mark the earn code selected
                async : false,
                data : {
                    selectedAssignment : assignment,
                    timeBlockReadOnly : isTimeBlockReadOnly
                }
            });
        },


        addAllEarnCodes : function () {
            var view = new EarnCodeView({collection : EarnCodes});
            // Append the earn code to <select>
            $("#earnCode-section").append(view.render().el);

        },

        fetchOvertimeEarnCode : function () {
            // Fetch earn codes based on the selected assignment
            // The fetch function is provided by backbone.js which also supports jQuery.ajax options.
            // See here for more information: http://documentcloud.github.com/backbone/#Collection-fetch
            this.$("#overtimePref").html("");
            OvertimeEarnCodes.fetch({
                async : false
            });
        },

        addAllOvertimeEarnCodes : function () {
            var view = new OvertimeEarnCodeView({collection : OvertimeEarnCodes});
            // Append the earn code to <select>
            this.$("#overtime-section").append(view.render().el);
        },

        showFieldByEarnCodeType : function () {
  			if($("#selectedEarnCode option:selected").val() == '') {
  				return;
  			}
            var earnCodeType = _.getEarnCodeType(EarnCodes.toJSON(), $("#selectedEarnCode option:selected").val());
            var fieldSections = [".clockInSection", ".clockOutSection", ".hourSection", ".amountSection", ".leaveAmountSection"];
            var leavePlan = this.getEarnCodeLeavePlan(EarnCodes.toJSON(), $("#selectedEarnCode option:selected").val());

            // reset values every time the earn code is changed
            $("#startTimeHourMinute, #startTime, #endTimeHourMinute, #endTime, #hours, #amount, #leaveAmount").val("");
            if(typeof leavePlan != 'undefined' && leavePlan != '' && leavePlan != null && leavePlan != 'undefined') {  // for leave block earn codes
            	var earnCodeUnit = this.getEarnCodeUnit(EarnCodes.toJSON(), $("#selectedEarnCode option:selected").val());
 				if(typeof earnCodeUnit == 'undefined' || earnCodeUnit == '' || earnCodeUnit == null || earnCodeUnit == 'undefined') {
 					var checkFlag = earnCodeType;
 				} else {
 					var checkFlag = earnCodeUnit;
 				}
                $(_.without(fieldSections, ".leaveAmountSection").join(",")).hide();
                $(fieldSections[4]).show();

                if (checkFlag == CONSTANTS.EARNCODE_UNIT.DAY) {
            		$('#unitOfTime').text('* Leave Days');
            	} else if (checkFlag == CONSTANTS.EARNCODE_UNIT.HOUR) {
            		$('#unitOfTime').text('* Leave Hours');
            	}
                var defaultTime = this.getEarnCodeDefaultTime(EarnCodes.toJSON(), $("#selectedEarnCode option:selected").val());
                $('#leaveAmount').val(defaultTime);

            }
            else {
	            // There might be a better way doing this, but we can revisit this later.
	            // Currently, the fields variable contains a list of the entry field classes.
	            // The Underscore.js _.without function returns an array except the ones you speficied.
	            if (earnCodeType == CONSTANTS.EARNCODE_TYPE.HOUR) {
	                $(_.without(fieldSections, ".hourSection").join(",")).hide();
	                $(fieldSections[2]).show();
	                // TODO: figure out why we had to do something crazy like below...
	                $('#startTime, #endTime').val("23:59");
	            } else if (earnCodeType == CONSTANTS.EARNCODE_TYPE.AMOUNT) {
	                $(_.without(fieldSections, ".amountSection").join(",")).hide();
	                $(fieldSections[3]).show();
	                $('#startTime, #endTime').val("23:59");
	            } 
	            else {
	                $(_.without(fieldSections, ".clockInSection", ".clockOutSection").join(",")).hide();
	                $(fieldSections[0] + "," + fieldSections[1]).show();
	            }
            }
        },

        changeAssignment : function () {
            this.fetchEarnCodeAndLoadFields();
        },

        fetchEarnCodeAndLoadFields : function () {
            var dfd = $.Deferred();
            dfd.done(this.fetchEarnCode(_.getSelectedAssignmentValue()))
                    .done(this.showFieldByEarnCodeType());
        },

        /**
         * Validations
         */

        validateTimeBlock : function () {
            var self = this;
            var isValid = true;
            isValid = isValid && this.checkEmptyField($("#selectedAssignment"), "Assignment");
            isValid = isValid && this.validateEarnCode();

            if (isValid) {
                //TODO // compare the original values with the modified ones. if there is no change, close the form instead of submitting it

                var docId = $('#documentId').val();
                var params = {};
                params['startDate'] = $('#startDate').val();
                params['endDate'] = $('#endDate').val();
                params['startTime'] = $('#startTime').val();
                params['endTime'] = $('#endTime').val();
                params['hours'] = $('#hours').val();
                params['amount'] = $('#amount').val();
                params['leaveAmount'] = $('#leaveAmount').val();
                params['selectedAssignment'] = _.getSelectedAssignmentValue();
                params['selectedEarnCode'] = $('#selectedEarnCode option:selected').val();
                if ($("#overtimePref") != undefined) {
                    params['overtimePref'] = $("#overtimePref").val();
                }
                params['acrossDays'] = $('#acrossDays').is(':checked') ? 'y' : 'n';
                params['spanningWeeks'] = $('#spanningWeeks').is(':checked') ? 'y' : 'n'; // KPME-1446
                params['tkTimeBlockId'] = $('#tkTimeBlockId').val();

                // validate timeblocks
                $.ajax({
                    async : false,
                    url : "TimeDetailWS.do?methodToCall=validateTimeEntry&documentId=" + docId,
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

        validateEarnCode : function () {
            var isValid = true;
            isValid = isValid && this.checkEmptyField($("#selectedEarnCode option:selected"), "Earn Code");

            // couldn't find an easier way to get the earn code json, so we validate by the field id
            // The method below will get a list of not hidden fields' ids
            var ids = $("#dialog-form input").not(":hidden").map(
                    function () {
                        return this.id;
                    }).get();

            if (_.contains(ids, "startTimeHourMinute") && _.contains(ids, "endTimeHourMinute")) {
                // the format has to be like "12:00 AM"
                isValid = isValid && this.checkLength($('#startTimeHourMinute'), "Time entry", 8, 8);
                isValid = isValid && this.checkLength($('#endTimeHourMinute'), "Time entry", 8, 8);
            }
            else if (_.contains(ids, "hours")) {
                var hours = $('#hours');
                isValid = isValid && (this.checkEmptyField(hours, "Hour") && this.checkMinLength(hours, "Hour", 1) && this.checkRegexp(hours, '/0/', 'Hours cannot be zero'));
            }
            else if (_.contains(ids, "amount")) {
                var amount = $('#amount');
                isValid = isValid && (this.checkEmptyField(amount, "Amount") && this.checkMinLength(amount, "Amount", 1) && this.checkRegexp(amount, '/0/', 'Amount cannot be zero'));
            }
           // get earn code leave plan, if it's not null, then the change is for a leave block  
           var leavePlan = this.getEarnCodeLeavePlan(EarnCodes.toJSON(), $("#selectedEarnCode option:selected").val());
           if (typeof leavePlan != 'undefined' && leavePlan != '' && leavePlan != null && leavePlan != 'undefined') {
                var leaveAmount = $('#leaveAmount');
                isValid = isValid && (this.checkEmptyField(leaveAmount, "Leave Amount") && this.checkMinLength(leaveAmount, "Leave Amount", 1) && this.checkRegexp(leaveAmount, '/0/', 'Leave Amount cannot be zero'));
                // check fraction allowed by the Earn Code
	            if(isValid) {
	            	var fraction = this.getEarnCodeFractionalAllowedTime(EarnCodes.toJSON(), $('#selectedEarnCode option:selected').val());
	        		if(typeof fraction != 'undefined' && fraction != '') {
	        			var fractionAr = fraction.split(".");
	        			var leaveAmountAr = leaveAmount.val().split(".");
	        			if(leaveAmountAr.length > 1) {
	        				fieldLength = leaveAmountAr[1].length;
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
            
            return isValid;
        },
        
        getEarnCodeLeavePlan : function (earnCodeJson, earnCode) {
            var matchedEarnCode = _.filter(earnCodeJson, function (json) {
                return json["earnCode"] == earnCode
            });
            return _.first(matchedEarnCode).leavePlan;
        },
        
        getEarnCodeFractionalAllowedTime : function (earnCodeJson, earnCode) {
       	 var matchedEarnCode = _.filter(earnCodeJson, function (json) {
                return json["earnCode"] == earnCode
            });
            return _.first(matchedEarnCode).fractionalTimeAllowed;
        },
        
        getEarnCodeDefaultTime : function (earnCodeJson, earnCode) {
       	 var matchedEarnCode = _.filter(earnCodeJson, function (json) {
                return json["earnCode"] == earnCode
            });
            return _.first(matchedEarnCode).defaultAmountofTime;
        },
        getEarnCodeUnit : function (earnCodeJson, earnCode) {
            var matchedEarnCode = _.filter(earnCodeJson, function (json) {
                return json["earnCode"] == earnCode
            });
            return _.first(matchedEarnCode).unitOfTime;
        },

        checkLength : function (o, n, min, max) {
            if (o.val().length > max || o.val().length < min) {
                this.displayErrorMessages(n + " field cannot be empty");
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

        checkRegexp : function (o, regexp, n) {
            if (( o.val().match(regexp) )) {
                this.displayErrorMessages(n);
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

        displayErrorMessages : function (t, object) {
            // add the error class ane messages
            $('#validation').html(t)
                    .addClass('error-messages');

            // highlight the field
            if (!_.isUndefined(object)) {
                object.addClass('ui-state-error');
            }
        },

        checkMinLength : function (o, n, min) {
            if (o.val().length < min) {
                this.displayErrorMessages(n + " field's value is incorrect");
                return false;
            }
            return true;
        },

        /**
         * Reset the values on the timeblock entry form.
         * @param fields
         */
        resetTimeBlockDialog : function (timeBlockDiv) {
            // We don't want to clear out the selected assignment when there is only one assignment and it's a hidden text field
            $("input:not(#selectedAssignment), select", timeBlockDiv).val("");
            // This is not the best solution, but we can probably live with this for now.
            $("#selectedEarnCode").html("<option value=''> -- select an earn code --");
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
        /**
         * Reset the values on the givin fields.
         * @param fields
         */
        resetValues : function (fields) {
            $(fields).val("");
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

    var OvertimeEarnCodeView = Backbone.View.extend({
        el : $("#overtimePref"),

        template : _.template($('#earnCode-template').html()),

        initialize : function () {
            _.bindAll(this, "render");
        },

        render : function () {
            var self = this;
            this.collection.each(function (earnCode) {
                $(self.el).append(self.template(earnCode.toJSON()));
            });

            return this;
        }
    });


    // Initialize the view. This is the kick-off point.
    var app = new TimeBlockView;

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
         * Fill in the time entry form by the timeblock
         * @param timeBlock
         */
        fillInForm : function (timeBlock) {
            $('#startDate').val(timeBlock.get("startDate"));
            $('#startTime').val(timeBlock.get("startTime"));
            $('#startTimeHourMinute').val(timeBlock.get("startTimeHourMinute"));
            $('#endDate').val(timeBlock.get("endDate"));
            $('#endTime').val(timeBlock.get("endTime"));
            $('#endTimeHourMinute').val(timeBlock.get("endTimeHourMinute"));
            $("#selectedAssignment option[value='" + timeBlock.get("assignment") + "']").attr("selected", "selected");
            $('#tkTimeBlockId').val(timeBlock.get("tkTimeBlockId"));
            $('#hours').val(timeBlock.get("hours"));
            $('#amount').val(timeBlock.get("amount"));
            $('#lunchDeleted').val(timeBlock.get("lunchDeleted"));

            if ($('#isVirtualWorkDay').val() == 'true') {
                var endDateTime = Date.parse($('#endDate').val() + " " + $('#endTime').val());
                $('#endDate').val(endDateTime.add(-1).days().toString(CONSTANTS.TIME_FORMAT.DATE_FOR_OUTPUT));
            }

        },
        /**
         * This method takes an earn code json, find the matched earn code, and returns the earn code type.
         * @param earnCodeJson
         * @param earnCode
         */
        getEarnCodeType : function (earnCodeJson, earnCode) {
            var matchedEarnCode = _.filter(earnCodeJson, function (json) {
                return json["earnCode"] == earnCode
            });

            return _.first(matchedEarnCode).type;
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
     * Make the calendar cell selectable
     */
    // When making a mouse selection, it creates a "lasso" effect which we want to get rid of.
    // In the future version of jQuery UI, lasso is going to one of the options where it can be enabled / disabled.
    // For now, the way to disable it is to modify the css.
    //
    // .ui-selectable-helper { border:none; }
    //
    // This discussion thread on stackoverflow was helpful:
    // http://bit.ly/fvRW4X

    var selectedDays = [];
    var selectingDays = [];
    var beginPeriodDateTimeObj = $('#beginPeriodDate').val() !== undefined ? new Date($('#beginPeriodDate').val()) : d + '/' + m + '/' + y;
    var endPeriodDateTimeObj = $('#endPeriodDate').val() !== undefined ? new Date($('#endPeriodDate').val()) : d + '/' + m + '/' + y;

    $(".cal-table").selectable({
        filter : "td",
        distance : 1,
        selected : function (event, ui) {
            selectedDays.push(ui.selected.id);
        },
        selecting : function (event, ui) {
            // get the index number of the selected td
            $(".ui-selecting", this).each(function () {
                selectingDays.push($(".cal-table td").index(this));
            });

        },

        stop : function (event, ui) {
            var currentDay = new Date(beginPeriodDateTimeObj);
            var startDay = new Date(currentDay);
            var endDay = new Date(currentDay);

            startDay.addDays(parseInt(_.first(selectedDays).split("_")[1]));
            endDay.addDays(parseInt(_.last(selectedDays).split("_")[1]));

            startDay = Date.parse(startDay).toString(CONSTANTS.TIME_FORMAT.DATE_FOR_OUTPUT);
            endDay = Date.parse(endDay).toString(CONSTANTS.TIME_FORMAT.DATE_FOR_OUTPUT);

            app.showTimeEntryDialog(startDay, endDay);

            // https://uisapp2.iu.edu/jira-prd/browse/TK-1593
            if ($("#selectedAssignment").is("input")) {
                app.fetchEarnCodeAndLoadFields();
            }

            selectedDays = [];
        }
    });

    if ($('#docEditable').val() == 'false') {
        $(".cal-table").selectable("destroy");
    }
});
