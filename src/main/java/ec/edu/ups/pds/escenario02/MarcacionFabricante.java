package ec.edu.ups.pds.escenario02;

import java.time.LocalDateTime;

public class MarcacionFabricante {
    private final String codigoEmpleado;
    private final LocalDateTime fechaHora;

    public MarcacionFabricante(String codigoEmpleado, LocalDateTime fechaHora) {
        this.codigoEmpleado = codigoEmpleado;
        this.fechaHora = fechaHora;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
}