package SystemFunctions;

/**
 * Returns strings used for display when an exception occurs in the program
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-08-11
 * last modified 2020-08-11
 */
public class ExceptionPresenter {
    /**
     * Presents errors based on a type and a process, based off an input
     *
     * @param input   the input
     * @param process the process
     * @param type    the type of error
     */
    public void exceptionMessage(int input, String process, String type) {
        switch (input) {
            case 1:
                System.out.println("\n" + process + " error for " + type + "!");
                break;
            case 2:
                System.out.println("\nMissing files for deserialization of " + type + "!");
                break;
        }
        System.exit(-1);
    }
}
