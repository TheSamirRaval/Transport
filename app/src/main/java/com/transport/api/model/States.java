package com.transport.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SAM on 28/3/20.
 */
public class States {

    @SerializedName("State")
    @Expose
    private List<State> state = null;
    @SerializedName("Status")
    @Expose
    private Status status;

    public List<State> getState() {
        return state;
    }

    public void setState(List<State> state) {
        this.state = state;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static class State implements Parcelable {

        @SerializedName("StateId")
        @Expose
        private Integer stateId;
        @SerializedName("StateName")
        @Expose
        private String stateName;

        protected State(Parcel in) {
            if (in.readByte() == 0) {
                stateId = null;
            } else {
                stateId = in.readInt();
            }
            stateName = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (stateId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(stateId);
            }
            dest.writeString(stateName);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<State> CREATOR = new Creator<State>() {
            @Override
            public State createFromParcel(Parcel in) {
                return new State(in);
            }

            @Override
            public State[] newArray(int size) {
                return new State[size];
            }
        };

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        @Override
        public String toString() {
            return "State{" +
                    "stateId=" + stateId +
                    ", stateName='" + stateName + '\'' +
                    '}';
        }
    }

}
