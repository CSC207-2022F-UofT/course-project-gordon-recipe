package interface_adapters.tui;

import java.util.*;

/**
 * A helper class for receiving input from the user in a textual interface.
 */
public class TextualReader {
    final private Scanner scanner;

    public TextualReader(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Returns an input string from the user.
     *
     * @param prompt the prompt to be shown to the user
     * @return the user's response
     */
    public String getInput(String prompt) {
        System.out.printf("%s ", prompt);

        return scanner.nextLine();
    }

    /**
     * Returns an input integer from the user.
     *
     * @param prompt the prompt to be shown to the user
     * @return The integer the user entered
     */
    public Integer getIntegerInput(String prompt) {
        while (true) {
            System.out.printf("%s ", prompt);

            String input = "";

            try {
                input = scanner.next();
                scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.printf("%s%s doesn't look like a number, try again.%s\n", Colour.RED, input, Colour.RESET);
            } catch (Exception e) {
                System.out.println("Couldn't receive your input, try again.");
            }
        }
    }

    /**
     * Returns a list of items from the user
     *
     * @param item the singular name of the items in the list, e.g. "Step" or "Note"
     * @return a list of the user's inputs
     */
    public List<String> getList(String item) {
        int count = 0;

        List<String> items = new ArrayList<>();

        Colour.printHeader("List Input");
        System.out.printf("Type '%sexit%s' to quit.\n\n", Colour.YELLOW_BOLD, Colour.RESET);

        while (true) {
            System.out.printf("%s %d: ", item, count);

            String input = scanner.nextLine();

            if (input.equals("exit")) {
                break;
            }

            items.add(input);

            count += 1;
        }

        return items;
    }

    /**
     * Presents a list of <code>TextualOperation</code>s to the user and lets them run one.
     *
     * @param operations the list of operations to show the user
     * @param header     the header for the list of operations
     */
    public void chooseOperation(List<TextualOperation> operations, String header) {
        while (true) {
            Colour.printHeader(header);

            for (TextualOperation operation : operations) {
                if (Objects.equals(operation.getDescription(), "")) {
                    Colour.println(Colour.YELLOW_BOLD, operation.getCode());
                } else {
                    System.out.printf(Colour.YELLOW_BOLD + "%s:" + Colour.YELLOW + " %s\n" + Colour.RESET, operation.getCode(), operation.getDescription());
                }
            }

            String exampleChoice = operations.get(0).getCode();

            System.out.printf("\nSelect a choice by its key, or type '%sexit%s' to quit:\n", Colour.YELLOW_BOLD, Colour.RESET);
            System.out.printf("Code (e.g. %s%s%s): ", Colour.YELLOW_BOLD, exampleChoice, Colour.RESET);

            String choice = scanner.nextLine();

            if (Objects.equals(choice, "exit")) {
                return;
            }

            TextualOperation operation = operations.stream().filter(e -> e.getCode().equals(choice)).toList().get(0);

            if (operation != null) {
                operation.run();
            } else {
                System.out.printf("\nHmm, %s%s%s doesn't seem to be a choice.\n\n", Colour.YELLOW_BOLD, choice, Colour.RESET);
            }
        }
    }

    /**
     * Prompts the user to select from a map of options, and returns the key of their choice.
     *
     * @param choices the map of choices the user can select from.
     *                Keys should be able to be easily typed by the user.
     *                Values are descriptions of choices, and can be empty strings.
     * @return the key of the choice the user selected, or null if the user decided to exit.
     */
    public String promptFromMap(Map<String, String> choices, String header) {
        while (true) {
            Colour.printHeader(header);
            for (Map.Entry<String, String> choice : choices.entrySet()) {
                String description = choice.getValue();
                String key = choice.getKey();

                if (Objects.equals(description, "")) {
                    Colour.println(Colour.YELLOW_BOLD, key);
                } else {
                    System.out.printf(Colour.YELLOW_BOLD + "%s:" + Colour.YELLOW + " %s\n" + Colour.RESET, key, description);
                }
            }

            String exampleChoice = (String) choices.keySet().toArray()[0];

            System.out.printf("\nSelect a choice by its key, or type '%sexit%s' to quit:\n", Colour.YELLOW_BOLD, Colour.RESET);
            System.out.printf("Code (e.g. %s%s%s): ", Colour.YELLOW_BOLD, exampleChoice, Colour.RESET);

            String choice = scanner.nextLine();

            if (Objects.equals(choice, "exit")) {
                return null;
            }

            if (choices.containsKey(choice)) {
                return choice;
            } else {
                System.out.printf("\nHmm, %s%s%s doesn't seem to be a choice.\n\n", Colour.YELLOW_BOLD, choice, Colour.RESET);
            }
        }
    }
}
