/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.uniminuto.ejb;

import co.com.uniminuto.entities.Plan;
import co.com.uniminuto.entities.UsuarioPlan;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Cristian
 */
@Local
public interface UsuarioPlanFacadeLocal {
    
    void create(UsuarioPlan usuarioPlan);

    void edit(UsuarioPlan usuarioPlan);
    
    UsuarioPlan merge(UsuarioPlan usuarioPlan);

    void remove(UsuarioPlan usuarioPlan);
    
    List<UsuarioPlan> findByIdUsuario(Integer idUsuario);
    
     List<Plan> findPlan(int idUser);
    
}
