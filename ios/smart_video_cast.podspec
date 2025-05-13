#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint smart_video_cast.podspec' to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'smart_video_cast'
  s.version          = '1.0.3'
  s.summary          = 'A Flutter plugin for casting to Chromecast and AirPlay devices.'
  s.description      = <<-DESC
A Flutter plugin for casting videos to devices like Chromecast and Apple TV.
                       DESC
  s.homepage         = 'https://github.com/smarthost-tr/smart_video_cast'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'SmartHost' => 'info@smarthost.co' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.dependency 'Flutter'
  s.dependency 'google-cast-sdk-no-bluetooth', '~> 4.7.0'
  s.platform = :ios, '13.0'
  s.static_framework = true

  # Flutter.framework does not contain a i386 slice.
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'VALID_ARCHS[sdk=iphonesimulator*]' => 'arm64 x86_64' }
  s.swift_version = '5.0'
end
