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
 * UPnP Transforms settings
 * <p>
 * class that can describes RendererSide Content Transforms settings e.g. the
 * current or wanted values of an transform
 *
 * @author Wouter van der Beek
 */
public class UPnPTransformSetting implements Parcelable, IUPnPTransformSetting {
    private String mName;
    private String mValue;

    // !< parameter id : setting name
    public static final int TRANSFORM_SETTING_NAME = 0;
    // !< parameter id : setting value
    public static final int TRANSFORM_SETTING_VALUE = 1;

    /**
     * Constructor
     */
    public UPnPTransformSetting() {
        mName = "";
        mValue = "";
    }

    /**
     * Constructor
     *
     * @param in in parcel from C code
     */
    public UPnPTransformSetting(Parcel in) {
        readFromParcel(in);
    }

    /**
     * Constructor
     *
     * @param _Name  name of the setting
     * @param _Value value of the setting
     */
    public UPnPTransformSetting(String _Name, String _Value) {
        mName = _Name;
        mValue = _Value;

        if (mName == null) {
            mName = "";
        }
        if (mValue == null) {
            mValue = "";
        }
    }

    /**
     * set a value
     *
     * @param id    the parameter id
     * @param value the value
     */
    public void setStringValue(int id, String value) {
        if (value == null) {
            value = "";
        }

        switch (id) {
            case TRANSFORM_SETTING_NAME:
                mName = value;
                break;
            case TRANSFORM_SETTING_VALUE:
                mValue = value;
                break;
            default:
                Log.v("UPnPTransformSetting", "setStringValue default case");
                break;
        }
    }

    /**
     * retrieve a value
     *
     * @param id the parameter id
     * @return the value
     */
    public String getStringValue(int id) {
        String value = null;

        switch (id) {
            case TRANSFORM_SETTING_NAME:
                value = mName;
                break;
            case TRANSFORM_SETTING_VALUE:
                value = mValue;
                break;
            default:
                Log.v("UPnPTransformSetting", "getStringValue default case");
                break;
        }

        return value;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mValue);
    }

    public void readFromParcel(Parcel in) {
        mName = in.readString();
        mValue = in.readString();
    }

    public static final Creator<UPnPTransformSetting> CREATOR = new Creator<UPnPTransformSetting>() {

        public UPnPTransformSetting createFromParcel(Parcel source) {
            return new UPnPTransformSetting(source);
        }

        public UPnPTransformSetting[] newArray(int size) {
            return new UPnPTransformSetting[size];
        }
    };
}
