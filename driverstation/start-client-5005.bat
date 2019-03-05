@echo off

:: This can be used to start listening to a GStreamer UDP feed on port 5005
:: For convenience, creating a shortcut on the desktop to this script is recommended

gst-launch-1.0 udpsrc port=5005 ! "application/x-rtp" ! rtph264depay ! avdec_h264 ! videoflip method=clockwise ! videoflip method=clockwise ! autovideosink
:: Notably, this flips the camera feed vertically (top becomes bottom)
:: You may need to install the avdec_h264 decoder, as it might not come with the default installation of GStreamer