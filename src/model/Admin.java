/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import interfaces.KelolaProduk;
/**
 *
 * @author LOQ
 */
    
public class Admin implements KelolaProduk {

     @Override
    public void tambahProduk(){
        System.out.println("Produk ditambahkan");
    }

    @Override
    public void hapusProduk() {
        System.out.println("Produk dihapus");
    }
    
}

