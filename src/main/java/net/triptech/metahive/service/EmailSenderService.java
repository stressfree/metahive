/*******************************************************************************
 * Copyright (c) 2012 David Harrison, Triptech Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     David Harrison, Triptech Ltd - initial API and implementation
 ******************************************************************************/
package net.triptech.metahive.service;

import java.io.File;
import java.net.URL;
import java.util.Calendar;
import java.util.TreeMap;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import net.triptech.metahive.ConvertHtmlToText;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


/**
 * The Class EmailSenderService.
 */
@Component
public class EmailSenderService {

    /** The logger. */
    private static Logger logger = Logger.getLogger(EmailSenderService.class);

    /** The java mail sender. */
    @Autowired
    private transient JavaMailSender mailSender;

    /**
     * Send an email message using the configured Spring sender. On success
     * record the sent message in the datastore for reporting purposes
     *
     * @param email the email
     * @param attachments the attachments
     * @throws ServiceException the service exception
     */
    public final void send(final SimpleMailMessage email,
            TreeMap<String, Object> attachments) throws ServiceException {

        // Check to see whether the required fields are set (to, from, message)
        if (email.getTo() == null) {
            throw new ServiceException("Error sending email: Recipient "
                    + "address required");
        }
        if (StringUtils.isBlank(email.getFrom())) {
            throw new ServiceException("Error sending email: Email requires "
                    + "a from address");
        }
        if (StringUtils.isBlank(email.getText())) {
            throw new ServiceException("Error sending email: No email "
                    + "message specified");
        }
        if (mailSender == null) {
            throw new ServiceException("The JavaMail sender has not "
                    + "been configured");
        }

        // Prepare the email message
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        boolean htmlMessage = false;
        if (StringUtils.containsIgnoreCase(email.getText(), "<html")) {
            htmlMessage = true;
            try {
                helper = new MimeMessageHelper(message, true, "UTF-8");
            } catch (MessagingException me) {
                throw new ServiceException("Error preparing email for sending: "
                        + me.getMessage());
            }
        } else {
            helper = new MimeMessageHelper(message);
        }

        try {
            helper.setTo(email.getTo());
            helper.setFrom(email.getFrom());
            helper.setSubject(email.getSubject());

            if (email.getCc() != null) {
                helper.setCc(email.getCc());
            }
            if (email.getBcc() != null) {
                helper.setBcc(email.getBcc());
            }

            if (htmlMessage) {
                String plainText = email.getText();
                try {
                    ConvertHtmlToText htmlToText = new ConvertHtmlToText();
                    plainText = htmlToText.convert(email.getText());
                } catch (Exception e) {
                    logger.error("Error converting HTML to plain text: "
                            + e.getMessage());
                }
                helper.setText(plainText, email.getText());
            } else {
                helper.setText(email.getText());
            }

            if (email.getSentDate() != null) {
                helper.setSentDate(email.getSentDate());
            } else {
                helper.setSentDate(Calendar.getInstance().getTime());
            }

        } catch (MessagingException me) {
            throw new ServiceException("Error preparing email for sending: "
                    + me.getMessage());
        }

        // Append any attachments (if an HTML email)
        if (htmlMessage && attachments != null) {
            for (String id : attachments.keySet()) {
                Object reference = attachments.get(id);

                if (reference instanceof File) {
                    try {
                        FileSystemResource res = new FileSystemResource(
                                (File) reference);
                        helper.addInline(id, res);
                    } catch (MessagingException me) {
                        logger.error("Error appending File attachment: "
                                + me.getMessage());
                    }
                }
                if (reference instanceof URL) {
                    try {
                        UrlResource res = new UrlResource((URL) reference);
                        helper.addInline(id, res);
                    } catch (MessagingException me) {
                        logger.error("Error appending URL attachment: "
                                + me.getMessage());
                    }
                }
            }
        }

        // Send the email message
        try {
            mailSender.send(message);
        } catch (MailException me) {
            logger.error("Error sending email: " + me.getMessage());
            throw new ServiceException("Error sending email: " + me.getMessage());
        }
    }

}
