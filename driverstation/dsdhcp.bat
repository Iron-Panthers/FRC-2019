@echo off

:: This script was for our practice field in the pre-season
:: It does not work with the FMS, and serves functionally no use in season competition

net session
:: Exists to check for admin permissions
:: If not enough permissions, puts "System error 5 has occured. ... Access is denied."
:: If permissions are sufficient, puts something else (often, "There are no entries in the list")

netsh interface ipv4 set address name="Wi-Fi" source=dhcp > nul
:: Changing IPv4 interface named "Wi-Fi" to DHCP
:: Needed to use IP addresses assigned automatically by a DHCP server, such as by a router
