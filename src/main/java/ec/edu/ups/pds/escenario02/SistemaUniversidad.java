package ec.edu.ups.pds.escenario02;

import java.time.LocalDate;

public class SistemaUniversidad {
    private final ServicioAsistencia servicioAsistencia;

    public SistemaUniversidad(ServicioAsistencia servicioAsistencia) {
        this.servicioAsistencia = servicioAsistencia;
    }

    public void consultar(String identificacion, LocalDate fecha) {
        RegistroAsistencia registro = servicioAsistencia.consultarAsistencia(identificacion, fecha);
        System.out.println("Colaborador: " + registro.getIdentificacion());
        System.out.println("Fecha: " + registro.getFecha());
        System.out.println("Ingreso: " + registro.getHoraIngreso());
        System.out.println("Salida: " + registro.getHoraSalida());
    }
}