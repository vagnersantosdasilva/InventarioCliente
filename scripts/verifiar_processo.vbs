strComputer = "."

Set objWMIService = GetObject("winmgmts:" _
	& "{impersonationLevel=impersonate}!\\" _
	& strComputer & "\root\cimv2")

Set colProcess = objWMIService.ExecQuery _
	("Select * from Win32_Process where Name='eclipse.exe'")
Processo_Existe = False

For Each objProcess in colProcess
	Processo_Existe = True
	exit for
Next

if not Processo_Existe then
	Set objShell = WScript.CreateObject("WScript.Shell")
	msgbox("não tem") 
end if

if Processo_Existe then
	Set objShell = WScript.CreateObject("WScript.Shell")
	msgbox("tem") 
end if	