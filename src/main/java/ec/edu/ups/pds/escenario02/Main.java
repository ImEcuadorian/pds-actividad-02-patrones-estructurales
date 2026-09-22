package ec.edu.ups.pds.escenario02;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        BibliotecaBiometricoNuevo biblioteca = new BibliotecaBiometricoNuevo();
        ServicioAsistencia servicio = new AdaptadorBiometricoNuevo(biblioteca);
        SistemaUniversidad sistema = new SistemaUniversidad(servicio);
        sistema.consultar("0102030405", LocalDate.of(2026, 9, 21));
    }
}