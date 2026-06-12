/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.services;

import foodstore.entities.Categoria;
import foodstore.entities.Producto;
import foodstore.exception.DatoInvalidoException;
import foodstore.exception.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Luis
 */
public class ProductoService {

    private List<Producto> productos = new ArrayList<>();

    public void agregar(Producto producto) {
        if (producto.getPrecio() < 0 || producto.getStock() < 0) {
            throw new DatoInvalidoException("Precio y stock no pueden ser negativos.");
        }
        productos.add(producto);
    }

    public List<Producto> listarActivos() {
        List<Producto> activos = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return Collections.unmodifiableList(activos);
    }

    public List<Producto> listarPorCategoria(Categoria categoria) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado() && p.getCategoria().equals(categoria)) {
                resultado.add(p);
            }
        }
        return Collections.unmodifiableList(resultado);
    }

    public Producto buscarPorId(long id) {
        for (Producto p : productos) {
            if (p.getId() == id && !p.isEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("Producto con id " + id + " no encontrado.");
    }

    public void eliminar(long id) {
        Producto p = buscarPorId(id);
        p.eliminar();
    }
    
    public void editar(long id, String nuevoNombre, Double nuevoPrecio,String nuevaDescripcion, Integer nuevoStock, String nuevaImagen){
        Producto p = buscarPorId(id);
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            p.actualizarNombre(nuevoNombre);
        }
        if (nuevoPrecio != null) {
            if (nuevoPrecio < 0) throw new DatoInvalidoException("El precio no puede ser negativo.");
            p.actualizarPrecio(nuevoPrecio);
        }
        if (nuevaDescripcion != null && !nuevaDescripcion.trim().isEmpty()) {
            p.actualizarDescripcion(nuevaDescripcion);
        }
        if (nuevoStock != null) {
            if (nuevoStock < 0) throw new DatoInvalidoException("El stock no puede ser negativo.");
            p.actualizarStock(nuevoStock);
            p.cambiarDisponible();
        }
        if (nuevaImagen != null && !nuevaImagen.trim().isEmpty()) {
            p.actualizarImagen(nuevaImagen);
        }
    }
}
