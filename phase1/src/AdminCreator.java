/**
 * Lets the initial admin create and add a new admin ot the system.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-06
 */
public class AdminCreator {
    private ItemManager im;
    private UserManager um;
    private AdminUser currentUser;

    public AdminCreator(AdminUser user, ItemManager im, UserManager um){
        currentUser = user;
        this.im = im;
        this.um = um;
        SystemPresenter sp = new SystemPresenter();
        new SignUpSystem(um).createNewAdmin();
        sp.adminCreator();
        new AdminDashboard(currentUser, im, um);
    }
}
