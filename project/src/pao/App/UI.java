package pao.app;

import java.util.Scanner;

public class UI {
    private final Services services;

    public UI(Services s) {
        services = s;
    }
    public final void showMenu() {
        System.out.println("# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #");
        System.out.println("# # # # #   Welcome to out delivery platform console App   # # # # #");
        System.out.println("# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #");
        System.out.println("    Here are the options that we have implemented at this moment : ");
        System.out.println("1. Add a user of the app.");
        System.out.println("2. Add an employee of the app.");
        System.out.println("3. Add a product.");
        System.out.println("4. Add a restaurant for the app.");
        System.out.println("5. Show user list of the food delivery app.");
        System.out.println("6. Show employee list of the app.");
        System.out.println("7. Show products.");
        System.out.println("8. Show restaurants.");
        System.out.println("9. Push a Food Order");
        System.out.println("10. Show the users that ordered today.");
        System.out.println("11. Show top 3 users that have ordered most times.");
        System.out.println("12. Increase the salary for top 2 most active drivers.");
//        System.out.println("13. For each user, print the sum of his last order.");
        System.out.println("14. EXIT");
        System.out.println("     ? ? ? ? ?   How can I help you   ? ? ? ? ? ");
        System.out.println(" - Introduce the option number that you choose : ");
    }

    public final void showLogo() {
        System.out.println("  _____           _   _                                                                       \n" +
                " |  __ \\         | | (_)                                                /\\                    \n" +
                " | |  | |   ___  | |  _  __   __   ___   _ __   _   _     ______       /  \\     _ __    _ __  \n" +
                " | |  | |  / _ \\ | | | | \\ \\ / /  / _ \\ | '__| | | | |   |______|     / /\\ \\   | '_ \\  | '_ \\ \n" +
                " | |__| | |  __/ | | | |  \\ V /  |  __/ | |    | |_| |               / ____ \\  | |_) | | |_) |\n" +
                " |_____/   \\___| |_| |_|   \\_/    \\___| |_|     \\__, |              /_/    \\_\\ | .__/  | .__/ \n" +
                "                                                 __/ |                         | |     | |    \n" +
                "                                                |___/                          |_|     |_|   ");
    }

    public final void run() {
        Scanner in = new Scanner(System.in);

        this.showLogo();

        System.out.println("\n\n\n");

//        System.out.println("Inainte de a porni meniul interactiv, vrei sa incarci datele din memorie ? (YES/NO) ");
//        String options = in.nextLine();
//        if (options.equals("YES")) {
//            services.uploadFromMemory();
//            System.out.println("S-a terminat incarcarea din memorie si acum poti continua activitatea pe aplicatie !");
//        } else {
//            if (!options.equals("NO")) {
//                System.out.println("Optiune invalida ! O sa continuam fara sa incarcam datele din memorie ! Multumim !");
//            }
//        }

        int option;
        boolean exit = false;

        while (!exit) {

            this.showMenu();

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
//                case 13 -> services.printSumLastOrderForEachUser();
                default -> exit = true;
            }
        }
    }
}
