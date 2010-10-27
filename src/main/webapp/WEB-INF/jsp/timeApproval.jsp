<%@ include file="/WEB-INF/jsp/TkTldHeader.jsp"%>
<c:set var="Form" value="${TimeApprovalActionForm}" scope="request"/>

<tk:tkHeader tabId="approvals">
	<html:hidden property="methodToCall" value=""/>

	<div class="approvals">

		<table id="approvals-table">
			<tr>
				<td colspan="18" align="center" style="border:none;">
					<span style="font-weight: bold; font-size: 1.5em;">Aaron Neal</span>
				</td>
				<!-- <span style="clear:both; float: right;">Pay Period:  -->
			    <td colspan="2" style="border: none;">
			        Pay periods:
					<select>
						<option>11/1 - 11/14</option>
						<option>11/15 - 11/30</option>
					</select>
			    </td>
					<!-- </span>  -->
			</tr>
			<tr class="ui-widget-header" style="border:none; white-space: nowrap;">
				<td style="border: none; background: #EEEEEE;"></td>
				<td>11/1</td>
				<td>11/2</td>
				<td>11/3</td>
				<td>11/4</td>
				<td>11/5</td>
				<td>11/6</td>
				<td>11/7</td>
				<td>Week 1</td>
				<td>11/8</td>
				<td>11/9</td>
				<td>11/10</td>
				<td>11/11</td>
				<td>11/12</td>
				<td>11/13</td>
				<td>11/14</td>
				<td>Week 2</td>
				<td><bean:message key="approval.totalHours"/></td>
				<td><bean:message key="approval.status"/></td>
				<td align="center"><bean:message key="approval.selectAllNone"/><br/><input type="checkbox" id="selectAll"/></td>
			</tr>

			<tr>
				<td style="border: none;"><button class="expand"></button><a href="TimeApproval.do?backdoorId=earl">Earl Hines</a> <br/> (assignment #1)</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td>VAC: 4</td>
				<td></td>
				<td></td>
				<td>16</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td></td>
				<td>SCK: 4</td>
				<td>RGN: 4</td>
				<td>RGN: 4</td>
				<td></td>
				<td>16</td>
				<td>32</td>
				<td>Approved</td>
				<td align="center"><!-- <input type="checkbox" name="selectedEmpl" />  --></td>
			</tr>
			
			<tr>
                <td style="border: none;">RGN</td>
                <td>4</td>
                <td>4</td>
                <td>4</td>
                <td>4</td>
                <td></td>
                <td></td>
                <td></td>
                <td>16</td>
                <td>4</td>
                <td>4</td>
                <td></td>
                <td></td>
                <td>4</td>
                <td>4</td>
                <td></td>
                <td>16</td>
                <td>32</td>
                <td style="border: none;"></td>
                <td style="border: none;"></td>
            </tr>
            
            <tr>
                <td style="border: none;">VAC</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>4</td>
                <td></td>
                <td></td>
                <td>4</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>0</td>
                <td>4</td>
                <td style="border: none;"></td>
                <td style="border: none;"></td>
            </tr>

            <tr>
                <td style="border: none;">SCK</td>
                <td></td>
                <td></td>
                <td></td>
                <td4</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td>0</td>
                <td></td>
                <td></td>
                <td></td>
                <td>4</td>
                <td></td>
                <td></td>
                <td></td>
                <td>4</td>
                <td>4</td>
                <td style="border: none;"></td>
                <td style="border: none;"></td>
            </tr>

			<tr>
				<td style="border: none;"><a href="TimeApproval.do?backdoorId=eric">Eric Dolphy</a> <br/> (assignment #2)</td>
				<td>RGN: 3</td>
				<td>RGN: 4</td>
				<td>RGN: 2</td>
				<td>VAC: 4</td>
				<td>VAC: 4</td>
				<td></td>
				<td></td>
				<td>9</td>
				<td>RGN: 4</td>
				<td>SCK: 4</td>
				<td></td>
				<td></td>
				<td>RGN: 4</td>
				<td></td>
				<td></td>
				<td>8</td>
				<td>17</td>
			    <td></td>
                <td align="center"><input type="checkbox" name="selectedEmpl" /></td>
			</tr>
			
			<tr>
				<td style="border: none;">RGN</td>
				<td>3</td>
				<td>4</td>
				<td>2</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>9</td>
				<td>4</td>
				<td></td>
				<td></td>
				<td></td>
				<td>4</td>
				<td></td>
				<td></td>
				<td>8</td>
				<td>17</td>
                <td style="border: none;"></td>
                <td style="border: none;"></td>
			</tr>
			
			<tr>
				<td style="border: none;">VAC</td>
				<td></td>
				<td></td>
				<td></td>
				<td>4</td>
				<td>4</td>
				<td></td>
				<td></td>
				<td>8</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>0</td>
				<td>8</td>
                <td style="border: none;"></td>
                <td style="border: none;"></td>
			</tr>

			<tr>
				<td style="border: none;">SCK</td>
				<td></td>
				<td></td>
				<td></td>
				<td4</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>0</td>
				<td></td>
				<td>4</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>4</td>
				<td>4</td>
                <td style="border: none;"></td>
                <td style="border: none;"></td>
			</tr>

			<tr>
				<td colspan="21" align="center" style="border:none;">
					<input type="button" class="button" value="Approve" name="Approve">
					<input type="button" class="button" value="Disapprove" name="Disapprove">
				</td>
			</tr>
		</table>
	</div>

</tk:tkHeader>