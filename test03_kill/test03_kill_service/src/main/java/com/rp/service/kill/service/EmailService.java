package com.rp.service.kill.service;

import com.rp.service.kill.dto.MailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Service
@Slf4j
public class EmailService {


    //ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Async
    public void sendSimpleEmail(MailDto mailDto){

        try {
            SimpleMailMessage smm = new SimpleMailMessage();
            smm.setFrom(env.getProperty("mail.send.from"));
            smm.setTo(mailDto.getTos());
            smm.setSubject(mailDto.getSubject());
            smm.setText(mailDto.getContent());
            javaMailSender.send(smm);
            log.info("EmailService ,send emial success=========================");
        } catch (MailException e) {
            log.error("send error");
            e.printStackTrace();
        }
    }


    @Async
    public void sendHtmlEmail(MailDto mailDto){

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper smm = new MimeMessageHelper(mimeMessage,true,"utf-8");
            smm.setFrom(env.getProperty("mail.send.from"));
            smm.setTo(mailDto.getTos());
            smm.setSubject(mailDto.getSubject());
            smm.setText(mailDto.getContent(),true);
            javaMailSender.send(mimeMessage);
            log.info("EmailService ,send emialHtml success=========================");
        } catch (Exception e) {
            log.error("send error");
            e.printStackTrace();
        }
    }

}
