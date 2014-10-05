package org.kuali.kpme.edo.base.web;

import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.kpme.edo.util.EdoUser;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.kns.web.struts.form.KualiForm;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lfox
 * Date: 8/27/12
 * Time: 8:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoForm extends KualiForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String methodToCall;
	private String principalId;
	 //edo-134
	private boolean useCandidateScreen = false;
	private boolean useReviewerScreen = false;
	private boolean useGenAdminScreen = false;
    private Map<String, String> aoe = new HashMap<String, String>();
    private boolean useHelpScreen = false;
    private boolean useAssignDelegateFunc = false;
    private boolean useAssignGuestFunc = false;
    private boolean useSubmitDossierButton = false;
    private boolean useEditDossierFunc = false;
    private boolean hasCandidateRole = false;
    private boolean hasChairRole = false;
    private boolean hasUploadReviewLetter = false;
    private boolean hasUploadExternalLetterByDept = false;
    private boolean hasViewVoteRecordCurrentDossier = false;
    private boolean hasViewReviewLetterCurrentDossier = false;
    private boolean canUploadReconsiderItems = false;
    private boolean canManageGroups = false;
    private String tabId;
    private boolean hasSuperUserRole = false;
    public String getMethodToCall() {
		return methodToCall;
	}

	public void setMethodToCall(String methodToCall) {
		this.methodToCall = methodToCall;
	}

    public boolean isHasUploadExternalLetterByDept() {
        return hasUploadExternalLetterByDept;
    }

    public void setHasUploadExternalLetterByDept(boolean hasUploadExternalLetterByDept) {
        this.hasUploadExternalLetterByDept = hasUploadExternalLetterByDept;
    }

    public boolean isHasViewReviewLetterCurrentDossier() {
        return hasViewReviewLetterCurrentDossier;
    }

    public void setHasViewReviewLetterCurrentDossier(boolean hasViewReviewLetterCurrentDossier) {
        this.hasViewReviewLetterCurrentDossier = hasViewReviewLetterCurrentDossier;
    }

    public boolean isHasUploadReviewLetter() {
        return hasUploadReviewLetter;
    }

    public void setHasUploadReviewLetter(boolean hasUploadReviewLetter) {
        this.hasUploadReviewLetter = hasUploadReviewLetter;
    }

    public boolean isHasCandidateRole() {
        return hasCandidateRole;
    }

    public void setHasCandidateRole(boolean hasCandidateRole) {
        this.hasCandidateRole = hasCandidateRole;
    }

    public boolean isHasViewVoteRecordCurrentDossier() {
        return hasViewVoteRecordCurrentDossier;
    }

    public void setHasViewVoteRecordCurrentDossier(boolean hasViewVoteRecordCurrentDossier) {
        this.hasViewVoteRecordCurrentDossier = hasViewVoteRecordCurrentDossier;
    }

    public boolean isHasChairRole() {
        return hasChairRole;
    }

    public void setHasChairRole(boolean hasChairRole) {
        this.hasChairRole = hasChairRole;
    }

    public String getApplicationUrl (){
		return ConfigContext.getCurrentContextConfig().getProperty("application.url");
	}

	public String getLoggedInUserNetworkId() {
		return EdoContext.getUser().getNetworkId();
	}

	public String getLoggedInUserEmplid() {
		return EdoContext.getUser().getEmplId();
	}

	public String getLoggedInUserName() {
		return EdoContext.getUser().getName();
	}

	public EdoUser getUser(){
		return EdoContext.getUser();
	}
	@Override
	public boolean shouldMethodToCallParameterBeUsed(
			String methodToCallParameterName,
			String methodToCallParameterValue, HttpServletRequest request) {
		return true;
	}

	@Override
	public boolean shouldPropertyBePopulatedInForm(String requestParameterName,
			HttpServletRequest request) {
		return true;
	}

    public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	//edo-134
	public boolean isUseCandidateScreen() {
		return useCandidateScreen;
	}

	public void setUseCandidateScreen(boolean useCandidateScreen) {
		this.useCandidateScreen = useCandidateScreen;
	}

	public boolean isUseReviewerScreen() {
		return useReviewerScreen;
	}

	public void setUseReviewerScreen(boolean useReviewerScreen) {
		this.useReviewerScreen = useReviewerScreen;
	}

	public boolean isUseGenAdminScreen() {
		return useGenAdminScreen;
	}

	public void setUseGenAdminScreen(boolean useGenAdminScreen) {
		this.useGenAdminScreen = useGenAdminScreen;
	}

    public Map<String, String> getAoe() {
        return EdoConstants.AREA_OF_EXCELLENCE;
    }

    public void setAoe() {
        this.aoe = EdoConstants.AREA_OF_EXCELLENCE;
    }


    public boolean isUseHelpScreen() {
        return useHelpScreen;
    }

    public void setUseHelpScreen(boolean useHelpScreen) {
        this.useHelpScreen = useHelpScreen;
    }

    public boolean isUseAssignDelegateFunc() {
        return useAssignDelegateFunc;
    }

    public void setUseAssignDelegateFunc(boolean useAssignDelegateFunc) {
        this.useAssignDelegateFunc = useAssignDelegateFunc;
    }

    public boolean isUseAssignGuestFunc() {
        return useAssignGuestFunc;
    }

    public void setUseAssignGuestFunc(boolean useAssignGuestFunc) {
        this.useAssignGuestFunc = useAssignGuestFunc;
    }

    public boolean isUseSubmitDossierButton() {
        return useSubmitDossierButton;
    }

    public void setUseSubmitDossierButton(boolean useSubmitDossierButton) {
        this.useSubmitDossierButton = useSubmitDossierButton;
    }

    public boolean isUseEditDossierFunc() {
        return useEditDossierFunc;
    }

    public void setUseEditDossierFunc(boolean useEditDossierFunc) {
        this.useEditDossierFunc = useEditDossierFunc;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

	public boolean isCanUploadReconsiderItems() {
		return canUploadReconsiderItems;
	}

	public void setCanUploadReconsiderItems(boolean canUploadReconsiderItems) {
		this.canUploadReconsiderItems = canUploadReconsiderItems;
	}

    public boolean isCanManageGroups() {
        return canManageGroups;
    }

    public void setCanManageGroups(boolean canManageGroups) {
        this.canManageGroups = canManageGroups;
    }

	public boolean isHasSuperUserRole() {
		return hasSuperUserRole;
	}

	public void setHasSuperUserRole(boolean hasSuperUserRole) {
		this.hasSuperUserRole = hasSuperUserRole;
	}
    
}
