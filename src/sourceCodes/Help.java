package sourceCodes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Help extends JFrame {
	private JPanel p1, p2, p3, p4, p5, p6;
	private Font f = new Font("Time New Roman", Font.BOLD, 30);
	private JLabel jlTitle = new JLabel("User Guide", JLabel.CENTER);
	private JLabel jl1 = new JLabel("Input Format", JLabel.CENTER);
	private JLabel jlImg = new JLabel(new ImageIcon("image/inputText.PNG"));
	private JButton jbtGo = new JButton("Continue");

	private JTextArea jtaBody = new JTextArea();

	public static void main(String[] args) {
		Help h = new Help();

	}

	public Help() {
		setVisible(true);
		setTitle("Information");
		setSize(700, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		/// UI
		setLayout(new BorderLayout());

		/////// title
		jlTitle.setFont(f);
		p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(jlTitle, BorderLayout.CENTER);

		///////////// button
		p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		p2.add(jbtGo);

		////////////// body
		jtaBody.append(
				"   This system is a parser automation system used by top-down parsing technique. Based on user input CFG, this system"
						+ "\n"
						+ " can automatically determine whether the input grammar is backtrack-free grammar. If the parser determines that input "
						+ "\n"
						+ "CFG is backtrack-free grammar, it can automatically build appropriate parse table. If the parser determines that "
						+ "\n"
						+ "the input CFG is not backtrack-free grammar, it reports the problem and appropriate information to the user."
						+ "\n"
						+ " Based on the input CFG, this system shows the user how a compiler’s parser (Top-down parser) works step by step."
						+ "\n"
						+ "After generating parse table, this system can make derivation process with an input string. If the system determines "
						+ "\n"
						+ "the input string is a valid string, it reports the result to user step by step. If the system determines the input "
						+ "\n" + "string is not a valid string, it reports the problem message to user.");
		jtaBody.setEditable(false);
		p3 = new JPanel();
		p3.setLayout(new BorderLayout());
		p3.add(jtaBody, BorderLayout.CENTER);

		p4 = new JPanel();
		p4.setLayout(new BorderLayout());
		p4.add(jl1, BorderLayout.NORTH);
		p4.add(jlImg, BorderLayout.CENTER);

		jl1.setFont(f);
		p5 = new JPanel();
		p5.setLayout(new BorderLayout());
		p5.add(p3, BorderLayout.NORTH);
		p5.add(p4, BorderLayout.CENTER);

		add(p1, BorderLayout.NORTH);
		add(p5, BorderLayout.CENTER);
		add(p2, BorderLayout.SOUTH);

		jbtGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parser p=new parser();
				setVisible(false);
				p.setVisible(true);
			}
		});

	}
}
