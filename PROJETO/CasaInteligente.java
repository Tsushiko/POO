import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.Serializable;

public class CasaInteligente implements Serializable{
    private String proprietario; //Nome do proprietario
    private int nif; //nif do proprietario
    private Map<String, List<SmartDevice>> divisoes;//isto representa as Divisões com os seus devices que pertencem á casa
    private Map<String, SmartDevice> devices; //Código/ID do device acompanhado com ele mesmo
                                              //isto representa os Devices que se encontram na casa
    private String fornecedor; //Nome do fornecedor associado a esta Casa

    /**
     * Construtores CasaInteligente
     */
    public CasaInteligente(){
        this.proprietario="";
        this.nif=0;
        this.divisoes=new HashMap();
        this.devices=new HashMap();
        this.fornecedor="";
    }
    
    public CasaInteligente(String proprietario,int nif,String fornecedor){
        this.proprietario=proprietario;
        this.nif=nif;
        this.divisoes=new HashMap();
        this.devices=new HashMap();
        this.fornecedor=fornecedor;
    }
    
    /**
     * Getters e Setters
     */
    public void setFornecedor(String fornecedor){
        this.fornecedor=fornecedor;
    }
    
    public String getFornecedor(){
        return this.fornecedor;

    }
    public void trocarFornecedor(String f){
        this.fornecedor=f;
    }
    
    public void setNif(int nif){
        this.nif=nif;
    }
    
    public int getNif(){
        return this.nif;
    }
    
    public void setProprietario(String proprietario){
        this.proprietario=proprietario;
    }
    
    public String getProprietario(){
        return this.proprietario;
    }
    /**
     * Atualizar os códigos de cada dispositivo 
     */
    public void fixCasa(){
        for(String d: this.devices.keySet()){
            this.devices.get(d).setCodigo(d);
        }
    }
    
    /**
     * Ligar todos os dispositivos de uma divisão
     */
    public void setDivisionOn(String s, boolean b) {
        for (SmartDevice d: this.divisoes.get(s)){
            d.setOn(b);
        }
    }
    
    /**
     * Ligar ou desligar um dispositivo
     * 
     * @param Código do dispositvo , True (ligar) / False (desligar)
     */
    public void setDeviceOn(String s,boolean b) {
        this.devices.get(s).setOn(b);
    }
    
    /**
     * Calcular a soma do custo de todos os dispositivos por dia
     * 
     * @return Soma do custo de todos os dispositivos por dia
     */
    public double FaturadaCasa(){
        double consumo=0;
        for(String d :this.devices.keySet()){
            consumo=consumo+this.devices.get(d).consumoDispositivo();
        }
        System.out.print("Consumo dos Dispositivos (Por dia):"+consumo+"\n");
        return consumo;
    }
    
    /**
     * Calcular custo total da instalação de todos os dispositivos
     * 
     * @return Custo total da instalação de todos os dispositivos
     */
    public double CustototalInstalacao(){
        double consumo=0;
        for(String d :this.devices.keySet()){
            consumo=consumo+this.devices.get(d).getCustoInstalacao();
        }
        System.out.print("CustoInstalação: "+consumo+ "\n");
        return consumo;
    }
    
    /**
     * Adicionar dispositivo a uma divisão
     * 
     * @param Nome da divisão , Dispositivo
     */
    public void addDevice(String x,SmartDevice s) {
        this.devices.put(s.getCodigo(), s);
        this.divisoes.get(x).add(s);
    }
    
    /**
     * Verificar se uma divisão existe
     * 
     * @param Nome da divisão
     */
    public boolean roomExists(String s){
        return this.divisoes.containsKey(s);
    }
    
    /**
     * Verificar se um dispositivo existe
     * 
     * @param Código do dispositivo
     */
    public boolean deviceExists(String s){
        return this.devices.containsKey(s);
    }
    
    /**
     * Adicionar divisão a uma casa
     * 
     * @param Nome da divisão
     */
    public void addRoom(String s) {
        this.divisoes.put(s, new ArrayList<>());
    }
    
    /**
     * toString
     */
    public String toString(){
        StringBuilder bruh=new StringBuilder();
        bruh.append("\nCasa:"+proprietario+","+nif+","+fornecedor+"\n");
        for (String d: this.divisoes.keySet()){
            bruh.append(d+"\n");
            for(SmartDevice t: this.divisoes.get(d)){
                bruh.append(t+"\n");
            }
        }
        return bruh.toString();
                
    }
}
