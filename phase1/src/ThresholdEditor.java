import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ThresholdEditor.java
 * lets admin change a certain trade threshold for a certain user
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-08
 */
public class ThresholdEditor {
    private ItemManager itemManager;
    private UserManager userManager;
    private AdminUser currentUser;

    /**
     * Creates a threshold editor that lets an admin user change a certain user's threshold
     * values
     * @param user admin user
     * @param im the system's item manager
     * @param um the system's user manager
     */
    public ThresholdEditor(AdminUser user, ItemManager im, UserManager um){
        currentUser = user;
        itemManager = im;
        userManager = um;

        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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
                    newThreshold = Integer.parseInt(br.readLine());
                    subjectUser.setWeeklyTradeMax(newThreshold);
                    break;
                case 2:
                    sp.thresholdEditor(2, subjectUser.getMeetingEditMax());
                    newThreshold = Integer.parseInt(br.readLine());
                    subjectUser.setMeetingEditMax(newThreshold);
                    break;
                case 3:
                    sp.thresholdEditor(3, subjectUser.getLendMinimum());
                    newThreshold = Integer.parseInt(br.readLine());
                    subjectUser.setLendMinimum(newThreshold);
                    break;
                case 4:
                    sp.thresholdEditor(4, subjectUser.getIncompleteMax());
                    newThreshold = Integer.parseInt(br.readLine());
                    subjectUser.setIncompleteMax(newThreshold);
                    break;
            }
            close();

        }catch (IOException e){
            sp.exceptionMessage();
        }
    }

    private void close() {
        new AdminDashboard(currentUser, itemManager, userManager);
    }
}
