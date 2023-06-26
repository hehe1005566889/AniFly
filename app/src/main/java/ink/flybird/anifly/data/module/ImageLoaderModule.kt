package ink.flybird.anifly.data.module


import android.content.Context
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import coil.ImageLoader
import coil.decode.Decoder
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.internal.concurrent.TaskRunner.Companion.logger
import javax.inject.Singleton

/**
 * Provides singleton [ImageLoader] for Coil.
 */
@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient,
    ): ImageLoader {
        return ImageLoader.Builder(context)
            // Shared OKHttpClient instance.
            .okHttpClient(okHttpClient)
            // This slightly improves scrolling performance
            .dispatcher(Dispatchers.Default)
            .components {
                // Support SVG decoding
                add(SvgDecoder.Factory())
                add(ImageDecoderDecoder.Factory())
                add(coil.decode.BitmapFactoryDecoder.Factory())
                // Support GIF decoding
                add(
                    if (SDK_INT >= Build.VERSION_CODES.P) {
                        ImageDecoderDecoder.Factory()
                    } else {
                        GifDecoder.Factory()
                    }
                )
            }
            .logger(DebugLogger())
            // Enable disk cache
            .diskCache(
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("images"))
                    .maxSizePercent(0.02)
                    .build()
            )
            // Enable memory cache
            .memoryCache(
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            )
            .build()
    }
}
