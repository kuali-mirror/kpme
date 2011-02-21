<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<%@ attribute name="width" required="false"%>

<div id="note">
	<h2><a href="#">Note</a></h2>
	<div style="text-align: center;">
		<div><strong>Existing Note:</strong> Data entry dept. short staffed on Aprial 2, 12, and 14. Tammy helped with data entry.</div><br/>
		<textarea rows="5" cols="70" style="margin-left: auto; margin-right: auto;"></textarea><br/>
		<input type="button" class="button" value="Submit" name="submitNote"/>
		<input type="reset" class="button" value="Clear" name="clearNote"/>
	</div>
</div>