create table usuarioDeMaquina
(
	login_responsavel varchar(30) not null primary key,
    nome varchar(30) not null,
    departamento varchar(30),
    funcao varchar(30)
);

create table maquina
(
	codigo_maquina int not null auto_increment,
	hostname varchar(30) not null unique,
    patrimonio varchar(15),
    modelo varchar(15),
    fabricante varchar(15),
    numero_serie varchar(20),
    login_responsavel varchar(30),
    tipo varchar(8),
    primary key (codigo_maquina)
);	

create table processador
(	
	codigo_maquina int not null,
    nome varchar(30) not null,
    numero_de_nucleos varchar(2),
    frequenciaMaxima varchar(15),
    numero_de_processadores_logicos varchar(6),
    fabricante varchar(15),
    status_processador varchar(5),
    primary key(codigo_maquina)
    
);	

create table unidadeArmazenamento
(
	codigo_maquina int not null,
    nome varchar(30),
    tamanho varchar(30),
    tipo_de_interface varchar(10),
    tipo_de_midea varchar(15),
    status_drive varchar(5),
    primary key(codigo_maquina)
);

create table software
(
	codigo_maquina int not null,
    nome varchar(40),
    arquitetura varchar(10),
    data_instalacao varchar(12),
    primary key(codigo_maquina)    
);

create table placaMae
(
	codigo_maquina int not null,
    modelo varchar(15),
    fabricante varchar(15),
    serial_placa varchar(15),
    status_drive varchar(5),
    primary key(codigo_maquina)
);    

create table sistemaOperacional(

	codigo_maquina int not null,
    codigo_sistema int not null,
    nome varchar(20) not null,
    arquitetura varchar(10),
    versao varchar(10),
    atualizacao varchar(15),
    primary key(codigo_maquina,codigo_sistema));

create table licencas 
(
	codigo_maquina int not null primary key,
    software varchar(35),
    chave varchar(30),
    data_expiracao date
);