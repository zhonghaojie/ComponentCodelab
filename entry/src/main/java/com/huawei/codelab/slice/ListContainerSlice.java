package com.huawei.codelab.slice;

import com.huawei.codelab.ResourceTable;
import com.huawei.codelab.databean.NewsInfo;
import com.huawei.codelab.provider.NewsContentProvider;
import com.huawei.codelab.provider.NewsTypeProvider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.components.TabList;
import ohos.agp.components.Text;
import ohos.agp.utils.Color;

import java.util.ArrayList;

public class ListContainerSlice extends AbilitySlice {
    private static final float FOCUS_TEXT_SIZE = 1.2f;
    private static final float UNFOCUS_TEXT_SIZE = 1.0f;

    private ListContainer selectorListContainer;
    private NewsTypeProvider newsTypeProvider;
    private ListContainer newsListContainer;
    private NewsContentProvider newsContentProvider;
    private ArrayList<NewsInfo> totalNews;
    private ArrayList<NewsInfo> selectNews;
    private Text selectText;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_news_list_layout);
        initView();
        initProvider();
        initListener();
    }
    private void initView(){
        selectorListContainer = (ListContainer) findComponentById(ResourceTable.Id_news_type_container);
        newsListContainer = (ListContainer) findComponentById(ResourceTable.Id_news_content_container);
    }
    private void initProvider(){
        String[] listNames = new String[]{"All", "Health", "Finance", "Technology", "Sport", "Internet", "Game"};
        newsTypeProvider = new NewsTypeProvider(listNames,this);
        selectorListContainer.setItemProvider(newsTypeProvider);


        //生成几个假数据
        String[] newsTypes = new String[]{"Health", "Finance", "Finance", "Technology", "Sport", "Health", "Internet", "Game", "Game", "Internet"};
        String[] newsTitles = new String[]{
                "Best Enterprise Wi-Fi Network Award of the Wireless Broadband Alliance 2020",
                "Openness and Cooperation Facilitate Industry Upgrade",
                "High-voltage super-fast charging is an inevitable trend",
                "Ten Future Trends of Digital Energy",
                "Ascend Helps Industry, Learning, and Research Promote AI Industry Development in the National AI Contest",
                "Enterprise data centers are moving towards autonomous driving network",
                "One optical fiber lights up a green smart room",
                "Trust technology, embrace openness, and share the world prosperity brought by technology",
                "Intelligent Twins Won the Leading Technology Achievement Award at the 7th World Internet Conference",
                "Maximizing the Value of Wireless Networks and Ushering in the Golden Decade of 5G"
        };
        totalNews = new ArrayList<>();
        selectNews = new ArrayList<>();
        for (int i = 0; i < newsTypes.length; i++) {
            NewsInfo newsInfo = new NewsInfo();
            newsInfo.setTitle(newsTitles[i]);
            newsInfo.setType(newsTypes[i]);
            totalNews.add(newsInfo);
        }
        selectNews.addAll(totalNews);

        newsContentProvider = new NewsContentProvider(selectNews, this);
        newsListContainer.setItemProvider(newsContentProvider);
    }

    private void initListener(){
        selectorListContainer.setItemClickedListener((listContainer, component, i, l) -> {
            setCategorizationFocus(false);
            Component newsTypeText = component.findComponentById(ResourceTable.Id_text_news_type);
            if (newsTypeText instanceof Text) {
                selectText = (Text) newsTypeText;
            }
            setCategorizationFocus(true);
            selectNews.clear();
            if (i == 0) {
                selectNews.addAll(totalNews);
            } else {
                String newsType = selectText.getText();
                for (NewsInfo newsData : totalNews) {
                    if (newsType.equals(newsData.getType())) {
                        selectNews.add(newsData);
                    }
                }
            }
            updateList();
        });
        selectorListContainer.setSelected(true);
        selectorListContainer.setSelectedItemIndex(0);
    }
    private void updateList(){
        newsContentProvider.notifyDataChanged();
        //官方有加这一句，实际发现不加也可以刷新列表
//        newsListContainer.invalidate();
        newsListContainer.scrollToCenter(0);

    }
    private void setCategorizationFocus(boolean isFocus) {
        if (selectText == null) {
            return;
        }
        if (isFocus) {
            selectText.setTextColor(new Color(Color.getIntColor("#afaafa")));
            selectText.setScaleX(FOCUS_TEXT_SIZE);
            selectText.setScaleY(FOCUS_TEXT_SIZE);
        } else {
            selectText.setTextColor(new Color(Color.getIntColor("#999999")));
            selectText.setScaleX(UNFOCUS_TEXT_SIZE);
            selectText.setScaleY(UNFOCUS_TEXT_SIZE);
        }
    }
}
