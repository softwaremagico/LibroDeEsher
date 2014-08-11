
    create table T_APPEARANCE (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        dicesResult integer,
        primary key (ID)
    );

    create table T_CATEGORY_COST (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_CHARACTERISTIC_ROLL_GROUP (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        characteristicAbbreviature varchar(255),
        characteristicPotentialValue integer,
        characteristicTemporalValue integer,
        roll_ID bigint,
        primary key (ID)
    );

    create table T_CHARACTERPLAYER (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        characteristicsConfirmed bit not null,
        chiPowersAllowed bit not null,
        cultureName varchar(255),
        darkSpellsAsBasicListsAllowed bit not null,
        firearmsAllowed bit not null,
        historyText varchar(255),
        name varchar(255),
        otherRealmtrainingSpellsAllowed bit not null,
        professionName varchar(255),
        raceName varchar(255),
        sex integer,
        version varchar(255),
        appearance_ID bigint,
        cultureDecisionsId bigint,
        historialId bigint,
        professionDecisionsId bigint,
        professionalRealmId bigint,
        primary key (ID)
    );

    create table T_CHARACTERPLAYER_INITIAL_TEMPORAL_VALUES (
        CharacterPlayer_ID bigint not null,
        characteristicsInitialTemporalValues integer,
        characteristicsInitialTemporalValues_KEY integer,
        primary key (CharacterPlayer_ID, characteristicsInitialTemporalValues_KEY)
    );

    create table T_CHARACTERPLAYER_LEVEL_UP (
        T_CHARACTERPLAYER_ID bigint not null,
        levelUps_ID bigint not null,
        level_index integer not null,
        primary key (T_CHARACTERPLAYER_ID, level_index)
    );

    create table T_CHARACTERPLAYER_PERKS_DECISIONS (
        CharacterPlayer_ID bigint not null,
        perkDecisions_ID bigint not null,
        perkDecisions_KEY varchar(255),
        primary key (CharacterPlayer_ID, perkDecisions_KEY)
    );

    create table T_CHARACTERPLAYER_POTENTIAL_VALUES (
        CharacterPlayer_ID bigint not null,
        characteristicsPotentialValues integer,
        characteristicsPotentialValues_KEY integer,
        primary key (CharacterPlayer_ID, characteristicsPotentialValues_KEY)
    );

    create table T_CHARACTERPLAYER_SELECTED_PERKS (
        T_CHARACTERPLAYER_ID bigint not null,
        selectedPerks_ID bigint not null
    );

    create table T_CHARACTERPLAYER_TEMPORAL_CHARACTERISTICS_ROLLS (
        T_CHARACTERPLAYER_ID bigint not null,
        characteristicsTemporalUpdatesRolls_ID bigint not null,
        characteristicsTemporalUpdatesRolls_KEY integer,
        primary key (T_CHARACTERPLAYER_ID, characteristicsTemporalUpdatesRolls_KEY)
    );

    create table T_CHARACTERPLAYER_TRAINING_DECISIONS (
        T_CHARACTERPLAYER_ID bigint not null,
        trainingDecisions_ID bigint not null,
        trainingDecisions_KEY varchar(255),
        primary key (T_CHARACTERPLAYER_ID, trainingDecisions_KEY)
    );

    create table T_CULTUREDECISIONS (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_CULTURE_HOBBY_RANKS (
        CultureDecisions_ID bigint not null,
        hobbyRanks integer,
        hobbyRanks_KEY varchar(255),
        primary key (CultureDecisions_ID, hobbyRanks_KEY)
    );

    create table T_CULTURE_LANGUAGE_RANKS (
        CultureDecisions_ID bigint not null,
        languageRanks integer,
        languageRanks_KEY varchar(255),
        primary key (CultureDecisions_ID, languageRanks_KEY)
    );

    create table T_CULTURE_SPELL_RANKS (
        CultureDecisions_ID bigint not null,
        spellRanks integer,
        spellRanks_KEY varchar(255),
        primary key (CultureDecisions_ID, spellRanks_KEY)
    );

    create table T_CULTURE_WEAPON_RANKS (
        CultureDecisions_ID bigint not null,
        weaponRanks integer,
        weaponRanks_KEY varchar(255),
        primary key (CultureDecisions_ID, weaponRanks_KEY)
    );

    create table T_HISTORIAL (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_HISTORIAL_CATEGORIES (
        Historial_ID bigint not null,
        categories varchar(255)
    );

    create table T_HISTORIAL_CHARACTERISTICS_UPDATES (
        T_HISTORIAL_ID bigint not null,
        characteristicsUpdates_ID bigint not null,
        roll_index integer not null,
        primary key (T_HISTORIAL_ID, roll_index)
    );

    create table T_HISTORIAL_SKILLS (
        Historial_ID bigint not null,
        skills varchar(255)
    );

    create table T_LEVELUP (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_LEVELUP_CATEGORIES_RANKS (
        LevelUp_ID bigint not null,
        categoriesRanks integer,
        categoriesRanks_KEY varchar(255),
        primary key (LevelUp_ID, categoriesRanks_KEY)
    );

    create table T_LEVELUP_GENERALIZED_SKILLS (
        LevelUp_ID bigint not null,
        generalizedSkills varchar(255)
    );

    create table T_LEVELUP_SKILLS_RANKS (
        LevelUp_ID bigint not null,
        skillsRanks integer,
        skillsRanks_KEY varchar(255),
        primary key (LevelUp_ID, skillsRanks_KEY)
    );

    create table T_LEVEL_UP_SKILL_SPECIALIZATIONS (
        LevelUp_ID bigint not null,
        skillSpecializations varchar(255)
    );

    create table T_LEVEL_UP_SPELLS_UPDATED (
        LevelUp_ID bigint not null,
        spellsUpdated varchar(255)
    );

    create table T_LEVEL_UP_TRAININGS_ADQUIRED (
        LevelUp_ID bigint not null,
        trainings varchar(255)
    );

    create table T_PERKS (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        cost integer,
        name varchar(255),
        primary key (ID)
    );

    create table T_PERKS_DECISION (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_PERKS_DECISION_CATEGORY_BONUS_CHOSEN (
        PerkDecision_ID bigint not null,
        categoriesBonusChosen varchar(255)
    );

    create table T_PERKS_DECISION_COMMON_SKILLS_CHOSEN (
        PerkDecision_ID bigint not null,
        commonSkillsChosen varchar(255)
    );

    create table T_PERKS_DECISION_SKILLS_BONUS_CHOSEN (
        PerkDecision_ID bigint not null,
        skillsBonusChosen varchar(255)
    );

    create table T_PROFESSIONS_REALMS_MAGIC (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_PROFESSION_COMMON_SKILLS_CHOSEN (
        ProfessionDecisions_ID bigint not null,
        commonSkillsChosen varchar(255)
    );

    create table T_PROFESSION_DECISIONS (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_PROFESSION_PROFESSIONAL_SKILLS_CHOSEN (
        ProfessionDecisions_ID bigint not null,
        professionalSkillsChosen varchar(255)
    );

    create table T_PROFESSION_REALMS_OF_MAGIC_AVAILABLE (
        ProfessionalRealmsOfMagicOptions_ID bigint not null,
        magicRealmsAvailable integer
    );

    create table T_PROFESSION_RESTRICTED_SKILLS_CHOSEN (
        ProfessionDecisions_ID bigint not null,
        restrictedSkillsChosen varchar(255)
    );

    create table T_PROFESSION_WEAPON_COST_CHOSEN (
        T_PROFESSION_DECISIONS_ID bigint not null,
        weaponsCost_ID bigint not null,
        weaponsCost_KEY varchar(255),
        primary key (T_PROFESSION_DECISIONS_ID, weaponsCost_KEY)
    );

    create table T_RANK_COSTS (
        CategoryCost_ID bigint not null,
        rankCost integer
    );

    create table T_ROLL (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        firstDice integer,
        secondDice integer,
        primary key (ID)
    );

    create table T_ROLL_GROUP (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        characteristicAbbreviature varchar(255),
        primary key (ID)
    );

    create table T_ROLL_LIST (
        T_ROLL_GROUP_ID bigint not null,
        rolls_ID bigint not null,
        roll_index integer not null,
        primary key (T_ROLL_GROUP_ID, roll_index)
    );

    create table T_TRAINING_CATEGORIES (
        TrainingCategoriesSelected_ID bigint not null,
        categories varchar(255)
    );

    create table T_TRAINING_CATEGORIES_SELECTED (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_TRAINING_CHARACTERISTICS_UPDATES (
        T_TRAINING_DECISION_ID bigint not null,
        characteristicsUpdates_ID bigint not null
    );

    create table T_TRAINING_DECISION (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_TRAINING_DECISION_CATEGORY_SELECTED (
        T_TRAINING_DECISION_ID bigint not null,
        categoriesSelected_ID bigint not null,
        categoriesSelected_KEY integer,
        primary key (T_TRAINING_DECISION_ID, categoriesSelected_KEY)
    );

    create table T_TRAINING_DECISION_SKILLS_SELECTED (
        T_TRAINING_DECISION_ID bigint not null,
        skillsSelected_ID bigint not null,
        skillsSelected_KEY integer,
        primary key (T_TRAINING_DECISION_ID, skillsSelected_KEY)
    );

    create table T_TRAINING_OBJECT (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        bonus integer not null,
        name varchar(255),
        probability integer not null,
        primary key (ID)
    );

    create table T_TRAINING_OBJECTS (
        T_TRAINING_DECISION_ID bigint not null,
        equipment_ID bigint not null
    );

    create table T_TRAINING_SKILL (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        ranks integer,
        primary key (ID)
    );

    create table T_TRAINING_SKILLS_OPTIONS (
        TrainingSkill_ID bigint not null,
        skillOptions varchar(255)
    );

    create table T_TRAINING_SKILLS_SELECTED (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_TRAINING_SKILLS_SELECTED_LIST_OF_SKILLS (
        TrainingSkillsSelected_ID bigint not null,
        skills integer,
        skills_KEY bigint not null,
        primary key (TrainingSkillsSelected_ID, skills_KEY)
    );

    create table T_TRAINING_SKILL_LIST (
        ID bigint not null,
        comparationId varchar(255) not null,
        creationTime datetime not null,
        updateTime datetime,
        primary key (ID)
    );

    create table T_TRAINING_SKILL_LIST_OF_SKILLS (
        TrainingSkillList_ID bigint not null,
        trainingSkills_ID bigint not null
    );

    alter table T_APPEARANCE 
        add constraint UK_hlcvnifaglalnf0dpfbcak1u8  unique (ID);

    alter table T_APPEARANCE 
        add constraint UK_fvvhnk8c7ii3jvbmo5b6fg5un  unique (comparationId);

    alter table T_CATEGORY_COST 
        add constraint UK_fcmfkigake2hqq44gyge781xw  unique (ID);

    alter table T_CATEGORY_COST 
        add constraint UK_8jn0hgs0qu2147j9ge5siu0qq  unique (comparationId);

    alter table T_CHARACTERISTIC_ROLL_GROUP 
        add constraint UK_g823n2nv2eyimog2a4dpckhbd  unique (ID);

    alter table T_CHARACTERISTIC_ROLL_GROUP 
        add constraint UK_76e7clc7lyv03qmf0d6lvgyi2  unique (comparationId);

    alter table T_CHARACTERPLAYER 
        add constraint UK_conobsyh7ebxy51m6q8ieunak  unique (ID);

    alter table T_CHARACTERPLAYER 
        add constraint UK_goif6qdq0k03683pf598f958  unique (comparationId);

    alter table T_CHARACTERPLAYER_LEVEL_UP 
        add constraint UK_l8mhtklrekoduk3xl5a85ll55  unique (levelUps_ID);

    alter table T_CHARACTERPLAYER_PERKS_DECISIONS 
        add constraint UK_2rei81rlw78vo1pss3lmg7lsq  unique (perkDecisions_ID);

    alter table T_CHARACTERPLAYER_SELECTED_PERKS 
        add constraint UK_c36v4kn7rg6y2oeqamj1vftp6  unique (selectedPerks_ID);

    alter table T_CHARACTERPLAYER_TEMPORAL_CHARACTERISTICS_ROLLS 
        add constraint UK_rsdhy70yoehafjvur7w9b2o66  unique (characteristicsTemporalUpdatesRolls_ID);

    alter table T_CHARACTERPLAYER_TRAINING_DECISIONS 
        add constraint UK_qjo2k3kqlj5990u97nk2bbf5c  unique (trainingDecisions_ID);

    alter table T_CULTUREDECISIONS 
        add constraint UK_blq96e017u1trnpdefgr091nl  unique (ID);

    alter table T_CULTUREDECISIONS 
        add constraint UK_i4vxctno4axrv6clm66mmrngb  unique (comparationId);

    alter table T_HISTORIAL 
        add constraint UK_b78higqrsil53rexxwbd88dn3  unique (ID);

    alter table T_HISTORIAL 
        add constraint UK_mguv54adf0m90noy52x1lea5x  unique (comparationId);

    alter table T_HISTORIAL_CHARACTERISTICS_UPDATES 
        add constraint UK_ryqf6ahmvfyfo3da42imitxen  unique (characteristicsUpdates_ID);

    alter table T_LEVELUP 
        add constraint UK_e9y7b2htj85ls5jccr8dscoiv  unique (ID);

    alter table T_LEVELUP 
        add constraint UK_s260ukdop7lmbms1wy8vru5mp  unique (comparationId);

    alter table T_PERKS 
        add constraint UK_poul3t4t5tpjoxm9csig34aml  unique (ID);

    alter table T_PERKS 
        add constraint UK_a0gfx9ounae0o9sm9x40i5rdg  unique (comparationId);

    alter table T_PERKS_DECISION 
        add constraint UK_lom2ng3ig68tkf86di7wwjk0s  unique (ID);

    alter table T_PERKS_DECISION 
        add constraint UK_s4uwcg9f92ddv6obvlt98shqo  unique (comparationId);

    alter table T_PROFESSIONS_REALMS_MAGIC 
        add constraint UK_b8a8hn5ijfh46oagm4d2um6qa  unique (ID);

    alter table T_PROFESSIONS_REALMS_MAGIC 
        add constraint UK_8nvq9erlbw339m1kj0gisa025  unique (comparationId);

    alter table T_PROFESSION_DECISIONS 
        add constraint UK_li3y57bhuj92ukf2ys827be5j  unique (ID);

    alter table T_PROFESSION_DECISIONS 
        add constraint UK_16qm0bkl5f4pi9qw4bf8x9hra  unique (comparationId);

    alter table T_PROFESSION_WEAPON_COST_CHOSEN 
        add constraint UK_hyu9pvkqjkxlo6ldjah1i6mkk  unique (weaponsCost_ID);

    alter table T_ROLL 
        add constraint UK_9r4ehgb8fn2dn3d3sd8xvd4fd  unique (ID);

    alter table T_ROLL 
        add constraint UK_ddfjd9r5evwwehksmtwe8cay1  unique (comparationId);

    alter table T_ROLL_GROUP 
        add constraint UK_ec1a2pas67gu5pl4tfelsan0r  unique (ID);

    alter table T_ROLL_GROUP 
        add constraint UK_1tsw2ugbypulgt0xywmak2llm  unique (comparationId);

    alter table T_ROLL_LIST 
        add constraint UK_aoc04hgu3c7snaanjdg9tdlq6  unique (rolls_ID);

    alter table T_TRAINING_CATEGORIES_SELECTED 
        add constraint UK_cjlsw6faxbj52bjt31aow627g  unique (ID);

    alter table T_TRAINING_CATEGORIES_SELECTED 
        add constraint UK_5erag6etowycwtuhypccyvl4o  unique (comparationId);

    alter table T_TRAINING_CHARACTERISTICS_UPDATES 
        add constraint UK_fnar1y6mb6imcc4ik6n3h72y6  unique (characteristicsUpdates_ID);

    alter table T_TRAINING_DECISION 
        add constraint UK_ooc3nxaim40wa5pawjtvkaeug  unique (ID);

    alter table T_TRAINING_DECISION 
        add constraint UK_1fy563xslnew4m359jnvmyghf  unique (comparationId);

    alter table T_TRAINING_DECISION_CATEGORY_SELECTED 
        add constraint UK_dcgwqauprl9jdgym1rwfd8i3v  unique (categoriesSelected_ID);

    alter table T_TRAINING_DECISION_SKILLS_SELECTED 
        add constraint UK_5nc8p7y8l2d6jwmdevwemmrtx  unique (skillsSelected_ID);

    alter table T_TRAINING_OBJECT 
        add constraint UK_6qjbapnqh4clnepqgn6llqq36  unique (ID);

    alter table T_TRAINING_OBJECT 
        add constraint UK_7dphteoforn6hgtxxd3ft66bq  unique (comparationId);

    alter table T_TRAINING_OBJECTS 
        add constraint UK_7ixq2spabhxg6hap4um27sj1y  unique (equipment_ID);

    alter table T_TRAINING_SKILL 
        add constraint UK_efgh32mr4nx5s2o51m02qmb4c  unique (ID);

    alter table T_TRAINING_SKILL 
        add constraint UK_ddblmd1gd1127mdjkupsm7n8c  unique (comparationId);

    alter table T_TRAINING_SKILLS_SELECTED 
        add constraint UK_8t3mjh043dknq4icvg66qjl9l  unique (ID);

    alter table T_TRAINING_SKILLS_SELECTED 
        add constraint UK_s4duauy8ccp5mc0938bw027dl  unique (comparationId);

    alter table T_TRAINING_SKILL_LIST 
        add constraint UK_kmmv73vgorqjik0phftinv7ry  unique (ID);

    alter table T_TRAINING_SKILL_LIST 
        add constraint UK_b540l9pmnuvfrgj7une98ksax  unique (comparationId);

    alter table T_TRAINING_SKILL_LIST_OF_SKILLS 
        add constraint UK_116wv314ey74v4oy4plvy9ied  unique (trainingSkills_ID);

    alter table T_CHARACTERISTIC_ROLL_GROUP 
        add constraint FK_jb6k15e74i40yqa7r969c1qwx 
        foreign key (roll_ID) 
        references T_ROLL (ID);

    alter table T_CHARACTERPLAYER 
        add constraint FK_85pdo40kki5hlykhibmdj10xl 
        foreign key (appearance_ID) 
        references T_APPEARANCE (ID);

    alter table T_CHARACTERPLAYER 
        add constraint FK_a6if2gfydatge62dg8j1pgy5w 
        foreign key (cultureDecisionsId) 
        references T_CULTUREDECISIONS (ID);

    alter table T_CHARACTERPLAYER 
        add constraint FK_fosyvcj2m4uxy71k8c0yal7f4 
        foreign key (historialId) 
        references T_HISTORIAL (ID);

    alter table T_CHARACTERPLAYER 
        add constraint FK_h988k07hk2n60qnuqd21dakou 
        foreign key (professionDecisionsId) 
        references T_PROFESSION_DECISIONS (ID);

    alter table T_CHARACTERPLAYER 
        add constraint FK_oj2d58brr5h1doqyb52ywkmew 
        foreign key (professionalRealmId) 
        references T_PROFESSIONS_REALMS_MAGIC (ID);

    alter table T_CHARACTERPLAYER_INITIAL_TEMPORAL_VALUES 
        add constraint FK_s4do2so1bmthu4487n53g901n 
        foreign key (CharacterPlayer_ID) 
        references T_CHARACTERPLAYER (ID);

    alter table T_CHARACTERPLAYER_LEVEL_UP 
        add constraint FK_l8mhtklrekoduk3xl5a85ll55 
        foreign key (levelUps_ID) 
        references T_LEVELUP (ID);

    alter table T_CHARACTERPLAYER_LEVEL_UP 
        add constraint FK_f486l1torg28a6w7chhh1ktbg 
        foreign key (T_CHARACTERPLAYER_ID) 
        references T_CHARACTERPLAYER (ID);

    alter table T_CHARACTERPLAYER_PERKS_DECISIONS 
        add constraint FK_2rei81rlw78vo1pss3lmg7lsq 
        foreign key (perkDecisions_ID) 
        references T_PERKS_DECISION (ID);

    alter table T_CHARACTERPLAYER_PERKS_DECISIONS 
        add constraint FK_1h6iqvuieg95r8ftqy5rnu14s 
        foreign key (CharacterPlayer_ID) 
        references T_CHARACTERPLAYER (ID);

    alter table T_CHARACTERPLAYER_POTENTIAL_VALUES 
        add constraint FK_n87ng61k8wj5d1sfq91jcvpt4 
        foreign key (CharacterPlayer_ID) 
        references T_CHARACTERPLAYER (ID);

    alter table T_CHARACTERPLAYER_SELECTED_PERKS 
        add constraint FK_c36v4kn7rg6y2oeqamj1vftp6 
        foreign key (selectedPerks_ID) 
        references T_PERKS (ID);

    alter table T_CHARACTERPLAYER_SELECTED_PERKS 
        add constraint FK_1i4nykoq34g1roy464q5mi2qe 
        foreign key (T_CHARACTERPLAYER_ID) 
        references T_CHARACTERPLAYER (ID);

    alter table T_CHARACTERPLAYER_TEMPORAL_CHARACTERISTICS_ROLLS 
        add constraint FK_rsdhy70yoehafjvur7w9b2o66 
        foreign key (characteristicsTemporalUpdatesRolls_ID) 
        references T_ROLL_GROUP (ID);

    alter table T_CHARACTERPLAYER_TEMPORAL_CHARACTERISTICS_ROLLS 
        add constraint FK_cwbxkyfe4g8d93yl6eruxvhyf 
        foreign key (T_CHARACTERPLAYER_ID) 
        references T_CHARACTERPLAYER (ID);

    alter table T_CHARACTERPLAYER_TRAINING_DECISIONS 
        add constraint FK_qjo2k3kqlj5990u97nk2bbf5c 
        foreign key (trainingDecisions_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_CHARACTERPLAYER_TRAINING_DECISIONS 
        add constraint FK_qxmqef1t6i3ikmucb2of78mdj 
        foreign key (T_CHARACTERPLAYER_ID) 
        references T_CHARACTERPLAYER (ID);

    alter table T_CULTURE_HOBBY_RANKS 
        add constraint FK_3qei47al2s0hxq2w1uo0ml0ai 
        foreign key (CultureDecisions_ID) 
        references T_CULTUREDECISIONS (ID);

    alter table T_CULTURE_LANGUAGE_RANKS 
        add constraint FK_f0iotbk7g37ft2eid62prpsct 
        foreign key (CultureDecisions_ID) 
        references T_CULTUREDECISIONS (ID);

    alter table T_CULTURE_SPELL_RANKS 
        add constraint FK_qf6tc6n7nslys2rhnhe94cgw 
        foreign key (CultureDecisions_ID) 
        references T_CULTUREDECISIONS (ID);

    alter table T_CULTURE_WEAPON_RANKS 
        add constraint FK_kcq1tf5emosdrc3jd4ok65whv 
        foreign key (CultureDecisions_ID) 
        references T_CULTUREDECISIONS (ID);

    alter table T_HISTORIAL_CATEGORIES 
        add constraint FK_bywynufv173nmi7oi67mifquw 
        foreign key (Historial_ID) 
        references T_HISTORIAL (ID);

    alter table T_HISTORIAL_CHARACTERISTICS_UPDATES 
        add constraint FK_ryqf6ahmvfyfo3da42imitxen 
        foreign key (characteristicsUpdates_ID) 
        references T_CHARACTERISTIC_ROLL_GROUP (ID);

    alter table T_HISTORIAL_CHARACTERISTICS_UPDATES 
        add constraint FK_bu9fdjwtblk4m412wp2v4ft9k 
        foreign key (T_HISTORIAL_ID) 
        references T_HISTORIAL (ID);

    alter table T_HISTORIAL_SKILLS 
        add constraint FK_3jaxecovlcm50nr1sxbl3hvkr 
        foreign key (Historial_ID) 
        references T_HISTORIAL (ID);

    alter table T_LEVELUP_CATEGORIES_RANKS 
        add constraint FK_qfiu7ugdjl7amg1s25i9j3byj 
        foreign key (LevelUp_ID) 
        references T_LEVELUP (ID);

    alter table T_LEVELUP_GENERALIZED_SKILLS 
        add constraint FK_6n8yhtwyywo6dd85q0w4f6hf3 
        foreign key (LevelUp_ID) 
        references T_LEVELUP (ID);

    alter table T_LEVELUP_SKILLS_RANKS 
        add constraint FK_pqvlidwgdyyxcup7fwk8pohs 
        foreign key (LevelUp_ID) 
        references T_LEVELUP (ID);

    alter table T_LEVEL_UP_SKILL_SPECIALIZATIONS 
        add constraint FK_67y497yssq86931g5r7rqpjyp 
        foreign key (LevelUp_ID) 
        references T_LEVELUP (ID);

    alter table T_LEVEL_UP_SPELLS_UPDATED 
        add constraint FK_kf3828eg2e2cxfb2jviphqu3e 
        foreign key (LevelUp_ID) 
        references T_LEVELUP (ID);

    alter table T_LEVEL_UP_TRAININGS_ADQUIRED 
        add constraint FK_qulxx88l837mgli4re9byjayc 
        foreign key (LevelUp_ID) 
        references T_LEVELUP (ID);

    alter table T_PERKS_DECISION_CATEGORY_BONUS_CHOSEN 
        add constraint FK_f1fjh72xeap6mctkfyviei7qy 
        foreign key (PerkDecision_ID) 
        references T_PERKS_DECISION (ID);

    alter table T_PERKS_DECISION_COMMON_SKILLS_CHOSEN 
        add constraint FK_4oocuq82gxdbki49xmfpteikt 
        foreign key (PerkDecision_ID) 
        references T_PERKS_DECISION (ID);

    alter table T_PERKS_DECISION_SKILLS_BONUS_CHOSEN 
        add constraint FK_ns1rxxvksdcpff7ckgpmymjel 
        foreign key (PerkDecision_ID) 
        references T_PERKS_DECISION (ID);

    alter table T_PROFESSION_COMMON_SKILLS_CHOSEN 
        add constraint FK_2raew6dquhw5mda7g7ukpjodc 
        foreign key (ProfessionDecisions_ID) 
        references T_PROFESSION_DECISIONS (ID);

    alter table T_PROFESSION_PROFESSIONAL_SKILLS_CHOSEN 
        add constraint FK_3nawdfjc26ba8fu1gwjiip7kp 
        foreign key (ProfessionDecisions_ID) 
        references T_PROFESSION_DECISIONS (ID);

    alter table T_PROFESSION_REALMS_OF_MAGIC_AVAILABLE 
        add constraint FK_a28bsrg0b5aje416kqoh9307e 
        foreign key (ProfessionalRealmsOfMagicOptions_ID) 
        references T_PROFESSIONS_REALMS_MAGIC (ID);

    alter table T_PROFESSION_RESTRICTED_SKILLS_CHOSEN 
        add constraint FK_gf55ebjk1v194n23twxc9v9wx 
        foreign key (ProfessionDecisions_ID) 
        references T_PROFESSION_DECISIONS (ID);

    alter table T_PROFESSION_WEAPON_COST_CHOSEN 
        add constraint FK_hyu9pvkqjkxlo6ldjah1i6mkk 
        foreign key (weaponsCost_ID) 
        references T_CATEGORY_COST (ID);

    alter table T_PROFESSION_WEAPON_COST_CHOSEN 
        add constraint FK_shfc02rjnvjc79t8k3256xaf7 
        foreign key (T_PROFESSION_DECISIONS_ID) 
        references T_PROFESSION_DECISIONS (ID);

    alter table T_RANK_COSTS 
        add constraint FK_69x0wc8v7hhn7cm9xxlp3ufjw 
        foreign key (CategoryCost_ID) 
        references T_CATEGORY_COST (ID);

    alter table T_ROLL_LIST 
        add constraint FK_aoc04hgu3c7snaanjdg9tdlq6 
        foreign key (rolls_ID) 
        references T_ROLL (ID);

    alter table T_ROLL_LIST 
        add constraint FK_ehkmut7e29mc7d4frcws27ui8 
        foreign key (T_ROLL_GROUP_ID) 
        references T_ROLL_GROUP (ID);

    alter table T_TRAINING_CATEGORIES 
        add constraint FK_18lm0woocm7rkt2so0nrim3ao 
        foreign key (TrainingCategoriesSelected_ID) 
        references T_TRAINING_CATEGORIES_SELECTED (ID);

    alter table T_TRAINING_CHARACTERISTICS_UPDATES 
        add constraint FK_fnar1y6mb6imcc4ik6n3h72y6 
        foreign key (characteristicsUpdates_ID) 
        references T_CHARACTERISTIC_ROLL_GROUP (ID);

    alter table T_TRAINING_CHARACTERISTICS_UPDATES 
        add constraint FK_h4k7fjq7243ufkniehfblk7sl 
        foreign key (T_TRAINING_DECISION_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_TRAINING_DECISION_CATEGORY_SELECTED 
        add constraint FK_dcgwqauprl9jdgym1rwfd8i3v 
        foreign key (categoriesSelected_ID) 
        references T_TRAINING_CATEGORIES_SELECTED (ID);

    alter table T_TRAINING_DECISION_CATEGORY_SELECTED 
        add constraint FK_jfqy4psrv51jc13552x13ru6f 
        foreign key (T_TRAINING_DECISION_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_TRAINING_DECISION_SKILLS_SELECTED 
        add constraint FK_5nc8p7y8l2d6jwmdevwemmrtx 
        foreign key (skillsSelected_ID) 
        references T_TRAINING_SKILLS_SELECTED (ID);

    alter table T_TRAINING_DECISION_SKILLS_SELECTED 
        add constraint FK_q2qlhc9q74eva8nvqkhnlqdlb 
        foreign key (T_TRAINING_DECISION_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_TRAINING_OBJECTS 
        add constraint FK_7ixq2spabhxg6hap4um27sj1y 
        foreign key (equipment_ID) 
        references T_TRAINING_OBJECT (ID);

    alter table T_TRAINING_OBJECTS 
        add constraint FK_o5udpgul4alabv13apa6ut630 
        foreign key (T_TRAINING_DECISION_ID) 
        references T_TRAINING_DECISION (ID);

    alter table T_TRAINING_SKILLS_OPTIONS 
        add constraint FK_778uv1d7fs8a0hbjh50c6cm8y 
        foreign key (TrainingSkill_ID) 
        references T_TRAINING_SKILL (ID);

    alter table T_TRAINING_SKILLS_SELECTED_LIST_OF_SKILLS 
        add constraint FK_2jyut54is6tewoer42u49rbr8 
        foreign key (skills_KEY) 
        references T_TRAINING_SKILL (ID);

    alter table T_TRAINING_SKILLS_SELECTED_LIST_OF_SKILLS 
        add constraint FK_6byn9lj59rtyyr7wb695wfid2 
        foreign key (TrainingSkillsSelected_ID) 
        references T_TRAINING_SKILLS_SELECTED (ID);

    alter table T_TRAINING_SKILL_LIST_OF_SKILLS 
        add constraint FK_116wv314ey74v4oy4plvy9ied 
        foreign key (trainingSkills_ID) 
        references T_TRAINING_SKILL (ID);

    alter table T_TRAINING_SKILL_LIST_OF_SKILLS 
        add constraint FK_b7snr3gc1km45ah7e8ga8qecn 
        foreign key (TrainingSkillList_ID) 
        references T_TRAINING_SKILL_LIST (ID);

    create table hibernate_sequences (
         sequence_name varchar(255),
         sequence_next_hi_value integer 
    );
