package org.kuali.kpme.edo.dossier.type;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.edo.api.dossier.type.EdoDossierType;
import org.kuali.kpme.edo.api.dossier.type.EdoDossierTypeContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class EdoDossierTypeBo extends HrBusinessObject implements EdoDossierTypeContract {
	
	static class KeyFields {
		private static final String DOSSIER_TYPE_CODE = "dossierTypeCode";
	}
	
	private static final long serialVersionUID = 6843318899816055301L;
	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(KeyFields.DOSSIER_TYPE_CODE)
            .build();
	
	//public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "PrincipalHRAttributes";

	public static final ModelObjectUtils.Transformer<EdoDossierTypeBo, EdoDossierType> toImmutable = new ModelObjectUtils.Transformer<EdoDossierTypeBo, EdoDossierType>() {
		public EdoDossierType transform(EdoDossierTypeBo input) {
			return EdoDossierTypeBo.to(input);
		};
	};

	public static final ModelObjectUtils.Transformer<EdoDossierType, EdoDossierTypeBo> toBo = new ModelObjectUtils.Transformer<EdoDossierType, EdoDossierTypeBo>() {
		public EdoDossierTypeBo transform(EdoDossierType input) {
			return EdoDossierTypeBo.from(input);
		};
	};
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(KeyFields.DOSSIER_TYPE_CODE, this.getDossierTypeCode())
			.build();
	}
	
	@Override
	public String getUniqueKey() {
		return this.getEdoDossierTypeID();
	}

    private String edoDossierTypeID;
    private String dossierTypeCode;
    private String dossierTypeName;
    private String documentTypeName;
  
    @Override
	public boolean isActive() {
		return super.isActive();
	}

	@Override
	public void setActive(boolean active) {
		super.setActive(active);
	}
	
	@Override
	public String getObjectId() {
		return super.getObjectId();
	}

	@Override
	public Long getVersionNumber() {
		return super.getVersionNumber();
	}

    @Override
   	public String getId() {
   		return this.getEdoDossierTypeID();
   	}
   	@Override
   	public void setId(String id) {
   		setEdoDossierTypeID(id);
   	}
    
    public String getEdoDossierTypeID() {
		return edoDossierTypeID;
	}

	public void setEdoDossierTypeID(String edoDossierTypeID) {
		this.edoDossierTypeID = edoDossierTypeID;
	}

	public String getDossierTypeName() {
        return dossierTypeName;
    }

    public void setDossierTypeName(String dossierTypeName) {
        this.dossierTypeName = dossierTypeName;
    }

    public String getDossierTypeCode() {
        return dossierTypeCode;
    }

    public void setDossierTypeCode(String dossierTypeCode) {
        this.dossierTypeCode = dossierTypeCode;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

	public String getEdoDossierTypeJSONString() {
        ArrayList<String> tmp = new ArrayList<String>();
        Type tmpType = new TypeToken<List<String>>() {}.getType();
        Gson gson = new Gson();

        tmp.add(this.getEdoDossierTypeID().toString());
        tmp.add(this.getDossierTypeName());
        tmp.add(this.getDossierTypeCode());
       
        return gson.toJson(tmp, tmpType).toString();
    }
    
    public static EdoDossierTypeBo from(EdoDossierType edoDossierType) {
        if (edoDossierType == null) {
            return null;
        }
        EdoDossierTypeBo edoDossierTypeBo = new EdoDossierTypeBo();
        
        edoDossierTypeBo.setEdoDossierTypeID(edoDossierType.getEdoDossierTypeID());
        edoDossierTypeBo.setDossierTypeCode(edoDossierType.getDossierTypeCode());
        edoDossierTypeBo.setDocumentTypeName(edoDossierType.getDocumentTypeName());
        edoDossierTypeBo.setDossierTypeName(edoDossierType.getDossierTypeName());
      
        // finally copy over the common fields into EdoDossierTypeBo from im
        copyCommonFields(edoDossierTypeBo, edoDossierType);

        return edoDossierTypeBo;
    }

    public static EdoDossierType to(EdoDossierTypeBo bo) {
        if (bo == null) {
            return null;
        }
       
        return EdoDossierType.Builder.create(bo).build();
    }
   
}
