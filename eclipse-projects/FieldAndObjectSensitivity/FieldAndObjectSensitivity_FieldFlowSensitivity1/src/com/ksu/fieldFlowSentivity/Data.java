package com.ksu.fieldFlowSentivity;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {
	
	public Data() {
		
	}
	
	private String mData1;
	private String mData2;
	
	public void setData1(String d) {
		this.mData1 = d;
	}
	
	public void setData2(String d) {
		this.mData2 = d;
	}
	
	public String retrieveData1(){
		return this.mData1;
	}
	
	public String retrieveData2(){
		return this.mData2;
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.mData1);
		dest.writeString(this.mData2);
	}
	
	// this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Data(Parcel in) {
        mData1 = in.readString();
        mData2 = in.readString();
    }
}
