package oauthServer.service.impl;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oauthServer.dao.RegistrationDao;
import oauthServer.model.Registration;
import oauthServer.service.RegistrationService;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService{

	@Autowired
	private RegistrationDao rDao;
	
	public RegistrationServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Registration> findAll() {
		// TODO Auto-generated method stub
		return rDao.findAll();
	}

	@Override
	public int add(Registration r) {
		// TODO Auto-generated method stub
		return rDao.add(r);
	}



	@Override
	public void update(Registration r) {
		// TODO Auto-generated method stub
		rDao.update(r);
	}

	@Override
	public Registration findByClientId(String client_id) {
		// TODO Auto-generated method stub
		return rDao.findByClientId(client_id);
	}

	@Override
	public Registration findById(int id) {
		// TODO Auto-generated method stub
		return rDao.findById(id);
	}


	
	

}
