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




public class ClientePrincipal {
    public static void main(String [] args) throws SocketException, UnknownHostException, IOException, InterruptedException{
        ExecutorService pool = Executors.newCachedThreadPool();
        DatagramSocket socket = new DatagramSocket();        
        Properties prop = Arquivo.getProp();
        
        int porta = Integer.parseInt(prop.getProperty("prop.server.porta"));
        InetAddress ip = InetAddress.getByName(prop.getProperty("prop.server.ip"));
        
        
        ThreadTeclado teclado = new ThreadTeclado(socket, porta, ip);
        ThreadExibir exibe = new ThreadExibir(socket);
        
        pool.execute(teclado);
        pool.execute(exibe);
        pool.shutdown();
        
    }
    
}
