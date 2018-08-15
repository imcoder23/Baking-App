package com.example.hunter.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Steps implements Parcelable {
   private int id;
   private String shortDescrptn;
   private String deScrptn;
   private String vdoUrl;
   private String thmbUrl;

    public Steps(int id, String shortDescrptn, String deScrptn, String vdoUrl, String thmbUrl) {
            this.id = id;
            this.shortDescrptn = shortDescrptn;
            this.deScrptn = deScrptn;
            this.vdoUrl = vdoUrl;
            this.thmbUrl = thmbUrl;
    }

    protected Steps(Parcel in) {
        id = in.readInt();
        shortDescrptn = in.readString();
        deScrptn = in.readString();
        vdoUrl = in.readString();
        thmbUrl = in.readString();
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };

//    public int getId() {
//        return id;
//    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public String getShortDescrptn() {
        return shortDescrptn;
    }

//    public void setShortDescrptn(String shortDescrptn) {
//        this.shortDescrptn = shortDescrptn;
//    }

    public String getDeScrptn() {
        return deScrptn;
    }

//    public void setDeScrptn(String deScrptn) {
//        this.deScrptn = deScrptn;
//    }

    public String getVdoUrl() {
        return vdoUrl;
    }

//    public void setVdoUrl(String vdoUrl) {
//        this.vdoUrl = vdoUrl;
//    }

    public String getThmbUrl() {
        return thmbUrl;
    }

//    public void setThmbUrl(String thmbUrl) {
//        this.thmbUrl = thmbUrl;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescrptn);
        dest.writeString(deScrptn);
        dest.writeString(vdoUrl);
        dest.writeString(thmbUrl);
    }
}
