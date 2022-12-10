import java.util.Comparator;

public class ContactComparatorDate implements Comparator<Contact> {
    public int compare(Contact contact1, Contact contact2) {
        int result = contact1.getDate().compareTo(contact2.getDate());
        return result;
    }
}