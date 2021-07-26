package a7;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConnectFourGame {
	
	public static void main(String [] args) {
		
		JFrame mainframe = new JFrame();
		mainframe.setTitle("Connect Four");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		mainframe.setContentPane(panel);
		
		ConnectFourWidget c = new ConnectFourWidget();
		panel.add(c, BorderLayout.CENTER);
		
		mainframe.pack();
		mainframe.setVisible(true);
		
	}
}
