package com.lucaslearning.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.lucaslearning.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
