create table cidade (
                         id bigserial not null,
                         ibge_id bigint not null,
                         uf varchar(2) not null,
                         nome_cidade varchar(200) not null,
                         capital boolean not null,
                         longitude double precision not null,
                         latitude double precision not null,
                         nome_cidade_sem_acento varchar(200) not null,
                         nome_alternativo varchar(200),
                         micro_regiao varchar(200) not null,
                         meso_regiao varchar(200) not null,
                         constraint pk_cidade primary key(id)
);

    create index idx_cidade_ibge_id on cidade (ibge_id);
    create index idx_cidade_uf on cidade (uf);
    create index idx_cidade_nome on cidade (nome_cidade);
    create index idx_cidade_nome_sem_acento on cidade (nome_cidade_sem_acento);
    create index idx_cidade_capital on cidade (capital);
    create index idx_cidade_lat_lon on cidade (latitude, longitude);
    create index idx_cidade_micro_regiao on cidade (micro_regiao);
    create index idx_cidade_meso_regiao on cidade (meso_regiao);
