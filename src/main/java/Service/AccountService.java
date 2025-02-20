package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /** 
     * 1. PROCESS NEW USER REGISTRATION
     *  1.1 CHECK the username and password of new account
     *  1.2 if not qualified, return null
     *      - if qualified, return the added account 
     * @param account
     * @return the account with generated account id
     * */ 
    public Account registerUser(Account account){
        // Check if the username exists in database OR null
        if(account.getUsername() == null|| 
            account.getUsername().isBlank() || 
            checkIfExist(account)) return null;
        //Check password length >= 4
        if(account.getPassword() == null || account.getPassword().length() < 4) return null;

        Account addedAccount = accountDAO.insertAccount(account);

        return addedAccount;
    }

    // Check if there exists username in the database
    private boolean checkIfExist(Account account){
        List<String> accounts = accountDAO.getAllAccountsName();
        return accounts.contains(account.getUsername());
    }

    /**
     * 2. PROCESS USER LOGINS
     *  2.1 Check if username exists
     *  2.2 Find the account in the database
     *  2.3 Compare with the login account & Check if the password match
     * 
     * @param account
     * @retunr if log in successfully
     * 
     */

     public Account loginCheck(Account account){
        //check if username exists
        Account retrievedAccount = accountDAO.findAccount(account);

        //Check if password match
        if(retrievedAccount != null && retrievedAccount.getPassword().equals(account.getPassword())) return true;
        
        return null;
     }

   
    
}
