import Flutter
import UIKit

public class SwiftSmartVideoCastPlugin: NSObject, FlutterPlugin {
    public static func register(with registrar: FlutterPluginRegistrar) {
        registrar.register(ChromeCastFactory(messenger: registrar.messenger()), withId: "ChromeCastButton")
        registrar.register(AirPlayFactory(messenger: registrar.messenger()), withId: "AirPlayButton")
    }
}
