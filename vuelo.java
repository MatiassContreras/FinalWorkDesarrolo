public class vuelo {
    private String numVuelo;
    private String idAvion;
    private String hora;
    private String dia;
    private String idRuta;
    private int cantPasajeros;
    private boolean vueloTerminados;

    public vuelo(String unNumVuelo,String unIdAvion,String unaHora,String unDia,String unIdRuta){
        this.numVuelo=unNumVuelo;
        this.idAvion=unIdAvion;
        this.hora=unaHora;
        this.dia=unDia;
        this.idRuta=unIdRuta;
        this.cantPasajeros=0;
        this.vueloTerminados=false;
    }

    //Observadores
    public String getNumVuelo(){
        return numVuelo;
    }
    public String getidAvion(){
        return idAvion;
    }
    public String getHora(){
        return hora;
    }
    public String getDia(){
        return dia;
    }
    public String getIdRuta(){
        return idRuta;
    }
    public int getCantPasajeros(){
        return cantPasajeros;
    }
    public boolean getVueloTerminado(){
        return vueloTerminados;
    }

    //Modificadores

    public void setIdAvion(String unIdAvion){
        this.idAvion=unIdAvion;
    }
    public void setHora(String unaHora){
        this.hora=unaHora;
    }
    public void setDia(String unDia){
        this.dia=unDia;
    }
    public void setRuta(String unIdRuta){
        this.idRuta=unIdRuta;
    }
    public void setCantPasajeros(int unaCant){
        this.cantPasajeros=unaCant;
    }
    public void setVueloTerminado(boolean unaCond){
        this.vueloTerminados=unaCond;
    }

    //Propia del tipo

    public boolean equals(vuelo unVuelo){
        return (this.numVuelo==unVuelo.getNumVuelo() ? true:false);
    }
}
