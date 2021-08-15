package com.ventas.ventas.service;

import com.ventas.ventas.Configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private Configuracion configuracion = new Configuracion();

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail( String to, String subject, String body) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(configuracion.getTienda());
        mail.setTo(to);
        mail.setSubject("Compras " + configuracion.getTienda() + " mes de " + subject);
        mail.setText(body);

        javaMailSender.send(mail);
    }
}