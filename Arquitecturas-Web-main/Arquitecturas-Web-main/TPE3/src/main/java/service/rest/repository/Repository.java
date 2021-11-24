package service.rest.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class Repository<Entity> {
	protected EntityManager em;
	private Class<Entity> entityClass;
	private final String GET_ALL;
	private final String DELETE_ALL;

	public Repository(EntityManager em, Class<Entity> entityClass) {
	    try {
	    	this.em = em;
	    	this.entityClass = entityClass;
	        this.GET_ALL    = "SELECT o FROM "+entityClass.getSimpleName()+" o"; 
	        this.DELETE_ALL = "DELETE   FROM "+entityClass.getSimpleName()+" o"; 
	    } catch (IllegalArgumentException e) {
	    	throw new Error("The class "+entityClass.getSimpleName()+" is not a mapped entity");
	    }
	}

	public Entity getById(int id) {
		return this.em.find(entityClass, id);
	}

	public void add(Entity e) {
		this.em.getTransaction().begin();
		this.em.persist(e);
		this.em.getTransaction().commit();
	}

	public void addAll(List<Entity> l) {
		for (Entity e : l) {
			this.add(e);
		}
	}

	public void update(Entity e) {
		this.em.getTransaction().begin();
		this.add(e);
		this.em.getTransaction().commit();
	}

	public void deleteById(int id) {
		Entity e = this.getById(id);
		if(e!=null) {
			this.delete(e);
		}
	}
	
	public void delete(Entity e) {
		this.em.getTransaction().begin();
		if (em.contains(e)) {
			em.remove(e);
		} else {
			em.merge(e);
		}
		this.em.getTransaction().commit();
	}

	public List<Entity> getAll() {
		TypedQuery<Entity> tq = this.em.createQuery(GET_ALL, entityClass);
		return tq.getResultList();
	}
	
	public void clear() {
		this.em.createQuery(DELETE_ALL);
	}

}
