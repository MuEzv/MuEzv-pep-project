package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Util.ConnectionUtil;

import Model.Account;

public class AccountDAO {
    

    // GET ALL AUTHORS FROM TABLE ACCOUNT
    public List<String> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<String> accounts = new ArrayList<>();
        
        try{
            String sql = "SELECT USERNAME FROM ACCOUNT";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                accounts.add(rs.getString("USERNAME"));
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;

    }
    
    
    //Insert an account into the author table
    public Account inserAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO ACCOUNT (USERNAME, PASSWORD) VALUES(?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            // Set account parameter
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            
            //Get the generated account id
            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                int generated_account_id = (int) rs.getInt(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    //Find account
    public Account findAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT USERNAME, PASSWORD FROM ACCOUNT WHERE USERNAME = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, account.getUsername());
            ResultSet rs = ps.executeQuery();
            return new Account(rs.getInt(1), rs.getString("USERNAME"), rs.getString("PASSWORD"));
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
