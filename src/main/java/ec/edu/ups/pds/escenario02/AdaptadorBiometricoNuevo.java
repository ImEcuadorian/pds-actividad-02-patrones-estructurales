package ec.edu.ups.pds.escenario02;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class AdaptadorBiometricoNuevo implements ServicioAsistencia {
    private final BibliotecaBiometricoNuevo biblioteca;

    public AdaptadorBiometricoNuevo(BibliotecaBiometricoNuevo biblioteca) {
        this.biblioteca = biblioteca;
    }

    @Override
    public RegistroAsistencia consultarAsistencia(String identificacion, LocalDate fecha) {
        List<MarcacionFabricante> marcaciones = biblioteca.obtenerMarcaciones(identificacion, fecha);
        if (marcaciones.isEmpty()) {
            return new RegistroAsistencia(identificacion, fecha, null, null);
        }
        LocalDateTime ingreso = marcaciones.stream().map(MarcacionFabricante::getFechaHora).min(Comparator.naturalOrder()).orElseThrow();
        LocalDateTime salida = marcaciones.stream().map(MarcacionFabricante::getFechaHora).max(Comparator.naturalOrder()).orElseThrow();
        return new RegistroAsistencia(identificacion, fecha, ingreso.toLocalTime(), salida.toLocalTime());
    }
}