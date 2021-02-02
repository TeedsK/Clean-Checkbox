import button.PressSpreadButton;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import java.awt.BasicStroke;
/**
 * Creates a the checkbox used in a checkox with a circle animation.
 * @author Teeds - Theo K
 */
public class Check extends PressSpreadButton{
    ArrayList<JPanel> ParentJPanels = new ArrayList<JPanel>();
    PressSpreadButton checkbox;
    boolean active = false;
    Color outline = new Color(29,38,46);
    int alpha = 255;
    /**
     * Creates a quick, basic checkbox
     */
    public Check() {
        super(new Color(0,0,0), new Color(39,48,56), new Color(38,47,55), new Color(50,68,85), 15);
        setup();
    }
    /**
     * @param background the color behind the checkbox
     */
    public Check(Color background) {
        super(background, new Color(39,48,56), new Color(38,47,55), new Color(50,68,85), 15);
        setup();
    }
    /**
     * @param background the color behind the checkbox
     * @param checkBox the color of the checkbox when off
     */
    public Check(Color background, Color checkBox) {
        super(background, checkBox, new Color(38,47,55), new Color(50,68,85), 15);
        setup();
    }
    /**
     * @param background the color behind the checkbox
     * @param checkBox the color of the checkbox when off
     * @param checkBoxHover the color of the checkbox when hovering
     */
    public Check(Color background, Color checkBox, Color checkBoxHover) {
        super(background, checkBox, checkBoxHover, new Color(50,68,85), 15);
        setup();
    }
    /**
     * @param background the color behind the checkbox
     * @param checkBox the color of the checkbox when off
     * @param checkBoxHover the color of the checkbox when hovering
     * @param checkBoxSelected the color of the checkbox when selected
     */
    public Check(Color background, Color checkBox, Color checkBoxHover, Color checkBoxSelected) {
        super(background, checkBox, checkBoxHover, checkBoxSelected, 15);
        setup();
    }
    /**
     * @param background the color behind the checkbox
     * @param checkBox the color of the checkbox when off
     * @param checkBoxHover the color of the checkbox when hovering
     * @param checkBoxSelected the color of the checkbox when selected
     * @param roundness the roundness of the checkbox
     */
    public Check(Color background, Color checkBox, Color checkBoxHover, Color checkBoxSelected, int roundness) {
        super(background, checkBox, checkBoxHover, checkBoxSelected, roundness);
        setup();
    }
    /**
     * @param background the color behind the checkbox
     * @param checkBox the color of the checkbox when off
     * @param checkBoxHover the color of the checkbox when hovering
     * @param checkBoxSelected the color of the checkbox when selected
     * @param checkBoxOutline the color of the checkbox outline
     * @param roundness the roundness of the checkbox
     */
    public Check(Color background, Color checkBox, Color checkBoxHover, Color checkBoxSelected, Color checkBoxOutline, int roundness) {
        super(background, checkBox, checkBoxHover, checkBoxSelected, roundness);
        outline = checkBoxOutline;
        setup();
    }

    /**
     * Marks the checkbox as being active
     */
    public void check() {
        active = true;
        changeOutlineAlpha(active, 0);
    }

    /**
     * Marks the checkbox as being deactive
     */
    public void uncheck() {
        active = false;
        changeOutlineAlpha(active, 255);
    }

    public boolean getActive() {
        return active;
    }

    /**
     * Changes the value of the alpha for the outline
     * @param on wether or not the alpha is increasing or decreasing, checkmark on/off
     * @param wanted the wanted alpha value
     */
    private void changeOutlineAlpha(boolean on, int wanted) {
        Thread t = new Thread() {
            public void run() {
                while(active == on && alpha != wanted) {
                    if(on) {
                        alpha -= 15;
                    } else {
                        alpha += 15;
                    }
                    alphaCheck();
                    update();
                    sleepTime(10);
                }
            }
        }; t.start();
    }
    
    /**
     * Sets up the rest of the checkbox
     */
    private void setup() {
        setPressIncrementAmount(8);
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(active) {
                    uncheck();
                    changeAlphaValues(4, 3, false);
                } else {
                    check();
                    changeAlphaValues(4, 3, true);
                }
            }
        });
        setOpaque(false);
    }

    /**
     * Checks the alpha value
     * @return the checked alpha value
     */
    private int alphaCheck() {
        if(alpha > 255) {
            alpha = 255;
            return 255;
        } else if(alpha < 0) {
            alpha = 0;
            return 0;
        } else {
            return alpha;
        }
    }

    /**
     * updates any connected jpanels
     */
    private void update() {
        revalidate();
        repaint();
        for(JPanel panel : ParentJPanels) {
            if(panel.isVisible() && panel.getBackground().getAlpha() > 0) {
                panel.revalidate();
                panel.repaint();
            }
        }
    }

    /**
     * Sleeps for the time given
     * @param time given amount of time to sleep for
     */
    private void sleepTime(int time) {
        try {
            Thread.sleep(time);
        } catch(Exception err1) {}
    }

    /**
     * paints the checkbox outline and the checkmark
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(2.4f));
        graphics.setColor(new Color(outline.getRed(),outline.getGreen(), outline.getBlue(), alphaCheck()));
        graphics.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 10, 10);

        int alp = 255 - alphaCheck();
        if(alp > 0) {
            graphics.setColor(new Color(255,255,255, alp));
            graphics.setStroke(new BasicStroke(2.1f));
            graphics.drawLine(getPoint(3.8) + 2, getPoint(1.9) + 2, getPoint(2.1) + 2, getPoint(1.4) + 2);
            graphics.drawLine(getPoint(2.1) + 2, getPoint(1.4) + 2,  getPoint(1.3) + 2, getPoint(3.2) + 2);
        }
    }

    /**
     * @param percent the position the given point should be at
     * @return
     */
    private int getPoint(double percent) {
        return (int) ((getWidth() - 4)/ percent);
    }
}
