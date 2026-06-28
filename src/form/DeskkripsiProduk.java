/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package form;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import asyncode_thrifthub.Session;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
/**
 *
 * @author LOQ
 */
public class DeskkripsiProduk extends javax.swing.JFrame {
    private JLabel lblGambar = new JLabel();
    private int idProdukAktif = -1;
    private String kategoriAktif = "";
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DeskkripsiProduk.class.getName());

    /**
     * Creates new form DeskkripsiProduk
     */
    public DeskkripsiProduk() {
    initComponents();

    GambarProduk.removeAll();
    GambarProduk.setLayout(new BorderLayout());

    lblGambar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    lblGambar.setVerticalAlignment(javax.swing.SwingConstants.CENTER);

    GambarProduk.add(lblGambar, BorderLayout.CENTER);

    GambarProduk.revalidate();
    GambarProduk.repaint();

    Beli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
}

public DeskkripsiProduk(int idProduk) {
    this();

    idProdukAktif = idProduk;
    loadDetailProduk(idProduk);
}
private void tampilkanGambarProduk(String pathGambar) {
    try {
        if (pathGambar == null || pathGambar.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Path gambar produk kosong di database.");
            return;
        }

        File fileGambar = new File(pathGambar);

        if (!fileGambar.exists()) {
            JOptionPane.showMessageDialog(this, "File gambar tidak ditemukan:\n" + pathGambar);
            return;
        }

        ImageIcon iconAsli = new ImageIcon(pathGambar);

        Image gambarResize = iconAsli.getImage().getScaledInstance(
                GambarProduk.getWidth() > 0 ? GambarProduk.getWidth() : 308,
                GambarProduk.getHeight() > 0 ? GambarProduk.getHeight() : 298,
                Image.SCALE_SMOOTH
        );

        lblGambar.setIcon(new ImageIcon(gambarResize));
        lblGambar.setText("");

        GambarProduk.removeAll();
        GambarProduk.setLayout(new BorderLayout());
        GambarProduk.add(lblGambar, BorderLayout.CENTER);

        GambarProduk.revalidate();
        GambarProduk.repaint();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menampilkan gambar: " + e.getMessage());
    }
}
private void loadDetailProduk(int idProduk) {
    try {
        Connection conn = koneksi.getConnection();

        String sql = "SELECT * FROM produk WHERE id_produk = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, idProduk);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String namaProduk = rs.getString("nama_produk");
            String hargaProduk = rs.getString("harga");
            String deskripsiProduk = rs.getString("deskripsi");
            String gambarProduk = rs.getString("gambar");
            String kategoriProduk = rs.getString("kategori");

            kategoriAktif = kategoriProduk;

            NamaProduk.setText(namaProduk);
            Harga.setText("Rp " + hargaProduk);
            Deskripsi.setText("<html><body style='width:300px'>" + deskripsiProduk + "</body></html>");

            tampilkanGambarProduk(gambarProduk);

            loadProdukLain(kategoriProduk, idProduk);

        } else {
            JOptionPane.showMessageDialog(this, "Produk tidak ditemukan. ID produk: " + idProduk);
        }

        rs.close();
        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menampilkan detail produk: " + e.getMessage());
    }
}private void loadProdukLain(String kategori, int idProdukSekarang) {
    try {
        jPanel1.removeAll();

        int lebarPanel = 823;
        int jarak = 20;

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, jarak, jarak));
        jPanel1.setPreferredSize(new Dimension(lebarPanel, 260));

        Connection conn = koneksi.getConnection();

        String sql = "SELECT * FROM produk "
                + "WHERE status = 'aktif' "
                + "AND kategori = ? "
                + "AND id_produk <> ? "
                + "ORDER BY id_produk DESC";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, kategori);
        pst.setInt(2, idProdukSekarang);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            int idProduk = rs.getInt("id_produk");
            String namaProduk = rs.getString("nama_produk");
            String hargaProduk = rs.getString("harga");
            String pathGambar = rs.getString("gambar");

            JPanel card = new JPanel();
            card.setPreferredSize(new Dimension(170, 230));
            card.setBackground(java.awt.Color.WHITE);
            card.setLayout(new javax.swing.BoxLayout(card, javax.swing.BoxLayout.Y_AXIS));

            card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)),
                    javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            JLabel gambar = new JLabel();
            gambar.setAlignmentX(Component.CENTER_ALIGNMENT);
            gambar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            gambar.setPreferredSize(new Dimension(140, 140));
            gambar.setMaximumSize(new Dimension(140, 140));

            ImageIcon icon = new ImageIcon(pathGambar);
            Image imgProduk = icon.getImage().getScaledInstance(
                    140,
                    140,
                    Image.SCALE_SMOOTH
            );

            gambar.setIcon(new ImageIcon(imgProduk));

            JLabel nama = new JLabel(namaProduk, javax.swing.SwingConstants.CENTER);
            nama.setAlignmentX(Component.CENTER_ALIGNMENT);
            nama.setMaximumSize(new Dimension(150, 25));

            JLabel harga = new JLabel("Rp " + hargaProduk, javax.swing.SwingConstants.CENTER);
            harga.setAlignmentX(Component.CENTER_ALIGNMENT);
            harga.setMaximumSize(new Dimension(150, 25));
            harga.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));

            card.add(gambar);
            card.add(javax.swing.Box.createVerticalStrut(8));
            card.add(nama);
            card.add(javax.swing.Box.createVerticalStrut(5));
            card.add(harga);

            card.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            card.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    DeskkripsiProduk detail = new DeskkripsiProduk(idProduk);
                    detail.setLocationRelativeTo(null);
                    detail.setVisible(true);
                    dispose();
                }
            });

            jPanel1.add(card);
        }

        int jumlahProduk = jPanel1.getComponentCount();
        int jumlahBaris = (int) Math.ceil(jumlahProduk / 4.0);
        int tinggiPanel = jumlahBaris * 260;

        jPanel1.setPreferredSize(new Dimension(lebarPanel, tinggiPanel));

        jPanel1.revalidate();
        jPanel1.repaint();

        rs.close();
        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menampilkan produk lain: " + e.getMessage());
    }
}
private void prosesBeli() {
    if (idProdukAktif == -1) {
        JOptionPane.showMessageDialog(this, "Produk belum dipilih");
        return;
    }

    if (Session.emailLogin == null || Session.emailLogin.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Silahkan login terlebih dahulu");
        return;
    }

    String alamat = JOptionPane.showInputDialog(
            this,
            "Masukkan alamat pengiriman:"
    );

    if (alamat == null) {
        return;
    }

    alamat = alamat.trim();

    if (alamat.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Alamat tidak boleh kosong");
        return;
    }

    int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Apakah anda yakin?",
            "Konfirmasi Pembelian",
            JOptionPane.YES_NO_OPTION
    );

    if (konfirmasi != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        Connection conn = koneksi.getConnection();

        String sql = "INSERT INTO pembelian (id_user, nama_pembeli, id_produk, alamat) "
        + "SELECT id_user, COALESCE(NULLIF(nama, ''), alamat_email), ?, ? "
        + "FROM users WHERE alamat_email = ? LIMIT 1";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, idProdukAktif);
        pst.setString(2, alamat);
        pst.setString(3, Session.emailLogin);

        int hasil = pst.executeUpdate();

        if (hasil > 0) {
            JOptionPane.showMessageDialog(this, "Pembelian berhasil disimpan");
        } else {
            JOptionPane.showMessageDialog(this, "Akun pembeli tidak ditemukan");
        }

        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan pembelian: " + e.getMessage());
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        GambarProduk = new javax.swing.JPanel();
        NamaProduk = new javax.swing.JLabel();
        Harga = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Beli = new javax.swing.JButton();
        kembali = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Deskripsi = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        GambarProduk.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout GambarProdukLayout = new javax.swing.GroupLayout(GambarProduk);
        GambarProduk.setLayout(GambarProdukLayout);
        GambarProdukLayout.setHorizontalGroup(
            GambarProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 308, Short.MAX_VALUE)
        );
        GambarProdukLayout.setVerticalGroup(
            GambarProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );

        NamaProduk.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        NamaProduk.setText("nama Produk");

        Harga.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        Harga.setText("Harga");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 823, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 243, Short.MAX_VALUE)
        );

        Beli.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        Beli.setText("Beli");
        Beli.addActionListener(this::BeliActionPerformed);

        kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return.png"))); // NOI18N
        kembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kembaliMouseClicked(evt);
            }
        });

        Deskripsi.setColumns(20);
        Deskripsi.setRows(5);
        jScrollPane2.setViewportView(Deskripsi);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(GambarProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(Beli, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(Harga, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(NamaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(152, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(kembali)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GambarProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(NamaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Harga, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(Beli, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(45, 45, 45)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BeliActionPerformed
    prosesBeli();

    }//GEN-LAST:event_BeliActionPerformed

    private void kembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kembaliMouseClicked
        Beranda beranda = new Beranda();
        beranda.setLocationRelativeTo(null);
        beranda.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_kembaliMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new DeskkripsiProduk().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Beli;
    private javax.swing.JTextArea Deskripsi;
    private javax.swing.JPanel GambarProduk;
    private javax.swing.JLabel Harga;
    private javax.swing.JLabel NamaProduk;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel kembali;
    // End of variables declaration//GEN-END:variables
}
