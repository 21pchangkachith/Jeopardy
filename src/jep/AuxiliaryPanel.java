package jep;

import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;


public class AuxiliaryPanel extends GamePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4196682132384448093L;
	protected JPanel contentPanel;
	protected GridBagConstraints c;
	public AuxiliaryPanel() {
		super();
		c = new GridBagConstraints();
        
        JButton backButton = createMenuBackButton("MainScreenPanel");
        c.gridx=0;
        c.gridy=0;
        c.weightx=0.3;
        c.weighty=0.5;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        add(backButton, c);
        contentPanel = new JPanel();
        GridLayout layout = new GridLayout(5,1);
        layout.setVgap(20);
        contentPanel.setLayout(layout);
        contentPanel.setOpaque(false);
	}

}
