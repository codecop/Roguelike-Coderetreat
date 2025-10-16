if exist vendor goto start
call composer install
:start
php -S 127.0.0.1:5014 -t bootstrap
