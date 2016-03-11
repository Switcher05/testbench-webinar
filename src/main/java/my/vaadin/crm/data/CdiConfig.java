package my.vaadin.crm.data;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CdiConfig {
	@Produces
	@Dependent
	@PersistenceContext(unitName = "demodb")
	public EntityManager entityManager;
}
