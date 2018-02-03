package itsallcode.org.fasttraceviewerforandroid.repository;

import java.util.List;

import javax.inject.Inject;

import itsallcode.org.fasttraceviewerforandroid.data.FastTraceDao;
import itsallcode.org.fasttraceviewerforandroid.repository.entities.FastTraceEntity;
import openfasttrack.core.SpecificationItem;

/**
 * Created by thomasu on 2/2/18.
 */

public class FastTraceDataSource implements FastTraceRepository {

    private FastTraceDao mFastTraceDao;

    @Inject
    public FastTraceDataSource(final FastTraceDao fastTraceDao) {
        mFastTraceDao = fastTraceDao;
    }

    @Override
    public void add(String name, List<SpecificationItem> itemList) {
        final  FastTraceEntity entity = new FastTraceEntity(name);
        mFastTraceDao.insert(entity);
    }
}
