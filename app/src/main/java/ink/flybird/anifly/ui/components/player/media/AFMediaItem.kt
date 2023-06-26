package ink.flybird.anifly.ui.components.player.media


import android.net.Uri
import androidx.annotation.RawRes
import com.google.android.exoplayer2.MediaItem.DrmConfiguration
import com.google.android.exoplayer2.MediaMetadata

interface BaseVideoPlayerMediaItem {
    val mediaMetadata: MediaMetadata
    val mimeType: String
}

/**
 * Representation of a media item for [io.sanghun.compose.video.VideoPlayer].
 *
 * @see RawResourceMediaItem
 * @see AssetFileMediaItem
 * @see StorageMediaItem
 * @see NetworkMediaItem
 */
sealed interface AFMediaItem : BaseVideoPlayerMediaItem {

    /**
     * A media item in the raw resource.
     * @param resourceId R.raw.xxxxx resource id
     * @param mediaMetadata Media Metadata. Default is empty.
     * @param mimeType Media mime type.
     */
    data class RawResourceMediaItem(
        @RawRes val resourceId: Int,
        override val mediaMetadata: MediaMetadata = MediaMetadata.EMPTY,
        override val mimeType: String = "",
    ) : AFMediaItem

    /**
     * A media item in the assets folder.
     * @param assetPath asset media file path (e.g If there is a test.mp4 file in the assets folder, test.mp4 becomes the assetPath.)
     * @throws com.google.android.exoplayer2.upstream.AssetDataSource.AssetDataSourceException asset file is not exist or load failed.
     * @param mediaMetadata Media Metadata. Default is empty.
     * @param mimeType Media mime type.
     */
    data class AssetFileMediaItem(
        val assetPath: String,
        override val mediaMetadata: MediaMetadata = MediaMetadata.EMPTY,
        override val mimeType: String = "",
    ) : AFMediaItem

    /**
     * A media item in the device internal / external storage.
     * @param storageUri storage file uri
     * @param mediaMetadata Media Metadata. Default is empty.
     * @param mimeType Media mime type.
     * @throws com.google.android.exoplayer2.upstream.FileDataSource.FileDataSourceException
     */
    data class StorageMediaItem(
        val storageUri: Uri,
        override val mediaMetadata: MediaMetadata = MediaMetadata.EMPTY,
        override val mimeType: String = "",
    ) : AFMediaItem

    /**
     * A media item in the internet
     * @param url network video url'
     * @param mediaMetadata Media Metadata. Default is empty.
     * @param mimeType Media mime type.
     * @param drmConfiguration Drm configuration for media. (Default is null)
     */
    data class NetworkMediaItem(
        val url: String,
        override val mediaMetadata: MediaMetadata = MediaMetadata.EMPTY,
        override val mimeType: String = "",
        val drmConfiguration: DrmConfiguration? = null,
    ) : AFMediaItem
}
