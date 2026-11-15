package com.aricsso.contact;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactMailer {

    private final JavaMailSender mailSender;
    private final String recipient;
    private final String fromAddress;

    public ContactMailer(
            JavaMailSender mailSender,
            @Value("${contact.mail.to}") String recipient,
            @Value("${contact.mail.from}") String fromAddress
    ) {
        this.mailSender = mailSender;
        this.recipient = recipient;
        this.fromAddress = fromAddress;
    }

    public void send(ContactForm form) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setFrom(fromAddress);
        message.setReplyTo(cleanOneLine(form.getEmail()));
        message.setSubject("Aricsso Kids Contact Form Submission");
        message.setText(buildBody(form));
        mailSender.send(message);
    }

    private String buildBody(ContactForm form) {
        return "Name: " + cleanOneLine(form.getName()) + "\n"
                + "Email: " + cleanOneLine(form.getEmail()) + "\n"
                + "Phone: " + optionalText(form.getPhone()) + "\n"
                + "Source: " + optionalText(form.getSource()) + "\n\n"
                + "Feedback:\n" + optionalBlock(form.getFeedback()) + "\n\n"
                + "Request to add content:\n" + optionalBlock(form.getContentRequest()) + "\n\n"
                + "Support request:\n" + optionalBlock(form.getSupport());
    }

    private String cleanOneLine(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("\r", " ").replace("\n", " ").trim();
    }

    private String optionalText(String value) {
        String clean = cleanOneLine(value);
        return clean.isEmpty() ? "Not provided" : clean;
    }

    private String optionalBlock(String value) {
        if (value == null) {
            return "Not provided";
        }
        String clean = value.trim();
        return clean.isEmpty() ? "Not provided" : clean;
    }
}
