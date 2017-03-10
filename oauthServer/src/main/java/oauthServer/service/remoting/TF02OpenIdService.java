package oauthServer.service.remoting;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.foxconn.model.FamilyAccount;
import com.foxconn.model.FamilyMembers;
import com.foxconn.model.MembersInfo;

public interface TF02OpenIdService {

	public FamilyAccount findFamilyAccoutByTelAndPassword(String tel,String password) throws NoSuchAlgorithmException;
	
	public List<FamilyMembers> findMemebersInfoByFamilyId(int familyId);
	
	public MembersInfo findMembersInfoById(int id);
}
