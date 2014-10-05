package org.kuali.kpme.edo.reviewernote;

import java.sql.Timestamp;
import java.util.Date;

import org.kuali.kpme.edo.api.reviewernote.EdoReviewerNote;
import org.kuali.kpme.edo.api.reviewernote.EdoReviewerNoteContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class EdoReviewerNoteBo extends PersistableBusinessObjectBase implements EdoReviewerNoteContract {

	private static final long serialVersionUID = 4600890413203389445L;
	/*
	static class KeyFields {
		private static final String EDO_ITEM_TYPE = "itemTypeName";
	}*/
	
	private String edoReviewerNoteId;
	private String edoDossierId;
    private String userPrincipalId;
    private String note;
    private Date reviewDateVal;
    private Timestamp createdAtVal;

	@Override
	public String getObjectId() {
		return super.getObjectId();
	}

	@Override
	public Long getVersionNumber() {
		return super.getVersionNumber();
	}
	
    public String getEdoReviewerNoteId() {
		return edoReviewerNoteId;
	}

	public void setEdoReviewerNoteId(String edoReviewerNoteId) {
		this.edoReviewerNoteId = edoReviewerNoteId;
	}

	public String getEdoDossierId() {
		return edoDossierId;
	}

	public void setEdoDossierId(String edoDossierId) {
		this.edoDossierId = edoDossierId;
	}

	public String getUserPrincipalId() {
		return userPrincipalId;
	}

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getReviewDateVal() {
		return reviewDateVal;
	}

	public void setReviewDateVal(Date reviewDateVal) {
		this.reviewDateVal = reviewDateVal;
	}

	public Timestamp getCreatedAtVal() {
		return createdAtVal;
	}

	public void setCreatedAtVal(Timestamp createdAtVal) {
		this.createdAtVal = createdAtVal;
	}
  
    
    public static EdoReviewerNoteBo from(EdoReviewerNote im) {
        if (im == null) {
            return null;
        }
        EdoReviewerNoteBo edoReviewerNoteBo = new EdoReviewerNoteBo();
        
        edoReviewerNoteBo.setEdoReviewerNoteId(im.getEdoReviewerNoteId());
        edoReviewerNoteBo.setEdoDossierId(im.getEdoDossierId());
        edoReviewerNoteBo.setUserPrincipalId(im.getUserPrincipalId());
        edoReviewerNoteBo.setNote(im.getNote());
        edoReviewerNoteBo.setCreatedAtVal(im.getCreatedAtVal());
        edoReviewerNoteBo.setReviewDateVal(im.getReviewDateVal());
        edoReviewerNoteBo.setVersionNumber(im.getVersionNumber());
        edoReviewerNoteBo.setObjectId(im.getObjectId());
        
     
        return edoReviewerNoteBo;
    } 
    
    public static EdoReviewerNote to(EdoReviewerNoteBo bo) {
        if (bo == null) {
            return null;
        }
        return EdoReviewerNote.Builder.create(bo).build();
    }
    
    public static final ModelObjectUtils.Transformer<EdoReviewerNoteBo, EdoReviewerNote> toImmutable = new ModelObjectUtils.Transformer<EdoReviewerNoteBo, EdoReviewerNote>() {
        public EdoReviewerNote transform(EdoReviewerNoteBo input) {
            return EdoReviewerNoteBo.to(input);
        };
    };
            
    public static final ModelObjectUtils.Transformer<EdoReviewerNote, EdoReviewerNoteBo> toBo = new ModelObjectUtils.Transformer<EdoReviewerNote, EdoReviewerNoteBo>() {
        public EdoReviewerNoteBo transform(EdoReviewerNote input) {
            return EdoReviewerNoteBo.from(input);
        };
    };

}
