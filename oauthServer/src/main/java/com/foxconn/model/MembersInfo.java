package com.foxconn.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.foxconn.utils.CustomDateSerializer;


public class MembersInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String membersname;

    private Byte sex;

    private String idType;

    private String idCard;

    private String tel;

    private String picture;

    private String bloodType;

    private String marryStatus;

    private String job;
    
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    private String socialCard;

    private String address;

    private String home;
    
    private String height;
    
    private String history;
    
    private String allergic;
    
    private String family;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private Byte delflag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMembersname() {
        return membersname;
    }

    public void setMembersname(String membersname) {
        this.membersname = membersname == null ? null : membersname.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType == null ? null : idType.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType == null ? null : bloodType.trim();
    }

    public String getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(String marryStatus) {
        this.marryStatus = marryStatus == null ? null : marryStatus.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }
    @JsonSerialize(using=CustomDateSerializer.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSocialCard() {
        return socialCard;
    }

    public void setSocialCard(String socialCard) {
        this.socialCard = socialCard == null ? null : socialCard.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home == null ? null : home.trim();
    }
    
    
    public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	
	public String getAllergic() {
		return allergic;
	}

	public void setAllergic(String allergic) {
		this.allergic = allergic;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	@JsonSerialize(using=CustomDateSerializer.class)
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Byte getDelflag() {
        return delflag;
    }

    public void setDelflag(Byte delflag) {
        this.delflag = delflag;
    }

	@Override
	public String toString() {
		return "MembersInfo [address=" + address + ", allergic=" + allergic
				+ ", birthday=" + birthday + ", bloodType=" + bloodType
				+ ", createtime=" + createtime + ", delflag=" + delflag
				+ ", family=" + family + ", height=" + height + ", history="
				+ history + ", home=" + home + ", id=" + id + ", idCard="
				+ idCard + ", idType=" + idType + ", job=" + job
				+ ", marryStatus=" + marryStatus + ", membersname="
				+ membersname + ", picture=" + picture + ", sex=" + sex
				+ ", socialCard=" + socialCard + ", tel=" + tel
				+ ", getAddress()=" + getAddress() + ", getAllergic()="
				+ getAllergic() + ", getBirthday()=" + getBirthday()
				+ ", getBloodType()=" + getBloodType() + ", getCreatetime()="
				+ getCreatetime() + ", getDelflag()=" + getDelflag()
				+ ", getFamily()=" + getFamily() + ", getHeight()="
				+ getHeight() + ", getHistory()=" + getHistory()
				+ ", getHome()=" + getHome() + ", getId()=" + getId()
				+ ", getIdCard()=" + getIdCard() + ", getIdType()="
				+ getIdType() + ", getJob()=" + getJob()
				+ ", getMarryStatus()=" + getMarryStatus()
				+ ", getMembersname()=" + getMembersname() + ", getPicture()="
				+ getPicture() + ", getSex()=" + getSex()
				+ ", getSocialCard()=" + getSocialCard() + ", getTel()="
				+ getTel() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}


    
}