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