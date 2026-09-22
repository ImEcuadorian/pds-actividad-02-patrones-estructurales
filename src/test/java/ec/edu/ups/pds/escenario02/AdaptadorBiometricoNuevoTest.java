package ec.edu.ups.pds.escenario02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdaptadorBiometricoNuevoTest {

    @Test
    @DisplayName("El adaptador debe implementar el contrato ServicioAsistencia")
    void adaptadorImplementaServicioAsistencia() {
        BibliotecaBiometricoNuevo biblioteca =
                new BibliotecaBiometricoNuevo();

        AdaptadorBiometricoNuevo adaptador =
                new AdaptadorBiometricoNuevo(biblioteca);

        assertInstanceOf(ServicioAsistencia.class, adaptador);
    }

    @Test
    @DisplayName("Debe convertir las marcaciones del fabricante en un RegistroAsistencia")
    void convierteMarcacionesEnRegistroAsistencia() {
        BibliotecaBiometricoNuevo biblioteca =
                new BibliotecaBiometricoNuevo();

        ServicioAsistencia servicio =
                new AdaptadorBiometricoNuevo(biblioteca);

        LocalDate fecha = LocalDate.of(2026, 9, 21);

        RegistroAsistencia registro =
                servicio.consultarAsistencia(
                        "0102030405",
                        fecha
                );

        assertNotNull(registro);

        assertEquals(
                "0102030405",
                registro.getIdentificacion()
        );

        assertEquals(
                fecha,
                registro.getFecha()
        );

        assertEquals(
                LocalTime.of(8, 2),
                registro.getHoraIngreso()
        );

        assertEquals(
                LocalTime.of(17, 5),
                registro.getHoraSalida()
        );
    }

    @Test
    @DisplayName("Debe seleccionar la primera marcación como hora de ingreso")
    void seleccionaPrimeraMarcacionComoIngreso() {

        LocalDate fecha =
                LocalDate.of(2026, 9, 21);

        BibliotecaBiometricoNuevo biblioteca =
                new BibliotecaBiometricoNuevo() {

                    @Override
                    public List<MarcacionFabricante> obtenerMarcaciones(
                            String codigoEmpleado,
                            LocalDate fechaConsulta) {

                        return List.of(
                                new MarcacionFabricante(
                                        codigoEmpleado,
                                        LocalDateTime.of(
                                                fechaConsulta,
                                                LocalTime.of(12, 30)
                                        )
                                ),
                                new MarcacionFabricante(
                                        codigoEmpleado,
                                        LocalDateTime.of(
                                                fechaConsulta,
                                                LocalTime.of(7, 55)
                                        )
                                ),
                                new MarcacionFabricante(
                                        codigoEmpleado,
                                        LocalDateTime.of(
                                                fechaConsulta,
                                                LocalTime.of(17, 10)
                                        )
                                )
                        );
                    }
                };

        ServicioAsistencia servicio =
                new AdaptadorBiometricoNuevo(biblioteca);

        RegistroAsistencia registro =
                servicio.consultarAsistencia(
                        "0102030405",
                        fecha
                );

        assertEquals(
                LocalTime.of(7, 55),
                registro.getHoraIngreso()
        );
    }

    @Test
    @DisplayName("Debe seleccionar la última marcación como hora de salida")
    void seleccionaUltimaMarcacionComoSalida() {

        LocalDate fecha =
                LocalDate.of(2026, 9, 21);

        BibliotecaBiometricoNuevo biblioteca =
                new BibliotecaBiometricoNuevo() {

                    @Override
                    public List<MarcacionFabricante> obtenerMarcaciones(
                            String codigoEmpleado,
                            LocalDate fechaConsulta) {

                        return List.of(
                                new MarcacionFabricante(
                                        codigoEmpleado,
                                        LocalDateTime.of(
                                                fechaConsulta,
                                                LocalTime.of(17, 45)
                                        )
                                ),
                                new MarcacionFabricante(
                                        codigoEmpleado,
                                        LocalDateTime.of(
                                                fechaConsulta,
                                                LocalTime.of(8, 0)
                                        )
                                ),
                                new MarcacionFabricante(
                                        codigoEmpleado,
                                        LocalDateTime.of(
                                                fechaConsulta,
                                                LocalTime.of(13, 15)
                                        )
                                )
                        );
                    }
                };

        ServicioAsistencia servicio =
                new AdaptadorBiometricoNuevo(biblioteca);

        RegistroAsistencia registro =
                servicio.consultarAsistencia(
                        "0102030405",
                        fecha
                );

        assertEquals(
                LocalTime.of(17, 45),
                registro.getHoraSalida()
        );
    }

    @Test
    @DisplayName("Si no existen marcaciones debe retornar ingreso y salida nulos")
    void retornaHorasNulasCuandoNoExistenMarcaciones() {

        BibliotecaBiometricoNuevo biblioteca =
                new BibliotecaBiometricoNuevo() {

                    @Override
                    public List<MarcacionFabricante> obtenerMarcaciones(
                            String codigoEmpleado,
                            LocalDate fechaConsulta) {

                        return List.of();
                    }
                };

        ServicioAsistencia servicio =
                new AdaptadorBiometricoNuevo(biblioteca);

        LocalDate fecha =
                LocalDate.of(2026, 9, 21);

        RegistroAsistencia registro =
                servicio.consultarAsistencia(
                        "0102030405",
                        fecha
                );

        assertNotNull(registro);

        assertEquals(
                "0102030405",
                registro.getIdentificacion()
        );

        assertEquals(
                fecha,
                registro.getFecha()
        );

        assertNull(registro.getHoraIngreso());
        assertNull(registro.getHoraSalida());
    }

    @Test
    @DisplayName("El sistema institucional debe depender del contrato y no directamente del fabricante")
    void sistemaPuedeTrabajarMedianteServicioAsistencia() {

        BibliotecaBiometricoNuevo biblioteca =
                new BibliotecaBiometricoNuevo();

        ServicioAsistencia servicio =
                new AdaptadorBiometricoNuevo(biblioteca);

        assertDoesNotThrow(
                () -> new SistemaUniversidad(servicio)
        );
    }
}