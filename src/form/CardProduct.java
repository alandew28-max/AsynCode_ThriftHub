/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;
import javax.swing.*;
import java.awt.*;

public class CardProduct extends JPanel{

    private JLabel GambarProduk;
    private JLabel NamaProduk;
    private JLabel Harga;

    public CardProduct(String nama, String harga) {

        setPreferredSize(new Dimension(180, 280));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        setLayout(new BorderLayout());

        GambarProduk = new JLabel();
        GambarProduk.setPreferredSize(new Dimension(180, 180));
        GambarProduk.setOpaque(true);
        GambarProduk.setBackground(new Color(80,120,90));

        NamaProduk = new JLabel(nama);
        NamaProduk.setHorizontalAlignment(SwingConstants.CENTER);

        Harga = new JLabel(harga);
        Harga.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel info = new JPanel();
        info.setLayout(new GridLayout(2,1));

        info.add(NamaProduk);
        info.add(Harga);

        add(GambarProduk, BorderLayout.CENTER);
        add(info, BorderLayout.SOUTH);
    }
}


