package gambol.examples.qqmail;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Created by maya on 17/7/25.
 */
public class MailBean implements Serializable {
    // 发送方
    private String mailFrom;
    // 收件人
    private List<String> mailTo;
    // 抄送
    private List<String> mailCC;
    // 主题
    private String subject;
    // 内容
    private String content;
    // 附件
    private List<File> attachmentList;

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public List<String> getMailTo() {
        return mailTo;
    }

    public void setMailTo(List<String> mailTo) {
        this.mailTo = mailTo;
    }

    public List<String> getMailCC() {
        return mailCC;
    }

    public void setMailCC(List<String> mailCC) {
        this.mailCC = mailCC;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<File> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<File> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
