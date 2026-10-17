@echo off
chcp 65001 >nul
echo ========================================================
echo   Отправка Slime Realm 2.0 в ваш GitHub-репозиторий...
echo ========================================================
git init
git config user.email "verysoftermen5637@users.noreply.github.com"
git config user.name "verysoftermen5637"
git remote remove origin 2>nul
git remote add origin https://github.com/verysoftermen5637/slime_realm.git
git add .
git commit -m "Slime Realm 2.0 Full Overhaul"
git branch -M main
git push -f -u origin main
echo.
echo ========================================================
echo   Готово! Откройте https://github.com/verysoftermen5637/slime_realm/actions
echo ========================================================
pause
