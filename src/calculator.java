// hello git min
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class calculator extends JFrame {
	private JPanel nPanel;
	private JLabel nInput;
	private JLabel nResult;
	private String[] btStr = { "CE", "C", "←", "÷", "7", "8", "9", "X", "4", "5", "6", "-", "1", "2", "3", "+", "0", ".", "=" }; 
	private JButton btn[] = new JButton[19];
	private JPanel bPanel;
	private GridBagLayout GBL = new GridBagLayout();
	private GridBagConstraints GBC = new GridBagConstraints();
	private String InputStr = "";
	private String ResultStr = "";
	private Integer parse_num1 = 0;
	private Integer parse_num2 = 0;
	private int num1 = 0;
	private int num2 = 0;
	private String Operator;
	private int reset = 0;
	private String result;
	
	public calculator( ) {
		int X = 0;
		int Y = 0;
		setTitle("계산기");
		setSize(300, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		nPanel = new JPanel(new GridLayout(2, 1));
		nInput = new JLabel(InputStr, JLabel.RIGHT);
		nInput.setFont(new Font("굴림", Font.PLAIN, 11));
		nResult = new JLabel(ResultStr, JLabel.RIGHT);
		nResult.setFont(new Font("굴림", Font.BOLD, 30));
		
		nPanel.setBackground(Color.WHITE);
		nPanel.add(nInput);
		nPanel.add(nResult);
		nPanel.setBounds(0, 0, 293, 80);
		
		bPanel = new JPanel();
		bPanel.setBackground(Color.WHITE);
		bPanel.setLayout(GBL);
		bPanel.setBounds(0, 81, 293, 283);
		
		GBC.fill = GridBagConstraints.BOTH;
		GBC.insets = new Insets(1,1,1,1);
		for(int i = 0; i < btn.length; i++) {
			
			if(i % 4 == 0) {
				X = 0;
				Y++;
			}
			btn[i] = new JButton(btStr[i]);
			btn[i].setBackground(Color.WHITE);
			btn[i].setFocusable(false);
			btn[i].addActionListener(new InputButton());
			if(i == 16) {
				addGrid(btn[i], GBL, GBC, X, Y, 2, 1, 1, 1);
				X++;
			}
			else {
				addGrid(btn[i], GBL, GBC, X, Y, 1, 1, 1, 1);
			}
			bPanel.add(btn[i]);
			X++;
		}
		
		add(nPanel);
		add(bPanel);
		addKeyListener(new InputKey());
		setVisible(true);
	}
	
	public void addGrid(Component c, GridBagLayout gbl, GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight
			,int weightx, int weighty) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbl.setConstraints(c, gbc);
	}
	
	class InputButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand() == "CE") {
				nResult.setText("");
				ResultStr = "";
			}
			else if(e.getActionCommand() == "C") {
				ResultStr = "";
				InputStr = "";
				nResult.setText("");
				nInput.setText("");
			}
			else if(e.getActionCommand() == "←") {
				if(ResultStr.length() > 0) {
					ResultStr = ResultStr.substring(0, ResultStr.length()-1);
				}
				nResult.setText(ResultStr);
			}
			else if (e.getActionCommand() == "÷" || e.getActionCommand() == "X" || e.getActionCommand() == "-"
					|| e.getActionCommand() == "+") {
				if (ResultStr.length() > 0) {
					Operator = e.getActionCommand();
					if (InputStr.length() > 0) {
						if (InputStr.charAt(InputStr.length() - 1) != Operator.charAt(0)) {
							InputStr = InputStr.substring(0, InputStr.length() - 1);
							InputStr += e.getActionCommand();
							nInput.setText(InputStr);
						} else {
							InputStr += ResultStr + e.getActionCommand();
							nInput.setText(InputStr);

							parse_num2 = Integer.parseInt(nResult.getText());
							num1 = parse_num1;
							num2 = parse_num2;
							result = Calculation(num1, num2, Operator);
							nResult.setText(result);
							ResultStr = result;

							parse_num1 = Integer.parseInt(ResultStr);
							reset = 1;
							// Operator = e.getActionCommand();
						}
					} else {
						// Operator = e.getActionCommand();
						InputStr += ResultStr + e.getActionCommand();
						nInput.setText(InputStr);

						reset = 1;
						parse_num1 = Integer.parseInt(ResultStr);
						ResultStr = "";
					}
				}
			}
			else if(e.getActionCommand() == ".") {
				if(ResultStr.length() > 0) {
					if(ResultStr.charAt(ResultStr.length()-1) != '.' && ResultStr.indexOf(".") == -1) {
						ResultStr += e.getActionCommand();
					}
				}
				nResult.setText(ResultStr);
			}
			else if (e.getActionCommand() == "=") {
				if (ResultStr.length() > 0) {
					if(parse_num1 != 0 && Operator != "") {
						InputStr += ResultStr;
						nInput.setText(InputStr);

						reset = 1;
						parse_num2 = Integer.parseInt(nResult.getText());
						num1 = parse_num1;
						num2 = parse_num2;
						result = Calculation(num1, num2, Operator);
						
						nResult.setText(result);
						ResultStr = result;

						InputStr = "";
						nInput.setText("");
						Operator = "";
					}
				}
			}
			else if(e.getActionCommand() == "0") {
				ResultStr += e.getActionCommand();
				if(ResultStr.length() < 3 && ResultStr.charAt(0) == '0') {
					ResultStr = "0";
				}
				nResult.setText(ResultStr);
			}
			else {
				if(reset == 1) {
					nResult.setText("");
					ResultStr = "";
					reset = 0;
				}
				ResultStr += e.getActionCommand();
				nResult.setText(ResultStr);
			}
		}
		
	}
	
	class InputKey extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int KeyCode = e.getKeyChar();
			if(ResultStr.length() > 0) {
				if(ResultStr.charAt(0) == '0' && KeyCode != 46) {
					ResultStr = "";
				}
			}
			switch(KeyCode) {
			case KeyEvent.VK_7:
				btn[4].doClick();
				break;
			case KeyEvent.VK_8:
				btn[5].doClick();
				break;
			case KeyEvent.VK_9:
				btn[6].doClick();
				break;
			case KeyEvent.VK_4:
				btn[8].doClick();
				break;
			case KeyEvent.VK_5:
				btn[9].doClick();
				break;
			case KeyEvent.VK_6:
				btn[10].doClick();
				break;
			case KeyEvent.VK_1:
				btn[12].doClick();
				break;
			case KeyEvent.VK_2:
				btn[13].doClick();
				break;
			case KeyEvent.VK_3:
				btn[14].doClick();
				break;
			case KeyEvent.VK_0:
				btn[16].doClick();
				break;
			case KeyEvent.VK_BACK_SPACE:
				btn[2].doClick();
				break;
			case 46:
				btn[17].doClick();
			}
			
		}
		
	}
	
	public String Calculation(int num1, int num2, String Operator) {
		
		Integer result = 0;
		
		if(Operator.equals("+")) {
			result =  num1 + num2;
		}
		else if(Operator.equals("-")) {
			result = num1 - num2;
		}
		else if(Operator.equals("X")) {
			result = num1 * num2;
		}
		else if(Operator.equals("÷")) {
			result = num1 / num2;
		}
		return result.toString();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new calculator();
	}
}