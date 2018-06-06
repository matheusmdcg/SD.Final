package cliente;

import io.grpc.examples.helloworld.GreeterGrpc;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




public class PrincipalClienteGrpc {
    public static void main(String [] args) throws SocketException, UnknownHostException, IOException, InterruptedException{
      
        Properties prop = Arquivo.getProp();

        int portagrpc = Integer.parseInt(prop.getProperty("prop.server.portagrpc"));
        String ipgrpc = prop.getProperty("prop.server.ipgrpc");
        
        ClienteGrpc client = new ClienteGrpc(ipgrpc, portagrpc);
        GreeterGrpc.GreeterStub asyncStub;
        asyncStub = client.getAsync();
        
        
        DatagramSocket socket = new DatagramSocket();
        InetAddress ip = socket.getLocalAddress();
        Integer porta = socket.getLocalPort();
        
        
        ExecutorService pool = Executors.newCachedThreadPool();
        NotificacaoGrpc threadNotificar = new NotificacaoGrpc(asyncStub, ip.toString() + porta.toString());
        ComandosGrpc threadComandos = new ComandosGrpc(client, ip.toString() + porta.toString());
        
        
        pool.execute(threadNotificar);
        pool.execute(threadComandos);
        pool.shutdown();
          
          
        
        
    }
    
}
