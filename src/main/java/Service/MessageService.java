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


    /**
     * 4. Retriieve all message
     * 
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * 5. Retrieve message by ID
     */

     public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
     }

     /**
      * 6. Delete Message by ID
      *
      */
     public Message deleteMessageById(int message_id){

        return messageDAO.deleteMessageById(message_id);
        
     }

     /**
      * 7. Update message by id
      * 7.1 message_id exist
      * 7.2 message_text not blank && length() <= 255
        @return the updated message
      */

      public Message updateMessageById(int message_id, String new_text){
        Message msg = getMessageById(message_id);
        if(new_text == null || new_text.length() > 255) return null;
        if(msg != null){
            return messageDAO.updateMessageById(message_id, new_text);
        }
        return null;
      }

      /**
       * 8. Retrieve all message by posted_by
       * 
       */
      public List<Message> getMessagesbyUser(int account_id){
        List<Message> mlst = messageDAO.getMessagebyUser(account_id);
        return mlst;
      }
}

