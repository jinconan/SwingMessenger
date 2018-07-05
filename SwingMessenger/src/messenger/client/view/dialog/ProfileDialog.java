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
	private JLabel jlb_name;
	private JLabel jlb_gender;
	private JLabel jlb_hp;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ProfileDialog(MemberVO memVO) {
		this.setTitle("ÇÁ·ÎÇÊ");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 350, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 334, 451);
		contentPane.add(panel);
		panel.setBackground(new Color(126, 195, 237));
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
		
		jlb_background = memVO.getMem_background();
		jlb_background.setForeground(Color.PINK);
		jlb_background.setBackground(Color.RED);
		jlb_background.setBounds(0, 0, 334, 260);
		panel.add(jlb_background);
		
		jlb_name = new JLabel(memVO.getMem_name());
		jlb_name.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_name.setBounds(0, 320, 334, 40);
		panel.add(jlb_name);
		
		jlb_gender = new JLabel(memVO.getMem_gender());
		jlb_gender.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_gender.setBounds(0, 360, 334, 40);
		panel.add(jlb_gender);
		
		jlb_hp = new JLabel(memVO.getMem_hp());
		jlb_hp.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_hp.setBounds(0, 400, 334, 40);
		panel.add(jlb_hp);
		
		this.setVisible(true);
	}
}
