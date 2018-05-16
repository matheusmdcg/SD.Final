package servidor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadLog extends Thread implements Runnable{
    
    private DatagramSocket socket;
    private byte[] buf = new byte[1400];

    BlockingQueue<String> fila;
            
    private Map<BigInteger, String> mapa = new HashMap<>();
    
    FileOutputStream arquivo;

    
    private int contlog;

    
    public ThreadLog(BlockingQueue dois,  FileOutputStream arq) throws SocketException, FileNotFoundException{
        contlog = 0;
        fila = dois;
        this.arquivo = arq;
    }
    
    
    @Override
    public void run() {
        
            try {
                while(true){
                    String str = fila.take();
                    String[] partes = str.split(" ");
                    
                    boolean grpc = false;
                    
                    if(partes.length<4)
                        grpc = true;
                    else
                        grpc = false;              
                    
                    if(partes[0].equals("2"))
                        continue;
                    else
                        str = partes[0]+" "+partes[1];
                    if(grpc == false){
                        if(partes.length>4)
                            str = str+" "+partes[2]+"\n";
                        else
                            str = str+"\n";
                    }
                    if(grpc == true){
                        if(partes.length>2)
                            str = str+" "+partes[2]+"\n";
                        else
                            str = str+"\n";
                    }
                    
                    arquivo.write(str.getBytes());
                }               
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(ThreadLog.class.getName()).log(Level.SEVERE, null, ex);
            }

    
    }
    

    
    
    
    
}
