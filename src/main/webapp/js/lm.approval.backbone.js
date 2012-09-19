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
    // section for each accrual category
    AccrualCategorySection = Backbone.Model;

    /**
     * ====================
     * Collections
     * ====================
     */
    // For leave approval table plus button details
    AccrualCategorySectionCollection = Backbone.Collection.extend({
        model : AccrualCategorySection,
        url : "LeaveApprovalWS.do?methodToCall=getLeaveSummary"
    });
    var AccrualCategorySections = new AccrualCategorySectionCollection;
    

    /**
     * ====================
     * Views
     * ====================
     */
    
    // view for Leave Calendar Summary. 
    var LeaveSummaryView = Backbone.View.extend({
        el : $("body"),
        
        template : _.template($('#leaveDetail-template').html()),
        
        events : {
            "click span[id^=showLeaveDetailButton]" : "showLeaveSummary"
        },
        
        initialize : function () {
            _.bindAll(this, "render");
        },
        
        render : function () {
            return this;
        },

        showLeaveSummary : function (e) {
            var self = this;
            var docId = e.target.id.split("_")[1];
            // This is to grab a person's <tr> in the table
            var $parent = ($("#" + e.target.id).closest("tr"));

            // Grab the + / - icon
            var $element = $("#" + e.target.id);
            // Toggle the + / - icon
            if ($element.hasClass('ui-icon-plus')) {
                // This triggerS the ajax call to fetch the leave summary rows.
                this.fetchLeaveSummary(docId);

                // Here we loop through the colleciton and insert the content to the template
                AccrualCategorySections.each(function (accrualCetegorySection, index) {
                    $parent.after(self.template({
                        // This is the leave summary rows
                        "section" : accrualCetegorySection.toJSON(),
                        // This is to give each <tr> in the leave summary section an identifier,
                        // so when the minus icon is clicked, it will remove the appened html.
                        "docId" : docId
                    }));
                });

                // change the icon from - to +
                $element.removeClass('ui-icon-plus').addClass('ui-icon-minus');
            } else {
                // remove the leave details rows.
                $(".leaveDetailRow_" + docId).remove();
                // change the icon from + to -
                $element.removeClass('ui-icon-minus').addClass('ui-icon-plus');
            }
        },
        /**
         * This method will make an ajax call to fetch the leave summary row based on the doc id.
         * @param documentId
         */
        fetchLeaveSummary : function (documentId) {
            AccrualCategorySections.fetch({
                async : false,
                data : {
                    documentId : documentId
                }
            });
        }
    });
    // Initialize the view.
    var leaveView = new LeaveSummaryView;
        
});