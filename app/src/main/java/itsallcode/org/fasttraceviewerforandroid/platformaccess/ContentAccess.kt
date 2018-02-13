package itsallcode.org.fasttraceviewerforandroid.platformaccess

import android.net.Uri
import java.io.File
import java.io.InputStream

/**
 * Created by thomasu on 2/11/18.
 */
interface ContentAccess {
    fun getName(uri : Uri) : String?
    fun open(uri: Uri) : InputStream
}