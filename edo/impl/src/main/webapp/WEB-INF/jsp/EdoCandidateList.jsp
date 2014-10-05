<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>

<edo:edoPageLayout>

    <edo:edoHeader></edo:edoHeader>

    <div style="width: 100%;">

        <edo:edoTreeNav />
        <div class="content">
            <div style="font-weight: bold; font-size: 1.2em; padding: 10px; margin: 10px 0 0 3px; background-color: #FADDDE; color: #DE3F45;">
            As a reviewer, you may wish to save and/or print for 'offline' review materials a candidate submitted.<br/>
            However, you should not save, print, or share any external or evaluative letters or votes.
            </div>
        <h3>Candidate List</h3>
        <edo:jq_candidatetable_init addLink = "EdoCandidateAdd.do" selectLink="EdoCandidateSelect.do?nid=cklst_0_0" />
        <table id="candidate_list"></table>

        </div>
        <br style="clear: both;" />
    </div>
    <edo:edoFooter/>
</edo:edoPageLayout>
