package sourceCodes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

public class parser extends JFrame {

	/////////////////////////////////////////////////////////////////////////// For
	/////////////////////////////////////////////////////////////////////////// Process

	private ArrayList<Table_Data> tDList;
	private ArrayList<NonTerminal> tListCopy;//////// left
	private ArrayList<NonTerminal> tList;
	private ArrayList<String> nonterminalList;
	private ArrayList<String> terminalList;
	private ArrayList<Production> pList;
	private ArrayList<String> nterList; /////// for copy
	private ArrayList<String> terList; ////// for copy
	private String[] in, h;
	private Table_Data d;

	private ArrayList<NonTerminal> temp;
	//// Belows are used for finding Follow Set
	private ArrayList<String> oList;
	// Belows are used for finding First+ Set
	private String plusTester;
	private String str;
	private String space;
	private int count;

	private ArrayList<String> fSet, foSet, tSet;
	private ArrayList<String> fSetData, foSetData, tSetData;

	////////////////////////////////////////////////////////////////////////////// For
	////////////////////////////////////////////////////////////////////////////// GUI
	private JTextArea jtaInput = new JTextArea();
	private JTextArea jtaTran = new JTextArea();
	private JTextArea jtaFirstplus = new JTextArea();
	private JTextArea jtaResult = new JTextArea();
	private JTextArea jTerminal = new JTextArea(2, 2);
	private JTextArea jNTerminal = new JTextArea(2, 2);

	private JTabbedPane jtp_terminal = new JTabbedPane();
	private JTabbedPane jtp_nterminal = new JTabbedPane();

	private JTabbedPane jtp_inputText = new JTabbedPane();
	private JTabbedPane jtp_tranText = new JTabbedPane();
	private JTabbedPane jPplus = new JTabbedPane();
	private JTabbedPane jPFirst = new JTabbedPane();
	private JTabbedPane jPFollow = new JTabbedPane();
	private JTabbedPane jPTable = new JTabbedPane();
	private JTabbedPane jPResult = new JTabbedPane();

	private DefaultTableModel firstData, followData, tableData;
	private JTable firstTable, followTable, table;

	private JPanel pOne = new JPanel();
	private JPanel pTwo = new JPanel();

	private JPanel pThree = new JPanel();
	private JPanel pFour = new JPanel();
	private JPanel pFive = new JPanel();

	private int[][] tableStore;

	private JPanel centerP = new JPanel();

	private JButton jbtStart = new JButton("Strat", new ImageIcon("image/start.png"));
	private JButton jbtCheck = new JButton("Check", new ImageIcon("image/check.png"));
	private JButton jbtReset = new JButton("Reset", new ImageIcon("image/reset.png"));
	private JButton jbtGotoMain=new JButton("Go to Main");
	private JButton jbtExit = new JButton("Exit", new ImageIcon("image/exit.png"));
	private JButton jbtOpen = new JButton("Open", new ImageIcon("image/open.png"));
	private JButton jbtGTable = new JButton("Generate Table", new ImageIcon("image/table.png"));
	private JButton jbtDerivate = new JButton("Go to Derivation!");

	private JPanel leftP = new JPanel(); ////////////////////// main
	private JPanel pp1 = new JPanel();
	private JPanel pp2 = new JPanel();
	private JPanel p1 = new JPanel();
	private JPanel p2 = new JPanel();
	private JPanel p3 = new JPanel();
	private JPanel p4 = new JPanel();
	private JPanel rightP = new JPanel();//////////////////// main
	private JPanel botP = new JPanel();///////////////////// main

	private JToolBar jtBar = new JToolBar();

	private File file;

	public static void main(String[] args) {
		///////////////////////////////////////////////////////////////////////// GUI_Start
		parser f = new parser();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public parser() {
		Toolkit tkit = this.getToolkit();
		Dimension screenSize = tkit.getScreenSize();
		setTitle("Top-Down Parser Generator");
		setIconImage(new ImageIcon("image/appicon.png").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(screenSize);
		setLayout(new BorderLayout());
		jtp_inputText.add(new JScrollPane(jtaInput), "Input CFG");

		jtp_tranText.add(new JScrollPane(jtaTran), "Tansformed CFG");

		p3.setLayout(new FlowLayout(FlowLayout.RIGHT));
		p3.add(jbtStart);

		pp1.setLayout(new BorderLayout());
		pp1.add(jtp_inputText, BorderLayout.CENTER);
		pp1.add(p3, BorderLayout.SOUTH);

		p4.setLayout(new FlowLayout(FlowLayout.RIGHT));
		p4.add(jbtCheck);

		pp2.setLayout(new BorderLayout());
		pp2.add(jtp_tranText, BorderLayout.CENTER);
		pp2.add(p4, BorderLayout.SOUTH);

		p1.setLayout(new GridLayout(1, 2)); /////////////////////////////////// for
											/////////////////////////////////// two
											/////////////////////////////////// text
											/////////////////////////////////// box
		p1.add(pp1);
		p1.add(pp2);

		leftP.setLayout(new BorderLayout());////////////////////////////////////// left
											////////////////////////////////////// panel
											////////////////////////////////////// addition
		//////////////////////////////////////////////// for first
		firstData = new DefaultTableModel();
		firstTable = new JTable(firstData);
		JPanel pf=new JPanel();
		pf.setLayout(new BorderLayout());
		pf.add(firstTable,BorderLayout.CENTER);
		//jPFirst.add(new JScrollPane(pf), "First Sets");
		////////////////////////////////////////////// for follow set
		followData = new DefaultTableModel();
		followTable = new JTable(followData);
		JPanel pfo=new JPanel();
		pfo.setLayout(new BorderLayout());
		pfo.add(followTable,BorderLayout.CENTER);
		//jPFollow.add(new JScrollPane(pfo), "Follow Sets");
		//////////////////////////////// for terminal and nonterminal
		jtp_terminal.add(new JScrollPane(jTerminal), "Terminal Nodes");
		jtp_nterminal.add(new JScrollPane(jNTerminal), "Non-Terminal Nodes");

		p2.setLayout(new GridLayout(4, 1));
		p2.add(jtp_terminal);
		p2.add(jtp_nterminal);
		p2.add(new JScrollPane(pf));
		p2.add(new JScrollPane(pfo));

		leftP.add(p1, BorderLayout.CENTER);
		leftP.add(p2, BorderLayout.SOUTH);

		jtBar.add(jbtOpen);

		///////////////////////////////////////////// for table

		tableData = new DefaultTableModel();
		table = new JTable(tableData);
		jPTable.add(table, "Parse Table");
		/////////////////////////////////////////////////// for result

		pFour.setLayout(new BorderLayout());
		pFour.add(new JScrollPane(jtaResult), BorderLayout.CENTER);
		jPResult.add(pFour, "Result");

		pFive.setLayout(new BorderLayout());
		pFive.add(new JScrollPane(jtaFirstplus), BorderLayout.CENTER);
		jPplus.add(pFive, "First+Sets");

		pOne.setLayout(new GridLayout(1, 2));
		pOne.add(jPplus);
		pOne.add(jPResult);

		/////////////////////// for button
		pThree.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pThree.add(jbtGTable);

		pTwo.setLayout(new BorderLayout());
		pTwo.add(pThree, BorderLayout.NORTH);
		pTwo.add(jPTable, BorderLayout.CENTER);

		rightP.setLayout(new GridLayout(2, 1));
		rightP.add(pOne);
		rightP.add(pTwo);

		///////////////////////////////////////////////////// binding center
		centerP.setLayout(new GridLayout(1, 2));
		centerP.add(leftP);
		centerP.add(rightP);

		//////////////////////////////////////////////////////// for bottom
		JPanel padd = new JPanel();
		padd.setLayout(new FlowLayout(FlowLayout.RIGHT));
		padd.add(jbtDerivate);

		botP.setLayout(new FlowLayout(FlowLayout.RIGHT));
		botP.add(jbtGotoMain);
		botP.add(jbtReset);
		botP.add(jbtExit);

		JPanel pall = new JPanel();
		pall.setLayout(new GridLayout(2, 1));
		pall.add(padd);
		pall.add(botP);
		//////////////////////////////////////////////////////////////// end
		//////////////////////////////////////////////////////////////// bottom

		add(jtBar, BorderLayout.NORTH);
		add(centerP, BorderLayout.CENTER);
		add(pall, BorderLayout.SOUTH);
		jbtStart.setEnabled(false);
		jbtCheck.setEnabled(false);
		jbtGTable.setEnabled(false);
		jbtDerivate.setEnabled(false);
		jbtOpen.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("."));
				if (chooser.showOpenDialog(jbtOpen) == JFileChooser.APPROVE_OPTION) {
					jtaInput.setText("");
					jtaTran.setText("");
					jTerminal.setText("");
					jNTerminal.setText("");
					jtaFirstplus.setText("");
					jtaResult.setText("");
					jbtStart.setEnabled(true);
					jbtDerivate.setEnabled(false);
					int row = firstData.getRowCount();
					firstData.setColumnCount(0);
					for (int i = 1; i <= row; i++) {
						firstData.removeRow(row - i);
					}
					int rrow = followData.getRowCount();
					followData.setColumnCount(0);
					for (int i = 1; i <= rrow; i++) {
						followData.removeRow(rrow - i);
					}
					int rc = tableData.getRowCount();
					tableData.setColumnCount(0);
					System.out.println("t r c  " + rc);
					for (int i = 1; i <= rc; i++) {
						tableData.removeRow(rc - i);
					}

					open(chooser.getSelectedFile());
					file = new File(chooser.getSelectedFile().getPath());
					System.out.println("path is now : " + chooser.getSelectedFile().getPath());
				}
			}

			private void open(File selectedFile) {
				try {
					BufferedInputStream in = new BufferedInputStream(new FileInputStream(selectedFile));
					byte[] b = new byte[in.available()];
					in.read(b, 0, b.length);
					jtaInput.append(new String(b, 0, b.length));
					in.close();
				} catch (Exception e) {
					System.err.println();
				}

			}
		});
		jbtStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbtStart.setEnabled(false);
				jbtCheck.setEnabled(true);
				Generate(file);
			}
		});
		jbtGotoMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Welcome w=new Welcome();
				w.setVisible(true);
				setVisible(false);
				w.setSize(650,450);
			}
		});
		jbtCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbtCheck.setEnabled(false);
				////////////// for firstSet
				fSet = new ArrayList<String>();
				fSetData = new ArrayList<String>();
				firstData.addColumn(" ");
				fSet.add(" ");
				fSetData.add("FirstSets");
				////////////// for follow
				foSet = new ArrayList<String>();
				foSetData = new ArrayList<String>();
				followData.addColumn(" ");
				foSet.add(" ");
				foSetData.add("FollowSets");
				////////////////////// start process
				for (NonTerminal nt : tList) {
					System.out.println("First of " + nt.getName() + " is " + nt.getFirst() + " ");
					///////////// for firstset
					firstData.addColumn(nt.getName());
					System.out.println("column is : " + nt.getName());
					fSetData.add(nt.getFirst().toString());
					fSet.add(nt.getName());
					/////////////// for followSet
					followData.addColumn(nt.getName());
					System.out.println("column is : " + nt.getName());
					foSetData.add(nt.getFollow().toString());
					foSet.add(nt.getName());
				}
				/////////////// for terminal and nonterminal
				jTerminal.append(terminalList.toString());
				jNTerminal.append(nonterminalList.toString());
				//////// for firstSet
				firstData.addRow(fSet.toArray());
				firstData.addRow(fSetData.toArray());
				///////// for followSet
				followData.addRow(foSet.toArray());
				followData.addRow(foSetData.toArray());
				/////////////// for firstPlus
				for (NonTerminal t : tList) {
					for (int o = 0; o < t.getProduction().size(); o++) {
						jtaFirstplus.append(t.getProduction().get(o).getPno() + "      ->      "
								+ t.getProduction().get(o).getFirstplus() + "\n");
					}
				}
				if (isBacktrackFree() == false) {
					System.out.println("Generating Table.......");
					for (NonTerminal no : tList) {
						if (no.getProduction().size() > 1) {
							ArrayList<String> str = new ArrayList<String>();
							for (int i = 0; i < no.getProduction().size(); i++) {
								str.add(no.getProduction().get(i).getFirstplus().toString());
								str.add("   n   ");
							}
							str.remove(str.size() - 1);
							str.add("  =  emptySet");
							for (String sg : str) {
								jtaResult.append(sg);
							}
							jtaResult.append("\n");
						}
					}
					jtaResult.append("\n");
					jtaResult.append("It's a Backtrack-free Grammer." + "\n");
					jtaResult.append("\n");
					jtaResult.append("So, you can generate parse table.");
					jbtGTable.setEnabled(true);
				} else {
					String tester;
					NonTerminal non = new NonTerminal();
					ArrayList<NonTerminal> newtList = new ArrayList<NonTerminal>();
					ArrayList<NonTerminal> copytList = new ArrayList<NonTerminal>();
					copytList.addAll(tList);
					for (NonTerminal n : tList) {
						if (n.getProduction().size() > 1) {
							for (int i = 0; i < n.getProduction().size(); i++) {
								for (int j = 0; j < n.getProduction().get(i).getFirstplus().size(); j++) {
									tester = n.getProduction().get(i).getFirstplus().get(j);
									for (int k = 0; k < n.getProduction().size(); k++) {
										if (k != i) {
											if (n.getProduction().get(k).getFirstplus().contains(tester)) {
												System.out.println("hello" + n.getProduction().size());
												non = n;
												if (!(newtList.contains(non))) {
													newtList.add(non);
												}
											}
										}
									}
								}
							}
						}
					}

					for (NonTerminal nti : newtList) {
						System.out.println("hellollllllll : : :  " + nti.getName());
					}

					jbtGTable.setEnabled(false);
					jbtDerivate.setEnabled(false);
					for (NonTerminal no : newtList) {
						if (no.getProduction().size() > 1) {
							ArrayList<String> str = new ArrayList<String>();
							for (int i = 0; i < no.getProduction().size(); i++) {
								str.add(no.getProduction().get(i).getFirstplus().toString());
								str.add("   n   ");
							}
							str.remove(str.size() - 1);
							str.add("  !=  emptySet");
							for (String sg : str) {
								jtaResult.append(sg);
							}
							jtaResult.append("\n");
						}
					}
					copytList.removeAll(newtList);
					for (NonTerminal no : copytList) {
						if (no.getProduction().size() > 1) {
							ArrayList<String> str = new ArrayList<String>();
							for (int i = 0; i < no.getProduction().size(); i++) {
								str.add(no.getProduction().get(i).getFirstplus().toString());
								str.add("   n   ");
							}
							str.remove(str.size() - 1);
							str.add("  =  emptySet");
							for (String sg : str) {
								jtaResult.append(sg);
							}
							jtaResult.append("\n");
						}
					}
					jtaResult.append("\n");
					jtaResult.append("Your CFG is not a Backtrack-free Grammer." + "\n");
					jtaResult.append("\n");
					jtaResult.append("Sorry,I can't let you generate parse table.");
				}

			}
		});
		jbtReset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jtaInput.setText("");
				jtaTran.setText("");
				jTerminal.setText("");
				jNTerminal.setText("");
				jtaFirstplus.setText("");
				jtaResult.setText("");
				for(int p=0;p<tableStore.length;p++){
					for(int q=0;q<tableStore[p].length;q++){
						tableStore[p][q]=0;
					}
				}
				jbtGTable.setEnabled(false);
				jbtDerivate.setEnabled(false);
				int row = firstData.getRowCount();
				firstData.setColumnCount(0);
				for (int i = 1; i <= row; i++) {
					firstData.removeRow(row - i);
				}
				int rrow = followData.getRowCount();
				followData.setColumnCount(0);
				for (int i = 1; i <= rrow; i++) {
					followData.removeRow(rrow - i);
				}
				int rc = tableData.getRowCount();
				tableData.setColumnCount(0);
				System.out.println("t r c  " + rc);
				for (int i = 1; i <= rc; i++) {
					tableData.removeRow(rc - i);
				}

			}
		});
		jbtExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		jbtGTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateParseTable();
				jbtGTable.setEnabled(false);
				jbtDerivate.setEnabled(true);
			}
		});
		jbtDerivate.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent arg0) {
				new Derivation(tableStore, terList, nterList,tList);
			}
		});
	}

	private void Generate(File file) {
		int left_rec_counter=0;

		tListCopy = new ArrayList<NonTerminal>();//////// left
		tList = new ArrayList<NonTerminal>();
		nonterminalList = new ArrayList<String>();
		terminalList = new ArrayList<String>();
		pList = new ArrayList<Production>();

		temp = new ArrayList<NonTerminal>();
		//// Belows are used for finding Follow Set
		oList = new ArrayList<String>();
		// Belows are used for finding First+ Set
		plusTester = new String();
		////////////////////////////////////////////////////////////////////// processes
		////////////////////////////////////////////////////////////////////// start
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader input = new BufferedReader(fr);
			int i = 0;
			String s;
			while ((s = input.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(s, "->,|"); //////////////////////////// a
				// System.out.println("Token count is : " + st.countTokens());
				// /////////////////////// a

				Production p1 = new Production();
				if (st.countTokens() == 2) {
					String left = (String) st.nextElement();
					//////////////////////////// additional
					System.out.println("Left is now hiihiihiihiihii..........: " + left);
					if (left.contains(" ")||left.contains("\t")) {
						 System.out.println("Yes"+nonterminalList.size());
						left = nonterminalList.get(nonterminalList.size() - 1);
						 System.out.println("Now Left is : " + left);
					}
					//////////////////////////// additional
					left = left.trim();
					p1.setLeft(left);
					String right = (String) st.nextElement();
					StringTokenizer st1 = new StringTokenizer(right, " ");
					while (st1.hasMoreElements()) {
						String a = st1.nextToken();

						a = a.trim();
						p1.getRight().add(a);
						if (!(terminalList.contains(a))) {
							terminalList.add(a);
						}
					}
					pList.add(p1);
					if (!(nonterminalList.contains(left))) {
						System.out.println("inin"+left);
						nonterminalList.add(left);
						NonTerminal t1 = new NonTerminal();
						t1.setName(left);
						t1.setproduction(p1);
						t1.setNo(i);
						i++;
						tList.add(t1);

					} else {
						tList.get(nonterminalList.indexOf(left)).setproduction(p1);
					}

				} else if (st.countTokens() > 2) {
					String left = st.nextElement().toString();
					if (left.contains(" ")) {
						left = nonterminalList.get(nonterminalList.size() - 1);
						p1.setLeft(left);
						String right = st.nextElement().toString();
						StringTokenizer st1 = new StringTokenizer(right, " ");
						while (st1.hasMoreElements()) {
							String a = st1.nextToken();
							a = a.trim();
							p1.getRight().add(a);
							if (!(terminalList.contains(a))) {
								terminalList.add(a);
							}
						}
						pList.add(p1);
						if (!(nonterminalList.contains(left))) {
							// System.out.println(left);
							nonterminalList.add(left);
							NonTerminal t1 = new NonTerminal();
							t1.setName(left);
							t1.setproduction(p1);
							t1.setNo(i);
							i++;
							tList.add(t1);

						} else {
							tList.get(nonterminalList.indexOf(left)).setproduction(p1);
						}
						while (st.hasMoreElements()) {
							System.out.println("left is is " + left);
							System.out.println("right is is " + right);
							Production pTwo = new Production();
							pTwo.setLeft(left);
							right = st.nextElement().toString();
							st1 = new StringTokenizer(right, " ");
							while (st1.hasMoreElements()) {
								String a = st1.nextToken();
								a = a.trim();
								pTwo.getRight().add(a);
								if (!(terminalList.contains(a))) {
									terminalList.add(a);
								}
							}
							pList.add(pTwo);
							if (!(nonterminalList.contains(left))) {
								// System.out.println(left);
								nonterminalList.add(left);
								NonTerminal t1 = new NonTerminal();
								t1.setName(left);
								t1.setproduction(p1);
								t1.setNo(i);
								i++;
								tList.add(t1);

							} else {
								tList.get(nonterminalList.indexOf(left)).setproduction(pTwo);
							}
						}
					} else {
						p1.setLeft(left);
						String right = st.nextElement().toString();
						StringTokenizer st1 = new StringTokenizer(right, " ");
						while (st1.hasMoreElements()) {
							String a = st1.nextToken();
							a = a.trim();
							p1.getRight().add(a);
							if (!(terminalList.contains(a))) {
								terminalList.add(a);
							}
						}
						pList.add(p1);
						if (!(nonterminalList.contains(left))) {
							// System.out.println(left);
							nonterminalList.add(left);
							NonTerminal t1 = new NonTerminal();
							t1.setName(left);
							t1.setproduction(p1);
							t1.setNo(i);
							i++;
							tList.add(t1);

						} else {
							tList.get(nonterminalList.indexOf(left)).setproduction(p1);
						}
						while (st.hasMoreElements()) {
							System.out.println("left is is " + left);
							System.out.println("right is is " + right);
							Production pOne = new Production();
							pOne.setLeft(left);
							right = st.nextElement().toString();
							st1 = new StringTokenizer(right, " ");
							while (st1.hasMoreElements()) {
								String a = st1.nextToken();
								a = a.trim();
								pOne.getRight().add(a);
								if (!(terminalList.contains(a))) {
									terminalList.add(a);
								}
							}
							pList.add(pOne);
							if (!(nonterminalList.contains(left))) {
								// System.out.println(left);
								nonterminalList.add(left);
								NonTerminal t1 = new NonTerminal();
								t1.setName(left);
								t1.setproduction(p1);
								t1.setNo(i);
								i++;
								tList.add(t1);

							} else {
								tList.get(nonterminalList.indexOf(left)).setproduction(pOne);
							}
						}
					}

				}

			}
			terminalList.removeAll(nonterminalList);
			// System.out.println(terminalList.removeAll(nonterminalList));
			// System.out.println("Terminal array is " + terminalList);
			// System.out.println(nonterminalList);
			for(NonTerminal b:tList){
				System.out.println(b.getProduction().size());
				for(int c=0;c<b.getProduction().size();c++){
					
					System.out.println(b.getName()+"->"+b.getProduction().get(c).getRight());
				}
			}
			System.out.println("/-----------Input text-------------/");
			for (NonTerminal a : tList) {
				if (a.getProduction().size() > 1) {
					System.out.println(a.getName() + "->" + a.getProduction().get(0).getRight());
					for (int j = 1; j < a.getProduction().size(); j++) {
						System.out.println("    " + "|" + a.getProduction().get(j).getRight());
					}
				} else {
					for (int k = 0; k < a.getProduction().size(); k++) {
						System.out.println(a.getName() + "->" + a.getProduction().get(k).getRight());
					}
				}
				tListCopy.add(a);
			}
			///////////////////////////////////////// copy for input text show
			/////////////////////////////////////////
			System.out.println("----------------------------------------------------------------");
			for (int j = 0; j < tList.size(); j++) {
				if (isLeftRecursion(tList.get(j))) {
					System.out.println("yes lc  "+tList.get(j).getName()+j);
					left_rec_counter++;
					NonTerminal t = tList.get(j);
					NonTerminal tnew = convertRightRecursion(t, pList);
					// System.out.println("Size is
					// "+tnew.getProduction().size());
					tnew.setNo(tList.indexOf(t) + 1);
					// System.out.println("t.getName() index is :
					// "+tList.indexOf(t));

					/////////////////////////////////////////////////// for
					/////////////////////////////////////////////////// empty
					/////////////////////////////////////////////////// set
					Production pEmp = new Production();
					pEmp.setLeft(tnew.getName());
					pEmp.getRight().add("@");

					tnew.getProduction().add(pEmp);
					///////////////////////////////////////////////////// end
					tList.add(tnew.getNo(), tnew);

					nonterminalList.add(tnew.getNo(), tnew.getName());
				}
			}

			System.out.println("/-----------After Transformation-------------/");
			;
			for (NonTerminal a : tList) {
				if (a.getProduction().size() > 1) {
					jtaTran.append(a.getName() + "->" + a.getProduction().get(0).getRight() + "\n");
					//////////////////////// for counting spaces
					str = a.getName();
					count = 0;
					for (int io = 0; io < str.length(); io++) {
						count++;
					}
					System.out.println("count is " + count);
					space = " ";
					for (int sti = 0; sti <= count; sti++) {
						space += "  ";
					}
					//////////////////////// for counting space end
					for (int j = 1; j < a.getProduction().size(); j++) {
						jtaTran.append(space + "|" + a.getProduction().get(j).getRight() + "\n");
					}
				} else {
					for (int k = 0; k < a.getProduction().size(); k++) {
						jtaTran.append(a.getName() + "->" + a.getProduction().get(k).getRight() + "\n");
					}
				}
			}
			if(left_rec_counter==0){
				jtaTran.append("\n");
				jtaTran.append("No Left-recursion, No need to transform!");
			}
			// showArrayList(rightFirst);
			System.out.println("---------------------------------------");
			if (terminalList.contains("@")) {
				terminalList.remove("@");
			}
			System.out.println("Terminal array is " + terminalList);
			System.out.println("Non-Terminal array is " + nonterminalList);
			System.out.println("---------------------------------------");
			// findFirstSetOf(pList);
			findFirstSet();

			System.out.println("---------------------------------------");
			tList.get(0).getFollow().add("eof");
			findFollowSet();
			System.out.println("---------------------------------------");
			findFirstPlusSet();
			System.out.println("----------------------------------------");
			System.out.println(" " + isBacktrackFree());
			if (isBacktrackFree()) {
				System.out.println("Generating Table.......");
			} else {
				System.out.println("Show Error Message ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void generateParseTable() {
		/////////////////////////////////////////// storing process
		nterList = new ArrayList<String>();
		terList = new ArrayList<String>();
		nterList.addAll(nonterminalList);
		terList.add("eof");
		terList.addAll(terminalList);
		tDList = new ArrayList<Table_Data>();
		int pno = 0;
		System.out.println("(" + nterList.size() + "," + terList.size() + ")");
		tableStore = new int[nterList.size()][terList.size()];

		for (NonTerminal t : tList) {
			in = new String[12];
			for (int p = 0; p < t.getProduction().size(); p++) {
				pno = t.getProduction().get(p).getPno();
				// System.out.println("pno = " + pno);
				for (int f = 0; f < t.getProduction().get(p).getFirstplus().size(); f++) {
					if (!t.getProduction().get(p).getFirstplus().get(f).equals("@")) {
						System.out.println(t.getName());
						// System.out.println("Found at (" +
						// nterList.indexOf(t.getName()) + ","
						// +
						// terList.indexOf(t.getProduction().get(p).getFirstplus().get(f))
						// + ")");
						in[0] = t.getName();
						tableStore[nterList.indexOf(t.getName())][terList
								.indexOf(t.getProduction().get(p).getFirstplus().get(f))] = pno + 1;
						System.out.println("now.. "
								+ tableStore[nterList.indexOf(t.getName())][terList
										.indexOf(t.getProduction().get(p).getFirstplus().get(f))]
								+ " is at (" + nterList.indexOf(t.getName()) + ","
								+ terList.indexOf(t.getProduction().get(p).getFirstplus().get(f)) + ")");
						in[terList.indexOf(t.getProduction().get(p).getFirstplus().get(f)) + 1] = String.valueOf(pno);

						for (String s : in) {
							System.out.print(s);
						}
						System.out.println("");
					}
				}
			}
			d = new Table_Data();
			d.setIndex(in);
			tDList.add(d);
		}
		for (Table_Data dt : tDList) {
			h = new String[12];
			h = dt.getIndex();
			for (String s : h) {
				System.out.print(s);
			}
			System.out.println("----------------");
		}
		////////////////////////////////////// end storing process
		tSet = new ArrayList<String>();
		tSetData = new ArrayList<String>();
		tableData.addColumn("");
		tSet.add("");
		tSetData.add("");
		for (String s : terList) {
			tableData.addColumn(s);
			tSet.add(s);
		}
		tableData.addRow(tSet.toArray());
		for (Table_Data dd : tDList) {
			String[] x = new String[12];
			x = dd.getIndex();
			tableData.addRow(x);
		}
		////////////////////////////////////// table data
		////////////////////////////////////// storage................. start
		////////////////////////////////////// testing ........
		for (int a = 0; a < tableStore.length; a++) {
			for (int b = 0; b < tableStore[a].length; b++) {
				if (tableStore[a][b] != 0) {
					System.out.print("(" + a + "," + b + ")");
					System.out.print(tableStore[a][b]);
				}
			}
			System.out.println();
		}
		////////////////////////////////////// table data storage
		System.out.print("index of name " + terList.indexOf("name"));
		System.out.print("index of Goal " + nterList.indexOf("Goal"));
		System.out.print("next replacement is " + tList.get(0).getProduction().get(0).getRight());
		
		///////////////////////////////////////////////////// end testing
		///////////////////////////////////////////////////// .............new
		///////////////////////////////////////////////////// begins...soon
	}

	private boolean isBacktrackFree() {
		String tester;
		for (NonTerminal n : tList) {
			if (n.getProduction().size() > 1) {
				for (int i = 0; i < n.getProduction().size(); i++) {
					System.out.println("child firstPlusSet  is :--->>> " + n.getProduction().get(i).getFirstplus());
					for (int j = 0; j < n.getProduction().get(i).getFirstplus().size(); j++) {
						tester = n.getProduction().get(i).getFirstplus().get(j);
						System.out.println("Tester is : " + tester);
						for (int k = 0; k < n.getProduction().size(); k++) {
							if (k == i) {
								System.out.println("Not at : " + n.getProduction().get(i).getFirstplus());
							} else {
								System.out.println("Have to compare is : " + n.getProduction().get(k).getFirstplus());
								if (n.getProduction().get(k).getFirstplus().contains(tester)) {
									System.out.println("Parrrrrrrrrrrrrrrrrrrrr");
									System.out.println("hello" + n.getProduction().size());
									return true;
								} else {
									System.out.println("No No No BBBBBBBBBBBBBBB");
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	public void findFirstPlusSet() {
		System.out.println("---First+ Sets--- ");
		int count = -1;
		for (NonTerminal non : tList) {
			for (int x = 0; x < non.getProduction().size(); x++) {
				count++;
				plusTester = non.getProduction().get(x).getRight().get(0);
				non.getProduction().get(x).setPno(count);
				// System.out.println("Tester is " + plusTester);
				if (nonterminalList.contains(plusTester)) {
					// System.out.println("Y......................." +
					// non.getFirst());
					for (NonTerminal n : tList) {
						if (n.getName().contains(plusTester)) {
							// System.out.println("Yess ::::: first set is : " +
							// n.getFirst());
							non.getProduction().get(x).getFirstplus().addAll(n.getFirst());
							break;
						}
					}
				} else if (plusTester.equals("@")) {
					non.getProduction().get(x).getFirstplus().addAll(non.getFollow());
					non.getProduction().get(x).getFirstplus().add(plusTester);
				} else {
					non.getProduction().get(x).getFirstplus().add(plusTester);
				}
				System.out.println(non.getProduction().get(x).getPno() + " " + non.getProduction().get(x).getFirstplus()
						+ " Parent " + non.getName() + " has " + non.getProduction().size() + " productions");
			}
		}
	}

	///////////////////// new Function : First Set below
	public void findFirstSet() {
		String tester;
		for (NonTerminal a : tList) {
			System.out.println("Name is " + a.getName());
			System.out.println("no of Production is " + a.getProduction().size());
			for (int i = 0; i < a.getProduction().size(); i++) {
				System.out.println("Production array no [" + i + "] is " + a.getProduction().get(i).getRight());
				tester = a.getProduction().get(i).getRight().get(0);
				System.out.println("Tester is " + tester);
				if (!(nonterminalList.contains(tester))) {
					if (a.getFirst().contains(tester)) {
						a.getFirst().remove(tester);
						a.getFirst().add(tester);
					} else {
						a.getFirst().add(tester);
					}

				} else {
					if (a.getFirst().contains(tester)) {
						a.getFirst().remove(tester);
						a.getFirst().add(tester);
					} else {
						a.getFirst().add(tester);
					}
				}
			}
			for (int g = 0; g < a.getFirst().size(); g++) {
				if (terminalList.contains(a.getFirst().get(g))) {
					System.out.println("Can find First " + a.getName());
					temp.add(a);

				}
			}
			System.out.println("---------------------------------------");
		}
		System.out.print("Temp includes : [");
		for (NonTerminal n : temp) {
			System.out.print(" " + n.getName() + " ");
		}
		System.out.println("]");
		System.out.println("---------------------------------------");
		/*
		 * for (NonTerminal n : tList) { for (int f = 0; f <
		 * n.getFirst().size(); f++) { if
		 * (nonterminalList.contains(n.getFirst().get(f))) { System.out.println(
		 * "Contains non terminal in " + n.getName() + ", it's " +
		 * n.getFirst().get(f)); firstOf(n);///// -->> goto firstOf() fun:
		 * n.getFirst().remove(n.getFirst().get(f)); } else {
		 * 
		 * } } System.out.println("First set of " + n.getName() + " is " +
		 * n.getFirst()); ///////////// first ///////////// set /////////////
		 * output }
		 */
		System.out.print("Initial temp is : [");
		for (NonTerminal nt : temp) {
			System.out.print(" " + nt.getName() + " ");
		}
		System.out.println("]");
		System.out.print("First is : [");
		for (NonTerminal nt : temp) {
			System.out.print(" " + nt.getFirst() + " ");
		}
		System.out.println("]");
		for (int t = 0; t < temp.size(); t++) {
			for (NonTerminal n : tList) {
				if (n.getFirst().contains(((temp.get(t).getName())))) {
					System.out.println("Yes  " + n.getName() + " It's first is " + n.getFirst());
					n.getFirst().addAll(temp.get(t).getFirst());
					n.getFirst().remove(temp.get(t).getName());
					temp.add(n);
					System.out.print("Temp now is : [");
					for (NonTerminal nt : temp) {
						System.out.print(" " + nt.getName() + " ");
					}
					System.out.println("]");
				} else {
					System.out.println("No  " + n.getName() + " It's first is " + n.getFirst());
				}
			}
		}
	}

	////////////////////// new Function : find follow set
	public void findFollowSet() {
		String name = null; /////////// terminal
		/////////// state
		System.out.println(
				"////////////////////////-----------------------------------------------------///////////////////////////////");
		for (NonTerminal t : tList) {
			String tester = t.getName();
			System.out.println("----------------------------------------------------------------------------");
			System.out.println("Mr.Tester is : " + tester);
			for (NonTerminal nt : tList) {
				for (int k = 0; k < nt.getProduction().size(); k++) {
					oList = nt.getProduction().get(k).getRight();
					System.out.println("production size : " + nt.getProduction().size());
					if (oList.contains(tester)) {
						System.out.println("Found at index no : " + oList.indexOf(tester));
						int index = oList.indexOf(tester);
						if (index == oList.size() - 1) {
							name = oList.get(oList.indexOf(tester));
							System.out.println("No Next Node : " + oList.indexOf(oList.get(index)));
							System.out.println("Parent Node is : " + nt.getProduction().get(k).getLeft());
							System.out.println("It's follow Set is : " + nt.getFollow());
							if (tester != nt.getProduction().get(k).getLeft()) {
								System.out.println("Ma tu lo pound tar..... parent conflict----" + tester);
								for (int it = 0; it < nt.getFollow().size(); it++) {
									if (!t.getFollow().contains(nt.getFollow().get(it))) {
										t.getFollow().add(nt.getFollow().get(it));
									}
								}
							}
							if (!t.getFollow().contains("eof")) {
								t.getFollow().add("eof");
							}
							// System.out.println(t.getName() + " : tester is "
							// + name + " and now is " + t.getFollow());
						} else {
							index++;
							if (nonterminalList.contains(oList.get(index))) {
								System.out.println("Next Node is Non-Terminal.");
								System.out.println("Next Node is : " + oList.get(index));
								for (NonTerminal tt : tList) {
									if (oList.get(index).contentEquals(tt.getName())) {
										System.out.println("Yes " + tt.getName());
										System.out.println("It's first Set is : " + tt.getFirst());
										System.out.println("It's follow Set is : " + tt.getFollow());
										if (tt.getFirst().contains("@")) {
											System.out.println("Par tal........fo is " + tt.getFollow());
											for (int f = 0; f < tt.getFirst().size(); f++) {
												if (!t.getFollow().contains(tt.getFirst().get(f))) {
													t.getFollow().add(tt.getFirst().get(f));
												}
											}
											t.getFollow().remove("@");
											for (String to : tt.getFollow()) {
												if (!t.getFollow().contains(to)) {
													t.getFollow().add(to);
												}
											}
										} else {
											for (int f = 0; f < tt.getFirst().size(); f++) {
												if (!t.getFollow().contains(tt.getFirst().get(f))) {
													t.getFollow().add(tt.getFirst().get(f));
												}
											}
										}
										// for (int e = 0; e <
										// tt.getFirst().size(); e++) {
										// if
										// (t.getFollow().contains(tt.getFirst().get(e)))
										// {
										// break;
										// } else {
										// t.getFollow().add(tt.getFirst().get(e));
										// }
										// }
										if (!t.getFollow().contains("eof")) {
											t.getFollow().add("eof");
										}
										// System.out.println(t.getName() +
										// "----------------" + t.getFollow());
									}

								}
							} else if (terminalList.contains(oList.get(index))) {
								System.out.println("Next Node is Terminal " + oList.get(index));
								System.out.println("Next Node is : " + oList.get(index));
								t.getFollow().add(oList.get(index));
								if (!t.getFollow().contains("eof")) {
									t.getFollow().add("eof");
								}
							}
						}
					} else {
						System.out.println("Error ");
					}
				}
			}
			// System.out.println(
			// t.getName() + "tester" + name +
			// "----------------------------------------" + t.getFollow());
		}

		// System.out.println("eState is "+eState);
		System.out.println("-------------------------------------------");
		for (NonTerminal n : tList) {
			System.out.println("First set of " + n.getName() + " is " + n.getFirst());
		}
		System.out.println("-------------------------------------------");
		for (NonTerminal nT : tList) {
			System.out.println("Follow Set of " + nT.getName() + " is " + nT.getFollow());
		}
	}

	//////////////////////// end and start old fun below

	public Boolean isLeftRecursion(NonTerminal t) {
		ArrayList<Production> p = t.getProduction();
		for (int i = 0; i < p.size(); i++) {
			if (p.get(i).getRight().indexOf(t.getName()) == 0) {
				// System.out.println("left");
				return true;
			}
		}
		return false;
	}

	public NonTerminal convertRightRecursion(NonTerminal t, ArrayList<Production> pList) {
		ArrayList<Production> p = new ArrayList<Production>();
		NonTerminal nt = t;
		p.addAll(t.getProduction());
		 System.out.println("t.getP.size is "+ t.getProduction().size());
		NonTerminal newt = new NonTerminal();
		newt.setName(t.getName() + "'");
		 System.out.println("t.getName() is " + t.getName());
		for (Production pro : p) {
			ArrayList<String> right = pro.getRight();
			if (right.indexOf(t.getName()) == 0) {
				System.out.println("hello");
				Production pnew = new Production();
				right.remove(0);
				right.add(newt.getName());

				pnew.setLeft(newt.getName());
				pnew.setRight(right);

				pList.add(pnew);
				newt.setproduction(pnew);

				pList.remove(pro);
				t.getProduction().remove(pro);

			} else {
				System.out.println("No hello");
				Production prNew = new Production();

				right.add(newt.getName());
				prNew.setLeft(t.getName());
				prNew.setRight(right);
				pList.add(prNew);
				System.out.println(t.getProduction().size()+"index "+pList.indexOf(pro));
				
			//	t.getProduction().add(pList.indexOf(pro), prNew);
				t.getProduction().add(t.getProduction().size(),prNew);
				pList.remove(pro);
				t.getProduction().remove(pro);
				for (int i = 0; i < nt.getProduction().size(); i++) {
					if (nt.getProduction().get(i).getRight().get(0).contains("@")) {
						System.out.println("Yes");
						prNew.getRight().remove("@");
					}
				}
			}
		}
		 System.out.println("size" + t.getProduction().size());

		return newt;
	}

}
