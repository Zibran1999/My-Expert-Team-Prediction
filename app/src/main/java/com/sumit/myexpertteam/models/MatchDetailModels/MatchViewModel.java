package com.sumit.myexpertteam.models.MatchDetailModels;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sumit.myexpertteam.models.AdsStatusModels.AdsStatusModel;
import com.sumit.myexpertteam.models.ContestCodeModels.ContestCodeModel;
import com.sumit.myexpertteam.models.FootballDataModels.FootballDataModel;
import com.sumit.myexpertteam.models.MatchNewsModels.MatchNewsModel;
import com.sumit.myexpertteam.models.TeamPlayerModels.TeamPlayerImageModel;


public class MatchViewModel extends AndroidViewModel {

    private final MatchDetailRepository matchDetailRepository;
    private String id;


    public MatchViewModel(@NonNull Application application) {
        super(application);
        matchDetailRepository = MatchDetailRepository.getInstance();

    }

    public MatchViewModel(Application application, String id) {
        super(application);
        this.id = id;
        matchDetailRepository = MatchDetailRepository.getInstance();

    }


    public LiveData<MatchDetailModel> getMatchData() {
        return matchDetailRepository.getMatchDetailModelMutableLiveData();
    }

    public LiveData<MatchNewsModel> getMatchNewsData() {
        return matchDetailRepository.getMatchNewsModelLiveData();
    }

    public LiveData<FootballDataModel> getFootballData() {
        return matchDetailRepository.getFootballDataLiveData();
    }

    public LiveData<ContestCodeModel> getContestData() {
        return matchDetailRepository.getCodeModelMutableLiveData();
    }

    public LiveData<MatchPreviewModel> getMatchPreview() {
        return matchDetailRepository.getMatchPreviewModelMutableLiveData(id);

    }

    public LiveData<TeamPlayerImageModel> getTeamImages() {
        return matchDetailRepository.getTeamPlayerImageModelMutableLiveData(id);
    }

    public LiveData<AdsStatusModel> getAdsStatus() {
        return matchDetailRepository.getAdsStatusModelMutableLiveData();
    }

    public LiveData<TeamPlayerImageModel> getGrandTeamImages() {
        return matchDetailRepository.getGrandTeamPlayerImageModelMutableLiveData(id);
    }

    public LiveData<TeamPlayerImageModel> getSimpleTeamImages() {
        return matchDetailRepository.getSimpleTeamPlayerImageModelMutableLiveData(id);
    }


}
