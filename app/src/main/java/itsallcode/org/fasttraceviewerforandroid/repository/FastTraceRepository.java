package itsallcode.org.fasttraceviewerforandroid.repository;

import java.util.List;

import openfasttrack.core.SpecificationItem;

/**
 * Created by thomasu on 1/27/18.
 */

public interface FastTraceRepository {
    void add(final String name, final List<SpecificationItem> itemList);
}
