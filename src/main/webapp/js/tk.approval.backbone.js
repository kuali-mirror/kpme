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

        // Create a time block model
    EarnCodeSection = Backbone.Model;

    /**
     * ====================
     * Collections
     * ====================
     */

        // Create a time block collection that holds multiple time blocks. This is essentially a list of hashmaps.
    EarnCodeSectionCollection = Backbone.Collection.extend({
        model : EarnCodeSection,
        url : "TimeApprovalWS.do?methodToCall=getTimeSummary"
    });


    var EarnCodeSections = new EarnCodeSectionCollection;

    /**
     * ====================
     * Views
     * ====================
     */

    var TimeSummaryView = Backbone.View.extend({

        el : $("body"),

        template : _.template($('#hourDetail-template').html()),

        events : {
            "click span[id^=showDetailButton]" : "showTimeSummary"
        },

        initialize : function () {
            _.bindAll(this, "render");
        },

        render : function () {
            return this;
        },

        showTimeSummary : function (e) {
            var self = this;
            var docId = e.target.id.split("_")[1];

            /**
             * There is still some DOM manipulation that can't be avoid.
             * We want to append the time hour details right after each
             * person's row, but due to the limit of the displaytag where
             * every element has to be wrapped in the <display:column> and
             * will be later converted to html. There isn't a way that I could
             * find to just append a div after the last <display:column>
             * inside the displaytag.
             *
             * To solve this issue, the current solution is to append the div
             * through DOM manipulation.
             */

            // This is to grab a person's <tr> in the table
            var $parent = ($("#" + e.target.id).closest("tr"));

            // Grab the + / - icon
            var $element = $("#" + e.target.id);
            // Toggle the + / - icon
            if ($element.hasClass('ui-icon-plus')) {
                // This triggerS the ajax call to fetch the time summary rows.
                this.fetchTimeSummary(docId);

                // Here we loop through the colleciton and insert the content to the template
                EarnCodeSections.each(function (earnCodeSection, index) {
                    $parent.after(self.template({
                        // This is the time summary rows
                        "section" : earnCodeSection.toJSON(),
                        // This is to check if the time summary row is the last one in the collection.
                        // If so, it will then render the earn group section.
                        "isLast" : index == 0 ? true : false,
                        // This is to give each <tr> in the time summary section an identifier,
                        // so when the minus icon is clicked, it will remove the appened html.
                        "docId" : docId
                    }));
                });

                // change the icon from - to +
                $element.removeClass('ui-icon-plus').addClass('ui-icon-minus');
            } else {
                // remove the hour details rows.
                $(".hourDetailRow_" + docId).remove();
                // change the icon from + to -
                $element.removeClass('ui-icon-minus').addClass('ui-icon-plus');
            }
        },
        /**
         * This method will make an ajax call to fetch the time summary row based on the doc id.
         * @param documentId
         */
        fetchTimeSummary : function (documentId) {
            EarnCodeSections.fetch({
                async : false,
                data : {
                    documentId : documentId
                }
            });
        }
    });

    // Initialize the view. This is the kick-off point.
    var app = new TimeSummaryView;

});