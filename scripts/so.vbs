ForAppending = 8
ForWriting = 2
ForReading = 1  

'Objeto FSO
Set objFSO = CreateObject("Scripting.FileSystemObject")
		'Objeto para ler o arquivo
		Set objFileRead = objFSO.OpenTextFile("repositorio\so.ivt", ForReading)
		
		'Obejto para escrever
		Set objFileWrite = objFSO.OpenTextFile ("repositorio\so.ivt", ForWriting, True)


On Error Resume Next
strComputer = "."
Set objWMIService = GetObject("winmgmts:\\" & strComputer & "\root\cimv2")
Set colItems = objWMIService.ExecQuery("Select * from Win32_OperatingSystem",,48)
For Each objItem in colItems
	
	objFileWrite.WriteLine "Nome : " & objItem.Caption
	objFileWrite.WriteLine "Atualizacao : "  & objItem.CSDVersion
	objFileWrite.WriteLine "HostName : " & objItem.CSName
	objFileWrite.WriteLine "DataInstalacao : " & objItem.InstallDate
	objFileWrite.WriteLine "UltimoBoot : " & objItem.LastBootUpTime
	objFileWrite.WriteLine "Serial : " & objItem.SerialNumber
	objFileWrite.WriteLine "Status : " & objItem.Status
	objFileWrite.WriteLine "Versao : " & objItem.Version
	
Next
objFileWrite.Close
objFileRead.Close




