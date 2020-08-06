package AdminUserFunctions;

import SystemManagers.UserManager;

/**
 * Lets admins review request to be unfrozen sent by frozen normal users.
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-08-05
 */
public class AccountUnfreezer {
    private String currUsername;
    private UserManager userManager;

    /**
     * Creates an <AccountUnfreezer></AccountUnfreezer> with the given admin username and user manager.
     *
     * @param username    the username of the admin who's currently logged in
     * @param userManager the system's user manager
     */
    public AccountUnfreezer(String username, UserManager userManager) {
        this.currUsername = username;
        this.userManager = userManager;

        // Header: systemPresenter.accountUnfreezer(1) for "Here are the users that requested to be unfrozen:"
        //          systemPresenter.accountUnfreezer(2) for "Select the unfreeze request you'd like to accept:"

        // 1. Display list of unfreeze requests: getUnfreezeRequests()
        //      > if empty, systemPresenter.emptyListMessage()
        // 2. Pass selected index into acceptUnfreezeRequest() to unfreeze
        // 3. Let user know unfreeze was successful: systemPresenter.accountUnfreezer(3);
        //      - swing dialog window? :o

//        OLD CODE
//        -----------------------
//        int indexInput = 0;
//        List<String> usernames;
//        do {
//            usernames = userManager.getUnfreezeRequests();
//            systemPresenter.adminGetUnfreezeRequests(usernames);
//            if (!usernames.isEmpty()) {
//                try {
//                    String input = bufferedReader.readLine();
//                    while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
//                        systemPresenter.invalidInput();
//                        input = bufferedReader.readLine();
//                    }
//                    if (input.equalsIgnoreCase("y")) {
//                        systemPresenter.adminGetUnfreezeRequests(1);
//
//                        int max = userManager.getNumUnfreezeRequest();
//                        String temp = bufferedReader.readLine();
//                        while (!temp.matches("[0-9]+") || Integer.parseInt(temp) > max) {
//                            systemPresenter.invalidInput();
//                            temp = bufferedReader.readLine();
//                        }
//                        indexInput = Integer.parseInt(temp);
//
//                        if (indexInput != 0) {
//                            String unfreezeUsername = userManager.getUnfreezeUsername(indexInput - 1);
//                            userManager.removeUnfreezeRequest(indexInput - 1);
//
//                            /* Notify normal user of account being unfrozen */
//                            userManager.notifyUser(unfreezeUsername).basicUpdate
//                                    ("UNFROZEN", unfreezeUsername, currUsername);
//
//                            systemPresenter.adminGetUnfreezeRequests(2);
//                        }
//                    }
//                    systemPresenter.adminGetUnfreezeRequests(3);
//                } catch (IOException e) {
//                    systemPresenter.exceptionMessage();
//                }
//            } else {
//                break;
//            }
//        } while (indexInput != 0);
    }

    /**
     * Converts the list of usernames who requested to be unfrozen into an array and returns it.
     *
     * @return an array containing all the usernames who requested to be unfrozen
     */
    public String[] getUnfreezeRequests() {
        return (String[]) userManager.getUnfreezeRequests().toArray();
    }

    /**
     * Accepts the unfreeze request at the given index by removing it from the list of
     * unfreeze requests and notifying the sender.
     *
     * @param index the index of the unfreeze request being accepted
     */
    public void acceptUnfreezeRequest(int index) {
        String unfreezeUsername = userManager.getUnfreezeUsername(index);
        userManager.removeUnfreezeRequest(index);

        /* Notify normal user of account being unfrozen */
        userManager.notifyUser(unfreezeUsername).basicUpdate
                ("UNFROZEN", unfreezeUsername, currUsername);
    }
}
