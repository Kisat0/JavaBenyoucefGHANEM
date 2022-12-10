import java.util.Scanner;

public class App {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        while (true) {
            int choix = Utils.displayMenu();
            Utils.lister();

            switch (choix) {
                case 1:
                    Utils.addContactMenu();
                    break;
                case 2:
                    Utils.listContact();
                    break;
                case 3:
                    Utils.editContact();
                    break;
                case 4:
                    Utils.deleteContact();
                    break;
                case 5:
                    Utils.sortByName();
                    break;
                case 6:
                    Utils.sortByDate();
                    break;
                case 7:
                    Utils.searchContact();
                    break;
                case 8:
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Mauvaise valeur !!");
                    break;
            }
        }
    }

    public static Scanner getScanner() {
        return sc;
    }
}
