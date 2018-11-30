ForAppending = 8
ForWriting = 2
ForReading = 1  

On Error Resume Next
strComputer = "."

'Objeto FSO
Set objFSO = CreateObject("Scripting.FileSystemObject")
'Objeto para ler o arquivo
Set objFileRead = objFSO.OpenTextFile("repositorio\driveCDROM.ivt", ForReading)

'Obejto para escrever
Set objFileWrite = objFSO.OpenTextFile ("repositorio\driveCDROM.ivt", ForWriting, True)


Set objWMIService = GetObject("winmgmts:\\" & strComputer & "\root\cimv2")
Set colItems = objWMIService.ExecQuery("Select * from Win32_CDROMDrive",,48)
For Each objItem in colItems
    
    objFileWrite.WriteLine "Caption#" & objItem.Caption
    objFileWrite.WriteLine "Manufacturer#" & objItem.Manufacturer
    objFileWrite.WriteLine "MediaType#" & objItem.MediaType
    objFileWrite.WriteLine "Drive#" & objItem.Drive
    objFileWrite.WriteLine "InstallDate# " & objItem.InstallDate
    objFileWrite.WriteLine "Status#" & objItem.Status
    
Next
objFileWrite.close
objFileRead.Close
