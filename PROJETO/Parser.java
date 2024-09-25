import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;
import java.io.*;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;



public class Parser{
    private Map<String,CasaInteligente> Casas=new HashMap(); //Registo das Casas no parser(Nome do proprietario acompanhado pela sua casa)
    private Map<String,Fornecedor> Fornecedores=new HashMap();//Registo dos Fornecedores (Nome do fornecedor acompanhado por ele mesmo)
    
    /**
     * Parser
     */
     public void parse() throws FileNotFoundException,IOException{
        List<String> linhas = lerFicheiro("logs.txt");
        String[] linhaPartida;
        String divisao = null;
        CasaInteligente casaMaisRecente = null;
        for (String linha : linhas) {
            linhaPartida = linha.split(":", 2);
            switch(linhaPartida[0]){
                case "Fornecedor":
                    Fornecedor fornecedor=parseFornecedor(linhaPartida[1]);
                    addFornecedor(fornecedor);
                    break;
                case "Casa":
                    casaMaisRecente = parseCasa(linhaPartida[1]);
                    addCasa(casaMaisRecente);
                    break;
                case "Divisao":
                    if (casaMaisRecente == null) System.out.println("Linha inválida.");
                    divisao = linhaPartida[1];
                    casaMaisRecente.addRoom(divisao);
                    break;
                case "SmartBulb":
                    if (divisao == null) System.out.println("Linha inválida.");
                    SmartBulb sb = parseSmartBulb(linhaPartida[1]);
                    casaMaisRecente.addDevice(divisao, sb);
                    break;
                case "SmartSpeaker":
                    if (divisao == null) System.out.println("Linha inválida.");
                    SmartSpeaker sp = parseSmartSpeaker(linhaPartida[1]);
                    casaMaisRecente.addDevice(divisao, sp);
                    break;
                case "SmartCamera":
                    if (divisao == null) System.out.println("Linha inválida.");
                    SmartCamera sc = parseSmartCamera(linhaPartida[1]);
                    casaMaisRecente.addDevice(divisao, sc);
                    break;
                default:
                    System.out.println("Linha inválida.");
                    break;
            }
        }
        //guardar ficheiro
        ObjectOutputStream forn = new ObjectOutputStream(new FileOutputStream("Fornecedores.txt"));
        forn.writeObject(Fornecedores);
        ObjectOutputStream homes = new ObjectOutputStream(new FileOutputStream("Casas.txt"));
        homes.writeObject(Casas);
        System.out.println("Parsing Completed.");
    }
    
    /**
     * Ler ficheiro
     * 
     * @param Nome do ficheiro
     */
    public List<String> lerFicheiro(String nomeFich) {
        List<String> lines;
        try { lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8); }
        catch(IOException exc) { lines = new ArrayList<>(); }
        return lines;
    }
    
    /**
     * Adicionar a casa ao HashMap das Casas
     * 
     * @param Casa que queremos adicionar
     */
    public void addCasa(CasaInteligente b){
        Casas.put(b.getProprietario(),b);
    }
    
    /**
     * Adicionar o fornecedor ao HashMap dos Fornecedores
     * 
     * @param Fornecedor que queremos adicionar
     */
    public void addFornecedor(Fornecedor b){
        Fornecedores.put(b.getNome(),b);
    }
    
    /**
     * Faz parse de uma linha iniciada por "Fornecedor:"
     * 
     * @param Dados de um fornecedor
     * @return Fornecedor 
     */
    public Fornecedor parseFornecedor(String input){
        String nome=input;
        return new Fornecedor(nome);
    }
    
    /**
     * Faz parse de uma linha iniciada por "Casa:"
     * 
     * @param Dadas de uma casa
     * @return Casa
     */
    public CasaInteligente parseCasa(String input){
        String[] campos = input.split(",");
        String proprietario = campos[0]; //Nome do proprietario
        int nif = Integer.parseInt(campos[1]);
        String fornecedor= campos[2];
        return new CasaInteligente(proprietario,nif,fornecedor);
    }
    
    /**
     * Faz parse de uma linha iniciada por "SmartBulb:"
     * 
     * @param Dadas de uma SmartBulb
     * @return SmartBulb
     */
    public SmartBulb parseSmartBulb(String input){
         String[] campos = input.split(",");
         String tonalidade= campos[0];
         int kick=0;
         boolean t=false;
         if(tonalidade.equals("Warm")|tonalidade.equals("Neutral")|tonalidade.equals("Cold")){t=true;}
         if (!t){kick=Integer.parseInt(tonalidade);}
         double dimensao= Double.parseDouble(campos[1]);
         double ConsumoBase=Double.parseDouble(campos[2]);
         return new SmartBulb(tonalidade,dimensao,ConsumoBase);
    }
    
    /**
     * Faz parse de uma linha iniciada por "SmartSpeaker:"
     * 
     * @param Dadas de uma SmartSpeaker
     * @return SmartSpeaker
     */
    public SmartSpeaker parseSmartSpeaker(String input){
        String[] campos = input.split(",");
        double volume=Double.parseDouble(campos[0]);
        String radio=campos[1];//canal da radio
        String marca=campos[2];
        double ConsumoBase=Double.parseDouble(campos[3]);
        return new SmartSpeaker(volume,radio,marca,ConsumoBase);
    }
    
    /**
     * Faz parse de uma linha iniciada por "SmartCamera:"
     * 
     * @param Dadas de uma SmartCamera
     * @return SmartCamera
     */
    public SmartCamera parseSmartCamera(String input){
        String[] campos = input.split(",");
        String resolucao=campos[0].replace("(","");
        String resolucaolimpa=resolucao.replace(")","");
        String[] actualresolucao=resolucaolimpa.split("x");
        double largura=Double.parseDouble(actualresolucao[0]);
        double altura=Double.parseDouble(actualresolucao[1]);
        double tamanho=Double.parseDouble(campos[1]);
        double ConsumoBase=Double.parseDouble(campos[2]);
        return new SmartCamera(largura,altura,tamanho,ConsumoBase);
    }

}

