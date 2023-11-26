import ConcreteCommand.Register;

import java.util.Scanner;

public class Parser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter command:");

        while(scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String[] commands = input.split(" ");

            switch (commands[0]) {
                case "register":
                    if (commands.length == 3) {
                        Register.registerUser(commands[1], commands[2]);
                    } else {
                        System.out.println("Invalid register command. Usage: register <username> <password>");
                    }
                    break;
                // 可以在这里增加更多命令的处理
                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }
    }
}
