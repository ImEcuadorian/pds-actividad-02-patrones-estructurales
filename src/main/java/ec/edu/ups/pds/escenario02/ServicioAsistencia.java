package ec.edu.ups.pds.escenario02;

import java.time.LocalDate;

public interface ServicioAsistencia {
    RegistroAsistencia consultarAsistencia(String identificacion, LocalDate fecha);
}