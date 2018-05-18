package cliente;

import java.io.IOException;
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
                client.greet(msg);
            }
          } finally {
            client.shutdown();
          }
        
        
    }
    
}
