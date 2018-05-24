package servidor;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorGrpc {
    private static final Logger logger = Logger.getLogger(ServidorGrpc.class.getName());
    private Server server;
    
    private BlockingQueue<String> fila01;
    

    
    
    public ServidorGrpc(BlockingQueue um, ServerBuilder g, BlockingQueue resposta, Map<BigInteger, ArrayList<String>> monitorar) throws IOException{

        fila01 = um;
        server = g.addService(new GreeterImpl(fila01, resposta, monitorar))
        .build()
        .start();
    }
    
    
  void start() throws IOException {
    /* The port on which the server should run */
    
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        // Use stderr here since the logger may have been reset by its JVM shutdown hook.
        System.err.println("*** shutting down gRPC server since JVM is shutting down");
        ServidorGrpc.this.stop();
        System.err.println("*** server shut down");
      }
    });
  }
  
  private void stop() {
    if (server != null) {
      server.shutdown();
    }
  }
  
  void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }
  
  
    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        private BlockingQueue<String> filatemp;
        private BlockingQueue<String> filaResposta;
        private Map<BigInteger, ArrayList<String>> monitorargrpc;
    public GreeterImpl(BlockingQueue um, BlockingQueue resposta, Map<BigInteger, ArrayList<String>> monitorar){
        filatemp = um;
        filaResposta = resposta;
        monitorargrpc = monitorar;
    }
        
        
    @Override
    public void say(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        String[] partes = req.getName().split(" ");
       
            try {
                              
                if(!partes[0].equals("6")){
                    filatemp.add(req.getName());
                    String resposta;
                    resposta = filaResposta.take();                         
                    HelloReply reply = HelloReply.newBuilder().setMessage(resposta).build();
                    responseObserver.onNext(reply);
                    responseObserver.onCompleted();
                }
                else{
                    while(true){
                        BigInteger chave = new BigInteger(partes[1]);
                        if(monitorargrpc.containsKey(chave)){
                            for(String temp: monitorargrpc.get(chave)){
                                HelloReply reply = HelloReply.newBuilder().setMessage(temp).build();
                                responseObserver.onNext(reply);
                                responseObserver.onCompleted();                               
                            }
                            monitorargrpc.remove(chave);
                            break;
                        }                      
                    }
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(ServidorGrpc.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        
    }
    
    
    
  }
  
}
