/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.entities;

/**
 *
 * @author Luis
 */
public class Producto extends Base{
    
    private String nombre;
    private double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Categoria categoria;
    
    public Producto(String nombre, double precio, String descripcion, int stock, String imagen){
        super();
        
        if(nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre del producto no debe estar vacío.");
        }
        
        if(precio < 0){
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        
        if(descripcion == null || descripcion.trim().isEmpty()){
            throw new IllegalArgumentException("La descripción del producto no puede estar vacía.");
        }
        
        if(stock < 0){
            throw new IllegalArgumentException("El Stock no puede ser negativo");
        }
        
        if(imagen == null || imagen.trim().isEmpty()){
            throw new IllegalArgumentException("La imagen del producto no puede estar vacía.");
        }
        
        this.nombre = nombre.trim();
        this.precio = precio;
        this.descripcion = descripcion.trim();
        this.stock = stock;
        this.imagen = imagen.trim();
        this.disponible = true;
    }
    
    //Getters & Setters
    public String getNombre(){
        return nombre;
    }
    
    public double getPrecio(){
        return precio;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public int getStock(){
        return stock;
    }
    
    public String getImagen(){
        return imagen;
    }
    
    public boolean isDisponible(){
        return disponible;
    }
    
    public void setCategoria(Categoria categoria){
        if(this.categoria == categoria){
            return;
        }
        
        if(this.categoria != null){
            this.categoria.borrarProducto(this);
        }
        
        this.categoria = categoria;
        
        if(categoria != null && !categoria.getProductos().contains(this)){
            categoria.agregarProducto(this);
        }
        
    }
    
    public Categoria getCategoria(){
        return categoria;
    }
    
    public void setStock(int stock) {
        if(stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo");
            this.stock = stock;
            cambiarDisponible();
        }   
    
    //Métodos
    public void cambiarDisponible(){
       this.disponible = this.stock > 0;
    }
    
    public void actualizarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void actualizarPrecio(double precio) {
        if (precio < 0) throw new IllegalArgumentException("El precio no puede ser negativo.");
        this.precio = precio;
    }

    public void actualizarStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo.");
        this.stock = stock;
        cambiarDisponible();
    }

    public void actualizarDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        this.descripcion = descripcion.trim();
    }

    public void actualizarImagen(String imagen) {
        if (imagen == null || imagen.trim().isEmpty()) {
            throw new IllegalArgumentException("La imagen no puede estar vacía.");
        }
        this.imagen = imagen.trim();
    }
    
    @Override
    public String toString() {
        return String.format("Producto{id=%d, nombre='%s', precio=%.2f, stock=%d, disponible=%s, categoria=%s}",
            getId(), nombre, precio, stock, disponible,
            categoria != null ? categoria.getNombre() : "Sin categoría");
    }
    
    
    
}
