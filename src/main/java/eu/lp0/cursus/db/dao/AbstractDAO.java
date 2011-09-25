/*
	cursus - Race series management program
	Copyright 2011  Simon Arlott

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.lp0.cursus.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import eu.lp0.cursus.db.DatabaseSession;
import eu.lp0.cursus.db.data.AbstractEntity;

public abstract class AbstractDAO<E extends AbstractEntity> {
	protected final Class<E> clazz;

	public AbstractDAO(Class<E> clazz) {
		this.clazz = clazz;
	}

	EntityManager getEntityManager() {
		return DatabaseSession.getEntityManager();
	}

	/**
	 * Save/update transient/persisted (but not detached) entity
	 */
	public void persist(E entity) {
		DatabaseSession.getEntityManager().persist(entity);
	}

	/**
	 * Copy detached entity to an updated persisted entity, merging all changes
	 * 
	 * Merge behaviour may cause unexpected changes (override with DIY merge)
	 */
	protected E merge(E entity) {
		return DatabaseSession.getEntityManager().merge(entity);
	}

	/**
	 * Remove persisted entity
	 */
	public void remove(E entity) {
		DatabaseSession.getEntityManager().remove(entity);
	}

	/**
	 * Detach persisted entity
	 */
	public void detach(E entity) {
		// Don't detach during a transaction as there may be persisted changes not yet committed
		assert (!DatabaseSession.isActive());
		DatabaseSession.getEntityManager().detach(entity);
	}

	public E get(E entity) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<E> q = cb.createQuery(clazz);
		Root<E> s = q.from(clazz);
		q.select(s);
		q.where(cb.equal(s.get("id"), entity.getId())); //$NON-NLS-1$

		TypedQuery<E> tq = em.createQuery(q);
		return tq.getSingleResult();
	}

	public List<E> findAll() {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<E> q = cb.createQuery(clazz);
		Root<E> s = q.from(clazz);
		q.select(s);

		TypedQuery<E> tq = em.createQuery(q);
		return tq.getResultList();
	}
}