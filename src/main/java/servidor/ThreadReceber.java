package servidor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadReceber extends Thread implements Runnable{
    
    private DatagramSocket socket;
    private byte[] buf = new byte[1400];
    private BlockingQueue<String> fila01;

    public ThreadReceber(DatagramSocket so, BlockingQueue um) throws SocketException, FileNotFoundException{
        this.socket  = so;
        fila01 = um;
    }
    
    @Override
    public void run() {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        try {
                while (true) {
                    
                    socket.receive(packet);
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    
                    String received = new String(packet.getData(), 0, packet.getLength());
                    received = received+" "+address+" "+port;
                    fila01.add(received);
      
                }            
        } catch (IOException ex) {
            Logger.getLogger(ThreadReceber.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
