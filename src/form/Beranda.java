/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package form;
import asyncode_thrifthub.Profile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import asyncode_thrifthub.Session;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 *
 * @author LOQ
 */
public class Beranda extends javax.swing.JFrame {
    private JLabel lblBanner = new JLabel();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Beranda.class.getName());

    /**
     * Creates new form Beranda
     */
    public Beranda() {
    initComponents();

    setupBannerPanel();
    loadBanner();

    panelProduk.setLayout(new java.awt.GridLayout(0, 4, 20, 20));
    panelProduk.setBackground(new java.awt.Color(245, 245, 245));

    boolean admin = "admin".equalsIgnoreCase(Session.roleLogin);

    if (admin) {
        toko.setVisible(true);
        toko.setEnabled(true);

        Edit.setVisible(true);
        Edit.setEnabled(true);
        Edit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        Edit.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pilihBanner();
            }
        });

    } else {
        toko.setVisible(false);
        toko.setEnabled(false);

        Edit.setVisible(false);
        Edit.setEnabled(false);
    }

    loadProduk(null);
}
    private void tampilkanBanner(String path) {
    File file = new File(path);

    if (!file.exists()) {
        return;
    }

    ImageIcon icon = new ImageIcon(path);

    Image gambar = icon.getImage().getScaledInstance(
            982,
            180,
            Image.SCALE_SMOOTH
    );

    lblBanner.setIcon(new ImageIcon(gambar));
    lblBanner.setBounds(0, 0, 982, 180);

    Banner.setComponentZOrder(lblBanner, Banner.getComponentCount() - 1);
    Banner.setComponentZOrder(Edit, 0);

    Banner.revalidate();
    Banner.repaint();
}
    private void loadBanner() {
    try {
        Connection conn = koneksi.getConnection();

        String sql = "SELECT banner_promosi FROM users "
                + "WHERE LOWER(role) = 'admin' "
                + "AND banner_promosi IS NOT NULL "
                + "ORDER BY id_user DESC LIMIT 1";

        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String pathBanner = rs.getString("banner_promosi");

            if (pathBanner != null && !pathBanner.trim().isEmpty()) {
                tampilkanBanner(pathBanner);
            }
        }

        rs.close();
        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menampilkan banner promosi: " + e.getMessage());
    }
}
    private void pilihBanner() {
    if (!"admin".equalsIgnoreCase(Session.roleLogin)) {
        JOptionPane.showMessageDialog(this, "Banner hanya bisa diubah oleh admin");
        return;
    }

    JFileChooser chooser = new JFileChooser();

    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "File Gambar",
            "jpg", "jpeg", "png"
    );

    chooser.setFileFilter(filter);

    int hasil = chooser.showOpenDialog(this);

    if (hasil == JFileChooser.APPROVE_OPTION) {
        String path = chooser.getSelectedFile().getAbsolutePath();

        try {
            Connection conn = koneksi.getConnection();

            String sql = "UPDATE users SET banner_promosi = ? WHERE alamat_email = ? AND LOWER(role) = 'admin'";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, path);
            pst.setString(2, Session.emailLogin);

            int result = pst.executeUpdate();

            if (result > 0) {
                tampilkanBanner(path);
                JOptionPane.showMessageDialog(this, "Banner berhasil disimpan");
            } else {
                JOptionPane.showMessageDialog(this, "Banner gagal disimpan. Akun admin tidak ditemukan.");
            }

            pst.close();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan banner: " + e.getMessage());
        }
    }
}
private void setupBannerPanel() {
    int lebar = 982;
    int tinggi = 180;

    Banner.setLayout(null);
    Banner.setPreferredSize(new java.awt.Dimension(lebar, tinggi));
    Banner.setMinimumSize(new java.awt.Dimension(lebar, tinggi));
    Banner.setSize(lebar, tinggi);

    lblBanner.setBounds(0, 0, lebar, tinggi);
    lblBanner.setOpaque(true);
    lblBanner.setBackground(new java.awt.Color(68, 116, 89));

    if (lblBanner.getParent() == null) {
        Banner.add(lblBanner);
    }

    Edit.setBounds(939, 6, 37, 20);

    Banner.setComponentZOrder(lblBanner, Banner.getComponentCount() - 1);
    Banner.setComponentZOrder(Edit, 0);

    Banner.revalidate();
    Banner.repaint();
}
    private void loadProduk(String kategori) {
    try {
        panelProduk.removeAll();

        int lebarPanel = 982;
        int jarak = 20;

        panelProduk.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, jarak, jarak));
        panelProduk.setPreferredSize(new java.awt.Dimension(lebarPanel, 260));

        Connection conn = koneksi.getConnection();

        String sql;

        if (kategori == null) {
            sql = "SELECT * FROM produk WHERE status='aktif' ORDER BY id_produk DESC";
        } else {
            sql = "SELECT * FROM produk WHERE status='aktif' AND kategori=? ORDER BY id_produk DESC";
        }

        PreparedStatement pst = conn.prepareStatement(sql);

        if (kategori != null) {
            pst.setString(1, kategori);
        }

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            int idProduk = rs.getInt("id_produk");

            javax.swing.JPanel card = new javax.swing.JPanel();
            card.setPreferredSize(new java.awt.Dimension(170, 230));
            card.setMaximumSize(new java.awt.Dimension(170, 230));
            card.setBackground(java.awt.Color.WHITE);
            card.setLayout(new javax.swing.BoxLayout(card, javax.swing.BoxLayout.Y_AXIS));

            java.awt.Color warnaNormal = java.awt.Color.WHITE;
            java.awt.Color warnaHover = new java.awt.Color(245, 250, 247);

            javax.swing.border.Border borderNormal = javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)),
                    javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)
            );

            javax.swing.border.Border borderHover = javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createLineBorder(new java.awt.Color(44, 74, 59), 2),
                    javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)
            );

            card.setBorder(borderNormal);

            JLabel gambar = new JLabel();
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

            JLabel nama = new JLabel(
                    rs.getString("nama_produk"),
                    javax.swing.SwingConstants.CENTER
            );
            nama.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            nama.setMaximumSize(new java.awt.Dimension(150, 25));

            JLabel harga = new JLabel(
                    "Rp " + rs.getString("harga"),
                    javax.swing.SwingConstants.CENTER
            );
            harga.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            harga.setMaximumSize(new java.awt.Dimension(150, 25));
            harga.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));

            card.add(gambar);
            card.add(javax.swing.Box.createVerticalStrut(8));
            card.add(nama);
            card.add(javax.swing.Box.createVerticalStrut(5));
            card.add(harga);

            card.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            gambar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            nama.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            harga.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            java.awt.event.MouseAdapter efekProduk = new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    card.setBackground(warnaHover);
                    card.setBorder(borderHover);
                    card.repaint();
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    card.setBackground(warnaNormal);
                    card.setBorder(borderNormal);
                    card.repaint();
                }

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    DeskkripsiProduk detail = new DeskkripsiProduk(idProduk);
                    detail.setLocationRelativeTo(null);
                    detail.setVisible(true);
                    dispose();
                }
            };

            card.addMouseListener(efekProduk);
            gambar.addMouseListener(efekProduk);
            nama.addMouseListener(efekProduk);
            harga.addMouseListener(efekProduk);

            panelProduk.add(card);
        }

        int jumlahProduk = panelProduk.getComponentCount();
        int jumlahBaris = (int) Math.ceil(jumlahProduk / 4.0);

        if (jumlahBaris < 1) {
            jumlahBaris = 1;
        }

        int tinggiPanel = jumlahBaris * 260;

        panelProduk.setPreferredSize(new java.awt.Dimension(lebarPanel, tinggiPanel));
        panelProduk.setMinimumSize(new java.awt.Dimension(lebarPanel, tinggiPanel));

        panelProduk.revalidate();
        panelProduk.repaint();

        jPanel1.revalidate();
        jPanel1.repaint();

        rs.close();
        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
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
        Banner = new javax.swing.JPanel();
        Edit = new javax.swing.JLabel();
        panelProduk = new javax.swing.JPanel();
        Logout = new javax.swing.JLabel();
        toko = new javax.swing.JLabel();
        Semua1 = new javax.swing.JButton();
        Kaos = new javax.swing.JButton();
        Celana = new javax.swing.JButton();
        Hoodie = new javax.swing.JButton();
        Jaket = new javax.swing.JButton();
        Sepatu = new javax.swing.JButton();
        Kemeja = new javax.swing.JButton();
        Profil = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        Banner.setBackground(new java.awt.Color(68, 116, 89));
        Banner.setPreferredSize(new java.awt.Dimension(982, 180));

        Edit.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        Edit.setText("Edit");

        javax.swing.GroupLayout BannerLayout = new javax.swing.GroupLayout(Banner);
        Banner.setLayout(BannerLayout);
        BannerLayout.setHorizontalGroup(
            BannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BannerLayout.createSequentialGroup()
                .addContainerGap(939, Short.MAX_VALUE)
                .addComponent(Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        BannerLayout.setVerticalGroup(
            BannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BannerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Edit)
                .addContainerGap(156, Short.MAX_VALUE))
        );

        panelProduk.setBackground(new java.awt.Color(255, 255, 255));
        panelProduk.setLayout(new java.awt.GridLayout(1, 0));

        Logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/door.png"))); // NOI18N
        Logout.setText("jLabel1");
        Logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogoutMouseClicked(evt);
            }
        });

        toko.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/shop.png"))); // NOI18N
        toko.setText("Toko");
        toko.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tokoMouseClicked(evt);
            }
        });

        Semua1.setBackground(new java.awt.Color(44, 74, 59));
        Semua1.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        Semua1.setForeground(new java.awt.Color(255, 255, 255));
        Semua1.setText("Semua");
        Semua1.addActionListener(this::Semua1ActionPerformed);

        Kaos.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        Kaos.setForeground(new java.awt.Color(44, 74, 59));
        Kaos.setText("Kaos");
        Kaos.addActionListener(this::KaosActionPerformed);

        Celana.setBackground(new java.awt.Color(44, 74, 59));
        Celana.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        Celana.setForeground(new java.awt.Color(255, 255, 255));
        Celana.setText("Celana");
        Celana.addActionListener(this::CelanaActionPerformed);

        Hoodie.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        Hoodie.setForeground(new java.awt.Color(44, 74, 59));
        Hoodie.setText("Hoodie");
        Hoodie.addActionListener(this::HoodieActionPerformed);

        Jaket.setBackground(new java.awt.Color(44, 74, 59));
        Jaket.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        Jaket.setForeground(new java.awt.Color(255, 255, 255));
        Jaket.setText("Jaket");
        Jaket.addActionListener(this::JaketActionPerformed);

        Sepatu.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        Sepatu.setForeground(new java.awt.Color(44, 74, 59));
        Sepatu.setText("Sepatu");
        Sepatu.addActionListener(this::SepatuActionPerformed);

        Kemeja.setBackground(new java.awt.Color(44, 74, 59));
        Kemeja.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        Kemeja.setForeground(new java.awt.Color(255, 255, 255));
        Kemeja.setText("Kemeja");
        Kemeja.addActionListener(this::KemejaActionPerformed);

        Profil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/avatar.png"))); // NOI18N
        Profil.setText("Profil");
        Profil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProfilMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Profil, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toko, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelProduk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Banner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(Semua1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Kaos, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Celana, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Hoodie, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Jaket, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Sepatu, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Kemeja, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Profil, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Logout)
                        .addComponent(toko)))
                .addGap(18, 18, 18)
                .addComponent(Banner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Semua1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Kaos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Celana, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Hoodie, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Jaket, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Sepatu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Kemeja, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelProduk, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tokoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tokoMouseClicked
     if (!"admin".equalsIgnoreCase(Session.roleLogin)) {
        JOptionPane.showMessageDialog(this, "Menu Toko hanya untuk admin");
        return;
    }

    new AdminPenjual().setVisible(true);
    this.dispose();
    }//GEN-LAST:event_tokoMouseClicked

    private void Semua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Semua1ActionPerformed
    loadProduk(null);        
    }//GEN-LAST:event_Semua1ActionPerformed

    private void KaosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KaosActionPerformed
    loadProduk("Kaos");
    }//GEN-LAST:event_KaosActionPerformed

    private void CelanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CelanaActionPerformed
    loadProduk("Celana");
    }//GEN-LAST:event_CelanaActionPerformed

    private void HoodieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HoodieActionPerformed
    loadProduk("Hoodie");
    }//GEN-LAST:event_HoodieActionPerformed

    private void JaketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JaketActionPerformed
    loadProduk("Jaket");
    }//GEN-LAST:event_JaketActionPerformed

    private void SepatuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SepatuActionPerformed
    loadProduk("Sepatu");
    }//GEN-LAST:event_SepatuActionPerformed

    private void KemejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KemejaActionPerformed
    loadProduk("Kemeja");
    }//GEN-LAST:event_KemejaActionPerformed

    private void ProfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProfilMouseClicked
    Profile profile = new Profile();
    profile.setLocationRelativeTo(null);
    profile.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_ProfilMouseClicked

    private void LogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutMouseClicked
asyncode_thrifthub.Logout.logout(this);
    }//GEN-LAST:event_LogoutMouseClicked

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
        java.awt.EventQueue.invokeLater(() -> new Beranda().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Banner;
    private javax.swing.JButton Celana;
    private javax.swing.JLabel Edit;
    private javax.swing.JButton Hoodie;
    private javax.swing.JButton Jaket;
    private javax.swing.JButton Kaos;
    private javax.swing.JButton Kemeja;
    private javax.swing.JLabel Logout;
    private javax.swing.JLabel Profil;
    private javax.swing.JButton Semua1;
    private javax.swing.JButton Sepatu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelProduk;
    private javax.swing.JLabel toko;
    // End of variables declaration//GEN-END:variables
}
