/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.menu;

import foodstore.services.*;
import java.util.Scanner;
import foodstore.entities.Categoria;
import foodstore.entities.Producto;
import foodstore.entities.Usuario;
import foodstore.entities.Pedido;
import foodstore.enums.Rol;
import foodstore.enums.Estado;
import foodstore.enums.FormaPago;

/**
 *
 * @author Luis
 */
public class MenuPrincipal {

    private Scanner scanner = new Scanner(System.in);
    private CategoriaService categoriaService = new CategoriaService();
    private ProductoService productoService = new ProductoService();
    private UsuarioService usuarioService = new UsuarioService();
    private PedidoService pedidoService = new PedidoService();

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== FOOD STORE ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");
            opcion = leerInt();
            switch (opcion) {
                case 1 -> menuCategorias();
                case 2 -> menuProductos();
                case 3 -> menuUsuarios();
                case 4 -> menuPedidos();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private int leerInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private long leerLong() {
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private double leerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void menuCategorias() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- CATEGORÍAS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            opcion = leerInt();
            switch (opcion) {
                case 1 ->
                    listarCategorias();
                case 2 ->
                    crearCategoria();
                case 3 ->
                    editarCategoria();
                case 4 ->
                    eliminarCategoria();
                case 0 ->
                    System.out.println("Volviendo...");
                default ->
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void listarCategorias() {
        var lista = categoriaService.listarActivas();
        if (lista.isEmpty()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }
        System.out.println("\n--- LISTADO DE CATEGORÍAS ---");
        for (var c : lista) {
            System.out.printf("ID: %d | Nombre: %s | Descripción: %s%n",
                    c.getId(), c.getNombre(), c.getDescripcion());
        }
        
    }

    private void crearCategoria() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine().trim();
        try {
            Categoria nueva = new Categoria(nombre, descripcion);
            categoriaService.agregar(nueva);
            System.out.println("Categoría creada con ID: " + nueva.getId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editarCategoria() {
        listarCategorias();
        System.out.print("Ingrese ID a editar: ");
        long id = leerLong();
        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Nueva descripción (Enter para mantener): ");
        String descripcion = scanner.nextLine().trim();
        try {
            categoriaService.editar(id,
                    nombre.isEmpty() ? null : nombre,
                    descripcion.isEmpty() ? null : descripcion);
            System.out.println("Categoría actualizada.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarCategoria() {
        listarCategorias();
        System.out.print("Ingrese ID a eliminar: ");
        long id = leerLong();
        System.out.print("¿Confirma eliminación? (S/N): ");
        String confirmacion = scanner.nextLine().trim();
        if (confirmacion.equalsIgnoreCase("S")) {
            try {
                categoriaService.eliminar(id);
                System.out.println("Categoría eliminada.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    private void menuProductos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- PRODUCTOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            opcion = leerInt();
            switch (opcion) {
                case 1 ->
                    listarProductos();
                case 2 ->
                    crearProducto();
                case 3 ->
                    editarProducto();
                case 4 ->
                    eliminarProducto();
                case 0 ->
                    System.out.println("Volviendo...");
                default ->
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void listarProductos() {
        var lista = productoService.listarActivos();
        if (lista.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }
        System.out.println("\n--- LISTADO DE PRODUCTOS ---");
        for (var p : lista) {
            System.out.printf("ID: %d | Nombre: %s | Precio: $%.2f | Stock: %d | Categoría: %s%n",
                    p.getId(), p.getNombre(), p.getPrecio(), p.getStock(),
                    p.getCategoria() != null ? p.getCategoria().getNombre() : "Sin categoría");
        }
    }

    private void crearProducto() {
        var categorias = categoriaService.listarActivas();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías disponibles. Cree una primero.");
            return;
        }
        listarCategorias();
        System.out.print("ID de categoría: ");
        long idCat = leerLong();
        try {
            var categoria = categoriaService.buscarPorId(idCat);
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            System.out.print("Descripción: ");
            String descripcion = scanner.nextLine().trim();
            System.out.print("Precio: ");
            double precio = leerDouble();
            System.out.print("Stock: ");
            int stock = leerInt();
            System.out.print("Imagen: ");
            String imagen = scanner.nextLine().trim();
            Producto nuevo = new Producto(nombre, precio, descripcion, stock, imagen);
            categoria.agregarProducto(nuevo);
            productoService.agregar(nuevo);
            System.out.println("Producto creado con ID: " + nuevo.getId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editarProducto() {
        listarProductos();
        System.out.print("Ingrese ID a editar: ");
        long id = leerLong();
        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Nuevo precio (Enter para mantener): ");
        String precioStr = scanner.nextLine().trim();
        Double precio = precioStr.isEmpty() ? null : Double.parseDouble(precioStr);
        System.out.print("Nueva descripción (Enter para mantener): ");
        String descripcion = scanner.nextLine().trim();
        System.out.print("Nuevo stock (Enter para mantener): ");
        String stockStr = scanner.nextLine().trim();
        int stock = stockStr.isEmpty() ? null : Integer.parseInt(stockStr);
        System.out.print("Nueva imagen (Enter para mantener): ");
        String imagen = scanner.nextLine().trim();
        try {
            productoService.editar(id,
                    nombre.isEmpty() ? null : nombre,
                    precio < 0 ? null : precio,
                    descripcion.isEmpty() ? null : descripcion,
                    stock < 0 ? null : stock,
                    imagen.isEmpty() ? null : imagen);
            System.out.println("Producto actualizado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarProducto() {
        listarProductos();
        System.out.print("Ingrese ID a eliminar: ");
        long id = leerLong();
        System.out.print("¿Confirma eliminación? (S/N): ");
        String confirmacion = scanner.nextLine().trim();
        if (confirmacion.equalsIgnoreCase("S")) {
            try {
                productoService.eliminar(id);
                System.out.println("Producto eliminado.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    private void menuUsuarios() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- USUARIOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            opcion = leerInt();
            switch (opcion) {
                case 1 ->
                    listarUsuarios();
                case 2 ->
                    crearUsuario();
                case 3 ->
                    editarUsuario();
                case 4 ->
                    eliminarUsuario();
                case 0 ->
                    System.out.println("Volviendo...");
                default ->
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void listarUsuarios() {
        var lista = usuarioService.listarActivos();
        if (lista.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return;
        }
        System.out.println("\n--- LISTADO DE USUARIOS ---");
        for (var u : lista) {
            System.out.printf("ID: %d | Nombre: %s %s | Mail: %s | Rol: %s%n",
                    u.getId(), u.getNombre(), u.getApellido(), u.getMail(), u.getRol());
        }
    }

    private void crearUsuario() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        System.out.print("Mail: ");
        String mail = scanner.nextLine().trim();
        System.out.print("Celular: ");
        String celular = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine().trim();
        System.out.println("Rol (1. ADMIN / 2. USUARIO): ");
        int rolOpcion = leerInt();
        Rol rol = rolOpcion == 1 ? Rol.ADMIN : Rol.USUARIO;
        try {
            Usuario nuevo = new Usuario(nombre, apellido, mail, celular, contrasena, rol);
            usuarioService.agregar(nuevo);
            System.out.println("Usuario creado con ID: " + nuevo.getId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editarUsuario() {
        listarUsuarios();
        System.out.print("Ingrese ID a editar: ");
        long id = leerLong();
        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Nuevo apellido (Enter para mantener): ");
        String apellido = scanner.nextLine().trim();
        System.out.print("Nuevo mail (Enter para mantener): ");
        String mail = scanner.nextLine().trim();
        System.out.print("Nuevo celular (Enter para mantener): ");
        String celular = scanner.nextLine().trim();
        try {
            usuarioService.editar(id,
                    nombre.isEmpty() ? null : nombre,
                    apellido.isEmpty() ? null : apellido,
                    mail.isEmpty() ? null : mail,
                    celular.isEmpty() ? null : celular);
            System.out.println("Usuario actualizado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        listarUsuarios();
        System.out.print("Ingrese ID a eliminar: ");
        long id = leerLong();
        System.out.print("¿Confirma eliminación? (S/N): ");
        String confirmacion = scanner.nextLine().trim();
        if (confirmacion.equalsIgnoreCase("S")) {
            try {
                usuarioService.eliminar(id);
                System.out.println("Usuario eliminado.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    
    private void menuPedidos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- PEDIDOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar estado/forma de pago");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            opcion = leerInt();
            switch (opcion) {
                case 1 ->
                    listarPedidos();
                case 2 ->
                    crearPedido();
                case 3 ->
                    editarPedido();
                case 4 ->
                    eliminarPedido();
                case 0 ->
                    System.out.println("Volviendo...");
                default ->
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void listarPedidos() {
        var lista = pedidoService.listarActivos();
        if (lista.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
            return;
        }
        System.out.println("\n--- LISTADO DE PEDIDOS ---");
        for (var p : lista) {
            System.out.printf("ID: %d | Usuario: %s %s | Estado: %s | FormaPago: %s | Total: $%.2f | Fecha: %s%n",
                    p.getId(),
                    p.getUsuario().getNombre(), p.getUsuario().getApellido(),
                    p.getEstado(), p.getFormaPago(), p.getTotal(), p.getFechaPedido());
        }
    }

    private void crearPedido() {
        listarUsuarios();
        System.out.print("ID de usuario: ");
        long idUsuario = leerLong();
        try {
            Usuario usuario = usuarioService.buscarPorId(idUsuario);
            System.out.println("Forma de pago:");
            System.out.println("1. TARJETA  2. TRANSFERENCIA  3. EFECTIVO");
            int fpOpcion = leerInt();
            FormaPago fp = switch (fpOpcion) {
                case 1 ->
                    FormaPago.TARJETA;
                case 2 ->
                    FormaPago.TRANSFERENCIA;
                default ->
                    FormaPago.EFECTIVO;
            };
            Pedido pedido = new Pedido(Estado.PENDIENTE, fp);
            usuario.agregarPedido(pedido);
            boolean agregandoDetalles = true;
            while (agregandoDetalles) {
                listarProductos();
                System.out.print("ID de producto (0 para terminar): ");
                long idProducto = leerLong();
                if (idProducto == 0) {
                    agregandoDetalles = false;
                } else {
                    try {
                        var producto = productoService.buscarPorId(idProducto);
                        System.out.print("Cantidad: ");
                        int cantidad = leerInt();
                        pedido.addDetallePedido(cantidad, producto);
                        System.out.println("Detalle agregado.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }
            pedidoService.agregar(pedido);
            System.out.println("Pedido creado con ID: " + pedido.getId());
            System.out.printf("Total: $%.2f%n", pedido.getTotal());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editarPedido() {
        listarPedidos();
        System.out.print("Ingrese ID a editar: ");
        long id = leerLong();
        System.out.println("Nuevo estado (0 para mantener):");
        System.out.println("1. PENDIENTE  2. CONFIRMADO  3. TERMINADO  4. CANCELADO");
        int estadoOpcion = leerInt();
        Estado estado = switch (estadoOpcion) {
            case 1 ->
                Estado.PENDIENTE;
            case 2 ->
                Estado.CONFIRMADO;
            case 3 ->
                Estado.TERMINADO;
            case 4 ->
                Estado.CANCELADO;
            default ->
                null;
        };
        System.out.println("Nueva forma de pago (0 para mantener):");
        System.out.println("1. TARJETA  2. TRANSFERENCIA  3. EFECTIVO");
        int fpOpcion = leerInt();
        FormaPago fp = switch (fpOpcion) {
            case 1 ->
                FormaPago.TARJETA;
            case 2 ->
                FormaPago.TRANSFERENCIA;
            case 3 ->
                FormaPago.EFECTIVO;
            default ->
                null;
        };
        try {
            pedidoService.editarEstadoYFormaPago(id, estado, fp);
            System.out.println("Pedido actualizado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarPedido() {
        listarPedidos();
        System.out.print("Ingrese ID a eliminar: ");
        long id = leerLong();
        System.out.print("¿Confirma eliminación? (S/N): ");
        String confirmacion = scanner.nextLine().trim();
        if (confirmacion.equalsIgnoreCase("S")) {
            try {
                pedidoService.eliminar(id);
                System.out.println("Pedido eliminado.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }
}
