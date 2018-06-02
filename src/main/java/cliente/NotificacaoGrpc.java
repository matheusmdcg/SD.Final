/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import io.grpc.Status;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.Reply;
import io.grpc.examples.helloworld.Request;
import io.grpc.stub.StreamObserver;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificacaoGrpc extends Thread implements Runnable {

    private static final Logger logger = Logger.getLogger(ClienteGrpc.class.getName());
    private GreeterGrpc.GreeterStub asyncStub;

    public NotificacaoGrpc(GreeterGrpc.GreeterStub asyncStub) {
        this.asyncStub = asyncStub;
    }

    @Override
    public void run() {
        Request request = Request.newBuilder().setTudo("w").build();

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
