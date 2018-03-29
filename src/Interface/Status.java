package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Servidor.Executador;

import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Button;

public class Status extends JFrame {

	private JPanel contentPane;
	private Executador instance;
	

	/**
	 * Create the frame.
	 */
	public Status() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblServidorOnline = new JLabel("Servidor Online");
		contentPane.add(lblServidorOnline);
		
		Button button = new Button("PARAR");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//instance.stop();
				
			}
		});
		contentPane.add(button);
	}

}
