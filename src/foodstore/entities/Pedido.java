/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import foodstore.enums.*;
import foodstore.exception.StockInvalidoException;
import foodstore.interfaces.Calculable;

/**
 *
 * @author Luis
 */
public class Pedido extends Base implements Calculable{
    
    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago formaPago;
    private List<DetallePedido> detalles;
    private Usuario usuario;
    
    public Pedido(Estado estado, FormaPago formaPago){
        super();
        
        this.fecha = LocalDate.now();
        this.estado = estado;
        this.total = 0;
        this.formaPago = formaPago;
        this.detalles = new ArrayList<>();
        
    }
    
    //Getter y Setters
    public LocalDate getFechaPedido(){
        return fecha;
    }
    
    public Estado getEstado(){
        return estado;
    }
    
    public double getTotal(){
        return total;
    }
    
    public FormaPago getFormaPago(){
        return formaPago;
    }
    
    public Usuario getUsuario(){
        return usuario;
    }
    
    public void setUsuario(Usuario usuario){
        if(this.usuario == usuario){
            return;
        }
        
        if(this.usuario != null){
            this.usuario.eliminarPedido(this);
        }
        
        this.usuario = usuario;
        
        if(usuario != null && !usuario.getPedido().contains(this)){
            usuario.agregarPedido(this);
        }    
    }
    
    public List<DetallePedido> getDetalles(){
        return Collections.unmodifiableList(detalles);
    }
    
    //Métodos
    
    public void addDetallePedido(int cantidad, Producto producto) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        if (producto.getStock() < cantidad) {
            throw new StockInvalidoException("Stock insuficiente. Stock disponible: " + producto.getStock());
        }
        detalles.add(new DetallePedido(cantidad, producto));
        producto.actualizarStock(producto.getStock() - cantidad);
        calcularTotal();
    }
    
    public void deleteDetallePedidoByProducto(Producto producto) {
        Iterator<DetallePedido> iterador = detalles.iterator();
        boolean encontrado = false;
        while(iterador.hasNext() && !encontrado) {
            DetallePedido detalle = iterador.next();
            if(detalle.getProducto().equals(producto)) {
                iterador.remove();
                encontrado = true;
            }
        }
        calcularTotal();
    }
    
    public DetallePedido findDetallePedidoByProducto(Producto producto){
        DetallePedido detalleEncontrado = null;
        Iterator<DetallePedido> iterador = this.detalles.iterator();
        while(iterador.hasNext() && detalleEncontrado == null){
            DetallePedido detalle = iterador.next();
            if(detalle.getProducto().equals(producto)){
                detalleEncontrado = detalle;    
            }
        }
        return detalleEncontrado;
    }
    
    @Override
    public void calcularTotal(){
        double acumulador = 0;
        for(DetallePedido detalle : detalles){
            acumulador = acumulador + detalle.getSubTotal();
        }
        this.total = acumulador;
    }
    
    public void actualizarEstado(Estado estado) {
        if (estado == null) throw new IllegalArgumentException("El estado no puede ser nulo.");
        this.estado = estado;
    }

    public void actualizarFormaPago(FormaPago formaPago) {
        if (formaPago == null) throw new IllegalArgumentException("La forma de pago no puede ser nula.");
        this.formaPago = formaPago;
    }
    
    @Override
    public String toString() {
        return String.format("Pedido{id=%d, fecha=%s, estado=%s, formaPago=%s, total=%.2f}",
            getId(), fecha, estado, formaPago, total);
    }   
    
}

