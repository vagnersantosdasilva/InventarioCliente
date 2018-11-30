'Const HKEY_CLASSES_ROOT   = &H80000000

'Const HKEY_CURRENT_USER   = &H80000001

'Const HKEY_LOCAL_MACHINE  = &H80000002

'Const HKEY_USERS          = &H80000003

'"S-1-5-21-1307239666-1480440652-3299296526-1001\Software\Microsoft\Windows\CurrentVersion\Uninstall\" 
strSID = wscript.arguments(0) '"Chave testada :S-1-5-21-3095905463-273531772-991149333-1001"
ForAppending = 8
ForWriting = 2
ForReading = 1  
Const HKU_USERS = &H80000003

Set objCtx = CreateObject("WbemScripting.SWbemNamedValueSet")
	objCtx.Add "__ProviderArchitecture", 64
	objCtx.Add "__RequiredArchitecture", TRUE
	
Set objLocator = CreateObject("Wbemscripting.SWbemLocator")
Set objServices = objLocator.ConnectServer(strComputer,"root\default",srtUser,strSenha,,,,objCtx)
Set objStdRegProv = objServices.Get("StdRegProv") 

'Objeto FSO
Set objFSO = CreateObject("Scripting.FileSystemObject")
'Objeto para ler o arquivo
Set objFileRead = objFSO.OpenTextFile("repositorio/programas.ivt", ForReading)

'Obejto para escrever
'Set objFileWrite = objFSO.OpenTextFile ("programas.txt", ForWriting, True)

'Objeto para acrescentar sem destruir arquivo previo
Set objFileWrite = objFSO.OpenTextFile ("repositorio/programas.ivt", ForAppending, True)

'objFileWrite.WriteLine "32-bit Applications"
'objFileWrite.WriteLine "--------------------------------------------------------------"


Call GetApplications 


Sub GetApplications 
On Error Resume next

' Use ExecMethod to call the GetStringValue method
Set Inparams = objStdRegProv.Methods_("EnumKey").Inparameters
Inparams.Hdefkey = HKU_USERS
Inparams.Ssubkeyname = strSID & "\Software\Microsoft\Windows\CurrentVersion\Uninstall\" 




Set Outparams = objStdRegProv.ExecMethod_("EnumKey", Inparams,,objCtx) 
set Outparams1 = objStdRegProv.ExecMethod_("EnumKey", Inparams,,objCtx) 
Set Outparams2 = objStdRegProv.ExecMethod_("EnumKey", Inparams,,objCtx)
Set Outparams3 = objStdRegProv.ExecMethod_("EnumKey", Inparams,,objCtx)

For Each strSubKey In Outparams.snames 

	Set Inparams = objStdRegProv.Methods_("GetStringValue").Inparameters
		Inparams.Hdefkey = HKU_USERS
		Inparams.Ssubkeyname = strSID & "\Software\Microsoft\Windows\CurrentVersion\Uninstall\"  & strSubKey
		Inparams.Svaluename = "DisplayName" 
	Set Outparams = objStdRegProv.ExecMethod_("GetStringValue", Inparams,,objCtx)

	Set Inparams =	Inparams = objStdRegProv.Methods_("GetStringValue").Inparameters
		Inparams.Hdefkey = HKU_USERS
		Inparams.Ssubkeyname = strSID & "\Software\Microsoft\Windows\CurrentVersion\Uninstall\"  & strSubKey
		Inparams.Svaluename = "InstallDate" 
	Set Outparams1 = objStdRegProv.ExecMethod_("GetStringValue", Inparams,,objCtx)
	
	Set Inparams = objStdRegProv.Methods_("GetStringValue").Inparameters
		Inparams.Hdefkey = HKU_USERS
		Inparams.Ssubkeyname = strSID & "\Software\Microsoft\Windows\CurrentVersion\Uninstall\"  & strSubKey
		Inparams.Svaluename = "InstallLocation" 
	Set Outparams2 = objStdRegProv.ExecMethod_("GetStringValue", Inparams,,objCtx)
	
	Set Inparams = objStdRegProv.Methods_("GetStringValue").Inparameters
		Inparams.Hdefkey = HKU_USERS
		Inparams.Ssubkeyname = strSID & "\Software\Microsoft\Windows\CurrentVersion\Uninstall\"  & strSubKey
		Inparams.Svaluename = "UninstallString" 
	Set Outparams3 = objStdRegProv.ExecMethod_("GetStringValue", Inparams,,objCtx)
	

	if (Outparams.sValue) <> "" then  objFileWrite.WriteLine replace(Outparams.SValue &";" & "PerfilUser" & "; " & Outparams1.SValue & "; " & Outparams2.SValue & "; " & Outparams3.SValue, "\","/")

	
	'UninstallString 	
	'DisplayIcon
	'DisplayVersion
	'HelpLink	

'Inparams.Svaluename = "QuietDisplayName"
'set Outparams = objStdRegProv.ExecMethod_("GetStringValue", Inparams,,objCtx)
'wscript.echo Outparams.SValue 


Next 
objFileWrite.close
objFileRead.Close

End Sub
