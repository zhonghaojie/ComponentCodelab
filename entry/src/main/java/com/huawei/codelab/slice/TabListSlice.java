package com.huawei.codelab.slice;

import com.huawei.codelab.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ListContainer;
import ohos.agp.components.TabList;
import ohos.agp.components.Text;

public class TabListSlice extends AbilitySlice {
    private TabList tabList;
    private Text tabContent;
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_tab_list);
        initComponent();
        addTabListItemClickListener();
    }

    private void initComponent(){
        tabList = (TabList) findComponentById(ResourceTable.Id_tab_list);
        tabContent= (Text) findComponentById(ResourceTable.Id_tab_content);
        initTab();
    }

    private void initTab(){
        if(tabList.getTabCount() == 0){
            tabList.addTab(createTab("Image"));
            tabList.addTab(createTab("Video"));
            tabList.addTab(createTab("Audio"));
            tabList.setFixedMode(true);
            tabList.getTabAt(0).select();
            tabContent.setText("Select the "+tabList.getTabAt(0).getText());

        }
    }

    private TabList.Tab createTab(String text){
        TabList.Tab tab = tabList.new Tab(this);
        tab.setText(text);
        tab.setMinWidth(64);
        tab.setPadding(12,0,12,0);
        return tab;
    }

    private void addTabListItemClickListener(){
        tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
            @Override
            public void onSelected(TabList.Tab tab) {
                tabContent.setText("Select the " + tab.getText());
            }

            @Override
            public void onUnselected(TabList.Tab tab) {

            }

            @Override
            public void onReselected(TabList.Tab tab) {

            }
        });
    }
}
