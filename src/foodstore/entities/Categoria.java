/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Luis
 */
public class Categoria extends Base{
    
    private String nombre;
    private String descripcion;
    private List<Producto> productos;
    
    //Constructor
    public Categoria(String nombre, String descripcion){
        super();
        
        if(nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacio");
        }
        
        if(descripcion == null || descripcion.trim().isEmpty()){
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        
        this.nombre = nombre.trim();
        this.descripcion = descripcion.trim();
        this.productos = new ArrayList<>();
    }
    
    //Getters
    public String getNombre(){
        return nombre;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public List<Producto> getProductos(){
        return Collections.unmodifiableList(productos);   
    }
    
    //Métodos
    
    public void agregarProducto(Producto producto){
        if(producto != null && !productos.contains(producto)){
            productos.add(producto);
            producto.setCategoria(this);
        }
    }
    
    public void borrarProducto(Producto producto){
        if(producto != null && productos.contains(producto)){
            productos.remove(producto);
            producto.setCategoria(null);
        }
    }
    
    public void actualizarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void actualizarDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        this.descripcion = descripcion.trim();
    }

    @Override
    public String toString() {
        return "Categoria{" + "Nombre = " + nombre + ", Descripción = " + descripcion + '}';
    }
    
    
}

