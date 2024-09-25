import java.io.Serializable;

public class SmartBulb extends SmartDevice implements Serializable{
    private String tonalidade; //Tonalidade do Dispositivo varia entre(Cold,Neutral,Warm)
    private double dimensao; //Dimensão do Dispositivo
    private double consumoBase; //Consumo Base do Dispositivo

    /**
     * Contrutores SmartBulb
     */
    
    public SmartBulb(){
        super();
        this.tonalidade="";
        this.dimensao=0;
        this.consumoBase=0;
    }
    
    public SmartBulb(String tonalidade,double dimensao,double consumoBase){
        super();
        this.tonalidade=tonalidade;
        this.dimensao=dimensao;
        this.consumoBase=consumoBase;
    }
    
    public SmartBulb(SmartBulb b){
        super(b);
        this.tonalidade=b.getTonalidade();
        this.dimensao=b.getDimensao();
        this.consumoBase=b.getConsumoBase();
    }

    /**
     * Getters e Setters
     */
    
    public String getTonalidade(){
        return this.tonalidade;
    }
    
    public void setTonalidade(String tonalidade){
        this.tonalidade=tonalidade;
    }
    
    public double getDimensao(){
        return this.dimensao;
    }
    
    public void setDimensao(double dimensao){
        this.dimensao=dimensao;
    }
    
    public void setConsumoBase(double consumoBase){
        this.consumoBase=consumoBase;
    }
    
    public double getConsumoBase(){
        return this.consumoBase;
    }
    
    /**
     * Calcular consumo de um dispositivo
     * 
     * @return Consumo do dispositivo
     */
    public double consumoDispositivo(){
        int i=0;
        String b=this.getTonalidade();
        if(!this.getOn()){
            return 0;
        }else{
        if (b.equals("Cold")){
            i=1;
        }
        if (b.equals("Neutral")){
            i=2;
        }
        if (b.equals("Warm")){
            i=3;
        }
        //System.out.println("Smartbulb check");
        double resultado=1+(i*10)+this.getDimensao()+this.getConsumoBase();
        //System.out.print(this.getCodigo()+": "+resultado+"\n");
        return resultado;
    }
    }
    
    /**
     * Clone
     */
    public SmartDevice clone(){
        return new SmartBulb(this);
    }
    
    /**
     * toString
     */
    public String toString(){
        return ("SmartBulb:"+this.tonalidade+","+this.dimensao+","+this.consumoBase+"-"+this.getCodigo()+"-"+this.getOn());
    }
}
