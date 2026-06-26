package asyncode_thrifthub;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Keranjang extends JFrame {
    private JTable tabelKeranjang;
    private DefaultTableModel tableModel;
    private JButton btnHapus;
    private JButton btnCheckOut;
    private JLabel lblTotal;
    private int totalHarga = 0;

    public Keranjang() {
        initCustomComponents();
    }

    private void initCustomComponents() {
        // Pengaturan dasar Jendela Aplikasi
        setTitle("Keranjang Belanja - ThriftHub");
        setSize(1080, 660);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());

        // 1. PANEL ATAS: Judul Halaman
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        JLabel lblTitle = new JLabel("KERANJANG BELANJA", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Nirmala Text", Font.BOLD, 36));
        lblTitle.setForeground(new Color(44, 74, 59));
        topPanel.add(lblTitle);

        // 2. PANEL TENGAH: Tabel Item Keranjang
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        String[] namaKolom = {"No", "Nama Barang", "Harga", "Jumlah", "Subtotal"};
        tableModel = new DefaultTableModel(namaKolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tabelKeranjang = new JTable(tableModel);
        tabelKeranjang.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tabelKeranjang.setRowHeight(30); 
        tabelKeranjang.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Dummy Data awal keranjang
        tambahItemKeKeranjang("1", "Jaket Vintage Denim", 150000, 1);
        tambahItemKeKeranjang("2", "Kaos Oversize Hitam", 75000, 2);
        tambahItemKeKeranjang("3", "Celana Cargo Olv", 120000, 1);

        JScrollPane scrollPane = new JScrollPane(tabelKeranjang);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // 3. PANEL BAWAH: Total Harga & Tombol Aksi
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 40, 50));

        lblTotal = new JLabel("Total Pembelian: Rp " + totalHarga, SwingConstants.RIGHT);
        lblTotal.setFont(new Font("Nirmala Text", Font.BOLD, 22));
        lblTotal.setForeground(new Color(44, 74, 59));
        bottomPanel.add(lblTotal, BorderLayout.NORTH);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panelTombol.setBackground(Color.WHITE);

        btnHapus = new JButton("Hapus Item");
        btnHapus.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnHapus.setBackground(new Color(180, 50, 50));
        btnHapus.setForeground(Color.WHITE);
        btnHapus.setPreferredSize(new Dimension(150, 45));

        btnCheckOut = new JButton("Check Out");
        btnCheckOut.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCheckOut.setBackground(new Color(44, 74, 59));
        btnCheckOut.setForeground(Color.WHITE);
        btnCheckOut.setPreferredSize(new Dimension(150, 45));

        panelTombol.add(btnHapus);
        panelTombol.add(btnCheckOut);
        bottomPanel.add(panelTombol, BorderLayout.SOUTH);

        // =========================================================
        // LOGIKA AKSI TOMBOL
        // =========================================================

        // Aksi Tombol Hapus Item
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int barisTerpilih = tabelKeranjang.getSelectedRow();
                
                if (barisTerpilih != -1) {
                    int konfirmasi = JOptionPane.showConfirmDialog(
                            Keranjang.this,
                            "Apakah anda yakin ingin menghapus item ini dari keranjang?",
                            "Hapus Item",
                            JOptionPane.YES_NO_OPTION
                    );
                    
                    if (konfirmasi == JOptionPane.YES_OPTION) {
                        int subtotalItem = (int) tableModel.getValueAt(barisTerpilih, 4);
                        totalHarga -= subtotalItem;
                        lblTotal.setText("Total Pembelian: Rp " + totalHarga);
                        
                        tableModel.removeRow(barisTerpilih);
                        
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            tableModel.setValueAt(String.valueOf(i + 1), i, 0);
                        }

                        // =========================================================
                        // PERUBAHAN DI SINI: Menambahkan Alert Penghapusan Berhasil
                        // =========================================================
                        JOptionPane.showMessageDialog(
                                Keranjang.this, 
                                "Penghapusan Berhasil!", 
                                "Sukses", 
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        // =========================================================
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            Keranjang.this, 
                            "Silakan pilih item di tabel yang ingin dihapus terlebih dahulu!", 
                            "Peringatan", 
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        // Aksi Tombol Check Out
        btnCheckOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(
                            Keranjang.this, 
                            "Keranjang belanja anda masih kosong!", 
                            "Gagal Check Out", 
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                JOptionPane.showMessageDialog(
                        Keranjang.this, 
                        "Pembelian Berhasil!\nBarang mu akan sampai dalam 5 hari.", 
                        "Informasi Pengiriman", 
                        JOptionPane.INFORMATION_MESSAGE
                );
                
                tableModel.setRowCount(0);
                totalHarga = 0;
                lblTotal.setText("Total Pembelian: Rp " + totalHarga);
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void tambahItemKeKeranjang(String no, String nama, int harga, int jumlah) {
        int subtotal = harga * jumlah;
        tableModel.addRow(new Object[]{no, nama, harga, jumlah, subtotal});
        totalHarga += subtotal;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Keranjang().setVisible(true);
        });
    }
}