import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;

public class Login {
	private JFrame frame;
	private static Login tela;
	private BD bd;

	private JTextField username;
	private JPasswordField senha;
	private JLabel titulo, titleImageLabel, userlabel, senhalabel;
	private JButton entrar, cadastrar;

	private PreparedStatement statement;
	private ResultSet resultSet;


	public Login(){
		montaTela();
	}

	private void montaTela(){
		frame = new JFrame("Muuuu");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(245, 204, 49));
		initComponents();
		defineEvents();

		frame.pack();
		frame.setBounds(700,300,230,500);
		frame.setVisible(true);
		bd = new BD();
	}

	private void initComponents(){
		Font f = new Font("Verdana", Font.ITALIC, 25);
		titulo = new JLabel("Login");
		titulo.setForeground(new Color(0, 0, 0));
		titulo.setFont(f);
		titulo.setBounds(30, 140, 230, 45);
		frame.add(titulo);

		titleImageLabel = new JLabel();
		titleImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user_login.png")));
		titleImageLabel.setBounds(50, 10, 250, 140);
		frame.add(titleImageLabel);

		userlabel = new JLabel("Nome de Usuário");
		userlabel.setForeground(new Color(0, 0, 0));
		userlabel.setFont(new Font("Verdana", Font.ITALIC, 18));
		username = new JTextField();
		username.setForeground(new Color(0, 0, 0));
		username.setBackground(new Color(100, 100, 100));
		username.setFont(new Font("Verdana", Font.ITALIC, 18));
		userlabel.setBounds(30,180,240,35);
		username.setBounds(30,220,150,35);
		frame.add(username);
		frame.add(userlabel);

		senhalabel = new JLabel("Senha");
		senhalabel.setForeground(new Color(0, 0, 0));
		senha = new JPasswordField();
		senha.setBackground(new Color(100, 100, 100));
		senha.setForeground(new Color(0, 0, 0));
		senha.setFont(new Font("Verdana", Font.ITALIC, 18));
		senhalabel.setFont(new Font("Verdana", Font.ITALIC, 18));
		senhalabel.setBounds(30,260,250,35);
		senha.setBounds(30,300,150,35);
		frame.add(senha);
		frame.add(senhalabel);

		entrar = new JButton("Entrar");
		entrar.setBackground(new Color(100, 100, 100));
		entrar.setForeground(new Color(0, 0, 0));
		entrar.setFont(new Font("Verdana",Font.ITALIC, 18));
		entrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/login.png")));
		entrar.setHorizontalTextPosition(JButton.LEFT);
		entrar.setFocusPainted(false);
		entrar.setBounds(30,360,130,35);
		frame.add(entrar);
	}


	private void defineEvents(){
		entrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if((username.getText().trim().equals("")) && (senha.getText().trim().equals(""))){
					JOptionPane.showMessageDialog(null, "Verifique se algum campo está vazio!");
				} else {
					try {
						if(!bd.getConnection()){
							JOptionPane.showMessageDialog(null, "Falha na conexão, o sistem será fechado!");
							System.exit(0);
						}
						String url = "SELECT * FROM users WHERE nome=? AND senha=?";
						statement = bd.connection.prepareStatement(url);
						statement.setString(1, username.getText());
						statement.setString(2, senha.getText());
						resultSet = statement.executeQuery();
						if(resultSet.next()){
							String nome = resultSet.getString("nome");
							JOptionPane.showMessageDialog(null, nome + " logado(a) com sucesso");
						} else {
							JOptionPane.showMessageDialog(null, "Usuario não encontrado!");
						}
						resultSet.close();
						statement.close();
						bd.close();
					} catch(Exception erro) {
						JOptionPane.showMessageDialog(null, "Algo de errado aconteceu:\n " + erro.toString());
					}
					
				}
			}
		});
	}

}