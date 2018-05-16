package servidor;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.internal.objects.NativeArray.map;

public class ThreadCrud extends Thread implements Runnable{
    
    private DatagramSocket socket;
    private byte[] buf = new byte[1400];

    private BlockingQueue<String> fila;
            
    private Map<BigInteger, String> mapa;
    
    private BufferedReader arquivo;
    private String enviar;
    
    private int porta;
    private InetAddress address;
    
    private DatagramPacket packet = new DatagramPacket(buf, buf.length);
    
    private BlockingQueue<String> filaResposta;
    
    
    public ThreadCrud(BlockingQueue tres,  Map mapas, DatagramSocket so, BlockingQueue resposta) throws SocketException, FileNotFoundException, IOException{
        fila = tres;
        mapa = mapas;
        socket = so;
        filaResposta = resposta;
    }

    
    public void lerFila() throws InterruptedException, IOException{
        String str;
        String[] partes;
        int operacao = 0;
        BigInteger chave = null;
        String valor = null;
        boolean grpc = false;
        String retorno;        
        int i =0;
        
        str = fila.take();
        partes = str.split(" ");
        operacao = Integer.parseInt(partes[i]);
        chave = new BigInteger(partes[++i]);
        
        if(partes.length<4)
            grpc = true;
        else
            grpc = false;
            
            
        if(partes.length>4 && grpc ==false)
            valor = partes[++i];
        if(partes.length>2 && grpc == true)
            valor = partes[++i];
        
        if(grpc == false){
            address = InetAddress.getByName(partes[++i].substring(1));
            porta = Integer.parseInt(partes[++i]);
            String n = this.processar(operacao, chave, valor);        
            this.enviar(n);
        }
        else{
            String n = this.processar(operacao, chave, valor);
            filaResposta.add(n);
        }

        i=0;
    }
    
    public String processar(int operacao, BigInteger chave, String valor) throws IOException, InterruptedException{
        String retorno;
        switch (operacao){
            case 1:
                retorno = this.criar(chave, valor);
                break;
            case 2:
                retorno = this.ler(chave, valor);
                break;
            case 3:
                retorno = this.modificar(chave, valor);
                break;
            case 4:
                retorno = this.deletar(chave, valor);
                break;
            default:
                retorno = "Sem valor";
        }
        return retorno;
    }
    
    public void enviar(String n) throws IOException{
        buf = n.getBytes();
        packet = new DatagramPacket(buf, buf.length, address, porta);
        socket.send(packet);
    }
    
    
    public String criar(BigInteger chave, String valor) throws IOException, InterruptedException{
        
        if(valor == null)
            return "Nao foi possivel criar poque o valor nao foi especificado";
        else{
            if(mapa.put(chave, valor) != null)
                return "Não Criado";
            else
                return "Criado";    
        }
    }
    
    public String ler(BigInteger chave, String valor){
        if(mapa.containsKey(chave))
            return mapa.get(chave);
        else
            return "Nao existe valor associado a essa chave";
    }
    
    public String modificar(BigInteger chave, String valor){
        if(mapa.containsKey(chave)){
            String antigo = mapa.replace(chave, valor);
            return "Valor modificado";
        }
        else
            return "Valor nao modificado";
    }
    
    public String deletar(BigInteger chave, String valor){
        if(mapa.containsKey(chave)){
            mapa.remove(chave);
            return "objeto deletado";
        }
        else
            return "objeto nao deletado";
    }
    
    
    @Override
    public void run() {
        try {
            while(true){
                this.lerFila();
            }
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(ThreadCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
}
