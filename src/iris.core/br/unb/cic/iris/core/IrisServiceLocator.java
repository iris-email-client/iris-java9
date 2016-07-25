package br.unb.cic.iris.core;

import java.util.Iterator;
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
	
	
	public Object getService(Class<?> clazz){
		return getService(clazz, getClass().getModule().getClassLoader());
	}
	//TODO testar (usar em outro modulo carregando coisas do outro modulo, passando o classloader)
	public Object getService(Class<?> clazz, ClassLoader loader){
		ServiceLoader<?> sl = ServiceLoader.load(clazz, loader);
		Iterator<?> it = sl.iterator();

		if (!it.hasNext())
			throw new IrisUncheckedException("No service implementation found for: "+clazz);
		
		return it.next();	
	}

	private void initEmailClient(){		
		ServiceLoader<IEmailClient> sl = ServiceLoader.load(IEmailClient.class);
		Iterator<IEmailClient> it = sl.iterator();

		if (!it.hasNext())
			throw new IrisUncheckedException("No mail client found!");
		
		client = it.next();
		System.out.println("Email client: "+client.getClass().getCanonicalName());
	}
	
	private void initEntityFactory(){		
		ServiceLoader<EntityFactory> sl = ServiceLoader.load(EntityFactory.class);
		Iterator<EntityFactory> it = sl.iterator();

		if (!it.hasNext())
			throw new IrisUncheckedException("No entity factory found!");
		
		entityFactory = it.next();
		System.out.println("Entity Factory: "+entityFactory.getClass().getCanonicalName());
	}
	
	private void initDaoFactory() {		
		ServiceLoader<DAOFactory> sl = ServiceLoader.load(DAOFactory.class);
		Iterator<DAOFactory> it = sl.iterator();

		if (!it.hasNext())
			throw new IrisUncheckedException("No DAO factory found!");
		
		daoFactory = it.next();
		System.out.println("DAO Factory: "+daoFactory.getClass().getCanonicalName());
	}
	
	
}