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
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Collections;

public class APP{
        /**
         * Atualiza as casas
         */
        public static Map<String,CasaInteligente> fixCasas(Map<String,CasaInteligente> Casas){
            for(String d: Casas.keySet()){
                Casas.get(d).fixCasa();
            }
            return Casas;
        }
        public static void main(String[] args)throws FileNotFoundException,IOException{
        Map<String,CasaInteligente> Casasnonfix=new HashMap();//Estrutura de dados que armazena os nomes dos proprietarios e as suas casas
        Map<String,Fornecedor> Fornecedores=new HashMap();//Estrutura de dados que armazena os nomes dos fornecedores e os fornecedores
        Map<String,CasaInteligente> Casas= new HashMap();//Estrutura de dados que armazena os nomes dos proprietarios e as suas casas atualizada
        List<Fatura> faturas= new ArrayList();//Estrutura de dados que armazena todas as faturas
        Parser p = new Parser();
        System.out.println("Parsing logs...");
        p.parse();
        ObjectInputStream forn = new ObjectInputStream(new FileInputStream("Fornecedores.txt"));
        try
        {
            Fornecedores = (Map<String,Fornecedor>) forn.readObject();
        }
        catch (IOException |ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
        }
        
        ObjectInputStream homes = new ObjectInputStream(new FileInputStream("Casas.txt"));
        try
        {
            Casasnonfix = (Map<String,CasaInteligente>) homes.readObject();
        }
        catch (IOException |ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
        }
        String receber="";
        String receber1="";
        String receber2="";
        String op="";
        boolean continuar=true;
        boolean datainvalida=true;
        Scanner input = new Scanner(System.in);
        String data;
        LocalDate primeiradata=LocalDate.now();
        LocalDate segundadata=LocalDate.now();
        long diferenca=0;
        Casas=fixCasas(Casasnonfix);
        while(continuar){
        System.out.println("\nQue opção pretende?:\n"+
                           "O intervalo de tempo começa nesta data:"+primeiradata+"\n"+
                           "O intervalo de tempo acaba nesta data:"+segundadata+ "\n"+
                           "11-Estatística.\n"+
                           "10-Verificar estado de uma casa.\n"+
                           "9-Criar Fornecedor.\n"+
                           "8-Criar Casa.\n"+
                           "7-Trocar Fornecedor.\n"+
                           "6-Criar divisão de uma Casa.\n"+
                           "5-Criar dispositivo de numa Divisão.\n"+
                           "4-Ligar ou Desligar Dispositivos/Divisões.\n"+
                           "3-Criar uma fatura de uma casa.\n"+
                           "2-Atualizar intervalo de tempo (Ps:As datas defaults representam o dia atual).\n"+
                           "1-Save/Load do estado.\n"+
                           "0-Sair");
       try{op=input.nextLine();
           int n=Integer.parseInt(op);
        switch(n){
        case(11):
        /**
         * 11-Estatística
         */
        try{
        System.out.println("Qual pretende:?\n"+
                           "4-Casa que gastou mais no intervalo de tempo.\n"+
                           "3-Comercializador/Fornecedor com maior volume de faturação.\n"+
                           "2-Listar as faturas emitidas por um comercializador \n"+
                           "1-Listar os consumidores por ordem de consumo (Durante o intervalo de tempo inserido).");
        op=input.nextLine();
        int l=Integer.parseInt(op);
        if(l>4 |l<1){throw new NumeroInvalidoException();}
        switch(l){
            /**
             * Casa que gastou mais no intervalo de tempo
             */
            case(4):
            try{
            System.out.println("Insira a data correspondente ao inicio do intervalo de tempo");
            receber=input.nextLine();
            LocalDate.parse(receber);
            System.out.println("Insira a data correspondente ao fim do intervalo de tempo");
            receber1=input.nextLine();
            LocalDate.parse(receber1);
            Fatura maximo=new Fatura();
            for(Fatura r: faturas){
              if(r.getDataInicial().toString().equals(receber) & r.getDataFinal().toString().equals(receber1))
                {if(r.getValor()>maximo.getValor()){maximo=r;}
              }
            }
            if(maximo.getValor()!=0){
            System.out.print(Casas.get(maximo.getProprietario()));}
            else{throw new FaturaNaoExisteNoIntervaloException();}
            }catch(FaturaNaoExisteNoIntervaloException e){
            System.out.println("Não há faturas nesse intervalo.");}
            break;
            case(3):
            /**
             * Comercializador/Fornecedor com maior volume de faturação
             */
            try{
            Map<String,Double> letra= new HashMap();
            double max=0;
            String fornmax="";
            if(!faturas.isEmpty()){
            for(Fatura r: faturas){
                if(!letra.containsKey(r.getFornecedor())){letra.put(r.getFornecedor(),r.getValor());}
                else{letra.put(r.getFornecedor(),(letra.get(r.getFornecedor())+(r.getValor())));}
            }
            for(String r: letra.keySet()){
                if(letra.get(r)>max){fornmax=r;
                                     max=letra.get(r);}
            }
            System.out.print(fornmax+": " + max);
            }else{throw new FaturaNaoExisteException();}
            }catch(FaturaNaoExisteException e){System.out.println("Ainda não foi registada uma fatura.");}
            break;
            case(2):
            /**
             * Listar as faturas emitidas por um comercializador
             */
            try{
            System.out.println("Insira o nome do Fornecedor que quer listar as suas Faturas:");
            receber=input.nextLine();
            if(Fornecedores.containsKey(receber)){
            System.out.print("Fornecedor: "+receber+"\n");
            for(Fatura r: faturas){
            if(r.getFornecedor().equals(receber)){System.out.print(r+"\n");}
            }
            }else{throw new FornecedorNaoExisteException();}
            }catch(FornecedorNaoExisteException e){System.out.println("Não existe um Fornecedor com esse nome.");}
            break;
            case(1):
            /**
             * Listar os consumidores por ordem de consumo (Durante o intervalo de tempo inserido).
             */
            try{
            System.out.println("Insira a data correspondente ao inicio do intervalo de tempo");
            receber=input.nextLine();
            LocalDate.parse(receber);
            //System.out.print(receber.getClass().getName()+"\n");
            //System.out.print(receber.getClass().getSimpleName()+"\n");
            System.out.println("Insira a data correspondente ao fim do intervalo de tempo");
            receber1=input.nextLine();
            LocalDate.parse(receber1);
            List<Fatura> maior= new ArrayList();
            for(Fatura r:faturas){
                if(r.getDataInicial().toString().equals(receber) & r.getDataFinal().toString().equals(receber1)){
                maior.add(r);}
            }
            Collections.sort(maior, new Comparator<Fatura>(){
              public int compare(Fatura c1, Fatura c2) {
                if (c1.getValor() > c2.getValor()) return -1;
                if (c1.getValor() < c2.getValor()) return 1;
                return 0;
             }
           });
           if(!maior.isEmpty()){
            for(Fatura r:maior){
            System.out.print(r.getProprietario()+": "+r.getValor()+"\n");
           }
           }else{throw new FaturaNaoExisteNoIntervaloException();}
           }catch(FaturaNaoExisteNoIntervaloException e){System.out.println("Não há faturas nesse Intervalo.");}
           }
          }catch(NumeroInvalidoException e){System.out.println("Não existe uma opção associada ao número que inseriu.");}
        break;
        case(10):
        /**
         * Verificar estado de uma casa
         */
        try{
        System.out.println("Insira o nome do proprietário da casa à qual aceder:");
        receber=input.nextLine();
        if(Casas.containsKey(receber)){
          System.out.print(Casas.get(receber).toString());
        }else{throw new CasaNaoExisteException();}
        }catch(CasaNaoExisteException e){System.out.println("Não existe uma casa com esse proprietário.");}
        break;
        case(9):
        /**
         * Criar Fornecedor
         */
        try{
        System.out.println("Insira o nome do Fornecedor que quer criar:");
        receber=input.nextLine();
        if(Fornecedores.containsKey(receber)){
            throw new FornecedorJaExisteException();
        }else{
        Fornecedor fornecedor= new Fornecedor(receber);
        Fornecedores.put(fornecedor.getNome(),fornecedor);}
        }catch(FornecedorJaExisteException e){System.out.println("Já existe um Fornecedor com esse nome.");}
        break;
        case(8):
        /**
         * Criar Casa
         */
        try{
        System.out.println("Insira os dados da casa neste formato Nome,Nif,Fornecedor:");
        receber=input.nextLine();
        CasaInteligente casa=p.parseCasa(receber);
        if(Fornecedores.containsKey(casa.getFornecedor())){
        Casas.put(casa.getProprietario(),casa);
        }else{throw new FornecedorNaoExisteException();}
        //não há necessidade de ver se existe porque
        //Casa proprietario só tem uma casa
        //if(Casas.containsKey(casa.getProprietario()){System.out.println("Já existe");
        //if(Casas.containsKey(Vicente de Carvalho Castro){System.out.println("Aug");
        }catch(FornecedorNaoExisteException e){System.out.println("Não existe um Fornecedor com esse nome.");}
        break;
        case(7):
        /**
         * Trocar Fornecedor
         */
        try{
        System.out.println("Insira o nome do proprietário:");
        receber=input.nextLine();
        if(Casas.containsKey(receber)){
        System.out.println("Insira o nome do fornecedor:");
        receber1=input.nextLine();
        if(Fornecedores.containsKey(receber1)){Casas.get(receber).trocarFornecedor(receber1);}
        else{throw new FornecedorNaoExisteException();}
        }else{throw new CasaNaoExisteException();}
        }catch(CasaNaoExisteException e){System.out.println("Não existe uma casa com esse proprietário.");
        }catch(FornecedorNaoExisteException e){System.out.println("Não existe um Fornecedor com esse nome.");}
        break;
        case(6):
        /**
         * Criar divisão de uma Casa
         */
        try{
        System.out.println("Insira o nome do proprietário:");
        receber=input.nextLine();
        if(Casas.containsKey(receber)){
            System.out.println("Insira o nome da divisão:");
            receber1=input.nextLine();
            if(!Casas.get(receber).roomExists(receber1)){
                Casas.get(receber).addRoom(receber1);
            }else{throw new DivisaoJaExisteException();}
        }else{throw new CasaNaoExisteException();}
        }catch(CasaNaoExisteException e){System.out.println("Não existe uma casa com esse proprietário.");
        }catch(DivisaoJaExisteException e){System.out.println("Já existe uma divisão na casa com esse nome.");}
        break;
        case(5):
        /**
         * Criar dispositivo de numa Divisão
         */
        try{
        System.out.println("Insira o nome do proprietário:");
        receber=input.nextLine();
        if(Casas.containsKey(receber)){
            System.out.println("Insira o nome da divisão:");
            receber1=input.nextLine();
            if(Casas.get(receber).roomExists(receber1)){
            System.out.println("Que tipo de dispositivo quer adicionar:\n"+
                               "3-SmartCamera\n"+
                               "2-SmartBulb\n"+
                               "1-SmartSpeaker");
            op=input.nextLine();
            int g=Integer.parseInt(op);
            if(g>3 |g<1){throw new NumeroInvalidoException();}
            switch(g){
            case(3):
            /**
             * Adicionar SmartCamera
             */
            System.out.println("Insira os dados da SmartCamera neste formato LarguraxAltura,Tamanho,ConsumoBase :");
            receber2=input.nextLine();
            SmartCamera sc=p.parseSmartCamera(receber2);
            Casas.get(receber).addDevice(receber1, sc);
            break;
            case(2):
            /**
             * Adicionar SmartBulb
             */
            System.out.println("Insira os dados da SmartBulb neste formato Tonalidade,Dimensão,ConsumoBase :");
            receber2=input.nextLine();
            SmartBulb sb=p.parseSmartBulb(receber2);
            Casas.get(receber).addDevice(receber1, sb); 
            break;
            case(1):
            /**
             * Adicionar SmartSpeaker
             */
            System.out.println("Insira os dados da SmartSpeaker neste formato Volume,Radio,Marca,ConsumoBase ");
            receber2=input.nextLine();
            SmartSpeaker sp=p.parseSmartSpeaker(receber2);
            Casas.get(receber).addDevice(receber1, sp); 
            break;
            }
            }else{throw new DivisaoNaoExisteException();}
        }else{throw new CasaNaoExisteException();}
        }catch(CasaNaoExisteException e){System.out.println("Não existe uma casa com esse proprietário.");
        }catch(DivisaoNaoExisteException e){System.out.println("Não existe uma divisão com esse nome.");
        }catch(NumeroInvalidoException e){System.out.println("Não existe uma opção associada ao numero que inseriu.");}
        break;
        case(4):
        /**
         * Ligar ou Desligar Dispositivos/Divisões
         */
        try{
        System.out.println("Insira o nome do proprietário:");
        receber=input.nextLine();
        if(Casas.containsKey(receber)){
        System.out.println("Pretende ligar ou desligar:\n"+
                           "1-Ligar\n"+
                           "0-Desligar");
        op=input.nextLine();
        int t=Integer.parseInt(op);
        if(t>1 |t<0){throw new NumeroInvalidoException();}
        if(t==1){
            System.out.println("Pretende ligar um dispositivo ou uma divisão:\n"+
                               "1-Dispositivo\n"+
                               "0-Divisão");
            op=input.nextLine();
            int i=Integer.parseInt(op);
            if(i>1 |i<0){throw new NumeroInvalidoException();}
            if(i==1){
            System.out.println("Indique no Código/Id do dispositivo que quer ligar:");
            receber1=input.nextLine();
            if(Casas.get(receber).deviceExists(receber1)){
            Casas.get(receber).setDeviceOn(receber1,true);}else{
                                System.out.println("bruh");
                throw new DeviceNaoExisteException();}
            }if(i==0){
            System.out.println("Indique a divisão que quer ligar os dispositivos:");
            receber1=input.nextLine();
            if(Casas.get(receber).roomExists(receber1)){
            Casas.get(receber).setDivisionOn(receber1,true);}else{throw new DivisaoNaoExisteException();}
            }
        
        }if(t==0){
            System.out.println("Pretende desligar um Dispositivo ou uma Divisão:\n"+
                               "1-Dispositivo\n"+
                               "0-Divisão");
            op=input.nextLine();
            int i=Integer.parseInt(op);
            if(i>1 |i<0){throw new NumeroInvalidoException();}
            if(i==1){
            System.out.println("Indique no Código/Id do dispositivo que quer desligar:");
            receber1=input.nextLine();
            if(Casas.get(receber).deviceExists(receber1)){
            Casas.get(receber).setDeviceOn(receber1,false);}else{throw new DeviceNaoExisteException();}
            }if(i==0){
            System.out.println("Indique a divisão que quer desligar os dispositivos:");
            receber1=input.nextLine();
            if(Casas.get(receber).roomExists(receber1)){
            Casas.get(receber).setDivisionOn(receber1,false);}else{throw new DivisaoNaoExisteException();}
            }
        }
        }else{throw new CasaNaoExisteException();}
        }catch(CasaNaoExisteException e){System.out.println("Não existe uma casa com esse proprietário.");
        }catch(DivisaoNaoExisteException e){System.out.println("Não existe uma divisão com esse nome.");
        }catch(DeviceNaoExisteException e){System.out.println("Não existe um device com esse Código/Id na casa.");
        }catch(NumeroInvalidoException e){System.out.println("Não existe uma opção associada ao numero que inseriu.");}
        break;
        case(3):
        /**
         * Criar uma fatura de uma casa
         */
        try{
        System.out.println("Insira o nome do proprietário que quer ver a fatura no intervalo de tempo inserido:");
        receber=input.nextLine();
        if(Casas.containsKey(receber)){
        double fatura=(diferenca*Casas.get(receber).FaturadaCasa())+Casas.get(receber).CustototalInstalacao()+(diferenca*Fornecedores.get(Casas.get(receber).getFornecedor()).precodia());
        double faturaround= (double) Math.round(fatura * 100) / 100;
        Fatura esta=new Fatura(receber,Casas.get(receber).getFornecedor(),primeiradata,segundadata,faturaround);
        faturas.add(esta);
        System.out.print(esta);
        }else{throw new CasaNaoExisteException();}
        }catch(CasaNaoExisteException e){System.out.println("Não existe uma casa com esse proprietário.");}
        break;
        case(2):
        /**
         * Atualizar intervalo de tempo
         */
        datainvalida=true;
        while(datainvalida){
        System.out.println("Insira a data (AAAA-MM-DD) em que começa o intervalo:");
        receber=input.nextLine();
        try{primeiradata=LocalDate.parse(receber);
        System.out.println("Insira a data (AAAA-MM-DD) em que acaba o intervalo:");
        receber1=input.nextLine();
        segundadata=LocalDate.parse(receber1);
        diferenca=ChronoUnit.DAYS.between(primeiradata,segundadata);
        //System.out.print(diferenca);
        if(diferenca<0){throw new IntervaloInvalidoException();
        }else{datainvalida=false;}
        }catch(IntervaloInvalidoException e){System.out.println("Não inseriu um intervalo válido (A data em que começa tem de ser mais antiga que a data que acaba).");
        }catch(Exception e){System.out.println("Colocou um formato inválido.");}
    }
        break;
        case(1):
        /**
         * Save/Load do estado
         */
        try{
        System.out.println("Qual pretende?\n"+
                           "1-Save\n"+
                           "0-Load");
        op=input.nextLine();
        int t=Integer.parseInt(op);
        if(t>1 |t<0){throw new NumeroInvalidoException();}
        if(t==1){
        ObjectOutputStream estado = new ObjectOutputStream(new FileOutputStream("Estado.txt"));
        estado.writeObject(Fornecedores);
        estado.writeObject(Casas);
        estado.writeObject(faturas);
        }if(t==0){
          ObjectInputStream estado = new ObjectInputStream(new FileInputStream("Estado.txt"));
           try
           {
            Fornecedores = (Map<String,Fornecedor>) estado.readObject();
            Casas = (Map<String,CasaInteligente>) estado.readObject();
            faturas=(List<Fatura>) estado.readObject();
            } 
           catch (IOException |ClassNotFoundException cnfe)
           {
             System.out.println("Erro na leitura.");
           }
        }
      }catch(NumeroInvalidoException e){System.out.println("Não existe uma opção associada ao numero que inseriu.");}
        break;
        case(0):
        /**
         * Sair
         */
        try{
        System.out.println("Tem a certeza que quer sair?:\n"+
                           "1-Sim\n"+
                           "0-Quero Voltar");
        op=input.nextLine();
        int m=Integer.parseInt(op);
        if(m>1 |m<0){throw new NumeroInvalidoException();}
        if(m==1){continuar=false;}
       }catch(NumeroInvalidoException e){System.out.println("Não existe uma opção associada ao numero que inseriu.");}
        break;
        default:
            System.out.println("Não existe uma opção associada ao numero que inseriu.");
        break;
            
       }
    }catch(Exception e){System.out.println("Colocou um formato inválido.");}
    }
    }
   
}
