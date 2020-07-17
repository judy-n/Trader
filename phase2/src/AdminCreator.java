/**
 * Lets the initial admin create and add a new admin to the system.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-12
 */
public class AdminCreator {
    private AdminUser currentAdmin;
    private ItemManager im;
    private UserManager um;

    /**
     * Creates an <AdminCreator></AdminCreator> with the given admin and item/user managers.
     * Lets the initial admin create other admins using <SignUpSystem></SignUpSystem>.
     *
     * @param user the initial admin
     * @param im   the system's item manager
     * @param um   the system's user manager
     */
    public AdminCreator(AdminUser user, ItemManager im, UserManager um) {
        currentAdmin = user;
        this.im = im;
        this.um = um;

        SystemPresenter sp = new SystemPresenter();
        new SignUpSystem(um).createNewAdmin();
        sp.adminCreator();
        close();
    }

    private void close() {
        new AdminDashboard(currentAdmin, im, um);
    }
}
