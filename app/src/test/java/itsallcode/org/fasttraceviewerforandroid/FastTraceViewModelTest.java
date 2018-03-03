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

package itsallcode.org.fasttraceviewerforandroid;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.inject.Inject;

import itsallcode.org.fasttraceviewerforandroid.dagger.components.AppTestComponent;
import itsallcode.org.fasttraceviewerforandroid.dagger.components.DaggerAppTestComponent;
import itsallcode.org.fasttraceviewerforandroid.viewmodel.FastTraceViewModel;
import android.arch.core.executor.testing.InstantTaskExecutorRule;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Unit test checking the FastTraceViewModel class.
 *
 */
public class FastTraceViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @Rule
    public final InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    AppTestComponent mAppTestComponent = DaggerAppTestComponent.create();

    @Inject
    FastTraceViewModel viewModel = new FastTraceViewModel();

    @Before
    public void setup() {
        mAppTestComponent.inject(viewModel);
    }

    @Test
    public void test() throws Exception {
        File file = new File(this.getClass().getClassLoader()
                .getResource("specobject/two-specobjects.xml").getFile());
        assertTrue(file.exists());
        FileInputStream inputStream = new FileInputStream(file);
        FileOutputStream outputStream = new FileOutputStream(file);
        Uri uri = Mockito.mock(Uri.class);

        Mockito.when(viewModel.contentAccess.open(uri)).thenAnswer( (invocation) -> inputStream);
        Mockito.when(viewModel.contentAccess.getName(uri)).thenAnswer(invocation -> "two-specobjects.xml");
        Mockito.when(viewModel.cacheAccess.copyToCache(inputStream, "two-specobjects.xml")).thenReturn(file);
        viewModel.importFile(uri);
        assertEquals(viewModel.fastTraceRepository.getAllFastTraceItems().getValue().size(), 1);
        assertEquals(viewModel.fastTraceRepository.getAllFastTraceItems().getValue().get(0).getName(), "two-specobjects.xml");
    }
}