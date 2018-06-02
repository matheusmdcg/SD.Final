package cliente;

import io.grpc.examples.helloworld.GreeterGrpc;
import java.io.IOException;
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
        
        ExecutorService pool = Executors.newCachedThreadPool();
        NotificacaoGrpc threadNotificar = new NotificacaoGrpc(asyncStub);
        ComandosGrpc threadComandos = new ComandosGrpc(client);
        
        
        pool.execute(threadNotificar);
        pool.execute(threadComandos);
        pool.shutdown();
          
          
        
        
    }
    
}
