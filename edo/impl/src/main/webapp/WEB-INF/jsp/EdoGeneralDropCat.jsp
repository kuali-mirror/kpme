<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<c:set var="Form" value="${EdoGeneralDropCatForm}"
	scope="request" />

<edo:edoPageLayout>
	<edo:edoHeader></edo:edoHeader>

	<div style="width: 100%;">

		<edo:edoTreeNav />
		<div class="content">
			<h2>Category's Instructions</h3>
			
			<h3>
			
			<p>General category has five sub categories. To expand it please click the arrow next to it.</p>
			
			<p>Research/Creative Activity category has eight sub categories. To expand it please click the arrow next to it.</p>
			
			<p>Teaching category has eighteen sub categories. To expand it please click the arrow next to it.</p>
			
			<p>Service/Engagement category has three sub categories. To expand it please click the arrow next to it.</p>
			
			</h3>
		</div>
	</div>
</edo:edoPageLayout>
