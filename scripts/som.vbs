ForAppending = 8
ForWriting = 2
ForReading = 1  
strComputer = "."
On Error Resume Next
Set objFSO = CreateObject("Scripting.FileSystemObject")
'Obejto para escrever
Set objFileRead = objFSO.OpenTextFile("repositorio\som.ivt", ForReading)
Set objFileWrite = objFSO.OpenTextFile ("repositorio\som.ivt", ForWriting, True)
Set objWMIService = GetObject("winmgmts:\\" & strComputer & "\root\cimv2")
Set colItems = objWMIService.ExecQuery("Select * from Win32_SoundDevice",,48)
For Each objItem in colItems
    'objFileWrite.WriteLine "Availability: " & objItem.Availability
    objFileWrite.WriteLine "Caption: " & objItem.Caption
    'objFileWrite.WriteLine "ConfigManagerErrorCode: " & objItem.ConfigManagerErrorCode
    'objFileWrite.WriteLine "ConfigManagerUserConfig: " & objItem.ConfigManagerUserConfig
    'objFileWrite.WriteLine "CreationClassName: " & objItem.CreationClassName
    objFileWrite.WriteLine "Description: " & objItem.Description
    'objFileWrite.WriteLine "DeviceID: " & objItem.DeviceID
    'objFileWrite.WriteLine "DMABufferSize: " & objItem.DMABufferSize
    'objFileWrite.WriteLine "ErrorCleared: " & objItem.ErrorCleared
    'objFileWrite.WriteLine "ErrorDescription: " & objItem.ErrorDescription
    objFileWrite.WriteLine "InstallDate: " & objItem.InstallDate
    'objFileWrite.WriteLine "LastErrorCode: " & objItem.LastErrorCode
    objFileWrite.WriteLine "Manufacturer: " & objItem.Manufacturer
    'objFileWrite.WriteLine "MPU401Address: " & objItem.MPU401Address
    objFileWrite.WriteLine "Name: " & objItem.Name
    'objFileWrite.WriteLine "PNPDeviceID: " & objItem.PNPDeviceID
    'objFileWrite.WriteLine "PowerManagementCapabilities: " & objItem.PowerManagementCapabilities
    'objFileWrite.WriteLine "PowerManagementSupported: " & objItem.PowerManagementSupported
    objFileWrite.WriteLine "ProductName: " & objItem.ProductName
    objFileWrite.WriteLine "Status: " & objItem.Status
    'objFileWrite.WriteLine "StatusInfo: " & objItem.StatusInfo
    'objFileWrite.WriteLine "SystemCreationClassName: " & objItem.SystemCreationClassName
    'objFileWrite.WriteLine "SystemName: " & objItem.SystemName
Next
objFileWrite.close
objFileRead.Close