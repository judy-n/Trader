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
 * last modified 2020-07-05
 */

public class ThresholdEditor {
    private ItemManager im;
    private UserManager um;
    private AdminUser currentUser;

    public ThresholdEditor(AdminUser user, ItemManager im, UserManager um){
        this.im = im;
        this.um = um;
        currentUser = user;
        NormalUser subjectUser;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String usernameInput;
        int choiceInput;
        int newThreshold;
        SystemPresenter sp = new SystemPresenter();
        sp.thresholdEditor(1);
        try{
            usernameInput = br.readLine();
            while(!um.usernameExists(usernameInput)){
                sp.invalidInput();
                usernameInput = br.readLine();
            }
            subjectUser = um.getNormalByUsername(usernameInput);
            sp.thresholdEditor(2);
            choiceInput = Integer.parseInt(br.readLine());
            while(choiceInput<0 || choiceInput > 4){
                sp.invalidInput();
                choiceInput = Integer.parseInt(br.readLine());
            }
            switch(choiceInput){
                case 0:
                    new AdminDashboard(currentUser, im, um);
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
            new AdminDashboard(currentUser, im, um);

        }catch (IOException e){
            sp.exceptionMessage();
        }





    }
}
