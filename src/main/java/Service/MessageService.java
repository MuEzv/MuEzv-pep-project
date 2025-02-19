package Service;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    
    private MessageDAO messageDAO;
    public MessageService(){
        messageDAO = new MessageDAO();
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
        
    }
}

