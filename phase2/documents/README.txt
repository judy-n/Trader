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
Please exit the program using the "Exit the program" button on the start menu.

- administrative users are separated from normal users in that they cannot trade and do not have an inventory, wishlist, etc.

- only normal users may sign up through the 'Sign up' option on the start menu
>>> admins can be added to the system by logging in as the initial admin and selecting the 'Create New Admin' option from the dashboard.

- all username and emails are unique, and the following restrictions apply when creating a new account:
    all credentials must be non-empty and cannot contain any spaces
    + usernames must be at least 3 characters long
        alphanumerical + underscore or period in between
    + emails must contain '@' and '.'
        must have at least one character before the '@' and in between the '@' and '.' + at least two characters after the '.'
    + passwords must be 6-20 characters long

For help on using the toggle buttons found in some parts of the program when logged in as a normal or admin user,
select 'About' in the top left corner, then select 'Help' to get a pop-up info message.

------------------------------------------------------------------------------------------------------------------------------------------------------------------

When logged in as a normal user, the following apply:

[!] the only thing that will put you at risk of getting your account frozen is if you have too many incomplete trades
    (trades where you and/or your trade partner fail to confirm a transaction on time)
>>> a trade becomes incomplete and is cancelled 24 hours after a scheduled meeting with at least one trader failing to confirm the transaction

Status - frozen:
- if your account is frozen, your dashboard will show the option to send an unfreeze request to an admin
>>> you may only send one request each time you are frozen
>>> once you are unfrozen, your incomplete trade count resets to 0
- you're not allowed to initiate any trades; this includes sending trade requests to others and accepting trade request from others
- other users will not be able to see any trade requests sent from you from the time before you were frozen
- you may continue any ongoing trades that started before you were frozen

Status - on vacation:
[!] you may only set your status to 'on vacation' if you don't have any ongoing trades
- while on vacation, your account's functionality is limited to turning vacation status off and viewing your notifications
>>> if your account is frozen as well, you're also allowed to send a request to be unfrozen
- other users will not be able to see your items in catalog, which means they can't send trade requests to you while you're on vacation
- any trade requests you sent to other users before going on vacation won't be visible to them, which means they can't start trades with you while you're on vacation


- when viewing the catalog, you will only be shown items from other users that have the same home city as you

- after selecting an item to trade while viewing the catalog, you are shown your inventory to choose which item you'd like to lend in a
potential two-way trade, or don't select an inventory item at all to make it a one-way trade
>>> the recipient of the request decides if the trade is permanent or temporary
    [!] you can't send a trade request twice in a row for the same item
    [!] if you send a trade request to the same user for a different item of theirs, it will replace the last request you sent to them

>>> the recipient also makes the first meeting suggestion if they accept the request
    [!] this suggestion doesn't count toward the number of edits made on the meeting by this user

- every account has a threshold value indicating how many more items you must have lent than you have borrowed in order to initiate a trade
>>> lendMinimum = 0 means you must have at least lent the same number of items as you've borrowed, otherwise the rule is
    you must have lent at least [lendMinimum] items more than you've borrowed
>>> the only time this doesn't apply is when you send your very first trade request, which must be for a two-way trade
>>> if you've lent exactly lendMinimum more items than you've borrowed, you can set up a trade request for an item, but it must be a two-way trade

- to see all actions you can take on a trade, select 'View Ongoing Trades' from the dashboard and then select the trade you'd like to work with
>>> buttons at the bottom of the window let you edit or agree with the meeting details, confirm the latest transaction, or cancel the trade
	[!] you are only allowed to edit or agree with the meeting if the other user was the last to edit
	[!] you cannot confirm a meeting if the suggested time has already passed
	[!] you cannot confirm a transaction before its scheduled time or when a meeting has not been agreed upon
	[!] you cannot cancel a trade after a meeting has been agreed upon

- if a trade is temporary, the 'Confirm Transaction' option will confirm the first meeting or the second meeting
  depending on what phase of the trade you and your trade partner are on
>>> confirming the first transaction will automatically create a second meeting that is exactly
    30 days after the first meeting--same time, same place

- scheduling too many transactions to occur in one week prompts you to choose a different date and time (doesn't freeze your account)

- wishlist editor doesn't have the option to add items, since that takes place when you view the catalog

- you may not remove an item from your inventory if it's involved in a trade or is being asked for in a trade request
    (you must reject the trade request(s) before you can remove it)
>>> an exception to this is if a trade has been cancelled due to it being incomplete
    since the system doesn't know the whereabouts of the traded items, you're allowed to remove your item from your inventory

- at the end of a permanent trade, the item you lent (if you lent one) will automatically be removed from your inventory
    and if you have your trade partner's item in your wishlist, it will also be removed

------------------------------------------------------------------------------------------------------------------------------------------------------------------

When logged in as an admin, the following apply:

- when viewing accounts that need to be frozen, you can only freeze all the accounts at once

- when viewing requests to be unfrozen, you can either accept a request or leave it there

- when editing the threshold values, enter a positive integer in the field corresponding to the threshold(s) you would like to change and
    leave the field empty if you don't want to make changes to that threshold, then press 'Confirm

- you can undo two types of user actions:
    1) approval of a normal user's item
        > only available for undoing if the user hasn't already removed the item from their inventory
            or the item hasn't been part of an accepted trade yet
        > undoing the approval will remove the item from its owner's inventory and remove all trade requests involving the item

    2) sending of a trade request
        > only available for undoing if the request hasn't been accepted or rejected by the recipient yet

------------------------------------------------------------------------------------------------------------------------------------------------------------------

When demo-ing the program, you can only view the catalog which will show all items belonging to all users in all cities
(since if they were to enter a city that no actual user has put as their home city yet, the catalog would be empty)
- pressing the 'Trade' button will only show a message telling you to sign up