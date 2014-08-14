/**
 * Created by mlemons on 8/14/14.
 */
$.ajaxSetup({ cache: false });

$(function () {

    var ClockView = Backbone.View.extend({

        // Set the element that our dialog form wants to bind to. This setting is necessary.
        el : $("body"),

        // Events are in the jQuery format.
        // The first part is an event and the second part is a jQuery selector.
        // Check out this page for more information about the jQuery selectors : http://api.jquery.com/category/selectors/
        events : {
            "click input[id$=-proxy]" : "confirmProxyClockAction",
            "click input[id$=-actualuser]" : "submitForm"
        },

        submitForm : function (e) {
            $('form[name="ClockActionForm"]').submit();
        },

        confirmProxyClockAction : function (e) {
            $('#confirm-proxy-clock-action-dialog').dialog({
                autoOpen: true,
                height: 'auto',
                width: 'auto',
                modal: true,
                buttons: {
                    "Proceed": function () {
                        $('form[name="ClockActionForm"]').submit();
                    },
                    Cancel: function () {
                        $(this).dialog("close");
                    }
                },
            });
        },

        initialize : function () {
            this.delegateEvents(this.events);
        },


    });

    var app = new ClockView;


});