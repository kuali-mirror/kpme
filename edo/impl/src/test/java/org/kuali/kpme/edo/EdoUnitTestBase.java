package org.kuali.kpme.edo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.kpme.core.KPMEIntegrationTestCase;
import org.kuali.kpme.edo.dossier.EdoDossier;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.core.impl.config.module.CoreConfigurer;
import org.kuali.rice.core.impl.config.property.ConfigFactoryBean;
import org.kuali.rice.kew.config.KEWConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"/edo-spring-beans.xml,classpath:**/*/CommonSpringBeans.xml"})
//@ContextConfiguration({"/edo-spring-beans.xml", "/edoTestHarnessSpringBeans.xml"})
public class EdoUnitTestBase extends KPMEIntegrationTestCase{

    static final String UNIT_TEST_SAMPLE_FILES_PATH="target/test-classes/sample-files/";
/*    @Autowired
	protected ConfigFactoryBean bootstrapConfig;
	@Autowired
	protected CoreConfigurer coreConfigurer;
	@Autowired
	protected KEWConfigurer kewConfigurer;*/
	
	protected EdoDossier testDossier = null;

    @Override
    public String getModuleName() {
        return "edo";
    }

	/*@Test
	public void testSpringContext() throws Exception {
		Assert.assertNotNull("Constructor message instance is null.", bootstrapConfig);
		List<String> locations = bootstrapConfig.getConfigLocations();
		for (String location : locations) {
			System.out.println("location: " + location);
			Assert.assertNotNull("location: " + location + " is null", location);
		}
	}*/

	/*@Before
	public void setUp() throws Exception {
		List<EdoDossier> dosseirs = EdoServiceLocator.getEdoDossierService().getDossierList();
		// find an submitted dosseir
		for (EdoDossier edoDossier : dosseirs) {
			if (StringUtils.equals(edoDossier.getDossierStatus(), EdoConstants.DOSSIER_STATUS.SUBMITTED) ||
					StringUtils.equals(edoDossier.getDossierType().getDossierTypeCode(), "TN")) {
				testDossier = edoDossier;
				break;
			}
		}
		
	}
	
    @After
    public void tearDown() throws Exception {
    }*/
	
}
