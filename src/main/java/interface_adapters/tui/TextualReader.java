package interface_adapters.tui;

import java.util.*;
import java.util.stream.Collectors;

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
     * @param args   the optional args for formatting the prompt
     * @return the user's response
     */
    public String getInput(String prompt, Object... args) {
        System.out.printf(prompt + " ", args);

        return scanner.nextLine();
    }

    /**
     * Returns an input integer from the user.
     *
     * @param prompt the prompt to be shown to the user
     * @param args   the optional args to format the prompt with
     * @return The integer the user entered
     */
    public Integer getIntegerInput(String prompt, Object... args) {
        while (true) {
            System.out.printf(prompt + " ", args);

            String input = "";

            try {
                input = scanner.next();
                scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                Colour.error("%s doesn't look like a number, try again", input);
            } catch (Exception e) {
                Colour.error("Couldn't receive your input, try again.");
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
        int count = 1;

        List<String> items = new ArrayList<>();

        Colour.printHeader("List Input");
        System.out.printf("Type %s to quit.\n\n", Colour.example("exit"));

        while (true) {
            String input = getInput("%s %d:", item, count);

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
        if (operations.size() < 1) {
            Colour.info("There are no operations to perform.");
            return;
        }

        while (true) {
            Colour.printHeader(header);

            for (TextualOperation operation : operations) {
                System.out.printf(Colour.YELLOW_BOLD + "%s" + Colour.RESET + ": %s\n", operation.getCode(), operation.getDescription());
            }

            System.out.printf("\nSelect a choice by its key, or type %s to quit:\n", Colour.example("exit"));

            String exampleChoice = operations.get(0).getCode();
            String choice = getInput("Code (e.g. %s):", Colour.example(exampleChoice));

            if (Objects.equals(choice, "exit")) {
                return;
            }

            // We suppress the warning here because IntelliJ is suggesting to refactor this,
            // but refactoring it will break the GitHub auto-grading.
            @SuppressWarnings("all")
            List<TextualOperation> matching_operations = operations.stream()
                    .filter(e -> e.getCode().equals(choice))
                    .collect(Collectors.toList());

            if (matching_operations.size() > 0) {
                matching_operations.get(0).run();
            } else {
                Colour.error("Hmm, %s%s%s doesn't seem to be a choice.", Colour.YELLOW_BOLD, choice, Colour.RED);
            }
        }
    }

    /**
     * Prompts the user to select from a list of objects, and returns their selection.
     *
     * @param items  the list of items to show the user
     * @param header the header of the list of items
     * @param <T>    the type of the item
     * @return the item chosen
     */
    public <T> T chooseFromList(List<T> items, String header) {
        List<String> descriptions = items.stream().map(T::toString).collect(Collectors.toList());
        Integer index = getListIndexInput(descriptions, header);

        if (index == null) {
            return null;
        }

        return items.get(index);
    }

    /**
     * Prompts the user to select from a list of options, and returns the index of their choice.
     * Uses 1-indexing when communicating with the user but converts to 0-indexing for internal use.
     *
     * @param list   the list of choices the user can select from
     * @param header the header that precedes the list of choices
     * @return the index of the choice the user selected, or null if the user decided to exit
     */
    public Integer getListIndexInput(List<String> list, String header) {
        while (true) {
            Colour.printHeader(header);

            int counter = 1;

            for (String choice : list) {
                System.out.printf("%s) %s\n", Colour.example(String.valueOf(counter)), choice);
                counter += 1;
            }

            System.out.printf("\nSelect a choice by its index, or type %s to quit:\n", Colour.example("exit"));
            System.out.printf("Index (e.g. %s): ", Colour.example("1"));

            String input = scanner.nextLine();

            if (Objects.equals(input, "exit")) {
                return null;
            }

            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < list.size()) {
                    return index;
                } else {
                    Colour.error("%s is not a valid index, try again.", input);
                }
            } catch (NumberFormatException e) {
                Colour.error("%s doesn't look like a number, try again.", input);
            } catch (Exception e) {
                Colour.error("Couldn't receive your input, try again.");
            }
        }
    }
}
