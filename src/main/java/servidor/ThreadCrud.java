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
import java.net.UnknownHostException;
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
    private BlockingQueue<String> filaM;
    
    private Map<BigInteger, ArrayList<String>> monitorar;
    
    
    public ThreadCrud(BlockingQueue tres,  Map mapas, DatagramSocket so, BlockingQueue resposta, Map<BigInteger, ArrayList<String>> m) throws SocketException, FileNotFoundException, IOException{
        fila = tres;
        mapa = mapas;
        socket = so;
        filaResposta = resposta;
        monitorar= m;
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
            InetAddress address = InetAddress.getByName(partes[++i].substring(1));
            int porta = Integer.parseInt(partes[++i]);
            String n = this.processar(operacao, chave, valor, grpc);      
            this.enviar(n, address, porta);
        }
        else{
            String n = this.processar(operacao, chave, valor, grpc);
            filaResposta.add(n);
        }

        i=0;
    }
    
    public String processar(int operacao, BigInteger chave, String valor, boolean grpc) throws IOException, InterruptedException{
        String retorno;
        switch (operacao){
            case 1:
                retorno = this.criar(chave, valor, grpc, operacao);
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
            case 5:
                retorno = this.monitorar(chave, grpc);
                break;
            default:
                retorno = "Sem valor";
        }
        return retorno;
    }
    
    public String monitorar(BigInteger chave, boolean grpc){
        ArrayList<String> temp = null;
        if(monitorar.containsKey(chave)){
            temp = monitorar.get(chave);
        }
        else{
            temp = new ArrayList<String>();
        }
        if(grpc == false){
            
            String portaip = address.toString()+" "+Integer.toString(porta);
            
            temp.add(portaip);
            
            monitorar.put(chave, temp);
            return "Chave sendo monitorada\n";
            
        }
        else{
            return "Chave sendo monitorada\n";
        }
        
    }
    
    
    public void enviar(String n, InetAddress address, int porta) throws IOException{
        buf = n.getBytes();
        packet = new DatagramPacket(buf, buf.length, address, porta);
        socket.send(packet);
    }
    
    public void enviar2(String[] partes, String retorno) throws UnknownHostException, IOException{
        buf = retorno.getBytes();
        packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(partes[0].substring(1)), Integer.parseInt(partes[1]));
        socket.send(packet);
    }
    
    public void verificar(BigInteger chave, boolean grpc, String retorno) throws IOException{
        String[] partes;
        for(String temp: monitorar.get(chave)){
            if(grpc == false){
                 partes = temp.split(" ");
                 this.enviar2(partes, retorno);            
            }
            else{
                
            }
        }
    }
    
    
    public String criar(BigInteger chave, String valor, boolean grpc,int operacao) throws IOException, InterruptedException{
        
        if(valor == null)
            return "Nao foi possivel criar poque o valor nao foi especificado";
        else{
            if(mapa.put(chave, valor) != null)
                return "Não Criado";
            else{
                if(monitorar.containsKey(chave))
                    this.verificar(chave, grpc, "Chave "+chave+"criada com o valor "+valor+"\n");
                return "Criado";
            }
                    
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
