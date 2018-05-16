package cliente;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Scanner;




public class PrincipalClienteGrpc {
    public static void main(String [] args) throws SocketException, UnknownHostException, IOException, InterruptedException{
      
        Properties prop = Arquivo.getProp();

        int portagrpc = Integer.parseInt(prop.getProperty("prop.server.portagrpc"));
        String ipgrpc = prop.getProperty("prop.server.ipgrpc");
        
        ClienteGrpc client = new ClienteGrpc(ipgrpc, portagrpc);
        Scanner sc = new Scanner(System.in);
          try {
              
            while(true){
                String msg = sc.nextLine();
                System.out.println("oq foi escrito: "+msg);
                client.greet(msg);
            }
          } finally {
            client.shutdown();
          }
        
        
    }
    
}
