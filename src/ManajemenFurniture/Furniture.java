/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ManajemenFurniture;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FLOW
 */
public class Furniture extends Database{
    private String id;
    private String nama;
    private String kategori;
    private int harga;
    private int stok;

    public Furniture(String id, String nama, String kategori, int harga, int stok) {
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.harga = harga;
        this.stok = stok;
    }
    
    private boolean isProdukExist(String productId) throws SQLException {
        String query = "SELECT COUNT(*) FROM produk WHERE id_produk = ?";
        preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setString(1, productId);

        resultSet = preparedStatement.executeQuery();
        resultSet.next();

        int count = resultSet.getInt(1);

        return count > 0;
    }
    
    // CREATE
    public boolean createProduk() {
        try {
            openConnection();
            
            if (isProdukExist(id)) {
                JOptionPane.showMessageDialog(null, "ID produk sudah ada. Tidak dapat menambahkan produk dengan ID yang sama.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

            String query = "INSERT INTO produk (id_produk, nama_produk, kategori, harga, stok) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, nama);
            preparedStatement.setString(3, kategori);
            preparedStatement.setInt(4, harga);
            preparedStatement.setInt(5, stok);

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException ex) {
            displayErrors(ex);
            return false;
        } finally {
            closeConnection();
        }
    }
    
    public DefaultTableModel read() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            openConnection();

            String query = "SELECT * FROM produk";
            preparedStatement = getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            model.addColumn("ID");
            model.addColumn("Nama");
            model.addColumn("Kategori");
            model.addColumn("Harga");
            model.addColumn("Stok");

            while (resultSet.next()) {
                this.id = resultSet.getString("id_produk");
                this.nama = resultSet.getString("nama_produk");
                this.kategori = resultSet.getString("kategori");
                this.harga= resultSet.getInt("harga");
                this.stok= resultSet.getInt("stok");

                model.addRow(new Object[]{id, nama, kategori, harga, stok});
            }
        } catch (SQLException ex) {
            displayErrors(ex);
        } finally {
            closeConnection();
        }

        return model;
    }
    
    public boolean updateProduk() {
        try {
            openConnection();

            String query = "UPDATE produk SET nama_produk=?, kategori=?, harga=?, stok=? WHERE id_produk=?";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, nama);
            preparedStatement.setString(2, kategori);
            preparedStatement.setInt(3, harga);
            preparedStatement.setInt(4, stok);
            preparedStatement.setString(5, id);

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException ex) {
            displayErrors(ex);
            return false;
        } finally {
            closeConnection();
        }
    }

    public boolean deleteProduk() {
        try {
            openConnection();

            String query = "DELETE FROM produk WHERE id_produk=?";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, id);

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException ex) {
            displayErrors(ex);
            return false;
        } finally {
            closeConnection();
        }
    }

}
