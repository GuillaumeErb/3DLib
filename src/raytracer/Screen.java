package raytracer;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Screen extends JFrame {
	private JLabel label;

    public Screen(int width, int height) {
        super( "Display" );
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing( WindowEvent e) {
                System.exit(0);
            }
        });
        this.setSize(width, height);
                
        this.label = new JLabel();
        this.add(this.label);
                
//        this.pack();
        this.setVisible(true);
    }

	public void display(BufferedImage image) {
		label.setIcon(new ImageIcon(image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT)));
    }

}