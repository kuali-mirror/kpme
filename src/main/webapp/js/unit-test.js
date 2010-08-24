$(document).ready(function() {

    // log
	QUnit.log = function(result, message) {
		if (window.console && window.console.log) {
			window.console.log(result + ' :: ' + message);
		}
	}

    // ajax tests
	module("Calendar Ajax Unit Test");

    var ajaxTestBaseUrl = "http://localhost:8080/tk-dev/TimeDetail.do?methodToCall=";

    test("Add / Load time blocks", function() {
        expect(1);
        stop();
        addTimeBlocks(function(response){
            getTimeBlocks(function(response){
              var timeBlocks = response.length;
              equals(timeBlocks,2, "number of time blocks");
              start();
            });
        });

    });

    test("Delete time blocks", function() {
        expect(2);
        stop();
        getTimeBlocks(function(response){
            for(i in response) {
                stop();
                var timeBlockId = response[i].id;

                deleteTimeBlocks(timeBlockId, function(response){
                    ok(true, "time block has been deleted");
                    setTimeout(function() {
                        start();
                    },500);
                });
            }
        });
    });

    function deleteTimeBlocks(id, callback) {
        $.ajax({
            type: 'GET',
            url: ajaxTestBaseUrl + 'deleteTimeBlock&timeBlockId=' + id,
            complete: function(response) {
                callback(response);
            }
        });
    }

    function addTimeBlocks(callback) {
        $.ajax({
            type: 'GET',
            url: ajaxTestBaseUrl + 'addTimeBlock&beginDate=08%2F04%2F2010&endDate=08%2F05%2F2010&earnCode=RGN&assignment=1&beginTime=8%3A0%3A20&endTime=17%3A0%3A21&acrossDays=y',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            complete: function(response) {
                callback(response);
            }
        });
    }

    function getTimeBlocks(callback) {
        $.ajax({
            type: 'GET',
            url: ajaxTestBaseUrl + 'webService&start=1280635200&end=1284264000',
            dataType: 'json',
            data: '{}',
            contentType: 'application/json; charset=utf-8',
            success: function(response) {
                callback(response);
            }
        });
    }

});