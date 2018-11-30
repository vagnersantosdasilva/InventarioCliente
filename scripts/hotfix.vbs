strComputer = "."


ForAppending = 8
ForWriting = 2
ForReading = 1  


strComputer = "."

Set objFSO = CreateObject("Scripting.FileSystemObject")

'Obejto para escrever

Set objFileRead = objFSO.OpenTextFile("repositorio\hotfix.ivt", ForReading)
Set objFileWrite = objFSO.OpenTextFile ("repositorio\hotfix.ivt", ForWriting, True)


Set objWMIService = GetObject("winmgmts:" _
& "{impersonationLevel=impersonate}!\\" & strComputer & "\root\cimv2")



Set colQuickFixes = objWMIService.ExecQuery _
("Select * from Win32_QuickFixEngineering")

For Each obj in colQuickFixes
	objFileWrite.WriteLine obj.Caption & "#" & obj.CSName & "#" & obj.Description & "#" & obj.FixComments & "#" & obj.HotFixID & "#" & obj.InstallDate & "#" & obj.InstalledBy & "#" & obj.InstalledOn & "#" & obj.Name & "#" & obj.ServicePackInEffect & "#" & obj.Status & "	"
Next

objFileWrite.close
objFileRead.Close


