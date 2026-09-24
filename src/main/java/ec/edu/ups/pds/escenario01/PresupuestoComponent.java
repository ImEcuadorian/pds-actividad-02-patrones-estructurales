package ec.edu.ups.pds.escenario01;

/**
 * Componente base del patrón Composite para presupuestos.
 *
 * Declara las operaciones comunes para elementos simples (PartidaPresupuestaria)
 * y elementos compuestos (GrupoPresupuestario).
 */
public interface PresupuestoComponent {

    /**
     * Obtiene el código identificador del componente presupuestario.
     *
     * @return código alfanumérico único.
     */
    String getCodigo();

    /**
     * Obtiene la descripción o nombre del componente presupuestario.
     *
     * @return descripción del concepto de gasto.
     */
    String getDescripcion();

    /**
     * Calcula el monto monetario total del componente.
     * Si es una hoja devuelve su valor propio, si es un grupo calcula
     * la sumatoria recursiva de todos sus subelementos.
     *
     * @return total monetario en dólares.
     */
    double calcularTotal();

    /**
     * Muestra la estructura jerárquica con sangría visual según el nivel de profundidad.
     *
     * @param nivel nivel de anidamiento (0 para la raíz, 1, 2, etc.).
     */
    void mostrarEstructura(int nivel);
}
