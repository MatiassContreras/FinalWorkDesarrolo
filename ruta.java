public class ruta {
    //Variable
    private String nroRuta;
    private String ciudadOrigen;
    private String ciudadDestino;
    private String kmDistancia;
    private String internacional;

    //Constructor
    public ruta(String unNro, String unOrigen, String unDestino, String unaDistancia, String esInternacional){
        this.nroRuta = unNro;
        this.ciudadOrigen = unOrigen;
        this.ciudadDestino = unDestino;
        this.kmDistancia = unaDistancia;
        this.internacional = esInternacional;
    }

    //Getters
    public String getNroRuta(){
        return nroRuta;
    }

    public String getOrigen(){
        return ciudadOrigen;
    }

    public String getDestino(){
        return ciudadDestino;
    }

    public String getKmDistancia(){
        return kmDistancia;
    }

    public String getEsInternacional(){
        return internacional;
    }

    //Setters
    public void setOrigen(String unOrigen){
        this.ciudadOrigen = unOrigen;
    }

    public void setDestino(String unDestino){
        this.ciudadDestino = unDestino;
    }

    public void setKmDistancia(String unaDistancia){
        this.kmDistancia = unaDistancia;
    }

    public void setEsInternacional(String esInternacional){
        this.internacional = esInternacional;
    }

    //Propias del tipo
    public String toString(){
        return("NUMERO DE RUTA: "+nroRuta+"\nCIUDAD DE ORIGEN: "+ciudadOrigen+"\nCIUDAD DE DESTINO: "+ciudadDestino+"\nKILOMETROS DE DISTANCIA: "+kmDistancia+"\nINTERNACIONAL: "+internacional);
    }


    public boolean equals(ruta unaRuta){
        boolean iguales;

        if (this.nroRuta == unaRuta.getNroRuta() && this.ciudadOrigen == unaRuta.getOrigen() && this.ciudadDestino == unaRuta.getDestino()) {
            iguales = true;
        }else{
            iguales = false;
        }

        return iguales;
    }
}
