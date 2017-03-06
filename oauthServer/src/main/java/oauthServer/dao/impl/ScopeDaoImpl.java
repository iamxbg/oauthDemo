package oauthServer.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import oauthServer.dao.ScopeDao;
import oauthServer.model.Scope;
import oauthServer.model.ServiceClientId;

@Repository
public class ScopeDaoImpl implements ScopeDao{
	
	@Autowired
	private SessionFactory sf;

	public ScopeDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Scope> getScopesByClientId(int cid) {
		// TODO Auto-generated method stub
		return sf.getCurrentSession().createQuery("form Scope scp inner join ClientScope cs on scp.id=cs.csId.sid where cs.cdId.cid=:cid")
				.setInteger("cid", cid).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Scope> getScopesBydServiceId(int sid) {
		// TODO Auto-generated method stub
		return sf.getCurrentSession().createQuery("form Scope scp where scp.sid=:sid")
				.setInteger("sid", sid).list();
	}

	@Override
	public Scope findById(int id) {
		// TODO Auto-generated method stub
		return (Scope) sf.getCurrentSession().createQuery("from Scope scp where scp.id=:id")
				.setInteger("id", id).uniqueResult();
	}

}
