package ec.edu.ups.pds.escenario02;

import java.time.LocalDate;
import java.time.LocalTime;

public class RegistroAsistencia {
    private final String identificacion;
    private final LocalDate fecha;
    private final LocalTime horaIngreso;
    private final LocalTime horaSalida;

    public RegistroAsistencia(String identificacion, LocalDate fecha, LocalTime horaIngreso, LocalTime horaSalida) {
        this.identificacion = identificacion;
        this.fecha = fecha;
        this.horaIngreso = horaIngreso;
        this.horaSalida = horaSalida;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraIngreso() {
        return horaIngreso;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }
}