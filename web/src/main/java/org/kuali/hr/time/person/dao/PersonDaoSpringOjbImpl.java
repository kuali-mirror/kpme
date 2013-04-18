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
package org.kuali.hr.time.person.dao;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.person.TKPerson;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class PersonDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements PersonDao {

    private static final Logger LOG = Logger.getLogger(PersonDaoSpringOjbImpl.class);

    public List<TKPerson> getPersonCollection(List<String> principalIds) {

        List<TKPerson> persons = new LinkedList<TKPerson>();

        for (String principalId : principalIds) {
            Person p = KimApiServiceLocator.getPersonService().getPerson(principalId);
            if (p != null) {
                TKPerson person = new TKPerson();
                person.setPrincipalId(p.getPrincipalId());
                person.setFirstName(p.getFirstName());
                person.setLastName(p.getLastName());
                person.setPrincipalName(p.getName());
                persons.add(person);
            }
        }
        return persons;
    }

}
