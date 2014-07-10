package org.kuali.kpme.edo.authorization.service;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.kpme.edo.dossier.EdoDossierBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.vote.EdoVoteRecordBo;
import org.kuali.kpme.edo.vote.dao.EdoVoteRecordDao;
import org.kuali.rice.core.framework.persistence.ojb.JtaOjbConfigurer;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional
public class AuthorizationServiceImplTest extends EdoUnitTestBase {

    //@Autowired
    //private JtaOjbConfigurer ojbConfigurer;

//	@Autowired
//	@Qualifier("edo")
//	private XAPoolDataSource datasource;

    @Test
    public void testSuppmentalRouteModule() throws Exception {

       // WorkflowDocument wfd = WorkflowDocumentFactory.createDocument(testDossier.getCandidatePrincipalId(), "SupplementalMaterialsDocument");
       // wfd.route("routing supp material doc");
       // DossierProcessDocumentHeader header = EdoServiceLocator.getDossierProcessDocumentHeaderService().getDossierProcessDocumentHeader(wfd.getDocumentId());
       /// Assert.assertEquals("Document is not enroute", header.getDocumentStatus(), wfd.getStatus().getCode());
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
       // EdoVoteRecordDao dao = (EdoVoteRecordDao) EdoServiceLocator.SPRING_APPLICATION_CONTEXT.getBean("edoVoteRecordDao");
        //dao.deleteVoteRecord(this.voteRecord);
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
       // voteRecord.setAbsentCountTenure(0);
       // voteRecord.setAbstainCountTenure(0);
       // voteRecord.setNoCountTenure(1);
       // voteRecord.setYesCountTenure(6);
       // voteRecord.setDossierId(testDossier.getDossierID().intValue());
       // voteRecord.setVoteType(EdoConstants.VoteType.VOTE_TYPE_TENURE);
      //  voteRecord.setReviewLayerDefinitionId(new BigDecimal(1));
       // EdoVoteRecordDao dao = (EdoVoteRecordDao) EdoServiceLocator.SPRING_APPLICATION_CONTEXT.getBean("edoVoteRecordDao");
        //dao.saveVoteRecord(this.voteRecord);
    }
}
