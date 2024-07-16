create table usuario(

    id bigint not null auto_increment,
    nombre varchar(100) not null unique,
    correoElectronico varchar(100) not null ,
    constraseña varchar(100) not null ,
--    perfil_id bigint not null,

    primary key(id)

--    constraint fk_usuario_perfil_id foreign key(perfil_id) references perfil(id)

);
