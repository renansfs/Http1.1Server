package Interface;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Servidor.Executador;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Button;
import javax.swing.BoxLayout;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Executador instance;
	private boolean control;
	private Thread thread;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		instance = new Executador();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JLabel lblServidorHttp = new JLabel("Servidor HTTP 1.1");
		contentPane.add(lblServidorHttp);
		
		Button button = new Button("Executar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(control) JOptionPane.showMessageDialog(null, "Servidor já iniciado.");
				else{
					JOptionPane.showMessageDialog(null, "Servidor iniciado.");
					Thread thread = new Thread(instance);
					thread.start();
					
					
					control = true;
				}
			}
		});
		contentPane.add(button);
		
		Button button_1 = new Button("Parar");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!control) JOptionPane.showMessageDialog(null, "Inicie o servidor primeiro!");
				else {
					instance.stop();
					JOptionPane.showMessageDialog(null, "Servidor Encerrado");
					control = false;
				}
				
			}
		});
		contentPane.add(button_1);
		
		Button button_2 = new Button("Configurar");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(control){
					JOptionPane.showMessageDialog(null, "Pare o servidor antes de alterar as configurações.");
				}else{
					Config conf = new Config();
					conf.setVisible(true);
				}
			}
		});
		contentPane.add(button_2);
		
		JLabel label = new JLabel("");
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("");
		contentPane.add(label_1);
	}

}
