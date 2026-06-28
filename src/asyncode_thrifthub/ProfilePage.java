/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package asyncode_thrifthub;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import form.Beranda;
import javax.swing.JOptionPane;
import java.io.File;
import java.util.Date;
import java.sql.ResultSet;

/**

/**
 *
 * @author Sulistio
 */
public class ProfilePage extends javax.swing.JFrame {
    private JLabel lblGambar = new JLabel();
    private String pathFoto = null;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ProfilePage.class.getName());

    /**
     * Creates new form ProfilePage
     */
    public ProfilePage() {
    initComponents();

    // Kunci ukuran panel foto
    Photo.setPreferredSize(new java.awt.Dimension(220, 244));
    Photo.setMinimumSize(new java.awt.Dimension(220, 244));
    Photo.setSize(220, 244);
    Photo.setLayout(null);
    Photo.setOpaque(true);

    // Label untuk gambar profil
    lblGambar.setBounds(0, 0, 220, 244);
    Photo.add(lblGambar);

    // Label Edit agar tetap muncul
    jLabel7.setBounds(177, 6, 37, 20);
    Photo.add(jLabel7);
    Photo.setComponentZOrder(jLabel7, 0);

    buttonGroup1.add(jCheckBox1);
    buttonGroup1.add(jCheckBox2);

    jLabel5.setText(Session.emailLogin);
    
    loadProfile();
    
    jButton1.addActionListener(e -> simpanProfile());

    Photo.revalidate();
    Photo.repaint();
    pack();
    }
    private void loadProfile() {
    String email = Session.emailLogin;

    if (email == null || email.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Email login tidak ditemukan.");
        return;
    }

    try {
        Connection conn = getConnection();

        String sql = "SELECT * FROM users WHERE alamat_email = ? LIMIT 1";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, email);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            jLabel5.setText(rs.getString("alamat_email"));

            String nama = rs.getString("nama");
            String nomorTelp = rs.getString("nomor_telp");
            String jenisKelamin = rs.getString("jenis_kelamin");
            java.sql.Date tanggalLahir = rs.getDate("tanggal_lahir");
            String foto = rs.getString("Foto");

            if (nama != null) {
                jTextField1.setText(nama);
            }

            if (nomorTelp != null) {
                jTextField2.setText(nomorTelp);
            }

            if ("Laki - Laki".equalsIgnoreCase(jenisKelamin)) {
                jCheckBox1.setSelected(true);
            } else if ("Perempuan".equalsIgnoreCase(jenisKelamin)) {
                jCheckBox2.setSelected(true);
            }

            if (tanggalLahir != null) {
                jDateChooser1.setDate(tanggalLahir);
            }

            if (foto != null && !foto.isEmpty()) {
                File fileFoto = new File(foto);

                if (fileFoto.exists()) {
                    pathFoto = foto;

                    ImageIcon icon = new ImageIcon(foto);
                    Image gambar = icon.getImage().getScaledInstance(
                            220,
                            244,
                            Image.SCALE_SMOOTH
                    );

                    lblGambar.setIcon(new ImageIcon(gambar));
                    lblGambar.setBounds(0, 0, 220, 244);

                    jLabel7.setBounds(177, 6, 37, 20);
                    Photo.setComponentZOrder(jLabel7, 0);

                    Photo.revalidate();
                    Photo.repaint();
                }
            }
        }

        rs.close();
        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menampilkan profil: " + e.getMessage());
    }
}
    
    private void simpanProfile() {
    String nama = jTextField1.getText();
    String email = jLabel5.getText();
    String nomorTelp = jTextField2.getText();

    String jenisKelamin = "";
    if (jCheckBox1.isSelected()) {
        jenisKelamin = "Laki - Laki";
    } else if (jCheckBox2.isSelected()) {
        jenisKelamin = "Perempuan";
    }

    if (nama.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nama belum diisi");
        return;
    }

    if (email.equals(".........") || email.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Email belum terdeteksi");
        return;
    }

    if (nomorTelp.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nomor telepon belum diisi");
        return;
    }

    if (jenisKelamin.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Jenis kelamin belum dipilih");
        return;
    }

    if (jDateChooser1.getDate() == null) {
        JOptionPane.showMessageDialog(this, "Tanggal lahir belum dipilih");
        return;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String tanggalLahir = sdf.format(jDateChooser1.getDate());

    try {
        Connection conn = getConnection();

        String sql = "UPDATE users SET nama = ?, nomor_telp = ?, jenis_kelamin = ?, tanggal_lahir = ?, Foto = ? WHERE alamat_email = ?";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, nama);
        pst.setString(2, nomorTelp);
        pst.setString(3, jenisKelamin);
        pst.setString(4, tanggalLahir);
        pst.setString(5, pathFoto);
        pst.setString(6, email);

        int hasil = pst.executeUpdate();

        if (hasil > 0) {
            JOptionPane.showMessageDialog(this, "Profil berhasil disimpan");
            loadProfile();
        } else {
            JOptionPane.showMessageDialog(this, "Data user tidak ditemukan. Cek email login.");
        }

        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan profil: " + e.getMessage());
    }
}
    private Connection getConnection() throws SQLException {
    String url = "jdbc:mysql://localhost:3306/toko_pakaian";
    String user = "root";
    String pass = "";

    return DriverManager.getConnection(url, user, pass);
}
   
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        Photo = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        kembali = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Photo.setBackground(new java.awt.Color(51, 255, 51));

        jLabel7.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel7.setText("Edit");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout PhotoLayout = new javax.swing.GroupLayout(Photo);
        Photo.setLayout(PhotoLayout);
        PhotoLayout.setHorizontalGroup(
            PhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PhotoLayout.createSequentialGroup()
                .addContainerGap(177, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PhotoLayout.setVerticalGroup(
            PhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PhotoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(221, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel1.setText("Nama :");

        jLabel2.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel2.setText("Email :");

        jLabel3.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel3.setText("Jenis Kelamin :");

        jLabel4.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel4.setText("Tanggal Lahir :");

        jCheckBox1.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jCheckBox1.setText("Laki - Laki");

        jCheckBox2.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jCheckBox2.setText("Perempuan");
        jCheckBox2.addActionListener(this::jCheckBox2ActionPerformed);

        jLabel5.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel5.setText(".........");

        jTextField1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 14)); // NOI18N
        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        jButton1.setText("Simpan");

        jLabel6.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel6.setText("Nomor Telp :");

        jTextField2.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jTextField2.addActionListener(this::jTextField2ActionPerformed);

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
                .addGap(215, 215, 215)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Photo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jCheckBox1)
                                        .addGap(18, 18, 18)
                                        .addComponent(jCheckBox2)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextField2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 385, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(307, 307, 307))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(kembali)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCheckBox1)
                                    .addComponent(jCheckBox2)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(Photo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(152, 152, 152))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
    JFileChooser chooser = new JFileChooser();

    int hasil = chooser.showOpenDialog(this);

    if (hasil == JFileChooser.APPROVE_OPTION) {

        pathFoto = chooser.getSelectedFile().getAbsolutePath();

        ImageIcon icon = new ImageIcon(pathFoto);

        int lebar = 220;
        int tinggi = 244;

        Image gambar = icon.getImage().getScaledInstance(
                lebar,
                tinggi,
                Image.SCALE_SMOOTH
        );

        lblGambar.setIcon(new ImageIcon(gambar));
        lblGambar.setBounds(0, 0, lebar, tinggi);

        jLabel7.setBounds(177, 6, 37, 20);
        Photo.setComponentZOrder(jLabel7, 0);

        Photo.revalidate();
        Photo.repaint();
    }

    }//GEN-LAST:event_jLabel7MouseClicked

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
        java.awt.EventQueue.invokeLater(() -> new ProfilePage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Photo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel kembali;
    // End of variables declaration//GEN-END:variables
}
