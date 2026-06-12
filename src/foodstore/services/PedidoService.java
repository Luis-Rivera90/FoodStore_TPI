 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.services;

import foodstore.entities.Pedido;
import foodstore.entities.Usuario;
import foodstore.exception.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.List;
import foodstore.enums.Estado;
import foodstore.enums.FormaPago;
import java.util.Collections;

/**
 *
 * @author Luis
 */
public class PedidoService {

    private List<Pedido> pedidos = new ArrayList<>();

    public void agregar(Pedido pedido) {
        if (pedido.getUsuario() == null) {
            throw new EntidadNoEncontradaException("El pedido debe tener un usuario asignado.");
        }
        pedidos.add(pedido);
    }

    public List<Pedido> listarActivos() {
        List<Pedido> activos = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return Collections.unmodifiableList(activos);
    }

    public List<Pedido> listarPorUsuario(Usuario usuario) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado() && p.getUsuario().equals(usuario)) {
                resultado.add(p);
            }
        }
        return Collections.unmodifiableList(resultado);
    }

    public Pedido buscarPorId(long id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id && !p.isEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("Pedido con id " + id + " no encontrado.");
    }

    public void eliminar(long id) {
        Pedido p = buscarPorId(id);
        p.eliminar();
    }
    
    public void editarEstadoYFormaPago(long id, Estado nuevoEstado, FormaPago nuevaFormaPago) {
        Pedido p = buscarPorId(id);
        if (nuevoEstado != null) {
            p.actualizarEstado(nuevoEstado);
        }
        if (nuevaFormaPago != null) {
            p.actualizarFormaPago(nuevaFormaPago);
        }
    }
}
