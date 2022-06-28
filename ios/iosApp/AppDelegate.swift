import Trikot
import TrikotFrameworkName
import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
	var window: UIWindow?

	// swiftlint:disable discouraged_optional_collection
	func application(
		_: UIApplication,
		didFinishLaunchingWithOptions _: [UIApplication.LaunchOptionsKey: Any]?
	) -> Bool {
		Environment().flavor = currentFlavor()
		HttpConfiguration().httpRequestFactory = TrikotHttpRequestFactory()
		HttpConfiguration().connectivityPublisher = TrikotConnectivityService.shared.publisher
		ImageViewModelResourceManager.shared = SampleImageResourceProvider()
		TrikotKword.shared.setCurrentLanguage("en")

		let window = UIWindow(frame: UIScreen.main.bounds)
		self.window = window

		window.rootViewController = HomeViewController()

		window.makeKeyAndVisible()

		return true
	}

	func applicationWillResignActive(_: UIApplication) {
		TrikotConnectivityService.shared.stop()
	}

	func applicationDidEnterBackground(_: UIApplication) {}

	func applicationWillEnterForeground(_: UIApplication) {}

	func applicationDidBecomeActive(_: UIApplication) {
		TrikotConnectivityService.shared.start()
	}

	func applicationWillTerminate(_: UIApplication) {}

	func currentFlavor() -> Environment.Flavor {
		switch (Bundle.main.object(forInfoDictionaryKey: "Environment") ?? "debug") as! String {
			case "debug": return .debug
			case "qa": return .qa
			case "staging": return .staging
			default: return Environment.Flavor.release_
		}
	}
}
