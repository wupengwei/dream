package com.wpw.solr.test;

import java.io.Serializable;

/**
 * @description:供求索引实体类
 * @projectName:sososteel-index-api
 * @className:SupplyDemandIndex.java
 * @author:钢联搜搜钢项目组 张东东
 * @createTime:2016年2月15日 下午5:00:25
 * @version 1.0
 */
public class SupplyDemandIndex
{

	private String id;
	private Integer supplyType;							// 类型 1供应2低价供应3求购4紧急求购
	private String pubcompanyName;						// 发布商家名
	private Long breedId;								// 品种ID
	private String breedName;							// 品种
	private String title;								// 标题
	private String prodPhotoFirstUrl;					// 第一张图片路径
	private String prodPhotoSecUrl;						// 第二张图片路径
	private String prodPhotoThirdUrl;					// 第三张图片路径
	private String prodPhotoForUrl;						// 第四张图片路径
	private Double price;								// 单价
	private Double prodNum;								// 数量
	private String unit;								// 单位
	private Double minquan;								// 起订数量
	private String payMethod;							// 付款方式: 全款、其它
	private String tranMethod;							// 交易方式：自提、送货上门
	private String companyCountry;						// 注册地国家
	private String companyProvince;						// 注册地省份
	private String companyCity;							// 注册地城市
	private String companyCounty;						// 注册地县
	private String spec;								// 规格
	private String material;							// 材质
	private String factory;								// 产地/厂家
	private String detailInfo;							// 详细信息
	private String appname;								// 申请人姓名
	private String companyName;							// 公司名称
	private String phone;								// 固话
	private String mobile;								// 手机
	private String fax;									// 传真
	private String email;								// 电子邮箱
	private String steellApp;							// 是否同步手机0否1是
	private String createName;							// 发布人
	private Long createTime;							// 发布时间
	private Integer dealFlag;							// 是否成交
	private Integer status;								// 状态(0待审核1已通过2已拒绝)
	private Long adminId;								// 管理人ID
	private String adminName;							// 管理人名称
	private String adminPhone;							// 管理员联系电话
	private Long checkId;								// 审核人ID
	private String checkName;							// 审核人名
	private Long checkTime;								// 审核时间
	private Integer clkNum;								// 点击数
	private Long memberId;								// 会员代码
	private Long refTime;								// 刷新时间
	private Long createId;								// 发布人id
	private Long gxtAge;								// 钢信通年限
	private Long gxtIntegral;							// 钢信通积分
	private Boolean topHad;								// 是否百强
	private Boolean topKind;							// 是否标王1是0否
	private Long dealTime;								// 成交时间
	private String transPrice;							// 成交价格
	private String transNum;							// 成交数量
	private Long expiryTime;							// 钢信通过期时间

	/* 压缩小图片 */
	private String prodphotoFirsturlSmall;
	private String prodphotoSecurlSmall;
	private String prodphotoThirdurlSmall;
	private String prodphotoForurlSmall;

	/* 压缩小图片手机版路径 */
	private String prodphotoFirsturlPhone;
	private String prodphotoSecurlPhone;
	private String prodphotoThirdurlPhone;
	private String prodphotoForurlPhone;

	public Integer getSupplyType()
	{
		return supplyType;
	}

	public void setSupplyType(Integer supplyType)
	{
		this.supplyType = supplyType;
	}

	public String getPubcompanyName()
	{
		return pubcompanyName;
	}

	public void setPubcompanyName(String pubcompanyName)
	{
		this.pubcompanyName = pubcompanyName;
	}

	public Long getBreedId()
	{
		return breedId;
	}

	public void setBreedId(Long breedId)
	{
		this.breedId = breedId;
	}

	public String getBreedName()
	{
		return breedName;
	}

	public void setBreedName(String breedName)
	{
		this.breedName = breedName;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getProdPhotoFirstUrl()
	{
		return prodPhotoFirstUrl;
	}

	public void setProdPhotoFirstUrl(String prodPhotoFirstUrl)
	{
		this.prodPhotoFirstUrl = prodPhotoFirstUrl;
	}

	public String getProdPhotoSecUrl()
	{
		return prodPhotoSecUrl;
	}

	public void setProdPhotoSecUrl(String prodPhotoSecUrl)
	{
		this.prodPhotoSecUrl = prodPhotoSecUrl;
	}

	public String getProdPhotoThirdUrl()
	{
		return prodPhotoThirdUrl;
	}

	public void setProdPhotoThirdUrl(String prodPhotoThirdUrl)
	{
		this.prodPhotoThirdUrl = prodPhotoThirdUrl;
	}

	public String getProdPhotoForUrl()
	{
		return prodPhotoForUrl;
	}

	public void setProdPhotoForUrl(String prodPhotoForUrl)
	{
		this.prodPhotoForUrl = prodPhotoForUrl;
	}

	public Double getPrice()
	{
		return price;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}

	public Double getProdNum()
	{
		return prodNum;
	}

	public void setProdNum(Double prodNum)
	{
		this.prodNum = prodNum;
	}

	public String getUnit()
	{
		return unit;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	public Double getMinquan()
	{
		return minquan;
	}

	public void setMinquan(Double minquan)
	{
		this.minquan = minquan;
	}

	public String getPayMethod()
	{
		return payMethod;
	}

	public void setPayMethod(String payMethod)
	{
		this.payMethod = payMethod;
	}

	public String getTranMethod()
	{
		return tranMethod;
	}

	public void setTranMethod(String tranMethod)
	{
		this.tranMethod = tranMethod;
	}

	public String getCompanyCountry()
	{
		return companyCountry;
	}

	public void setCompanyCountry(String companyCountry)
	{
		this.companyCountry = companyCountry;
	}

	public String getCompanyProvince()
	{
		return companyProvince;
	}

	public void setCompanyProvince(String companyProvince)
	{
		this.companyProvince = companyProvince;
	}

	public String getCompanyCity()
	{
		return companyCity;
	}

	public void setCompanyCity(String companyCity)
	{
		this.companyCity = companyCity;
	}

	public String getCompanyCounty()
	{
		return companyCounty;
	}

	public void setCompanyCounty(String companyCounty)
	{
		this.companyCounty = companyCounty;
	}

	public String getSpec()
	{
		return spec;
	}

	public void setSpec(String spec)
	{
		this.spec = spec;
	}

	public String getMaterial()
	{
		return material;
	}

	public void setMaterial(String material)
	{
		this.material = material;
	}

	public String getFactory()
	{
		return factory;
	}

	public void setFactory(String factory)
	{
		this.factory = factory;
	}

	public String getDetailInfo()
	{
		return detailInfo;
	}

	public void setDetailInfo(String detailInfo)
	{
		this.detailInfo = detailInfo;
	}

	public String getAppname()
	{
		return appname;
	}

	public void setAppname(String appname)
	{
		this.appname = appname;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getFax()
	{
		return fax;
	}

	public void setFax(String fax)
	{
		this.fax = fax;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getSteellApp()
	{
		return steellApp;
	}

	public void setSteellApp(String steellApp)
	{
		this.steellApp = steellApp;
	}

	public String getCreateName()
	{
		return createName;
	}

	public void setCreateName(String createName)
	{
		this.createName = createName;
	}

	public Long getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Long createTime)
	{
		this.createTime = createTime;
	}

	public Integer getDealFlag()
	{
		return dealFlag;
	}

	public void setDealFlag(Integer dealFlag)
	{
		this.dealFlag = dealFlag;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Long getAdminId()
	{
		return adminId;
	}

	public void setAdminId(Long adminId)
	{
		this.adminId = adminId;
	}

	public String getAdminName()
	{
		return adminName;
	}

	public void setAdminName(String adminName)
	{
		this.adminName = adminName;
	}

	public String getAdminPhone()
	{
		return adminPhone;
	}

	public void setAdminPhone(String adminPhone)
	{
		this.adminPhone = adminPhone;
	}

	public Long getCheckId()
	{
		return checkId;
	}

	public void setCheckId(Long checkId)
	{
		this.checkId = checkId;
	}

	public String getCheckName()
	{
		return checkName;
	}

	public void setCheckName(String checkName)
	{
		this.checkName = checkName;
	}

	public Long getCheckTime()
	{
		return checkTime;
	}

	public void setCheckTime(Long checkTime)
	{
		this.checkTime = checkTime;
	}

	public Integer getClkNum()
	{
		return clkNum;
	}

	public void setClkNum(Integer clkNum)
	{
		this.clkNum = clkNum;
	}

	public Long getMemberId()
	{
		return memberId;
	}

	public void setMemberId(Long memberId)
	{
		this.memberId = memberId;
	}

	public Long getRefTime()
	{
		return refTime;
	}

	public void setRefTime(Long refTime)
	{
		this.refTime = refTime;
	}

	public Long getCreateId()
	{
		return createId;
	}

	public void setCreateId(Long createId)
	{
		this.createId = createId;
	}

	public Long getGxtAge()
	{
		return gxtAge;
	}

	public void setGxtAge(Long gxtAge)
	{
		this.gxtAge = gxtAge;
	}

	public Long getGxtIntegral()
	{
		return gxtIntegral;
	}

	public void setGxtIntegral(Long gxtIntegral)
	{
		this.gxtIntegral = gxtIntegral;
	}

	public Boolean getTopHad()
	{
		return topHad;
	}

	public void setTopHad(Boolean topHad)
	{
		this.topHad = topHad;
	}

	public Boolean getTopKind()
	{
		return topKind;
	}

	public void setTopKind(Boolean topKind)
	{
		this.topKind = topKind;
	}

	public Long getDealTime()
	{
		return dealTime;
	}

	public void setDealTime(Long dealTime)
	{
		this.dealTime = dealTime;
	}

	public String getTransPrice()
	{
		return transPrice;
	}

	public void setTransPrice(String transPrice)
	{
		this.transPrice = transPrice;
	}

	public String getTransNum()
	{
		return transNum;
	}

	public void setTransNum(String transNum)
	{
		this.transNum = transNum;
	}

	public Long getExpiryTime()
	{
		return expiryTime;
	}

	public void setExpiryTime(Long expiryTime)
	{
		this.expiryTime = expiryTime;
	}

	public String getProdphotoFirsturlSmall()
	{
		return prodphotoFirsturlSmall;
	}

	public void setProdphotoFirsturlSmall(String prodphotoFirsturlSmall)
	{
		this.prodphotoFirsturlSmall = prodphotoFirsturlSmall;
	}

	public String getProdphotoSecurlSmall()
	{
		return prodphotoSecurlSmall;
	}

	public void setProdphotoSecurlSmall(String prodphotoSecurlSmall)
	{
		this.prodphotoSecurlSmall = prodphotoSecurlSmall;
	}

	public String getProdphotoThirdurlSmall()
	{
		return prodphotoThirdurlSmall;
	}

	public void setProdphotoThirdurlSmall(String prodphotoThirdurlSmall)
	{
		this.prodphotoThirdurlSmall = prodphotoThirdurlSmall;
	}

	public String getProdphotoForurlSmall()
	{
		return prodphotoForurlSmall;
	}

	public void setProdphotoForurlSmall(String prodphotoForurlSmall)
	{
		this.prodphotoForurlSmall = prodphotoForurlSmall;
	}

	public String getProdphotoFirsturlPhone()
	{
		return prodphotoFirsturlPhone;
	}

	public void setProdphotoFirsturlPhone(String prodphotoFirsturlPhone)
	{
		this.prodphotoFirsturlPhone = prodphotoFirsturlPhone;
	}

	public String getProdphotoSecurlPhone()
	{
		return prodphotoSecurlPhone;
	}

	public void setProdphotoSecurlPhone(String prodphotoSecurlPhone)
	{
		this.prodphotoSecurlPhone = prodphotoSecurlPhone;
	}

	public String getProdphotoThirdurlPhone()
	{
		return prodphotoThirdurlPhone;
	}

	public void setProdphotoThirdurlPhone(String prodphotoThirdurlPhone)
	{
		this.prodphotoThirdurlPhone = prodphotoThirdurlPhone;
	}

	public String getProdphotoForurlPhone()
	{
		return prodphotoForurlPhone;
	}

	public void setProdphotoForurlPhone(String prodphotoForurlPhone)
	{
		this.prodphotoForurlPhone = prodphotoForurlPhone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}