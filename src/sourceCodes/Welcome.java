package sourceCodes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Welcome extends JFrame {

	private JPanel p1, p2, p3, p4, p5, pTop, pCenter, pBot;
	private JLabel jlbIcon = new JLabel(new ImageIcon("image/logo.jpg"));
	private JLabel jlbUniOne = new JLabel(" University Of Technology");
	private JLabel jlbUniTwo = new JLabel("   (Yatanarpon Cyber City)");

	private JLabel jlbThOne = new JLabel("                 Parse-Table Generator");
	private JLabel jlbThTwo = new JLabel("               Using Top-Down Parsing");

	private JLabel jlbSVOne = new JLabel("  Supervised By");
	private JLabel jlbSVTwo = new JLabel("  Dr.Shwe Thinzar Aung");

	private JLabel jlbCOne = new JLabel("Presented By");
	private JLabel jlbCTwo = new JLabel("Nyi Nyi Htun Lwin  ");
	private JLabel jlbCThree = new JLabel("5IS-145");
	
	private JButton jbtStart=new JButton("Start",new ImageIcon("image/start.png"));

	private Font fontOne = new Font("Time New Roman", Font.PLAIN, 35);
	private Font fontTwo = new Font("Time New Roman", Font.BOLD, 20);

	public static void main(String[] args) {
		Welcome w = new Welcome();
		w.setTitle("Parse-Table Generator");
		w.setIconImage(new ImageIcon("image/appicon.png").getImage());
		w.setSize(650, 450);
		w.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		w.setLocationRelativeTo(null);
		w.setVisible(true);
		w.setResizable(false);
	}

	public Welcome() {
		setLayout(new BorderLayout());
		p1 = new JPanel();
		jlbUniOne.setFont(fontOne);
		jlbUniTwo.setFont(fontTwo);
		p1.setLayout(new GridLayout(2, 1));
		p1.add(jlbUniOne);
		p1.add(jlbUniTwo);
		p1.setBackground(Color.white);

		pTop = new JPanel();
		pTop.setLayout(new BorderLayout());
		pTop.add(jlbIcon, BorderLayout.WEST);
		pTop.add(p1, BorderLayout.CENTER);
		pTop.setBackground(Color.white);
		////////////////////////////////////// top end
		jlbThOne.setFont(fontOne);
		jlbThTwo.setFont(fontOne);
		p2 = new JPanel();
		p2.setLayout(new GridLayout(2,1));
		p2.add(jlbThOne);
		p2.add(jlbThTwo);
		
		p5=new JPanel();
		p5.add(jbtStart);
		
		pCenter=new JPanel();
		pCenter.setLayout(new GridLayout(2,1));
		pCenter.add(p2);
		pCenter.add(p5);

		//////////////////////////////////// center end
		p3 = new JPanel();
		p4 = new JPanel();
		jlbSVOne.setFont(fontTwo);
		jlbSVTwo.setFont(fontTwo);
		jlbCOne.setFont(fontTwo);
		jlbCTwo.setFont(fontTwo);
		jlbCThree.setFont(fontTwo);
		p3.setLayout(new GridLayout(3, 1));
		p3.add(jlbSVOne);
		p3.add(jlbSVTwo);
		p3.setBackground(Color.white);

		p4.setLayout(new GridLayout(3, 1));
		p4.add(jlbCOne);
		p4.add(jlbCTwo);
		p4.add(jlbCThree);
		p4.setBackground(Color.white);
		
		pBot=new JPanel();
		pBot.setLayout(new BorderLayout());
		pBot.add(p3,BorderLayout.WEST);
		pBot.add(p4,BorderLayout.EAST);
		pBot.setBackground(Color.white);
		////////////////////////////////// bottom end

		add(pTop, BorderLayout.NORTH);
		add(pCenter, BorderLayout.CENTER);
		add(pBot,BorderLayout.SOUTH);
		
		jbtStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Help();
				setVisible(false);
			}
		});

	}
}
