package org.kuali.kpme.edo.group;

import java.math.BigDecimal;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/27/14
 * Time: 2:21 PM
 */
public class EdoRoleResponsibility {

    private BigDecimal kimRoleResponsibilityId;
    private String kimRoleName;
    private String kimResponsibilityName;
    private String kimActionTypeCode;
    private String kimActionPolicyCode;
    private BigDecimal kimPriority;
    private BigDecimal kimForceAction;

    public BigDecimal getKimRoleResponsibilityId() {
        return kimRoleResponsibilityId;
    }

    public void setKimRoleResponsibilityId(BigDecimal kimRoleResponsibilityId) {
        this.kimRoleResponsibilityId = kimRoleResponsibilityId;
    }

    public String getKimRoleName() {
        return kimRoleName;
    }

    public void setKimRoleName(String kimRoleName) {
        this.kimRoleName = kimRoleName;
    }

    public String getKimResponsibilityName() {
        return kimResponsibilityName;
    }

    public void setKimResponsibilityName(String kimResponsibilityName) {
        this.kimResponsibilityName = kimResponsibilityName;
    }

    public String getKimActionTypeCode() {
        return kimActionTypeCode;
    }

    public void setKimActionTypeCode(String kimActionTypeCode) {
        this.kimActionTypeCode = kimActionTypeCode;
    }

    public String getKimActionPolicyCode() {
        return kimActionPolicyCode;
    }

    public void setKimActionPolicyCode(String kimActionPolicyCode) {
        this.kimActionPolicyCode = kimActionPolicyCode;
    }

    public BigDecimal getKimPriority() {
        return kimPriority;
    }

    public void setKimPriority(BigDecimal kimPriority) {
        this.kimPriority = kimPriority;
    }

    public BigDecimal getKimForceAction() {
        return kimForceAction;
    }

    public void setKimForceAction(BigDecimal kimForceAction) {
        this.kimForceAction = kimForceAction;
    }
}
