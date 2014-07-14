package org.kuali.kpme.edo.item.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.item.EdoItemBo;
import org.kuali.kpme.edo.item.dao.EdoItemDao;
import org.kuali.kpme.edo.item.type.service.EdoItemTypeService;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    private EdoItemTypeService edoItemTypeService = EdoServiceLocator.getEdoItemTypeService();
    
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
    
    public List<EdoItem> getItemList(String edoDossierID, String edoChecklistItemID) {
        List<EdoItemBo> bos = edoItemDao.getItemList(edoDossierID, edoChecklistItemID);
    	return convertToImmutable(bos);
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
    	List<EdoItemBo> bos = this.edoItemDao.getItemsByDossierIdForAddendumFalgZero(edoDossierId, edoChecklistItemID);
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
    
    public List<EdoItem> getReviewLetterEdoItems(String edoDossierId, String edoReviewLayerDefinitionId) {
    	List<EdoItemBo> bos = this.edoItemDao.getReviewLetterEdoItems(edoDossierId, edoReviewLayerDefinitionId);
    	List<EdoItemBo> returnedBos = new ArrayList<EdoItemBo>();
    	
    	for (EdoItemBo bo : bos) {
    		String itemTypeName = edoItemTypeService.getItemType(bo.getEdoItemId()).getItemTypeName();
    		if (StringUtils.equals(itemTypeName, EdoConstants.EDO_ITEM_TYPE_NAME_REVIEW_LETTER)) {
    			returnedBos.add(bo);
    		}
    	}

    	return convertToImmutable(returnedBos);
    }
    
    public List<EdoItem> getListOfEdoItems(List<String> idList) {
    	List<EdoItemBo> bos = this.edoItemDao.getListOfEdoItems(idList);
    	return convertToImmutable(bos);
    }
    
    public String getItemJSONString(EdoItem item) {
    	ArrayList<String> tmp = new ArrayList<String>();
        Type tmpType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();
        String uploadString = new SimpleDateFormat("yyyy-MM-dd hh:mma").format(new Timestamp(item.getActionFullDateTime().getMillis()));

        tmp.add(item.getRowIndex()+"");
        tmp.add(item.getEdoItemId());
        tmp.add(item.getEdoItemTypeId());
        tmp.add(item.getFileName());
        tmp.add(item.getFileLocation());
        tmp.add(item.getEdoChecklistItemId());
        tmp.add(uploadString);
        tmp.add(item.getUserPrincipalId());
        tmp.add("");
        tmp.add(item.getEdoDossierId());
        tmp.add(item.getEdoReviewLayerDefId() == null ? "" : item.getEdoReviewLayerDefId());
        tmp.add(item.isRouted() == true ? "Y" : "N");
        tmp.add(item.getFileDescription());
        tmp.add(item.getAction());
        tmp.add(item.isActive() == true ? "Y" : "N");

        return gson.toJson(tmp, tmpType);
    }

}
