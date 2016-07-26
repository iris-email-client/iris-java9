package br.unb.cic.iris.core;

import java.util.Iterator;
import java.util.Optional;
import java.util.ServiceLoader;

import br.unb.cic.iris.exception.IrisUncheckedException;
import br.unb.cic.iris.mail.IEmailClient;
import br.unb.cic.iris.model.EntityFactory;
import br.unb.cic.iris.persistence.DAOFactory;

public class IrisServiceLocator {
	private static IrisServiceLocator instance = new IrisServiceLocator();
	
	private IEmailClient client;	
	private EntityFactory entityFactory;
	private DAOFactory daoFactory;
	
	private IrisServiceLocator(){
		initEntityFactory();
		initDaoFactory();
		initEmailClient();
	}
	
	public static IrisServiceLocator instance(){
		return instance;
	}
	
		
	public IEmailClient getEmailClient() {
		return client;
	}

	public EntityFactory getEntityFactory() {
		return entityFactory;
	}

	public DAOFactory getDaoFactory() {
		return daoFactory;
	}
	
	
	private Optional<?> getService(Class<?> clazz){		
		return getService(clazz, getClass().getModule().getClassLoader());
	}
	//TODO testar (usar em outro modulo carregando coisas do outro modulo, passando o classloader)
	public static Optional<?> getService(Class<?> clazz, ClassLoader loader){
		ServiceLoader<?> sl = ServiceLoader.load(clazz, loader);
		Iterator<?> it = sl.iterator();

		Optional<?> op = Optional.empty();		
		if (it.hasNext()){
			op = Optional.of(it.next());
		}
		
		return op;	
	}


	private void initEmailClient(){		
		client = (IEmailClient) getService(IEmailClient.class)
				.orElseThrow(() -> new IrisUncheckedException("No mail client found!"));
		System.out.println("Email client: "+client.getClass().getCanonicalName());
	}
	
	private void initEntityFactory(){		
		entityFactory = (EntityFactory) getService(EntityFactory.class)
				.orElseThrow(() -> new IrisUncheckedException("No entity factory found!"));
		System.out.println("Entity Factory: "+entityFactory.getClass().getCanonicalName());
	}
	
	private void initDaoFactory() {		
		daoFactory = (DAOFactory) getService(DAOFactory.class)
				.orElseThrow(() -> new IrisUncheckedException("No DAO factory found!"));
		System.out.println("DAO Factory: "+daoFactory.getClass().getCanonicalName());
		/*ServiceLoader<DAOFactory> sl = ServiceLoader.load(DAOFactory.class);
		Iterator<DAOFactory> it = sl.iterator();

		if (!it.hasNext())
			throw new IrisUncheckedException("No DAO factory found!");
		
		daoFactory = it.next();*/		
	}
		
}
