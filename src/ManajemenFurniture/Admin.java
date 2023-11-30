/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ManajemenFurniture;

import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author FLOW
 */
public class Admin extends Database{
    private String user_id;
    private String password;
    private int id_admin;
    private String nama_lengkap;
    private String alamat;
    private int no_telepon;
    private String email;

    public Admin(String user_id, String password, int id_admin, String nama_lengkap, String alamat, int no_telepon, String email) {
        this.user_id = user_id;
        this.password = password;
        this.id_admin = id_admin;
        this.nama_lengkap = nama_lengkap;
        this.alamat = alamat;
        this.no_telepon = no_telepon;
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPassword() {
        return password;
    }

    public int getId_admin() {
        return id_admin;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public String getAlamat() {
        return alamat;
    }

    public int getNo_telepon() {
        return no_telepon;
    }

    public String getEmail() {
        return email;
    }
    
    
    
    public boolean loginUser(){
        boolean isAuthenticated = false;
        
        try {
            openConnection();

            String query = "SELECT * FROM akun WHERE user_id = ? AND password = ?";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, user_id);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isAuthenticated = true;
            }
        } catch (SQLException ex) {
            displayErrors(ex);
        } finally {
            closeConnection();
        }

        return isAuthenticated;
    }
    
    public boolean registerUser() {
        boolean isRegistered = false;

        try {
            openConnection();

            String query = "INSERT INTO akun (user_id, password) VALUES (?, ?)";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, user_id);
            preparedStatement.setString(2, password);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                isRegistered = true;
            }
        } catch (SQLException ex) {
            displayErrors(ex);
        } finally {
            closeConnection();
        }

        return isRegistered;
    } 
    
    public boolean createOrUpdateDataAdmin() {
        try {
            openConnection();
            getConnection().setAutoCommit(false); 

            String queryDeleteOld = "DELETE FROM `admin` WHERE `user_id` = ?";
            preparedStatement = getConnection().prepareStatement(queryDeleteOld);
            preparedStatement.setString(1, user_id); 

            preparedStatement.executeUpdate();

            String queryInsert = "INSERT INTO `admin`(`id_admin`, `nama_lengkap`, `alamat`, `no_telepon`, `email`, `user_id`) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = getConnection().prepareStatement(queryInsert);
            preparedStatement.setInt(1, id_admin);
            preparedStatement.setString(2, nama_lengkap);
            preparedStatement.setString(3, alamat);
            preparedStatement.setInt(4, no_telepon);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, user_id);

            preparedStatement.executeUpdate();

            getConnection().commit(); 
            return true;
        } catch (SQLException ex) {
            try {
                if (getConnection() != null) {
                    getConnection().rollback(); 
                }
            } catch (SQLException rollbackEx) {
                displayErrors(rollbackEx);
            }

            displayErrors(ex);
            return false;
        } finally {
            try {
                getConnection().setAutoCommit(true); 
            } catch (SQLException autoCommitEx) {
                displayErrors(autoCommitEx);
            }
            closeConnection();
        }
    }

    
    public boolean readUser(String user){
        boolean isAuthenticated = false;
        
        try {
            openConnection();

            String query = "SELECT * FROM admin WHERE user_id = ?";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, user);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.id_admin = resultSet.getInt("id_admin");
                this.nama_lengkap = resultSet.getString("nama_lengkap");
                this.alamat = resultSet.getString("alamat");
                this.no_telepon = resultSet.getInt("no_telepon");
                this.email = resultSet.getString("email");
                 
                isAuthenticated = true;
                
//                System.out.println(id_admin + nama_lengkap + alamat + no_telepon + email);
            }
        } catch (SQLException ex) {
            displayErrors(ex);
        } finally {
            closeConnection();
        }

        return isAuthenticated;
    }
}
