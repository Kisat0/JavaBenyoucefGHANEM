import java.util.Comparator;

public class ContactComparator implements Comparator<Contact> {
    public int compare(Contact contact1, Contact contact2) {
        int result = contact1.getNom().compareTo(contact2.getNom());
        return result;
    }
}