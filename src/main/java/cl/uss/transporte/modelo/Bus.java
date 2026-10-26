package cl.uss.transporte.modelo;

public class Bus {
    private int id;
    private String marca;
    private String modelo;
    private String patente;
    private Viaje viaje;

    public Bus() { }

    public Bus(int id, String marca, String modelo, String patente, Viaje viaje) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.viaje = viaje;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }
    public Viaje getViaje() { return viaje; }
    public void setViaje(Viaje viaje) { this.viaje = viaje; }
}
