package org.kuali.kpme.edo.group.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.kpme.edo.api.group.EdoGroupDefinition;
import org.kuali.kpme.edo.group.EdoGroupDefinitionBo;
import org.kuali.kpme.edo.group.dao.EdoGroupDefinitionDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 12/18/13
 * Time: 10:44 AM
 */
public class EdoGroupDefinitionServiceImpl implements EdoGroupDefinitionService {

    private static final Logger LOG = Logger.getLogger(EdoGroupDefinitionServiceImpl.class);
    private EdoGroupDefinitionDao edoGroupDefinitionDao;
    
    protected List<EdoGroupDefinition> convertToImmutable(List<EdoGroupDefinitionBo> bos) {
		return ModelObjectUtils.transform(bos, EdoGroupDefinitionBo.toImmutable);
	}

    public EdoGroupDefinitionDao getEdoGroupDefinitionDao() {
        return edoGroupDefinitionDao;
    }

    public void setEdoGroupDefinitionDao(EdoGroupDefinitionDao edoGroupDefinitionDao) {
        this.edoGroupDefinitionDao = edoGroupDefinitionDao;
    }

    public EdoGroupDefinition getEdoGroupDefinition(String edoGroupId) {        
        return EdoGroupDefinitionBo.to(edoGroupDefinitionDao.getEdoGroupDefinition(edoGroupId));
    }

    public List<EdoGroupDefinition> getEdoGroupDefinitionsByWorkflowId(String edoWorkflowId) {        
        List<EdoGroupDefinitionBo> bos = edoGroupDefinitionDao.getEdoGroupDefinitionsByWorkflowId(edoWorkflowId);
		return convertToImmutable(bos);
    }

}
