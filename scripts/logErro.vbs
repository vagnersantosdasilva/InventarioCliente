ForAppending = 8
ForWriting = 2
ForReading = 1
'-----------------------------------------Leitura e Escrita de arquivo ---------------------------------------------------------------------
'Objeto FSO
Set objFSO = CreateObject("Scripting.FileSystemObject")
'Objeto para ler o arquivo
Set objFileRead = objFSO.OpenTextFile("repositorio\erros.ivt", ForReading)
'Obejto para escrever
Set objFileWrite = objFSO.OpenTextFile ("repositorio\erros.ivt", ForWriting, True)

'-------------------------------------------------------------------------------------------------------------------------------------------

Set objWMIService = GetObject("winmgmts:root\cimv2")
'Set colEvents = objWMIService.ExecQuery ("Select * from Win32_NTLogEvent where LogFile='Application'")
'WScript.Echo "Application Log Count:" & colEvents.count

Set colEvents = objWMIService.ExecQuery ("Select * from Win32_NTLogEvent where LogFile='System'")
'WScript.Echo "System Log Count:" & colEvents.count

'Set colEvents = objWMIService.ExecQuery ("Select * from Win32_NTLogEvent where LogFile='Security'")
'WScript.Echo "Security Log Count:" & colEvents.count

On Error Resume Next

	For Each objEvent in colEvents
		If objEvent.Type = "Nível Crítico" or objEvent.Type ="Critical Level" or objEvent.Type ="Erro" or objEvent.Type="Error" Then       
			objFileWrite.WriteLine	"["
			objFileWrite.WriteLine  "Category: " & objEvent.Category
			'objFileWrite.WriteLine  "CategoryString: " & objEvent.CategoryString
			'objFileWrite.WriteLine  "ComputerName: " & objEvent.ComputerName
			'objFileWrite.WriteLine  "Data: " & objEvent.Data
			objFileWrite.WriteLine  "EventCode: " & objEvent.EventCode
			'objFileWrite.WriteLine  "EventIdentifier: " & objEvent.EventIdentifier
			'objFileWrite.WriteLine  "InsertionStrings: " & objEvent.InsertionStrings
			objFileWrite.WriteLine  "LogFile: " & objEvent.Logfile
			objFileWrite.WriteLine  "Message: " & objEvent.Message
			objFileWrite.WriteLine  "RecordNumber: " & objEvent.RecordNumber 
			objFileWrite.WriteLine  "SourceName: " & objEvent.SourceName 
			objFileWrite.WriteLine  "TimeGenerated:" & objEvent.TimeGenerated 
			'objFileWrite.WriteLine  "TimeWritten: " & objEvent.TimeWritten 
			objFileWrite.WriteLine  "Type: " & objEvent.Type
			objFileWrite.WriteLine  "User: " & objEvent.User
			objFileWrite.WriteLine	"]"
			  
		End If
	Next
	objFileWrite.close
	objFileRead.Close
