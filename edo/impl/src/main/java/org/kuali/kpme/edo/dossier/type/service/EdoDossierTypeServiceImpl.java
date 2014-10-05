package org.kuali.kpme.edo.dossier.type.service;

import org.kuali.kpme.edo.api.dossier.type.EdoDossierType;
import org.kuali.kpme.edo.dossier.type.EdoDossierTypeBo;
import org.kuali.kpme.edo.dossier.type.dao.EdoDossierTypeDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import java.util.List;


public class EdoDossierTypeServiceImpl implements EdoDossierTypeService {

    private EdoDossierTypeDao edoDossierTypeDao;
    
    public void setEdoDossierTypeDao(EdoDossierTypeDao dossierType) {
        this.edoDossierTypeDao = dossierType;
    }
    
    public EdoDossierType getEdoDossierTypeById(String edoDossierTypeID) {
    	EdoDossierTypeBo edoDossierTypeBo = edoDossierTypeDao.getEdoDossierTypeById(edoDossierTypeID);
    	
    	if ( edoDossierTypeBo == null){
    		return null;
    	}
    	
    	EdoDossierType.Builder builder = EdoDossierType.Builder.create(edoDossierTypeBo);
    	
    	return builder.build();
    	
    }

    public List<EdoDossierType> getEdoDossierTypeList() {
    	
    	return ModelObjectUtils.transform(edoDossierTypeDao.getEdoDossierTypeList(), EdoDossierTypeBo.toImmutable);
    }

    public void saveOrUpdate(EdoDossierType dossierType) {
    	EdoDossierTypeBo bo = EdoDossierTypeBo.from(dossierType);
    	
        this.edoDossierTypeDao.saveOrUpdate(bo);
    }
    
    /*
    @Override
	public EdoDossierType saveOrUpdate(EdoDossierType edoDossierType) {
        if (edoDossierType == null) {
            return null;
        }
        return EdoDossierTypeBo.to(KRADServiceLocatorWeb.getLegacyDataAdapter().save(EdoDossierTypeBo.from(edoDossierType)));
	}*/
    

    public EdoDossierType getEdoDossierTypeByName(String dossierTypeName) {
    	EdoDossierTypeBo edoDossierTypeBo = edoDossierTypeDao.getEdoDossierTypeByName(dossierTypeName);
    	
    	if ( edoDossierTypeBo == null){
    		return null;
    	}
    	
    	EdoDossierType.Builder builder = EdoDossierType.Builder.create(edoDossierTypeBo);
    	
    	return builder.build();
    }
    
    public EdoDossierType getEdoDossierTypeByCode(String dossierTypeCode) {
    	EdoDossierTypeBo edoDossierTypeBo = edoDossierTypeDao.getEdoDossierTypeByCode(dossierTypeCode);
    	
    	if ( edoDossierTypeBo == null){
    		return null;
    	}
    	
    	EdoDossierType.Builder builder = EdoDossierType.Builder.create(edoDossierTypeBo);
    	
    	return builder.build();
    }
}
