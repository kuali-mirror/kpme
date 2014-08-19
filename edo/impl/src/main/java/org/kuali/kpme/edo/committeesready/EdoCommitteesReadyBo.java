package org.kuali.kpme.edo.committeesready;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.kuali.kpme.core.groupkey.HrGroupKeyBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.edo.api.committeesready.EdoCommitteesReady;
import org.kuali.kpme.edo.api.committeesready.EdoCommitteesReadyContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

import com.google.common.collect.ImmutableList;

public class EdoCommitteesReadyBo extends PersistableBusinessObjectBase implements EdoCommitteesReadyContract {

	private static final long serialVersionUID = -1822153069956148475L;

	static class KeyFields {
		private static final String GROUP_KEY_CODE = "groupKeyCode";
	}
	
	private String edoCommitteesReadyId;
	private boolean ready;
	private String userPrincipalId;
	private String groupKeyCode;
	private transient HrGroupKeyBo groupKey;
	private Timestamp timestamp;

	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
    		.add(KeyFields.GROUP_KEY_CODE)
			.build();
	
	public String getEdoCommitteesReadyId() {
		return edoCommitteesReadyId;
	}

	public void setEdoCommitteesReadyId(String edoCommitteesReadyId) {
		this.edoCommitteesReadyId = edoCommitteesReadyId;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	@Override
	public String getUserPrincipalId() {
		return userPrincipalId;
	}

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}
	
	@Override
    public String getGroupKeyCode() {
        return groupKeyCode;
    }

    public void setGroupKeyCode(String groupKeyCode) {
        this.groupKeyCode = groupKeyCode;
    }
    
    @Override
    public HrGroupKeyBo getGroupKey() {
        if (groupKey == null) {
            groupKey = HrGroupKeyBo.from(HrServiceLocator.getHrGroupKeyService().getHrGroupKey(getGroupKeyCode(), getCreateTime().toLocalDate()));
        }
        return groupKey;
    }

    public void setGroupKey(HrGroupKeyBo groupKey) {
        this.groupKey = groupKey;
    }
    
    public Timestamp getTimestamp() {
		return timestamp == null ?  null : new Timestamp(timestamp.getTime());
	}

    public DateTime getCreateTime() {
        return timestamp == null ? null : new DateTime(timestamp.getTime());
    }

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public static EdoCommitteesReadyBo from(EdoCommitteesReady im) {
        if (im == null) {
            return null;
        }
        
        EdoCommitteesReadyBo ecd = new EdoCommitteesReadyBo();

        ecd.setEdoCommitteesReadyId(im.getEdoCommitteesReadyId());
        ecd.setReady(im.isReady());
        ecd.setUserPrincipalId(im.getUserPrincipalId());
        ecd.setGroupKeyCode(im.getGroupKeyCode());
        ecd.setGroupKey(HrGroupKeyBo.from(im.getGroupKey()));
        ecd.setTimestamp(im.getCreateTime() == null ? null : new Timestamp(im.getCreateTime().getMillis()));       
        ecd.setVersionNumber(im.getVersionNumber());
        ecd.setObjectId(im.getObjectId());

        return ecd;
    }

    public static EdoCommitteesReady to(EdoCommitteesReadyBo bo) {
        if (bo == null) {
            return null;
        }

        return EdoCommitteesReady.Builder.create(bo).build();
    }
    
    public static final ModelObjectUtils.Transformer<EdoCommitteesReadyBo, EdoCommitteesReady> toImmutable = new ModelObjectUtils.Transformer<EdoCommitteesReadyBo, EdoCommitteesReady>() {
        public EdoCommitteesReady transform(EdoCommitteesReadyBo input) {
            return EdoCommitteesReadyBo.to(input);
        };
    };
            
    public static final ModelObjectUtils.Transformer<EdoCommitteesReady, EdoCommitteesReadyBo> toBo = new ModelObjectUtils.Transformer<EdoCommitteesReady, EdoCommitteesReadyBo>() {
        public EdoCommitteesReadyBo transform(EdoCommitteesReady input) {
            return EdoCommitteesReadyBo.from(input);
        };
    };
}
