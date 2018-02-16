/*
 * Copyright (C) 2017 Motorola, Inc.
 * All Rights Reserved.
 *
 * The contents of this file are Motorola Confidential Restricted (MCR).
 */

package itsallcode.org.fasttraceviewerforandroid.dagger.modules


import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import itsallcode.org.fasttraceviewerforandroid.platformaccess.AppExecutors
import itsallcode.org.fasttraceviewerforandroid.platformaccess.AppExecutorsImpl

/**
 * Dagger Module for generic purpose: Provides Application.
 */
@Module
class AppExecutorsModule() {

    @Provides
    @Singleton
    internal fun providesExecutors(): AppExecutors {
        return AppExecutorsImpl()
    }
}