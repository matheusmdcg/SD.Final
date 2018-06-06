package servidor;

import io.grpc.examples.helloworld.Reply;
import io.grpc.stub.StreamObserver;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.internal.objects.NativeArray.map;

public class ThreadCrud extends Thread implements Runnable {

    private DatagramSocket socket;
    private byte[] buf = new byte[1400];

    private BlockingQueue<String> fila;

    private Map<BigInteger, String> mapa;

    private BufferedReader arquivo;
    private String enviar;

    private int porta;
    private InetAddress address;
    private String iporta;

    private DatagramPacket packet = new DatagramPacket(buf, buf.length);

    private BlockingQueue<String> filaResposta;
    private BlockingQueue<String> filaM;

    private Map<BigInteger, ArrayList<String>> monitorar;//Chave para Resposta

    private Map<String, StreamObserver<Reply>> monitorargrpc;//Identificador para Observer
    private Map<BigInteger, ArrayList<String>> monitorarChaveId;//Chave para Lista de Identificadores
    private Map<BigInteger, ArrayList<String>> monitorarChaveResp;//Chave para Resposta

    public ThreadCrud(BlockingQueue tres, Map mapas, DatagramSocket so, BlockingQueue resposta,
            Map<String, StreamObserver<Reply>> monitorar,
            Map<BigInteger, ArrayList<String>> monitorar2,
            Map<BigInteger, ArrayList<String>> monitorar3,
            Map<BigInteger, ArrayList<String>> m) throws SocketException, FileNotFoundException, IOException {

        fila = tres;
        mapa = mapas;
        socket = so;
        filaResposta = resposta;
        monitorargrpc = monitorar;
        monitorarChaveId = monitorar2;
        monitorarChaveResp = monitorar3;
        this.monitorar = m;
    }

    public void lerFila() throws InterruptedException, IOException {
        String str;
        String[] partes;
        int operacao = 0;

        String valor = null;
        boolean grpc = false;
        String retorno;
        int i = 0;

        str = fila.take();
        partes = str.split(" ");
        operacao = Integer.parseInt(partes[i]);
        i += 1;
        String h = partes[i];
        BigInteger chave = new BigInteger(h);

        if (partes.length < 4) {
            grpc = true;
        } else {
            grpc = false;
        }

        if (partes.length > 4 && grpc == false) {
            valor = partes[++i];
        }
        if (partes.length > 2 && grpc == true) {
            valor = partes[++i];
        }

        if (!grpc) {
            InetAddress address = InetAddress.getByName(partes[++i].substring(1));
            int porta = Integer.parseInt(partes[++i]);
            iporta = address.toString() + " " + Integer.toString(porta);
            String n = this.processar(operacao, chave, valor, grpc);
            this.enviar(n, address, porta);
        } else {
            String n = this.processar(operacao, chave, valor, grpc);
            filaResposta.add(n);
        }

        i = 0;
    }

    public String processar(int operacao, BigInteger chave, String valor, boolean grpc) throws IOException, InterruptedException {
        String retorno;
        switch (operacao) {
            case 1:
                retorno = this.criar(chave, valor, grpc, operacao);
                break;
            case 2:
                retorno = this.ler(chave, valor);
                break;
            case 3:
                retorno = this.modificar(chave, valor, grpc);
                break;
            case 4:
                retorno = this.deletar(chave, valor, grpc);
                break;
            case 5:
                retorno = this.monitorar(chave, grpc);
                break;
            default:
                retorno = "Sem valor";
        }
        return retorno;
    }

    public String monitorar(BigInteger chave, boolean grpc) {

        if (grpc == false) {
            ArrayList<String> temp = new ArrayList<String>();
            if (monitorar.containsKey(chave)) {
                temp = monitorar.get(chave);
            }

            temp.add(iporta);
            monitorar.put(chave, temp);
            return "Chave sendo monitorada\n";
        } else {
            return "Chave já sendo monitorada\n";
        }
    }

    public void enviar(String n, InetAddress address, int porta) throws IOException {
        buf = n.getBytes();
        packet = new DatagramPacket(buf, buf.length, address, porta);
        socket.send(packet);
    }

    public void verificar(BigInteger chave, boolean grpc, String retorno) throws IOException {
        String[] partes;
        for (String temp : monitorar.get(chave)) {
            partes = temp.split(" ");
            this.enviar(retorno, InetAddress.getByName(partes[0].substring(1)), Integer.parseInt(partes[1]));
        }
    }

    public String criar(BigInteger chave, String valor, boolean grpc, int operacao) throws IOException, InterruptedException {

        if (valor == null) {
            return "Nao foi possivel criar poque o valor nao foi especificado";
        } else {
            if (mapa.put(chave, valor) != null) {
                return "Não Criado";
            } else {
                if (monitorarChaveId.containsKey(chave)) {
                    for (String id : monitorarChaveId.get(chave)) {
                        StreamObserver<Reply> responseObserver = monitorargrpc.get(id);
                        Reply reply = Reply.newBuilder().setResp("Chave " + chave + " criada com o valor " + valor + "\n").build();
                        responseObserver.onNext(reply);
                    }
                }
                if (monitorar.containsKey(chave)) {
                    this.verificar(chave, grpc, "Chave " + chave + " criada com o valor " + valor + "\n");
                }
                return "Criado";
            }
        }
    }

    public String ler(BigInteger chave, String valor) {
        if (mapa.containsKey(chave)) {
            return mapa.get(chave);
        } else {
            return "Nao existe valor associado a essa chave";
        }
    }

    public String modificar(BigInteger chave, String valor, boolean grpc) throws IOException {
        if (mapa.containsKey(chave)) {
            String antigo = mapa.replace(chave, valor);
            if (monitorarChaveId.containsKey(chave)) {
                for (String id : monitorarChaveId.get(chave)) {
                    StreamObserver<Reply> responseObserver = monitorargrpc.get(id);
                    Reply reply = Reply.newBuilder().setResp("Chave " + chave + " modificada com o valor " + valor + "\n").build();
                    responseObserver.onNext(reply);
                }
            }

            if (monitorar.containsKey(chave)) {
                this.verificar(chave, grpc, "Chave " + chave + " modificada com o valor " + valor + "\n");
            }

            return "Valor modificado";
        } else {
            return "Valor nao modificado";
        }
    }

    public String deletar(BigInteger chave, String valor, boolean grpc) throws IOException {
        if (mapa.containsKey(chave)) {
            mapa.remove(chave);
            if (monitorarChaveId.containsKey(chave)) {
                for (String id : monitorarChaveId.get(chave)) {
                    StreamObserver<Reply> responseObserver = monitorargrpc.get(id);
                    Reply reply = Reply.newBuilder().setResp("Chave " + chave + " deletada com seu valor\n").build();
                    responseObserver.onNext(reply);
                }
            }
//            if(grpc == false){
            if (monitorar.containsKey(chave)) {
                this.verificar(chave, grpc, "Chave " + chave + " deletada com seu valor\n");
            }
//            }
//            ArrayList<StreamObserver<Reply>> tempo = this.temp(chave);
//            for (StreamObserver<Reply> temp : tempo) {
//                Reply reply = Reply.newBuilder().setResp("Chave " + chave + " deletada com seu valor\n").build();
//                temp.onNext(reply);
//            }

            return "objeto deletado";
        } else {
            return "objeto nao deletado";
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.lerFila();
            }
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(ThreadCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
