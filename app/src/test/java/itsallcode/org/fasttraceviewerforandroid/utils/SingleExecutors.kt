
package itsallcode.org.fasttraceviewerforandroid.utils

import itsallcode.org.fasttraceviewerforandroid.platformaccess.AppExecutorsImpl
import java.util.concurrent.Executor

/**
 * Allow instant execution of tasks.
 */
class SingleExecutors : AppExecutorsImpl(instant, instant) {
    companion object {
        private val instant = Executor { command -> command.run() }
    }
}