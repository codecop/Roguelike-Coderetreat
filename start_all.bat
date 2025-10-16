cls
rem call \Develop\Go\setenv1.25.bat
go version
java --version
rem call \Develop\JavaScript\setenv-10.bat
node --version
rem call \Develop\PHP\setenv82.bat
php --version
rem call \Develop\Python\setenv3_13.bat
python --version

for %%a in (
stats Motiv8 team-ab sellerz AirWaves jj BRUTE_FORCE alu room-green_test
) do start "%%a" /D %%a CMD /T:87 /K start.bat && pause
