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
package org.kuali.kpme.tklm.time.workflow.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class TimesheetDocumentHeaderDaoOjbImpl extends PlatformAwareDaoBaseOjb implements TimesheetDocumentHeaderDao {

    @Override
    public TimesheetDocumentHeader getTimesheetDocumentHeader(String documentId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("documentId", documentId);
        return (TimesheetDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TimesheetDocumentHeader.class, crit));
    }

    @Override
    public void saveOrUpdate(TimesheetDocumentHeader documentHeader) {
        if (documentHeader != null) {
            this.getPersistenceBrokerTemplate().store(documentHeader);
        }
    }
    
    public void deleteTimesheetHeader(String documentId){
        Criteria crit = new Criteria();
        crit.addEqualTo("documentId", documentId);
        this.getPersistenceBrokerTemplate().deleteByQuery(QueryFactory.newQuery(TimesheetDocumentHeader.class, crit));
    }

    @Override
    public TimesheetDocumentHeader getTimesheetDocumentHeader(String principalId, DateTime payBeginDate, DateTime payEndDate) {
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        crit.addEqualTo("endDate", payEndDate.toDate());
        crit.addEqualTo("beginDate", payBeginDate.toDate());

        return (TimesheetDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TimesheetDocumentHeader.class, crit));
    }

    /**
     * Document header IDs are ordered, so an ID less than the current will
     * always be previous to current.
     */
    public TimesheetDocumentHeader getPreviousDocumentHeader(String principalId, DateTime payBeginDate) {
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        // the pay begin date is the end date of the previous pay period
        crit.addEqualTo("endDate", payBeginDate.toDate());
        QueryByCriteria query = new QueryByCriteria(TimesheetDocumentHeader.class, crit);
        query.addOrderByDescending("documentId");
        query.setStartAtIndex(0);
        query.setEndAtIndex(1);

        return (TimesheetDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public TimesheetDocumentHeader getNextDocumentHeader(String principalId, DateTime payEndDate) {
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        // the pay end date is the begin date of the next pay period
        crit.addEqualTo("beginDate", payEndDate.toDate());
        QueryByCriteria query = new QueryByCriteria(TimesheetDocumentHeader.class, crit);
        query.setStartAtIndex(0);
        query.setEndAtIndex(1);

        return (TimesheetDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
    
    @Override
    public List<TimesheetDocumentHeader> getDocumentHeaders(DateTime payBeginDate, DateTime payEndDate) {
        Criteria crit = new Criteria();
        List<TimesheetDocumentHeader> lstDocumentHeaders = new ArrayList<TimesheetDocumentHeader>();
        
        crit.addEqualTo("endDate", payEndDate.toDate());
        crit.addEqualTo("beginDate", payBeginDate.toDate());
        QueryByCriteria query = new QueryByCriteria(TimesheetDocumentHeader.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
            lstDocumentHeaders.addAll(c);
        }
        
        return lstDocumentHeaders;
    }
    
    public List<TimesheetDocumentHeader> getDocumentHeadersForPrincipalId(String principalId) {
   	 	Criteria crit = new Criteria();
        List<TimesheetDocumentHeader> lstDocumentHeaders = new ArrayList<TimesheetDocumentHeader>();

        crit.addEqualTo("principalId", principalId);
        QueryByCriteria query = new QueryByCriteria(TimesheetDocumentHeader.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
            lstDocumentHeaders.addAll(c);
        }
        return lstDocumentHeaders;
   }
   
   public List<TimesheetDocumentHeader> getDocumentHeadersForYear(String principalId, String year) {
	   	 Criteria crit = new Criteria();
	     List<TimesheetDocumentHeader> lstDocumentHeaders = new ArrayList<TimesheetDocumentHeader>();
	     
    	 crit.addEqualTo("principalId", principalId);
    	 DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy");
    	 LocalDate currentYear = formatter.parseLocalDate(year);
    	 LocalDate nextYear = currentYear.plusYears(1);
    	 crit.addGreaterOrEqualThan("beginDate", currentYear.toDate());
    	 crit.addLessThan("beginDate", nextYear.toDate());
    	 
    	 QueryByCriteria query = new QueryByCriteria(TimesheetDocumentHeader.class, crit);
    	 Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
    	 if (c != null) {
    		 lstDocumentHeaders.addAll(c);
    	 }
    	 
    	 return lstDocumentHeaders;
   }
   
   public TimesheetDocumentHeader getDocumentHeaderForDate(String principalId, DateTime asOfDate) {
	   Criteria crit = new Criteria();
       crit.addEqualTo("principalId", principalId);
       crit.addLessOrEqualThan("beginDate", asOfDate.toDate());
       crit.addGreaterOrEqualThan("endDate", asOfDate.toDate());
       
       QueryByCriteria query = new QueryByCriteria(TimesheetDocumentHeader.class, crit);

       return (TimesheetDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(query); 
   }
}
