/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.services;

import foodstore.entities.Categoria;
import foodstore.exception.EntidadNoEncontradaException;
import foodstore.exception.DatoInvalidoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 * @author Luis
 */
public class CategoriaService {
    
    private List<Categoria> categorias = new ArrayList<>();
    
    public void agregar(Categoria categoria){
        for(Categoria c : categorias){
            if(!c.isEliminado() && c.getNombre().equalsIgnoreCase(categoria.getNombre())){
                throw new DatoInvalidoException("Ya existe una categoría con ese nombre.");
            }
        }
        categorias.add(categoria);
    }
    
    public List<Categoria> listarActivas() {
        List<Categoria> activas = new ArrayList<>();
        for (Categoria c : categorias) {
            if (!c.isEliminado()) {
                activas.add(c);
            }
        }
        return Collections.unmodifiableList(activas);
    }
    
    public Categoria buscarPorId(long id) {
        for (Categoria c : categorias) {
            if (c.getId() == id && !c.isEliminado()) {
                return c;
            }
        }
        throw new EntidadNoEncontradaException("Categoría con id " + id + " no encontrada.");
    }
    
    public void eliminar(long id) {
        Categoria c = buscarPorId(id);
        c.eliminar();
    }
    
    public void editar(long id, String nuevoNombre, String nuevaDescripcion) {
        Categoria c = buscarPorId(id);
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            for (Categoria cat : categorias) {
                if (!cat.isEliminado() && cat.getId() != id && 
                    cat.getNombre().equalsIgnoreCase(nuevoNombre)) {
                    throw new DatoInvalidoException("Ya existe una categoría con ese nombre.");
                }
            }
            c.actualizarNombre(nuevoNombre);
        }
        if (nuevaDescripcion != null && !nuevaDescripcion.trim().isEmpty()) {
            c.actualizarDescripcion(nuevaDescripcion);
        }
    }
    
}
