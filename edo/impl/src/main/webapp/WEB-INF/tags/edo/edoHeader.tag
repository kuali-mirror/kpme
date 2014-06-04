<%@ include file="/WEB-INF/jsp/EdoTldHeader.jsp"%>
<edo:headerInclude/>
<input type="hidden" id="tabId" value="${tabId}" />


<div id="tabs" class="ui-tabs ui-widget ui-corner-all">

    <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-corner-all">
        <div class="headerarea" id="headerarea">
            <div>
            <span class="left">
                <a href="EdoIndex.do?">
                <img src="images/iu_crimson.gif" alt="Logo" width="171" height="44" style="vertical-align: top;">
                <div style="font-size: 150%; font-weight: bold; margin-left: 150px; color: white;">
                    ${appTitle}<br>
                    <span style="font-size: small; font-style: italic;">${appSubTitle}</span>
                </div>
                </a>

                <jsp:doBody />

            </span>
            </div>
            <edo:edoIdentityBar/>
        </div>
       
        <edo:edoTabs />
    </ul>
</div>

