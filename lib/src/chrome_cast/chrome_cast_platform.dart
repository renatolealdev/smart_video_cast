import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:smart_video_cast/src/chrome_cast/chrome_cast_event.dart';
import 'package:smart_video_cast/src/chrome_cast/method_channel_chrome_cast.dart';

/// The interface that platform-specific implementations of `smart_video_cast` must extend.
abstract class ChromeCastPlatform {
  static ChromeCastPlatform _instance = MethodChannelChromeCast();

  /// The default instance of [ChromeCastPlatform] to use.
  ///
  /// Defaults to [MethodChannelChromeCast].
  static get instance => _instance;

  /// Initializes the platform interface with [id].
  ///
  /// This method is called when the plugin is first initialized.
  Future<void> init(int id) {
    throw UnimplementedError('init() has not been implemented.');
  }

  /// Add listener for receive callbacks.
  Future<void> addSessionListener({required int id}) {
    throw UnimplementedError('addSessionListener() has not been implemented.');
  }

  /// Remove listener for receive callbacks.
  Future<void> removeSessionListener({required int id}) {
    throw UnimplementedError(
        'removeSessionListener() has not been implemented.');
  }

  /// A session is started.
  ///
  /// Emits an event when a ChromeCast connection has been established.
  Stream<SessionStartedEvent> onSessionStarted({required int id}) {
    throw UnimplementedError('onSessionStarted() has not been implemented.');
  }

  /// A session is ended.
  ///
  /// Emits an event when a ChromeCast connection has been terminated.
  Stream<SessionEndedEvent> onSessionEnded({required int id}) {
    throw UnimplementedError('onSessionEnded() has not been implemented.');
  }

  /// A request has completed.
  ///
  /// Emits an event when a request to the ChromeCast device has completed successfully.
  Stream<RequestDidCompleteEvent> onRequestCompleted({required int id}) {
    throw UnimplementedError('onRequestCompleted() has not been implemented.');
  }

  /// A request has failed.
  ///
  /// Emits an event when a request to the ChromeCast device has failed, including an error message.
  Stream<RequestDidFailEvent> onRequestFailed({required int id}) {
    throw UnimplementedError('onRequestFailed() has not been implemented.');
  }

  /// Load a new media by providing an [url].
  ///
  /// Starts playing a media on the connected ChromeCast device.
  Future<void> loadMedia(
    String url, {
    required int id,
  }) {
    throw UnimplementedError('loadMedia() has not been implemented.');
  }

  /// Plays the video playback.
  ///
  /// Resumes playback if it was paused.
  Future<void> play({required int id}) {
    throw UnimplementedError('play() has not been implemented.');
  }

  /// Pauses the video playback.
  ///
  /// Pauses the current media playback on the ChromeCast device.
  Future<void> pause({required int id}) {
    throw UnimplementedError('pause() has not been implemented.');
  }

  /// If [relative] is set to false sets the video position to an [interval] from the start.
  ///
  /// If [relative] is set to true sets the video position to an [interval] from the current position.
  Future<void> seek(bool relative, double interval, {required int id}) {
    throw UnimplementedError('seek() has not been implemented.');
  }

  /// Stop the current video.
  ///
  /// Stops playback of the current media on the ChromeCast device.
  Future<void> stop({required int id}) {
    throw UnimplementedError('stop() has not been implemented.');
  }

  /// Returns `true` when a cast session is connected, `false` otherwise.
  ///
  /// Checks if a connection to a ChromeCast device is currently active.
  Future<bool?> isConnected({required int id}) {
    throw UnimplementedError('isConnected() has not been implemented.');
  }

  /// Returns `true` when a cast session is playing, `false` otherwise.
  ///
  /// Checks if media is currently playing on the connected ChromeCast device.
  Future<bool?> isPlaying({required int id}) {
    throw UnimplementedError('isPlaying() has not been implemented.');
  }

  /// Returns a widget displaying the button.
  ///
  /// Builds the platform-specific ChromeCast button widget.
  Widget buildView(Map<String, dynamic> arguments,
      PlatformViewCreatedCallback onPlatformViewCreated) {
    throw UnimplementedError('buildView() has not been implemented.');
  }
}
