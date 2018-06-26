package servidor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessaLog {

    private BufferedReader leituraLogs;
    private BufferedReader leituraSnaps;

    private File diretorioSnaps;
    private File diretorioLogs;
    private String snaps;
    private String logs;

    private String enviar;
    private Map<BigInteger, String> mapa;

    private int contador = 0;

    public ProcessaLog(Map mapas, String a, String b) throws SocketException, FileNotFoundException {
        mapa = mapas;
        snaps = a;
        logs = b;
    }

    public void onde() throws FileNotFoundException, IOException {
        diretorioSnaps = new File(snaps);
        diretorioLogs = new File(logs);

        File[] listaSnaps = diretorioSnaps.listFiles();
        if (listaSnaps.length == 0) {

            Date dataHoraAtual = new Date();
            String hora = new SimpleDateFormat("ddMMyyyyHHmmss").format(dataHoraAtual);
            File temp = new File(snaps + "/snap" + hora + ".txt");
            temp.createNewFile();
        }
        listaSnaps = diretorioSnaps.listFiles();
        leituraSnaps = new BufferedReader((new FileReader(listaSnaps[listaSnaps.length - 1])));

        File[] listaLogs = diretorioLogs.listFiles();
        if (listaLogs.length == 0) {
            Date dataHoraAtual = new Date();
            String hora = new SimpleDateFormat("ddMMyyyyHHmmss").format(dataHoraAtual);
            File temp2 = new File(logs + "/log" + hora + ".txt");
            temp2.createNewFile();
        }
        listaLogs = diretorioLogs.listFiles();
        leituraLogs = new BufferedReader(new FileReader(listaLogs[listaLogs.length - 1]));

    }

    public void processaSnap() throws IOException {
        String str;
        String[] partes;
        BigInteger chave = null;
        String valor = null;
        String retorno;

        if (leituraSnaps != null) {
            str = leituraSnaps.readLine();
            while (str != null) {
                partes = str.split(" ");
                chave = new BigInteger(partes[0]);
                valor = partes[1];
                mapa.put(chave, valor);
                str = leituraSnaps.readLine();
            }
            leituraSnaps.close();
        }
    }

    public void lerLog() throws InterruptedException, IOException {
        String str;
        String[] partes;
        int operacao = 0;
        BigInteger chave = null;
        String valor = null;
        String retorno;

        if (leituraLogs != null) {
            str = leituraLogs.readLine();
            while (str != null) {
                partes = str.split(" ");

                operacao = Integer.parseInt(partes[0]);
                chave = new BigInteger(partes[1]);
                if (partes.length > 2) {
                    valor = partes[2];
                }
                str = leituraLogs.readLine();
                String n = this.processar(operacao, chave, valor);
                
            }
            leituraLogs.close();
        }
    }

    public String processar(int operacao, BigInteger chave, String valor) throws IOException, InterruptedException {
        String retorno;
        switch (operacao) {
            case 1:
                retorno = this.criar(chave, valor);
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

    public String criar(BigInteger chave, String valor) throws IOException, InterruptedException {

        if (mapa.put(chave, valor) != null) {
            return "Não Criado";
        } else {
            return "Criado";
        }

    }

    public String modificar(BigInteger chave, String valor) {
        if (mapa.containsKey(chave)) {
            String antigo = mapa.replace(chave, valor);
            return "Valor modificado";
        } else {
            return "Valor nao modificado";
        }
    }

    public String deletar(BigInteger chave, String valor) {
        if (mapa.containsKey(chave)) {
            mapa.remove(chave);
            return "objeto deletado";
        } else {
            return "objeto nao deletado";
        }
    }

    public void run() {
        try {
            this.onde();
            this.processaSnap();
            this.lerLog();
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(ProcessaLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
