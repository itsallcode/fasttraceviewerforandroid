
package itsallcode.org.fasttraceviewerforandroid.platformaccess

import java.util.concurrent.Executor

/**
 * Defines executors used in application.
 */
interface AppExecutors {
    val bgExecutor: Executor
    val mainThread: Executor
}