package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    //Retrieve all messages
    public List<Message> getAllMessages(){

        try{
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT MESSAGE_ID, POSTED_BY, MESSAGE_TEXT, TIME_POSTED_RPOCH FROM MESSAGE";

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Message> mlst = new ArrayList<>();
            while(rs.next()){
                mlst.add(new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4)));

            }
            return mlst;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    //Get Message by ID
    public Message getMessageById(int message_id){

        try{

            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT MESSAGE_ID, POSTED_BY, MESSAGE_TEXT, TIME_POSTED_EPOCH FROM MESSAGE WHERE MESSAGE_ID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
        
    } 
} // class 
