package org.kuali.kpme.edo.reviewlayerdef;

import java.util.ArrayList;
import java.util.List;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoReviewLayerDefinitionContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class EdoReviewLayerDefinitionBo extends HrBusinessObject implements EdoReviewLayerDefinitionContract, Comparable<EdoReviewLayerDefinitionBo> {

    private static final long serialVersionUID = -4931968769900946949L;
    
    static class KeyFields {
		private static final String NODE_NAME = "nodeName";
		private static final String REVIEW_LEVEL = "reviewLevel";
		private static final String ROUTE_LEVEL = "routeLevel";
		private static final String WORK_FLOW_ID = "workflowId";
	}
    
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
			.add(KeyFields.NODE_NAME)
			.add(KeyFields.REVIEW_LEVEL)
			.add(KeyFields.ROUTE_LEVEL)
			.add(KeyFields.WORK_FLOW_ID)
			.build();
    
    private String edoReviewLayerDefinitionId;
    private String nodeName;
    private String voteType;
    private String description;
    private boolean reviewLetter;
    private String reviewLevel;
    private String routeLevel;
    private String workflowId;
    private String workflowQualifier;
    
    private List<EdoSuppReviewLayerDefinitionBo> suppReviewLayerDefinitions = new ArrayList<EdoSuppReviewLayerDefinitionBo>();
    
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
		return  getEdoReviewLayerDefinitionId();
	}

	@Override
	public void setId(String edoReviewLayerDefinitionId) {
		setEdoReviewLayerDefinitionId(edoReviewLayerDefinitionId);
	}

	@Override
	protected String getUniqueKey() {
		return getEdoReviewLayerDefinitionId();
	}
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return  new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.NODE_NAME, this.getNodeName())
				.put(KeyFields.REVIEW_LEVEL, this.getReviewLevel())
				.put(KeyFields.ROUTE_LEVEL, this.getRouteLevel())
				.put(KeyFields.WORK_FLOW_ID, this.getWorkflowId())
				.build();
	}
	
	public int compareTo(EdoReviewLayerDefinitionBo edoReviewLayerDefinition) {
		return edoReviewLayerDefinitionId.compareTo(edoReviewLayerDefinition.getEdoReviewLayerDefinitionId());
	}

	public String getEdoReviewLayerDefinitionId() {
		return edoReviewLayerDefinitionId;
	}

	public void setEdoReviewLayerDefinitionId(String edoReviewLayerDefinitionId) {
		this.edoReviewLayerDefinitionId = edoReviewLayerDefinitionId;
	}

	public void setReviewLevel(String reviewLevel) {
		this.reviewLevel = reviewLevel;
	}

	public void setRouteLevel(String routeLevel) {
		this.routeLevel = routeLevel;
	}

	public String getReviewLevel() {
		return reviewLevel;
	}

	public String getRouteLevel() {
		return routeLevel;
	}

	public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isReviewLetter() {
        return reviewLetter;
    }

    public void setReviewLetter(boolean reviewLetter) {
        this.reviewLetter = reviewLetter;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowQualifier() {
        return workflowQualifier;
    }

    public void setWorkflowQualifier(String workflowQualifier) {
        this.workflowQualifier = workflowQualifier;
    }

 	public List<EdoSuppReviewLayerDefinitionBo> getSuppReviewLayerDefinitions() {
		return suppReviewLayerDefinitions;
	}

	public void setSuppReviewLayerDefinitions(
			List<EdoSuppReviewLayerDefinitionBo> suppReviewLayerDefinitions) {
		this.suppReviewLayerDefinitions = suppReviewLayerDefinitions;
	}

	public static EdoReviewLayerDefinitionBo from(EdoReviewLayerDefinition edoReviewLayerDefinition) {
         if (edoReviewLayerDefinition == null) {
             return null;
         }
         EdoReviewLayerDefinitionBo edoReviewLayerDefinitionBo = new EdoReviewLayerDefinitionBo();
         
         edoReviewLayerDefinitionBo.setEdoReviewLayerDefinitionId(edoReviewLayerDefinition.getEdoReviewLayerDefinitionId());
         
        
         edoReviewLayerDefinitionBo.setNodeName(edoReviewLayerDefinition.getNodeName());
         edoReviewLayerDefinitionBo.setVoteType(edoReviewLayerDefinition.getVoteType());
         edoReviewLayerDefinitionBo.setDescription(edoReviewLayerDefinition.getDescription());
         
         edoReviewLayerDefinitionBo.setReviewLetter(edoReviewLayerDefinition.isReviewLetter());
         edoReviewLayerDefinitionBo.setReviewLevel(edoReviewLayerDefinition.getReviewLevel());
        
         edoReviewLayerDefinitionBo.setRouteLevel(edoReviewLayerDefinition.getRouteLevel());
         edoReviewLayerDefinitionBo.setWorkflowId(edoReviewLayerDefinition.getWorkflowId());
         
         edoReviewLayerDefinitionBo.setWorkflowQualifier(edoReviewLayerDefinition.getWorkflowQualifier());
         edoReviewLayerDefinitionBo.setSuppReviewLayerDefinitions(ModelObjectUtils.transform(edoReviewLayerDefinition.getSuppReviewLayerDefinitions(), EdoSuppReviewLayerDefinitionBo.toBo));
         
         // finally copy over the common fields into phra from im
         copyCommonFields(edoReviewLayerDefinitionBo, edoReviewLayerDefinition);
      
         return edoReviewLayerDefinitionBo;
     } 
     
     public static EdoReviewLayerDefinition to(EdoReviewLayerDefinitionBo bo) {
         if (bo == null) {
             return null;
         }
         return EdoReviewLayerDefinition.Builder.create(bo).build();
     }
     
     public static final ModelObjectUtils.Transformer<EdoReviewLayerDefinitionBo, EdoReviewLayerDefinition> toImmutable = new ModelObjectUtils.Transformer<EdoReviewLayerDefinitionBo, EdoReviewLayerDefinition>() {
         public EdoReviewLayerDefinition transform(EdoReviewLayerDefinitionBo input) {
             return EdoReviewLayerDefinitionBo.to(input);
         };
     };
     
     public static final ModelObjectUtils.Transformer<EdoReviewLayerDefinition, EdoReviewLayerDefinitionBo> toBo = new ModelObjectUtils.Transformer<EdoReviewLayerDefinition, EdoReviewLayerDefinitionBo>() {
         public EdoReviewLayerDefinitionBo transform(EdoReviewLayerDefinition input) {
             return EdoReviewLayerDefinitionBo.from(input);
         };
     };
     
}
