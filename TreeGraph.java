// This work is mine unless otherwise cited. -Robert McMaster

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.Font;
import java.awt.Color;
import java.awt.Image;

public class TreeGraph extends Applet implements Runnable, KeyListener{

	public BinaryTree tree;
	public boolean search;
	public int searchNum;
	public Thread thread;
	public Graphics dbGraphics;
	public Image dbImage;

	private Font leafFont = new Font("Monaco", Font.BOLD, 25);
	private Font titleFont = new Font("Monaco", Font.BOLD, 35);
	String number = "";

	public void init(){
		addKeyListener(this);
		setSize(1300, 800);
		setBackground(new Color(16, 186, 70));
		searchNum = -1;
		search = false;

	}

	public void paint(Graphics g){

		g.setFont(titleFont);

		if(!search){

			if(tree != null) g.drawString("", 380, 680);
			g.drawString("Enter a number into the tree: " + number, 450, 30);
		}

			if(searchNum > -1) g.drawString( tree.search( searchNum ), 420, 200);

		if(tree != null){
			g.drawString("Current Size = " + tree.size(), 850, 70);
			g.drawString("Current Depth = " + tree.depth(), 150, 70);
			g.setFont(leafFont);
			g.setColor(new Color(255, 247, 0));
			tree.drawTree(g, getSize().width, getSize().height);
		}
	}

	public void update(Graphics g){
		if(dbImage == null){
			dbImage = createImage(getSize().width, getSize().height);
			dbGraphics = dbImage.getGraphics();
		}
		dbGraphics.setColor(getBackground());
		dbGraphics.fillRect(0, 0, getSize().width, getSize().height);
		dbGraphics.setColor(getForeground());
		paint(dbGraphics);
		g.drawImage(dbImage, 0, 0, this);
	}

	public void keyPressed(KeyEvent e){
	}

	public void keyReleased(KeyEvent e){

		int keyCode = e.getKeyCode();

		for(int i=48; i<58; i++) if(keyCode == i) number += (i-48);

		if(keyCode == KeyEvent.VK_BACK_SPACE) number = number.substring(0, number.length()-1);

		if(keyCode == KeyEvent.VK_ENTER && number.length() != 0){

			if(search){
				tree.search( Integer.parseInt( number ) );
				searchNum = Integer.parseInt( number );
			}
			else{
				if(tree == null) tree = new BinaryTree( Integer.parseInt( number ) );
				else tree.add( Integer.parseInt ( number ) );
			}

			number = "";
		}

		if(keyCode == KeyEvent.VK_SPACE){
			if(search) search = false;
			else search = true;
		}
	}

	public void keyTyped(KeyEvent e){
	}

	public void start(){

		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}

	public void run(){

		while(thread != null){
			repaint();
			try{
				Thread.sleep(20);
			}
			catch(InterruptedException e){
			}
		}
	}

	public void stop(){

		thread = null;
	}

	public static void main(String[] args){

		Applet thisApplet = new TreeGraph();
		thisApplet.init();
		thisApplet.start();
		JFrame frame = new JFrame("Binary Tree Visualization");
		frame.setSize(thisApplet.getSize());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(thisApplet, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
