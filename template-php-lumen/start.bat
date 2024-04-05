if exist vendor goto start
call composer install
:start
php -S localhost:8000 -t bootstrap
