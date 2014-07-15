$(document).ready(function () { 
/** date picker */
    $("#startDate, #endDate").datepicker({
        changeMonth:true,
        changeYear:true,
        showOn:'button',
        showAnim:'fadeIn',
        buttonImage:'kr/static/images/cal.gif',
        buttonImageOnly:true,
        buttonText:'Choose a date',
        showButtonPanel:true,
        numberOfMonths:1,
        appendText:' (mm/dd/yyyy)',
        constrainInput:true
//				yearRange : '-1:+1'
    });

    $("#startDate, #endDate").change(function () {
        var flag;
        var dl = Date.parse(this.value);

        if (this.value.length > 0) {
            dl == null ? flag = false : this.value = dl.toString('MM/dd/yyyy');
        }
    });
    /** end of date picker */
    
    //edo-97
    $('#submit').click(function(){
    	//  var hasfile = document.getElementById('uploadFile').value;
    	if(!$("input[type=file]").val()) {
            alert('Please select a file to upload');
            return false;
         }
	
	});
    
    //edo-379
    $('#saveVoteRecord').click(function(){
		$('#saveVoteRecord').hide();
		$('#ajaxSubmit').show();
	
	});
    $('#submitDossier').click(function(){
		$('#submitDossier').hide();
		$('#ajaxSubmit').show();
	
	});
    $('#routeDossier').click(function(){
		$('#routeDossier').hide();
		$('#ajaxSubmit').show();
	
	});
    $('#submitReconsider').click(function(){
		$('#submitReconsider').hide();
		$('#ajaxSubmit').show();
	
	});
    $('#closeDossier').click(function(){
		$('#closeDossier').hide();
		$('#ajaxSubmit').show();
	
	});
    $('#reconsiderDossier').click(function(){
		$('#reconsiderDossier').hide();
		$('#ajaxSubmit').show();
	
	});
    $('#superUserReturn').click(function(){
		$('#superUserReturn').hide();
		$('#ajaxSubmit').show();
	
	});
    $('#supeUserApprove').click(function(){
		$('#supeUserApprove').hide();
		$('#ajaxSubmit').show();
	
	});
    $('#cancelDossier').click(function(){
		$('#cancelDossier').hide();
		$('#ajaxSubmit').show();
	
	});
   
});

/*
function isDate(txtDate)
{
  var currVal = txtDate;
  if(currVal == '')
    return true;
  
  //Declare Regex  
  var rxDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/; 
  var dtArray = currVal.match(rxDatePattern); // is format OK?

  if (dtArray == null)
	  alert("invalid date");
  		return false;
 
  //Checks for mm/dd/yyyy format.
  dtMonth = dtArray[1];
  dtDay= dtArray[3];
  dtYear = dtArray[5];

  if (dtMonth < 1 || dtMonth > 12)
      alert("invalid date");
  	
  else if (dtDay < 1 || dtDay> 31)
	  alert("invalid date");
  
  else if ((dtMonth==4 || dtMonth==6 || dtMonth==9 || dtMonth==11) && dtDay ==31)
	  alert("invalid date");
  
  else if (dtMonth == 2)
  {
     var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
     if (dtDay> 29 || (dtDay ==29 && !isleap))
    	  alert("invalid date");
     
  }
  return false;
}
*/
function isDate(txtDate)
{
var valid = true;

txtDate = txtDate.replace('/-/g', '');
//if (txtDate == null) valid = false;
var month = parseInt(txtDate.substring(0, 2),10);
var day   = parseInt(txtDate.substring(2, 4),10);
var year  = parseInt(txtDate.substring(4, 8),10);

if((month < 1) || (month > 12)) valid = false;
else if((day < 1) || (day > 31)) valid = false;
else if(((month == 4) || (month == 6) || (month == 9) || (month == 11)) && (day > 30)) valid = false;
else if((month == 2) && (((year % 400) == 0) || ((year % 4) == 0)) && ((year % 100) != 0) && (day > 29)) valid = false;
else if((month == 2) && ((year % 100) == 0) && (day > 29)) valid = false;

return valid;
}

function validateRange(startDate, endDate, emplId)
{
	
	if(startDate != null) {
		if(isDate(startDate) == false) {
			alert("Invalid date");
			return false;
		}
	
		
	}
	if(endDate != null) {
		if(isDate(endDate) == false) {
			alert("Invalid date");
			return false;
		}
		
	}
	var startDate1 = new Date(startDate);
	var endDate1 = new Date(endDate);
	var formName = "EdoAssignCandidateDelegateForm";
	var actionSave = "save";
	
	if(startDate != null && endDate !== null) {
	DaysDiff = Math.floor((startDate1.getTime() - endDate1.getTime())/(1000*60*60*24));
		if(DaysDiff > 0){
			   alert('End Date should be always greater than Start Date');
			  return false;
			
		}
		else {
				$('input[name=methodToCall]').val(actionSave);
				$('input[name=emplToAdd]').val(emplId);
				document.forms[formName].submit();
				return true;
		}
		
	}
	else {
		//submit the form
		
		  $('input[name=methodToCall]').val(actionSave);
		  $('input[name=emplToAdd]').val(emplId);
		document.forms[formName].submit();
		return true;
	}
	
	   return false;
	
}

function validateDateRange(startDate, endDate, emplId)
{
	
	if(startDate != null) {
		if(isDate(startDate) == false) {
			alert("Invalid date");
			return false;
		}
	
		
	}
	if(endDate != null) {
		if(isDate(endDate) == false) {
			alert("Invalid date");
			return false;
		}
		
	}
	var startDate1 = new Date(startDate);
	var endDate1 = new Date(endDate);
	var formName = "EdoAssignCandidateGuestForm";
	var actionSave = "save";
	
	if(startDate != null && endDate !== null) {
	DaysDiff = Math.floor((startDate1.getTime() - endDate1.getTime())/(1000*60*60*24));
		if(DaysDiff > 0){
			   alert('End Date should be always greater than Start Date');
			  return false;
			
		}
		else {
				$('input[name=methodToCall]').val(actionSave);
				$('input[name=emplToAdd]').val(emplId);
				document.forms[formName].submit();
				return true;
		}
		
	}
	else {
		//submit the form
		
		  $('input[name=methodToCall]').val(actionSave);
		  $('input[name=emplToAdd]').val(emplId);
		document.forms[formName].submit();
		return true;
	}
	
	   return false;
	
}