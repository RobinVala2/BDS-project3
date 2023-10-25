package bds.project3.services;

import bds.project3.api.*;
import bds.project3.data.AppRepository;
import bds.project3.api.*;
import bds.project3.data.AppRepository;

import java.io.Serializable;
import java.util.List;

public class AppService
{
    private final AppRepository appRepository;

    public AppService(AppRepository appRepository)
    {
        this.appRepository = appRepository;
    }

    // Basic View
    public List<AppBasicView> getMemberBasicView()
    {
        return appRepository.getAllMembers();
    }

    // Detailed View
    public AppDetailedView getSelectedMember(String last_name)
    {
        return appRepository.getDetailedView(last_name);
    }

    //Filtered View
    public List<AppBasicView> getFilteredView(String last_name)
    {
        return appRepository.getFilteredMembers(last_name);
    }

    // Create View
    public void createMember(AppCreateView createView)
    {
        appRepository.createMember(createView);
    }

    //Edit View
    public void editMember(AppEditView editView)
    {
        appRepository.editMember(editView);
    }

    // injection
    public List<InjectionView> getInjectionView(String input)
    {
        return appRepository.getInjectionView(input);
    }
}

