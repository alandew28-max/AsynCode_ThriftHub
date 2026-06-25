/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class koneksi {

    public static Connection koneksi;

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/toko_pakaian";
            String user = "root";
            String password = "";

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            koneksi = DriverManager.getConnection(url, user, password);
            System.out.println("Koneksi Berhasil");
        } catch (SQLException e) {
            System.out.println("Koneksi Gagal : " + e.getMessage());
        }
        return koneksi;
    }
}