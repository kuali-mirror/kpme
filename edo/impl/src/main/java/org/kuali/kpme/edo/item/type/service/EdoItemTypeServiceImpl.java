package org.kuali.kpme.edo.item.type.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.edo.api.item.type.EdoItemType;
import org.kuali.kpme.edo.item.type.EdoItemTypeBo;
import org.kuali.kpme.edo.item.type.dao.EdoItemTypeDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/20/12
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoItemTypeServiceImpl implements EdoItemTypeService {

    private EdoItemTypeDao edoItemTypeDao;
    
    protected List<EdoItemType> convertToImmutable(List<EdoItemTypeBo> bos) {
		return ModelObjectUtils.transform(bos, EdoItemTypeBo.toImmutable);
	}
    
    public void setEdoItemTypeDao(EdoItemTypeDao itemType) {
        this.edoItemTypeDao = itemType;
    }

    public List<EdoItemType> getItemTypeList(LocalDate asOfDate) {
    	List<EdoItemTypeBo> bos = edoItemTypeDao.getItemTypeList(asOfDate);
        return convertToImmutable(bos);
    }

    public EdoItemType getItemType(String edoItemTypeID) {        
        return EdoItemTypeBo.to(edoItemTypeDao.getItemType(edoItemTypeID));
    }

    public void saveOrUpdate(EdoItemType itemTypeObj) {
        this.edoItemTypeDao.saveOrUpdate(EdoItemTypeBo.from(itemTypeObj));
    }

    public String getItemTypeID(String itemTypeName, LocalDate asOfDate) {
        return this.edoItemTypeDao.getItemTypeID(itemTypeName, asOfDate);
    }
    
    public String getEdoItemTypeJSONString(EdoItemType aType) {
    	
    	 ArrayList<String> tmp = new ArrayList<String>();
         Type tmpType = new TypeToken<List<String>>() {}.getType();
         Gson gson = new Gson();

         tmp.add(aType.getEdoItemTypeID().toString());
         tmp.add(aType.getItemTypeName());
         tmp.add(aType.getItemTypeDescription());
         tmp.add(aType.getItemTypeInstructions());
         tmp.add(String.valueOf(aType.isItemTypeExtAvailable()));

         return gson.toJson(tmp, tmpType).toString();
    }
}
