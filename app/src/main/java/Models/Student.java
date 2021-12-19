package Models;

public class Student {

   private String sname=" ";
   private String sid=" ";
   private String classes=" ";
   private String spass=" ";
   private String semail=" ";

    public Student(String sname, String sid, String classes, String spass,String semail) {
        this.sname = sname;
        this.sid = sid;
        this.classes = classes;
        this.spass = spass;
        this.semail=semail;
    }

    public String getSemail() {
        return semail;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getSpass() {
        return spass;
    }

    public void setSpass(String spass) {
        this.spass = spass;
    }
}
