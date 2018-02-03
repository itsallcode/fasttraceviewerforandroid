package itsallcode.org.fasttraceviewerforandroid.repository.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

import openfasttrack.core.SpecificationItem;

/**
 * Created by thomasu on 2/2/18.
 */

@Entity()
public class FastTraceEntity {

    public FastTraceEntity(final String name) {
        mName = name;
    }

//    public FastTraceEntity(final String name, final List<SpecificationItem> items) {
//        mName = name;
//        mSpecificationItemList = items;
//    }

    @PrimaryKey
    @NonNull
    private String mName;

//    @Embedded
//    private List<SpecificationItem> mSpecificationItemList;

    public String getName() {
        return mName;
    }
     public List<SpecificationItem> getSpecificationItems() {
        return null;
//        return mSpecificationItemList;
     }
}
