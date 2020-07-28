import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays a dashboard for a demo user.
 *
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-07-19
 * last modified 2020-07-20
 */

public class DemoDashboard {

    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private int input;

    /**
     * Creates a <DemoDashboard></DemoDashboard> with the given demo user and item/user/trade managers.
     *
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public DemoDashboard(ItemManager im, UserManager um, TradeManager tm){
        itemManager = im;
        userManager = um;
        tradeManager = tm;

        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sp.demoDashboard();

        try {
            String temp;
            temp = br.readLine();
            while (!temp.matches("[0-2]")) {
                sp.invalidInput();
                temp = br.readLine();
            }
            input = Integer.parseInt(temp);
        } catch (IOException e) {
            sp.exceptionMessage();
        }

        switch (input) {
            case 0:
                sp.exitProgram();
                System.exit(0);
            case 1:
                new DemoCatalogViewer(im, um, tm);
                break;
            case 2:
                new SystemController();
        }
    }
}
