/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.uniminuto.ejb;


import co.com.uniminuto.entities.Plan;
import co.com.uniminuto.entities.UsuarioPlan;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Cristian
 */
@Stateless(name = "UsuarioPlanFacade", mappedName = "UsuarioPlanFacadeBean")
public class UsuarioPlanFacade extends AbstractFacade<UsuarioPlan> implements UsuarioPlanFacadeLocal{
    
    @PersistenceContext(unitName = "Marketplace-PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioPlanFacade() {
        super(UsuarioPlan.class);
    }

    @Override
    public UsuarioPlan merge(UsuarioPlan usuarioPlan) {
         return em.merge(usuarioPlan);
    }

    @Override
    public List<UsuarioPlan> findByUsuarioPlan(Integer idUsuario) {
        List<UsuarioPlan> lu = new ArrayList<>();
        try{
            lu = em.createNamedQuery("UsuarioPlan.findByUsuarioPlan").setParameter("idUsuario", idUsuario).getResultList();
        }catch(Exception e){
            e.getCause();
        }
        return lu;
    }
    
    @Override
    public List<Plan> findPlan(int idUser) {
        List<Plan> lu = new ArrayList<>();
        try {
            lu = em.createNamedQuery("UsuarioPlan.findByPlan").setParameter("idUsuario",idUser).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lu;
    }
    
}
