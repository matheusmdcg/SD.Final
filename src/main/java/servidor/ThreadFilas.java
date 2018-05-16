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
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadFilas extends Thread implements Runnable{
    
        BlockingQueue<String> fila01;
        BlockingQueue<String> fila02;
        BlockingQueue<String> fila03;

    public ThreadFilas(BlockingQueue um, BlockingQueue dois, BlockingQueue tres) throws SocketException, FileNotFoundException{
            fila01 = um;
            fila02 = dois;
            fila03 = tres;
    }
    

    @Override
    public void run() {
        while(true){
            String str;
            try {
                str = fila01.take();
                fila02.add(str);
                fila03.add(str);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadFilas.class.getName()).log(Level.SEVERE, null, ex);
            }

        }        
    }
      
}
