package com.barberia.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;  //este es el que realmente envía el mensaje
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

//Este se encarga de enviar los correos electrónicos desde el sistema


@Service
public class CorreoService {

    private final JavaMailSender mailSender;

    public CorreoService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarCorreoHtml(String para, String asunto, String contenido) throws MessagingException { //envia un correo en formato html
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper correo = new MimeMessageHelper(mensaje, true);

        correo.setTo(para);               //este se utiliza para la confirmación de la cita y el recordatorio de la cita
        correo.setSubject(asunto);
        correo.setText(contenido, true);

        mailSender.send(mensaje);
    }
}
