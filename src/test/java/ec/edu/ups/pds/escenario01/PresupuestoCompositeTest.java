package ec.edu.ups.pds.escenario01;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PresupuestoCompositeTest {

    @Test
    @DisplayName("Una PartidaPresupuestaria (hoja) devuelve su valor asignado como total")
    void partidaPresupuestariaRetornaValorPropio() {
        PartidaPresupuestaria partida = new PartidaPresupuestaria("PAR-01", "Servicios de Internet", 150.50);

        assertEquals("PAR-01", partida.getCodigo());
        assertEquals("Servicios de Internet", partida.getDescripcion());
        assertEquals(150.50, partida.getValor(), 0.001);
        assertEquals(150.50, partida.calcularTotal(), 0.001);
    }

    @Test
    @DisplayName("Un GrupoPresupuestario calcula el total sumando recursivamente todos sus niveles anidados")
    void grupoPresupuestarioCalculaTotalRecursivoMultiNivel() {
        // Subgrupo nivel 2
        GrupoPresupuestario subgrupo = new GrupoPresupuestario("GRP-SUB", "Suministros de Oficina");
        subgrupo.agregarElemento(new PartidaPresupuestaria("P-1", "Papel", 100.00));
        subgrupo.agregarElemento(new PartidaPresupuestaria("P-2", "Toner", 250.00));
        assertEquals(350.00, subgrupo.calcularTotal(), 0.001);

        // Grupo principal nivel 1
        GrupoPresupuestario principal = new GrupoPresupuestario("GRP-MAIN", "Operaciones");
        principal.agregarElemento(new PartidaPresupuestaria("P-3", "Alquiler", 1500.00));
        principal.agregarElemento(subgrupo); // Agrega el subgrupo compuesto

        // 1500 + 100 + 250 = 1850
        assertEquals(1850.00, principal.calcularTotal(), 0.001);
    }

    @Test
    @DisplayName("Escenario 01 del enunciado: Grupo Tecnologías de la Información con subgrupo Infraestructura")
    void escenario01EjemploTecnologiasDeLaInformacionEInfraestructura() {
        // Grupo: Tecnologías de la Información
        GrupoPresupuestario ti = new GrupoPresupuestario("GRP-TI", "Tecnologias de la Informacion");

        // Partidas directas de TI
        ti.agregarElemento(new PartidaPresupuestaria("PAR-LIC", "Licenciamiento", 1200.00));
        ti.agregarElemento(new PartidaPresupuestaria("PAR-SRV", "Servicios Cloud", 800.00));

        // Subgrupo: Infraestructura
        GrupoPresupuestario infraestructura = new GrupoPresupuestario("GRP-INFRA", "Infraestructura");
        infraestructura.agregarElemento(new PartidaPresupuestaria("PAR-SRV-01", "Servidores", 3000.00));
        infraestructura.agregarElemento(new PartidaPresupuestaria("PAR-ALM-02", "Almacenamiento", 1500.00));
        infraestructura.agregarElemento(new PartidaPresupuestaria("PAR-COM-03", "Comunicaciones", 900.00));

        // El grupo TI contiene al subgrupo Infraestructura
        ti.agregarElemento(infraestructura);

        // Verificación del subgrupo Infraestructura: 3000 + 1500 + 900 = 5400
        assertEquals(5400.00, infraestructura.calcularTotal(), 0.001);

        // Verificación del grupo principal TI: 1200 + 800 + 5400 = 7400
        assertEquals(7400.00, ti.calcularTotal(), 0.001);
    }

    @Test
    @DisplayName("Transparencia: Partidas y Grupos se tratan mediante la misma abstracción PresupuestoComponent sin ifs")
    void elementosSeTratanMedianteMismaAbstraccionPresupuestoComponent() {
        // Tratamiento uniforme mediante la interfaz común
        PresupuestoComponent partidaSimple = new PartidaPresupuestaria("PAR-01", "Gasto Simple", 500.00);

        GrupoPresupuestario grupo = new GrupoPresupuestario("GRP-01", "Grupo Compuesto");
        grupo.agregarElemento(new PartidaPresupuestaria("PAR-A", "Item A", 200.00));
        grupo.agregarElemento(new PartidaPresupuestaria("PAR-B", "Item B", 300.00));
        PresupuestoComponent grupoCompuesto = grupo;

        // Lista genérica de la abstracción PresupuestoComponent
        List<PresupuestoComponent> componentes = List.of(partidaSimple, grupoCompuesto);

        // El cliente opera sobre ellos de forma idéntica sin distinguir el tipo
        assertEquals(500.00, componentes.get(0).calcularTotal(), 0.001);
        assertEquals(500.00, componentes.get(1).calcularTotal(), 0.001);

        double totalConsolidado = componentes.stream()
                .mapToDouble(PresupuestoComponent::calcularTotal)
                .sum();
        assertEquals(1000.00, totalConsolidado, 0.001);
    }

    @Test
    @DisplayName("Permite agregar y eliminar componentes dinámicamente actualizando la lista y el cálculo")
    void permiteAgregarYEliminarElementosCorrectamente() {
        GrupoPresupuestario grupo = new GrupoPresupuestario("GRP-01", "Prueba");
        PartidaPresupuestaria partida1 = new PartidaPresupuestaria("P-1", "Servicio A", 500.00);
        PartidaPresupuestaria partida2 = new PartidaPresupuestaria("P-2", "Servicio B", 300.00);

        grupo.agregarElemento(partida1);
        grupo.agregarElemento(partida2);
        assertEquals(2, grupo.getElementos().size());
        assertEquals(800.00, grupo.calcularTotal(), 0.001);

        boolean eliminado = grupo.eliminarElemento(partida1);
        assertTrue(eliminado);
        assertEquals(1, grupo.getElementos().size());
        assertEquals(300.00, grupo.calcularTotal(), 0.001);

        // Intentar eliminar un elemento no presente
        boolean eliminadoInexistente = grupo.eliminarElemento(partida1);
        assertFalse(eliminadoInexistente);
    }

    @Test
    @DisplayName("mostrarEstructura genera salida visual jerárquica con sangría adecuada")
    void mostrarEstructuraGeneraSalidaJerarquicaConSangria() {
        GrupoPresupuestario raiz = new GrupoPresupuestario("R-01", "Raiz");
        raiz.agregarElemento(new PartidaPresupuestaria("P-1", "Partida Uno", 120.00));

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream stdoutOriginal = System.out;
        try {
            System.setOut(new PrintStream(salidaCapturada, true, StandardCharsets.UTF_8));
            raiz.mostrarEstructura(0);
        } finally {
            System.setOut(stdoutOriginal);
        }

        String salida = salidaCapturada.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("+ [Grupo] R-01: Raiz"));
        assertTrue(salida.contains("  - [Partida] P-1: Partida Uno"));
    }

    @Test
    @DisplayName("getElementos devuelve una lista inmutable para proteger el encapsulamiento")
    void getElementosRetornaListaInmutable() {
        GrupoPresupuestario grupo = new GrupoPresupuestario("GRP-SEG", "Seguridad");
        grupo.agregarElemento(new PartidaPresupuestaria("P-SEG", "Antivirus", 200.00));

        assertThrows(UnsupportedOperationException.class, () ->
                grupo.getElementos().add(new PartidaPresupuestaria("P-X", "Inyeccion Ilegal", 999.00)));
    }

    @Test
    @DisplayName("Validaciones defensivas ante datos nulos o valores erróneos")
    void validacionesDefensivasAnteDatosInvalidos() {
        // Partida no acepta textos vacíos o nulos
        assertThrows(IllegalArgumentException.class, () ->
                new PartidaPresupuestaria(null, "Descripcion", 100.00));
        assertThrows(IllegalArgumentException.class, () ->
                new PartidaPresupuestaria("P-1", "   ", 100.00));

        // Partida no acepta valores negativos
        assertThrows(IllegalArgumentException.class, () ->
                new PartidaPresupuestaria("P-1", "Gasto", -50.00));

        // Grupo no acepta textos vacíos o nulos
        assertThrows(IllegalArgumentException.class, () ->
                new GrupoPresupuestario("", "Descripcion"));
        assertThrows(IllegalArgumentException.class, () ->
                new GrupoPresupuestario("GRP-1", null));

        // Grupo no acepta elementos nulos
        GrupoPresupuestario grupo = new GrupoPresupuestario("GRP-1", "General");
        assertThrows(IllegalArgumentException.class, () ->
                grupo.agregarElemento(null));
    }
}
