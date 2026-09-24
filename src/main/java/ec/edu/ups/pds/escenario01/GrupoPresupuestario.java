package ec.edu.ups.pds.escenario01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Compuesto (Composite) del patrón Composite.
 * Agrupa múltiples componentes presupuestarios (partidas u otros subgrupos)
 * y delega las operaciones recursivamente a sus hijos.
 */
public class GrupoPresupuestario implements PresupuestoComponent {

    private final String codigo;
    private final String descripcion;
    private final List<PresupuestoComponent> elementos;

    public GrupoPresupuestario(String codigo, String descripcion) {
        validarTexto("codigo", codigo);
        validarTexto("descripcion", descripcion);

        this.codigo = codigo.trim();
        this.descripcion = descripcion.trim();
        this.elementos = new ArrayList<>();
    }

    /**
     * Agrega un nuevo componente presupuestario (partida o subgrupo) a este grupo.
     *
     * @param elemento componente a agregar, no puede ser nulo.
     */
    public void agregarElemento(PresupuestoComponent elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El componente presupuestario no puede ser nulo");
        }
        elementos.add(elemento);
    }

    /**
     * Elimina un componente presupuestario de este grupo.
     *
     * @param elemento componente a eliminar.
     * @return true si el elemento estaba presente y fue removido, false en caso contrario.
     */
    public boolean eliminarElemento(PresupuestoComponent elemento) {
        return elementos.remove(elemento);
    }

    /**
     * Devuelve una vista inmutable de la lista de elementos hijos.
     *
     * @return lista de componentes hijos no modificable.
     */
    public List<PresupuestoComponent> getElementos() {
        return Collections.unmodifiableList(elementos);
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Calcula la sumatoria del valor monetario de todos los elementos contenidos recursivamente.
     *
     * @return suma total acumulada.
     */
    @Override
    public double calcularTotal() {
        double total = 0.0;
        for (PresupuestoComponent elemento : elementos) {
            total += elemento.calcularTotal();
        }
        return total;
    }

    /**
     * Muestra la estructura jerárquica con sangría visual según el nivel,
     * imprimiendo el encabezado del grupo y delegando a cada hijo con nivel + 1.
     *
     * @param nivel nivel de anidamiento para la sangría.
     */
    @Override
    public void mostrarEstructura(int nivel) {
        String sangria = "  ".repeat(Math.max(0, nivel));
        System.out.printf("%s+ [Grupo] %s: %s (Subtotal: $%.2f USD)%n",
                sangria, codigo, descripcion, calcularTotal());

        for (PresupuestoComponent elemento : elementos) {
            elemento.mostrarEstructura(nivel + 1);
        }
    }

    private static void validarTexto(String campo, String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo '" + campo + "' no puede ser nulo ni vacío");
        }
    }

    @Override
    public String toString() {
        return String.format("+ [%s] %s (%d elementos, Total: $%.2f)",
                codigo, descripcion, elementos.size(), calcularTotal());
    }
}
