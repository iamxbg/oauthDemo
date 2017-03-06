package oauthServer.remoting;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.foxconn.model.FamilyAccount;
import com.foxconn.model.FamilyMembers;
import com.foxconn.model.MembersInfo;
import com.foxconn.service.AccountService;

import oauthServer.config.RootConfig;
import oauthServer.config.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RootConfig.class,WebConfig.class})
@WebAppConfiguration
public class RemotingTest {

	@Autowired
	private AccountService accountService;
	
	//@Test
	public void testNotNull(){
		Assert.assertNotNull(accountService);
	}
	
	//@Test
	public void testExec(){
		
		MembersInfo info=accountService.getMemberInfoById(2);
		Assert.assertNotNull(info);
		System.out.println(info.getMembersname());
		//factoryBean.invoke(methodInvocation)
	}
	
	@Test
	public void testFindMembersInfoByFamilyId() throws NoSuchAlgorithmException{
		String password="dudeisdude";
		String tel="15555555555";
		FamilyAccount fa=accountService.getFamilyAccountByTelAndPassword(tel, password);
		
		System.out.println("family:"+fa.getFamily());

		List<FamilyMembers> fmList=accountService.findMembersInfoByFamilyId(fa.getId());
		for(FamilyMembers fm: fmList){
			System.out.println("mid:"+fm.getMemberId()+" nickname:"+fm.getNickname());
			MembersInfo mi=accountService.getMemberInfoById(Integer.parseInt(fm.getMemberId()));
			System.out.println("membersname:"+mi.getMembersname());
		}
		
	}
}
