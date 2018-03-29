package Interface;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Config extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField porta;
	private JTextField user;
	private JPasswordField passwordField;
	private Save save;

	/**
	 * Create the frame.
	 */
	public Config() {
		save = new Save();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*Opcoes do diretorio*/
		JLabel lblEscolhaAOpo = new JLabel("Escolha a op\u00E7\u00E3o para o diret\u00F3rio:");
		lblEscolhaAOpo.setBounds(10, 67, 199, 14);
		contentPane.add(lblEscolhaAOpo);
		
		JRadioButton rdbtnListagem = new JRadioButton("Listagem");
		rdbtnListagem.setBounds(10, 88, 109, 23);
		contentPane.add(rdbtnListagem);
		
		JRadioButton rdbtnIndexhtml = new JRadioButton("index.html");
		rdbtnIndexhtml.setBounds(10, 114, 109, 23);
		contentPane.add(rdbtnIndexhtml);
		
		JRadioButton rdbtnMensagemDeQue = new JRadioButton("Mensagem de que n\u00E3o \u00E9 poss\u00EDvel a listagem");
		rdbtnMensagemDeQue.setBounds(10, 137, 310, 23);
		contentPane.add(rdbtnMensagemDeQue);
		
		/*Grupo dos radios*/
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnListagem);
		group.add(rdbtnIndexhtml);
		group.add(rdbtnMensagemDeQue);
		
		/*Fim opcoes do diretorio*/
		
		JLabel lblConfiguraes = new JLabel("Configura\u00E7\u00F5es");
		lblConfiguraes.setBounds(10, 11, 121, 14);
		contentPane.add(lblConfiguraes);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String dir = null;
				if(rdbtnListagem.isSelected()) dir = "1";
				else if(rdbtnIndexhtml.isSelected()) dir = "2";
				else if (rdbtnMensagemDeQue.isSelected()) dir = "3";
				else if(dir == null) dir = "1";
				String port = porta.getText();
				if(port.equals("")) port = "8888";
				String urs = user.getText();
				if(urs.equals("")) urs = "admin";
				@SuppressWarnings("deprecation")
				String pass = passwordField.getText();
				if(pass.equals("")) pass = "1234";
				save.save(true, port, dir, urs, pass);
				JOptionPane.showMessageDialog(null, " \nConfiguração: \n\n Porta: "+port+"\n Opção dos diretórios: "+dir+"\n User: "+urs+"\n Senha: "+pass);
				setVisible(false);
			}
		});
		btnSalvar.setBounds(286, 228, 89, 23);
		contentPane.add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCancelar.setBounds(385, 228, 89, 23);
		contentPane.add(btnCancelar);
		
		porta = new JTextField();
		porta.setBounds(45, 36, 86, 20);
		contentPane.add(porta);
		porta.setColumns(10);
		
		JLabel lblPorta = new JLabel("Porta:");
		lblPorta.setBounds(10, 39, 46, 14);
		contentPane.add(lblPorta);
		
		JLabel lblPadro = new JLabel("Default: 8888");
		lblPadro.setBounds(141, 39, 89, 14);
		contentPane.add(lblPadro);
		
		
		 
		
		 
		JLabel lblLogin = new JLabel("Usu\u00E1rio: ");
		lblLogin.setBounds(256, 11, 64, 14);
		contentPane.add(lblLogin);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(256, 39, 46, 14);
		contentPane.add(lblSenha);
		
		user = new JTextField();
		user.setBounds(338, 8, 86, 20);
		contentPane.add(user);
		user.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(338, 36, 86, 20);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel = new JLabel("Todas as informa\u00E7\u00F5es s\u00E3o encontradas no arquivo config.txt no diret\u00F3rio htdocs.");
		lblNewLabel.setBounds(10, 203, 464, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblPadroadminE = new JLabel(" Padr\u00E3o: \"admin\" e \"1234\"");
		lblPadroadminE.setBounds(256, 67, 188, 14);
		contentPane.add(lblPadroadminE);
		
		JLabel lblPadroListagem = new JLabel("Padr\u00E3o: Listagem");
		lblPadroListagem.setBounds(10, 167, 121, 14);
		contentPane.add(lblPadroListagem);
		
		JButton btnRestaurar = new JButton("Restaurar");
		btnRestaurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save.save(false, "", "", "", "");
				JOptionPane.showMessageDialog(null, " \nConfiguração Restauradas: \n\n Porta: 8888\n Opção dos diretórios: 1\n User: \"admin\"\n Senha: \"1234\"");

				setVisible(false);
				
				
			}
		});
		btnRestaurar.setBounds(165, 228, 111, 23);
		contentPane.add(btnRestaurar);
		
		JLabel lblOsCamposDeixados = new JLabel("Os campos deixados em branco ter\u00E3o os valores padr\u00F5es.");
		lblOsCamposDeixados.setBounds(10, 192, 383, 14);
		contentPane.add(lblOsCamposDeixados);
	}
}
