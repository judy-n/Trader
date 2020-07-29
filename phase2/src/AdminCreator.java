/**
 * Lets the initial admin create and add a new admin to the system.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-28
 */
public class AdminCreator {
    private AdminUser currentAdmin;
    private ItemManager itemManager;
    private UserManager userManager;

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
        itemManager = im;
        userManager = um;
        String username = "";
        String email = "";
        String password = "";

        SystemPresenter systemPresenter = new SystemPresenter();
        new SignUpSystem(userManager).createNewAdmin(username,email,password);
        systemPresenter.adminCreator();
        close();
    }

    private void close() {
        new AdminDashboard(currentAdmin, itemManager, userManager);
    }
}
