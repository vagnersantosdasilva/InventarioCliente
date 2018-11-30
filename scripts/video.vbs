ForAppending = 8
ForWriting = 2
ForReading = 1  
on Error Resume Next
strComputer = "."
Set objFSO = CreateObject("Scripting.FileSystemObject")
'Obejto para escrever
Set objFileRead = objFSO.OpenTextFile("repositorio\video.ivt", ForReading)
Set objFileWrite = objFSO.OpenTextFile ("repositorio\video.ivt", ForWriting, True)
Set objWMIService = GetObject("winmgmts:\\" & strComputer & "\root\cimv2")
Set colItems = objWMIService.ExecQuery("Select * from Win32_VideoController",,48)

For Each objItem in colItems

	objFileWrite.WriteLine "AcceleratorCapabilities: " & objItem.AcceleratorCapabilities
    objFileWrite.WriteLine "AdapterCompatibility: " & objItem.AdapterCompatibility
    objFileWrite.WriteLine "AdapterDACType: " & objItem.AdapterDACType
    objFileWrite.WriteLine "AdapterRAM: " & objItem.AdapterRAM
    objFileWrite.WriteLine "Availability: " & objItem.Availability
    objFileWrite.WriteLine "CapabilityDescriptions: " & objItem.CapabilityDescriptions
    objFileWrite.WriteLine "Caption: " & objItem.Caption
    objFileWrite.WriteLine "ColorTableEntries: " & objItem.ColorTableEntries
    objFileWrite.WriteLine "ConfigManagerErrorCode: " & objItem.ConfigManagerErrorCode
    objFileWrite.WriteLine "ConfigManagerUserConfig: " & objItem.ConfigManagerUserConfig
    objFileWrite.WriteLine "CreationClassName: " & objItem.CreationClassName
    objFileWrite.WriteLine "CurrentBitsPerPixel: " & objItem.CurrentBitsPerPixel
    objFileWrite.WriteLine "CurrentHorizontalResolution: " & objItem.CurrentHorizontalResolution
    objFileWrite.WriteLine "CurrentNumberOfColors: " & objItem.CurrentNumberOfColors
    objFileWrite.WriteLine "CurrentNumberOfColumns: " & objItem.CurrentNumberOfColumns
    objFileWrite.WriteLine "CurrentNumberOfRows: " & objItem.CurrentNumberOfRows
    objFileWrite.WriteLine "CurrentRefreshRate: " & objItem.CurrentRefreshRate
    objFileWrite.WriteLine "CurrentScanMode: " & objItem.CurrentScanMode
    objFileWrite.WriteLine "CurrentVerticalResolution: " & objItem.CurrentVerticalResolution
    objFileWrite.WriteLine "Description: " & objItem.Description
    objFileWrite.WriteLine "DeviceID: " & objItem.DeviceID
    objFileWrite.WriteLine "DeviceSpecificPens: " & objItem.DeviceSpecificPens
    objFileWrite.WriteLine "DitherType: " & objItem.DitherType
    objFileWrite.WriteLine "DriverDate: " & objItem.DriverDate
    objFileWrite.WriteLine "DriverVersion: " & objItem.DriverVersion
    objFileWrite.WriteLine "ErrorCleared: " & objItem.ErrorCleared
    objFileWrite.WriteLine "ErrorDescription: " & objItem.ErrorDescription
    objFileWrite.WriteLine "ICMIntent: " & objItem.ICMIntent
    objFileWrite.WriteLine "ICMMethod: " & objItem.ICMMethod
    objFileWrite.WriteLine "InfFilename: " & objItem.InfFilename
    objFileWrite.WriteLine "InfSection: " & objItem.InfSection
    objFileWrite.WriteLine "InstallDate: " & objItem.InstallDate
    objFileWrite.WriteLine "InstalledDisplayDrivers: " & objItem.InstalledDisplayDrivers
    objFileWrite.WriteLine "LastErrorCode: " & objItem.LastErrorCode
    objFileWrite.WriteLine "MaxMemorySupported: " & objItem.MaxMemorySupported
    objFileWrite.WriteLine "MaxNumberControlled: " & objItem.MaxNumberControlled
    objFileWrite.WriteLine "MaxRefreshRate: " & objItem.MaxRefreshRate
    objFileWrite.WriteLine "MinRefreshRate: " & objItem.MinRefreshRate
    objFileWrite.WriteLine "Monochrome: " & objItem.Monochrome
    objFileWrite.WriteLine "Name: " & objItem.Name
    objFileWrite.WriteLine "NumberOfColorPlanes: " & objItem.NumberOfColorPlanes
    objFileWrite.WriteLine "NumberOfVideoPages: " & objItem.NumberOfVideoPages
    objFileWrite.WriteLine "PNPDeviceID: " & objItem.PNPDeviceID
    objFileWrite.WriteLine "PowerManagementCapabilities: " & objItem.PowerManagementCapabilities
    objFileWrite.WriteLine "PowerManagementSupported: " & objItem.PowerManagementSupported
    objFileWrite.WriteLine "ProtocolSupported: " & objItem.ProtocolSupported
    objFileWrite.WriteLine "ReservedSystemPaletteEntries: " & objItem.ReservedSystemPaletteEntries
    objFileWrite.WriteLine "SpecificationVersion: " & objItem.SpecificationVersion
    objFileWrite.WriteLine "Status: " & objItem.Status
    objFileWrite.WriteLine "StatusInfo: " & objItem.StatusInfo
    objFileWrite.WriteLine "SystemCreationClassName: " & objItem.SystemCreationClassName
    objFileWrite.WriteLine "SystemName: " & objItem.SystemName
    objFileWrite.WriteLine "SystemPaletteEntries: " & objItem.SystemPaletteEntries
    objFileWrite.WriteLine "TimeOfLastReset: " & objItem.TimeOfLastReset
    objFileWrite.WriteLine "VideoArchitecture: " & objItem.VideoArchitecture
    objFileWrite.WriteLine "VideoMemoryType: " & objItem.VideoMemoryType
    objFileWrite.WriteLine "VideoMode: " & objItem.VideoMode
    objFileWrite.WriteLine "VideoModeDescription: " & objItem.VideoModeDescription
    objFileWrite.WriteLine "VideoProcessor: " & objItem.VideoProcessor
	    
Next

objFileWrite.close
objFileRead.Close