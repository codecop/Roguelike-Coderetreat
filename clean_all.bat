@rem Java
cd template-java-spark
rmdir /S /Q target
cd ..
cd inventory
rmdir /S /Q target
cd ..

@rem PHP
cd template-php-lumen
rmdir /S /Q vendor
rmdir /S /Q storage
del .phpunit.result.cache
del composer.lock
cd ..

@rem TS
cd template-ts-express
rmdir /S /Q dist
rmdir /S /Q node_modules
del package-lock.json
cd ..
cd stats
rmdir /S /Q dist
rmdir /S /Q node_modules
del package-lock.json
cd ..
