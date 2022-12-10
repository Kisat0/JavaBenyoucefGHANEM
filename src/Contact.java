import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

public class Contact implements Comparable<Contact> {
    public static final String SEPARATEUR = ";";

    private String nom;
    private String prenom;
    private String tel;
    private Date date;
    private String mail;

    Contact(String nom, String prenom, String tel, String date, String mail) throws ParseException {
        this.nom = nom;
        this.prenom = prenom;
        Pattern patT = Pattern.compile("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$");
        Matcher testT = patT.matcher(tel);
        if (testT.matches()) {
            this.tel = tel;
        } else {
            throw new ParseException("Format du numéro incorrect.", 0);
        }
        this.tel = tel;
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        this.date = dtf.parse(date);
        Pattern patm = Pattern.compile("^[a-zA-Z0-9_.-]+@{1}[a-zA-Z0-9_.-]{2,}\\.[a-zA-Z.]{2,10}$");
        Matcher testm = patm.matcher(mail);
        if (testm.matches()) {
            this.mail = mail;
        } else {
            throw new ParseException("Format du mail incorrect.", 0);
        }
        this.mail = mail;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getMail() {
        return this.mail;
    }

    public String getTel() {
        return this.tel;
    }

    public Date getDate() {
        return this.date;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String nom) {
        this.nom = nom;
    }

    public static void enregistrer(Contact contact) {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", true)));
            pw.println(contact.toString());
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void enregistrerTous(List<Contact> contacts) throws IOException {

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("contacts.csv", false)));
        try {
            for (Contact contact : contacts) {
                pw.println(contact.toString());
            }
        } finally {
            pw.close();
        }
    }

    public void setTel(String tel) throws ParseException {
        Pattern pat = Pattern.compile("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$");
        Matcher test = pat.matcher(tel);
        if (test.matches()) {
            this.tel = tel;
        } else {
            throw new ParseException("Format du numéro incorrect.", 0);
        }
    }

    public void setDate(String date) throws ParseException {
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        this.date = dtf.parse(date);
    }

    public void setMail(String mail) throws ParseException {
        Pattern pat = Pattern.compile("^[a-zA-Z0-9_.-]+@{1}[a-zA-Z0-9_.-]{2,}\\.[a-zA-Z.]{2,10}$");
        Matcher test = pat.matcher(mail);
        if (test.matches()) {
            this.mail = mail;
        } else {
            throw new ParseException("Format du mail incorrect.", 0);
        }
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append(getNom());
        build.append(SEPARATEUR);
        build.append(getPrenom());
        build.append(SEPARATEUR);
        build.append(getMail());
        build.append(SEPARATEUR);
        build.append(getTel());
        build.append(SEPARATEUR);
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        build.append(dtf.format(getDate()));
        return build.toString();
    }

    @Override
    public int compareTo(Contact o) {
        if (this.getNom().equals(o.getNom())) {
            return this.getPrenom().compareTo(o.getPrenom());
        }
        return this.getNom().compareTo(o.getNom());
    }
}
