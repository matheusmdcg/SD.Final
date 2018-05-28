package servidor;

import cliente.*;
import io.grpc.ServerBuilder;
import io.grpc.examples.helloworld.Reply;
import io.grpc.stub.StreamObserver;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.io.grpc.examples.helloworld.*;



public class ServidorPrincipal{
    
    public static void main(String [] args) throws SocketException, UnknownHostException, IOException, InterruptedException{
        
        Properties prop = Arquivo.getProp();
        int porta = Integer.parseInt(prop.getProperty("prop.server.porta"));
        int portagrpc = Integer.parseInt(prop.getProperty("prop.server.portagrpc"));
        
        DatagramSocket socket = new DatagramSocket(porta);
        
        BlockingQueue<String> fila01 = new LinkedBlockingDeque<String>();
        BlockingQueue<String> fila02 = new LinkedBlockingDeque<String>();
        BlockingQueue<String> fila03 = new LinkedBlockingDeque<String>();
        BlockingQueue<String> filaResposta = new LinkedBlockingDeque<String>();
       
        Map<BigInteger, ArrayList<String>> monitorar = new HashMap<BigInteger, ArrayList<String>>();
        Map<BigInteger, ArrayList<StreamObserver<Reply>>> monitorargrpc = new HashMap<BigInteger, ArrayList<StreamObserver<Reply>>>();
        Map<BigInteger, String> mapa = new HashMap<BigInteger, String>();
        
        
        
        BufferedReader leitura = new BufferedReader(new FileReader("log.txt"));
        FileOutputStream escrita = new FileOutputStream("log.txt", true);
        
        ExecutorService pool = Executors.newCachedThreadPool();
        
        ProcessaLog reescrever = new ProcessaLog(leitura, mapa);          
        ThreadLog s1 = new ThreadLog(fila02, escrita);
        ThreadFilas s2 = new ThreadFilas(fila01, fila02, fila03);
        ThreadCrud s3 = new ThreadCrud(fila03, mapa, socket,filaResposta, monitorar, monitorargrpc);
        ThreadReceber s4 = new ThreadReceber(socket,fila01);
       
        reescrever.run();
        pool.execute(s1);
        pool.execute(s2);
        pool.execute(s3);
        pool.execute(s4);
        
        ServerBuilder<? extends ServerBuilder<?>> serverc = ServerBuilder.forPort(portagrpc);
        ServidorGrpc grpc = new ServidorGrpc(fila01, serverc, filaResposta, monitorargrpc);
        grpc.start();
        grpc.blockUntilShutdown();
        
        pool.shutdown();

    }
}
