package servidor;

import cliente.*;
import com.sun.xml.internal.ws.api.pipe.Fiber;
import io.grpc.ServerBuilder;
import io.grpc.examples.helloworld.Reply;
import io.grpc.stub.StreamObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
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
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



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
       
        Map<String, StreamObserver<Reply>> monitorargrpc = new HashMap<String, StreamObserver<Reply>>();
        Map<BigInteger, ArrayList<String>> monitorarChaveId = new HashMap<BigInteger, ArrayList<String>>();  
        Map<BigInteger, ArrayList<String>> monitorarResp = new HashMap<BigInteger, ArrayList<String>>();
        Map<BigInteger, ArrayList<String>> mapa = new HashMap<BigInteger, ArrayList<String>>();
        Map<BigInteger, ArrayList<String>> monitorar = new HashMap<BigInteger, ArrayList<String>>();
        
        
//        BufferedReader leitura = new BufferedReader(new FileReader("log.txt"));
//        BufferedReader leitura2 = new BufferedReader(new FileReader("snap.txt"));
//        FileOutputStream escrita = new FileOutputStream("log.txt", true);
        
//        File diretorioSnap = new File("/snaps");
//        File[] snaps = diretorioSnap.listFiles();
//        
//        File diretorioLog = new File("/logs");
//        File[] logs = diretorioLog.listFiles();
        
        String snaps =  "snaps";
        String logs =  "logs";
        
        ExecutorService pool = Executors.newCachedThreadPool();
        //ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        
        BlockingQueue<Integer> novo = new LinkedBlockingDeque<Integer>();
        novo.add(0);
        
        ProcessaLog reescrever = new ProcessaLog(mapa, snaps, logs);          
        ThreadLog s1 = new ThreadLog(fila02, logs, novo);
        ThreadFilas s2 = new ThreadFilas(fila01, fila02, fila03);
        ThreadCrud s3 = new ThreadCrud(fila03, mapa,  socket, filaResposta, monitorargrpc, monitorarChaveId, monitorarResp, monitorar);
        ThreadReceber s4 = new ThreadReceber(socket,fila01);
        ThreadSnap s5 = new ThreadSnap(mapa, snaps, logs, novo);
       
        reescrever.run();
        pool.execute(s1);
        pool.execute(s2);
        pool.execute(s3);
        pool.execute(s4);
        pool.execute(s5);
        //executor.schedule(s5, 5, TimeUnit.SECONDS);
      
        
        ServerBuilder<? extends ServerBuilder<?>> serverc = ServerBuilder.forPort(portagrpc);
        ServidorGrpc grpc = new ServidorGrpc(fila01, serverc, filaResposta, monitorargrpc, monitorarChaveId);
        grpc.start();
        grpc.blockUntilShutdown();
        
        pool.shutdown();
        //executor.shutdown();

    }
}
