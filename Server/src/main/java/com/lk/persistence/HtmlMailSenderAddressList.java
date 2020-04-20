package com.lk.persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;


public class HtmlMailSenderAddressList{
    private String SMTP_AUTH_USER = "ksu.priem@yandex.ru";
    private String SMTP_AUTH_PWD = "YnqsKsu-Priem#44";
    private String SMTP_HOST_NAME = "smtp.yandex.ru";
    private int  SMTP_HOST_PORT = 465;
    private Properties properties;

    private Logger logger = LoggerFactory.getLogger(HtmlMailSenderAddressList.class);

    public HtmlMailSenderAddressList() {
        /*properties = new Properties(); //для auth = true
        properties.put("mail.smtp.auth", "true"); //для auth = true
        properties.put("mail.debug", "true"); //для auth = true
        properties.put("mail.smtp.starttls.enable", "true"); //для auth = true
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.port", "587");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", SMTP_HOST_NAME);*/
        properties = new Properties();
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.host", "smtp.yandex.ru");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "true"); //для auth = true
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl", "true");
        properties.put("mail.smtp.starttls.enable", "true"); //для auth = true
        properties.put("mail.smtp.socketFactory.port", "465");
    }

    public void asyncSend(String subject, String message,  String fromEmail, List<String> addressList, final Message.RecipientType recipientType)throws MessagingException{
        final String mailSubject = subject;
        final String mailText = message;
        final List<String> mailAddressList = addressList;

        new Thread(new Runnable() {
            public void run() {
                try {
                    send(mailSubject, mailText,  mailAddressList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean send(String subject, String messageText,  List<String> emailList)  {
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
                    }
                });

        try {
           /* Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("danil51510@yandex.ru"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("danila7417@yandex.ru"));
            message.setSubject("Subject");
            message.setText("Text");
            Transport transport = session.getTransport("smtps");
            transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();*/

            MimeMessage message = new MimeMessage(session );
            Transport transport = session.getTransport("smtps");
            Message.RecipientType recipientType = Message.RecipientType.TO;
            //от кого
            message.setFrom(new InternetAddress(SMTP_AUTH_USER));
            //кому


            if (emailList!=null && emailList.size()>0) {
                message.setRecipients(recipientType, InternetAddress.parse(emailList.get(0)));
                for(int i=1; i<emailList.size(); i++){
                    message.addRecipients(recipientType, InternetAddress.parse(emailList.get(i)));
                    //message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailList.get(i), true ));
                }

                //тема сообщения
                message.setSubject(subject, "UTF-8");

                ////текст - если без картинки
                //message.setContent(messageText, "text/html; charset=UTF-8");

                // This mail has 2 part, the BODY and the embedded image
                MimeMultipart multipart = new MimeMultipart("related");

                // first part (the html)
                BodyPart messageBodyPartText = new MimeBodyPart();
                messageBodyPartText.setContent(messageText, "text/html; charset=UTF-8");
                multipart.addBodyPart(messageBodyPartText);
                message.setContent(multipart);
                //отправляем сообщение

                transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD); //для auth = true
                transport.sendMessage(message, message.getAllRecipients()); //для auth = true
                transport.close(); //для auth = true
                return true;
            }
        } catch (MessagingException ex) {
            logger.error("Exception in HtmlMailSenderAddressList class, send function is: " + ex.getLocalizedMessage(), ex);
         } catch (Exception ex) {
            logger.error("Exception in HtmlMailSenderAddressList class, send function is: " + ex.getLocalizedMessage(), ex);
        }
        return false;
    }



    public Session getSession() {
        return Session.getDefaultInstance(properties);
    }

    public static String cyr2latCh(char ch){
        switch (ch){
            case 'А': return "A";
            case 'Б': return "B";
            case 'В': return "V";
            case 'Г': return "G";
            case 'Д': return "D";
            case 'Е': return "E";
            case 'Ё': return "JE";
            case 'Ж': return "ZH";
            case 'З': return "Z";
            case 'И': return "I";
            case 'Й': return "Y";
            case 'К': return "K";
            case 'Л': return "L";
            case 'М': return "M";
            case 'Н': return "N";
            case 'О': return "O";
            case 'П': return "P";
            case 'Р': return "R";
            case 'С': return "S";
            case 'Т': return "T";
            case 'У': return "U";
            case 'Ф': return "F";
            case 'Х': return "KH";
            case 'Ц': return "C";
            case 'Ч': return "CH";
            case 'Ш': return "SH";
            case 'Щ': return "JSH";
            case 'Ъ': return "HH";
            case 'Ы': return "IH";
            case 'Ь': return "JH";
            case 'Э': return "EH";
            case 'Ю': return "JU";
            case 'Я': return "JA";
            case 'а': return "a";
            case 'б': return "b";
            case 'в': return "v";
            case 'г': return "g";
            case 'д': return "d";
            case 'е': return "e";
            case 'ё': return "je";
            case 'ж': return "zh";
            case 'з': return "z";
            case 'и': return "i";
            case 'й': return "y";
            case 'к': return "k";
            case 'л': return "l";
            case 'м': return "m";
            case 'н': return "n";
            case 'о': return "o";
            case 'п': return "p";
            case 'р': return "r";
            case 'с': return "s";
            case 'т': return "t";
            case 'у': return "u";
            case 'ф': return "f";
            case 'х': return "kh";
            case 'ц': return "c";
            case 'ч': return "ch";
            case 'ш': return "sh";
            case 'щ': return "jsh";
            case 'ъ': return "hh";
            case 'ы': return "ih";
            case 'ь': return "jh";
            case 'э': return "eh";
            case 'ю': return "ju";
            case 'я': return "ja";
            default: return String.valueOf(ch);
        }
    }

    public static String cyr2lat(String s){
        StringBuilder sb = new StringBuilder(s.length()*2);
        for(char ch: s.toCharArray()){
            sb.append(cyr2latCh(ch));
        }
        return sb.toString();
    }
}
