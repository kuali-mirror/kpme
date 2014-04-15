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
//Override the function to turn growl off
function showGrowl(message, title, theme) {
	return;
}

jQuery(document).ready(function () {
	jQuery("[title='Search Field']").attr("tabindex","-1");
	jQuery("[name^='performLookup']").attr("tabindex","-1");
	
	jQuery(document).keypress(function(event) {
		/* use IE naming first then firefox. */
	    var elemType = event.srcElement ? event.srcElement.type : event.target.type;
	    if (elemType != null && (elemType.toLowerCase() == 'textarea' || elemType.toLowerCase() == 'submit')) {
	      // KULEDOCS-1728: textareas need to have the return key enabled
	      return true;
	    }
		var initiator = event.srcElement ? event.srcElement.name : event.target.name;
		var key = event.keyCode;
		/* initiator is undefined check is to prevent return from doing anything if not in a form field since the initiator is undefined */
		/* 13 is return key code */
		/* length &gt; 0 check is to allow user to hit return on links */
		if ( key == 13 ) {
			if( initiator == undefined || ( initiator.length > 0) ) {
			  // disallow enter key from fields that dont match prefix.
			  return false;
			}
		}
	});
});