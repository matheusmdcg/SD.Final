package cliente;


import java.util.Scanner;
import io.grpc.examples.helloworld.GreeterGrpc;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComandosGrpc extends Thread implements Runnable{
    private ClienteGrpc client;
    public ComandosGrpc(ClienteGrpc c){
        client = c;
    }
    
    public void run(){
        Scanner sc = new Scanner(System.in);
          try {
            String msg;            
            while(true){
                msg = sc.nextLine();                
                client.greet(msg);
            }
          } finally {
            try {
                client.shutdown();
            } catch (InterruptedException ex) {
                Logger.getLogger(ComandosGrpc.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
    }
}
