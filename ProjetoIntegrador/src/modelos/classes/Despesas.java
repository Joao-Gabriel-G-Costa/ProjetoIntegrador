/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.classes;

/**
 *
 * @author anaju
 */
public class Despesas {
    private int ID = 0;
    private String descricao = "";

    public Despesas() {}
    
    public Despesas(int ID, String descricao) {
        this.ID = ID;
        this.descricao = descricao;
    }
    

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
