package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.h2.command.Prepared;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    

    //Insert Message
    public Message insertMessage(Message msg){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "INSERT INTO MESSAGE (POSTED_BY, MESSAGE_TEXT, TIME_POSTED_RPOCH) VALUES(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setInt(1, msg.getPosted_by());
            ps.setString(2, msg.getMessage_text());
            ps.setLong(3, msg.getTime_posted_epoch());

            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                int generated_message_id = (int) rs.getInt(1);
                return new Message(generated_message_id, msg.getPosted_by(), msg.getMessage_text(), msg.getTime_posted_epoch());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    
} // class 
