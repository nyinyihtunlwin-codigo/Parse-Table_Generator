package sourceCodes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Derivation extends JFrame {

	private JButton jbtStart, jbtReset,jbtBack;
	private JTextField jtfInput = new JTextField(20);
	private JLabel jlbTitle = new JLabel(" Enter input string ..");
	private JLabel jlbResult = new JLabel("");

	private JTabbedPane jtpShow = new JTabbedPane();

	private JPanel p1, p2, p3, p4, pShow;

	private Font f = new Font("Time New Roman", Font.BOLD, 18);

	private DefaultTableModel Data;
	private JTable Table;
	private ArrayList<String> row;
	private String[] colNames = { "Rule", "Stack", "Input" };

	private ArrayList<String> inputList = new ArrayList<String>();

	private int tableStore[][];
	private ArrayList<NonTerminal> tList = new ArrayList<NonTerminal>();
	private ArrayList<String> terList = new ArrayList<String>();
	private ArrayList<String> nonterList = new ArrayList<String>();
	ArrayList<String> rule;
	ArrayList<String> stack;
	ArrayList<String> input;

	public Derivation(int tableStore[][], ArrayList<String> terList, ArrayList<String> nonterList,
			ArrayList<NonTerminal> tList) {
		this.tableStore = tableStore;
		this.terList = terList;
		this.nonterList = nonterList;
		this.tList = tList;
		GenerateUI();
	}

	private void GenerateUI() {
		setTitle("Derivation");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);
		setLayout(new BorderLayout());

		jbtStart = new JButton("Check");
		jbtReset = new JButton("Reset");
		

		p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(jtfInput, BorderLayout.CENTER);
		p1.add(jbtStart, BorderLayout.EAST);

		jlbTitle.setFont(f);
		p2 = new JPanel();
		p2.setLayout(new GridLayout(2, 1));
		p2.add(jlbTitle);
		p2.add(p1);

		p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.RIGHT));
		p3.add(jbtReset);

		//// for result message
		jlbResult.setFont(f);
		p4 = new JPanel();
		p4.setLayout(new BorderLayout());
		p4.add(jlbResult, BorderLayout.CENTER);
		p4.add(p3, BorderLayout.SOUTH);
		////////////////

		Data = new DefaultTableModel();
		Table = new JTable(Data);
		
		JPanel pt=new JPanel();
		pt.setLayout(new BorderLayout());
		pt.add(Table,BorderLayout.CENTER);

		pShow = new JPanel();
		pShow.setLayout(new BorderLayout());
		pShow.add(new JScrollPane(pt));
		jtpShow.add(pShow, "Result");

		add(p2, BorderLayout.NORTH);
		add(jtpShow, BorderLayout.CENTER);
		add(p4, BorderLayout.SOUTH);

		jbtReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jbtStart.setEnabled(true);
				jtfInput.setText("");
				jlbResult.setText("");
				inputList.removeAll(inputList);
				int row = Data.getRowCount();
				Data.setColumnCount(0);
				for (int i = 1; i <= row; i++) {
					Data.removeRow(row - i);
				}
			}
		});
		jbtStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jbtStart.setEnabled(false);

				String strInput = jtfInput.getText().toString();
				System.out.println(strInput);
				StringTokenizer st = new StringTokenizer(strInput, " ");
				while (st.hasMoreTokens()) {
					inputList.add(st.nextElement().toString());
				}
				System.out.println(inputList);

				System.out.println("Table Store is ...........");
				for (int a = 0; a < tableStore.length; a++) {
					for (int b = 0; b < tableStore[a].length; b++) {
						if (tableStore[a][b] != 0) {
							System.out.print("(" + a + "," + b + ")");
							System.out.print(tableStore[a][b]);
						}
					}
					System.out.println();
				}
				System.out.println("terlist is " + terList);
				System.out.println("nterList is " + nonterList);

				if (strInput.equals("")) {
					System.out.println("Wrong Input");
					JOptionPane.showMessageDialog(null, "Please, Enter an input string !", "Message",
							JOptionPane.INFORMATION_MESSAGE);
					jbtStart.setEnabled(true);
					jtfInput.setText("");
					jlbResult.setText("");
					inputList.removeAll(inputList);
					int row = Data.getRowCount();
					Data.setColumnCount(0);
					for (int i = 1; i <= row; i++) {
						Data.removeRow(row - i);
					}

				} else {
					Data.addColumn("Rule");
					Data.addColumn("Stack");
					Data.addColumn("Input");
					Data.addRow(colNames);
					int count = 0;
					for (int s = 0; s < inputList.size(); s++) {
						if (!terList.contains(inputList.get(s))) {
							count++;
						}
					}
					if (count > 0) {
						JOptionPane.showMessageDialog(null, "Input String Error!", "Message",
								JOptionPane.INFORMATION_MESSAGE);
						jbtStart.setEnabled(true);
						jtfInput.setText("");
						jlbResult.setText("");
						inputList.removeAll(inputList);
						int row = Data.getRowCount();
						Data.setColumnCount(0);
						for (int i = 1; i <= row; i++) {
							Data.removeRow(row - i);
						}
					} else {
						funDerivate();
					}
				}

			}
		});

	}

	public void funDerivate() {
		rule = new ArrayList<String>();
		stack = new ArrayList<String>();
		input = new ArrayList<String>();
		rule.add("_");
		stack.add(nonterList.get(0));
		input.addAll(inputList);
		showMe();
		int pointer = 0;
		while (stack.size() >= 0) {
			System.out.println("Now stack size is : " + stack.size());
			if (input.get(pointer).contentEquals(stack.get(0))) {
				System.out.println("Change state");
				if (input.size() == 1) {
					System.out.println("One?");
					for (int i = stack.size() - 1; i >= 0; i++) {
						stack.remove(0);
						rule.add("->");
						showMe();
					}
					break;
				} else {
					System.out.println("NO One?");
					if (input.size() - 1 == pointer) {
						System.out.println("Yes, it's Done");
						for (int i = stack.size() - 1; i >= 0; i++) {
							stack.remove(0);
							rule.add("->");
							showMe();
						}
					} else {
						pointer++;
						stack.remove(0);
						System.out.println("Now Pointer is at (" + pointer + ")" + input.get(pointer));
						rule.add("->");
						showMe();
					}
				}
			} else {
				System.out.println("process.....");
				System.out.println("Now pointer is ..(" + pointer + ")" + input.get(pointer));
				System.out.println("item no is :(" + nonterList.indexOf(stack.get(0)) + ","
						+ terList.indexOf(input.get(pointer)) + ")");
				int i = tableStore[nonterList.indexOf(stack.get(0))][terList.indexOf(input.get(pointer))];
				int j = i - 1;
				if (j == -1) {
					jlbResult.setText("Input string is invalid!");
					break;
				} else {
					jlbResult.setText("Input string is a valid string!");
					System.out.println("data is " + j);
					for (NonTerminal nt : tList) {
						for (int k = 0; k < nt.getProduction().size(); k++) {
							System.out.println("no is " + nt.getProduction().get(k).getPno());
							if (nt.getProduction().get(k).getPno() == j) {
								if (nt.getProduction().get(k).getRight().get(0).contentEquals("@")) {
									System.out.println("find @ and skip");
									stack.remove(0);
									rule.add("" + nt.getProduction().get(k).getPno());
									showMe();
								} else {
									System.out.println("Replace with = " + nt.getProduction().get(k).getRight());
									stack.remove(0);
									stack.addAll(0, nt.getProduction().get(k).getRight());
									rule.add("" + nt.getProduction().get(k).getPno());
									showMe();
								}
							}
						}

					}
				}

			}

		}
	}

	public void showMe() {
		System.out.println(rule.get(rule.size() - 1) + "\t" + stack + "\t" + input);
		row = new ArrayList<String>();
		ArrayList<String> newRow = new ArrayList<String>();
		ArrayList<String> reverseList = new ArrayList<String>();
		row.add(rule.get(rule.size() - 1));
		newRow.addAll(stack);
		newRow.add("eof");
		for (int i = newRow.size() - 1; i >= 0; i--) {
			reverseList.add(newRow.get(i));
		}
		row.add(reverseList.toString());
		row.add(input.toString());
		Data.addRow(row.toArray());
	}
}
