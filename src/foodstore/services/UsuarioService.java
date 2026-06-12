/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.services;

import foodstore.entities.Usuario;
import foodstore.exception.DatoInvalidoException;
import foodstore.exception.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author Luis
 */
public class UsuarioService {

    private List<Usuario> usuarios = new ArrayList<>();

    public void agregar(Usuario usuario) {
        for (Usuario u : usuarios) {
            if (!u.isEliminado() && u.getMail().equalsIgnoreCase(usuario.getMail())) {
                throw new DatoInvalidoException("Ya existe un usuario con ese mail.");
            }
        }
        usuarios.add(usuario);
    }

    public List<Usuario> listarActivos() {
        List<Usuario> activos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) {
                activos.add(u);
            }
        }
        return Collections.unmodifiableList(activos);
    }

    public Usuario buscarPorId(long id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id && !u.isEliminado()) {
                return u;
            }
        }
        throw new EntidadNoEncontradaException("Usuario con id " + id + " no encontrado.");
    }

    public void eliminar(long id) {
        Usuario u = buscarPorId(id);
        u.eliminar();
    }
    
    public void editar(long id, String nuevoNombre, String nuevoApellido,String nuevoMail, String nuevoCelular) {
        Usuario u = buscarPorId(id);
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            u.actualizarNombre(nuevoNombre);
        }
        if (nuevoApellido != null && !nuevoApellido.trim().isEmpty()) {
            u.actualizarApellido(nuevoApellido);
        }
        if (nuevoMail != null && !nuevoMail.trim().isEmpty()) {
            for (Usuario usr : usuarios) {
                if (!usr.isEliminado() && usr.getId() != id && 
                    usr.getMail().equalsIgnoreCase(nuevoMail)) {
                    throw new DatoInvalidoException("Ya existe un usuario con ese mail.");
                }
            }
            u.actualizarMail(nuevoMail);
        }
        if (nuevoCelular != null && !nuevoCelular.trim().isEmpty()) {
            u.actualizarCelular(nuevoCelular);
        }
    }
}
