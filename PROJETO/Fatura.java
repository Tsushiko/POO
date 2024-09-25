import java.time.LocalDate;
import java.io.Serializable;

public class Fatura implements Serializable{
    private String proprietario; //Nome do proprietario da casa que foi faturada
    private String fornecedor;//Nome do fornecedor associado á casa faturada
    private LocalDate dataInicial;//Data inicial da fatura
    private LocalDate dataFinal;//Data final da fatura
    private double valor;//Valor da fatura
    
    /**
     * Contrutores Fatura
     */
    public Fatura(){
        this.proprietario="";
        this.fornecedor="";
        this.dataInicial=LocalDate.now();
        this.dataFinal=LocalDate.now();
        this.valor=0;
    }
    
    public Fatura(String proprietario,String fornecedor,LocalDate inicio,LocalDate fim,double valor){
        this.proprietario=proprietario;
        this.fornecedor=fornecedor;
        this.dataInicial=inicio;
        this.dataFinal=fim;
        this.valor=valor;
    }
    
    public Fatura(Fatura f){
        this.proprietario=f.getProprietario();
        this.fornecedor=f.getFornecedor();
        this.dataInicial=f.getDataInicial();
        this.dataFinal=f.getDataFinal();
        this.valor=f.getValor();
    }
    /**
     * Getters e Setters
     */
    public String getProprietario(){
        return this.proprietario;
    }
    
    public void setProprietario(String s){
        this.proprietario=s;
    }
    
    public String getFornecedor(){
        return this.fornecedor;
    }
    
    public void setFornecedor(String s){
        this.fornecedor=s;
    }
    
    public LocalDate getDataInicial(){
        return this.dataInicial;
    }
    
    public void setDataInicial(LocalDate i){
        this.dataInicial=i;
    }
    
    public LocalDate getDataFinal(){
        return this.dataFinal;
    }
    
    public void setDataFinal(LocalDate f){
        this.dataFinal=f;
    }
    
    public double getValor(){
        return this.valor;
    }
    
    public void setValor(double v){
        this.valor=v;
    }
    
    /**
     *toString
     */
    public String toString(){
        return ("("+this.getDataInicial().toString()+","+this.getDataFinal().toString()+")  "+this.proprietario+","+this.fornecedor+": "+this.valor+"\n");
    }
}