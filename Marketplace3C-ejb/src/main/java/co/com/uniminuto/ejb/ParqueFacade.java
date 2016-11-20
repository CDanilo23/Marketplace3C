/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.uniminuto.ejb;

import co.com.uniminuto.entities.Parque;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author pc
 */
@Stateless
public class ParqueFacade extends AbstractFacade<Parque> implements ParqueFacadeLocal {

    @PersistenceContext(unitName = "Marketplace-PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParqueFacade() {
        super(Parque.class);
    }

    @Override
    public List<Parque> listarParques() {
        List<Parque> lp = new ArrayList<>();
        lp = em.createNamedQuery("Parque.findAll",Parque.class).getResultList();
        return lp;
    }

    public void crear(Parque parque) {
        super.create(parque);
    }

    public void editar(Parque parque) {
        super.edit(parque);
    }

    public Parque encontrar(Integer id) {
        return super.find(id);
    }

}
