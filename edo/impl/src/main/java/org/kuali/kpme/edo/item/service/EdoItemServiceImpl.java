package org.kuali.kpme.edo.item.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.item.EdoItemBo;
import org.kuali.kpme.edo.item.dao.EdoItemDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 2/28/13
 * Time: 2:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoItemServiceImpl extends PlatformAwareDaoBaseOjb implements EdoItemService {

    private EdoItemDao edoItemDao;
    
    protected List<EdoItem> convertToImmutable(List<EdoItemBo> bos) {
		return ModelObjectUtils.transform(bos, EdoItemBo.toImmutable);
	}
    
    protected List<EdoItemBo> convertToBo(List<EdoItem> ims) {
		return ModelObjectUtils.transform(ims, EdoItemBo.toBo);
	}
    
    public void setEdoItemDao(EdoItemDao item) {
        this.edoItemDao = item;
    }
    
    public EdoItem getEdoItem(String edoItemID) {
        return EdoItemBo.to(getEdoItemBo(edoItemID));
    }
        
    public  EdoItemBo getEdoItemBo(String edoItemID) {
        return edoItemDao.getEdoItem(edoItemID);
    }
    
    public void saveOrUpdate(EdoItemBo itemBo) {
        this.edoItemDao.saveOrUpdate(itemBo);
    }

    public void saveOrUpdate(EdoItem item) {
        this.saveOrUpdate(EdoItemBo.from(item));
    }
    
    public void saveOrUpdate(List<EdoItem> edoItems) {
    	this.edoItemDao.saveOrUpdate(convertToBo(edoItems));
    }

    public EdoItem getFile(String edoItemID, ByteArrayOutputStream bao) throws IOException {

        EdoItemBo item = edoItemDao.getEdoItem(edoItemID);
        File fp = new File(item.getFileLocation());
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fp);
            byte[] fileContents = new byte[(int)fp.length()];
            fis.read(fileContents);
            bao.write(fileContents);
            if (fis != null) {
                fis.close();
            }
            return EdoItemBo.to(item);
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve file with item ID = " + edoItemID.toString() + ".");
        }
    }

    @Override
    public Integer getNextRowIndexNum(String edoChecklistItemId, String uploader) {
       return this.edoItemDao.getNextRowIndexNum(edoChecklistItemId, uploader);
    }

    @Override
    public List<EdoItem> getPendingItemsByDossierId(String edoDossierId, String edoChecklistItemID) {
    	List <EdoItemBo> bos = this.edoItemDao.getPendingItemsByDossierId(edoDossierId, edoChecklistItemID);
        return convertToImmutable(bos);
    }
    
    @Override
    public List<EdoItem> getItemsByDossierIdForAddendumFalgZero(String edoDossierId, String edoChecklistItemID) {
    	List <EdoItemBo> bos = this.edoItemDao.getItemsByDossierIdForAddendumFalgZero(edoDossierId, edoChecklistItemID);
    	return convertToImmutable(bos);
    }

    public boolean isReviewLetterPendingRoute(String edoDossierId, String edoReviewLayerDefintionId ) {
        boolean isPending = false;

        List<EdoItemBo> letters = this.edoItemDao.getPendingLettersByDossierId(edoDossierId, edoReviewLayerDefintionId);

        if (CollectionUtils.isNotEmpty(letters)) {
            isPending = true;
        }

        return isPending;
    }

    public void updateLetterAsLevelRouted(String edoDossierId, String edoReviewLayerDefintionId ) {
        List<EdoItemBo> letters = this.edoItemDao.getPendingLettersByDossierId(edoDossierId, edoReviewLayerDefintionId);

        for (EdoItemBo ltr : letters) {
        	// According to Maria, layerLevel is a flag to indicate if the item has been routed, so we have combined layerLevel field 
        	// and addendum field and made boolean "routed" field.
        	// TODO Make sure setting routed to true won't break anything
            //ltr.setLayerLevel("ROUTED");
        	ltr.setRouted(true);
            this.edoItemDao.saveOrUpdate(ltr);
        }

        return;
    }
    
    public void deleteItem(EdoItem item) {
    	this.edoItemDao.deleteItem(EdoItemBo.from(item));
    }

}
