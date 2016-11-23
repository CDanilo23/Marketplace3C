/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.uniminuto.ejb;


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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UsuarioPlan> findByIdUsuario(Integer idUsuario) {
        List<UsuarioPlan> lu = new ArrayList<>();
        try{
            lu = em.createNamedQuery("UsuarioPlan.findByIdUsuario").setParameter("idUsuario", idUsuario).getResultList();
        }catch(Exception e){
            e.getCause();
        }
        return lu;
    }
    
}
