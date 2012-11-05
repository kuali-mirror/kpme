/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.workflow.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class TimesheetDocumentHeaderDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements TimesheetDocumentHeaderDao {

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
    public TimesheetDocumentHeader getTimesheetDocumentHeader(String principalId, Date payBeginDate, Date payEndDate) {
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        crit.addEqualTo("endDate", payEndDate);
        crit.addEqualTo("beginDate", payBeginDate);

        return (TimesheetDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TimesheetDocumentHeader.class, crit));
    }

    /**
     * Document header IDs are ordered, so an ID less than the current will
     * always be previous to current.
     */
    public TimesheetDocumentHeader getPreviousDocumentHeader(String principalId, Date payBeginDate) {
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        // the pay begin date is the end date of the previous pay period
        crit.addEqualTo("endDate", payBeginDate);
        QueryByCriteria query = new QueryByCriteria(TimesheetDocumentHeader.class, crit);
        query.addOrderByDescending("documentId");
        query.setStartAtIndex(0);
        query.setEndAtIndex(1);

        return (TimesheetDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public TimesheetDocumentHeader getNextDocumentHeader(String principalId, Date payEndDate) {
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        // the pay end date is the begin date of the next pay period
        crit.addEqualTo("beginDate", payEndDate);
        QueryByCriteria query = new QueryByCriteria(TimesheetDocumentHeader.class, crit);
        query.setStartAtIndex(0);
        query.setEndAtIndex(1);

        return (TimesheetDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public List<TimesheetDocumentHeader> getDocumentHeaders(Date payBeginDate) {
        Criteria crit = new Criteria();
        List<TimesheetDocumentHeader> lstDocumentHeaders = new ArrayList<TimesheetDocumentHeader>();

        crit.addEqualTo("beginDate", payBeginDate);
        QueryByCriteria query = new QueryByCriteria(TimesheetDocumentHeader.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
            lstDocumentHeaders.addAll(c);
        }
        
        return lstDocumentHeaders;
    }
    
    @Override
    public List<TimesheetDocumentHeader> getDocumentHeaders(Date payBeginDate, Date payEndDate) {
        Criteria crit = new Criteria();
        List<TimesheetDocumentHeader> lstDocumentHeaders = new ArrayList<TimesheetDocumentHeader>();
        
        crit.addEqualTo("payEndDate", payEndDate);
        crit.addEqualTo("payBeginDate", payBeginDate);
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
	     
	     try {
	    	 crit.addEqualTo("principalId", principalId);
	    	 DateFormat df = new SimpleDateFormat("yyyy");
	    	 java.util.Date cYear = df.parse(year);
	    	 String nextYear = Integer.toString((Integer.parseInt(year) + 1));
	    	 java.util.Date nYear = df.parse(nextYear);
	    	 
			crit.addGreaterOrEqualThan("beginDate", cYear);
		    crit.addLessThan("beginDate", nYear );
		    QueryByCriteria query = new QueryByCriteria(TimesheetDocumentHeader.class, crit);
		    Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		    if (c != null) {
		        lstDocumentHeaders.addAll(c);
			}
		  } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }
		  return lstDocumentHeaders;
   }
   
   public TimesheetDocumentHeader getDocumentHeaderForDate(String principalId, Date asOfDate) {
	   Criteria crit = new Criteria();
       crit.addEqualTo("principalId", principalId);
       crit.addLessOrEqualThan("beginDate", asOfDate);
       crit.addGreaterOrEqualThan("endDate", asOfDate);
       
       QueryByCriteria query = new QueryByCriteria(TimesheetDocumentHeader.class, crit);

       return (TimesheetDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(query); 
   }
}
