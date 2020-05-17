
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
class Client
{
    Socket s ;
    DataInputStream dis ;
    PrintStream ps ;
    String wordText ;
    public Client() throws IOException
    {
        
        s = new Socket("127.0.0.1" , 5000) ;
        dis = new DataInputStream(s.getInputStream()) ;
        ps = new PrintStream(s.getOutputStream()) ;
        // = dis.readLine() ;
       class SampleUI extends JFrame 
        {
        public SampleUI(){
        this.setLayout(new FlowLayout());
        JTextArea ta = new JTextArea(20,30);
        JScrollPane scroll = new JScrollPane(ta);
        scroll.setViewportView(ta);
        JTextField tf = new JTextField(30);
        JButton okButton = new JButton("Send");
        okButton.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae){
            
                wordText = tf.getText() ;
                ps.println(wordText);
                tf.setText("");
                System.out.print(wordText);

        }
        });
        
        new Thread(new Runnable()
        { 
            @Override
            public void run() {
                try
                {
                    while(true)
                    {
                         wordText = dis.readLine();
                         if(wordText !="")
                         {
                             ta.append(wordText+"\n") ;
                         }
                        
                    }
           
                } 
                catch(IOException ex)
                {
                    
                }
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        ).start();
        
        add(scroll);
        add(tf);
        add(okButton);
        }
         
          }
       
          SampleUI ui=new SampleUI();
          ui.setSize(400, 500);
          ui.setVisible(true);
          

}
}
public class ClientApp {
    public static void main(String[] args) throws IOException {
        Client c = new Client() ;
    }
}
