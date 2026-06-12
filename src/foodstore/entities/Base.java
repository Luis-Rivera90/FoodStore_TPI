/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstore.entities;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author Luis
 */
public abstract class Base {
    
    private long id;
    private boolean eliminado;
    private LocalDateTime createdAt;
    
    //Contructor
    public Base(){
        this.id = generarID();
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }
    
    //Getters
    public long getId(){
        return id;
    }
    
    public boolean isEliminado(){
        return eliminado;
    }
    
    public LocalDateTime getFechaCreacion(){
        return createdAt;
    }
    
    //Métodos
    private Long generarID(){
        return Math.abs(UUID.randomUUID().getMostSignificantBits());
    }
    
    public void eliminar(){
        this.eliminado = true;
    }

    public void restaurar(){
        this.eliminado = false;
    }

    @Override
    public abstract String toString();
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
            Base otro = (Base) obj;
            return getId() == otro.getId();
        }

    @Override
    public int hashCode() {
        return Long.hashCode(getId());
    }
    
}
