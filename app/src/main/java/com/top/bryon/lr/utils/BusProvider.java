package com.top.bryon.lr.utils;

import com.squareup.otto.Bus;

/**
 * Created by bryonliu on 2016/1/12.
 */
public final class BusProvider {
    private static final Bus bus = new Bus();

    public static Bus instance(){
        return bus;
    }
    private BusProvider(){
    }
}
