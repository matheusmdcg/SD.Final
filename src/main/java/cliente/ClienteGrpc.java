package cliente;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.helloworld.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple client that requests a greeting from the {@link HelloWorldServer}.
 */
public class ClienteGrpc {
  private static final Logger logger = Logger.getLogger(ClienteGrpc.class.getName());

  private final ManagedChannel channel;
  private final GreeterGrpc.GreeterBlockingStub blockingStub;

  /** Construct client connecting to HelloWorld server at {@code host:port}. */
  public ClienteGrpc(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
        // needing certificates.
        .usePlaintext()
        .build());
  }

  /** Construct client for accessing RouteGuide server using the existing channel. */
  ClienteGrpc(ManagedChannel channel) {
    this.channel = channel;
    blockingStub = GreeterGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

    public void greet(String msg) {
        String[] partes = msg.split(" ");
            if(!partes[0].equals("5")){        
                Request request = Request.newBuilder().setTudo(msg).build();
                Reply response;
                response = blockingStub.say(request);
                logger.info("Resposta: " + response.getResp());
            }
            else{
                Request request = Request.newBuilder().setTudo(msg).build();
                Reply response;
                response = blockingStub.say(request);
                logger.info("Mudança: " + response.getResp());
            }
    }
}




