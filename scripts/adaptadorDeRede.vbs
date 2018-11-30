ForAppending = 8
ForWriting = 2
ForReading = 1  

On Error Resume Next
strComputer = "."

'Objeto FSO
Set objFSO = CreateObject("Scripting.FileSystemObject")
'Objeto para ler o arquivo
Set objFileRead = objFSO.OpenTextFile("repositorio\adaptadoresDeRede.ivt", ForReading)

'Obejto para escrever
Set objFileWrite = objFSO.OpenTextFile ("repositorio\adaptadoresDeRede.ivt", ForWriting, True)

Set objWMIService = GetObject("winmgmts:\\" & strComputer & "\root\cimv2")
Set colItems = objWMIService.ExecQuery("Select * from Win32_NetworkAdapter",,48)
For Each objItem in colItems
    
    objFileWrite.WriteLine "Name# " & objItem.Name
    objFileWrite.WriteLine "Description# " & objItem.Description
    objFileWrite.WriteLine "Index# " & objItem.Index
    objFileWrite.WriteLine "MACAddress# " & objItem.MACAddress
    objFileWrite.WriteLine "Manufacturer# " & objItem.Manufacturer
    objFileWrite.WriteLine "Speed# " & objItem.Speed
    objFileWrite.WriteLine "Status# " & objItem.Status
    objFileWrite.WriteLine "TimeOfLastReset# " & objItem.TimeOfLastReset
Next
objFileWrite.close
objFileRead.Close