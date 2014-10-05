package org.kuali.kpme.edo.notification.service;

//import edu.iu.uis.sit.util.mail.AuthenticatedMailer;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.core.api.config.property.ConfigContext;

import org.apache.log4j.Logger;

import javax.mail.MessagingException;

public class EdoNotificationServiceImpl implements EdoNotificationService {
    private static final Logger LOG = Logger.getLogger(EdoNotificationServiceImpl.class);

    @Override
    public void sendMail(String toAddress, String fromAddress, String subject, String content) {
        if (StringUtils.isBlank(toAddress) || StringUtils.isBlank(fromAddress) || StringUtils.isBlank(content)) {
            LOG.error("EdoNotificationService.sendMail: Invalid content when attempting to send mail");
            return;
        }

        String user = ConfigContext.getCurrentContextConfig().getProperty(EdoConstants.MAIL_USER);
        String pwd = ConfigContext.getCurrentContextConfig().getProperty(EdoConstants.MAIL_PWD);

        LOG.info("EdoNotificationService.sendMail: User " + user);
        /*AuthenticatedMailer ml = new AuthenticatedMailer(fromAddress, user, pwd);
        try {
            //check for comma delimited list
            String[] toAddresses = StringUtils.split(toAddress, ',');
            for (String addr : toAddresses) {
                ml.sendMessage(addr, subject, content);
                LOG.info("EdoNotificationService.sendMail: Mail sent to [" + addr + "]");
            }
        } catch (MessagingException e) {
            LOG.error("EdoNotificationService.sendMail: Error when sending notification to " + toAddress, e);
        }*/
    }
}
