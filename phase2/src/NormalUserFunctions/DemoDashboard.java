package NormalUserFunctions;
import SystemManagers.*;
import Entities.*;
import SystemFunctions.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays a dashboard for a demo user.
 *
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-07-19
 * last modified 2020-07-28
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

        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        systemPresenter.demoDashboard();

        try {
            String temp;
            temp = bufferedReader.readLine();
            while (!temp.matches("[0-2]")) {
                systemPresenter.invalidInput();
                temp = bufferedReader.readLine();
            }
            input = Integer.parseInt(temp);
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }

        switch (input) {
            case 0:
                systemPresenter.exitProgram();
                System.exit(0);
            case 1:
                new DemoCatalogViewer(im, um, tm);
                break;
            case 2:
                new SystemController();
        }
    }
}
