package org.kuali.kpme.edo.item.service;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.edo.item.EdoItem;
import org.kuali.kpme.edo.item.dao.EdoItemDao;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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

    public List<EdoItem> getItemList(BigDecimal checklistSectionID) {
        return edoItemDao.getItemList(checklistSectionID);
    }
    public EdoItem getEdoItem( BigDecimal itemID ) {
        return edoItemDao.getEdoItem(itemID);
    }

    public void setEdoItemDao(EdoItemDao item) {
        this.edoItemDao = item;
    }

    public void saveOrUpdate(EdoItem item) {
        this.edoItemDao.saveOrUpdate(item);
    }

    public EdoItem getFile(BigDecimal itemID, ByteArrayOutputStream bao) throws IOException {

        EdoItem item = edoItemDao.getEdoItem(itemID);
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
            return item;
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve file with item ID = " + itemID.toString() + ".");
        }
    }

    @Override
    public Integer getNextRowIndexNum(BigDecimal checklistItemId, String uploader) {
       return this.edoItemDao.getNextRowIndexNum(checklistItemId, uploader);
    }

    @Override
    public List<EdoItem> getPendingItemsByDossierId(BigDecimal dossierId, BigDecimal checklistItemID) {

        return this.edoItemDao.getPendingItemsByDossierId( dossierId, checklistItemID );
    }
    
    @Override
    public List<EdoItem> getItemsByDossierIdForAddendumFalgZero( BigDecimal dossierId, BigDecimal checklistItemID) {
    	 return this.edoItemDao.getItemsByDossierIdForAddendumFalgZero(dossierId, checklistItemID);
    }

    public boolean isReviewLetterPendingRoute( BigDecimal dossierId, BigDecimal reviewLayerDefintionId ) {
        boolean isPending = false;

        List<EdoItem> letters = this.edoItemDao.getPendingLettersByDossierId(dossierId, reviewLayerDefintionId);

        if (CollectionUtils.isNotEmpty(letters)) {
            isPending = true;
        }

        return isPending;
    }

    public void updateLetterAsLevelRouted( BigDecimal dossierId, BigDecimal reviewLayerDefinitionId ) {
        List<EdoItem> letters = this.edoItemDao.getPendingLettersByDossierId(dossierId, reviewLayerDefinitionId);

        for ( EdoItem ltr : letters ) {
            ltr.setLayerLevel("ROUTED");
            this.edoItemDao.saveOrUpdate(ltr);
        }

        return;
    }

}
