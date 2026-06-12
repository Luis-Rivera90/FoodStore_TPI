/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.entities;

/**
 *
 * @author Luis
 */
public class DetallePedido extends Base{
    
    private int cantidad;
    private double subTotal;
    private Producto producto;
    
    public DetallePedido(int cantidad, Producto producto){
        super();
        
        if(cantidad < 0){
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        
        if(producto == null){
            throw new IllegalArgumentException("No se puede crear un pedido sin productos.");
        }
        
        this.cantidad = cantidad;
        this.producto = producto;
        this.subTotal = calcularSubTotal();
    }
    
    //Getters
    public int getCantidad(){
        return cantidad;
    }
    
    public Producto getProducto(){
        return producto;
    }
    
    public double getSubTotal(){
        return subTotal;
    }
    
    //Método
    private double calcularSubTotal(){
        return producto.getPrecio() * this.cantidad;
    }

    @Override
    public String toString() {
        return String.format("DetallePedido{id=%d, producto='%s', cantidad=%d, subtotal=%.2f}",
            getId(), producto.getNombre(), cantidad, subTotal);
    }
    
    
    
    
}

