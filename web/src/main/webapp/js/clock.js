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