
The files used to save information outside of the program will be created during the first execution of the program.
Due to the fixed file path of these external files, please ensure that the project is open under the phase1 folder and not the group_0043 folder or anything else.

--------------------------------------------------------------------

Here are the login details for the very first admin:

Username: Hello_World

Email: admin01@email.com

Password: pa55word

---------------------------------------------------------------------

Our program uses a text UI so all navigation of the program will be through typing a response and hitting enter to proceed.

Administrative users are separated from normal users in that they cannot trade and do not have an inventory, wishlist, etc.
Only normal users may sign up through the 'Sign up' option on the start menu.
Admins can be added to the system by logging in as the initial admin and selecting the 'add new admin to the system' option from the dashboard.

When logged in as a normal user, the following apply:

- by selecting an item to trade while viewing the catalog, you send a trade request to the item's owner which is a one-way borrowing
>>> the recipient of the request can view all available items in your inventory and...
	a) choose whichever item they want in return, making it a two-way trade, or
	b) choose not to borrow from you and lend their item to you in a one-way trade
>>> the recipient also decides on whether the trade is temporary or permanent, and makes the first meeting suggestion

- to see all actions you can take on a trade, select 'view ongoing trades' from the dashboard and enter the index of the trade you'd like to work with
>>> a new menu will pop up which lets you edit or confirm the meeting details, confirm the latest transaction, or cancel the trade

	! you are only allowed to edit or confirm the meeting if the other user was the last to edit
	! you cannot confirm a meeting if the suggested time has already passed
	! you cannot confirm a transaction before its scheduled time or when a meeting has not been agreed upon

- if a trade is temporary, the 'confirm the latest meeting took place' option will confirm the first meeting 
  or the second meeting depending on what phase of the trade you and your trade partner are on

- a trade becomes incomplete and is cancelled 24 hours after a scheduled meeting with at least one trader failing to confirm the transaction

- scheduling too many transactions to occur in one week simply prompts you to choose a different date and time (doesn't freeze your account)

- wishlist editor doesn't have the option to add items, since that takes place when you view the catalog


When logged in as an admin, the following apply:

- when viewing accounts that need to be frozen, you can either freeze all of them or none of them