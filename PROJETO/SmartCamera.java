import java.io.Serializable;

public class SmartCamera extends SmartDevice implements Serializable{
    private double altura; //altura do Dispositivo
    private double largura; //largura do Dispositivo
    private double tamanho; // tamanho do Dispositivo
    private double consumoBase; //Consumo base do Dispositivo
    
    /**
     * Contrutores SmartCamera
     */
    
    public SmartCamera(){
        super();
        this.altura=0;
        this.largura=0;
        this.tamanho=0;
        this.consumoBase=0;
    }

    public SmartCamera(double largura,double altura,double tamanho,double consumoBase){
        super();
        this.altura=altura;
        this.largura=largura;
        this.tamanho=tamanho;
        this.consumoBase=consumoBase;
    }
    
    public SmartCamera(SmartCamera s){
        super(s);
        this.altura=s.getAltura();
        this.largura=s.getLargura();
        this.tamanho=s.getTamanho();
        this.consumoBase=s.getConsumoBase();
    }

    /**
     * Getters e Setters
     */
    
    public double getAltura(){
        return this.altura;
    }
    
    public void setAltura(double altura){
        this.altura=altura;
    }
    
    public double getLargura(){
        return this.largura;
    }
    
    public void setLargura(double largura){
        this.largura=largura;
    }
    
    public double getTamanho(){
        return this.tamanho;
    }
    
    public void setTamanho(double tamanho){
        this.tamanho=tamanho;
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
        if(!this.getOn()){
            return 0;
        }else{
            //System.out.println("SmartCamera check");
            double resultado=(this.getAltura()*0.1)+(this.getLargura()*0.1)+this.getTamanho()+this.getConsumoBase();
            //System.out.print(this.getCodigo()+": "+resultado+"\n");
            return resultado;
        }
    }
    
    /**
     * Clone
     */
    public SmartDevice clone(){
        return new SmartCamera(this);
    }
    
    /**
     * toString
     */
    public String toString(){
        return ("SmartCamera:("+this.largura+"x"+this.altura+"),"+this.tamanho+","+this.consumoBase+"-"+this.getCodigo()+"-"+this.getOn());
    }
}