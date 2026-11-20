package view;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Draws a pie or bar chart of seconds spent per activity.
 * Crude prototype: plain Java2D, no chart library needed.
 */
public class ChartPanel extends JPanel {
    private static final Color[] PALETTE = {
            new Color(0x4E79A7), new Color(0xF28E2B), new Color(0xE15759), new Color(0x76B7B2),
            new Color(0x59A14F), new Color(0xEDC948), new Color(0xB07AA1), new Color(0xFF9DA7)
    };

    private Map<String, Long> seconds = new LinkedHashMap<>();
    private boolean pie = true;

    public ChartPanel() {
        setPreferredSize(new Dimension(520, 300));
        setBackground(Color.WHITE);
    }

    public void setData(Map<String, Long> seconds, boolean pie) {
        this.seconds = seconds;
        this.pie = pie;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        long total = seconds.values().stream().mapToLong(Long::longValue).sum();
        if (total <= 0) {
            String msg = "No entries in this period.";
            FontMetrics fm = g.getFontMetrics();
            g.setColor(Color.GRAY);
            g.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2);
            g.dispose();
            return;
        }
        if (pie) {
            drawPie(g, total);
        } else {
            drawBars(g, total);
        }
        g.dispose();
    }

    private void drawPie(Graphics2D g, long total) {
        int size = Math.min(getHeight() - 40, getWidth() / 2 - 20);
        int x = 20, y = (getHeight() - size) / 2;
        double angle = 90;
        int i = 0;
        for (Map.Entry<String, Long> e : seconds.entrySet()) {
            double extent = -360.0 * e.getValue() / total;
            g.setColor(PALETTE[i % PALETTE.length]);
            g.fill(new Arc2D.Double(x, y, size, size, angle, extent, Arc2D.PIE));
            angle += extent;
            i++;
        }
        // legend
        int lx = x + size + 30, ly = 40;
        i = 0;
        for (Map.Entry<String, Long> e : seconds.entrySet()) {
            g.setColor(PALETTE[i % PALETTE.length]);
            g.fillRect(lx, ly - 11, 12, 12);
            g.setColor(Color.DARK_GRAY);
            g.drawString(e.getKey() + "  " + format(e.getValue())
                    + " (" + Math.round(100.0 * e.getValue() / total) + "%)", lx + 20, ly);
            ly += 22;
            i++;
        }
    }

    private void drawBars(Graphics2D g, long total) {
        long max = seconds.values().stream().mapToLong(Long::longValue).max().orElse(1);
        int labelW = 120, rowH = 32, y = 20, i = 0;
        int barMax = getWidth() - labelW - 120;
        for (Map.Entry<String, Long> e : seconds.entrySet()) {
            g.setColor(Color.DARK_GRAY);
            g.drawString(e.getKey(), 10, y + 18);
            g.setColor(PALETTE[i % PALETTE.length]);
            int w = (int) (barMax * (double) e.getValue() / max);
            g.fillRect(labelW, y + 4, Math.max(w, 2), rowH - 12);
            g.setColor(Color.DARK_GRAY);
            g.drawString(format(e.getValue()), labelW + w + 8, y + 18);
            y += rowH;
            i++;
        }
    }

    static String format(long secs) {
        return (secs / 3600) + "h " + ((secs % 3600) / 60) + "m";
    }
}
