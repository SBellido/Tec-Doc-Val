package Repository;

import java.util.List;

import javax.persistence.EntityManager;
// Esta clase abstrae el comportamiento común de todos los repositorios
public abstract class Repository<T> {
	
	protected EntityManager em;
	
	public Repository(EntityManager em) {
		this.em = em;
	}

	public abstract T getById(int id);

	public void add(T o) {
		this.em.persist(o);
	}

	public void addAll(List<T> l) {
		for (T o : l) {
			this.add(o);
		}
	}

	public void update(T o) {
		this.add(o);
	}

	public void delete(T o) {
		if (em.contains(o)) {
			em.remove(o);
		} else {
			em.merge(o);
		}
	}

}