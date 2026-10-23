# Midnight Academy - app + home screen widget (one project)
Build the APK with GitHub (no Android Studio):
1. github.com > New repository (private is fine), e.g. midnight-app.
2. Upload everything in this folder (drag the folders in). If the hidden .github folder is skipped: Add file > Create new file, name it `.github/workflows/build.yml`, paste the file's text.
3. Actions tab > Build APK (press Run workflow if it did not start). Wait ~5 min for the green tick.
4. Open the run > Artifacts > MidnightAcademy-apk > unzip > app-debug.apk.
5. Copy to your phone, tap it, allow "Install unknown apps" once.
6. Open Midnight Academy once, then long-press home screen > Widgets > Midnight Academy.
Notes: needs Android 10+. No internet permission; everything is stored on the phone.
Widget shows your current focus, as last synced while the app was open; tap it to open the app.
Backup: Vault > Export saves a .json into your Downloads folder. Uninstalling the app deletes your data, so export first.
