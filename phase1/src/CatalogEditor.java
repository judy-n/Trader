import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Lets AdminUser approve/deny pending items
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-05
 */
public class CatalogEditor {
    private ItemManager im;
    private UserManager um;
    private AdminUser currentUser;
    private int max;

    public CatalogEditor(AdminUser user, ItemManager im, UserManager um){
        this.im = im;
        this.um = um;
        int input;
        int actionInput;
        max = im.getNumPendingItems();
        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        currentUser = user;

        if(im.getPendingItems().isEmpty()){
            sp.catalogEditor(1);
            new AdminDashboard(currentUser, im, um);
        }else {
            sp.catalogEditor(im.getPendingItems());
        }
        try{
            do{
                sp.catalogEditor(2);
                input = Integer.parseInt(br.readLine());
                while(input<0 || input>max){
                    sp.invalidInput();
                    input = Integer.parseInt(br.readLine());
                }
                if(input == 0){
                    new AdminDashboard(currentUser, im, um);
                }
                Item i = im.getPendingItem(input);
                sp.catalogEditor(i);
                actionInput = Integer.parseInt(br.readLine());
                while (actionInput != 1 && actionInput != 2) {
                    sp.invalidInput();
                    actionInput = Integer.parseInt(br.readLine());
                }
                if(actionInput == 1){
                    im.approveItem(i);
                }else{
                    im.rejectItem(i);
                }
            }while(input != 0);
            new AdminDashboard(currentUser, im, um);

        }catch (IOException e){
            sp.exceptionMessage();
        }
    }


}
