package itsallcode.org.fasttraceviewerforandroid.platformaccess

import android.os.Handler
import android.os.Looper

import java.util.concurrent.Executor
import java.util.concurrent.Executors


open class AppExecutorsImpl constructor (
        override val bgExecutor: Executor = Executors.newSingleThreadExecutor(),
        override val mainThread: Executor = MainThreadExecutor()
) : AppExecutors {

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}