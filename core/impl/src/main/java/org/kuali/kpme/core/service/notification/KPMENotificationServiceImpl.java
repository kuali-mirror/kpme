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
package org.kuali.kpme.core.service.notification;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.mail.EmailBody;
import org.kuali.rice.core.api.mail.EmailFrom;
import org.kuali.rice.core.api.mail.EmailSubject;
import org.kuali.rice.core.api.mail.EmailTo;
import org.kuali.rice.core.api.mail.Mailer;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.krad.util.KRADConstants;

public class KPMENotificationServiceImpl implements KPMENotificationService {
	
	private static final Logger LOG = Logger.getLogger(KPMENotificationServiceImpl.class);
	
	private static final String KPME_NOTIFICATIONS_ENABLED = "kpme.notifications.enabled";
    private static final String DEFAULT_EMAIL_FROM_ADDRESS = "admin@localhost";
	
	private Mailer mailer;
	private ParameterService parameterService;
	private PersonService personService;

	@Override
	public void sendNotification(String subject, String message, String... principalIds) {
		if (sendEmailNotification()) {
			for (String principalId : principalIds) {
				Person person = getPersonService().getPerson(principalId);
				
				if (person != null && StringUtils.isNotBlank(person.getEmailAddressUnmasked())) {
					EmailFrom emailFrom = new EmailFrom(getApplicationEmailAddress());
					EmailTo emailTo = new EmailTo(person.getEmailAddressUnmasked());
					EmailSubject emailSubject = new EmailSubject(subject);
					EmailBody emailBody = new EmailBody(message);
			        
					try {
						getMailer().sendEmail(emailFrom, emailTo, emailSubject, emailBody, false);
					} catch (RuntimeException re) {
						LOG.error("Email message sending failure:", re);
					}
				} else {
					if (person == null) {
						LOG.warn("Could not send message to principalId " + principalId + " because the person entity does not exist");
					} else {
						LOG.warn("Could not send message to principalId " + principalId + " because the person entity does not have an email address");
					}
				}
			}
		} else {
			LOG.info("Not sending message due to config parameter " + KPME_NOTIFICATIONS_ENABLED + " indicating emails should not be sent");
		}
	}
	
    private boolean sendEmailNotification() {
        return ConfigContext.getCurrentContextConfig().getBooleanProperty(KPME_NOTIFICATIONS_ENABLED, false);
    }
	
    private String getApplicationEmailAddress() {
        String fromAddress = getParameterService().getParameterValueAsString(KewApiConstants.KEW_NAMESPACE, KRADConstants.DetailTypes.MAILER_DETAIL_TYPE, 
        		KewApiConstants.EMAIL_REMINDER_FROM_ADDRESS);
        if (StringUtils.isEmpty(fromAddress)) {
            fromAddress = DEFAULT_EMAIL_FROM_ADDRESS;
        }
        return fromAddress;
    }

	public Mailer getMailer() {
		return mailer;
	}

	public void setMailer(Mailer mailer) {
		this.mailer = mailer;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

}