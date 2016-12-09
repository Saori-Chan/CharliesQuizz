package charlies.generators;

import java.util.List;

import charlies.datastore.DatastoreManager;
import charlies.exceptions.NoResultException;
import charlies.sections.QuizzSection;

public abstract class SectionGenerator {
	
	protected int nb;
	protected DatastoreManager datastore;
	
	public SectionGenerator(int nb, DatastoreManager datastore) {
		this.nb = nb;
		this.datastore = datastore;
	}

	public abstract List<QuizzSection> generate(int nb) throws NoResultException;

}
