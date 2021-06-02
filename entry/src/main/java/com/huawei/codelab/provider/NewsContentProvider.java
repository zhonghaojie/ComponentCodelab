package com.huawei.codelab.provider;

import com.huawei.codelab.ResourceTable;
import com.huawei.codelab.databean.NewsInfo;
import ohos.agp.components.*;
import ohos.app.Context;

import java.util.List;
import java.util.Optional;

public class NewsContentProvider extends BaseItemProvider {
    private List<NewsInfo> newsInfoList;
    private Context context;
    private LayoutScatter layoutScatter;

    public NewsContentProvider(List<NewsInfo> newsInfos, Context context) {
        this.newsInfoList = newsInfos;
        this.context = context;
        layoutScatter = LayoutScatter.getInstance(context);
    }

    @Override
    public int getCount() {
        return newsInfoList == null ? 0 : newsInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return Optional.of(newsInfoList.get(i));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        ViewHolder vh = null;
        Component com = component;
        if (com == null) {
            com = layoutScatter.parse(ResourceTable.Layout_item_news_layout, null, false);
            vh = new ViewHolder();
            Component text = com.findComponentById(ResourceTable.Id_item_news_title);
            Component image = com.findComponentById(ResourceTable.Id_item_news_image);

            if (text instanceof Text) {
                vh.text = (Text) text;
            }
            if (image instanceof Image) {
                vh.image = (Image) image;
            }
            com.setTag(vh);
        } else if (com.getTag() instanceof ViewHolder) {
            vh = (ViewHolder) com.getTag();
        }
        if (vh != null) {
            vh.image.setScaleMode(Image.ScaleMode.STRETCH);
            vh.text.setText(newsInfoList.get(i).getTitle());
        }
        return com;
    }

    private static class ViewHolder {
        Image image;
        Text text;
    }
}
