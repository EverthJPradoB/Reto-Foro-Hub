create table respuesta(
    id bigint not null auto_increment,
    mensaje varchar(100) not null,
    fecha datetime not null,
    topico_id bigint not null,
    autor_id bigint not null,
    solucion tinyint not null,
    primary key(id),
    constraint fk_topico_id foreign key(topico_id) references topico(id),
    constraint fk_autor_id foreign key(autor_id) references usuario(id)
);

