/*-
 * #%L
 \* FastTraceViewerForAndroid
 * %%
 * Copyright (C) 2017 - 2018 itsallcode.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package itsallcode.org.fasttraceviewerforandroid.dagger.components

import javax.inject.Singleton

import dagger.Component
import itsallcode.org.fasttraceviewerforandroid.dagger.modules.*
import itsallcode.org.fasttraceviewerforandroid.viewmodel.FastTraceViewModel
import itsallcode.org.fasttraceviewerforandroid.viewmodel.SpecDetailViewModel
import itsallcode.org.fasttraceviewerforandroid.viewmodel.SpecListViewModel

/**
 * Created by thomasu on 8/19/17.
 */
@Component(modules = [AppModule::class,
                        ContentModule::class,
                        CacheModule::class,
                        FastTraceRepositoryModule::class,
                        AppExecutorsModule::class])
@Singleton
interface AppComponent {
    fun inject(viewModel: FastTraceViewModel)
    fun inject(viewModel: SpecListViewModel)
    fun inject(viewModel: SpecDetailViewModel)
}
