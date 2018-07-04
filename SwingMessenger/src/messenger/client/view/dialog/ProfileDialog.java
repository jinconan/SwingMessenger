package messenger.client.view.dialog;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import messenger._db.vo.MemberVO;

public class ProfileDialog extends JDialog {

	private JPanel contentPane;
	private JLabel jlb_background;
	private JLabel jlb_id;
	private JLabel jlb_profile;
	private JLabel jlb_nick;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ProfileDialog(MemberVO memVO) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 350, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 334, 451);
		contentPane.add(panel);
		panel.setLayout(null);
		
		jlb_nick = new JLabel(memVO.getMem_nick());
		jlb_nick.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_nick.setBackground(Color.CYAN);
		jlb_nick.setBounds(0, 93, 334, 60);
		panel.add(jlb_nick);
		
		jlb_profile = memVO.getMem_profile();
		jlb_profile.setBounds(130, 185, 75, 75);
		panel.add(jlb_profile);
		
		jlb_id = new JLabel(memVO.getMem_id());
		jlb_id.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_id.setBackground(Color.MAGENTA);
		jlb_id.setBounds(0, 260, 334, 45);
		panel.add(jlb_id);
		
		jlb_background = new JLabel();
		jlb_background.setIcon(new ImageIcon(ProfileDialog.class.getResource("/messenger/server/images/background/background_0.jpg")));
		jlb_background.setForeground(Color.PINK);
		jlb_background.setBackground(Color.RED);
		jlb_background.setBounds(0, 0, 334, 260);
		panel.add(jlb_background);
		
		this.setVisible(true);
	}
}
