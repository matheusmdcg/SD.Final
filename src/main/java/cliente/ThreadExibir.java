
package cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.ThreadReceber;

public class ThreadExibir extends Thread implements Runnable{
    private DatagramSocket socket;
    private byte[] buf = new byte[1400];
    
    
    public ThreadExibir(DatagramSocket so){
        socket = so;
    }
    
    
    @Override
    public void run(){
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try {
                while (true) {
                    
                    socket.receive(packet);
                    
                    String received = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(received);
      
                }            
        } catch (IOException ex) {
            Logger.getLogger(ThreadReceber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
