
    create table T_INSERTED_TRAINING_DECISIONS (
        T_INSERTED_DATA_ID bigint not null,
        trainingDecisions_ID bigint not null,
        trainingDecisions_KEY varchar(255),
        primary key (T_INSERTED_DATA_ID, trainingDecisions_KEY)
    );

    create table T_LEVEL_UP_CHARACTERISTICS_UPDATES (
        T_LEVELUP_ID bigint not null,
        characteristicsUpdates_ID bigint not null
    );

    create table T_LEVEL_UP_TRAINING_DECISIONS (
        T_LEVELUP_ID bigint not null,
        trainingDecisions_ID bigint not null,
        trainingDecisions_KEY varchar(255),
        primary key (T_LEVELUP_ID, trainingDecisions_KEY)
    );

    alter table T_APPEARANCE 
        drop constraint UK_fvvhnk8c7ii3jvbmo5b6fg5un;

    alter table T_APPEARANCE 
        add constraint UK_fvvhnk8c7ii3jvbmo5b6fg5un  unique (comparationId);

    alter table T_CATEGORY_COST 
        drop constraint UK_8jn0hgs0qu2147j9ge5siu0qq;

    alter table T_CATEGORY_COST 
        add constraint UK_8jn0hgs0qu2147j9ge5siu0qq  unique (comparationId);

    alter table T_CHARACTERPLAYER 
        drop constraint UK_goif6qdq0k03683pf598f958;

    alter table T_CHARACTERPLAYER 
        add constraint UK_goif6qdq0k03683pf598f958  unique (comparationId);

    alter table T_CULTUREDECISIONS 
        drop constraint UK_i4vxctno4axrv6clm66mmrngb;

    alter table T_CULTUREDECISIONS 
        add constraint UK_i4vxctno4axrv6clm66mmrngb  unique (comparationId);

    alter table T_INSERTED_TRAINING_DECISIONS 
        drop constraint UK_ahxpf8sitwcloo3ha8nft78k3;

    alter table T_INSERTED_TRAINING_DECISIONS 
        add constraint UK_ahxpf8sitwcloo3ha8nft78k3  unique (trainingDecisions_ID);

    alter table T_LEVELUP 
        drop constraint UK_s260ukdop7lmbms1wy8vru5mp;

    alter table T_LEVELUP 
        add constraint UK_s260ukdop7lmbms1wy8vru5mp  unique (comparationId);

    alter table T_LEVEL_UP_CHARACTERISTICS_UPDATES 
        drop constraint UK_mayw23itpoug630tuos9ycyjx;

    alter table T_LEVEL_UP_CHARACTERISTICS_UPDATES 
        add constraint UK_mayw23itpoug630tuos9ycyjx  unique (characteristicsUpdates_ID);

    alter table T_LEVEL_UP_TRAINING_DECISIONS 
        drop constraint UK_e55t66sktxlv50j5s7jk3j7x1;

    alter table T_LEVEL_UP_TRAINING_DECISIONS 
        add constraint UK_e55t66sktxlv50j5s7jk3j7x1  unique (trainingDecisions_ID);

    alter table T_PROFESSIONS_REALMS_MAGIC 
        drop constraint UK_8nvq9erlbw339m1kj0gisa025;

    alter table T_PROFESSIONS_REALMS_MAGIC 
        add constraint UK_8nvq9erlbw339m1kj0gisa025  unique (comparationId);

    alter table T_PROFESSION_DECISIONS 
        drop constraint UK_16qm0bkl5f4pi9qw4bf8x9hra;

    alter table T_PROFESSION_DECISIONS 
        add constraint UK_16qm0bkl5f4pi9qw4bf8x9hra  unique (comparationId);

    alter table T_ROLL 
        drop constraint UK_ddfjd9r5evwwehksmtwe8cay1;

    alter table T_ROLL 
        add constraint UK_ddfjd9r5evwwehksmtwe8cay1  unique (comparationId);

    alter table T_ROLL_GROUP 
        drop constraint UK_1tsw2ugbypulgt0xywmak2llm;

    alter table T_ROLL_GROUP 
        add constraint UK_1tsw2ugbypulgt0xywmak2llm  unique (comparationId);

    alter table T_INSERTED_TRAINING_DECISIONS 
        add constraint FK_ahxpf8sitwcloo3ha8nft78k3 
        foreign key (trainingDecisions_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_INSERTED_TRAINING_DECISIONS 
        add constraint FK_op0s59o6argyn0qqylbm4m0rk 
        foreign key (T_INSERTED_DATA_ID) 
        references T_INSERTED_DATA (ID);

    alter table T_LEVEL_UP_CHARACTERISTICS_UPDATES 
        add constraint FK_mayw23itpoug630tuos9ycyjx 
        foreign key (characteristicsUpdates_ID) 
        references T_CHARACTERISTIC_ROLL_GROUP (ID);

    alter table T_LEVEL_UP_CHARACTERISTICS_UPDATES 
        add constraint FK_rvudfsdm7kvogeki9oi10twqm 
        foreign key (T_LEVELUP_ID) 
        references T_LEVELUP (ID);

    alter table T_LEVEL_UP_TRAINING_DECISIONS 
        add constraint FK_e55t66sktxlv50j5s7jk3j7x1 
        foreign key (trainingDecisions_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_LEVEL_UP_TRAINING_DECISIONS 
        add constraint FK_lkuxpl4qspogc1n057w8gq208 
        foreign key (T_LEVELUP_ID) 
        references T_LEVELUP (ID);
