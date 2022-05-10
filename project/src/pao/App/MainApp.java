package pao.App;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Services services = Services.getInstance();

        System.out.println("Inainte de a porni meniul interactiv, vrei sa incarci datele din memorie ? (YES/NO) ");
        String options = in.nextLine();
        if (options.equals("YES")) {
            services.uploadFromMemory();
            System.out.println("S-a terminat incarcarea din memorie si acum poti continua activitatea pe aplicatie !");
        } else {
            if (!options.equals("NO")) {
                System.out.println("Optiune invalida ! O sa continuam fara sa incarcam datele din memorie ! Multumim !");
            }
        }


        int option;
        boolean exit = false;

        while (!exit) {

            services.showMenu();

            option = in.nextInt();

            switch (option) {
                case 1 -> services.addClient();
                case 2 -> services.addDriver();
                case 3 -> services.addProduct();
                case 4 -> services.addRestaurant();
                case 5 -> services.viewClients();
                case 6 -> services.viewDrivers();
                case 7 -> services.viewProducts();
                case 8 -> services.viewRestaurants();
                case 9 -> services.orderFood();
                case 10 -> services.printUserThatOrderedToday();
                case 11 -> services.printFirst3UsersWithMaxNumOrders();
                case 12 -> services.increaseSalaryForTop2MostActiveDrivers();
                case 13 -> services.printSumLastOrderForEachUser();
                default -> exit = true;
            }
        }
    }
}
