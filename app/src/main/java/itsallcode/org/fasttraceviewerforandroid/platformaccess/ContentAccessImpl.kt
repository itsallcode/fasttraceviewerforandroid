package itsallcode.org.fasttraceviewerforandroid.platformaccess

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.InputStream

import javax.inject.Inject

/**
 * Created by thomasu on 2/11/18.
 */
class ContentAccessImpl @Inject constructor (private val context : Context) : ContentAccess {
    override fun getName(uri : Uri) : String? {
        val uriString = uri.toString()
        val myFile = File(uriString)
        var displayName: String? = null

        if (uriString.startsWith("content://")) {
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver.query(uri, null, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        } else if (uriString.startsWith("file://")) {
            displayName = myFile.name
        }
        return displayName
    }

    override fun open(uri: Uri) : InputStream {
        return context.contentResolver.openInputStream(uri)
    }

}