package org.kuali.kpme.edo.supplemental;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.kuali.kpme.edo.api.supplemental.EdoSupplementalTracking;
import org.kuali.kpme.edo.api.supplemental.EdoSupplementalTrackingContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 9/10/13
 * Time: 11:36 AM
 */
public class EdoSupplementalTrackingBo extends PersistableBusinessObjectBase implements EdoSupplementalTrackingContract {

	private static final long serialVersionUID = -1025195039577956152L;
	
	static class KeyFields {
		private static final String EDO_DOSSIER_ID = "edoDossierId";
		private static final String REVIEW_LEVEL = "reviewLevel";
	}
	
	private String edoSupplementalTrackingId;
    private String edoDossierId;
    private int reviewLevel;
    private boolean acknowledged;
	private Timestamp actionTimestamp;
	private String userPrincipalId;
	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
			.add(KeyFields.EDO_DOSSIER_ID)
			.add(KeyFields.REVIEW_LEVEL)
			.build();

	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return  new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.EDO_DOSSIER_ID, this.getEdoDossierId())
				.put(KeyFields.REVIEW_LEVEL, this.getReviewLevel())
				.build();
	}
	
	public String getId() {
		return getEdoSupplementalTrackingId();
	}

	public void setId(String id) {
		setEdoSupplementalTrackingId(id);
	}
	
    public String getEdoSupplementalTrackingId() {
        return edoSupplementalTrackingId;
    }

    public void setEdoSupplementalTrackingId(String edoSupplementalTrackingId) {
        this.edoSupplementalTrackingId = edoSupplementalTrackingId;
    }

    public String getEdoDossierId() {
        return edoDossierId;
    }

    public void setEdoDossierId(String edoDossierId) {
        this.edoDossierId = edoDossierId;
    }

   public int getReviewLevel() {
		return reviewLevel;
	}

	public void setReviewLevel(int reviewLevel) {
		this.reviewLevel = reviewLevel;
	}

	public boolean isAcknowledged() {
		return acknowledged;
	}

	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}
	
	public String getUserPrincipalId() {
		return userPrincipalId;
	}

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}
	
	public Timestamp getActionTimestamp() {
		return actionTimestamp;
	}

	public void setActionTimestamp(Timestamp actionTimestamp) {
		this.actionTimestamp = actionTimestamp;
	}

    public DateTime getActionFullDateTime() {
        return actionTimestamp != null ? new DateTime(actionTimestamp) : null;
    }
    
	public static EdoSupplementalTrackingBo from(EdoSupplementalTracking im) {
        if (im == null) {
            return null;
        }
        EdoSupplementalTrackingBo it = new EdoSupplementalTrackingBo();
        it.setEdoSupplementalTrackingId(im.getEdoSupplementalTrackingId());
        it.setEdoDossierId(im.getEdoDossierId());
        it.setReviewLevel(im.getReviewLevel());
        it.setAcknowledged(im.isAcknowledged());
        it.setUserPrincipalId(im.getUserPrincipalId());
        it.setActionTimestamp(im.getActionFullDateTime() == null ? null : new Timestamp(im.getActionFullDateTime().getMillis()));
        it.setVersionNumber(im.getVersionNumber());
        it.setObjectId(im.getObjectId());
        
        return it;
    }

    public static EdoSupplementalTracking to(EdoSupplementalTrackingBo bo) {
        if (bo == null) {
            return null;
        }

        return EdoSupplementalTracking.Builder.create(bo).build();
    }
    
    public static final ModelObjectUtils.Transformer<EdoSupplementalTrackingBo, EdoSupplementalTracking> toImmutable = new ModelObjectUtils.Transformer<EdoSupplementalTrackingBo, EdoSupplementalTracking>() {
        public EdoSupplementalTracking transform(EdoSupplementalTrackingBo input) {
            return EdoSupplementalTrackingBo.to(input);
        };
    };
            
    public static final ModelObjectUtils.Transformer<EdoSupplementalTracking, EdoSupplementalTrackingBo> toBo = new ModelObjectUtils.Transformer<EdoSupplementalTracking, EdoSupplementalTrackingBo>() {
        public EdoSupplementalTrackingBo transform(EdoSupplementalTracking input) {
            return EdoSupplementalTrackingBo.from(input);
        };
    };

   
}
