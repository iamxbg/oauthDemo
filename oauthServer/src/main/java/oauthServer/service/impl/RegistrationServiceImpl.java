package oauthServer.service.impl;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oauthServer.dao.RegistrationDao;
import oauthServer.model.Registration;
import oauthServer.service.OAuthService;

@Service
@Transactional
public class RegistrationServiceImpl implements OAuthService{

	@Autowired
	private RegistrationDao rDao;
	
	public RegistrationServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Registration> getRegistrationList() {
		// TODO Auto-generated method stub
		return rDao.findAll();
	}

	@Override
	public void doRegistration(Registration r) {
		// TODO Auto-generated method stub
		rDao.add(r);
	}
	
	

}
