REM This is a window batch file to run test using maven command	 REM is batch files way of commenting out
cd\ REM this command takes you to the root
cd "C:\Users\medin\git\SimpleKayakLocalRepo\SimpleKayak"
mvn test -Dtest=com.tests.KayakProjectTests
pause
