package prototype;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.*;

//import Test.MyPanel;
public class test {

	private JFrame mainFrame = new JFrame();
	private JPanel northP, centerP, westP;
	private JTabbedPane tabbedPanel;
	private JToolBar toolBar;

	JLabel statusLabel;
	
	MakerMouseListener MakerMouse = new MakerMouseListener();
	MoverMouseListener MoverMouse = new MoverMouseListener();
	SizerMouseListener SizerMouse = new SizerMouseListener();
	SelectorMouseListener SelectorMouse = new SelectorMouseListener();

	
	int IOC = 0;// Index of Component
	int MOM = 0;// Mode of Mouse 0. hand mode, 1. remove mode, 2. maker mode

	Vector<MyComponent> VC = new Vector<MyComponent>(); // Vector of Components

	test() {
		mainFrame.setTitle("Drawing Square");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1000, 1000);

		mainFrame.setLayout(new BorderLayout(10, 10));

		mkNorthP();
		mkCenterP();
		mkWestP();

		statusLabel = new JLabel("Status", JLabel.CENTER);
		statusLabel.setSize(350, 100);
		statusLabel.setForeground(Color.blue);
		centerP.add(statusLabel);

		JMenuBar myMenuBar = new JMenuBar();
		JMenu m_File = new JMenu("File");

		JMenuItem mi_New = new JMenuItem("New");
		m_File.add(mi_New);
		JMenuItem mi_Open = new JMenuItem("Open");
		m_File.add(mi_Open);
		JMenuItem mi_Save = new JMenuItem("Save");
		m_File.add(mi_Save);

		myMenuBar.add(m_File);
		mainFrame.setJMenuBar(myMenuBar);
		mainFrame.setVisible(true);
	}

	void mkNorthP() {
		northP = new JPanel();
		mainFrame.add(northP, BorderLayout.NORTH);

		JButton NewButton = new JButton("New");
		NewButton.addActionListener(new NewButtonListener());
		northP.setLayout(new BorderLayout());

		JButton OpenButton = new JButton("Open");
		JButton SaveButton = new JButton("Save");

		JButton DeleteButton = new JButton("Delete");
		DeleteButton.addActionListener(new DeleteButtonListener());

		JButton SizeButton = new JButton("Size");
		SizeButton.addActionListener(new SizeButtonListener());

		JButton MoveButton = new JButton("Move");
		MoveButton.addActionListener(new MoveButtonListener());

		toolBar = new JToolBar();
		toolBar.setRollover(true);
		northP.add(toolBar);

		toolBar.add(NewButton);
		toolBar.add(OpenButton);
		toolBar.add(SaveButton);
		toolBar.add(DeleteButton);
		northP.setVisible(true);
	}

	void mkCenterP() {
		centerP = new JPanel(null);
		mainFrame.add(centerP, BorderLayout.CENTER);
		centerP.setBackground(Color.white);

		centerP.addMouseListener(MakerMouse);
		centerP.addMouseMotionListener(MakerMouse);
	}

	void mkWestP() {
		westP = new JPanel();
		mainFrame.add(westP, BorderLayout.WEST);
		tabbedPanel = new JTabbedPane();
		tabbedPanel.setPreferredSize(new Dimension(200, 800));
		westP.add(tabbedPanel);
	}



	public static void main(String[] args) {
		new test();
	}

	class NewButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			statusLabel.setText("maked Tab");

		}
	}

	class OpenButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			statusLabel.setText("maked Tab");

		}
	}

	class SaveButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			statusLabel.setText("maked Tab");

		}
	}

	class DeleteButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (MOM != 1) {
				MOM = 1;
				statusLabel.setText("Delete Mode On");
			} else {
				MOM = 0;
				statusLabel.setText("Delete Mode Off");
			}
			System.out.println("mode: " + MOM);
		}
	}

	class SizeButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			statusLabel.setText("Sizer Mode On");

		}
	}

	class MoveButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			statusLabel.setText("Mover Mode On");
		}
	}

	class TextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (MyComponent I : VC) {
				if (e.getSource() == I.T_name) {
					I.name = e.getActionCommand();
					statusLabel.setText("changed Name");
					I.updata();
					break;
				} else if (e.getSource() == null) {
					statusLabel.setText("NumberFormatException");
				} else if (e.getSource() == I.T_x) {
					I.p.x = Integer.parseInt(e.getActionCommand());
					statusLabel.setText("changed X");
					I.updata();
					break;
				} else if (e.getSource() == I.T_y) {
					I.p.y = Integer.parseInt(e.getActionCommand());
					statusLabel.setText("changed Y");
					I.updata();
					break;
				} else if (e.getSource() == I.T_w) {
					I.d.width = Integer.parseInt(e.getActionCommand());
					statusLabel.setText("changed width");
					I.updata();
					break;
				} else if (e.getSource() == I.T_h) {
					I.d.height = Integer.parseInt(e.getActionCommand());
					statusLabel.setText("changed height");
					I.updata();
					break;
				}
			}
		}
	}

	class RemoveButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (MyComponent I : VC) {
				if (e.getSource() == I.UCP && MOM == 1) {
					I.UCP.hide();
					I.T_x.disable();
					I.T_y.disable();
					I.T_w.disable();
					I.T_h.disable();
					I.T_name.disable();
					I.updata();
					VC.removeElement(I);
					MOM = 0;
					statusLabel.setText("Removed Component");
					break;
				} else if (e.getSource() == I.UCP && MOM == 0) {
					break;
				}
			}
		}
	}

	class MakerMouseListener extends MouseAdapter implements MouseMotionListener {
		Point StartP = null;
		Point EndP = null;

		public void mousePressed(MouseEvent e) {
			IOC++;
			StartP = e.getPoint();
			VC.add(new MyComponent(IOC, StartP.x, StartP.y, new Dimension(0, 0)));
		}

		public void mouseReleased(MouseEvent e) {
			EndP = e.getPoint();
			VC.lastElement().setSize(EndP.x - StartP.x, EndP.y - StartP.y);
			centerP.removeMouseListener(MakerMouse);
			centerP.removeMouseMotionListener(MakerMouse);
			centerP.addMouseListener(SelectorMouse);
			centerP.addMouseMotionListener(SelectorMouse);
		}

		public void mouseDragged(MouseEvent e) {
			EndP = e.getPoint();
			VC.lastElement().setSize(EndP.x - StartP.x, EndP.y - StartP.y);
		}
	}

	class MoverMouseListener extends MouseAdapter implements MouseMotionListener {
		Point StartP = null;
		Point EndP = null;
		MyComponent T;

		public void mousePressed(MouseEvent e) {
			StartP = e.getPoint();
			for (MyComponent I : VC) {
				if (I.UCP == e.getComponent()) {
					T = I;
					break;
				}
			}
			// System.out.println("StartP : " + StartP);
		}

		public void mouseReleased(MouseEvent e) {
			EndP = e.getPoint();
			T.setLocation(e.getComponent().getX() + EndP.x - StartP.x,
					e.getComponent().getY() + EndP.y - StartP.y);
			// System.out.println("EndP : " + EndP);
		}

		public void mouseDragged(MouseEvent e) {
			EndP = e.getPoint();
			T.setLocation(e.getComponent().getX() + EndP.x - StartP.x,
					e.getComponent().getY() + EndP.y - StartP.y);
		}
	}

	class SizerMouseListener extends MouseAdapter implements MouseMotionListener {
		Point StartP = null;
		Point EndP = null;
		MyComponent T;
		Boolean MOM2 = true;
		public void mousePressed(MouseEvent e) {
			StartP = e.getPoint();
			for (MyComponent I : VC) {
				if (I.UCP == e.getComponent()) {
					T = I;
					break;
				}
			}
			System.out.println("StartP : " + StartP);
		}

		public void mouseReleased(MouseEvent e) {
			EndP = e.getPoint();
			System.out.println("EndP : " + EndP);
//			T.setSize(EndP.x, EndP.y);
		}

		public void mouseDragged(MouseEvent e) {
			EndP = e.getPoint();
	//		T.setSize(EndP.x, EndP.y);
		}
		public void mouseMoved(MouseEvent e) {
			
		}
	}
	class PanelMouseListener extends MouseAdapter implements MouseMotionListener{
		
	}
	class SelectorMouseListener extends MouseAdapter implements MouseMotionListener{
		Point StartP = null;
		Point EndP = null;
		Point S, N, W, E, SE, SW, NW, NE;
		
		MyComponent SC = null;//Selected Component
		
		public void mousePressed(MouseEvent e) {
			if(centerP == e.getComponent()){//만약 마우스가 센터페널이라면 사이즈 변경을 하면 안된다.
				SC = null;
			}
			StartP = e.getPoint();
			for (MyComponent I : VC) {//선택
				if (I.UCP == e.getComponent()) {
					SC = I;
					SE = new Point(SC.d.width, SC.d.height);
					I.setName("넌 선택되었다");
					return;
				}
				else{//만약 선택된게 있었더라면 선택 취소
					I.setName("넌 선택안되었다.");	
				}
			}
			System.out.println("StartP : " + StartP);
		}

		public void mouseReleased(MouseEvent e) {
			if(centerP == e.getComponent()){//만약 마우스가 센터페널이라면 사이즈 변경을 하면 안된다.
				SC = null;
				return;
			}
			EndP = e.getPoint();
			System.out.println("EndP : " + EndP);
			SC.setSize(EndP.x, EndP.y);
		}

		public void mouseDragged(MouseEvent e) {
			if(centerP == e.getComponent()){//만약 마우스가 센터페널이라면 사이즈 변경을 하면 안된다.
				SC = null;
				return;
			}
			EndP = e.getPoint();
			SC.setSize(EndP.x, EndP.y);
		}
		public void mouseMoved(MouseEvent e) {
			if(SC == null)
				return;
			else if(SC.UCP == e.getComponent()){
				if(e.getPoint().distance(SE) < 50)
					SC.UCP.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
			}
			else{//SC가 존재하고 마우스가 밖에 있을때
				SC.UCP.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				SC.setName("넌 선택되었다.");
			}
			
		}
	}

	class MyComponent{
		//JButton UCP = new JButton();
		JLabel UCP = new JLabel();
		//JCheckBox UCP = new JCheckBox();
		String name;
		Point p;
		Dimension d;
		
		int index;

		JPanel TP = new JPanel();//Tabbed_Panel
		
		JTextField T_name = new JTextField();
		// txt add
		JTextField T_x = new JTextField();
		JTextField T_y = new JTextField();
		JTextField T_w = new JTextField();
		JTextField T_h = new JTextField();

		MyComponent(int index, int x, int y, Dimension d) {// 위치, 크기
			this.index = index;
			setName("Component " + Integer.toString(index));
			setLocation(new Point(x, y));
			setSize(d);
			System.out.println("npd-c");
			construct();
		}

		public void setName(String name) {
			this.name = name;
			T_name.setText(name);
			UCP.setText(name);
		}

		public void setLocation(Point p) {
			this.p = p;
			T_x.setText(Integer.toString(p.x));
			T_y.setText(Integer.toString(p.y));
			UCP.setLocation(p);
		}

		public void setLocation(int x, int y) {
			p.setLocation(x, y);
			T_x.setText(Integer.toString(x));
			T_y.setText(Integer.toString(y));
			UCP.setLocation(x, y);
		}

		public void setSize(Dimension d) {
			this.d = d;
			T_w.setText(Integer.toString(d.width));
			T_h.setText(Integer.toString(d.height));
			UCP.setSize(d);
		}

		public void setSize(int w, int h) {
			d.setSize(w, h);
			T_w.setText(Integer.toString(d.width));
			T_h.setText(Integer.toString(d.height));
			UCP.setSize(w, h);
		}

		public void updata() {
			setName(name);
			setLocation(p);
			setSize(d);
		}
		
		public void construct(){
			mkTab();
			centerP.add(UCP);
			T_name.addActionListener(new TextListener());
			T_x.addActionListener(new TextListener());
			T_y.addActionListener(new TextListener());
			T_w.addActionListener(new TextListener());
			T_h.addActionListener(new TextListener());
			UCP.setOpaque(true);
			UCP.addMouseMotionListener(SelectorMouse);
			UCP.addMouseListener(SelectorMouse);
		}
		
		void mkTab() {
			TP.setPreferredSize(new Dimension(200, 100));
			tabbedPanel.addTab(Integer.toString(index), TP);
			System.out.println("인덱스" + index + "");
			TP.setLayout(new GridLayout(0, 2));

			TP.add(new JLabel("Name : "));
			TP.add(T_name);
			TP.add(new JLabel("x : "));
			TP.add(T_x);
			TP.add(new JLabel("y : "));
			TP.add(T_y);
			TP.add(new JLabel("width : "));
			TP.add(T_w);
			TP.add(new JLabel("height : "));
			TP.add(T_h);

			System.out.println("텝을만들어");
		}
	}
}
