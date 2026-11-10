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

Shortcut **Jumat Bersih** mencoba menampilkan portal Google Apps Script di dalam Safari pada iPhone melayang. Jika tampak abu-abu / ikon halaman rusak, Google kemungkinan menolak iframe dengan kebijakan X-Frame-Options/CSP. Klik **▣ Mini** atau **Buka Jendela Mini** agar aplikasi asli terbuka langsung dalam popup Chrome berukuran seperti HP; login dan formulir bisa digunakan tanpa iframe. Popup Chrome **tidak otomatis Always on Top**; pin memakai pengaturan/perangkat lunak Windows bila tersedia. Tombol **↗ Asli** tetap membuka tab normal. Bahkan bila HtmlOutput.setXFrameOptionsMode(HtmlService.XFrameOptionsMode.ALLOWALL) dipasang di server Apps Script, Google dapat memblokir iframe, dan ALLOWALL mengurangi perlindungan dari clickjacking; tidak disarankan mengaktifkan tanpa peninjauan keamanan portal. Situs lain pun dapat melarang preview iframe. Jika halaman kosong, gunakan tombol **Buka Website Asli**.

## Dashboard Ride Computer
Buka **https://firmansyahachmad.github.io/Firman-OS/ride.html**

Dashboard speedometer sebelumnya tetap ada: GPS speed/distance/time, mode demo, estimasi biaya BBM, Google Maps, dan launcher Apps Script.

## Android V0.4
APK Android tetap ada di GitHub Actions dan tidak berubah.

## Deployment
GitHub Pages: branch `main`, folder `/(root)`. File statis HTML/CSS/JavaScript tanpa server pribadi, billing, atau build frontend. Data shortcut dan favorit disimpan lokal di browser.
