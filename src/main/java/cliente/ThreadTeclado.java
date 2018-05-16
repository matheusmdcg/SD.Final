package cliente;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ThreadTeclado extends Thread implements Runnable{
    private DatagramSocket socket;
    private InetAddress address;
    private byte[] buf = new byte[1400];
    private int portaservidor;
    private boolean running;
    
    BufferedReader leitura;
    
    public ThreadTeclado(DatagramSocket so,int porta, InetAddress x) throws SocketException, UnknownHostException, FileNotFoundException {
        this.leitura = new BufferedReader(new FileReader("testes.txt"));
        socket = so;
        address = x;
        portaservidor = porta;
    }
    @Override
    public void run(){
        running = true;
        while(running){
        try {
            this.funcaoTeste();
            } catch (IOException ex) {
                Logger.getLogger(ThreadTeclado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

//        running = true;
//        Scanner sc = new Scanner(System.in);
//        while(running){
//            String msg = sc.nextLine();
//            try {
//                this.sendEcho(msg, portaservidor);
//            } catch (IOException ex) {
//                Logger.getLogger(ThreadTeclado.class.getName()).log(Level.SEVERE, null, ex);
//            }            
//        }
//
//        sc.close();
        socket.close();
    }
 
    public void sendEcho(String msg, int porta) throws IOException {
        String[] partes;
        partes = msg.split(" ");
        byte[] chave = new byte[20];
        chave = partes[1].getBytes();
        if(chave.length<=20){
            buf = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, porta);
            socket.send(packet);  
        }
        else{
            System.out.println("Tamanho da chave excedido");
        }
        
        

    }
    
    public void funcaoTeste() throws IOException{
        String str;
        if(leitura != null){
            str = leitura.readLine();
            while(str!=null) {
                this.sendEcho(str, portaservidor);
                str = leitura.readLine();    
            }
        }        
    }
    
    
}
