import java.util.Random;
import java.io.Serializable;

public class Fornecedor implements Serializable{
    private String nome; // Nome do Fornecedor
    private double valorBase;//Valor base do Fornecedor(este valor é comum para todos os Fornecedores)
    private double imposto;//Imposto (este valor é comum para todos os Fornecedores)
    private int rand; //Valor random 0 ou 1 que vai decidir qual formula usar
    private int rand2; //Valor random de 0 a 20 que vai ser usado na formula

    /**
     * Construtores Fornecedor
     */
    public Fornecedor(){
        this.nome="";
        this.valorBase=0;
        this.imposto=0;
        Random rn = new Random();
        this.rand=rn.nextInt(1 - 0 + 1) + 0;
        Random t = new Random();
        this.rand2=t.nextInt(20- 0 + 1) + 0;
    }
    
    public Fornecedor(String s){
        this.nome=s;
        this.valorBase=10;
        this.imposto=0.15;
        Random rn = new Random();
        this.rand=rn.nextInt(1 - 0 + 1) + 0;
        Random t = new Random();
        this.rand2=t.nextInt(20- 0 + 1) + 0;
    }
    public Fornecedor(String nome,double valorBase, double imposto){
        this.nome=nome;
        this.valorBase=valorBase;
        this.imposto=imposto;
        Random rn = new Random();
        this.rand=rn.nextInt(1 - 0 + 1) + 0;
        Random t = new Random();
        this.rand2=t.nextInt(20- 0 + 1) + 0;
    }
    
    /**
     * Getters e Setters
     */
    public void setNome(String nome){
        this.nome=nome;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public void setValorBase(double valorBase){
        this.valorBase=valorBase;
    }
    
    public double getValorBase(){
        return this.valorBase;
    }
    
    public void setImposto(double imposto){
        this.imposto=imposto;
    }
    
    public double getImposto(){
        return this.imposto;
    }
    
    public int getrand(){
        return this.rand;
    }
    
    public int getrand2(){
    return this.rand2;
    }
    
    /**
     * Calcular custo por dia do fornecedor
     * 
     * @return Custo por dia do fornecedor
     */
    public double precodia(){
        double resultado=0;
        switch(this.getrand()){
            case(0):
            resultado=(this.getValorBase()  * 1 + this.getImposto()) + this.getrand2();
            break;
            case(1):
            resultado=(this.getValorBase())  + (1 + this.getImposto() * this.getrand2());
            break;
        }
        System.out.print("Custo imposto pelo Fornecedor("+getNome()+") (Por dia): " +resultado+"\n");
        return resultado;
    }
    
        
}
    
    

