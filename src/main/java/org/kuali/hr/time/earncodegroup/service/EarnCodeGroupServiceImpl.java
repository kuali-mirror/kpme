/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.time.earncodegroup.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earncodegroup.EarnCodeGroup;
import org.kuali.hr.time.earncodegroup.EarnCodeGroupDefinition;
import org.kuali.hr.time.earncodegroup.dao.EarnCodeGroupDaoService;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EarnCodeGroupServiceImpl implements EarnCodeGroupService {
    private EarnCodeGroupDaoService earnCodeGroupDao;

    @Override
    public EarnCodeGroup getEarnCodeGroup(String earnCodeGroup, Date asOfDate) {
        return earnCodeGroupDao.getEarnCodeGroup(earnCodeGroup, asOfDate);
    }

    public EarnCodeGroupDaoService getEarnCodeGroupDao() {
        return earnCodeGroupDao;
    }

    public void setEarnCodeGroupDao(EarnCodeGroupDaoService earnCodeGroupDao) {
        this.earnCodeGroupDao = earnCodeGroupDao;
    }

    @Override
    public EarnCodeGroup getEarnCodeGroupSummaryForEarnCode(String earnCode, Date asOfDate) {
        return earnCodeGroupDao.getEarnCodeGroupSummaryForEarnCode(earnCode, asOfDate);
    }

    @Override
    public EarnCodeGroup getEarnCodeGroupForEarnCode(String earnCode, Date asOfDate) {
        return earnCodeGroupDao.getEarnCodeGroupForEarnCode(earnCode, asOfDate);
    }

    public Set<String> getEarnCodeListForEarnCodeGroup(String earnCodeGroup, Date asOfDate) {
        Set<String> earnCodes = new HashSet<String>();
        EarnCodeGroup earnGroupObj = earnCodeGroupDao.getEarnCodeGroup(earnCodeGroup, asOfDate);
        if ( earnGroupObj != null ) {
            for (EarnCodeGroupDefinition earnGroupDef : earnGroupObj.getEarnCodeGroups()) {
                if (!earnCodes.contains(earnGroupDef.getEarnCode())) {
                    earnCodes.add(earnGroupDef.getEarnCode());
                }
            }
        }
        return earnCodes;
    }

    @Override
    public EarnCodeGroup getEarnCodeGroup(String hrEarnCodeGroupId) {
        return earnCodeGroupDao.getEarnCodeGroup(hrEarnCodeGroupId);
    }
    
    @Override
    public List<String> warningTextFromEarnCodeGroupsOfDocument(TimesheetDocument timesheetDocument) {
    	 List<String> warningMessages = new ArrayList<String>();
	     List<TimeBlock> tbList = timesheetDocument.getTimeBlocks();
	     if (tbList.isEmpty()) {
	         return warningMessages;
	     }
	     
	     Set<String> aSet = new HashSet<String>();
	     for(TimeBlock tb : tbList) {
	    	EarnCodeGroup eg = this.getEarnCodeGroupForEarnCode(tb.getEarnCode(), tb.getBeginDate());
	    	if(eg != null && !StringUtils.isEmpty(eg.getWarningText())) {
	    		aSet.add(eg.getWarningText());
	    	}
	     }
	    warningMessages.addAll(aSet);
		return warningMessages;
    }
    @Override
    public int getEarnCodeGroupCount(String earnCodeGroup) {
    	return earnCodeGroupDao.getEarnCodeGroupCount(earnCodeGroup);
    }
    @Override
    public int getNewerEarnCodeGroupCount(String earnCodeGroup, Date effdt) {
    	return earnCodeGroupDao.getNewerEarnCodeGroupCount(earnCodeGroup, effdt);
    }
    
    @Override
    public List<EarnCode> getEarnCodeGroups(String earnCodeGroup, String descr, Date fromEffdt, Date toEffdt, String active, String showHist) {
    	return earnCodeGroupDao.getEarnCodeGroups(earnCodeGroup, descr, fromEffdt, toEffdt, active, showHist);
    }
}
