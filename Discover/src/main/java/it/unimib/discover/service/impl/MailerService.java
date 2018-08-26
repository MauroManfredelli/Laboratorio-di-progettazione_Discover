package it.unimib.discover.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import it.unimib.discover.configuration.MailerConfig;
import it.unimib.discover.entity.MyUserAccount;

@Service
public class MailerService {

	private final String DEFAULT_PREFIX_SUBJECT= "[DISCOVER] ";
	
	private final Logger logger = Logger.getLogger(MailerService.class);
	
	@Autowired private MailerConfig mailerConfig;
	
	/**
	 * Send HMTL email
	 * @param to
	 * @param subject
	 * @param text
	 */
	private void sendEmail(String to, String subject, String text){
		try {
			MimeMessage mimeMessage = mailerConfig.javaMailService().createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
			
			messageHelper.setTo(to);
			messageHelper.setSubject(DEFAULT_PREFIX_SUBJECT + subject);
			messageHelper.setText(text,true);
			
			if(mailerConfig.isEnable()){
				mailerConfig.javaMailService().send(mimeMessage);
			}
		} catch (MessagingException e) {
			logger.error("Error while sendEmail >>", e);
		}
	}

	public void registerUser(MyUserAccount user) {
		StringWriter stringWriter = new StringWriter();
		try {
			Template template = mailerConfig.velocityEngine().getTemplate("./email/templates/user.vm");
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("fullaName", user.getUserName());
			velocityContext.put("userId", user.getId() );
			velocityContext.put("name", user.getFirstName() );
			velocityContext.put("surname",user.getLastName() );
			velocityContext.put("email", user.getEmail() );
			
			template.merge(velocityContext, stringWriter);
		} catch (VelocityException | IOException e) {
			logger.error("Error while registerUser >>", e);
		}
		
		System.out.println(stringWriter.toString());
		
		sendEmail(user.getEmail(), MyUserAccount.class.getSimpleName(), stringWriter.toString());
		
	}
	
	private SimpleDateFormat getSimpleDateFormat(){
		return new SimpleDateFormat("dd/MM/yyyy HH:mm");
	}
}