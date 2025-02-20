package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    

    //Insert Message
    public Message insertMessage(Message msg){
        try{
            Connection connection = ConnectionUtil.getConnection();
            String sql = "INSERT INTO MESSAGE (POSTED_BY, MESSAGE_TEXT, TIME_POSTED_EPOCH) VALUES(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, msg.getPosted_by());
            ps.setString(2, msg.getMessage_text());
            ps.setLong(3, msg.getTime_posted_epoch());

            int row = ps.executeUpdate();
            if(row > 0){ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    int generated_message_id = (int) rs.getInt(1);
                    return new Message(generated_message_id, msg.getPosted_by(), msg.getMessage_text(), msg.getTime_posted_epoch());
                }
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
            String sql = "SELECT MESSAGE_ID, POSTED_BY, MESSAGE_TEXT, TIME_POSTED_EPOCH FROM MESSAGE";

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

    //Delete Message by ID
    public Message deleteMessageById(int message_id){
        try{
            Connection connection = ConnectionUtil.getConnection();
            Message msg = getMessageById(message_id);
            String sql = "DELETE FROM MESSAGE WHERE MESSAGE_ID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);
            int row = ps.executeUpdate();
            if(row > 0)return msg;

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Update Message by ID
    public Message updateMessageById(int message_id, String new_text){
        try{
            Connection connection = ConnectionUtil.getConnection();
            String sql = "UPDATE MESSAGE SET MESSAGE_TEXT = ? WHERE MESSAGE_ID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, new_text);
            ps.setInt(2, message_id);

            int row = ps.executeUpdate();
            if(row > 0) return getMessageById(message_id);

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Get messages by user
    public List<Message> getMessagebyUser(int posted_by){
        List<Message> mlst = new ArrayList<>();
        try{
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT MESSAGE_ID, POSTED_BY, MESSAGE_TEXT, TIME_POSTED_EPOCH FROM MESSAGE WHERE POSTED_BY = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, posted_by);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                mlst.add(new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4)));
            }
            return mlst;

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

  
} // class 
