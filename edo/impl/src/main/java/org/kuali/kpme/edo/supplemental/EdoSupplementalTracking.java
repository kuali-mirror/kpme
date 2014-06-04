package org.kuali.kpme.edo.supplemental;

import java.math.BigDecimal;
import java.util.Date;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 9/10/13
 * Time: 11:36 AM
 */
public class EdoSupplementalTracking {

    private Integer supplementalTrackingId;
    private Integer dossierId;
    private BigDecimal reviewLevel;
    private Date lastUpdated;
    private String updatedBy;
    private boolean acknowledged;

    public Integer getSupplementalTrackingId() {
        return supplementalTrackingId;
    }

    public void setSupplementalTrackingId(Integer supplementalTrackingId) {
        this.supplementalTrackingId = supplementalTrackingId;
    }

    public Integer getDossierId() {
        return dossierId;
    }

    public void setDossierId(Integer dossierId) {
        this.dossierId = dossierId;
    }

   public BigDecimal getReviewLevel() {
		return reviewLevel;
	}

	public void setReviewLevel(BigDecimal reviewLevel) {
		this.reviewLevel = reviewLevel;
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

	public boolean isAcknowledged() {
		return acknowledged;
	}

	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

   
}
