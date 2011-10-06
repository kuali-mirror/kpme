/**
 * Magic time parsing, based on Simon Willison's Magic date parser
 * @see http://simon.incutio.com/archive/2003/10/06/betterDateInput
 * @author Stoyan Stefanov &lt;stoyan@phpied.com&gt;
 */


/**
 * This is the place to customize the result format,
 * once the date is figured out
 *
 * @param Date d A date object
 * @return string A time string in the preferred format
 */
function getReadable(d) {
    return padAZero(d.getHours())
            + ':'
            + padAZero(d.getMinutes())
//           + ':'
//           + padAZero(d.getSeconds())
            ;
}
/**
 * Helper function to pad a leading zero to an integer
 * if the integer consists of one number only.
 * This function s not related to the algo, it's for
 * getReadable()'s purposes only.
 *
 * @param int s An integer value
 * @return string The input padded with a zero if it's one number int
 * @see getReadable()
 */
function padAZero(s) {
    s = s.toString();
    if (s.length == 1) {
        return '0' + s;
    } else {
        return s;
    }
}

/**
 * Helper function to format the time
 *
 */

function formatDate(d) {

    var a_p = "";
    var h = d.getHours();

    if (h < 12) {
        a_p = "AM";
    } else {
        a_p = "PM";
    }
    if (h == 0) {
        h = 12;
    }
    if (h > 12) {
        h = h - 12;
    }

    var m = d.getMinutes();

    return padAZero(h) + ":" + padAZero(m) + " " + a_p;
}


/**
 * Array of objects, each has:
 * <ul><li>'re' - a regular expression</li>
 * <li>'handler' - a function for creating a date from something
 *     that matches the regular expression</li>
 * <li>'example' - an array of examples that show matching examples</li>
 * Handlers may throw errors if string is unparseable.
 * Examples are used for automated testing, so they should be updated
 *   once a regexp is added/modified.
 */
var timeParsePatterns = [
    // Now
    {   re: /^now/i,
        example: new Array('now'),
        handler: function() {
            return new Date();
        }
    },
    // 12a
    {
        re: /12(?:a| a)/i,
        example: new Array('12:00a'),
        handler: function(bits) {
            var d = new Date();
            d.setHours(0);
            d.setMinutes(0);
            return d;
        }

    },
    // 12:00am
    {
        re: /12:\d{1,2}(?:[a|am]| [a|am])/i,
        example: new Array('12:00a'),
        handler: function(bits) {
            var d = new Date();
            d.setHours(0);
            d.setMinutes(0);
            return d;
        }

    },
    //  hmm p/pm
    {  	re: /^([0-9]{3})(p$|pm$| p$| pm$)/i,
        example: new Array('905p', '905 p', '905p', '905 pm'),
        handler: function(bits) {
            var d = new Date();
            var h = parseInt(bits[1].substring(0, 1));
            var m = parseInt(bits[1].substring(1, 3), 10);
            if (isNaN(m)) {
                m = 0;
            }
            if (h < 12) {
                h += 12;
            }
            
            d.setHours(h);
            //d.setHours(parseInt(h, 10));
            d.setMinutes(parseInt(m, 10));

            return d;
        }
    },
    // p.m.
    {   re: /(\d{1,2}):(\d{1,2}):(\d{1,2})(?:p| p)/i,
        example: new Array('9:55:00 pm', '12:55:00 p.m.', '9:55:00 p', '11:5:10pm', '9:5:1p'),
        handler: function(bits) {
            var d = new Date();
            var h = parseInt(bits[1], 10);
            if (h < 12) {
                h += 12;
            }
            d.setHours(h);
            d.setMinutes(parseInt(bits[2], 10));
//            d.setSeconds(parseInt(bits[3], 10));
            return d;
        }
    },
    // p.m., no seconds
    {   re: /(\d{1,2}):(\d{1,2})(?:p| p)/i,
        example: new Array('9:55 pm', '12:55 p.m.', '9:55 p', '11:5pm', '9:5p'),
        handler: function(bits) {
            var d = new Date();
            var h = parseInt(bits[1], 10);
            if (h < 12) {
                h += 12;
            }
            d.setHours(h);
            d.setMinutes(parseInt(bits[2], 10));
//            d.setSeconds(0);
            return d;
        }
    },
    // p.m., hour only
    {   re: /(\d{1,2})(?:p| p)/i,
        example: new Array('9 pm', '12 p.m.', '9 p', '11pm', '9p'),
        handler: function(bits) {
            var d = new Date();
            var h = parseInt(bits[1], 10);
            if (h < 12) {
                h += 12;
            }
            d.setHours(h);
            d.setMinutes(0);
//            d.setSeconds(0);
            return d;
        }
    },
    // hh:mm:ss
    {   re: /(\d{1,2}):(\d{1,2}):(\d{1,2})/,
        example: new Array('9:55:00', '19:55:00', '19:5:10', '9:5:1', '9:55:00 a.m.', '11:55:00a'),
        handler: function(bits) {
            var d = new Date();
            d.setHours(parseInt(bits[1], 10));
            d.setMinutes(parseInt(bits[2], 10));
//            d.setSeconds(parseInt(bits[3], 10));
            return d;
        }
    },
    // hh:mm
    {   re: /(\d{1,2}):(\d{1,2})/,
        example: new Array('9:55', '19:55', '19:5', '9:55 a.m.', '11:55a'),
        handler: function(bits) {
            var d = new Date();
            d.setHours(parseInt(bits[1], 10));
            d.setMinutes(parseInt(bits[2], 10));
//            d.setSeconds(0);
            return d;
        }
    },
    // 9, hhmm
    {   re: /(^[0-9]{3}$)/,
        example: new Array('900', '905'),
        handler: function(bits) {
            var d = new Date();
            var h = bits[1].substring(0, 1);
            var m = parseInt(bits[1].substring(1, 3), 10);
            if (isNaN(m)) {
                m = 0;
            }
            d.setHours(parseInt(h, 10));
            d.setMinutes(parseInt(m, 10));

            return d;
        }
    },
    // 10, hhmm a/am
    {  	re: /^([0-9]{3})(a$|am$| a$| am$)/i,
        example: new Array('905a', '905 a', '905am', '905 am'),
        handler: function(bits) {
            var d = new Date();
            var h = bits[1].substring(0, 1);
            var m = parseInt(bits[1].substring(1, 3), 10);
            if (isNaN(m)) {
                m = 0;
            }
            d.setHours(parseInt(h, 10));
            d.setMinutes(parseInt(m, 10));

            return d;
        }
    },
    // 11, hhmmss
    {   re: /(\d{1,6})/,
    	example: new Array('9', '9a', '9am', '19', '1950', '195510', '0955'),
        handler: function(bits) {
            var d = new Date();
            var h = bits[1].substring(0, 2);
            var m = parseInt(bits[1].substring(2, 4), 10);
            var s = parseInt(bits[1].substring(4, 6), 10);
            if (isNaN(m)) {
                m = 0;
            }
            if (isNaN(s)) {
                s = 0;
            }
            d.setHours(parseInt(h, 10));
            d.setMinutes(parseInt(m, 10));
//            d.setSeconds(parseInt(s, 10));
            return d;
        }
    }
];

/**
 * Method that loops through all regexp's examples and lists them.
 * Optionally, the method can also run tests with the examples.
 *
 * @param boolean run_test TRUE is tests should be run on the examples, FALSE if only to show examples
 * @return object An XML 'ul' node
 */
function getExamples(run_tests) {
    var xml = document.createElement('ul');
    xml.setAttribute('id', 'time-parser-examples');
    for (var i = 0; i < timeParsePatterns.length; i++) {
        try {
            var example = timeParsePatterns[i].example;
            for (var j = 0; j < example.length; j++) {
                var list_item = document.createElement('li');
                if (!run_tests) {
                    var list_item_value = document.createTextNode(example[j]);
                } else {
                    var parsed = parseTimeString(example[j])
                    var result = getReadable(parsed) + ' -> ' + parsed.toTimeString();
                    var list_item_value = document.createTextNode(example[j] + ' -> ' + result)
                }
                list_item.appendChild(list_item_value)
                xml.appendChild(list_item);
            }
        } catch(e) {
        }
    }
    return xml;
}

/**
 * Parses a string to figure out the time it represents
 *
 * @param string s String to parse
 * @return Date a valid Date object
 * @throws Error
 */
function parseTimeString(s) {
    for (var i = 0; i < timeParsePatterns.length; i++) {
        var re = timeParsePatterns[i].re;
        var handler = timeParsePatterns[i].handler;
        var bits = re.exec(s);
        if (bits) {
            // this is to debug which regex it actually used
            //console.log(i);
            return handler(bits);
        }
    }
    throw new Error("Invalid time format");
}

function magicTime(input) {

    var messagespan = input.attr('id') + '-messages';

    // get the date field
    var dateRangeField = input.attr('id') == 'beginTimeField' ? $('#date-range-begin').val() : $('#date-range-end').val();

    // clear the error message
    $("#" + input.attr('id') + '-error').html("");
    try {
        var d = parseTimeString(input.val());

        // this came with the original source code which generates the military time format
        // input.value = getReadable(d);
        input.val(formatDate(d));
        input.className = '';
        try {
            // Human readable time
            el = $("#" + messagespan);
            dateRangeField = dateRangeField.split("/");

            d.setFullYear(dateRangeField[2], dateRangeField[0], dateRangeField[1]);
            d.setSeconds(0);
            d.setMilliseconds(0);
            el.val(/*d.getMonth() + "/" + d.getDate() + "/" + d.getFullYear() + " " + */d.getHours() + ":" + d.getMinutes());
            //el.value = d.getTime();
            el.addClass("normal");
        }
        catch (e) {
            // the message div is not in the document
            // alert('no message div');
        }
    }
    catch (e) {
        try {
            el = $("#" + input.attr('id') + '-error')

            el.addClass("error");
            var message = e.message;
            // Fix for IE6 bug
            if (message.indexOf('is null or not an object') > -1) {
                message = 'Invalid format';
            }
            el.html(message);
        } catch (e) {
            if ($("#" + input.id).val() != '') {
                el.html("Invalid format");
            }
        }

        return false;
    }
}
