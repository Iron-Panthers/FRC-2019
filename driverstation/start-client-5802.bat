@echo off

:: This can be used to start listening to a GStreamer UDP (on port 5802)
:: Do not change the port number, as it is one of the FMS-compatible UDP ports available to FRC teams. 
:: Changing it will break things, so do not change it unless you totally know what you are doing.

:: For convenience, creating a shortcut on the desktop to this script is recommended

gst-launch-1.0 udpsrc port=5802 ! "application/x-rtp" ! rtph264depay ! avdec_h264 ! videoflip method=clockwise ! videoflip method=clockwise ! autovideosink
:: Notably, this flips the camera feed vertically (top becomes bottom)
:: You may need to install the avdec_h264 decoder, as it might not come with the default installation of GStreamer

pause
:: If the script fails then wait for confirmation before closing