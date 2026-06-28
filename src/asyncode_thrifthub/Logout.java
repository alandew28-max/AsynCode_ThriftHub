/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asyncode_thrifthub;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import form.dashboard;

public class Logout {

    public static void logout(JFrame halamanSaatIni) {
        int pilih = JOptionPane.showConfirmDialog(
                halamanSaatIni,
                "Yakin ingin logout?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (pilih == JOptionPane.YES_OPTION) {
            Session.emailLogin = null;
            Session.roleLogin = null;

            dashboard login = new dashboard();
            login.setLocationRelativeTo(null);
            login.setVisible(true);

            halamanSaatIni.dispose();
        }
    }
}
