package com.vd.util;

public class RemoteMAC {
	public static String getMAC(String licence,String pcInfo,String timestamp) {
		licence+=licence.length();
		pcInfo+=pcInfo.length();
		timestamp+=timestamp.length();
		String tmp = encrypt(encrypt(licence, pcInfo),timestamp);
		tmp+=licence.length()*pcInfo.length()*timestamp.length();
		return MD5.getMD5(tmp.getBytes());
	}
	public static String encrypt(String strOld, String strKey) {
		byte[] data = strOld.getBytes();
		byte[] keyData = strKey.getBytes();
		int keyIndex = 0 ;
		for(int x = 0 ; x < strOld.length() ; x++) {
			data[x] = (byte)(data[x] ^ keyData[keyIndex]);
			if (++keyIndex == keyData.length){
				keyIndex = 0;
			}
		}
		return new String(data);
	} 
}
