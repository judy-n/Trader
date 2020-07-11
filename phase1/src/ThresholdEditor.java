import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lets an admin change a certain threshold value for a certain user.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-10
 */
public class ThresholdEditor {
    private ItemManager itemManager;
    private UserManager userManager;
    private AdminUser currentUser;
    private SystemPresenter sp;
    private BufferedReader br;

    /**
     * Creates a ThresholdEditor that lets an admin change a certain user's threshold values.
     *
     * @param user admin user
     * @param im the system's item manager
     * @param um the system's user manager
     */
    public ThresholdEditor(AdminUser user, ItemManager im, UserManager um){
        currentUser = user;
        itemManager = im;
        userManager = um;

        sp = new SystemPresenter();
        br = new BufferedReader(new InputStreamReader(System.in));

        NormalUser subjectUser;
        String usernameInput;
        int newThreshold;

        sp.thresholdEditor(1);
        try{
            usernameInput = br.readLine();
            while(!userManager.usernameExists(usernameInput, false)&&!usernameInput.equals("0")){
                sp.invalidInput();
                usernameInput = br.readLine();
            }
            if(usernameInput.equals("0")){
                close();
            }

            subjectUser = userManager.getNormalByUsername(usernameInput);

            sp.thresholdEditor(2);
            String temp = br.readLine();
            while (!temp.matches("[0-4]")) {
                sp.invalidInput();
                temp = br.readLine();
            }
            int choiceInput = Integer.parseInt(temp);

            switch(choiceInput){
                case 0:
                    break;
                case 1:
                    sp.thresholdEditor(1, subjectUser.getWeeklyTradeMax());
                    newThreshold = thresholdInputCheck();
                    subjectUser.setWeeklyTradeMax(newThreshold);
                    break;
                case 2:
                    sp.thresholdEditor(2, subjectUser.getMeetingEditMax());
                    newThreshold = thresholdInputCheck();
                    subjectUser.setMeetingEditMax(newThreshold);
                    break;
                case 3:
                    sp.thresholdEditor(3, subjectUser.getLendMinimum());
                    newThreshold = thresholdInputCheck();
                    subjectUser.setLendMinimum(newThreshold);
                    break;
                case 4:
                    sp.thresholdEditor(4, subjectUser.getIncompleteMax());
                    newThreshold = thresholdInputCheck();
                    subjectUser.setIncompleteMax(newThreshold);
                    break;
            }
            close();

        }catch (IOException e){
            sp.exceptionMessage();
        }
    }

    private int thresholdInputCheck() throws IOException {
        String temp2 = br.readLine();
        while (!temp2.matches("[0-9]+")) {
            sp.invalidInput();
            temp2 = br.readLine();
        }
        return Integer.parseInt(temp2);
    }

    private void close() {
        new AdminDashboard(currentUser, itemManager, userManager);
    }
}
