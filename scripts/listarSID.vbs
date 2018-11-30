ForAppending = 8
ForWriting = 2
ForReading = 1  
'strComputer = wscript.arguments(0)
'srtUser=wscript.arguments(1)
'strSenha=wscript.arguments(2)
strComputer = "."
srtUser =""
strSenha =""



'Objeto FSO
Set objFSO = CreateObject("Scripting.FileSystemObject")
'Objeto para ler o arquivo
Set objFileRead = objFSO.OpenTextFile("scripts/listaSID", ForReading)

'Obejto para escrever
Set objFileWrite = objFSO.OpenTextFile ("scripts/listaSID", ForWriting, True)


'objFileWrite.WriteLine "32-bit Applications"
'objFileWrite.WriteLine "--------------------------------------------------------------"



Dim objWMIService, colItems

Set objWMIService = GetObject( "winmgmts:\\" & strComputer & "\root\cimv2")
Set colItems = objWMIService.ExecQuery("Select * from Win32_Account",,48)
For Each objItem in colItems
	    'Wscript.Echo "Caption: " & objItem.Caption
	    'Wscript.Echo "Description: " & objItem.Description
	    'Wscript.Echo "Domain: " & objItem.Domain
	    'Wscript.Echo "InstallDate: " & objItem.InstallDate
	    'Wscript.Echo "Name: " & objItem.Name
	    'Wscript.Echo "SID: " & objItem.SID
	    'Wscript.Echo "SIDType: " & objItem.SIDType
		'Wscript.Echo "Status: " & objItem.Status
		objFileWrite.WriteLine objItem.SID
Next



