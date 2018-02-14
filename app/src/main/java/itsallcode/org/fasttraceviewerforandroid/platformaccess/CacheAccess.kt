package itsallcode.org.fasttraceviewerforandroid.platformaccess

import java.io.File
import java.io.InputStream

/**
 * Created by thomasu on 2/11/18.
 */
interface CacheAccess {
    fun copyToCache(source : InputStream, name : String?) : File
}