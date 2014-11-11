package aviansiesKiller;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class aviansiesKillerGUI extends JFrame {

	private JPanel contentPane;
	private JTextField foodName;
	private JTextField ammoName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					aviansiesKillerGUI frame = new aviansiesKillerGUI();
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
	public aviansiesKillerGUI() {
		setTitle("aviansiesKiller by pitoluwa");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 461, 274);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(!ammoName.getText().equals(""))
				{
					aviansiesKiller.pickAmmo = true;
					aviansiesKiller.ammoName = ammoName.getText();
				}
				aviansiesKiller.foodname = foodName.getText();				
				aviansiesKiller.guiWait = false;
				dispose();
			}
		});
		
		JTextArea txtrPleaseStartThe = new JTextArea();
		txtrPleaseStartThe.setText("Please start the script inside GWD.\r\nIt kills aviansies for adamant bars and use regenerate to heal!\r\n\r\nIf you don't want to pick ammo, leave ammo name blank.");
		
		JLabel lblFoodName = new JLabel("Food name:");
		
		JLabel lblAmmoName = new JLabel("Ammo name:");
		
		foodName = new JTextField();
		foodName.setText("Lobster");
		foodName.setColumns(10);
		
		ammoName = new JTextField();
		ammoName.setText("Adamant bolts");
		ammoName.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addComponent(lblFoodName)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(foodName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addComponent(lblAmmoName)
								.addGap(18)
								.addComponent(ammoName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(btnNewButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
						.addComponent(txtrPleaseStartThe, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFoodName)
						.addComponent(foodName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAmmoName)
						.addComponent(ammoName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(txtrPleaseStartThe, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton)
					.addContainerGap(204, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	public JTextField getAmmoID() {
		return ammoName;
	}
	public JTextField getFoodID() {
		return foodName;
	}
}
