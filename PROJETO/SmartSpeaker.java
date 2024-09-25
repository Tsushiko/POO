import java.io.Serializable;

public class SmartSpeaker extends SmartDevice implements Serializable{
    private double volume; //Volume do Dispositivo
    private String radio;  //Nome da Radio que se encontra ligado
    private String marca;  //Nome da marca do Dispositivo
    private double consumoBase; //Consumo base do dispositivo
    
    /**
     * Contrutores SmartSpeaker
     */
    
    public SmartSpeaker(){
        super();
        this.volume=0;
        this.radio="";
        this.marca="";
        this.consumoBase=0;
    }

    public SmartSpeaker(double volume,String radio,String marca,double consumoBase){
        super();
        this.volume=volume;
        this.radio=radio;
        this.marca=marca;
        this.consumoBase=consumoBase;
    }
    
    public SmartSpeaker(SmartSpeaker s){
        super(s);
        this.volume=s.getVolume();
        this.radio=s.getRadio();
        this.marca=s.getMarca();
        this.consumoBase=s.getConsumoBase();
    }
    
    /**
     * Getters e Setters
     */
    
    public double getVolume(){
        return this.volume;
    }
    
    public void setVolume(double volume){
        this.volume=volume;
    }
    
    public String getRadio(){
        return this.radio;
    }
    
    public void setRadio(String radio){
        this.radio=radio;
    }
    
    public String getMarca(){
        return this.marca;
    }
    
    public void setMarca(String marca){
        this.marca=marca;
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
         //System.out.println("SmartSpeaker check");
         double resultado=this.getVolume()+this.getMarca().length()+this.getConsumoBase();
         //System.out.print(this.getCodigo()+": "+resultado+"\n");
         return resultado;   
        }
    }
    
    /**
     * Clone
     */
    public SmartDevice clone(){
        return new SmartSpeaker(this);
    }
    
    /**
     * toString
     */
    public String toString(){
        return ("SmartSpeaker:"+this.volume+","+this.radio+","+this.marca+","+this.consumoBase+"-"+this.getCodigo()+"-"+this.getOn());
    }
}
