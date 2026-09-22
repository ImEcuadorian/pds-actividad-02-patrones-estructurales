package ec.edu.ups.pds.escenario02;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BibliotecaBiometricoNuevo {
    public List<MarcacionFabricante> obtenerMarcaciones(String codigoEmpleado, LocalDate fechaConsulta) {
        return List.of(new MarcacionFabricante(codigoEmpleado, LocalDateTime.of(fechaConsulta, java.time.LocalTime.of(8, 2))), new MarcacionFabricante(codigoEmpleado, LocalDateTime.of(fechaConsulta, java.time.LocalTime.of(17, 5))));
    }
}