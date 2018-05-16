package servidor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProcessaLog{
    private BufferedReader arquivo;
    private String enviar;
    private Map<BigInteger, String> mapa;
    
    public ProcessaLog(BufferedReader leitura,  Map mapas) throws SocketException, FileNotFoundException{
        mapa = mapas;
        arquivo = leitura;
    }

    public void lerArq() throws InterruptedException, IOException{
        String str;
        String[] partes;
        int operacao = 0;
        BigInteger chave = null;
        String valor = null;
        String retorno;
        
        if(arquivo != null){
            str = arquivo.readLine();
            while(str!=null){
                partes = str.split(" ");

                operacao = Integer.parseInt(partes[0]);
                chave = new BigInteger(partes[1]);
                if(partes.length>2)
                    valor = partes[2];
                str = arquivo.readLine();
                String n = this.processar(operacao, chave, valor);
            }
        }
    
    }
    
    public String processar(int operacao, BigInteger chave, String valor) throws IOException, InterruptedException{
        String retorno;
        switch (operacao){
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
    
    public String criar(BigInteger chave, String valor) throws IOException, InterruptedException{

        if(mapa.put(chave, valor) == null)
            return "Não Criado";
        else
            return "Criado";
        
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
    
    public void run() {
        try {
            this.lerArq();
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(ProcessaLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


    
}
