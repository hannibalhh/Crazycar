@echo off

@call "%~dp0\..\..\bin\setenv.bat"

@%JAVACMD% %LOOKUP_GROUPS_PROP% -classpath %SIGAR_JARS%;%GS_JARS%;%ANT_JARS%;"%JAVA_HOME%/lib/tools.jar" org.apache.tools.ant.Main %1
