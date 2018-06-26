package servidor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadSnap extends Thread implements Runnable {

    //private FileOutputStream escrita;
    private Map<BigInteger, String> mapa;

    private File diretorioSnaps;
    private File diretorioLogs;
    private String snaps;
    private String logs;

    private BlockingQueue<Integer>  novo;
    
   
    private int cont = 0;

    public ThreadSnap(Map mapas, String a, String b, BlockingQueue bo) {
        mapa = mapas;
        snaps = a;
        logs = b;
        
        novo = bo;
    }

    public String onde() throws FileNotFoundException, IOException {
        diretorioSnaps = new File(snaps);
        diretorioLogs = new File(logs);
        File[] listaSnaps = diretorioSnaps.listFiles();
        
        Date dataHoraAtual = new Date();
        String hora = new SimpleDateFormat("ddMMyyyyHHmmss").format(dataHoraAtual);
        
        return snaps+"/snap"+hora+".txt";
   
    }

    public void apagar() throws IOException {
        File[] listaSnaps = diretorioSnaps.listFiles();
        File[] listaLogs = diretorioLogs.listFiles();
        
        if(listaSnaps.length>4){
            listaSnaps[0].delete();
        }
        if(listaLogs.length>4){
            listaLogs[0].delete();
        }        

    }

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(7000);
                FileOutputStream escrita = new FileOutputStream(this.onde());
                mapa.forEach((K, V) -> {
                    try {
                        String w = K.toString() + " " + V + "\n";
                        escrita.write(w.getBytes());
                        escrita.flush();
                        
                    } catch (IOException ex) {
                        Logger.getLogger(ThreadSnap.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                novo.take();
                novo.add(1);
                escrita.close();
                this.apagar();

            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadSnap.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ThreadSnap.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ThreadSnap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
