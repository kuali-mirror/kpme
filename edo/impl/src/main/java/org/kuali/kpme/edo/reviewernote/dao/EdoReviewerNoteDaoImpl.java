package org.kuali.kpme.edo.reviewernote.dao;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
//import org.kuali.kpme.edo.item.type.EdoItemTypeBo;
import org.kuali.kpme.edo.reviewernote.EdoReviewerNoteBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class EdoReviewerNoteDaoImpl extends PlatformAwareDaoBaseOjb  implements EdoReviewerNoteDao {

    /**
     * Retrieve a single item type from the database given the specified ID
     *
     * @param   edoReviewerNoteId  the database ID of the requested item type
     * @return              an object of EdoReviewerNoteBo, null on error or no data
     */
	public EdoReviewerNoteBo getReviewerNoteById(String edoReviewerNoteId) {

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("edoReviewerNoteId", edoReviewerNoteId);

        Query query = QueryFactory.newQuery(EdoReviewerNoteBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            if (c.size() == 1) {
                return (EdoReviewerNoteBo)c.toArray()[0];
            }
        }
        
        return null;
    }

	public List<EdoReviewerNoteBo> getReviewerNotesByDossierId(String edoDossierId) {

        List<EdoReviewerNoteBo> reviewerNoteList = new LinkedList<EdoReviewerNoteBo>();

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("edoDossierId", edoDossierId);
        //cConditions.addEqualTo("edoDossierId", OjbSubQueryUtil.getEffectiveDateSubQuery(EdoReviewerNoteBo.class, edoDossierId, EdoItemTypeBo.BUSINESS_KEYS, false));
         
        Query query = QueryFactory.newQuery(EdoReviewerNoteBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            if (c.size() > 0) {
            	reviewerNoteList.addAll(c);
                return reviewerNoteList;
            }
        }
        
        return null;
	}
	
	public List<EdoReviewerNoteBo> getReviewerNotesByUserPrincipalId(String userPrincipalId) {
		List<EdoReviewerNoteBo> reviewerNoteList = new LinkedList<EdoReviewerNoteBo>();

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("userPrincipalId", userPrincipalId);
        //cConditions.addEqualTo("edoDossierId", OjbSubQueryUtil.getEffectiveDateSubQuery(EdoReviewerNoteBo.class, edoDossierId, EdoItemTypeBo.BUSINESS_KEYS, false));
         
        Query query = QueryFactory.newQuery(EdoReviewerNoteBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            if (c.size() > 0) {
            	reviewerNoteList.addAll(c);
                return reviewerNoteList;
            }
        }
        
        return null;
		
	}
	
    /**
     * Save or update the EdoItemType object back to the database
     *
     * @param   itemType    the object of EdoItemType to save or update
     */
    public void saveOrUpdate(EdoReviewerNoteBo edoReviewerNoteBo) {
        this.getPersistenceBrokerTemplate().store( edoReviewerNoteBo );
    }

}
