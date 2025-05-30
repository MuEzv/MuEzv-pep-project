package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

     AccountService accountService;
     MessageService messageService;

     public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
     }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        //Process user registration
        app.post("/register", this::registerUserHandler);
        // User Login
        app.post("/login", this::loginUserHandler);
        app.post("/messages", this:: newMessageHandler);
        app.get("/messages", this::allMessageHandler);
        app.get("/messages/{message_id}", this::findMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


    // User registration handler
    private void registerUserHandler(Context ctx){
        Account newAccount = ctx.bodyAsClass(Account.class);
        Account registeredAccount = accountService.registerUser(newAccount);
        if(registeredAccount != null){
            ctx.json(registeredAccount);
        }else{
            ctx.status(400);
        }
        
    }

    // User login handler
    private void loginUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account loginAccount = mapper.readValue(ctx.body(), Account.class);
        Account loggedInAccount = accountService.loginCheck(loginAccount);
        
        if(loggedInAccount != null){
            ctx.json(loggedInAccount).status(200);
        }else{
            ctx.status(401);
        }
    }

    // Create new Message
    private void newMessageHandler(Context ctx){
        
        Message msg = ctx.bodyAsClass(Message.class);
        Message submitMsg = messageService.creatMessage(msg);
        if(submitMsg != null){
            ctx.json(submitMsg).status(200);
        }else{
            ctx.status(400);
        }
    }

    // Get all messages
    private void allMessageHandler(Context ctx){
        List<Message> msgLst = messageService.getAllMessages();
        ctx.json(msgLst);
    }

    //Find message by ID
    private void findMessageByIdHandler(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("message_id")); 
        Message msg = messageService.getMessageById(messageId);
        if(msg != null){
            ctx.json(msg).status(200);
        }else{
            ctx.result("").status(200);
        }
        
        
    }

    //Delete Message by id
    private void deleteMessageByIdHandler(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMsg = messageService.deleteMessageById(messageId);
        if(deletedMsg != null){
            ctx.json(deletedMsg).status(200);
        }else{
            ctx.result("").status(200);
        }
    }

    // Update Message by id
    private void updateMessageByIdHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        String new_text = ctx.bodyAsClass(Message.class).getMessage_text();
        Message updatedMsg = messageService.updateMessageById(message_id, new_text);
        if(updatedMsg != null){
            ctx.json(updatedMsg).status(200);
        }else{
            ctx.status(400);
        }
    }

    private void getMessagesByUserHandler(Context ctx){
        int user_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> mlst = messageService.getMessagesbyUser(user_id);
        if(mlst != null){
            ctx.json(mlst);
        }else{
            ctx.result("").status(200);
        }
    }

}