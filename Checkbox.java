package src.general.AnimatedCheckbox;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
@SuppressWarnings("serial")
/**
 * Creates a customizable checkbox with an outer circle animation when pressed
 * @author Teeds - Theo k
 */
public class Checkbox extends JPanel {
    ArrayList<JPanel> ParentJPanels = new ArrayList<JPanel>();
    ArrayList<Circle> circles = new ArrayList<Circle>();
    Check checkbox;
    public static Circle currentCircle = null;
    Color checkColor = new Color(255,255,255);
    Color uncheckColor = new Color(255,255,255);
    /**
     * Creates a quick, basic checkbox
     */
    public Checkbox() {
        super();
        checkbox = new Check(null, new Color(39,48,56), new Color(38,47,55), new Color(50,68,85), 15);
        setup();
    }
    /**
     * @param checkBox the color of the checkbox when unchecked
     */
    public Checkbox(Color checkBox) {
        super();
        checkbox = new Check(null, checkBox, new Color(38,47,55), new Color(50,68,85), 15);
        setup();
    }
    /**
     * @param checkBox the color of the checkbox when unchecked
     * @param checkBoxHover the color of the checkbox when hovering
     */
    public Checkbox(Color checkBox, Color checkBoxHover) {
        super();
        checkbox = new Check(null, checkBox, checkBoxHover, new Color(50,68,85), 15);
        setup();
    }
    /**
     * @param checkBox the color of the checkbox when unchecked
     * @param checkBoxHover the color of the checkbox when hovering
     * @param checkBoxSelected the color of the checkbox when selected
     */
    public Checkbox(Color checkBox, Color checkBoxHover, Color checkBoxSelected) {
        super();
        checkbox = new Check(null, checkBox, checkBoxHover, checkBoxSelected, 15);
        setup();
    }
    /**
     * @param checkBox the color of the checkbox when unchecked
     * @param checkBoxHover the color of the checkbox when hovering
     * @param checkBoxSelected the color of the checkbox when selected
     * @param roundness the roundness of the checkbox
     */
    public Checkbox(Color checkBox, Color checkBoxHover, Color checkBoxSelected, int roundness) {
        super();
        checkbox = new Check(null, checkBox, checkBoxHover, checkBoxSelected, roundness);
        setup();
    }
    /**
     * @param checkBox the color of the checkbox when unchecked
     * @param checkBoxHover the color of the checkbox when hovering
     * @param checkBoxSelected the color of the checkbox when selected
     * @param checkBoxOutline the color of the checkbox outline
     * @param roundness the roundness of the checkbox
     */
    public Checkbox(Color checkBox, Color checkBoxHover, Color checkBoxSelected, Color checkBoxOutline, int roundness) {
        super();
        checkbox = new Check(null, checkBox, checkBoxHover, checkBoxSelected, checkBoxOutline, roundness);
        setup();
    }
    /**
     * @param color the color of the circle to use when the checkbox is checked
     */
    public void setCheckedColor(Color color) {
        this.checkColor = color;
    }

    /**
     * @param color the color of the circle to use when the checkbox is unchecked
     */
    public void setUnCheckedColor(Color color) {
        this.uncheckColor = color;
    }

    /**
     * @return if checkmark is active
     */
    public boolean getActive() {
        return checkbox.getActive();
    }

    /**
     * Sets up the rest of the checkbox
     */
    private void setup() {
        checkbox.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Circle circle = new Circle(checkbox.getWidth());
                if(checkbox.getActive()) {
                    circle.setColor(uncheckColor);
                    circle.setAlpha(110);
                    circle.setMaxAlpha(110);
                } else {
                    circle.setColor(checkColor);
                    circle.setAlpha(190);
                    circle.setMaxAlpha(190);
                }
                currentCircle = circle;
                changeSize(currentCircle, getWidth(), 2);
                circles.add(circle);
            }
            public void mouseReleased(MouseEvent e) {
                if(currentCircle != null) {
                    changeAlphaValues(currentCircle, 0, -5);
                }
            }
        });
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(checkbox, BorderLayout.CENTER);
    }

    /**
     * Does the checkbox action without the animation
     */
    public void doAction() {
        if(checkbox.getActive()) {
            checkbox.uncheck();
            checkbox.changeAlphaValues(4, 3, false);
        } else {
            checkbox.check();
            checkbox.changeAlphaValues(4, 3, true);
        }
    }


    /**
     * paints the checkbox outline and the checkmark
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try {
            for(Circle circle : circles) {
                try {
                    Color color = circle.getColor();
                    graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), circle.getAlpha()));
                    graphics.fillOval((getWidth() / 2) - (circle.getDiameter() / 2), (getHeight() / 2) - (circle.getDiameter() / 2), circle.getDiameter(), circle.getDiameter());
                } catch(java.lang.NullPointerException e) {}
            }
        } catch(java.util.ConcurrentModificationException e) {}
    }
    
    /**
     * Changes the size of the circle
     * @param circle the circle to change the size of
     * @param wanted the wanted size of the circle
     * @param increment the increment amount for the circle size
     */
    public void changeSize(Circle circle, int wanted, int increment) {
        circle.setWanted(wanted);
        Thread t = new Thread() {
            public void run() {
                while(circle.getWanted() == wanted && circle.getDiameter() != wanted) {
                    circle.setDiameter(circle.getDiameter() + increment);
                    revalidate();
                    repaint();
                    sleepTime(15);
                }
            }
        }; t.start();
    }
    
    /**
     * Changes the value of the alpha
     * @param on wether or not the alpha is increasing or decreasing, checkmark on/off
     * @param wanted the wanted alpha value
     */
    private void changeAlphaValues(Circle circle, int wanted, int increment) {
        Thread t = new Thread() {
            public void run() {
                while(circle.getAlpha() != wanted && circle.getAlpha() <= circle.getMaxAlpha()) {
                    circle.setAlpha(circle.getAlpha() + increment); 
                    alphaCheck(circle);
                    update();
                    sleepTime(10);
                }
                if(circle.getAlpha() <= 5) {
                    circles.remove(circle);
                }
            }
        }; t.start();
    }

    /**
     * Checks the alpha value
     * @return the checked alpha value
     */
    private int alphaCheck(Circle circle) {
        if(circle.getAlpha() > 255) {
            circle.setAlpha(255);
            return 255;
        } else if(circle.getAlpha() < 0) {
            circle.setAlpha(0);
            return 0;
        } else {
            return circle.getAlpha();
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
}
