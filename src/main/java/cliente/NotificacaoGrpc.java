package cliente;

import io.grpc.Status;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.Reply;
import io.grpc.examples.helloworld.Request;
import io.grpc.stub.StreamObserver;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificacaoGrpc extends Thread implements Runnable {

    private static final Logger logger = Logger.getLogger(ClienteGrpc.class.getName());
    private GreeterGrpc.GreeterStub asyncStub;
    private String individual;

    public NotificacaoGrpc(GreeterGrpc.GreeterStub asyncStub, String i) {
        this.asyncStub = asyncStub;
        this.individual = i;
    }

    @Override
    public void run() {
        Request request = Request.newBuilder().setTudo(this.individual).build();
        asyncStub.notificar(request, new StreamObserver<Reply>() {
            @Override
            public void onNext(Reply note) {
                System.out.println(note.getResp());
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                logger.log(Level.WARNING, "RouteChat Failed: {0}", status);
            }

            @Override
            public void onCompleted() {
                logger.info("Mudança finalizadas \n");
            }
        });
    }
}
