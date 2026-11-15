package com.aricsso.contact;

import jakarta.validation.Valid;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    private final ContactMailer contactMailer;

    public ContactController(ContactMailer contactMailer) {
        this.contactMailer = contactMailer;
    }

    @PostMapping(path = "/api/contact")
    public String submit(@Valid @ModelAttribute ContactForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/contact.html?status=error";
        }

        try {
            contactMailer.send(form);
            return "redirect:/contact.html?status=ok";
        } catch (MailException ex) {
            return "redirect:/contact.html?status=error";
        }
    }
}
