package appInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextArea;

/**
 * Frame with an information message about how accounts are created.
 * @author Ciprian Banci 
 * @version 1.0
 */
public class NewAccount extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void lunch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewAccount frame = new NewAccount();
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
	public NewAccount() {
		setFont(new Font("Tahoma", Font.PLAIN, 16));
		setTitle("Creare cont");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final NewAccount f = this; //it will help us close this frame on buttons actions
		
		//right pannel
		
		JPanel panelRight = new JPanel();
		panelRight.setLayout(null);
		panelRight.setBounds(396, 10, 390, 425);
		contentPane.add(panelRight);
		
		//load the image
		
		Image image = null;
		try {
			image = (new ImageIcon(this.getClass().getResource("/tips_account.png"))).getImage();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//JLabel for the image
		
		JLabel lblImage = new JLabel("");
		lblImage.setBounds(44,70,319,316);
		
		//scale the image
		
		Image scaledImage = image.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_DEFAULT);
				
		//display the login image
				
		lblImage.setIcon(new ImageIcon(scaledImage));
		panelRight.add(lblImage);
		
		//left panel
		
		JPanel panelLeft = new JPanel();
		panelLeft.setBounds(10, 10, 390, 425);
		contentPane.add(panelLeft);
		panelLeft.setLayout(null);
		
		//left panel content
		
		JLabel lblTips = new JLabel("Important!");
		lblTips.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTips.setBounds(21, 27, 102, 22);
		panelLeft.add(lblTips);
		
		//tips for creating new account
		
		JTextArea txtrTips = new JTextArea();
		txtrTips.setEditable(false);
		txtrTips.setBackground(new Color(204, 204, 204));
		txtrTips.setFont(new Font("Monospaced", Font.ITALIC, 14));
		txtrTips.setText("Crearea conturilor se face de catre \r\nadministratorul fiecarei asociatii. \r\n"
				+ "Dupa crarea contului de administrator,\r\nacesta creeaza conturile aferente \r\nfiecarui "
				+ "proprietar urmand ca datele \r\nacestor conturi sa fie distribuite \r\nprin email catre proprietari.");
		txtrTips.setBounds(21, 57, 333, 144);
		panelLeft.add(txtrTips);
		
		//Sunt administrator button->create administrator account
		
		JButton btnNewAcc = new JButton("Sunt administrator");
		btnNewAcc.setBackground(new Color(204, 255, 204));
		btnNewAcc.setBounds(76, 281, 216, 36);
		panelLeft.add(btnNewAcc);
		btnNewAcc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewAccounts.lunch();
				f.dispose();
			}
		});
		btnNewAcc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		//ok button -> back to MainFarme
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.lunch();
				f.dispose();
			}
		});
		btnOk.setBackground(new Color(255, 255, 153));
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnOk.setBounds(76, 342, 216, 36);
		panelLeft.add(btnOk);
		
	}
}
