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
package org.kuali.kpme.tklm.time.timehourdetail.service;

import java.util.List;

import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetailService;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetailBo;
import org.kuali.kpme.tklm.time.timehourdetail.dao.TimeHourDetailDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class TimeHourDetailServiceImpl implements TimeHourDetailService {
    private static final ModelObjectUtils.Transformer<TimeHourDetailBo, TimeHourDetail> toTimeHourDetail =
            new ModelObjectUtils.Transformer<TimeHourDetailBo, TimeHourDetail>() {
                public TimeHourDetail transform(TimeHourDetailBo input) {
                    return TimeHourDetailBo.to(input);
                };
            };
    private static final ModelObjectUtils.Transformer<TimeHourDetail, TimeHourDetailBo> toTimeHourDetailBo =
            new ModelObjectUtils.Transformer<TimeHourDetail, TimeHourDetailBo>() {
                public TimeHourDetailBo transform(TimeHourDetail input) {
                    return TimeHourDetailBo.from(input);
                };
            };
	TimeHourDetailDao timeHourDetailDao;

	@Override
	public TimeHourDetail getTimeHourDetail(String timeHourDetailId) {
		return TimeHourDetailBo.to(getTimeHourDetailBo(timeHourDetailId));
	}

    protected TimeHourDetailBo getTimeHourDetailBo(String timeHourDetailId) {
        return timeHourDetailDao.getTimeHourDetail(timeHourDetailId);
    }

	@Override
	public TimeHourDetail saveTimeHourDetail(TimeBlock tb) {

		TimeHourDetailBo td = new TimeHourDetailBo();

		td.setTkTimeBlockId(tb.getTkTimeBlockId());
		td.setEarnCode(tb.getEarnCode());
		td.setHours(tb.getHours());
		td.setAmount(tb.getAmount());

		TimeHourDetailBo timeHourDetailBo = KRADServiceLocator.getBusinessObjectService().save(td);

		return TimeHourDetailBo.to(timeHourDetailBo);
	}

	public void setTimeHourDetailDao(TimeHourDetailDao timeHourDetailDao) {
		this.timeHourDetailDao = timeHourDetailDao;
	}

    @Override
	public List<TimeHourDetail> getTimeHourDetailsForTimeBlock(String timeBlockId) {
		return ModelObjectUtils.transform(this.timeHourDetailDao.getTimeHourDetailsForTimeBlock(timeBlockId), toTimeHourDetail);
	}

    public void removeTimeHourDetails(String timeBlockId) {
        this.timeHourDetailDao.remove(timeBlockId);
    }
    
    @Override
    public void removeTimeHourDetail(String timeHourDetailId) {
        timeHourDetailDao.removeById(timeHourDetailId);
    }
}
