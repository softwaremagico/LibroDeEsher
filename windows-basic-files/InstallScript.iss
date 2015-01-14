; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "@projectname@"
#define MyAppVersion "@version@"
#define MyAppPublisher "@group@"
#define MyAppURL "@url@"
#define MyAppExeName "@winexe@"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{2F6FF636-D27D-4C2A-8C0A-284D8E652B80}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={pf}\SoftwareMagico\{#MyAppName}
DefaultGroupName=SoftwareMagico\{#MyAppName}
LicenseFile=gnu.txt
OutputBaseFilename={#MyAppName}-{#MyAppVersion}
SetupIconFile=icon.ico
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "spanish"; MessagesFile: "compiler:Languages\Spanish.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked
Name: "quicklaunchicon"; Description: "{cm:CreateQuickLaunchIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked; OnlyBelowVersion: 0,6.1

[Files]
Source: "LibroDeEsher.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "lib"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "lib\*"; DestDir: "{app}\lib"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "manual"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "manual\*"; DestDir: "{app}\manual"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "rolemaster"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "rolemaster\*"; DestDir: "{app}\rolemaster"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "LibroDeEsher.jar"; DestDir: "{app}"; Flags: ignoreversion
Source: "icon.ico"; DestDir: "{app}"; Flags: ignoreversion
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{group}\Documentacion"; Filename: "{app}\manual\EsherManual.pdf"
Name: "{group}\{cm:UninstallProgram,{#MyAppName}}"; Filename: "{uninstallexe}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon
Name: "{userappdata}\Microsoft\Internet Explorer\Quick Launch\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: quicklaunchicon

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent

