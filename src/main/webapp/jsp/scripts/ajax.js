
var fieldToUpdate = new Array();

//Parameters:
//action: action to be executed to retrieve data
//focusField: field that triggers this javascript function
//queryString: Parameters and its values that will be passed along in the URL's action
//fieldsToUpdateList: List of comma separated fields that will be automatically updated.
//                    It's assumed they're in the same nestingPath as the focusField.
function makeAjaxRequest(action, focusField, queryString, fieldsToUpdateList) {
  http_request = false;
  var fullPath = focusField.name.substring(0,focusField.name.lastIndexOf('.'));
  fieldToUpdate = fieldsToUpdateList.split(',');
  for (i=0; i < fieldToUpdate.length; i++) {
     fieldToUpdate[i] = fullPath + "." + fieldToUpdate[i];
  }
  
   if (window.XMLHttpRequest) { // Mozilla, Safari, ...
        http_request = new XMLHttpRequest();
    } else if (window.ActiveXObject) { // IE
        http_request = new ActiveXObject("Microsoft.XMLHTTP");
    }

    http_request.open('POST', action, true);
    http_request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    http_request.onreadystatechange = processTheChange;
    http_request.send(queryString+document.forms[0][focusField.name].value);
}

function processTheChange() {
	if (http_request.readyState == 4) {
        updatePage(http_request.responseText);
    }
}

function updatePage(responseText){
    var result = new Array();
    result = responseText.split("||");
    result[0] = result[0].replace(/^\s*|\s*$/,""); 
    if (result[0] == "success") {
       for (i=0; i < fieldToUpdate.length; i++) {
         result[i+1]=escape(result[i+1]);
         result[i+1]=result[i+1].replace(/%0D%0A/g,"") //removes CR from networkId (windows)
         result[i+1]=result[i+1].replace(/%0A/g,"") //removes CR from networkId (unix)
         result[i+1]=unescape(result[i+1]);
         document.getElementById(fieldToUpdate[i]).innerHTML = result[i+1];
         eval('document.forms[0]["'+fieldToUpdate[i]+'"].value="'+result[i+1]+'"');
       }
    } else {
       for (i=0; i < fieldToUpdate.length; i++) {
        document.getElementById(fieldToUpdate[i]).innerHTML = "";
       }
    }
    try { resetImage(); }
    catch(exc) {}
}

