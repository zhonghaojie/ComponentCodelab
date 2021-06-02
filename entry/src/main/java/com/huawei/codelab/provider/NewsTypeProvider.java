package com.huawei.codelab.provider;

import com.huawei.codelab.ResourceTable;
import ohos.agp.components.*;
import ohos.app.Context;

import java.util.Optional;

/**
 * 类似Android的Adapter
 */
public class NewsTypeProvider extends BaseItemProvider {
    private String[] newsTypeList;
    private Context context;
    public NewsTypeProvider(String[] listBasicInfo, Context context) {
        this.newsTypeList = listBasicInfo;
        this.context = context;
    }
    @Override
    public int getCount() {
        return newsTypeList == null ? 0 : newsTypeList.length;
    }

    @Override
    public Object getItem(int i) {
        return Optional.of(newsTypeList[i]);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Android ListView的Adapter的思路
     * 通过设置Tag的方式来达到复用组件的效果
     *
     * @param i
     * @param component
     * @param componentContainer
     * @return
     */
    @Override
    public Component getComponent(int i, Component component, ComponentContainer componentContainer) {
        ViewHolder viewHolder = null;
        Component mComponent = component;
        if (mComponent == null) {
            //创建组件
            mComponent = LayoutScatter.getInstance(context).parse(ResourceTable.Layout_item_news_type_layout, null, false);
            viewHolder = new ViewHolder();
            Component newTypeText = mComponent.findComponentById(ResourceTable.Id_text_news_type);
            if (newTypeText instanceof Text) {
                viewHolder.title = (Text) newTypeText;
            }
            mComponent.setTag(viewHolder);
        } else if (mComponent.getTag() instanceof ViewHolder) {
            viewHolder = (ViewHolder) mComponent.getTag();
        }
        if (viewHolder != null && viewHolder.title != null) {
            viewHolder.title.setText(newsTypeList[i]);
        }
        return mComponent;
    }

    private static class ViewHolder {
        Text title;
    }
}
