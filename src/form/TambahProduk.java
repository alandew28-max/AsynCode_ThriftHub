/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package form;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
/**
 *
 * @author LOQ
 */
public class TambahProduk extends javax.swing.JFrame {
    private JLabel lblGambar = new JLabel();
    private int idEdit = -1;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TambahProduk.class.getName());

    /**
     * Creates new form TambahProduk
     */
    public TambahProduk() {
        initComponents();
        lblGambar.setBounds(0, 0, 152, 195);

    GambarProduk.setLayout(null);
    GambarProduk.add(lblGambar);

        loadKategori();
        loadHistoryProduk();
    }
    public TambahProduk(int idProduk) {
    this();

    idEdit = idProduk;
    Tambah.setText("Update");

    loadDataEdit(idProduk);
}
    private void loadDataEdit(int idProduk) {
    try {
        Connection conn = koneksi.getConnection();

        String sql = "SELECT * FROM produk WHERE id_produk=?";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, idProduk);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            NamaProduk.setText(rs.getString("nama_produk"));
            HargaProduk.setText(rs.getString("harga"));
            DeskripsiProduk.setText(rs.getString("deskripsi"));
            Kategori.setSelectedItem(rs.getString("kategori"));
            img.setText(rs.getString("gambar"));

            ImageIcon icon = new ImageIcon(rs.getString("gambar"));
            Image gambar = icon.getImage().getScaledInstance(
                    152,
                    195,
                    Image.SCALE_SMOOTH
            );

            lblGambar.setIcon(new ImageIcon(gambar));
            lblGambar.setBounds(0, 0, 152, 195);

            GambarProduk.repaint();
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    private void loadHistoryProduk() {
    try {
        panelHistory.removeAll();
        panelHistory.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 15));

        Connection conn = koneksi.getConnection();

        String sql = "SELECT * FROM produk ORDER BY id_produk DESC";

        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while(rs.next()) {

    javax.swing.JPanel card = new javax.swing.JPanel();
    card.setPreferredSize(new java.awt.Dimension(170, 240));
    card.setBackground(java.awt.Color.WHITE);
    card.setLayout(new javax.swing.BoxLayout(card, javax.swing.BoxLayout.Y_AXIS));

    card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)),
        javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));

    javax.swing.JLabel gambar = new javax.swing.JLabel();
    gambar.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
    gambar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    gambar.setPreferredSize(new java.awt.Dimension(140, 140));
    gambar.setMaximumSize(new java.awt.Dimension(140, 140));

    ImageIcon icon = new ImageIcon(rs.getString("gambar"));
    Image imgProduk = icon.getImage().getScaledInstance(
            140,
            140,
            Image.SCALE_SMOOTH
    );
    gambar.setIcon(new ImageIcon(imgProduk));

    javax.swing.JLabel nama = new javax.swing.JLabel(
            rs.getString("nama_produk"),
            javax.swing.SwingConstants.CENTER
    );
    nama.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
    nama.setMaximumSize(new java.awt.Dimension(150, 25));

    javax.swing.JLabel harga = new javax.swing.JLabel(
            "Rp " + rs.getString("harga"),
            javax.swing.SwingConstants.CENTER
    );
    harga.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
    harga.setMaximumSize(new java.awt.Dimension(150, 25));
    harga.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));

    card.add(gambar);
    card.add(javax.swing.Box.createVerticalStrut(8));
    card.add(nama);
    card.add(javax.swing.Box.createVerticalStrut(4));
    card.add(harga);

    panelHistory.add(card);
}

    } catch(Exception e) {
        e.printStackTrace();
    }
}
    private void loadKategori() {

    try {

        Connection conn = koneksi.getConnection();

        String sql = "SELECT nama_kategori FROM kategori";

        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        Kategori.removeAllItems();

        while(rs.next()) {
            Kategori.addItem(
                rs.getString("nama_kategori")
            );
        }

    } catch(Exception e) {
        e.printStackTrace();
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
        jPanel1 = new javax.swing.JPanel();
        GambarProduk = new javax.swing.JPanel();
        Tambah = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        NamaProduk = new javax.swing.JTextField();
        HargaProduk = new javax.swing.JTextField();
        DeskripsiProduk = new javax.swing.JTextField();
        Kategori = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        panelHistory = new javax.swing.JPanel();
        Browse = new javax.swing.JLabel();
        img = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        kembali = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        GambarProduk.setBackground(new java.awt.Color(153, 255, 153));
        GambarProduk.setPreferredSize(new java.awt.Dimension(152, 195));

        javax.swing.GroupLayout GambarProdukLayout = new javax.swing.GroupLayout(GambarProduk);
        GambarProduk.setLayout(GambarProdukLayout);
        GambarProdukLayout.setHorizontalGroup(
            GambarProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 152, Short.MAX_VALUE)
        );
        GambarProdukLayout.setVerticalGroup(
            GambarProdukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 193, Short.MAX_VALUE)
        );

        Tambah.setText("Tambah");
        Tambah.addActionListener(this::TambahActionPerformed);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Kategori Produk");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Nama Produk");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Deskripsi Produk");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Harga Produk");

        NamaProduk.addActionListener(this::NamaProdukActionPerformed);

        HargaProduk.addActionListener(this::HargaProdukActionPerformed);

        Kategori.addActionListener(this::KategoriActionPerformed);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("History Produk");

        panelHistory.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout panelHistoryLayout = new javax.swing.GroupLayout(panelHistory);
        panelHistory.setLayout(panelHistoryLayout);
        panelHistoryLayout.setHorizontalGroup(
            panelHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 901, Short.MAX_VALUE)
        );
        panelHistoryLayout.setVerticalGroup(
            panelHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 429, Short.MAX_VALUE)
        );

        Browse.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Browse.setText("Browse");
        Browse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BrowseMouseClicked(evt);
            }
        });

        img.addActionListener(this::imgActionPerformed);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Image Produk");

        kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/return.png"))); // NOI18N
        kembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kembaliMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(294, 294, 294)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(NamaProduk)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(HargaProduk, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Kategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(57, 57, 57)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DeskripsiProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(Browse, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(GambarProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(309, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(579, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(566, 566, 566)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(kembali)
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NamaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Browse))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(HargaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(DeskripsiProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(GambarProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(623, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(62, 62, 62)
                    .addComponent(jLabel7)
                    .addContainerGap(1323, Short.MAX_VALUE)))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1074, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NamaProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NamaProdukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NamaProdukActionPerformed

    private void HargaProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HargaProdukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HargaProdukActionPerformed

    private void imgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_imgActionPerformed

    private void BrowseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BrowseMouseClicked
JFileChooser chooser = new JFileChooser();

    int hasil = chooser.showOpenDialog(this);

    if (hasil == JFileChooser.APPROVE_OPTION) {

        String path = chooser.getSelectedFile().getAbsolutePath();

        img.setText(path);

        ImageIcon icon = new ImageIcon(path);

        Image gambar = icon.getImage().getScaledInstance(
                152,
                195,
                Image.SCALE_SMOOTH
        );

        lblGambar.setIcon(new ImageIcon(gambar));

        lblGambar.setBounds(0, 0, 152, 195);

        GambarProduk.repaint();
    }    }//GEN-LAST:event_BrowseMouseClicked

    private void kembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kembaliMouseClicked
    new AdminPenjual().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_kembaliMouseClicked

    private void KategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KategoriActionPerformed

    private void TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TambahActionPerformed
    try {

        Connection conn = koneksi.getConnection();

        String nama = NamaProduk.getText();
String harga = HargaProduk.getText();
String deskripsi = DeskripsiProduk.getText();
String kategori = Kategori.getSelectedItem().toString();
String gambar = img.getText();

PreparedStatement pst;

if (idEdit == -1) {
    String sql = "INSERT INTO produk "
            + "(nama_produk, harga, kategori, deskripsi, gambar, status) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

    pst = conn.prepareStatement(sql);

    pst.setString(1, nama);
    pst.setString(2, harga);
    pst.setString(3, kategori);
    pst.setString(4, deskripsi);
    pst.setString(5, gambar);
    pst.setString(6, "aktif");

    pst.executeUpdate();

    JOptionPane.showMessageDialog(this, "Produk berhasil ditambahkan!");

} else {
    String sql = "UPDATE produk SET "
            + "nama_produk=?, harga=?, kategori=?, deskripsi=?, gambar=?, status='aktif' "
            + "WHERE id_produk=?";

    pst = conn.prepareStatement(sql);

    pst.setString(1, nama);
    pst.setString(2, harga);
    pst.setString(3, kategori);
    pst.setString(4, deskripsi);
    pst.setString(5, gambar);
    pst.setInt(6, idEdit);

    pst.executeUpdate();

    JOptionPane.showMessageDialog(this, "Produk berhasil diupdate!");
}

new AdminPenjual().setVisible(true);
this.dispose();
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                e.getMessage()
        );
    }
    }//GEN-LAST:event_TambahActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new TambahProduk().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Browse;
    private javax.swing.JTextField DeskripsiProduk;
    private javax.swing.JPanel GambarProduk;
    private javax.swing.JTextField HargaProduk;
    private javax.swing.JComboBox<String> Kategori;
    private javax.swing.JTextField NamaProduk;
    private javax.swing.JButton Tambah;
    private javax.swing.JTextField img;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel kembali;
    private javax.swing.JPanel panelHistory;
    // End of variables declaration//GEN-END:variables
}
