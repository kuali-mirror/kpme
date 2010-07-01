<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>

<tk:tkHeader tabId="approvals">
	<html:hidden property="methodToCall" value=""/>

	<div class="ui-widget ui-widget-content approvals">

		<table id="approvals-table">
			<tr>
				<td colspan="19" align="center" style="border:none;">
					<span style="font-weight: bold; font-size: 1.5em;">Aaron Neal</span>
				<span style="clear:both; float: right;">Pay Period:
					<select>
						<option>5/2 - 5/16</option>
						<option>5/6 - 5/20</option>
					</select>
					</span>
				</td>
			</tr>
			<tr class="ui-widget-header" style="border:none;">
				<td style="border: none; background: #EEEEEE;"></td>
				<td>5/2 Sun</td>
				<td>5/3 Mon</td>
				<td>5/4 Tue</td>
				<td>5/5 Wed</td>
				<td>5/6 Thu</td>
				<td>5/7 Fri</td>
				<td>5/8 Sat</td>
				<td>5/9 Sun</td>
				<td>5/10 Mon</td>
				<td>5/11 Tue</td>
				<td>5/12 Wed</td>
				<td>5/13 Thu</td>
				<td>5/14 Fri</td>
				<td>5/15 Sat</td>
				<td>5/16 Sun</td>
				<td><bean:message key="approval.totalHours"/></td>
				<td><bean:message key="approval.status"/></td>
				<td align="center"><bean:message key="approval.selectAllNone"/><br/><input type="checkbox" id="selectAll"/></td>
			</tr>

			<tr>
				<td style="border: none;">Kenneth Lee <br/> (assignment #1)</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td></td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td></td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>52</td>
				<td>A</td>
				<td align="center"><input type="checkbox" name="selectedEmpl" /></td>
			</tr>

			<tr>
				<td style="border: none;">Kenneth Lee <br/> (assignment #2)</td>
				<td>RGN: 3</td>
				<td>RGN: 4</td>
				<td>RGN: 2</td>
				<td>RGN: 4</td>
				<td>RGN: 1</td>
				<td>RGN: 1</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td></td>
				<td></td>
				<td></td>
				<td>RGN: 4</td>
				<td></td>
				<td>RGN: 4</td>
				<td>52</td>
				<td></td>
				<td align="center"><input type="checkbox" name="selectedEmpl" /></td>
			</tr>

			<tr>
				<td style="border: none;">Allen Fox <br/> (assignment #1)</td>
				<td>RGN: 3</td>
				<td>RGN: 4</td>
				<td>RGN: 5</td>
				<td>RGN: 4</td>
				<td>RGN: 2</td>
				<td></td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td></td>
				<td>RGN: 2</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>52</td>
				<td>P</td>
				<td align="center"><input type="checkbox" name="selectedEmpl" /></td>
			</tr>

			<tr>
				<td style="border: none;">Damion Junk <br/> (assignment #1)</td>
				<td>RGN: 7</td>
				<td>RGN: 4</td>
				<td>RGN: 6</td>
				<td>RGN: 4</td>
				<td></td>
				<td>RGN: 4</td>
				<td>RGN: 3</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td></td>
				<td>RGN: 5</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>52</td>
				<td>D</td>
				<td align="center"><input type="checkbox" name="selectedEmpl" /></td>
			</tr>
			<tr>
				<td colspan="19" align="center" style="border:none;">
					<input type="button" class="button" value="Approve" name="Approve">
					<input type="button" class="button" value="Disapprove" name="Disapprove">
				</td>
			</tr>
		</table>
	</div>

</tk:tkHeader>