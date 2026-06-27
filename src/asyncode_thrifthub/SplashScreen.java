package asyncode_thrifthub;

import javax.swing.JWindow;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.AlphaComposite;

/**
 * Splash screen dengan animasi fade in -> tahan sebentar -> fade out
 * lalu otomatis membuka IndexPage.
 *
 * Cara pakai (taruh di method main aplikasi kamu):
 *
 *   public static void main(String[] args) {
 *       java.awt.EventQueue.invokeLater(() -> new SplashScreen());
 *   }
 */
public class SplashScreen extends JWindow {

    private float opacity = 0f;
    private static final int FADE_IN_DELAY = 30;   // ms per langkah fade in
    private static final float FADE_IN_STEP = 0.05f;
    private static final int HOLD_DURATION = 1500; // ms teks diam penuh sebelum fade out
    private static final int FADE_OUT_DELAY = 30;
    private static final float FADE_OUT_STEP = 0.05f;

    private LogoPanel logoPanel;

    public SplashScreen() {
        initSplash();
        startFadeIn();
    }

    private void initSplash() {
        int width = 500;
        int height = 350;

        logoPanel = new LogoPanel();
        logoPanel.setLayout(null);

        JLabel logoLabel = new JLabel("Thrift.", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Rockwell", Font.BOLD, 48));
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setBounds(0, height / 2 - 40, width, 60);
        logoPanel.add(logoLabel);

        setContentPane(logoPanel);
        setSize(width, height);

        // Posisikan di tengah layar
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);

        setVisible(true);
    }

    private void startFadeIn() {
        Timer fadeInTimer = new Timer(FADE_IN_DELAY, null);
        fadeInTimer.addActionListener(e -> {
            opacity += FADE_IN_STEP;
            if (opacity >= 1f) {
                opacity = 1f;
                fadeInTimer.stop();
                // Tahan sebentar sebelum mulai fade out
                Timer holdTimer = new Timer(HOLD_DURATION, holdEvt -> startFadeOut());
                holdTimer.setRepeats(false);
                holdTimer.start();
            }
            logoPanel.setOpacity(opacity);
        });
        fadeInTimer.start();
    }

    private void startFadeOut() {
        Timer fadeOutTimer = new Timer(FADE_OUT_DELAY, null);
        fadeOutTimer.addActionListener(e -> {
            opacity -= FADE_OUT_STEP;
            if (opacity <= 0f) {
                opacity = 0f;
                fadeOutTimer.stop();
                goToIndexPage();
            }
            logoPanel.setOpacity(opacity);
        });
        fadeOutTimer.start();
    }

    private void goToIndexPage() {
        dispose(); // tutup splash screen
        IndexPage indexPage = new IndexPage();
        indexPage.setVisible(true);
    }

    /**
     * Panel custom dengan background hijau gelap dan dukungan transparansi
     * untuk efek fade pada teks/logo di dalamnya.
     */
    private static class LogoPanel extends javax.swing.JPanel {

        private float opacity = 0f;

        public LogoPanel() {
            setBackground(new Color(44, 74, 59)); // warna hijau gelap sesuai desain
            setOpaque(true);
        }

        public void setOpacity(float opacity) {
            this.opacity = opacity;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Gambar background solid dulu (selalu terlihat penuh)
            g2.setColor(getBackground());
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.dispose();

            // Atur transparansi untuk komponen anak (teks "Thrift.")
            Graphics2D g2Child = (Graphics2D) g.create();
            g2Child.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            super.paintComponent(g2Child);
            g2Child.dispose();
        }

        @Override
        protected void paintChildren(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            super.paintChildren(g2);
            g2.dispose();
        }
    }
}
