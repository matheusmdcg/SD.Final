package servidor;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.examples.helloworld.*;
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

    public ServidorGrpc(BlockingQueue um, ServerBuilder g, BlockingQueue resposta, Map<BigInteger, ArrayList<StreamObserver<Reply>>> monitorar) throws IOException {

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
        private Map<BigInteger, ArrayList<StreamObserver<Reply>>> monitorargrpc;

        public GreeterImpl(BlockingQueue um, BlockingQueue resposta, Map<BigInteger, ArrayList<StreamObserver<Reply>>> monitorar) {
            filatemp = um;
            filaResposta = resposta;
            monitorargrpc = monitorar;
        }

        @Override
        public void say(Request req, StreamObserver<Reply> responseObserver) {
            try {
                filatemp.add(req.getTudo());
                String resposta;
                resposta = filaResposta.take();

                Reply reply = Reply.newBuilder().setResp(resposta).build();
                responseObserver.onNext(reply);
                responseObserver.onCompleted();

            } catch (InterruptedException ex) {
                Logger.getLogger(ServidorGrpc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void monitorar(RequestM req, StreamObserver<Reply> responseObserver) {

            ArrayList<StreamObserver<Reply>> temp;
            BigInteger chave = new BigInteger(req.getChave());
            temp = monitorargrpc.get(chave);
            temp.add(responseObserver);

            monitorargrpc.put(chave, temp);
            //filatemp.add(req.getOperacao()+" "+req.getChave());  

            Reply reply = Reply.newBuilder().setResp("Chave sendo monitorada").build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();

        }

        @Override
        public void notificar(Request req, StreamObserver<Reply> responseObserver) {
            Reply reply = Reply.newBuilder().setResp("resposta").build();
            responseObserver.onNext(reply);
            //responseObserver.onCompleted();
        }
        
        
//        @Override     
//        public void create(CreateRequest req, StreamObserver<CreateReply> responseObserver) {
////            int operacao = Integer.parseInt(req.getOperacao());
////            BigInteger chave = new BigInteger(req.getChave());
////            String valor = req.getValor();
//                try {
//                    filatemp.add(req.getOperacao()+" "+req.getChave()+" "+req.getValor());
//                    String resposta;
//                    resposta = filaResposta.take();      
//                        
//                    CreateReply reply = CreateReply.newBuilder().setOk(resposta).build();
//                    responseObserver.onNext(reply);
//                    responseObserver.onCompleted();      
//                    
//                    } catch (InterruptedException ex) {
//                    Logger.getLogger(ServidorGrpc.class.getName()).log(Level.SEVERE, null, ex);
//                }
//        }
//        
//        @Override
//        public void read(ReadRequest req, StreamObserver<ReadReply> responseObserver) {
////            int operacao = Integer.parseInt(req.getOperacao());
////            BigInteger chave = new BigInteger(req.getChave());
////            String valor = req.getValor();
//                try {
//                    filatemp.add(req.getOperacao()+" "+req.getChave());
//                    String resposta;
//                    resposta = filaResposta.take();      
//                        
//                    ReadReply reply = ReadReply.newBuilder().setValor(resposta).build();
//                    responseObserver.onNext(reply);
//                    responseObserver.onCompleted();      
//                    
//                    } catch (InterruptedException ex) {
//                    Logger.getLogger(ServidorGrpc.class.getName()).log(Level.SEVERE, null, ex);
//                }
//        }          
    }

}
