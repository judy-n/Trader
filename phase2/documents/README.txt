Group member git log names:
    Ning Zhang = Allistari
    Yingjia Liu = liuyjca
    Liam Huff = Liam
    Judy Naamani = Judy Naamani
    Kushagra Mehta = mehtaku6

The files used to save information outside of the program will be created during the first execution of the program.
Due to the fixed file path of these external files, please ensure that the project is open under the phase1 folder and not the group_0043 folder or anything else.

* The main method to start the program is located in the Launcher class *

=======================================================================

Here are the login details for the very first admin:

Username: Hello_World

Email: admin01@email.com

Password: pa55word

=======================================================================

Our program uses a GUI. Successfully logging in, signing up, or choosing to demo the program will open a new window where you
use the actual trade program. To log out, close the dashboard window and click the logout button on the remaining window.
The original window is not closable using the [X] button on the window--please exit the program using the "Exit the program" button on the start menu.

- administrative users are separated from normal users in that they cannot trade and do not have an inventory, wishlist, etc.

- only normal users may sign up through the 'Sign up' option on the start menu
>>> admins can be added to the system by logging in as the initial admin and selecting the 'add new admin to the system' option from the dashboard.

- all username and emails are unique, and the following restrictions apply when creating a new account:
    all credentials must be non-empty and cannot contain any spaces
    + usernames must be at least 3 characters long
    + emails must contain '@' and '.'
    + passwords must be 6-20 characters long

- the 'log out' option on both the normal and admin dashboard will bring you back to the start menu, where you can then exit the program (or sign up or log in)

------------------------------------------------------------------------------------------------------------------------------------------------------------------

When logged in as a normal user, the following apply:

! the only thing that will put you at risk of getting your account frozen is if you have too many incomplete trades
    (trades where you and/or your trade partner fail to confirm a transaction on time)
>>> a trade becomes incomplete and is cancelled 24 hours after a scheduled meeting with at least one trader failing to confirm the transaction

- if your account is frozen, your dashboard will show the option to send an unfreeze request to an admin
>>> you may only send one request each time you are frozen
>>> once you are unfrozen, your incomplete trade count resets to 0

- by selecting an item to trade while viewing the catalog, you send a trade request to the item's owner which is a one-way borrowing

    ! you can't send a trade request twice in a row for the same item
    ! if you send a trade request to the same user for a different item of theirs, it will replace the last request you sent to them

>>> the recipient of the request can view all available items in your inventory and...
	a) choose whichever item they want in return, making it a two-way trade, or
	b) choose not to borrow from you and lend their item to you in a one-way trade
>>> the recipient also decides on whether the trade is temporary or permanent, and makes the first meeting suggestion

- your account has a threshold value indicating how many more items you must have lent than you have borrowed in order to initiate a trade
>>> lendMinimum = 0 means you must have at least lent the same number of items as you've borrowed, otherwise the rule is
    you must have lent at least [lendMinimum] items more than you've borrowed
>>> the only time this doesn't apply is when you send your very first trade request

- to see all actions you can take on a trade, select 'view ongoing trades' from the dashboard and enter the index of the trade you'd like to work with
>>> a new menu will pop up which lets you edit or confirm the meeting details, confirm the latest transaction, or cancel the trade

	! you are only allowed to edit or confirm the meeting if the other user was the last to edit
	! you cannot confirm a meeting if the suggested time has already passed
	! you cannot confirm a transaction before its scheduled time or when a meeting has not been agreed upon

- if a trade is temporary, the 'confirm the latest meeting took place' option will confirm the first meeting 
  or the second meeting depending on what phase of the trade you and your trade partner are on

- scheduling too many transactions to occur in one week simply prompts you to choose a different date and time (doesn't freeze your account)

- wishlist editor doesn't have the option to add items, since that takes place when you view the catalog

- you may not remove an item from your inventory if it's involved in a trade or is being asked for in a trade request
    (you must reject the trade request(s) before you can remove it)
>>> an exception to this is if a trade has been cancelled due to it being incomplete
    since the system doesn't know the whereabouts of the traded items, you're allowed to remove your item from your inventory

- at the end of a permanent trade, the item you lent (if you lent one) will automatically be removed from your inventory
    and if you have your trade partner's item in your wishlist, it will also be removed

------------------------------------------------------------------------------------------------------------------------------------------------------------------

When logged in as an admin, the following apply:

- when viewing accounts that need to be frozen, you can either freeze all of them or none of them

- when viewing requests to be unfrozen, you can either accept a request or leave it there

- upon choosing the 'edit a user's threshold values' option, you must enter the username of the normal user whose thresholds you wish to change