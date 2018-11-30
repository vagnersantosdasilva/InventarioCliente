@echo off

REM Criado por Vagner Santos da Silva
REM Gera listarSID  - > Percorre lista SID e Alimenta script percorreHKU com as linhas encontradas -> Se HKU encontrar registro , alimenta repostitorio-programas.ivt 

wscript scripts\listarSID.vbs 
for /f %%1 in (scripts\listaSID) do (wscript scripts\percorreHKU.vbs %%1 > ErroListaSID)
if exist scripts\ErroListaSID del scripts\ErroListaSID

exit