package org.eclipse.repositories;

import java.util.List;

public interface GenericDAO<Model, PK> {
	List<Model> findAll();

	Model findById(PK id);

	Model save(Model model);

	Model update(Model model);

	boolean remove(PK id);
}
