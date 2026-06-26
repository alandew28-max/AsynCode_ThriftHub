package asyncode_thrifthub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Profile extends JFrame {
    private JLabel lblNama;
    private JLabel lblEmail;
    private JLabel lblRole;
    private JButton btnKeluar;

    // 1. Konstruktor Utama
    public Profile() {
        initCustomComponents("Nama User", "user@gmail.com", "User");
    }

    // 2. Konstruktor Khusus untuk menerima data setelah Login sukses
    public Profile(String namaUser, String emailUser, String roleUser) {
        initCustomComponents(namaUser, emailUser, roleUser);
    }

    // 3. Logika pembuatan tampilan secara manual
    private void initCustomComponents(String namaUser, String emailUser, String roleUser) {
        // Pengaturan dasar Frame (Jendela Aplikasi)
        setTitle("Profile User");
        
        // =========================================================
        // PERUBAHAN UKURAN: Diatur tetap ke W: 1080 dan H: 660
        // =========================================================
        setSize(1080, 660);
        setResizable(false); // Mengunci ukuran agar tidak bisa di-resize
        // =========================================================
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Membuka jendela tepat di tengah layar
        setLayout(new BorderLayout());

        // Membuat Panel Utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100)); // Menyesuaikan jarak padding agar rapi di layar luas

        // Komponen Judul Halaman
        JLabel lblTitle = new JLabel("PROFILE", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Nirmala Text", Font.BOLD, 36)); // Diperbesar agar sesuai dengan resolusi layar baru
        lblTitle.setForeground(new Color(44, 74, 59));

        // Komponen Menampilkan Data User (Ukuran font dinaikkan menjadi 22 agar lebih terbaca)
        lblNama = new JLabel("Nama: " + namaUser, SwingConstants.LEFT);
        lblNama.setFont(new Font("Nirmala Text", Font.PLAIN, 22));
        lblNama.setForeground(new Color(44, 74, 59));

        lblEmail = new JLabel("Email: " + emailUser, SwingConstants.LEFT);
        lblEmail.setFont(new Font("Nirmala Text", Font.PLAIN, 22));
        lblEmail.setForeground(new Color(44, 74, 59));

        lblRole = new JLabel("Role: " + roleUser, SwingConstants.LEFT);
        lblRole.setFont(new Font("Nirmala Text", Font.PLAIN, 22));
        lblRole.setForeground(new Color(44, 74, 59));

        // Tombol Keluar / Log Out
        btnKeluar = new JButton("Keluar");
        btnKeluar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnKeluar.setPreferredSize(new Dimension(150, 45)); // Tombol dibuat sedikit lebih besar
        
        // Alert Konfirmasi Keluar
        btnKeluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pilihan = JOptionPane.showConfirmDialog(
                        Profile.this, 
                        "Apakah anda yakin mau keluar?", 
                        "Konfirmasi Keluar", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE
                );
                
                if (pilihan == JOptionPane.YES_OPTION) {
                    dispose(); // Menutup halaman profile saat ini
                    
                    // Jika halaman Login kamu sudah siap, hapus tanda komentar di bawah untuk mengaktifkannya:
                    // new Login().setVisible(true);
                }
            }
        });

        // Memasukkan Label ke dalam Panel Utama
        mainPanel.add(lblTitle);
        mainPanel.add(lblNama);
        mainPanel.add(lblEmail);
        mainPanel.add(lblRole);

        // Panel tombol Keluar di bagian bawah
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0)); // Memberikan jarak dari bawah layar
        buttonPanel.add(btnKeluar);

        // Menggabungkan susunan layout ke Frame
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method main untuk uji coba menjalankan halaman ini secara mandiri
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Profile("Muhammad Sulistio", "sulistio@gmail.com", "Admin").setVisible(true);
        });
    }
}