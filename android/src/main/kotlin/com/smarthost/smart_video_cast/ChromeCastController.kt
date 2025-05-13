package com.smarthost.smart_video_cast

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.mediarouter.app.MediaRouteButton
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaLoadOptions
import com.google.android.gms.cast.MediaLoadRequestData
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.Session
import com.google.android.gms.cast.framework.SessionManagerListener
import com.google.android.gms.cast.framework.media.RemoteMediaClient
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView

class ChromeCastController(
        messenger: BinaryMessenger,
        viewId: Int,
        context: Context?
) : PlatformView, MethodChannel.MethodCallHandler, SessionManagerListener<Session>, RemoteMediaClient.Callback {
    private val channel = MethodChannel(messenger, "smart_video_cast/chromeCast_$viewId")
    private val chromeCastButton = MediaRouteButton(ContextThemeWrapper(context, R.style.Theme_AppCompat_NoActionBar))
    private val sessionManager = CastContext.getSharedInstance()?.sessionManager

    init {
        CastButtonFactory.setUpMediaRouteButton(context, chromeCastButton)
        channel.setMethodCallHandler(this)
    }

    private fun loadMedia(args: Any?) {
        if (args is Map<*, *>) {
            val url = args["url"] as? String
            val metadata = MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE)
            val mediaInfo = MediaInfo.Builder(url ?: "")
                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                    .setContentType("video/mp4")
                    .setMetadata(metadata)
                    .build()
            val remoteMediaClient = sessionManager?.currentCastSession?.remoteMediaClient
            remoteMediaClient?.registerCallback(this)
            val options = MediaLoadOptions.Builder().build()
            remoteMediaClient?.load(mediaInfo, options)
        }
    }

    private fun play() {
        val remoteMediaClient = sessionManager?.currentCastSession?.remoteMediaClient
        remoteMediaClient?.registerCallback(this)
        remoteMediaClient?.play()
    }

    private fun pause() {
        val remoteMediaClient = sessionManager?.currentCastSession?.remoteMediaClient
        remoteMediaClient?.registerCallback(this)
        remoteMediaClient?.pause()
    }

    private fun seek(args: Any?) {
        if (args is Map<*, *>) {
            val relative = (args["relative"] as? Boolean) ?: false
            var interval = args["interval"] as? Double
            interval = interval?.times(1000)
            if (relative) {
                interval = interval?.plus(sessionManager?.currentCastSession?.remoteMediaClient?.approximateStreamPosition ?: 0)
            }
            val remoteMediaClient = sessionManager?.currentCastSession?.remoteMediaClient
            remoteMediaClient?.registerCallback(this)
            remoteMediaClient?.seek(interval?.toLong() ?: 0)
        }
    }

    private fun stop() {
        val remoteMediaClient = sessionManager?.currentCastSession?.remoteMediaClient
        remoteMediaClient?.registerCallback(this)
        remoteMediaClient?.stop()
    }

    private fun isPlaying(): Boolean {
        val remoteMediaClient = sessionManager?.currentCastSession?.remoteMediaClient
        return remoteMediaClient?.isPlaying ?: false
    }

    private fun isConnected() = sessionManager?.currentCastSession?.isConnected ?: false

    private fun addSessionListener() {
        sessionManager?.addSessionManagerListener(this)
    }

    private fun removeSessionListener() {
        sessionManager?.removeSessionManagerListener(this)
    }

    override fun getView() = chromeCastButton

    override fun dispose() {
        sessionManager?.currentCastSession?.remoteMediaClient?.unregisterCallback(this)
        sessionManager?.removeSessionManagerListener(this)
    }

    // Flutter methods handling

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when(call.method) {
            "chromeCast#wait" -> result.success(null)
            "chromeCast#loadMedia" -> {
                loadMedia(call.arguments)
                result.success(null)
            }
            "chromeCast#play" -> {
                play()
                result.success(null)
            }
            "chromeCast#pause" -> {
                pause()
                result.success(null)
            }
            "chromeCast#seek" -> {
                seek(call.arguments)
                result.success(null)
            }
            "chromeCast#stop" -> {
                stop()
                result.success(null)
            }
            "chromeCast#isPlaying" -> result.success(isPlaying())
            "chromeCast#isConnected" -> result.success(isConnected())
            "chromeCast#addSessionListener" -> {
                addSessionListener()
                result.success(null)
            }
            "chromeCast#removeSessionListener" -> {
                removeSessionListener()
                result.success(null)
            }
            else -> result.notImplemented()
        }
    }

    // SessionManagerListener

    override fun onSessionStarted(p0: Session?, p1: String?) {
        channel.invokeMethod("chromeCast#didStartSession", null)
    }

    override fun onSessionEnded(p0: Session?, p1: Int) {
        channel.invokeMethod("chromeCast#didEndSession", null)
    }

    override fun onSessionResuming(p0: Session?, p1: String?) {
        // Not needed for basic functionality
    }

    override fun onSessionResumed(p0: Session?, p1: Boolean) {
        // Not needed for basic functionality
    }

    override fun onSessionResumeFailed(p0: Session?, p1: Int) {
        // Not needed for basic functionality
    }

    override fun onSessionSuspended(p0: Session?, p1: Int) {
        // Not needed for basic functionality
    }

    override fun onSessionStarting(p0: Session?) {
        // Not needed for basic functionality
    }

    override fun onSessionEnding(p0: Session?) {
        // Not needed for basic functionality
    }

    override fun onSessionStartFailed(p0: Session?, p1: Int) {
        // Not needed for basic functionality
    }

    // RemoteMediaClient.Callback

    override fun onStatusUpdated() {
        channel.invokeMethod("chromeCast#requestDidComplete", null)
    }

    override fun onMetadataUpdated() {
        // Not needed for basic functionality
    }

    override fun onQueueStatusUpdated() {
        // Not needed for basic functionality
    }

    override fun onPreloadStatusUpdated() {
        // Not needed for basic functionality
    }

    override fun onSendingRemoteMediaRequest() {
        // Not needed for basic functionality
    }

    override fun onAdBreakStatusUpdated() {
        // Not needed for basic functionality
    }
}
