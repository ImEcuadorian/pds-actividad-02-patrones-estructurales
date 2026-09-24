package ec.edu.ups.pds.escenario01;

import java.util.Scanner;

/**
 * Clase Cliente que interactúa con la jerarquía a través de la interfaz PresupuestoComponent.
 *
 * Demuestra la transparencia del patrón Composite: el cliente trata de la misma
 * forma a partidas presupuestarias individuales (hojas) y a grupos complejos anidados (compuestos).
 */
public class Cliente {

    public static void main(String[] args) {
        if (args.length > 0 && "--demo".equals(args[0])) {
            ejecutarDemostracion();
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            boolean continuar = true;

            while (continuar) {
                mostrarMenu();
                String opcion = scanner.nextLine().trim();

                switch (opcion) {
                    case "1":
                        ejecutarDemostracion();
                        break;
                    case "2":
                        demostrarModificacionDinamica();
                        break;
                    case "3":
                        crearPresupuestoInteractivo(scanner);
                        break;
                    case "0":
                        continuar = false;
                        System.out.println("Programa finalizado exitosamente.");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }

                System.out.println();
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("=== PATRÓN COMPOSITE: GESTIÓN DE PRESUPUESTOS ===");
        System.out.println("1. Demostración institucional completa (Grupos, Subgrupos y Partidas)");
        System.out.println("2. Demostración de eliminación dinámica (recalculo automático)");
        System.out.println("3. Construir presupuesto interactivo (crear Grupos, Subgrupos y Partidas)");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public static void ejecutarDemostracion() {
        System.out.println("\n========================================================");
        System.out.println("       ESTRUCTURA INSTITUCIONAL DE PRESUPUESTO 2026     ");
        System.out.println("========================================================");

        // Nivel 0: Raíz Compuesta
        GrupoPresupuestario presupuestoGeneral = new GrupoPresupuestario(
                "PRE-2026", "Presupuesto General Anual");

        // Nivel 1: Subgrupos Principales
        GrupoPresupuestario gastosOperativos = new GrupoPresupuestario(
                "GRP-01", "Gastos Operativos y Administrativos");
        GrupoPresupuestario talentoHumano = new GrupoPresupuestario(
                "GRP-02", "Talento Humano y Nómina");
        GrupoPresupuestario marketing = new GrupoPresupuestario(
                "GRP-03", "Marketing y Expansión Comercial");

        // Nivel 2 y Hojas de Gastos Operativos
        gastosOperativos.agregarElemento(new PartidaPresupuestaria(
                "PAR-101", "Arriendo de Edificio Central", 3500.00));
        gastosOperativos.agregarElemento(new PartidaPresupuestaria(
                "PAR-102", "Servicios Básicos (Agua, Energía, Internet)", 1200.00));

        // Subgrupo anidado dentro de Gastos Operativos (Nivel 2 compuesto)
        GrupoPresupuestario suministros = new GrupoPresupuestario(
                "GRP-011", "Materiales y Suministros");
        suministros.agregarElemento(new PartidaPresupuestaria(
                "PAR-111", "Papelería e Insumos de Oficina", 450.00));
        suministros.agregarElemento(new PartidaPresupuestaria(
                "PAR-112", "Licencias de Software y Seguridad", 2100.00));
        gastosOperativos.agregarElemento(suministros);

        // Hojas de Talento Humano
        talentoHumano.agregarElemento(new PartidaPresupuestaria(
                "PAR-201", "Sueldos y Remuneraciones Base", 25000.00));
        talentoHumano.agregarElemento(new PartidaPresupuestaria(
                "PAR-202", "Aportes Patronales y Beneficios de Ley", 4800.00));
        talentoHumano.agregarElemento(new PartidaPresupuestaria(
                "PAR-203", "Capacitación Continua y Certificaciones", 1500.00));

        // Hojas de Marketing
        marketing.agregarElemento(new PartidaPresupuestaria(
                "PAR-301", "Campañas en Medios Digitales", 3000.00));
        marketing.agregarElemento(new PartidaPresupuestaria(
                "PAR-302", "Participación en Ferias y Eventos", 1800.00));

        // Ensamble en la raíz
        presupuestoGeneral.agregarElemento(gastosOperativos);
        presupuestoGeneral.agregarElemento(talentoHumano);
        presupuestoGeneral.agregarElemento(marketing);

        // Invocación polimórfica de mostrarEstructura
        presupuestoGeneral.mostrarEstructura(0);

        System.out.println("--------------------------------------------------------");
        System.out.printf("PRESUPUESTO TOTAL CONSOLIDADO: $%.2f USD%n", presupuestoGeneral.calcularTotal());
        System.out.println("========================================================");
    }

    private static void demostrarModificacionDinamica() {
        System.out.println("\n--- DEMOSTRACIÓN DE MODIFICACIÓN DINÁMICA ---");
        GrupoPresupuestario proyectoTI = new GrupoPresupuestario("PRY-TI", "Proyecto Modernización Tecnológica");

        PartidaPresupuestaria servidores = new PartidaPresupuestaria("PAR-TI-01", "Servidores Cloud", 5000.00);
        PartidaPresupuestaria consultoria = new PartidaPresupuestaria("PAR-TI-02", "Consultoría Externa", 3000.00);
        PartidaPresupuestaria hardware = new PartidaPresupuestaria("PAR-TI-03", "Equipos Portátiles", 4500.00);

        proyectoTI.agregarElemento(servidores);
        proyectoTI.agregarElemento(consultoria);
        proyectoTI.agregarElemento(hardware);

        System.out.println("1. Estructura Inicial:");
        proyectoTI.mostrarEstructura(0);
        System.out.printf("Total Inicial: $%.2f USD%n%n", proyectoTI.calcularTotal());

        System.out.println("2. Se elimina la partida 'Consultoría Externa'...");
        boolean eliminado = proyectoTI.eliminarElemento(consultoria);
        System.out.println("Resultado de eliminación: " + (eliminado ? "Éxito" : "No encontrado"));

        System.out.println("\n3. Estructura Actualizada:");
        proyectoTI.mostrarEstructura(0);
        System.out.printf("Total Recalculado Automáticamente: $%.2f USD%n", proyectoTI.calcularTotal());
    }

    private static void crearPresupuestoInteractivo(Scanner scanner) {
        System.out.println("\n--- CONSTRUCCIÓN INTERACTIVA DE PRESUPUESTO (COMPOSITE) ---");
        System.out.print("Código del grupo raíz (ej. PRE-2026): ");
        String codRaiz = leerTexto(scanner);
        System.out.print("Descripción del grupo raíz (ej. Presupuesto Institucional): ");
        String descRaiz = leerTexto(scanner);

        GrupoPresupuestario raiz = new GrupoPresupuestario(codRaiz, descRaiz);

        // Permite anidar partidas y subgrupos con cualquier nivel de profundidad
        gestionarContenidoGrupo(scanner, raiz);

        System.out.println("\n========================================================");
        System.out.println("        ESTRUCTURA FINAL DEL PRESUPUESTO CREADO         ");
        System.out.println("========================================================");
        raiz.mostrarEstructura(0);
        System.out.println("--------------------------------------------------------");
        System.out.printf("TOTAL CONSOLIDADO DEL PRESUPUESTO: $%.2f USD%n", raiz.calcularTotal());
        System.out.println("========================================================");
    }

    /**
     * Permite gestionar interactivamente el contenido de un grupo,
     * admitiendo tanto hojas (PartidaPresupuestaria) como compuestos anidados (GrupoPresupuestario).
     */
    private static void gestionarContenidoGrupo(Scanner scanner, GrupoPresupuestario grupoActual) {
        boolean continuarEnEsteGrupo = true;

        while (continuarEnEsteGrupo) {
            System.out.printf("%n[Editando Grupo: %s - %s]%n",
                    grupoActual.getCodigo(), grupoActual.getDescripcion());
            System.out.println("  1. Agregar Partida Presupuestaria (Hoja simple)");
            System.out.println("  2. Crear y agregar Subgrupo Presupuestario (Compuesto anidado)");
            System.out.println("  3. Ver estructura actual y subtotal de este grupo");
            System.out.println("  0. Finalizar edición de este grupo (volver / guardar)");
            System.out.print("  Seleccione opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    System.out.print("  Código de la partida (ej. PAR-101): ");
                    String codP = leerTexto(scanner);
                    System.out.print("  Descripción de la partida (ej. Servicios Básicos): ");
                    String descP = leerTexto(scanner);
                    System.out.print("  Monto monetario (USD): ");
                    double valor = leerDouble(scanner);

                    grupoActual.agregarElemento(new PartidaPresupuestaria(codP, descP, valor));
                    System.out.println("  -> Partida agregada exitosamente.");
                    break;

                case "2":
                    System.out.print("  Código del nuevo subgrupo (ej. GRP-02): ");
                    String codSub = leerTexto(scanner);
                    System.out.print("  Descripción del nuevo subgrupo (ej. Marketing): ");
                    String descSub = leerTexto(scanner);

                    GrupoPresupuestario subgrupo = new GrupoPresupuestario(codSub, descSub);
                    grupoActual.agregarElemento(subgrupo);
                    System.out.println("  -> Subgrupo creado y vinculado. Ahora puedes agregarle elementos a este subgrupo:");

                    // Llamada recursiva para gestionar el nuevo subgrupo
                    gestionarContenidoGrupo(scanner, subgrupo);
                    break;

                case "3":
                    System.out.println("\n  --- Vista Previa del Grupo ---");
                    grupoActual.mostrarEstructura(1);
                    System.out.printf("  Subtotal actual: $%.2f USD%n", grupoActual.calcularTotal());
                    break;

                case "0":
                    continuarEnEsteGrupo = false;
                    break;

                default:
                    System.out.println("  Opción no válida. Intente nuevamente.");
            }
        }
    }

    private static String leerTexto(Scanner scanner) {
        while (true) {
            String entrada = scanner.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.print("El campo no puede estar vacío. Intente nuevamente: ");
        }
    }

    private static double leerDouble(Scanner scanner) {
        while (true) {
            String entrada = scanner.nextLine().trim().replace(',', '.');
            try {
                double val = Double.parseDouble(entrada);
                if (val >= 0) {
                    return val;
                }
                System.out.print("El valor no puede ser negativo. Intente nuevamente: ");
            } catch (NumberFormatException e) {
                System.out.print("Número inválido. Ingrese un valor decimal válido: ");
            }
        }
    }
}
