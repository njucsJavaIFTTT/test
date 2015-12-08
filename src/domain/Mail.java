package domain;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/* �����ʼ�ʵ���� */
public class Mail {
    private MimeMessage mimeMsg;
    private Session session;
    private Properties props;
    private String username;//�û���
    private String password;//����
    private Multipart mp;
    
    public Mail(){};
    
    public Mail(String smtp) {
        setSmtpHost(smtp);
        createMimeMessage();
    } 

    /* ���÷��������˺ŵ��û��������� */
    public void setNamePass(String name, String pass) {
        username = name;
        password = pass;
    }
    
    public void setSmtpHost(String hostName) {
        System.out.println("����ϵͳ���ԣ�mail.smtp.host=" + hostName);
        if (props == null) {
            props = System.getProperties();
        }
        props.put("mail.smtp.host", hostName);
    }
    
    public boolean createMimeMessage() {
        try {
            System.out.println("׼����ȡ�ʼ��Ự����");
            session = Session.getDefaultInstance(props, null);
        } catch (Exception e) {
            System.out.println("��ȡ�ʼ��Ự����" + e);
            return false;
        }
        System.out.println("׼������MIME�ʼ�����");
        try {
            mimeMsg = new MimeMessage(session);
            mp = new MimeMultipart();
            return true;
        } catch (Exception e) {
            System.out.println("����MIME�ʼ�����ʧ�ܣ�" + e);
            return false;
        }
    }
    
    /*����SMTP�Ƿ���Ҫ��֤*/
    public void setNeedAuth(boolean need) {
        System.out.println("����smtp�����֤��mail.smtp.auth = " + need);
        if (props == null)
        	props = System.getProperties();
        if (need)
            props.put("mail.smtp.auth", "true");
        else
            props.put("mail.smtp.auth", "false");
    }

    /*�����ʼ�����*/
    public boolean setSubject(String mailSubject) {
        System.out.println("�����ʼ����⣡");
        try {
        	mimeMsg.setSubject(mailSubject);
            return true;
        } catch (Exception e) {
            System.err.println("�����ʼ����ⷢ������");
            return false;
        }
    }
    
    /*�����ʼ�����*/
    public boolean setBody(String mailBody) {
        try {
        	BodyPart bp = new MimeBodyPart();
            bp.setContent("" + mailBody, "text/html;charset=GBK");
            mp.addBodyPart(bp);
            return true;
        } catch (Exception e) {
            System.err.println("�����ʼ�����ʱ��������" + e);
            return false;
        }
    }

    /*���÷�����*/
    public boolean setFrom(String from) {
        System.out.println("���÷����ˣ�");
        try {
            mimeMsg.setFrom(new InternetAddress(from)); //������
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*����������*/
    public boolean setTo(String to) {
        if (to == null)
        	return false;
        System.out.println("���������ˣ�");
        try {
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*���峭����*/
    public boolean setCopyTo(String copyto) {
        if (copyto == null)
            return false;
        try {
            mimeMsg.setRecipients(Message.RecipientType.CC, 
            		(Address[])InternetAddress.parse(copyto));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*�����ʼ�ģ��*/
    public boolean sendOut() {
        try {
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            System.out.println("�ʼ�������....");
            Session mailSession = Session.getInstance(props, null);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect((String) props.get("mail.smtp.host"), username, password);
            transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
            System.out.println("���ͳɹ���");
            transport.close();
            return true;
        } catch (Exception e) {
            System.err.println("�ʼ�����ʧ�ܣ�" + e);
            return false;
        }
    }

    /*����sendOut������ɷ���*/
    public static boolean sendAndCc(String smtp, String from, String to, String copyto,
        String subject, String content, String username, String password) {
        Mail theMail = new Mail(smtp);
        theMail.setNeedAuth(true); // ��֤
        if (!theMail.setSubject(subject))
            return false;
        if (!theMail.setBody(content))
            return false;
        if (!theMail.setTo(to))
            return false;
        if (!theMail.setCopyTo(copyto))
            return false;
        if (!theMail.setFrom(from))
            return false;
        theMail.setNamePass(username, password);
        if (!theMail.sendOut())
            return false;
        return true;
    }

}

