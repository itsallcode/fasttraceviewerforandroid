/*
 * Copyright (C) 2017 Motorola, Inc.
 * All Rights Reserved.
 *
 * The contents of this file are Motorola Confidential Restricted (MCR).
 */

package itsallcode.org.fasttraceviewerforandroid.dagger.modules

import android.app.Application

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import itsallcode.org.fasttraceviewerforandroid.platformaccess.CacheAccess
import itsallcode.org.fasttraceviewerforandroid.platformaccess.CacheAccessImpl

/**
 * Dagger Module for generic purpose: Provides Application.
 */
@Module
class CacheModule(private val mApplication: Application) {

    @Provides
    @Singleton
    internal fun providesCacheAccess(): CacheAccess {
        return CacheAccessImpl(mApplication)
    }
}