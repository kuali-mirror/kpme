package org.kuali.kpme.edo;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

import java.math.BigDecimal;
import java.util.Date;

public abstract class EdoBusinessObject extends PersistableBusinessObjectBase {


    private static final long serialVersionUID = -6225199435609687779L;
    private Date createDate;
    private String createdBy;
    private Date lastUpdated;
    private String updatedBy;

    public abstract BigDecimal getId();

    public abstract void setId(BigDecimal id);

    protected abstract BigDecimal getUniqueKey();

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
