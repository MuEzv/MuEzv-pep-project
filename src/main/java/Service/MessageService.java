package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {
    
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;
    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }


    /*
     * 3. PROCESS THE CREATION OF NEW MESSAGE
     *  3.1 Message is not blank && length <= 255
     *  3.2 posted_by exists
     *  
     * @param Message
     * @return new message
     */
    public Message creatMessage(Message msg){
        //check message text
        if(msg.getMessage_text() == null || msg.getMessage_text().length() > 255) return null;
        //Check posted_by
        List<Account> accounts = accountDAO.getAllAccounts();
        for(Account a : accounts){
            // if posted_by id exists, insert new message
            if(a.getAccount_id() == msg.getPosted_by()){
                return messageDAO.insertMessage(msg);
            }
        }
        return null;
    }
}

