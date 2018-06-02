package io.grpc.examples.helloworld;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * The greeting service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.11.0)",
    comments = "Source: helloworld.proto")
public final class GreeterGrpc {

  private GreeterGrpc() {}

  public static final String SERVICE_NAME = "helloworld.Greeter";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getSayMethod()} instead. 
  public static final io.grpc.MethodDescriptor<io.grpc.examples.helloworld.Request,
      io.grpc.examples.helloworld.Reply> METHOD_SAY = getSayMethodHelper();

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.helloworld.Request,
      io.grpc.examples.helloworld.Reply> getSayMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<io.grpc.examples.helloworld.Request,
      io.grpc.examples.helloworld.Reply> getSayMethod() {
    return getSayMethodHelper();
  }

  private static io.grpc.MethodDescriptor<io.grpc.examples.helloworld.Request,
      io.grpc.examples.helloworld.Reply> getSayMethodHelper() {
    io.grpc.MethodDescriptor<io.grpc.examples.helloworld.Request, io.grpc.examples.helloworld.Reply> getSayMethod;
    if ((getSayMethod = GreeterGrpc.getSayMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getSayMethod = GreeterGrpc.getSayMethod) == null) {
          GreeterGrpc.getSayMethod = getSayMethod = 
              io.grpc.MethodDescriptor.<io.grpc.examples.helloworld.Request, io.grpc.examples.helloworld.Reply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "say"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.helloworld.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.helloworld.Reply.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("say"))
                  .build();
          }
        }
     }
     return getSayMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getCreateMethod()} instead. 
  public static final io.grpc.MethodDescriptor<io.grpc.examples.helloworld.CreateRequest,
      io.grpc.examples.helloworld.CreateReply> METHOD_CREATE = getCreateMethodHelper();

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.helloworld.CreateRequest,
      io.grpc.examples.helloworld.CreateReply> getCreateMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<io.grpc.examples.helloworld.CreateRequest,
      io.grpc.examples.helloworld.CreateReply> getCreateMethod() {
    return getCreateMethodHelper();
  }

  private static io.grpc.MethodDescriptor<io.grpc.examples.helloworld.CreateRequest,
      io.grpc.examples.helloworld.CreateReply> getCreateMethodHelper() {
    io.grpc.MethodDescriptor<io.grpc.examples.helloworld.CreateRequest, io.grpc.examples.helloworld.CreateReply> getCreateMethod;
    if ((getCreateMethod = GreeterGrpc.getCreateMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getCreateMethod = GreeterGrpc.getCreateMethod) == null) {
          GreeterGrpc.getCreateMethod = getCreateMethod = 
              io.grpc.MethodDescriptor.<io.grpc.examples.helloworld.CreateRequest, io.grpc.examples.helloworld.CreateReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "create"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.helloworld.CreateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.helloworld.CreateReply.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("create"))
                  .build();
          }
        }
     }
     return getCreateMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getReadMethod()} instead. 
  public static final io.grpc.MethodDescriptor<io.grpc.examples.helloworld.ReadRequest,
      io.grpc.examples.helloworld.ReadReply> METHOD_READ = getReadMethodHelper();

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.helloworld.ReadRequest,
      io.grpc.examples.helloworld.ReadReply> getReadMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<io.grpc.examples.helloworld.ReadRequest,
      io.grpc.examples.helloworld.ReadReply> getReadMethod() {
    return getReadMethodHelper();
  }

  private static io.grpc.MethodDescriptor<io.grpc.examples.helloworld.ReadRequest,
      io.grpc.examples.helloworld.ReadReply> getReadMethodHelper() {
    io.grpc.MethodDescriptor<io.grpc.examples.helloworld.ReadRequest, io.grpc.examples.helloworld.ReadReply> getReadMethod;
    if ((getReadMethod = GreeterGrpc.getReadMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getReadMethod = GreeterGrpc.getReadMethod) == null) {
          GreeterGrpc.getReadMethod = getReadMethod = 
              io.grpc.MethodDescriptor.<io.grpc.examples.helloworld.ReadRequest, io.grpc.examples.helloworld.ReadReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "read"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.helloworld.ReadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.helloworld.ReadReply.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("read"))
                  .build();
          }
        }
     }
     return getReadMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getMonitorarMethod()} instead. 
  public static final io.grpc.MethodDescriptor<io.grpc.examples.helloworld.RequestM,
      io.grpc.examples.helloworld.Reply> METHOD_MONITORAR = getMonitorarMethodHelper();

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.helloworld.RequestM,
      io.grpc.examples.helloworld.Reply> getMonitorarMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<io.grpc.examples.helloworld.RequestM,
      io.grpc.examples.helloworld.Reply> getMonitorarMethod() {
    return getMonitorarMethodHelper();
  }

  private static io.grpc.MethodDescriptor<io.grpc.examples.helloworld.RequestM,
      io.grpc.examples.helloworld.Reply> getMonitorarMethodHelper() {
    io.grpc.MethodDescriptor<io.grpc.examples.helloworld.RequestM, io.grpc.examples.helloworld.Reply> getMonitorarMethod;
    if ((getMonitorarMethod = GreeterGrpc.getMonitorarMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getMonitorarMethod = GreeterGrpc.getMonitorarMethod) == null) {
          GreeterGrpc.getMonitorarMethod = getMonitorarMethod = 
              io.grpc.MethodDescriptor.<io.grpc.examples.helloworld.RequestM, io.grpc.examples.helloworld.Reply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "monitorar"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.helloworld.RequestM.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.helloworld.Reply.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("monitorar"))
                  .build();
          }
        }
     }
     return getMonitorarMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getNotificarMethod()} instead. 
  public static final io.grpc.MethodDescriptor<io.grpc.examples.helloworld.Request,
      io.grpc.examples.helloworld.Reply> METHOD_NOTIFICAR = getNotificarMethodHelper();

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.helloworld.Request,
      io.grpc.examples.helloworld.Reply> getNotificarMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<io.grpc.examples.helloworld.Request,
      io.grpc.examples.helloworld.Reply> getNotificarMethod() {
    return getNotificarMethodHelper();
  }

  private static io.grpc.MethodDescriptor<io.grpc.examples.helloworld.Request,
      io.grpc.examples.helloworld.Reply> getNotificarMethodHelper() {
    io.grpc.MethodDescriptor<io.grpc.examples.helloworld.Request, io.grpc.examples.helloworld.Reply> getNotificarMethod;
    if ((getNotificarMethod = GreeterGrpc.getNotificarMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getNotificarMethod = GreeterGrpc.getNotificarMethod) == null) {
          GreeterGrpc.getNotificarMethod = getNotificarMethod = 
              io.grpc.MethodDescriptor.<io.grpc.examples.helloworld.Request, io.grpc.examples.helloworld.Reply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "helloworld.Greeter", "notificar"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.helloworld.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.helloworld.Reply.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("notificar"))
                  .build();
          }
        }
     }
     return getNotificarMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GreeterStub newStub(io.grpc.Channel channel) {
    return new GreeterStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GreeterBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new GreeterBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GreeterFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new GreeterFutureStub(channel);
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static abstract class GreeterImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void say(io.grpc.examples.helloworld.Request request,
        io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.Reply> responseObserver) {
      asyncUnimplementedUnaryCall(getSayMethodHelper(), responseObserver);
    }

    /**
     */
    public void create(io.grpc.examples.helloworld.CreateRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.CreateReply> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateMethodHelper(), responseObserver);
    }

    /**
     */
    public void read(io.grpc.examples.helloworld.ReadRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.ReadReply> responseObserver) {
      asyncUnimplementedUnaryCall(getReadMethodHelper(), responseObserver);
    }

    /**
     */
    public void monitorar(io.grpc.examples.helloworld.RequestM request,
        io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.Reply> responseObserver) {
      asyncUnimplementedUnaryCall(getMonitorarMethodHelper(), responseObserver);
    }

    /**
     */
    public void notificar(io.grpc.examples.helloworld.Request request,
        io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.Reply> responseObserver) {
      asyncUnimplementedUnaryCall(getNotificarMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSayMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.helloworld.Request,
                io.grpc.examples.helloworld.Reply>(
                  this, METHODID_SAY)))
          .addMethod(
            getCreateMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.helloworld.CreateRequest,
                io.grpc.examples.helloworld.CreateReply>(
                  this, METHODID_CREATE)))
          .addMethod(
            getReadMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.helloworld.ReadRequest,
                io.grpc.examples.helloworld.ReadReply>(
                  this, METHODID_READ)))
          .addMethod(
            getMonitorarMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.helloworld.RequestM,
                io.grpc.examples.helloworld.Reply>(
                  this, METHODID_MONITORAR)))
          .addMethod(
            getNotificarMethodHelper(),
            asyncServerStreamingCall(
              new MethodHandlers<
                io.grpc.examples.helloworld.Request,
                io.grpc.examples.helloworld.Reply>(
                  this, METHODID_NOTIFICAR)))
          .build();
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class GreeterStub extends io.grpc.stub.AbstractStub<GreeterStub> {
    private GreeterStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GreeterStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GreeterStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GreeterStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void say(io.grpc.examples.helloworld.Request request,
        io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.Reply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSayMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void create(io.grpc.examples.helloworld.CreateRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.CreateReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void read(io.grpc.examples.helloworld.ReadRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.ReadReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getReadMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void monitorar(io.grpc.examples.helloworld.RequestM request,
        io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.Reply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getMonitorarMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void notificar(io.grpc.examples.helloworld.Request request,
        io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.Reply> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getNotificarMethodHelper(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class GreeterBlockingStub extends io.grpc.stub.AbstractStub<GreeterBlockingStub> {
    private GreeterBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GreeterBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GreeterBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GreeterBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public io.grpc.examples.helloworld.Reply say(io.grpc.examples.helloworld.Request request) {
      return blockingUnaryCall(
          getChannel(), getSayMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.helloworld.CreateReply create(io.grpc.examples.helloworld.CreateRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreateMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.helloworld.ReadReply read(io.grpc.examples.helloworld.ReadRequest request) {
      return blockingUnaryCall(
          getChannel(), getReadMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.helloworld.Reply monitorar(io.grpc.examples.helloworld.RequestM request) {
      return blockingUnaryCall(
          getChannel(), getMonitorarMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<io.grpc.examples.helloworld.Reply> notificar(
        io.grpc.examples.helloworld.Request request) {
      return blockingServerStreamingCall(
          getChannel(), getNotificarMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class GreeterFutureStub extends io.grpc.stub.AbstractStub<GreeterFutureStub> {
    private GreeterFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GreeterFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GreeterFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GreeterFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.helloworld.Reply> say(
        io.grpc.examples.helloworld.Request request) {
      return futureUnaryCall(
          getChannel().newCall(getSayMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.helloworld.CreateReply> create(
        io.grpc.examples.helloworld.CreateRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.helloworld.ReadReply> read(
        io.grpc.examples.helloworld.ReadRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getReadMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.helloworld.Reply> monitorar(
        io.grpc.examples.helloworld.RequestM request) {
      return futureUnaryCall(
          getChannel().newCall(getMonitorarMethodHelper(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAY = 0;
  private static final int METHODID_CREATE = 1;
  private static final int METHODID_READ = 2;
  private static final int METHODID_MONITORAR = 3;
  private static final int METHODID_NOTIFICAR = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GreeterImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GreeterImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAY:
          serviceImpl.say((io.grpc.examples.helloworld.Request) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.Reply>) responseObserver);
          break;
        case METHODID_CREATE:
          serviceImpl.create((io.grpc.examples.helloworld.CreateRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.CreateReply>) responseObserver);
          break;
        case METHODID_READ:
          serviceImpl.read((io.grpc.examples.helloworld.ReadRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.ReadReply>) responseObserver);
          break;
        case METHODID_MONITORAR:
          serviceImpl.monitorar((io.grpc.examples.helloworld.RequestM) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.Reply>) responseObserver);
          break;
        case METHODID_NOTIFICAR:
          serviceImpl.notificar((io.grpc.examples.helloworld.Request) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.helloworld.Reply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class GreeterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GreeterBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.grpc.examples.helloworld.HelloWorldProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Greeter");
    }
  }

  private static final class GreeterFileDescriptorSupplier
      extends GreeterBaseDescriptorSupplier {
    GreeterFileDescriptorSupplier() {}
  }

  private static final class GreeterMethodDescriptorSupplier
      extends GreeterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    GreeterMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GreeterGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GreeterFileDescriptorSupplier())
              .addMethod(getSayMethodHelper())
              .addMethod(getCreateMethodHelper())
              .addMethod(getReadMethodHelper())
              .addMethod(getMonitorarMethodHelper())
              .addMethod(getNotificarMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
