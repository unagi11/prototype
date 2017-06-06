package prototype;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.*;

//import Test.MyPanel;

public class test{

	private JFrame mainFrame = new JFrame();
	private JPanel northP, centerP, westP;
	private JTabbedPane tabbedPanel;
	private JToolBar toolBar;
	
	MakerMouseListener MakerMouse = new MakerMouseListener();
	MoverMouseListener MoverMouse = new MoverMouseListener();
	SizerMouseListener SizerMouse = new SizerMouseListener();
	DestroyerMouseListener DestroyerMouse = new DestroyerMouseListener();
	
	int IOC = 0;// Index of Component
	int mode;

	
	Vector<MyComponent> VC = new Vector<MyComponent>(); //Vector of Components
	
	test() {
		mainFrame.setTitle("Drawing Square");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1000, 1000);

		mainFrame.setLayout(new BorderLayout(10, 10));

		mkNorthP();
		mkCenterP();
		mkWestP();
		
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
		NewButton.addActionListener(new MkCompoListener());
		northP.setLayout(new BorderLayout());

		JButton OpenButton = new JButton("Open");
		JButton SaveButton = new JButton("Save");

		JButton DeleteButton = new JButton("Delete");
		DeleteButton.addActionListener(new ChangeModeListener());
		
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
	}
	
	
	void mkWestP() {
		westP = new JPanel();
		mainFrame.add(westP, BorderLayout.WEST);
		tabbedPanel = new JTabbedPane();
		tabbedPanel.setPreferredSize(new Dimension(200, 800));
		westP.add(tabbedPanel);
	}
	
	void mkTab() {
		IOC++;
		VC.add(new MyComponent(Integer.toString(IOC),IOC*100,IOC*100,new Dimension(100,100)));
		JPanel TP = new JPanel();
		TP.setPreferredSize(new Dimension(200, 100));
		tabbedPanel.addTab(Integer.toString(IOC), TP);
		System.out.println("인덱스"+ VC.size()+ "");

		TP.setLayout(new GridLayout(0,2));

		TP.add(new JLabel("Name : "));
		TP.add(VC.lastElement().T_name);
		TP.add(new JLabel("x : "));
		TP.add(VC.lastElement().T_x);
		TP.add(new JLabel("y : "));
		TP.add(VC.lastElement().T_y);
		TP.add(new JLabel("width : "));
		TP.add(VC.lastElement().T_w);
		TP.add(new JLabel("height : "));
		TP.add(VC.lastElement().T_h);
		
		System.out.println("텝을만들어");
	}


	public static void main(String[] args){
		new test();
	}
	
	class MkCompoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mkTab();
		}
	}
	
	class ChangeModeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(mode ==0)
				mode =1;
			else
				mode =0;
			
			System.out.println("mode: "+mode);
		}
	}

	class ClickButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for(MyComponent I: VC){
				if(e.getSource() == I){
					I.hide();
					I.T_x.disable();
					I.T_y.disable();
					I.T_w.disable();
					I.T_h.disable();
					I.T_name.disable();
					I.updata();
					VC.removeElement(I);
					break;
				}
			}
		}
	}
	
	class MakerMouseListener extends MouseAdapter implements MouseMotionListener {
		public void mousePressed(MouseEvent e) {

		}
		
		public void mouseReleased(MouseEvent e) {

		}

		public void mouseDragged(MouseEvent e) {

		}

		public void mouseMoved(MouseEvent e) {
		}
	}
	class MoverMouseListener extends MouseAdapter implements MouseMotionListener {
		
	}
	class SizerMouseListener extends MouseAdapter implements MouseMotionListener {
		
	}
	class DestroyerMouseListener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			Point MouseP = e.getPoint();
			System.out.println("destory :"+MouseP.getX()+","+MouseP.getY());
			for(MyComponent I : VC){
				if((I.p.x<MouseP.x)&&(I.p.x+I.d.width>MouseP.x)&&(I.p.y<MouseP.y)&&(I.p.y+I.d.height>MouseP.y)){
					VC.removeElement(I);
					break;
				}
			}
		}
	}


	class MyComponent extends JButton{
		String name;
		Point p;
		Dimension d;
		
		JTextField T_name = new JTextField();
		JTextField T_x = new JTextField();
		JTextField T_y = new JTextField();
		JTextField T_w = new JTextField();
		JTextField T_h = new JTextField();
		
		MyComponent() {// 디폴트 생성자
			centerP.add(this);
			addActionListener(new ClickButtonListener());
			setName("unknown");
			setLocation(new Point(200, 200));
			setSize(new Dimension(100, 100));
			System.out.println("Default-c");
		}

		MyComponent(String name, int x, int y, Dimension d) {// 위치, 크기
			centerP.add(this);
			addActionListener(new ClickButtonListener());
			setName(name);
			setLocation(new Point(x, y));
			setSize(d);
			System.out.println("npd-c");
		}

		MyComponent(String name) {// 이름, 위치, 크기
			centerP.add(this);
			addActionListener(new ClickButtonListener());
			setName(name);
			setLocation(new Point(200, 200));
			setSize(new Dimension(100,100));
			System.out.println("n-c");
		}
		

		public void setName(String name) {
			this.name = name;
			super.setText("name");
			T_name.setText(name);
			System.out.println("setname : "+name);
		}

		public void setLocation(Point p) {
			this.p = p;
			T_x.setText(Double.toString(p.x));
			T_y.setText(Double.toString(p.y));
			super.setLocation(p);
		}

		public void setSize(Dimension d) {
			this.d = d;
			T_w.setText(Double.toString(d.getWidth()));
			T_h.setText(Double.toString(d.getHeight()));
			super.setSize(d);
		}
		
		public void updata(){
			setName(name);
			setLocation(p);
			setSize(d);
		}
	}
}
