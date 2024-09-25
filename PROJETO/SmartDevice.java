import java.util.Random;

public abstract class SmartDevice{
    private boolean on; //Booleano corresponde a ligado(true) e desligado(false)
    private String codigo;//Codigo do Device gerado aleatóriamente ,o valor varia entre 0 e 10000000
    private double custoInstalacao;//Custo da Instalação do Device(este valor é comum para todos Devices,independentemente do tipo que sejam)
    
    /**
     * Contrutores SmartDevice
     */
    
    public SmartDevice(){
        this.on=false;
        this.custoInstalacao=10;
        Random rn = new Random();
        int rand=rn.nextInt(10000000 - 0 + 1) + 0;
        this.codigo=Integer.toString(rand);
        
    }
    
    public SmartDevice(boolean on,String codigo,double custoInstalacao){
        this.on=on;
        this.codigo=codigo;
        this.custoInstalacao=custoInstalacao;
    }

    public SmartDevice(SmartDevice s){
        this.on=s.getOn();
        this.codigo=s.getCodigo();
        this.custoInstalacao=s.getCustoInstalacao();
    }
    
    /**
     * Getters e Setters
     */
    
    public boolean getOn(){
        return this.on;
    }
    
    public void setOn(boolean on){
        this.on=on;
    }
    
    public String getCodigo(){
        return this.codigo;
    }
    
    public void setCodigo(String codigo){
        this.codigo=codigo;
    }
        
    public double getCustoInstalacao(){
        return this.custoInstalacao;
    }
    
    public void setCustoInstalacao(double custoInstalacao){
        this.custoInstalacao=custoInstalacao;
    }
    
    /**
     * Equals
     */
    
    public boolean equals(Object o){
       if (this==o) return true;
       if((o==null) || (o.getClass() != this.getClass())) return false;
       SmartDevice s = (SmartDevice) o;
       return (this.codigo==s.getCodigo() && this.on==s.getOn()  && this.custoInstalacao==s.getCustoInstalacao());
    }
    
    /**
     * Ligar um dispositivo
     */
    
    public void turnOn(){
        this.on=true;
    }
    
    /**
     * Desligar um dispositivo
     */
    
    public void turnOff(){
        this.on=false;
    }
    
    public abstract SmartDevice clone();
    
    public abstract double consumoDispositivo();
    
    //public abstract 
}
