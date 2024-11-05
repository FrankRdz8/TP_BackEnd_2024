package com.example.notificaciones.Servicios;

import com.example.notificaciones.Entidades.Notificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    private JavaMailSender mailSender;

    @Autowired
    public NotificacionService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        // Crear el mensaje
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinatario);
        message.setSubject(asunto);
        message.setText(cuerpo);

        // Enviar el mensaje
        try {
            mailSender.send(message);
        } catch (MailException e) {
            // Manejo de errores en el envío del correo
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }
}
