package ec.edu.ups.pds.escenario01;

/**
 * Hoja (Leaf) del patrón Composite.
 * Representa una partida presupuestaria indivisible con un valor monetario asignado.
 */
public class PartidaPresupuestaria implements PresupuestoComponent {

    private final String codigo;
    private final String descripcion;
    private final double valor;

    public PartidaPresupuestaria(String codigo, String descripcion, double valor) {
        validarTexto("codigo", codigo);
        validarTexto("descripcion", descripcion);
        validarNoNegativo("valor", valor);

        this.codigo = codigo.trim();
        this.descripcion = descripcion.trim();
        this.valor = valor;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public double calcularTotal() {
        return valor;
    }

    @Override
    public void mostrarEstructura(int nivel) {
        String sangria = "  ".repeat(Math.max(0, nivel));
        System.out.printf("%s- [Partida] %s: %s -> $%.2f USD%n",
                sangria, codigo, descripcion, valor);
    }

    private static void validarTexto(String campo, String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo '" + campo + "' no puede ser nulo ni vacío");
        }
    }

    private static void validarNoNegativo(String campo, double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("El campo '" + campo + "' no puede ser negativo");
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: $%.2f", codigo, descripcion, valor);
    }
}
