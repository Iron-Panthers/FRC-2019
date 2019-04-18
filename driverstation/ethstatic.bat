@echo off

net session
:: Exists to check for admin permissions
:: If not enough permissions, puts "System error 5 has occured. ... Access is denied."
:: If permissions are sufficient, puts something else (often, "There are no entries in the list")

netsh interface ipv4 set address name="Ethernet 6" static 10.50.26.5 255.0.0.0 10.50.26.1 > nul
:: Changing IPv4 interface named "Ethernet 6" to static, with the following configs:
:: IP Address: 10.50.26.5
:: Subnet Mask: 255.0.0.0
:: Default Gateway: 10.50.26.1
