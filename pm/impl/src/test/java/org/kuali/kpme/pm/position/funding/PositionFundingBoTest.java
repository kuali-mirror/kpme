/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.pm.position.funding;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.kfs.coa.businessobject.Account;
import org.kuali.kpme.pm.api.position.funding.PositionFunding;
import org.kuali.kpme.pm.position.PositionBo;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.mockito.Mockito;

public class PositionFundingBoTest {

	private static BusinessObjectService businessObjectService;
	private static Map<String, PositionFunding> testPositionFundingBos;
	public static PositionFunding.Builder PositionFundingBuilder = PositionFunding.Builder.create();
	private static final LocalDate currentTime = new LocalDate();
	
	static {
		testPositionFundingBos = new HashMap<String, PositionFunding>();
		PositionFundingBuilder.setAccount("9999");
		PositionFundingBuilder.setHrPositionId("TST-PSTNID");
		PositionFundingBuilder.setAmount(new BigDecimal(100));
		PositionFundingBuilder.setPmPositionFunctionId("TST-PSTNFUNDING");
		PositionFundingBuilder.setChart("MC");
		PositionFundingBuilder.setVersionNumber(1L);
		PositionFundingBuilder.setEffectiveLocalDateOfOwner(currentTime);
		PositionFundingBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		PositionFundingBuilder.setObjectCode("9000");
		PositionFundingBuilder.setPercent(new BigDecimal(100));
		testPositionFundingBos.put(PositionFundingBuilder.getAccount(), PositionFundingBuilder.build());
	}
	
	@Test
    public void testNotEqualsWithGroup() {
		PositionFunding immutable = PositionFundingBoTest.getPositionFunding("9999");
		PositionFundingBo bo = PositionFundingBo.from(immutable);
		PositionBo positionBo = new PositionBo();
		positionBo.setEffectiveLocalDate(currentTime);
		bo.setOwner(positionBo);
		bo.setBusinessObjectService(businessObjectService);
        Assert.assertFalse(bo.equals(immutable));
        Assert.assertFalse(immutable.equals(bo));
        Assert.assertEquals(immutable, PositionFundingBo.to(bo));
    }
	
	@Before
    public void setup() throws Exception {
    	
    	Account account = new Account();
    	account.setAccountNumber("9999");
    	account.setChartOfAccountsCode("MC");
    	account.setActive(true);
    	
    	businessObjectService = mock(BusinessObjectService.class);
        {
            when( businessObjectService.findByPrimaryKey(Mockito.any(Class.class), Mockito.anyMap())).thenReturn(account);
        }
    }
	
    public static PositionFunding getPositionFunding(String account) {
        return testPositionFundingBos.get(account);
    }
}
