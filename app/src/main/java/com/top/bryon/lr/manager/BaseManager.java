package com.top.bryon.lr.manager;

import com.top.bryon.lr.utils.HandlerUtils;

/**
 * Created by bryonliu on 2015/11/12.
 */
public class BaseManager {
    public void runOnUI(Runnable runnable) {
        HandlerUtils.getMainHandler().post(runnable);
    }
}
