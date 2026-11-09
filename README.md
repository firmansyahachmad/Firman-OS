# Firman.OS

Firman.OS is a motorcycle-inspired ride dashboard. It now has two separate editions in the same repository:

- **Web V1.0** — runs directly in Chrome on Windows or Android, no APK or emulator required. Source: [index.html](./index.html).
- **Android V0.4** — native Android dashboard/APK built by the existing GitHub Actions workflow.

## Web V1.0

The web edition includes:

- Responsive cockpit dashboard (desktop, tablet, mobile)
- Live speed, elapsed time, estimated distance and average speed when supported by browser geolocation
- Fuel usage and cost estimation with adjustable efficiency and fuel price
- A clearly labeled **Demo Mode** for testing without GPS
- Google Maps destination launcher
- Direct **Web App** button to the Google Apps Script deployment provided by the project owner
- Local trip history and local preferences (stored only in the browser, not synchronized to a server)

### Publish to GitHub Pages (one-time setup)

1. Open the repository **Settings → Pages**: https://github.com/firmansyahachmad/Firman-OS/settings/pages
2. Under **Build and deployment**, choose **Source: Deploy from a branch**.
3. Set branch to **main** and folder to **/(root)**.
4. Click **Save**, then visit the site URL shown on that page after publication.

Expected public address, *once GitHub Pages is enabled and published*:

**https://firmansyahachmad.github.io/Firman-OS/**

The `index.html` file is at the root of `main`. No npm install, backend, billing, or separate hosting are required. Android files are preserved.

### Run in Chrome on Windows

- Open the GitHub Pages address after publication.
- Click **OPEN GOOGLE APPS SCRIPT** to open the configured Apps Script app in a new browser tab. Its sign-in and access are controlled separately by Apps Script.
- Click **TRY DEMO MODE** to test the dashboard without real GPS; the displayed speed and distance are synthetic.
- For real location tracking, click **START GPS RIDE** and grant location permission. Browser geolocation requires HTTPS, and a typical Windows laptop may not provide real GPS speed or precise location. Keep the browser open during tracking.
- **END RIDE** stops tracking and stores a summary locally in that browser. **CLEAR** deletes local trip history.
- Google Maps navigation opens a separate tab.

**Safety and limitations:** Do not interact with the dashboard while driving. Do not treat browser-based estimates as a vehicle odometer, fuel sensor, or validated navigation instrument. History does not sync between computers. The Google Apps Script link is a launcher, not an embedded backend or shared login.

## Android V0.4

- Landscape riding dashboard
- Live GPS speed and estimated trip distance
- Trip duration
- Estimated fuel usage and fuel cost
- Google Maps navigation handoff
- **WEB APP ↗** launcher (opens the Apps Script URL in a browser)

Download the Android build from the repository's **Actions** tab and install the APK on an Android device. APK files cannot run natively in Windows without an emulator.

## Roadmap

Route/ETA, traffic information, richer ride UI, notifications, trip history improvements, and configurable vehicle profiles.
