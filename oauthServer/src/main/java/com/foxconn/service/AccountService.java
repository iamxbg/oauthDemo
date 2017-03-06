package com.foxconn.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.foxconn.model.FamilyAccount;
import com.foxconn.model.FamilyMembers;
import com.foxconn.model.MembersInfo;

public interface AccountService {

	public MembersInfo getMemberInfoById(int id);
	
	public FamilyAccount getFamilyAccountByTelAndPassword(String tel,String password) throws NoSuchAlgorithmException;
	
	public List<FamilyMembers> findMembersInfoByFamilyId(int id);
	

	
}