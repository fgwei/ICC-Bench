package com.ksu.fieldFlowSentivity;

import android.content.Context;
import android.telephony.TelephonyManager;

public class SensitiveData extends Data{
	@Override
	public String retrieveData(Context context) {
		TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tel.getDeviceId(); //source
		return imei;
	}
}
