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
$(document).ready(function() {

    // log
	QUnit.log = function(result, message) {
		if (window.console && window.console.log) {
			window.console.log(result + ' :: ' + message);
		}
	}

    // ajax tests
	module("Calendar Ajax Unit Test");

    var ajaxTestBaseUrl = "http://localhost:8090/kpme-dev/TimeDetail.do?methodToCall=";

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