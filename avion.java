public class avion{
    private String Identificacion;
    private String Modelo;
    private String cantVuelos;
    private String cantAsientos;
    private String KMRecorridos;
    

    public avion(String Id, String unModelo, String CantVuelos, String CantAsientos, String CantKMRecorridos ){
        this.Identificacion=Id;
        this.Modelo=unModelo;
        this.cantVuelos=CantVuelos;
        this.cantAsientos=CantAsientos;
        this.KMRecorridos=CantKMRecorridos;
    }

    public String getIdentificacion(){
        return this.Identificacion;

    }
    public String getModelo(){
        return this.Modelo;
    }

    public String getCantVuelos(){
        return this.cantVuelos;

    }
    public String getCantVuelo(){
        return this.cantAsientos;
    }
    public String getKMRecorridos(){
        return this.KMRecorridos;
    }

    //Metodo Modificador

    public void setCantVuelos(String CantVuelos){
        this.cantVuelos=CantVuelos;
    }
    public void setKMRecorridos(String unKM){
        this.KMRecorridos=unKM;
    }


    //

    public boolean Equals(avion unAvion){
        boolean equals=false;
        if(this.Identificacion==unAvion.Identificacion){
            equals=true;
        }
        return equals;
    }

}