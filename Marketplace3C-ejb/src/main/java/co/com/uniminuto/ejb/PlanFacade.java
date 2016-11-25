/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.uniminuto.ejb;

import co.com.uniminuto.entities.Plan;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cristian.ordonez
 */
@Stateless
public class PlanFacade extends AbstractFacade<Plan> implements PlanFacadeLocal {

    @PersistenceContext(unitName = "Marketplace-PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanFacade() {
        super(Plan.class);
    }
    
    public Plan merge(Plan plan){
        return em.merge(plan);
    }

    @Override
    public List<Plan> findPlanById(Integer idPlan) {
         List<Plan> lp = new ArrayList<>();
        try {
            lp = em.createNamedQuery("Plan.findByIdPlan").setParameter("idPlan", idPlan).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lp;
    }
}
