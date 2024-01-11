package clients;

import middle.MiddleFactory;

import javax.swing.*;
import java.awt.*;

public abstract class DefaultClient extends JFrame {
    
	private static final long serialVersionUID = 1L;
	protected MiddleFactory middleFactory;

    public DefaultClient(String title, MiddleFactory middleFactory, int width, int height) {
        super();
        this.middleFactory = middleFactory;
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        Dimension pos = PosOnScrn.getPos();
        setLocation(pos.width, pos.height);
    }

    protected abstract void setupComponents();

    public void start() {
        setupComponents();
        setVisible(true);
    }
}
