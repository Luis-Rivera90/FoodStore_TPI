/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import foodstore.enums.Rol;

/**
 *
 * @author Luis
 */
public class Usuario extends Base{
    
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasena;
    private Rol rol;
    private List<Pedido> pedidos = new ArrayList<>();
    
    //Constructor
    public Usuario(String nombre, String apellido, String mail, String celular, String contrasena, Rol rol){
        super();
        
        if(nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre del usuario no debe estar vacío.");
        }
        
        if(apellido == null || apellido.trim().isEmpty()){
            throw new IllegalArgumentException("El apellido del usuario no debe estar vacío.");
        }
        
        if(mail == null || mail.trim().isEmpty()){
            throw new IllegalArgumentException("El mail del usuario no debe estar vacío.");
        }
        
        if(celular == null || celular.trim().isEmpty()){
            throw new IllegalArgumentException("El celular del usuario no debe estar vacío.");
        }
        
        if(contrasena == null || contrasena.trim().isEmpty()){
            throw new IllegalArgumentException("El usuario debe tener una contraseña Obligatoria.");
        }
        
        this.nombre = nombre.trim();
        this.apellido = apellido.trim();
        this.mail = mail.trim();
        this.celular = celular.trim();
        this.contrasena = contrasena.trim();
        this.rol = rol;
                
    }
    
    //Getters y Setters
    public String getNombre(){
        return nombre;
    }
    
    public String getApellido(){
        return apellido;
    }
    
    public String getMail(){
        return mail;
    }
    
    public String getCelular(){
        return celular;
    }
    
    public String getContrasena(){
        return contrasena;
    }
    
    public Rol getRol(){
        return rol;
    }
    
    public List<Pedido> getPedido(){
        return Collections.unmodifiableList(pedidos);
    }
    
    //Método
    public void agregarPedido(Pedido pedido){
        if(pedido != null && !pedidos.contains(pedido)) {
            pedidos.add(pedido);
            if(pedido.getUsuario() != this){
                pedido.setUsuario(this);
            }  
        }
    }
    
    public void eliminarPedido(Pedido pedido) {
        if(pedido != null && pedidos.contains(pedido)) {
            pedidos.remove(pedido);
            if(pedido.getUsuario() == this){
                pedido.setUsuario(null);
            }
        }
    }
    
    public void actualizarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void actualizarApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
        this.apellido = apellido.trim();
    }

    public void actualizarMail(String mail) {
        if (mail == null || mail.trim().isEmpty()) {
            throw new IllegalArgumentException("El mail no puede estar vacío.");
        }
        this.mail = mail.trim();
    }

    public void actualizarCelular(String celular) {
        if (celular == null || celular.trim().isEmpty()) {
            throw new IllegalArgumentException("El celular no puede estar vacío.");
        }
        this.celular = celular.trim();
    }
    
    @Override
    public String toString() {
        return String.format("Usuario{id=%d, nombre='%s %s', mail='%s', rol=%s}",
            getId(), nombre, apellido, mail, rol);
    }
    
}

