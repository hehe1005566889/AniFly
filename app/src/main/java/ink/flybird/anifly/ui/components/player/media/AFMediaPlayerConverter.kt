package ink.flybird.anifly.ui.components.player.media


import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.upstream.AssetDataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.FileDataSource
import com.google.android.exoplayer2.upstream.FileDataSource.FileDataSourceException
import com.google.android.exoplayer2.upstream.RawResourceDataSource

/**
 * Converts [VideoPlayerMediaItem] to [android.net.Uri].
 *
 * @param context Pass application context or activity context. Use this context to load asset file using [android.content.res.AssetManager].
 * @return [android.net.Uri]
 */
internal fun AFMediaItem.toUri(
    context: Context,
): Uri = when (this) {
    is AFMediaItem.RawResourceMediaItem -> {
        RawResourceDataSource.buildRawResourceUri(resourceId)
    }

    is AFMediaItem.AssetFileMediaItem -> {
        val dataSpec = DataSpec(Uri.parse("asset:///$assetPath"))
        val assetDataSource = AssetDataSource(context)
        try {
            assetDataSource.open(dataSpec)
        } catch (e: AssetDataSource.AssetDataSourceException) {
            e.printStackTrace()
        }

        assetDataSource.uri ?: Uri.EMPTY
    }

    is AFMediaItem.NetworkMediaItem -> {
        Uri.parse(url)
    }

    is AFMediaItem.StorageMediaItem -> {
        val dataSpec = DataSpec(storageUri)
        val fileDataSource = FileDataSource()
        try {
            fileDataSource.open(dataSpec)
        } catch (e: FileDataSourceException) {
            e.printStackTrace()
        }

        fileDataSource.uri ?: Uri.EMPTY
    }
}
