/*******************************************************************************
 *
 * Copyright (c) 2010 Koninklijke Philips Electronics N.V. All rights reserved.
 *
 * This source code and any compilation or derivative thereof is the proprietary
 * information of Koninklijke Philips Electronics N.V. and is confidential in
 * nature.
 *
 * Under no circumstances is this software to be combined with any Open Source
 * Software in any way or placed under an Open Source License of any type
 * without the express written permission of Koninklijke Philips Electronics
 * N.V.
 *
 * Developed and Maintained by Philips Consumer Lifestyle High Impact Innovation
 * Center Eindhoven - The Netherlands
 *
 *******************************************************************************/
package org.droidtv.tv.dlna;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * UPnP Transforms
 *
 * class that can describes RendererSide Content Transforms.
 * 
 * @author Wouter van der Beek
 */
public class UPnPTransform implements Parcelable, IUPnPTransform {
	private static final String TAG = "DLNA/UPnPTransform";
	private String mName;
	private String mFriendlyName;
	private String mUnit;
	private int mMinVal;
	private int mMaxVal;
	private int mStepSize;
	private int mInActiveVal;
	private int mError;

	// !< parameter id: Transform Name
	public static final int TRANSFORM_NAME = 0;
	// !< parameter id: Friendly name of the transform
	public static final int TRANSFORM_FRIENDLY_NAME = 1;
	// !< parameter id: min-value of the transform
	public static final int MINIMUM_VALUE = 2;
	// !< parameter id: max-value of the transform
	public static final int MAXIMUM_VALUE = 3;
	// !< parameter id: step-value of the transform
	public static final int STEP_SIZE = 4;
	// !< parameter id: inactive-value of the transform
	public static final int INACTIVE_VALUE = 5;
	// !< parameter id: error code
	public static final int ERROR = 6;
	// !< parameter id: unit of the transform
	public static final int TRANSFORM_UNIT = 7;

	/**
	 * Constructor
	 */
	public UPnPTransform() {
		mName = "";
		mFriendlyName = "";
		mMinVal = 0;
		mMaxVal = 0;
		mStepSize = 0;
		mInActiveVal = 0;
		mError = 0;
		mUnit = "";

	}

	/**
	 * Constructor
	 * 
	 * @param in in parcel from C code
	 */
	public UPnPTransform(Parcel in) {
		readFromParcel(in);
	}

	/**
	 * Constructor
	 * 
	 * @param _Name the standardised name of the transform
	 * @param _FriendlyName the friendly name of the transform
	 * @param _ValueList the value list of the transform
	 */
	public UPnPTransform(String _Name, String _FriendlyName, String _unit, int _MinValue, int _MaxValue, int _StepSize,
                         int _InactiveValue, int _Error) {

		mName = _Name;
		mFriendlyName = _FriendlyName;
		mUnit = _unit;
		mMinVal = _MinValue;
		mMaxVal = _MaxValue;
		mStepSize = _StepSize;
		mInActiveVal = _InactiveValue;
		mError = _Error;
		if (mName == null) {
			mName = "";
		}
		if (mFriendlyName == null) {
			mFriendlyName = "";
		}
		if (mUnit == null) {
			mUnit = "";
		}
	}

	/**
	 * sets a string value of the transform
	 * 
	 * @param id the parameter id
	 * @param value the value to set
	 */
	public void setStringValue(int id, String value) {
		switch (id) {
		case TRANSFORM_NAME:
			mName = value;
			if (mName == null) {
				mName = "";
			}
			break;
		case TRANSFORM_FRIENDLY_NAME:
			mFriendlyName = value;
			if (mFriendlyName == null) {
				mFriendlyName = "";
			}
			break;
		case TRANSFORM_UNIT:
			mUnit = value;
			if (mUnit == null) {
				mUnit = "";
			}
			break;
		default:
			Log.v(TAG, "setStringValue default case");
			break;
		}
	}

	/**
	 * retrieves a string value of the transform
	 * 
	 * @param id the parameter id
	 * @return the value of the parameter
	 */
	public String getStringValue(int id) {
		String value = null;

		switch (id) {
		case TRANSFORM_NAME:
			value = mName;
			break;
		case TRANSFORM_FRIENDLY_NAME:
			value = mFriendlyName;
			break;
		case TRANSFORM_UNIT:
			value = mUnit;
			break;
		default:
			Log.v(TAG, "setStringValue default case");
			value = "";
			break;
		}

		return value;
	}

	public int describeContents() {
		return 0;
	}

	public void setIntValue(int id, int value) {
		switch (id) {
		case MINIMUM_VALUE:
			mMinVal = value;
			break;
		case MAXIMUM_VALUE:
			mMaxVal = value;
			break;
		case STEP_SIZE:
			mStepSize = value;
			break;
		case INACTIVE_VALUE:
			mInActiveVal = value;
			break;
		case ERROR:
			mError = value;
			break;
		default:
			Log.v(TAG, "Default case");
			break;
		}

	}

	public int getIntValue(int id) {
		int value = 0;
		switch (id) {
		case MINIMUM_VALUE:
			value = mMinVal;
			break;
		case MAXIMUM_VALUE:
			value = mMaxVal;
			break;
		case STEP_SIZE:
			value = mStepSize;
			break;
		case INACTIVE_VALUE:
			value = mInActiveVal;
			break;
		case ERROR:
			value = mError;
			break;
		default:
			Log.v(TAG, "Default case");
			break;
		}
		return value;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mName);
		dest.writeString(mFriendlyName);
		dest.writeInt(mMinVal);
		dest.writeInt(mMaxVal);
		dest.writeInt(mStepSize);
		dest.writeInt(mInActiveVal);
		dest.writeInt(mError);
		dest.writeString(mUnit);
	}

	public void readFromParcel(Parcel in) {
		mName = in.readString();
		mFriendlyName = in.readString();
		mMinVal = in.readInt();
		mMaxVal = in.readInt();
		mStepSize = in.readInt();
		mInActiveVal = in.readInt();
		mError = in.readInt();
		mUnit = in.readString();
	}

	public static final Creator<UPnPTransform> CREATOR = new Creator<UPnPTransform>() {

		public UPnPTransform createFromParcel(Parcel source) {
			return new UPnPTransform(source);
		}

		public UPnPTransform[] newArray(int size) {
			return new UPnPTransform[size];
		}
	};
}
