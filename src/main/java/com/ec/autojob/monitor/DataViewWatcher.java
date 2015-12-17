package com.ec.autojob.monitor;

import com.ec.watcher.model.WatcherView;
import com.ec.watcher.task.BaseReportGenerationTask;

import java.util.Calendar;

/**
 * Created by ecuser on 2015/11/17.
 */
public class DataViewWatcher extends BaseReportGenerationTask {



    @Override
    protected String getReportName() {
        return null;
    }

    @Override
    protected long getInterval() {
        return 0;
    }

    @Override
    protected Calendar getStartTime() {
        return null;
    }

    @Override
    protected WatcherView generateWatcherView() {
        return null;
    }
}
