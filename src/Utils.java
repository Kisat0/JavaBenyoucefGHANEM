
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Stream;

public class Utils {
    public static final String SEPARATEUR = ";";

    public static int displayMenu() {
        System.out.println("------✨ MENU ✨------");
        System.out.println("1- Ajouter un nouveau contact 👮");
        System.out.println("2- Lister tout les contacts 👨‍👦‍👦");
        System.out.println("3- Modifier un contact 🖋️");
        System.out.println("4- Supprimer un contact ❌");
        System.out.println("5- Trier les contacts par nom 📈");
        System.out.println("6- Trier les contacts par date 📉");
        System.out.println("7- Rechercher les contacts par nom 🔎");
        System.out.println("8- Quitter 🥹");
        System.out.println("---------------------");

        String choix = App.getScanner().next();
        int value;

        if (Handler.isNumeric(choix))
            value = Integer.parseInt(choix);
        else
            value = -1;

        return value;
    }

    public static void addContactMenu() throws ParseException, IOException {
        String nom;
        String prenom;
        String tel;
        String date;
        String mail;

        ArrayList<Contact> contacts = lister();

        System.out.println("Quel est le nom de ce contact ?");
        nom = App.getScanner().next();

        System.out.println("Quel est le prenom de ce contact ?");
        prenom = App.getScanner().next();

        System.out.println("Quel est le numéro de ce contact ?");
        tel = App.getScanner().next();

        System.out.println("Quel est le mail de ce contact ?");
        mail = App.getScanner().next();

        System.out.println("Quel est le date de naissance de ce contact ?");
        date = App.getScanner().next();

        try {
            Contact contact = new Contact(nom, prenom, tel, date, mail);
            Contact.enregistrer(contact);
            contacts.add(contact);

        } catch (Exception e) {
            System.out.println("ERREUR LORS DES INPUTS, FORMAT INCORRECT");
            addContactMenu();

        }
    }

    public static void listContact() throws IOException, ParseException {
        try {
            ArrayList<Contact> contacts = lister();
            if (contacts.size() == 0) {
                System.out.println("Il n'y a aucun contact pour le moment");
            } else {
                for (Contact contact : contacts) {
                    System.out.println(beautifulToString(contact));

                }
            }
        } catch (Exception e) {
            System.out.println("Fichier CSV (contacts.csv) introuvable ou erreur de lors de la lecture");
        }

    }

    public static void editContact() throws ParseException, IOException {
        ArrayList<Contact> contacts = lister();

        if (contacts.size() == 0) {
            System.out.println("Il n'y a aucun contact pour le moment");

        } else {
            for (Contact contact : contacts) {
                System.out.println(beautifulToString(contact));
            }
        }
        System.out.println("Quel est le nom et du contact que vous souhaitez modifier ?");
        String nom = App.getScanner().next();
        System.out.println("Quel est le prenom et du contact que vous souhaitez modifier ?");
        String prenom = App.getScanner().next();

        for (Contact contact : contacts) {
            if (contact.getNom().equals(nom) && contact.getPrenom().equals(prenom)) {
                int choix = editMenu(contact);
                edit(choix, contact);
                Contact.enregistrerTous(contacts);
                System.out.println("MODIFICATIONS TERMINEES");

            }
        }
    }

    public static int editMenu(Contact contact) {
        System.out.println("Que souhaitez-vous editer ? ");
        System.out.println("Contact: " + contact.getNom() + " " + contact.getPrenom());
        System.out.println("1-nom");
        System.out.println("2-prenom");
        System.out.println("3-mail");
        System.out.println("4-date");
        System.out.println("5-tel");
        System.out.println("6-quitter");

        while (true) {
            int value;
            String choix = App.getScanner().next();

            if (Handler.isNumeric(choix))
                value = Integer.parseInt(choix);
            else
                value = -1;

            if (value < 1 || value > 6) {
                System.out.println("Que souhaitez-vous editer ? ");
                System.out.println("Contact: " + contact.getNom() + " " + contact.getPrenom());
                System.out.println("1-nom");
                System.out.println("2-prenom");
                System.out.println("3-mail");
                System.out.println("4-date");
                System.out.println("5-tel");
                System.out.println("6-quitter");
            }
            return value;
        }
    }

    public static void edit(int choix, Contact contact) throws ParseException {
        switch (choix) {
            case 1:
                System.out.println("Quel est le nouveau nom ?");
                String newNom = App.getScanner().next();
                contact.setNom(newNom);
                break;
            case 2:
                System.out.println("Quel est le nouveau prenom ?");
                String newPrenom = App.getScanner().next();
                contact.setPrenom(newPrenom);
                break;
            case 3:
                System.out.println("Quel est le nouveau mail ?");
                String newMail = App.getScanner().next();
                contact.setMail(newMail);
                break;
            case 4:
                System.out.println("Quel est la nouvelle date ?");
                String newDate = App.getScanner().next();
                contact.setMail(newDate);
                break;
            case 5:
                System.out.println("Quel est le nouveau numéro ?");
                String newTel = App.getScanner().next();
                contact.setTel(newTel);
                break;

            default:
                break;
        }
    }

    public static void deleteContact() throws ParseException, IOException {
        ArrayList<Contact> contacts = lister();

        if (contacts.size() == 0) {
            System.out.println("Il n'y a aucun contact pour le moment");

        } else {
            for (Contact contact : contacts) {
                System.out.println(beautifulToString(contact));

            }
        }
        System.out.println("Quel est le mail du contact que vous souhaitez supprimer ?");
        String mail = App.getScanner().next();

        try {
            Contact contactASuppr = null;
            for (Contact c : contacts) {
                if (c.getMail().equals(mail)) {
                    contactASuppr = c;
                }
            }
            if (contactASuppr != null) {
                contacts.remove(contactASuppr);
                Contact.enregistrerTous(contacts);
                System.out.println("le contact a été supprimer");
            } else {
                System.out.println("Aucun contact n'a ce mail");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void searchContact() throws IOException, ParseException {
        Stream<Contact> contacts = lister().stream();

        System.out.println("Quel est le nom et du contact que vous souhaitez chercher?");
        String nom = App.getScanner().next();

        Stream<Contact> searchResult = contacts.filter(e -> e.getNom().contains(nom));
        searchResult.forEach(c -> System.out.println(c.toString()));

    }

    public static ArrayList<Contact> lister() throws IOException, ParseException {
        ArrayList<Contact> contacts = new ArrayList<>();
        BufferedReader buf = new BufferedReader(new FileReader("contacts.csv"));
        try {
            String ligne = buf.readLine();
            while (ligne != null) {
                String[] tab = ligne.split(SEPARATEUR);
                Contact c = new Contact(tab[0], tab[1], tab[3], tab[4], tab[2]);
                contacts.add(c);
                ligne = buf.readLine();
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture sur le fichier");
        } finally {
            buf.close();
        }
        return contacts;

    }

    public static void sortByDate() throws IOException, ParseException {
        ArrayList<Contact> contacts = lister();

        Collections.sort(contacts, new ContactComparatorDate());

        for (Contact contact : contacts) {
            System.out.println(beautifulToString(contact));
        }
    }

    public static void sortByName() throws IOException, ParseException {
        ArrayList<Contact> contacts = lister();

        Collections.sort(contacts, new ContactComparator());

        for (Contact contact : contacts) {
            System.out.println(beautifulToString(contact));
        }
    }

    public static String beautifulToString(Contact contact) {
        return "Nom: " + contact.getNom() + "," + "\n" +
                "Prenom: " + contact.getPrenom() + "," + "\n" +
                "Mail: " + contact.getMail() + "," + "\n" +
                "Tel: " + contact.getTel() + "," + "\n" +
                "Date: " + contact.getDate() + "," + "\n";
    }
}
