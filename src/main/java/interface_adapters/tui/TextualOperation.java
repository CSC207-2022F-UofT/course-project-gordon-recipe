package interface_adapters.tui;

/**
 * An operation the user can run in the textual user interface.
 */
public interface TextualOperation {
    /**
     * The code for this operation that the user types to run it, e.g. "delete recipe".
     */
    String getCode();

    /**
     * The description for this operation that is shown to the user, e.g. "Delete a recipe from storage".
     */
    String getDescription();

    /**
     * Runs the operation.
     */
    void run();
}