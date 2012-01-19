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

    // Create a time block collection that holds multiple time blocks. This is essentially a hash map
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

    // Create a collection of earn codes
    EarnCodeCollection = Backbone.Collection.extend({
        model : EarnCode,
        url : "TimeDetailWS.do?methodToCall=getEarnCodeJson"
    });

    var EarnCodes = new EarnCodeCollection;

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
            "click img[id*=delete]" : "deleteTimeBlock",
            "click td[id*=create]" : "showTimeEntryDialog",
            "change #startTime, #endTime" : "formatTime",
            "change #selectedAssignment" : "fetchEarnCode",
            "change #selectedEarnCode" : "showFieldByEarnCodeType"
        },

        initialize : function () {
            // This step binds the functions to the view so you can call the methods like : this.addOneEarnCode
            _.bindAll(this, "addAllEarnCodes", "render");

            // Bind the events onto the earncode collection, so these events will be triggered
            // when an earncode collection is created.
            EarnCodes.bind('add', this.addAllEarnCodes);
            EarnCodes.bind('reset', this.addAllEarnCodes);
        },

        render : function () {
            // If there is anything you want to render when the view is initiated, place them here.
            // A good convention is to return this at the end of render to enable chained calls.
            return this;
        },

        formatTime : function (e) {

            var id = e.target.id;
            var value = e.target.value;
            // Use Datejs to parse the value
            var dateTime = Date.parse(value);
            if (_.isNull(dateTime)) {
                // Date.js returns null if it couldn't understand the format from user's input.
                // If that's the case, clear the values on the form and make the border red.
                $("#" + id).addClass("block-error").val("");
                $("#" + id + "HourMinute").val("");
            }
            // This magic line first finds the element by the id.
            // Uses Datejs (a 3rd party js lib) to parse user's input and update the value by the specifed format.
            // See the list of the formats in tk.js.
            $("#" + id).val(dateTime.toString('hh : mm tt'));

            // set the value to the military format on a different field for further timeblock actions.
            $("#" + id + "HourMinute").val(dateTime.toString('H:mm'));
        },


        showTimeEntryDialog : function () {

            $("#dialog-form").dialog({
                autoOpen : true,
                height : 'auto',
                width : '450',
                modal : true,
                beforeClose : function () {
                    // TODO: create a method to reset the values instead of spreading this type of code all over the place.
                    _(new TimeBlock).fillInForm();
                },
                buttons : {
                    "Add" : function () {
                   	 var params = {};
                     var endTimeValue = $('#endTimeHourMinute').val().toUpperCase();
                     var endDateValue = $('#endDate').val();

                     // if the end time is 12:00 am, move the end date to the next day

                     if (endTimeValue == "0:0") {
                         var dateRangeField = endDateValue.split("/");
                         if (dateRangeField[1].charAt(0) == '0') {
                             dateRangeField[1] = dateRangeField[1].replace('0', '');
                         }

                         var dateString = parseInt(dateRangeField[1]) + 1;
                         endDateValue = dateRangeField[0] + "/" + dateString + "/" + dateRangeField[2];
                     }

                     $('#startDate').val($('#startDate').val());
                     $('#endDate').val(endDateValue);
                     $('#startTime').val($('#startTimeHourMinute').val());
                     $('#endTime').val(endTimeValue);
                     $('#hours').val($('#hoursField').val());
                     $('#amount').val($('#amountField').val());
                     

                     $('#acrossDays').val($('#acrossDaysField').is(':checked') ? 'y' : 'n');
                     $('#methodToCall').val(CONSTANTS.ACTIONS.ADD_TIME_BLOCK);
                     $('#time-detail').submit();
                     $(this).dialog("close");
                        
                        // TODO: need to port the existing logics from tk.calendar.js
                    },
                    Cancel : function () {
                        $(this).dialog("close");

                    }
                }
            });
        },

        showTimeBlock : function (e) {

            var key = _(e.target.id).parseEventKey();
            this.showTimeEntryDialog();
            // Retrieve the selected timeblock
            var timeBlock = timeBlockCollection.get(key.id);
            // Deferred is a jQuery method which makes sure things happen in the order you want.
            // For more informaiton : http://api.jquery.com/category/deferred-object/
            // Here we want to fire the ajax call first to grab the earn codes.
            // After that is done, we fill out the form and make the entry field show / hide based on the earn code type.
            var dfd = $.Deferred();
            // Fill in the values. See the note above regarding why we didn't use a template
            dfd.done(_(timeBlock).fillInForm())
               .done(this.fetchEarnCode(timeBlock))
               .done(this.showFieldByEarnCodeType());


        },

        deleteTimeBlock : function (e) {

            var key = _(e.target.id).parseEventKey();
            var timeBlock = timeBlockCollection.get(key.id);

            if (confirm('You are about to delete a time block. Click OK to confirm the delete.')) {
                window.location = "TimeDetail.do?methodToCall=deleteTimeBlock&documentId=" + timeBlock.get("documentId") + "&tkTimeBlockId=" + key.id;
                e.preventDefault();
                e.stopPropagation();
            }
        },

        fetchEarnCode : function () {
            // Fetch earn codes based on the selected assignment
            // The fetch function is provided by backbone.js which also supports jQuery.ajax options.
            // See here for more information: http://documentcloud.github.com/backbone/#Collection-fetch
            this.$("#selectedEarnCode").html("");
            EarnCodes.fetch({
                // Make the ajax call not async to be able to mark the earn code selected
                async : false,
                data : {
                    selectedAssignment : $("#selectedAssignment").val()
                }
            });

        },


        addAllEarnCodes : function () {
            var view = new EarnCodeView({collection : EarnCodes});
            // Append the earn code to <select>
            this.$("#earnCode-section").append(view.render().el);

        },

        showFieldByEarnCodeType : function () {
            var earnCodeType = _.getEarnCodeType(EarnCodes.toJSON(), $("#selectedEarnCode option:selected").val());
            var fields = [".clockInSection", ".clockOutSection", ".hourSection", ".amountSection"];

            // There might be a better way doing this, but we can revisit this later.
            // Currently, the fields variable contains a list of the entry field classes.
            // The Underscore.js _.without function returns an array except the ones you speficied.
            if (earnCodeType == CONSTANTS.EARNCODE_TYPE.HOUR) {
                $(_.without(fields, ".hourSection").join(",")).hide();
                $(fields[2]).show();
            } else if (earnCodeType == CONSTANTS.EARNCODE_TYPE.AMOUNT) {
                $(_.without(fields, ".amountSection").join(",")).hide();
                $(fields[3]).show();
            } else {
                $(_.without(fields, ".clockInSection", ".clockOutSection").join(",")).hide();
                $(fields[0] + "," + fields[1]).show();
            }
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
            this.collection.each(function (earnCode) {
                $(self.el).append(self.template(earnCode.toJSON()));
            });

            return this;
        }
    });

    // Initialize the view
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
         * @param string
         */
        parseEventKey : function (string) {
            return {
                action : string.split("_")[0],
                id : string.split("_")[1]
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
            $("#selectedEarnCode option[value='" + timeBlock.get("earnCode") + "']").attr("selected", "selected");
            $('#tkTimeBlockId').val(timeBlock.get("tkTimeBlockId"));
            $('#hours').val(timeBlock.get("hours"));
            $('#amount').val(timeBlock.get("amount"));
        },

        /**
         * This method takes an earn code json, find the matched earn code, and returns the earn code type.
         * @param earnCodeJson
         * @param earnCode
         */
        getEarnCodeType : function (earnCodeJson, earnCode) {
            var type = "";

            $.each(earnCodeJson, function(i){
                if (earnCodeJson[i]["earnCode"] == earnCode) {
                    type = earnCodeJson[i]["type"];
                }
            });

            return type;
        },
        /**
         * Provides a helper method to be able to change the button name on the time entry form.
         * @param string
         */
        updateDialogButtonName : function (string) {

        }

    });
});
