package servidor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadLog extends Thread implements Runnable {

    private DatagramSocket socket;
    private byte[] buf = new byte[1400];

    private BlockingQueue<String> fila;
    private Map<BigInteger, String> mapa = new HashMap<>();
    private FileOutputStream arquivo;

    private String logs;
    private File diretorioLogs;
    private int contador = 0;
    private BlockingQueue<Integer>  novo;

    public ThreadLog(BlockingQueue dois, String lo, BlockingQueue bo) throws SocketException, FileNotFoundException {
        fila = dois;
        diretorioLogs = new File(lo);
        logs = lo;
        
        novo = bo;
    }

    @Override
    public void run() {
        try {
            File[] listaLogs = diretorioLogs.listFiles();

            while (true) {

                if (listaLogs.length == 0 || novo.take() == 1) {
                    Date dataHoraAtual = new Date();
                    String hora = new SimpleDateFormat("ddMMyyyyHHmmss").format(dataHoraAtual);
                    arquivo = new FileOutputStream(logs + "/log" + hora + ".txt");
                    novo.add(0);
                }
                else{
                    arquivo = new FileOutputStream(listaLogs[listaLogs.length-1]);
                    novo.add(0);
                }

                String str = fila.take();
                String[] partes = str.split(" ");

                boolean grpc = false;

                if (partes.length < 4) {
                    grpc = true;
                } else {
                    grpc = false;
                }

                if (partes[0].equals("2") || partes[0].equals("5")) {
                    continue;
                } else {
                    str = partes[0] + " " + partes[1];
                }

                if (!partes[0].equals("4")) {
                    str = str + " " + partes[2] + "\n";
                } else {
                    str = str + "\n";
                }

                arquivo.write(str.getBytes());
            }
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(ThreadLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
