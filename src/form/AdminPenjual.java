/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package form;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;
import asyncode_thrifthub.Session;
import java.io.File;

/**
 *
 * @author LOQ
 */
public class AdminPenjual extends javax.swing.JFrame {
    private JLabel lblBanner = new JLabel();
    private String kategoriAktif = null;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminPenjual.class.getName());

    /**
     * Creates new form AdminPenjual
     */
    public AdminPenjual() {
        initComponents();

    setupBannerPanel();
    loadNamaToko();
    setupCursor();
    loadProduk(null);
    loadBanner();
    }
    private void loadBanner() {
    try {
        Connection conn = koneksi.getConnection();

        String sql = "SELECT banner_toko FROM users WHERE alamat_email = ? LIMIT 1";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, Session.emailLogin);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String pathBanner = rs.getString("banner_toko");

            if (pathBanner != null && !pathBanner.trim().isEmpty()) {
                tampilkanBanner(pathBanner);
            }
        }

        rs.close();
        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menampilkan banner toko: " + e.getMessage());
    }
}
    private void tampilkanBanner(String path) {
    File file = new File(path);

    if (!file.exists()) {
        return;
    }

    ImageIcon icon = new ImageIcon(path);

    Image gambar = icon.getImage().getScaledInstance(
            901,
            220,
            Image.SCALE_SMOOTH
    );

    lblBanner.setIcon(new ImageIcon(gambar));
    lblBanner.setBounds(0, 0, 901, 220);

    jPanel2.setComponentZOrder(lblBanner, jPanel2.getComponentCount() - 1);
    jPanel2.setComponentZOrder(Edit, 0);
    jPanel2.setComponentZOrder(NamaToko, 0);
    jPanel2.setComponentZOrder(jLabel3, 0);
    jPanel2.setComponentZOrder(jLabel6, 0);
    jPanel2.setComponentZOrder(jLabel4, 0);
    jPanel2.setComponentZOrder(jLabel5, 0);
    jPanel2.setComponentZOrder(jLabel2, 0);

    jPanel2.revalidate();
    jPanel2.repaint();
}
    private void setupCursor() {
    java.awt.Cursor cursorTangan = new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR);

    // JLabel yang bisa diklik
    Kembali.setCursor(cursorTangan);
    Logout.setCursor(cursorTangan);
    TambahProduk.setCursor(cursorTangan);
    Edit.setCursor(cursorTangan);

    // JButton kategori
    Semua.setCursor(cursorTangan);
    Kaos.setCursor(cursorTangan);
    Celana.setCursor(cursorTangan);
    Hoodie.setCursor(cursorTangan);
    Jaket.setCursor(cursorTangan);
    Sepatu.setCursor(cursorTangan);
    Kemeja.setCursor(cursorTangan);
}
    private void loadNamaToko() {
    try {
        Connection conn = koneksi.getConnection();

        String sql = "SELECT nama FROM users WHERE alamat_email = ? LIMIT 1";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, Session.emailLogin);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String nama = rs.getString("nama");

            if (nama != null && !nama.trim().isEmpty()) {
                NamaToko.setText(nama);
            } else {
                NamaToko.setText("Nama Toko");
            }
        }

        rs.close();
        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menampilkan nama toko: " + e.getMessage());
    }
}
    private void setupBannerPanel() {
    int lebar = 901;
    int tinggi = 220;

    jPanel2.setPreferredSize(new Dimension(lebar, tinggi));
    jPanel2.setMinimumSize(new Dimension(lebar, tinggi));
    jPanel2.setMaximumSize(new Dimension(lebar, tinggi));
    jPanel2.setSize(lebar, tinggi);

    jPanel2.setLayout(null);

    lblBanner.setBounds(0, 0, lebar, tinggi);
    lblBanner.setOpaque(true);
    lblBanner.setBackground(new Color(153, 255, 153));

    if (lblBanner.getParent() == null) {
        jPanel2.add(lblBanner);
    }

    Edit.setBounds(855, 10, 40, 25);

    NamaToko.setBounds(14, 120, 250, 42);

    jLabel3.setBounds(14, 170, 32, 32);
    jLabel6.setBounds(50, 170, 32, 32);
    jLabel4.setBounds(86, 170, 32, 32);
    jLabel5.setBounds(122, 170, 32, 32);
    jLabel2.setBounds(158, 170, 32, 32);

    jPanel2.setComponentZOrder(lblBanner, jPanel2.getComponentCount() - 1);
    jPanel2.setComponentZOrder(Edit, 0);
    jPanel2.setComponentZOrder(NamaToko, 0);
    jPanel2.setComponentZOrder(jLabel3, 0);
    jPanel2.setComponentZOrder(jLabel6, 0);
    jPanel2.setComponentZOrder(jLabel4, 0);
    jPanel2.setComponentZOrder(jLabel5, 0);
    jPanel2.setComponentZOrder(jLabel2, 0);

    jPanel2.revalidate();
    jPanel2.repaint();

    jPanel1.revalidate();
    jPanel1.repaint();
}
    private void hapusProduk(int idProduk) {
    try {
        Connection conn = koneksi.getConnection();

        String sql = "UPDATE produk SET status='hapus' WHERE id_produk=?";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, idProduk);

        pst.executeUpdate();

        JOptionPane.showMessageDialog(this, "Produk berhasil dihapus dari Beranda!");

        loadProduk(kategoriAktif);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
private void loadProduk(String kategori) {
    try {
        kategoriAktif = kategori;

        jPanel3.removeAll();
        jPanel3.setLayout(new java.awt.GridLayout(0, 4, 20, 20));

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
            String namaProduk = rs.getString("nama_produk");
            String hargaProduk = rs.getString("harga");
            String pathGambar = rs.getString("gambar");

            JPanel card = new JPanel();
            card.setPreferredSize(new java.awt.Dimension(170, 240));
            card.setBackground(Color.WHITE);
            card.setLayout(new javax.swing.BoxLayout(card, javax.swing.BoxLayout.Y_AXIS));

            card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createLineBorder(new Color(220, 220, 220)),
                    javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));

            JPanel panelGambar = new JPanel(null);
            panelGambar.setPreferredSize(new java.awt.Dimension(150, 150));
            panelGambar.setMaximumSize(new java.awt.Dimension(150, 150));
            panelGambar.setBackground(Color.WHITE);

            JLabel gambar = new JLabel();
            gambar.setBounds(0, 0, 150, 150);
            gambar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

            ImageIcon icon = new ImageIcon(pathGambar);
            Image imgProduk = icon.getImage().getScaledInstance(
                    140,
                    140,
                    Image.SCALE_SMOOTH
            );
            gambar.setIcon(new ImageIcon(imgProduk));

            JButton opsi = new JButton() {
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);

        // titik 3 vertikal
        g.fillOval(10, 5, 3, 3);
        g.fillOval(10, 13, 3, 3);
        g.fillOval(10, 21, 3, 3);
    }
};

        opsi.setBounds(135, 4, 15, 30);
        opsi.setContentAreaFilled(false);
        opsi.setBorderPainted(false);
        opsi.setFocusPainted(false);
        opsi.setOpaque(false);
        opsi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));   

            JPopupMenu menu = new JPopupMenu();
            JMenuItem edit = new JMenuItem("Edit");
            JMenuItem hapus = new JMenuItem("Hapus");

            menu.add(edit);
            menu.add(hapus);

            opsi.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    menu.show(opsi, 0, opsi.getHeight());
                }
            });

            edit.addActionListener(e -> {
                new TambahProduk(idProduk).setVisible(true);
                dispose();
            });

            hapus.addActionListener(e -> {
                int konfirmasi = JOptionPane.showConfirmDialog(
                        this,
                        "Yakin mau hapus produk ini dari Beranda?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION
                );

                if (konfirmasi == JOptionPane.YES_OPTION) {
                    hapusProduk(idProduk);
                }
            });

            panelGambar.add(gambar);
            panelGambar.add(opsi);
            panelGambar.setComponentZOrder(opsi, 0);

            JLabel nama = new JLabel(namaProduk, javax.swing.SwingConstants.CENTER);
            nama.setAlignmentX(Component.CENTER_ALIGNMENT);
            nama.setMaximumSize(new java.awt.Dimension(150, 25));

            JLabel harga = new JLabel("Rp " + hargaProduk, javax.swing.SwingConstants.CENTER);
            harga.setAlignmentX(Component.CENTER_ALIGNMENT);
            harga.setMaximumSize(new java.awt.Dimension(150, 25));
            harga.setFont(new Font("Segoe UI", Font.BOLD, 13));

            card.add(panelGambar);
            card.add(javax.swing.Box.createVerticalStrut(8));
            card.add(nama);
            card.add(javax.swing.Box.createVerticalStrut(4));
            card.add(harga);

            jPanel3.add(card);
        }

        jPanel3.revalidate();
        jPanel3.repaint();

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
        Kaos = new javax.swing.JButton();
        Semua = new javax.swing.JButton();
        Celana = new javax.swing.JButton();
        Hoodie = new javax.swing.JButton();
        Jaket = new javax.swing.JButton();
        Sepatu = new javax.swing.JButton();
        Kemeja = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        NamaToko = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Edit = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Kembali = new javax.swing.JLabel();
        Logout = new javax.swing.JLabel();
        TambahProduk = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        Kaos.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        Kaos.setForeground(new java.awt.Color(44, 74, 59));
        Kaos.setText("Kaos");
        Kaos.addActionListener(this::KaosActionPerformed);

        Semua.setBackground(new java.awt.Color(44, 74, 59));
        Semua.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        Semua.setForeground(new java.awt.Color(255, 255, 255));
        Semua.setText("Semua");
        Semua.addActionListener(this::SemuaActionPerformed);

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

        jPanel2.setBackground(new java.awt.Color(153, 255, 153));

        NamaToko.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        NamaToko.setText("NamaToko");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/star.png"))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/star.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/star.png"))); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/star.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/star.png"))); // NOI18N

        Edit.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        Edit.setText("Edit");
        Edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EditMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NamaToko)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)))
                        .addGap(0, 716, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Edit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                .addComponent(NamaToko, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout());

        Kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/home.png"))); // NOI18N
        Kembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                KembaliMouseClicked(evt);
            }
        });

        Logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/door.png"))); // NOI18N
        Logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogoutMouseClicked(evt);
            }
        });

        TambahProduk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/plus.png"))); // NOI18N
        TambahProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TambahProdukMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Kembali)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TambahProduk)
                .addGap(18, 18, 18)
                .addComponent(Logout)
                .addGap(528, 528, 528))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Semua, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                .addComponent(Kemeja, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(597, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Kembali)
                    .addComponent(Logout)
                    .addComponent(TambahProduk))
                .addGap(15, 15, 15)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Semua, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Kaos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Celana, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Hoodie, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Jaket, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Sepatu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Kemeja, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(296, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1082, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void KembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KembaliMouseClicked
    new Beranda().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_KembaliMouseClicked

    private void TambahProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TambahProdukMouseClicked
    new TambahProduk().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_TambahProdukMouseClicked

    private void KaosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KaosActionPerformed
     loadProduk("Kaos");   
    }//GEN-LAST:event_KaosActionPerformed

    private void SemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SemuaActionPerformed
    loadProduk(null);
    }//GEN-LAST:event_SemuaActionPerformed

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

    private void EditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditMouseClicked
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

            String sql = "UPDATE users SET banner_toko = ? WHERE alamat_email = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, path);
            pst.setString(2, Session.emailLogin);

            int result = pst.executeUpdate();

            if (result > 0) {
                tampilkanBanner(path);
                JOptionPane.showMessageDialog(this, "Banner berhasil disimpan");
            } else {
                JOptionPane.showMessageDialog(this, "Akun tidak ditemukan");
            }

            pst.close();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan banner: " + e.getMessage());
        }
    }


    }//GEN-LAST:event_EditMouseClicked

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
        java.awt.EventQueue.invokeLater(() -> new AdminPenjual().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Celana;
    private javax.swing.JLabel Edit;
    private javax.swing.JButton Hoodie;
    private javax.swing.JButton Jaket;
    private javax.swing.JButton Kaos;
    private javax.swing.JLabel Kembali;
    private javax.swing.JButton Kemeja;
    private javax.swing.JLabel Logout;
    private javax.swing.JLabel NamaToko;
    private javax.swing.JButton Semua;
    private javax.swing.JButton Sepatu;
    private javax.swing.JLabel TambahProduk;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
