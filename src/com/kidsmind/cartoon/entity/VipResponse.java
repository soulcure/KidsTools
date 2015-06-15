package com.kidsmind.cartoon.entity;

import com.kidsmind.cartoon.entity.PayResponse;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VipResponse extends PayResponse {

	private static final long serialVersionUID = -3907409059590720764L;

	// iVmall vip 套餐属性
	public static class Vip {
		public String vipGuid;
		public String vipTitle;
		public String vipDescription;
		public Double listprice; // 原价
		public Double price; // 现价
		public String partnerProductId;

		public Vip() {
		}

		public Vip(String vipGuid, String vipTitle, Double price) {
			this.vipGuid = vipGuid;
			this.vipTitle = vipTitle;
			this.price = price;
		}

		public Vip(String vipGuid, String vipTitle, Double price,
				String partnerProductId) {
			this.vipGuid = vipGuid;
			this.vipTitle = vipTitle;
			this.price = price;
			this.partnerProductId = partnerProductId;
		}

		public Vip(String vipGuid, String vipTitle, String vipDescription,
				Double listprice, Double price, String partnerProductId) {
			super();
			this.vipGuid = vipGuid;
			this.price = price;
			// this.vipGuid = "daad3f4a-61de-4342-97a4-88fdb4e1094b";
			// this.price = 0.01;
			this.vipTitle = vipTitle;
			this.vipDescription = vipDescription;
			this.listprice = listprice;

			this.partnerProductId = partnerProductId;
		}
	}

	private List<Vip> mVipList = null;
	private Vip mMonthVip; // 包年套餐
	private Vip mYearVip; // 包月套餐

	/**
	 * @return the vipList
	 */
	public List<Vip> getVipList() {
		return mVipList;
	}

	/**
	 * @param vipList
	 *            the vipList to set
	 */
	public void setVipList(List<Vip> vipList) {
		mVipList = vipList;
	}

	/**
	 * @return the monthVip
	 */
	public Vip getMonthVip() {
		return mMonthVip;
	}

	/**
	 * @param monthVip
	 *            the monthVip to set
	 */
	public void setMonthVip(Vip monthVip) {
		mMonthVip = monthVip;
	}

	/**
	 * @return the yearVip
	 */
	public Vip getYearVip() {
		return mYearVip;
	}

	/**
	 * @param yearVip
	 *            the yearVip to set
	 */
	public void setYearVip(Vip yearVip) {
		mYearVip = yearVip;
	}

	/**
	 * @param dataArray
	 *            the vipArray to set
	 */
	public void setVipList(JSONArray dataArray) {
		mVipList = new ArrayList<Vip>(dataArray.length());
		for (int i = 0; i < dataArray.length(); i++) {
			try {
				JSONObject item = dataArray.getJSONObject(i); // 得到每个对象
				Vip vip = new Vip(item.getString("vipGuid"),
						item.getString("vipTitle"),
						item.getString("vipDescription"),
						item.getDouble("listprice"), item.getDouble("price"),
						item.optString("partnerProductId")); // 可选，大麦支付必须
				mVipList.add(vip);

				// 同时确定包年/包月套餐, 包月价格不会超过50元
				if (vip.listprice < 50.0 && mMonthVip == null) {
					mMonthVip = vip;
				} else {
					mYearVip = vip;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

//	@Override
//	public VipResponse fromJsonString(String jsonString) {
//		super.fromJsonString(jsonString);
//		try {
//			if (mJsonObject == null) {
//				mJsonObject = new JSONObject(jsonString);
//			}
//
//			JSONObject data = mJsonObject.getJSONObject("data");
//			this.setVipList(data.getJSONArray("list"));
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}

//		return this;
//	}

}
