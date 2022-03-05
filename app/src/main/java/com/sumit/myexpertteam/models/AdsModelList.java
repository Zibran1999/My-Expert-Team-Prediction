package com.sumit.myexpertteam.models;

import java.util.List;

public class AdsModelList {
    List<com.sumit.myexpertteam.models.AdsModel> data = null;

    public AdsModelList(List<com.sumit.myexpertteam.models.AdsModel> data) {
        this.data = data;
    }

    public List<com.sumit.myexpertteam.models.AdsModel> getData() {
        return data;
    }
}
