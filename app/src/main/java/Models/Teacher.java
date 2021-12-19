package Models;

public class Teacher {

   private String tname,tid,subject,classes,tpass,temail;

    public Teacher(String tname, String tid, String subject, String classes, String tpass,String temail) {
        this.tname = tname;
        this.tid = tid;
        this.subject = subject;
        this.classes = classes;
        this.tpass = tpass;
        this.temail=temail;
    }

    public String getTemail() {
        return temail;
    }

    public void setTemail(String temail) {
        this.temail = temail;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getTpass() {
        return tpass;
    }

    public void setTpass(String tpass) {
        this.tpass = tpass;
    }
}
