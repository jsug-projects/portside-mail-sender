package jsug.portside.mailsender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MailSenderSink {
	private final MailSender mailSender;
	private final PortsideMailProperties portsideMailProperties;
	private static final Logger log = LoggerFactory.getLogger(MailSenderSink.class);

	public MailSenderSink(MailSender mailSender, PortsideMailProperties portsideMailProperties) {
		this.mailSender = mailSender;
		this.portsideMailProperties = portsideMailProperties;
	}

	@StreamListener(Sink.INPUT)
	public void sendMail(@Payload MailData mailData) {
		log.info("Received {}", mailData);
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom(portsideMailProperties.getFrom());
		email.setSubject(mailData.subject);
		email.setText(mailData.body);
		email.setTo(mailData.mailTo);
		mailSender.send(email);
	}
}
