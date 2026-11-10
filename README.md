# Firman.OS

Firman.OS dapat dibuka dari Chrome Windows tanpa APK, emulator, instalasi, atau akses administrator.

## Halaman utama — Firman.OS Floating (iPhone Simulator V6)
Buka **https://firmansyahachmad.github.io/Firman-OS/**

Versi ini memulihkan simulator iPhone dari Firman iOS Web Simulator V5 yang sebelumnya dipakai. Termasuk Home Screen, jam & Dynamic Island, Safari/URL preview, shortcut aplikasi, favorit, pilihan ukuran/wallpaper, serta tombol **Buka FIRMAN.OS Melayang**.

**Jendela melayang:**
1. Buka website di **Chrome Windows/Edge terbaru** (HTTPS).
2. Klik tombol **Buka FIRMAN.OS Melayang** di panel kiri.
3. Jika fitur Document Picture-in-Picture tersedia, tampilan ponsel pindah ke jendela kecil **Always on Top**. Jendela dapat digeser/diubah ukurannya. Biarkan tab browser utama terbuka.
4. Tutup jendela melayang untuk mengembalikan simulator ke halaman utama.

**Mode alternatif:** Jika Document Picture-in-Picture tidak tersedia atau dinonaktifkan kebijakan perusahaan, tombol akan mencoba membuka popup Chrome biasa; popup tersebut **tidak selalu di atas jendela lain**.

**V7:** Menekan ikon **Jumat Bersih** di iPhone melayang sekarang **langsung membuka jendela Chrome mini seukuran HP** sebagai halaman asli (top-level), tidak lagi mencoba iframe yang menyebabkan layar abu-abu. Form dan login dapat bekerja seperti halaman Chrome biasa. Jika popup diblokir, izinkan pop-up untuk situs Firman.OS, atau gunakan tombol **↗ Asli**. Popup Chrome tidak otomatis **Always on Top**. Untuk mempertahankan jendela mini di atas Excel, pengguna Windows yang memiliki Microsoft PowerToys dapat mengaktifkan **Always On Top** dengan shortcut **Win + Ctrl + T**. iPhone PiP tetap digunakan sebagai launcher, bukan wadah halaman Google. Akses aplikasi mengikuti otorisasi pada Google Apps Script. Situs lain pun dapat melarang preview iframe. Jika halaman kosong, gunakan tombol **Buka Website Asli**.

## Dashboard Ride Computer
Buka **https://firmansyahachmad.github.io/Firman-OS/ride.html**

Dashboard speedometer sebelumnya tetap ada: GPS speed/distance/time, mode demo, estimasi biaya BBM, Google Maps, dan launcher Apps Script.

## Android V0.4
APK Android tetap ada di GitHub Actions dan tidak berubah.

## Deployment
GitHub Pages: branch `main`, folder `/(root)`. File statis HTML/CSS/JavaScript tanpa server pribadi, billing, atau build frontend. Data shortcut dan favorit disimpan lokal di browser.
