import java.io.*;
import java.net.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Client extends JFrame {
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	//Declare Components
	private JLabel heading = new JLabel ("Client");
	private JTextArea messageArea = new JTextArea();
	private JTextField messageInput = new JTextField();
	private Font font = new Font("Roboto",Font.PLAIN,20);
	
	
	public Client() {
		try {
			 System.out.println("Sending request to server");
			 socket = new Socket("127.0.0.1",1);
			 System.out.println("connection done");
			 br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
	         out = new PrintWriter(socket.getOutputStream());
			createGUI();
			handleEvents();
	         startReading();
	         //startWriting();
		  }
		     catch(Exception e) {
			 e.printStackTrace();
	  }
 }
	       private void createGUI() {
		    this.setTitle("Client Messanger");
		    this.setSize(600,700);
		    this.setLocationRelativeTo(null); //Keep the window on center
		    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    //coding for components
		    heading.setFont(font);
		    messageArea.setFont(font);
		    messageInput.setFont(font);
		    heading.setIcon(new ImageIcon("onlinelogomaker-092222-0322-3757.png"));
		    heading.setHorizontalTextPosition(SwingConstants.CENTER);
		    heading.setVerticalTextPosition(SwingConstants.BOTTOM);
		    heading.setHorizontalAlignment(SwingConstants.CENTER);
		    heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		    messageArea.setEditable(false);
		    messageInput.setHorizontalAlignment(SwingConstants.CENTER);
		    //setting the layout of the frame 
		    this.setLayout(new BorderLayout());
		    
		    //adding the components to frame
		    this.add(heading,BorderLayout.NORTH);
		    JScrollPane jScrollPane= new JScrollPane(messageArea);
		    this.add(jScrollPane,BorderLayout.CENTER);
		    this.add(messageInput,BorderLayout.SOUTH);
		   
		    this.setVisible(true);
	       }
	private void handleEvents() {
		messageInput.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("Key released "+e.getKeyCode());
				if(e.getKeyCode()==10) {  // key code of enter is 10
			    //System.out.println("you have pressed enter button");
			    String contentToSend= messageInput.getText();
			    messageArea.append("Me : "+contentToSend+"\n");
			    out.println(contentToSend);
			    out.flush();
			    messageInput.setText("");
			    messageInput.requestFocus();
				}
				
			}
			
		});
	}
	       
	       
            public void  startReading(){  // this thread will read the data
		    Runnable r1 = ()->{
			System.out.println("reader started..");
			try {
			while(true) {
			String msg = br.readLine();
			if(msg.equals("exit")){
			System.out.println("Server terminated the chat");
			JOptionPane.showMessageDialog(this,"Server terminated the chat");
			messageInput.setEnabled(false);
			socket.close();
			break;
			  }
			//System.out.println("Server: " + msg);
			messageArea.append("You: " + msg+"\n");
			   }
			} catch(Exception e) {
			  System.out.println("Connection is closed");
		}
	};
		    new Thread(r1).start();
}
	        public void startWriting() {  // this thread will send the data to client
            Runnable r2 = ()->{
        	System.out.println("writer started..");
        	try {
        	while(!socket.isClosed()) {	
 			BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
 			String content = br1.readLine();
 			out.println(content);
 			out.flush();
 			if(content.equals("exit")){
 			socket.close();
 			break;
 			   }
 			}  
        }   catch(Exception e) {
        	System.out.println("Connection is closed");
        	   }
		};
		   new Thread(r2).start();
	}
	      public static void main(String[] args) {
		  System.out.println("This is client..");
          new Client();
	}

}
