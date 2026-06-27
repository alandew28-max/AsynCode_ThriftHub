/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LOQ
 */
public class ProdukBundle extends Produk{
    
    public ProdukBundle(int id, String nama, double harga){
        super(id, nama, harga);
    }

    @Override
    public String tampilInfo() {
        return "Produk Bundle : " + getNamaProduk();
    }
}
