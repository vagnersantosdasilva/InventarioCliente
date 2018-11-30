ForAppending = 8
ForWriting = 2
ForReading = 1  
'strComputer = wscript.arguments(0)
'srtUser=wscript.arguments(1)
'strSenha=wscript.arguments(2)

Const HKLM = &h80000002

Set objCtx = CreateObject("WbemScripting.SWbemNamedValueSet")
	objCtx.Add "__ProviderArchitecture", 32
	objCtx.Add "__RequiredArchitecture", TRUE
	
Set objLocator = CreateObject("Wbemscripting.SWbemLocator")
Set objServices = objLocator.ConnectServer(strComputer,"root\default",srtUser,strSenha,,,,objCtx)
Set objStdRegProv = objServices.Get("StdRegProv") 

'Objeto FSO
Set objFSO = CreateObject("Scripting.FileSystemObject")
'Objeto para ler o arquivo
Set objFileRead = objFSO.OpenTextFile("repositorio\programas.ivt", ForReading)

'Obejto para escrever
Set objFileWrite = objFSO.OpenTextFile ("repositorio\programas.ivt", ForWriting, True)


'objFileWrite.WriteLine "32-bit Applications"
'objFileWrite.WriteLine "--------------------------------------------------------------"


Call GetApplications 

	Sub GetApplications 
		On Error Resume next

		' Use ExecMethod to call the GetStringValue method
		Set Inparams = objStdRegProv.Methods_("EnumKey").Inparameters
			Inparams.Hdefkey = HKLM
			Inparams.Ssubkeyname = "Software\Microsoft\Windows\CurrentVersion\Uninstall\" 


		Set Outparams = objStdRegProv.ExecMethod_("EnumKey", Inparams,,objCtx) 
		Set Outparams1 = objStdRegProv.ExecMethod_("EnumKey", Inparams,,objCtx) 
		Set Outparams2 = objStdRegProv.ExecMethod_("EnumKey", Inparams,,objCtx)
		Set Outparams3 = objStdRegProv.ExecMethod_("EnumKey", Inparams,,objCtx)

		For Each strSubKey In Outparams.snames 

			Set Inparams = objStdRegProv.Methods_("GetStringValue").Inparameters
				Inparams.Hdefkey = HKLM
				Inparams.Ssubkeyname = "Software\Microsoft\Windows\CurrentVersion\Uninstall\" & strSubKey
				Inparams.Svaluename = "DisplayName" 
				Set Outparams = objStdRegProv.ExecMethod_("GetStringValue", Inparams,,objCtx)

			Set Inparams = objStdRegProv.Methods_("GetStringValue").Inparameters
				Inparams.Hdefkey = HKLM
				Inparams.Ssubkeyname = "Software\Microsoft\Windows\CurrentVersion\Uninstall\" & strSubKey
				Inparams.Svaluename = "InstallDate" 
				Set Outparams1 = objStdRegProv.ExecMethod_("GetStringValue", Inparams,,objCtx)
			
			
			Set Inparams = objStdRegProv.Methods_("GetStringValue").Inparameters
				Inparams.Hdefkey = HKLM
				Inparams.Ssubkeyname = "Software\Microsoft\Windows\CurrentVersion\Uninstall\" & strSubKey
				Inparams.Svaluename = "InstallLocation" 
				Set Outparams2 = objStdRegProv.ExecMethod_("GetStringValue", Inparams,,objCtx)
			
			
			Set Inparams = objStdRegProv.Methods_("GetStringValue").Inparameters
				Inparams.Hdefkey = HKLM
				Inparams.Ssubkeyname = "Software\Microsoft\Windows\CurrentVersion\Uninstall\" & strSubKey
				Inparams.Svaluename = "UninstallString" 
				Set Outparams3 = objStdRegProv.ExecMethod_("GetStringValue", Inparams,,objCtx)
			
			Set Inparams = objStdRegProv.Methods_("GetStringValue").Inparameters
				Inparams.Hdefkey = HKLM
				Inparams.Ssubkeyname = "Software\Microsoft\Windows\CurrentVersion\Uninstall\" & strSubKey
				Inparams.Svaluename = "InstalledOn" 
				Set Outparams4 = objStdRegProv.ExecMethod_("GetStringValue", Inparams,,objCtx)
			
						
				if (Outparams.sValue) <> "" then  objFileWrite.WriteLine replace (Outparams.SValue &";" & "32-bit" & "; " & Outparams1.SValue & "; " & Outparams2.SValue & "; " & Outparams3.SValue & ";" & Outparams4.SValue,"\","/")
				
			
		Next 
		objFileWrite.close
		objFileRead.Close

	End Sub

